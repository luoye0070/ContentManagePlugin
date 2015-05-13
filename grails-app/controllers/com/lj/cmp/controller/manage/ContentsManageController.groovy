package com.lj.cmp.controller.manage

import com.lj.cmp.util.customenum.ReCode
import com.lj.cmp.data.ContentClassInfo
import com.lj.cmp.data.ContentsInfo

import com.lj.cmp.util.common.Number
import com.lj.cmp.util.common.TypeConversion;

class ContentsManageController {
    def contentsService;
    /**************************内容类别管理************************/
    def editContentClass() {
        def success = null;
        def errors = null;
        ContentClassInfo contentClassInfo = null;
        if (params.id) {
            def reInfo = contentsService.contentClassList([id:params.id]);
            if (reInfo.contentClassInfoList) {
                contentClassInfo = reInfo.contentClassInfoList.get(0);
            }else{
                errors ="没有找到内容类别";
                render(view: "/cmp/manage/contentsManage/editContentClass", model: [errors: errors]);
                return;
            }
        } else {
            contentClassInfo = new ContentClassInfo();
        }
        boolean isSaveOk=false;
        if (request.method == "POST") {
            def reInfo = contentsService.contentClassSave(params);
            if (reInfo.recode == ReCode.OK) {
                success = reInfo.recode.label;
                contentClassInfo = reInfo.contentClassInfo;
                isSaveOk=true;
            } else {
                if (reInfo.recode == ReCode.SAVE_FAILED) {
                    errors = reInfo.errors;
                } else {
                    errors = reInfo.recode.label;
                }
            }
        }
        def contentClassListWithoutCurrent = contentsService.contentClassListWithoutCurrent(contentClassInfo?.id).contentClassList;
        render(view: "/cmp/manage/contentsManage/editContentClass", model: [isSaveOk:isSaveOk,contentClassListWithoutCurrent: contentClassListWithoutCurrent, contentClassInfoInstance: contentClassInfo, success: success, errors: errors]);
    }

    def contentClassList() {
        def reInfo = contentsService.contentClassList(params);
        log.info("reInfo-->" + reInfo);
        render(view: "/cmp/manage/contentsManage/contentClassList", model: reInfo);
    }

    def contentClassDelete() {
        log.info("params->" + params);
        def reInfo = contentsService.deleteContentClass();
        if (reInfo.recode == ReCode.OK) {
            flash.success = reInfo.msgs;
            flash.errors = reInfo.errors;
        } else {
            flash.errors = reInfo.recode.label;
        }
        params.remove("ids");
        redirect(action: "contentClassList", params: params);
    }

    /********************************内容管理********************************/
    def editContents() {
        log.info("params-->"+params);
        def success = null;
        def errors = null;
        ContentsInfo contentsInfo = null;
        if (params.id) {
            def reInfo = contentsService.contentList([id:params.id]);
            if (reInfo.contentsInfoList) {
                contentsInfo = reInfo.contentsInfoList.get(0);
            }else{
                errors ="没有找到内容";
                render(view: "/cmp/manage/contentsManage/editContents", model: [errors: errors]);
                return;
            }
        } else {
            //contentsInfo = new ContentsInfo();
            contentsInfo = new ContentsInfo(params);
            long contentClassId = Number.toLong(params.contentClassId);
            contentsInfo.contentClassInfo = ContentClassInfo.get(contentClassId);
        }
        int imageCount=contentsInfo.imageInfoList.size();
        boolean isSaveOk=false;
        if (request.method == "POST") {
            try {
                def reInfo = contentsService.contentSave(params);
                if (reInfo.recode == ReCode.OK) {
                    success = reInfo.recode.label;
                    contentsInfo = reInfo.contentsInfo;
                    imageCount=contentsInfo.imageInfoList.size();
                    isSaveOk=true;
                } else {
                    if (reInfo.recode == ReCode.SAVE_FAILED) {
                        errors = reInfo.errors;
                    } else {
                        errors = reInfo.recode.label;
                    }
                }
            } catch (Exception ex) {
                errors = ex.message;
            }
        }
        String cciTreeStr=contentsService.getContentClassTreeForBui();
        log.info("cciTreeStr-->"+cciTreeStr);
        render(view: "/cmp/manage/contentsManage/editContents", model: [isSaveOk:isSaveOk,cciTreeStr:cciTreeStr,imageCount:imageCount,contentsInfoInstance: contentsInfo, success: success, errors: errors]);
    }

    def contentsList() {
        log.info("params-->"+params);
        def reInfo = contentsService.contentList(params);
        log.info("reInfo-->" + reInfo);
        String cciTreeStr=contentsService.getContentClassTreeForBui();
        log.info("cciTreeStr-->"+cciTreeStr);
        reInfo<<[cciTreeStr:cciTreeStr];
        render(view: "/cmp/manage/contentsManage/contentsList", model: reInfo);
    }

    def contentsDelete() {
        log.info("params->" + params);
        def reInfo = contentsService.deleteContents();
        if (reInfo.recode == ReCode.OK) {
            //flash.success = reInfo.recode.label;
            flash.success = reInfo.msgs;
            flash.errors = reInfo.errors;
        } else {
            flash.errors = reInfo.recode.label;
        }
        params.remove("ids");
        redirect(action: "contentsList", params: params);
    }
    def contentsShow() {
        def success = null;
        def errors = null;
        ContentsInfo contentsInfo = null;
        def reInfo = contentsService.contentList([id:params.id]);
        log.info("reInfo-->" + reInfo);
        if (reInfo.contentsInfoList) {
            contentsInfo = reInfo.contentsInfoList.get(0);
        }else{
            errors ="没有找到内容";
            render(view: "/cmp/manage/contentsManage/contentsShow", model: [errors: errors]);
            return;
        }
        render(view: "/cmp/manage/contentsManage/contentsShow", model: [contentsInfoInstance: contentsInfo, success: success, errors: errors]);
    }


    def contentsListForSelect() {
        log.info("params-->"+params);
        def reInfo = contentsService.contentList(params);
        log.info("reInfo-->" + reInfo);
        String cciTreeStr=contentsService.getContentClassTreeForBui();
        log.info("cciTreeStr-->"+cciTreeStr);
        reInfo<<[cciTreeStr:cciTreeStr];
        render(view: "/cmp/manage/contentsManage/contentsListForSelect", model: reInfo);
    }

}
