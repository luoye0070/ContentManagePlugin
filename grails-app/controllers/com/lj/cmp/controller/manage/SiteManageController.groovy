package com.lj.cmp.controller.manage

import com.lj.cmp.util.customenum.ReCode
import com.lj.cmp.data.ContentBlockInfo
import com.lj.cmp.data.ContentClassInfo
import com.lj.cmp.data.ContentsInfo
import com.lj.cmp.data.WebSiteInfo

class SiteManageController {
    def webSiteInfoService;
    def contentBlockService;
    def contentsService;
    def index() {
        redirect(action: "editWebSiteInfo");
    }

    def editWebSiteInfo() {
        log.info("params-->" + params);
        def success = null;
        def errors = null;
        def reInfo = webSiteInfoService.getWebSiteInfo();
        WebSiteInfo webSiteInfo = reInfo.webSiteInfo;
        int linkCount=webSiteInfo.linkInfoList.size();
        if (request.method == "POST") {
            try {
                reInfo = webSiteInfoService.saveWebSiteInfo(params);
                log.info("reInfo-->" + reInfo);
                if (reInfo.recode == ReCode.OK) {
                    linkCount=webSiteInfo.linkInfoList.size();
                    success = reInfo.recode.label;
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
        render(view: "/cmp/manage/siteManage/editWebSiteInfo", model: [linkCount:linkCount,webSiteInfoInstance: webSiteInfo, success: success, errors: errors]);
    }

    def contentBlockList(){
        def reInfo=contentBlockService.list();
        log.info("reInfo-->" + reInfo);
        render(view: "/cmp/manage/siteManage/contentBlockList", model: reInfo);
    }
    def editContentBlock(){
        log.info("params-->"+params);
        def success = null;
        def errors = null;
        ContentBlockInfo contentBlockInfo = null;
//        if (params.id) {
            def reInfo = contentBlockService.get([id:params.id]);
            if (reInfo.contentBlockInfo) {
                contentBlockInfo = reInfo.contentBlockInfo;
            }else{
                errors ="没有找到内容块";
                render(view: "/cmp/manage/siteManage/editContentBlock", model: [errors: errors]);
                return;
            }
//        } else {
//            errors ="没有找到内容";
//            render(view: "/cmp/manage/contentsManage/editContents", model: [errors: errors]);
//            return;
//        }

        boolean isSaveOk=false;
        if (request.method == "POST") {
            try {
                reInfo = contentBlockService.save(params);
                if (reInfo.recode == ReCode.OK) {
                    success = reInfo.recode.label;
                    contentBlockInfo = reInfo.contentBlockInfo;
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
        //查询出contentClassList;
        String cciNamesStr="没有类别";
        String cciIdsStr="0";
        if(contentBlockInfo.contentClassInfoList){
            cciNamesStr="";
            cciIdsStr="";
            contentBlockInfo.contentClassInfoList.each {
                cciNamesStr+=it.name+",";
                cciIdsStr+=it.id+","
            }
            cciNamesStr=cciNamesStr.substring(0,cciNamesStr.length()-1);
            cciIdsStr=cciIdsStr.substring(0,cciIdsStr.length()-1);
        }

        String cciTreeStr=contentsService.getContentClassTreeForBui();
        log.info("cciTreeStr-->"+cciTreeStr);
        render(view: "/cmp/manage/siteManage/editContentBlock", model: [cciNamesStr:cciNamesStr,cciIdsStr:cciIdsStr,isSaveOk:isSaveOk,cciTreeStr:cciTreeStr,contentBlockInfoInstance: contentBlockInfo, success: success, errors: errors]);
    }

//    def initWebSite(){
//        log.info("params->"+params);
//        if(params.initCode=="ljgzs2009lzg13123452344546aasdfergrhjeuoj34564545sfdcdf343dfd"){
//            def reInfo = webSiteInfoService.initWebSiteInfo();
//            log.info("reInfo1->"+reInfo);
//            reInfo=contentBlockService.initContentBlock();
//            log.info("reInfo2->"+reInfo);
//            reInfo=contentsService.initContent();
//            log.info("reInfo3->"+reInfo);
//        }
//    }
}
