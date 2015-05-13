package com.lj.cmp.service.front

import com.lj.cmp.util.customenum.ContentBlockCode
import com.lj.cmp.data.ContentBlockInfo
import com.lj.cmp.data.ContentClassInfo
import com.lj.cmp.data.ContentsInfo

import com.lj.cmp.util.common.Number
import com.lj.cmp.util.common.TypeConversion;

class FrontPageService {
    def contentsService;
    def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib();
    //获取菜单信息
    def getMenus() {
        def menuItems=[];
       //从内容块获取需要放到菜单的项
        def contentBlockList=ContentBlockInfo.findAllByInMenu(true);
        if(contentBlockList){
           contentBlockList.each {
               def menuItem;
               if(it.contentsInfo){
                   menuItem=[menuMark:"block"+it.id,name:it.name,url:g.createLink(controller: "frontPage",action: "newsDetail",params: [detailId:it.contentsInfo.id]).toString()];
               }else if(it.contentClassInfoList){
                   def children=[];
                   it.contentClassInfoList.each { child ->
                        children.add([name:child.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:child.id]).toString()]);
                   }
                   menuItem=[menuMark:"block"+it.id,name:it.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [blockId:it.id]).toString(),children:children];
               }else{
                   menuItem=[menuMark:"block"+it.id,name:it.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [blockId:it.id]).toString()];
               }
               menuItems.add(menuItem);
           }
        }
        //从类别中获取
        def contentClassList=ContentClassInfo.findAllByOnMenuAndParentIsNull(true);
        if(contentClassList){
            contentClassList.each {
                def menuItem;
                def contentClassChildren=ContentClassInfo.findAllByOnMenuAndParent(true,it);
                if(contentClassChildren){
                    def children=[];
                    contentClassChildren.each { child ->
                        children.add([name:child.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:child.id]).toString()]);
                    }
                    menuItem=[menuMark:"class"+it.id,name:it.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:it.id]).toString(),children:children];
                }else{
                    menuItem=[menuMark:"class"+it.id,name:it.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:it.id]).toString()];
                }
                menuItems.add(menuItem);
            }
        }
        log.info("menuItems->"+menuItems);
        return menuItems;
    }
    //获取当前页面所在菜单项标示
    def getCurrentMenu(def params){
         if(params.detailId!=null){
             long detailId=Number.toLong(params.detailId);
             ContentsInfo contentsInfo=ContentsInfo.get(detailId);
             if(contentsInfo==null){
                 return "errorOfNoContent";
             }
             //首先查询是否在某个内容块中
             ContentBlockInfo contentBlockInfo=ContentBlockInfo.findByContentsInfo(contentsInfo);
             if(contentBlockInfo){
                 return "block"+contentBlockInfo.id;
             }else{//查找所属类别
                  ContentClassInfo contentClassInfo=contentsInfo.contentClassInfo;
                  if(contentClassInfo){
                      List<ContentClassInfo> classInfoList=getParentList(contentClassInfo);
                      return "class"+classInfoList.get(0).id;
                  }else{
                      return "class0";
                  }
             }
         }
        if(params.blockId!=null){
            return "block"+params.blockId;
        }
        if(params.contentClassId){
            long contentClassId=Number.toLong(params.contentClassId);
            ContentClassInfo contentClassInfo=ContentClassInfo.get(contentClassId);
            if(contentClassInfo){
                List<ContentClassInfo> classInfoList=getParentList(contentClassInfo);
                return "class"+classInfoList.get(0).id;
            }else{
                return "errorOfNoClass";
            }
        }
        //没有参数是首页
        return "home";
    }
    //获取类别列表
    def getParentList(ContentClassInfo contentClassInfo){
        List<ContentClassInfo> classInfoList=new ArrayList<ContentClassInfo>();
        if(contentClassInfo.parent){
            classInfoList.addAll(getParentList(contentClassInfo.parent));
        }
        classInfoList.add(contentClassInfo);
        return classInfoList;
    }

    //获取快速导航菜单列表
    def getNavs(){
        def navItems=[];
        List<ContentBlockInfo> contentBlockList=ContentBlockInfo.findAllByInMenuAndInNav(false,true);
        if(contentBlockList){
            contentBlockList.each {
                def navItem;
                if(it.contentsInfo){
                    navItem=[name:it.name,url:g.createLink(controller: "frontPage",action: "newsDetail",params: [detailId:it.contentsInfo.id]).toString()];
                }else{
                    navItem=[name:it.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [blockId:it.id]).toString()];
                }
                navItems.add(navItem);
            }
        }
        return navItems;
    }

    //首页数据获取
    def getIndexData(){
        def blockMap=[:];
        List<ContentBlockInfo> contentBlockInfoList=ContentBlockInfo.findAll();
        if(contentBlockInfoList){
            contentBlockInfoList.each {
                String url=null;
                //List imageList=null;
                def contentList=null;
                def contentSummaryList=null;

                if(it.contentsInfo){
                    url=g.createLink(controller: "frontPage",action: "newsDetail",params: [detailId:it.contentsInfo.id]).toString();
                    contentList=[it.contentsInfo];
                }else if(it.contentsInfoList){
                    url=g.createLink(controller: "frontPage",action: "newsList",params: [blockId:it.id]).toString();
                    contentList=it.contentsInfoList;
                }else if(it.contentClassInfoList){
                    contentList=ContentsInfo.findAllByContentClassInfoInList(it.contentClassInfoList,[max:it.maxCount,sort:"id",order:"desc"]);
                    url=g.createLink(controller: "frontPage",action: "newsList",params: [blockId:it.id]).toString()
                }
                if(contentList){
                    contentSummaryList=contentList.collect { cinfo ->
                        String title=cinfo.title;
                        Date time=cinfo.time;
                        String detailUrl=g.createLink(controller: "frontPage",action: "newsDetail",params: [detailId:cinfo.id]).toString();
                        String summary=null;
                        if(cinfo.content){
                            String regex="<([^>]*)>";
                            //String regex1="<embed\\s*\\w*/>";
                            summary=cinfo.content.replaceAll(regex,"");
                            summary=summary.replaceAll("\r","").replaceAll("\n","").replaceAll("\t","").replaceAll("&nbsp;","");
                            int iEnd=summary.length();
                            if(iEnd>100){
                                iEnd=100;
                                summary=summary.substring(0,iEnd);
                            }
                        }
                        String imageUrl=null;
                        if(cinfo.imageInfoList){
                            imageUrl=cinfo.imageInfoList.get(0).imgUrl;
                        }else if(cinfo.content){//从content中获取
                            int iBegin=cinfo.content.indexOf("<img");
                            if(iBegin>=0){
                                int iEnd=cinfo.content.indexOf("/>",iBegin);
                                String imgTag=cinfo.content.substring(iBegin,iEnd);
                                iBegin=imgTag.indexOf("src=\"");
                                if(iBegin>=0){
                                    iEnd=imgTag.indexOf("\"",iBegin+5);
                                    imageUrl=imgTag.substring(iBegin+5,iEnd);
                                }
                            }
                        }
                        if(imageUrl){
                            int iBegin=imageUrl.indexOf("uploadFile"+File.separator+"image");
                            if(iBegin>=0){
                                String fileFullName=imageUrl.substring(iBegin+11);
                                int width=355;
                                int height=260;
                                if(it.code==ContentBlockCode.CODE_YWBB.code) {
                                    width=355;
                                    height=260;
                                }
                                if(it.code==ContentBlockCode.CODE_FQXW.code) {
                                    width=144;
                                    height=94;
                                }
                                imageUrl=g.createLink(controller: "imageShow",action: "downloadThumbnail",params: [fileFullName:fileFullName,width:width,height:height]).toString();
                            }
                        }else if(cinfo.content.indexOf("<embed")>=0){
                            imageUrl=g.resource(dir:"images",file:"media.png").toString();
                        }else{
                            imageUrl=g.resource(dir:"images",file:"zwtp.jpg").toString();
                        }
                        [title:title,time:time,detailUrl:detailUrl,summary:summary,imageUrl:imageUrl];
                    }
                }
                def cbiMap=[url:url,contentSummaryList:contentSummaryList,name:it.name];
                blockMap.put("block"+it.code,cbiMap);
            }
        }
        //搜索类别
        List<ContentClassInfo> contentClassInfoList=ContentClassInfo.findAllByParentIsNull();
        def contentClassSummarys=[[name:"全部",value:0]];
        contentClassInfoList.each {
            contentClassSummarys.add([name:it.name,value:it.id])
        }
        blockMap.put("searchClass",contentClassSummarys);
        return blockMap;
    }

    //获取list页面数据
    def getListData(def params){
        //获取参数
        //String title=params.title;
        long contentClassId=Number.toLong(params.contentClassId);
        long blockId=Number.toLong(params.blockId);

        //获取左边导航
        def leftNavItems=[];
        if(blockId){
            ContentBlockInfo contentBlockInfo=ContentBlockInfo.get(blockId);
            if(contentBlockInfo&&contentBlockInfo.contentClassInfoList&&contentBlockInfo.contentClassInfoList.size()>1){
                contentBlockInfo.contentClassInfoList.each { child ->
                    leftNavItems.add([name:child.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:child.id]).toString()]);
                }
            }
        }
        if(contentClassId){
            ContentClassInfo contentClassInfo=ContentClassInfo.get(contentClassId);
            if(contentClassInfo){
                List<ContentClassInfo> contentClassInfoList=ContentClassInfo.findAllByParent(contentClassInfo);
                if(contentClassInfoList){
                    contentClassInfoList.each { child ->
                        leftNavItems.add([name:child.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:child.id]).toString()]);
                    }
                }
            }
        }

        //获取头部路径
        def topNavItems=[];
        List<ContentClassInfo> classInfoList=null;
        if(contentClassId){
            ContentClassInfo contentClassInfo=ContentClassInfo.get(contentClassId);
            if(contentClassInfo){
                classInfoList=getParentList(contentClassInfo);
                classInfoList.remove(contentClassInfo);//移除当前类别
            }
        }
        if(classInfoList){
            classInfoList.each { child ->
                topNavItems.add([name:child.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:child.id]).toString()]);
            }
        }


        //获取内容列表
        def contentsInfoList=null;
        int totalCount=0;
        def contentSummaryList=null;
        if(blockId){
            ContentBlockInfo contentBlockInfo=ContentBlockInfo.get(blockId);
            if(contentBlockInfo.contentsInfoList){
                contentsInfoList=contentBlockInfo.contentsInfoList;
                totalCount=contentsInfoList.size();
            }else{
               def reInfo=contentsService.contentList(params);
                contentsInfoList=reInfo.contentsInfoList;
                totalCount=reInfo.totalCount;
            }
        }else{
            def reInfo=contentsService.contentList(params);
            contentsInfoList=reInfo.contentsInfoList;
            totalCount=reInfo.totalCount;
        }
        if(contentsInfoList){
            contentSummaryList=contentsInfoList.collect { cinfo ->
                String title=cinfo.title;
                Date time=cinfo.time;
                String detailUrl=g.createLink(controller: "frontPage",action: "newsDetail",params: [detailId:cinfo.id]).toString();
                String summary=null;
                if(cinfo.content){
                    String regex="<([^>]*)>";
                    //String regex1="<embed\\s*\\w*/>";
                    summary=cinfo.content.replaceAll(regex,"");
                    int iEnd=summary.length();
                    if(iEnd>200){
                        iEnd=200;
                        summary=summary.substring(0,iEnd);
                    }
                }
                String imageUrl=null;
                if(cinfo.imageInfoList){
                    imageUrl=cinfo.imageInfoList.get(0).imgUrl;
                }else if(cinfo.content){//从content中获取
                    int iBegin=cinfo.content.indexOf("<img");
                    if(iBegin>=0){
                        int iEnd=cinfo.content.indexOf("/>",iBegin);
                        String imgTag=cinfo.content.substring(iBegin,iEnd);
                        iBegin=imgTag.indexOf("src=\"");
                        if(iBegin>=0){
                            iEnd=imgTag.indexOf("\"",iBegin+5);
                            imageUrl=imgTag.substring(iBegin+5,iEnd);
                        }
                    }
                }
                if(imageUrl){
                    int iBegin=imageUrl.indexOf("uploadFile"+File.separator+"image");
                    if(iBegin>=0){
                        String fileFullName=imageUrl.substring(iBegin);
                        int width=220;
                        int height=120;
                        imageUrl=g.createLink(controller: "imageShow",action: "downloadThumbnail",params: [fileFullName:fileFullName,width:width,height:height]).toString();
                    }
                }else if(cinfo.content.indexOf("<embed")>=0){
                    imageUrl=g.resource(dir:"images",file:"media.png").toString();
                }else{
                    imageUrl=g.resource(dir:"images",file:"zwtp.jpg").toString();
                }
                [title:title,time:time,detailUrl:detailUrl,summary:summary,imageUrl:imageUrl];
            }
        }

        //取当前block信息
        ContentBlockInfo contentBlockInfo=null;
        if(blockId){
            contentBlockInfo=ContentBlockInfo.get(blockId);
        }
        ContentClassInfo contentClassInfo=null;
        if(contentClassId){
            contentClassInfo=ContentClassInfo.get(contentClassId);
        }
        return [leftNavItems:leftNavItems,classInfoList:classInfoList,topNavItems:topNavItems,contentsInfoList:contentsInfoList,
                contentSummaryList:contentSummaryList,totalCount:totalCount,contentBlockInfo:contentBlockInfo,contentClassInfo:contentClassInfo];
    }

    //获取detail页面数据
    def getDetailData(def params){
        long detailId=Number.toLong(params.detailId);
        //获取内容详情
        ContentsInfo contentsInfo=ContentsInfo.get(detailId);

        //获取头部路径
        def topNavItems=[];
        List<ContentClassInfo> classInfoList=null;
        if(contentsInfo){
            ContentClassInfo contentClassInfo=contentsInfo.contentClassInfo;
            if(contentClassInfo){
                classInfoList=getParentList(contentClassInfo);
                //classInfoList.remove(contentClassInfo);//移除当前类别
            }
            if(classInfoList){
                classInfoList.each { child ->
                    topNavItems.add([name:child.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:child.id]).toString()]);
                }
            }
        }

        //获取左边导航
        def leftNavItems=[];
        if(classInfoList){
            ContentClassInfo contentClassInfo=classInfoList.get(0);
            leftNavItems.add([name:contentClassInfo.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:contentClassInfo.id]).toString()]);
            if(contentClassInfo){
                List<ContentClassInfo> contentClassInfoList=ContentClassInfo.findAllByParent(contentClassInfo);
                if(contentClassInfoList){
                    contentClassInfoList.each { child ->
                        leftNavItems.add([name:child.name,url:g.createLink(controller: "frontPage",action: "newsList",params: [contentClassId:child.id]).toString()]);
                    }
                }
            }
        }

        return [leftNavItems:leftNavItems,topNavItems:topNavItems,contentsInfo:contentsInfo];
    }
}
