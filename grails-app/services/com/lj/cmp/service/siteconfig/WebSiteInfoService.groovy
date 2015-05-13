package com.lj.cmp.service.siteconfig

import com.lj.cmp.util.common.I18nError
import com.lj.cmp.util.customenum.ReCode
import com.lj.cmp.util.customenum.ResourceType
import com.lj.cmp.data.LinkInfo
import com.lj.cmp.data.ResourceFileInfo
import com.lj.cmp.data.WebSiteInfo

import com.lj.cmp.util.common.Number
import com.lj.cmp.util.common.TypeConversion;

class WebSiteInfoService {
    def webUtilService;
    def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib();

    def getWebSiteInfo() {
        WebSiteInfo webSiteInfo = WebSiteInfo.first();
        if (webSiteInfo == null) {
            webSiteInfo = new WebSiteInfo();//没有则创建一个
            webSiteInfo.siteName = "中国人保伊犁分公司幸福企业工作部";//网站名称
            webSiteInfo.logoUrl = "";
            webSiteInfo.flashUrl = ""; //首页Flash地址
            webSiteInfo.imageUrl = ""; //首页图片地址
            webSiteInfo.maxSizeOfFile = 1024 * 1024 * 100;//上传资源文件最大字节数
            if (webSiteInfo.save(flush: true)) {
                webSiteInfo.refresh();
            }
        }
        return [recode: ReCode.OK, webSiteInfo: webSiteInfo];
    }

    def saveWebSiteInfo(def params) {
        //上传资源文件最大字节数单位换算
        params.maxSizeOfFile = Number.toLong(params.maxSizeOfFile) * 1024*1024;

        WebSiteInfo webSiteInfo = WebSiteInfo.first();
        if (webSiteInfo == null) {
            webSiteInfo = new WebSiteInfo();//没有则创建一个
        }

        def linkNumList = [];
        if (params.linkNums instanceof String) { //传入id
            //获取参数
            long linkNum = Number.toLong(params.linkNums);//图片信息ID
            linkNumList.add(linkNum);
        } else if (params.linkNums instanceof String[]) {//传入id数组
            for (int i = 0; i < params.linkNums.length; i++) {
                long linkNum = Number.toLong(params.linkNums[i]);
                linkNumList.add(linkNum);
            }
        }
        //保存图片信息
        if (!linkNumList.isEmpty()) {
            linkNumList.each {
                long linkId = Number.toLong(params.get("linkId" + it));
                String name = params.get("name" + it);
                String url = params.get("url" + it);
                LinkInfo linkInfo = LinkInfo.get(linkId);
                boolean isNew = false;
                if (linkInfo == null) {
                    linkInfo = new LinkInfo();
                    isNew = true;
                }
                linkInfo.name = name;
                linkInfo.url = url;
                if (!linkInfo.save(flush: true)) {
                    throw new RuntimeException(I18nError.getMessage(g, linkInfo.errors.allErrors));
                }
                if (isNew) {
                    webSiteInfo.linkInfoList.add(linkInfo);
                }
            }
        }
        //删除已有的图片信息
        List<Long> deleteIiIds = new ArrayList<Long>();
        if (params.deleteLinkInfoIds && webSiteInfo.linkInfoList) {
            String deleteLinkInfoIds = params.deleteLinkInfoIds;
            String[] deleteLinkInfoIdsArray = deleteLinkInfoIds.split(",");
            deleteLinkInfoIdsArray.each {
                long linkId = Number.toLong(it);
                LinkInfo linkInfo = LinkInfo.get(linkId);
                if (linkInfo) {
                    int size = webSiteInfo.linkInfoList.size();
                    for (int i = 0; i < size; i++) {
                        LinkInfo linkInfoTemp = webSiteInfo.linkInfoList.get(i);
                        if (linkInfoTemp.id == linkInfo.id) {
                            webSiteInfo.linkInfoList.remove(i);
                            deleteIiIds.add(linkInfo.id);
                            break;
                        }
                    }
                }
            }
        }

        webSiteInfo.siteName = params.siteName;
        webSiteInfo.logoUrl = params.logoUrl;
        webSiteInfo.flashUrl = params.flashUrl;
        webSiteInfo.imageUrl = params.imageUrl;
        webSiteInfo.maxSizeOfFile=Number.toLong(params.maxSizeOfFile);
        webSiteInfo.rightInfo=params.rightInfo;
        webSiteInfo.mailAddr=params.mailAddr;



        //webSiteInfo.setProperties(params);
        if (!webSiteInfo.save(flush: true)) {
            throw new RuntimeException(I18nError.getMessage(g, webSiteInfo.errors.allErrors));
            //return [recode: ReCode.SAVE_FAILED, webSiteInfo: webSiteInfo, errors: I18nError.getMessage(g, webSiteInfo.errors.allErrors)]
        }
        webSiteInfo.refresh();
        return [recode: ReCode.OK, webSiteInfo: webSiteInfo]; ;
    }

    def initWebSiteInfo(def context){
        //创建资源
        ResourceFileInfo.executeUpdate("delete from ResourceFileInfo where fileFullName='image"+File.separator+"14286775519761428677551907QQ20150410225147.png'");
        ResourceFileInfo resourceFileInfo=new ResourceFileInfo();
        resourceFileInfo.dirName="image";
        resourceFileInfo.fileName="QQ20150410225147.png";
        resourceFileInfo.fileFullName="image"+File.separator+"14286775519761428677551907QQ20150410225147.png";
        resourceFileInfo.type=ResourceType.IMAGE.code;
        resourceFileInfo.size=26274;
        resourceFileInfo.save(flush: true);

        ResourceFileInfo.executeUpdate("delete from ResourceFileInfo where fileFullName='image"+File.separator+"14284227492881428422749136tp.png'");
        ResourceFileInfo resourceFileInfo1=new ResourceFileInfo();
        resourceFileInfo1.dirName="image";
        resourceFileInfo1.fileName="tp.png";
        resourceFileInfo1.fileFullName="image"+File.separator+"14284227492881428422749136tp.png";
        resourceFileInfo1.type=ResourceType.IMAGE.code;
        resourceFileInfo1.size=91816;
        resourceFileInfo1.save(flush: true);

        ResourceFileInfo.executeUpdate("delete from ResourceFileInfo where fileFullName='flash"+File.separator+"1428674529069142867452899814283214786271428321478505flash2077.swf'");
        ResourceFileInfo resourceFileInfo2=new ResourceFileInfo();
        resourceFileInfo2.dirName="flash";
        resourceFileInfo2.fileName="14283214786271428321478505flash2077.swf";
        resourceFileInfo2.fileFullName="flash"+File.separator+"1428674529069142867452899814283214786271428321478505flash2077.swf";
        resourceFileInfo2.type=ResourceType.FLASH.code;
        resourceFileInfo2.size=119808;
        resourceFileInfo2.save(flush: true);

        //创建网站信息
        WebSiteInfo.executeUpdate("delete from WebSiteInfo where 1=1");//先删除所有
        WebSiteInfo webSiteInfo = new WebSiteInfo();//没有则创建一个
        webSiteInfo.siteName = "中国人保伊犁分公司幸福企业工作部";//网站名称
        webSiteInfo.logoUrl = context.getContextPath()+""+File.separator+"uploadFile"+File.separator+"image"+File.separator+"14286775519761428677551907QQ20150410225147.png";
        webSiteInfo.flashUrl = context.getContextPath()+""+File.separator+"uploadFile"+File.separator+"flash"+File.separator+"1428674529069142867452899814283214786271428321478505flash2077.swf"; //首页Flash地址
        webSiteInfo.imageUrl = context.getContextPath()+""+File.separator+"uploadFile"+File.separator+"image"+File.separator+"14284227492881428422749136tp.png"; //首页图片地址
        webSiteInfo.maxSizeOfFile = 1024 * 1024 * 100;//上传资源文件最大字节数
        if (webSiteInfo.save(flush: true)) {
            webSiteInfo.refresh();
        }


    }
}
