package com.lj.cmp.service.siteconfig

import com.lj.cmp.util.common.I18nError
import com.lj.cmp.util.customenum.ContentBlockCode
import com.lj.cmp.util.customenum.ReCode
import com.lj.cmp.data.ContentBlockInfo
import com.lj.cmp.data.ContentClassInfo
import com.lj.cmp.data.ContentsInfo
import com.lj.cmp.data.ImageInfo

import com.lj.cmp.util.common.Number
import com.lj.cmp.util.common.TypeConversion;

class ContentBlockService {
    def webUtilService;

    def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib();

    def save(def params) {
        def request = webUtilService.getRequest();
        //获取参数
        long id = Number.toLong(params.id);
        String name = params.name;
        int code = Number.toInteger(params.code);
        String logoUrl = params.logoUrl;
        boolean inMenu = TypeConversion.toBoolean(params.inMenu);
        boolean inNav = TypeConversion.toBoolean(params.inNav);
        int maxCount=Number.toInteger(params.maxCount);

        long contentsId = Number.toLong(params.contentsId);
        ContentsInfo contentsInfo = ContentsInfo.get(contentsId);
        if(contentsInfo){
            contentsInfo.canRemove=false;
            contentsInfo.save(flush: true);
        }

        ContentBlockInfo contentBlockInfo = ContentBlockInfo.get(id);
        if (contentBlockInfo == null) {
            contentBlockInfo = new ContentBlockInfo();
        }

        if(contentBlockInfo.contentsInfo){
            contentBlockInfo.contentsInfo.canRemove=true;
            contentBlockInfo.contentsInfo.save(flush: true);
        }

        contentBlockInfo.name = name;
        contentBlockInfo.code = code;
        contentBlockInfo.logoUrl = logoUrl;
        contentBlockInfo.inMenu = inMenu;
        contentBlockInfo.inNav = inNav;
        contentBlockInfo.contentsInfo = contentsInfo;
        contentBlockInfo.maxCount=maxCount;

        //获取设置类别列表
        if(contentBlockInfo.contentClassInfoList){
            contentBlockInfo.contentClassInfoList.each {
                it.canRemove=true;
                it.save(flush: true);
            }
        }
        String contentClassIdsStr = params.contentClassIds;
        def ccIdsStrs = contentClassIdsStr.split(",");
        if (ccIdsStrs) {
            List<ContentClassInfo> contentClassInfoList = new ArrayList<ContentClassInfo>();
            ccIdsStrs.each {
                long ccId = TypeConversion.toLong(it);
                ContentClassInfo contentClassInfo = ContentClassInfo.get(ccId);
                if (contentClassInfo) {
                    contentClassInfo.canRemove=false;
                    contentBlockInfo.save(flush: true);
                    contentClassInfoList.add(contentClassInfo);
                }
            }
            if (contentClassInfoList) {
                contentBlockInfo.contentClassInfoList = contentClassInfoList;
            }
            else{
                contentBlockInfo.contentClassInfoList =null;
            }
        } else{
            contentBlockInfo.contentClassInfoList =null;
        }

        //设置内容列表
        if(contentBlockInfo.contentsInfoList){
            contentBlockInfo.contentsInfoList.each {
                it.canRemove=true;
                it.save(flush: true);
            }
        }
        if (params.contentsInfoIds) {
            List<ContentsInfo> contentsInfoList = new ArrayList<ContentsInfo>();
            if (params.contentsInfoIds instanceof String) {
                long contentsInfoId = Number.toLong(params.contentsInfoIds);
                ContentsInfo contentsInfo1 = ContentsInfo.get(contentsInfoId);
                if (contentsInfo1) {
                    contentsInfoList.add(contentsInfo1);
                    contentsInfo1.canRemove=false;
                    contentsInfo1.save(flush: true);
                }
            } else if (params.contentsInfoIds instanceof String[]) {
                String[] contentsInfoIds = params.contentsInfoIds;
                contentsInfoIds.each {
                    long contentsInfoId = Number.toLong(it);
                    ContentsInfo contentsInfo1 = ContentsInfo.get(contentsInfoId);
                    if (contentsInfo1) {
                        contentsInfoList.add(contentsInfo1);
                        contentsInfo1.canRemove=false;
                        contentsInfo1.save(flush: true);
                    }
                }
            }
            contentBlockInfo.contentsInfoList=contentsInfoList;
        } else {
            contentBlockInfo.contentsInfoList = null;
        }

        if (!contentBlockInfo.save(flush: true)) {
            throw new RuntimeException(I18nError.getMessage(g, contentBlockInfo.errors.allErrors));
            //return [recode: ReCode.SAVE_FAILED, contentsInfo: contentsInfo, errors: I18nError.getMessage(g, contentsInfo.errors.allErrors)]
        }
        contentBlockInfo.refresh();
        return [recode: ReCode.OK, contentBlockInfo: contentBlockInfo];
    }

    def list() {
        def contentBlockInfoList = ContentBlockInfo.getAll();
        return [recode: ReCode.OK, contentBlockInfoList: contentBlockInfoList];
    }

    def get(def params) {
        long id = Number.toLong(params.id);
        ContentBlockInfo contentBlockInfo = ContentBlockInfo.get(id);
        if (contentBlockInfo) {
            return [recode: ReCode.OK, contentBlockInfo: contentBlockInfo];
        } else {
            return [recode: ReCode.NO_RESULT, contentBlockInfo: contentBlockInfo];
        }
    }
    //删除内容
    def deleteContentBlock() {
        def request = webUtilService.getRequest();
        def params = request.getParameterMap();
        def idList = [];
        if (params.ids instanceof String) { //传入id
            //获取参数
            long id = Number.toLong(params.ids);//图片信息ID
            idList.add(id);
        } else if (params.ids instanceof String[]) {//传入id数组
            for (int i = 0; i < params.ids.length; i++) {
                Long id = Number.toLong(params.ids[i]);
                idList.add(id);
            }
        }
        def errors = "";
        def msgs = "";
        //查询出内容
        def contentBlockInfoList = ContentBlockInfo.findAllByIdInList(idList);
        if (contentBlockInfoList) {
            contentBlockInfoList.each {
                if (it.delete(flush: true)) {
                    log.info("删除内容块" + it + "成功");
                    msgs += "内容块[" + it.name + "]删除成功<br/>";
                } else {
                    log.info("删除内容块" + it + "失败");
                    errors += "内容块[" + it.name + "]不能删除<br/>";
                }
            }
            return [recode: ReCode.OK, msgs: msgs, errors: errors];
        } else {
            return [recode: ReCode.NO_RESULT];
        }
    }
    //初始化创建contentBlock
    def initContentBlock() {
        def errors = "";
        def msgs = "";
        def codes = ContentBlockCode.codes;
        if (codes) {
            //先删除所有
            ContentBlockInfo.executeUpdate("delete from ContentBlockInfo where 1=1");
            codes.each {
                String name = it.label;
                int code = it.code;
                ContentBlockInfo contentBlockInfo = new ContentBlockInfo();
                contentBlockInfo.code = code;
                contentBlockInfo.name = name;
                if(code==ContentBlockCode.CODE_XFQYGZBJJ.code){
                    contentBlockInfo.inMenu=true;
                }
                if (contentBlockInfo.save(flush: true)) {
                    msgs += "内容块[" + name + "]创建成功<br/>";
                } else {
                    errors += "内容块[" + name + "]创建失败：<br/>" + I18nError.getMessage(g, contentBlockInfo.errors.allErrors);
                }
            }
            return [recode: ReCode.OK, msgs: msgs, errors: errors];
        } else {
            return [recode: ReCode.SYSTEM_ERROR, errors: ReCode.SYSTEM_ERROR.label];
        }
    }
}
