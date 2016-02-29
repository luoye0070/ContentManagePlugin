package com.lj.cmp.service.content

import com.lj.cmp.util.common.I18nError
import com.lj.cmp.util.customenum.ContentBlockCode
import com.lj.cmp.util.customenum.ReCode
import com.lj.cmp.data.ContentBlockInfo
import com.lj.cmp.data.ContentClassInfo
import com.lj.cmp.data.ContentsInfo
import com.lj.cmp.data.ImageInfo

import java.text.SimpleDateFormat
import com.lj.cmp.util.common.Number
import com.lj.cmp.util.common.TypeConversion;

class ContentsService {
    def webUtilService;

    def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib();

    //获取类别id列表
    private List<Long> getContentClassIds(List<Long> classIds) {
        List<Long> childrenIds = new ArrayList<Long>();
        if (classIds) {
            classIds.each {
                def classInfos = ContentClassInfo.findAllByParent(ContentClassInfo.get(it));
                if (classInfos) {
                    classInfos.each {
                        childrenIds.add(it.id);
                    }
                }
            }
        }
        if (childrenIds) {
            List<Long> grandchildrenIds = getContentClassIds(childrenIds);
            classIds.addAll(grandchildrenIds);
        }
        return classIds;
    }
    /**************************内容操作相关方法**************************/
    //保存内容
    def contentSave(def params) {
        def request = webUtilService.getRequest();
        //获取参数
        long id = Number.toLong(params.id);
        String title = params.title;
        String author = params.author;
        String content = params.content;
        long contentClassId = Number.toLong(params.contentClassId);
        ContentClassInfo contentClassInfo = ContentClassInfo.get(contentClassId);

        def imageNumList = [];
        if (params.imageNums instanceof String) { //传入id
            //获取参数
            long imageNum = Number.toLong(params.imageNums);//图片信息ID
            imageNumList.add(imageNum);
        } else if (params.imageNums instanceof String[]) {//传入id数组
            for (int i = 0; i < params.imageNums.length; i++) {
                long imageNum = Number.toLong(params.imageNums[i]);
                imageNumList.add(imageNum);
            }
        }

        ContentsInfo contentsInfo = ContentsInfo.get(id);
        if (contentsInfo == null) {
            contentsInfo = new ContentsInfo();
        }

        //保存图片信息
        if (!imageNumList.isEmpty()) {
            imageNumList.each {
                long imageId = Number.toLong(params.get("imageId" + it));
                String name = params.get("name" + it);
                String imgUrl = params.get("imgUrl" + it);
                String decription = params.get("decription" + it);
                ImageInfo imageInfo = ImageInfo.get(imageId);
                boolean isNew = false;
                if (imageInfo == null) {
                    imageInfo = new ImageInfo();
                    isNew = true;
                }
                imageInfo.name = name;
                imageInfo.imgUrl = imgUrl;
                imageInfo.decription = decription;
                if (!imageInfo.save(flush: true)) {
                    throw new RuntimeException(I18nError.getMessage(g, imageInfo.errors.allErrors));
                }
                if (isNew) {
                    contentsInfo.imageInfoList.add(imageInfo);
                }
            }
        }
        //删除已有的图片信息
        List<Long> deleteIiIds = new ArrayList<Long>();
        if (params.deleteImageInfoIds && contentsInfo.imageInfoList) {
            String deleteImageInfoIds = params.deleteImageInfoIds;
            String[] deleteImageInfoIdsArray = deleteImageInfoIds.split(",");
            deleteImageInfoIdsArray.each {
                long imageId = Number.toLong(it);
                ImageInfo imageInfo = ImageInfo.get(imageId);
                if (imageInfo) {
                    int size = contentsInfo.imageInfoList.size();
                    for (int i = 0; i < size; i++) {
                        ImageInfo imageInfoTemp = contentsInfo.imageInfoList.get(i);
                        if (imageInfoTemp.id == imageInfo.id) {
                            contentsInfo.imageInfoList.remove(i);
                            deleteIiIds.add(imageInfo.id);
                            break;
                        }
                    }
                }
            }
        }

        contentsInfo.title = title;
        contentsInfo.author = author;
        contentsInfo.content = content;
        contentsInfo.contentClassInfo = contentClassInfo;

        if (!contentsInfo.save(flush: true)) {
            throw new RuntimeException(I18nError.getMessage(g, contentsInfo.errors.allErrors));
            //return [recode: ReCode.SAVE_FAILED, contentsInfo: contentsInfo, errors: I18nError.getMessage(g, contentsInfo.errors.allErrors)]
        }
        contentsInfo.refresh();

        //删除imageinfo
        int size = deleteIiIds.size();
        for (int i = 0; i < size; i++) {
            ImageInfo imageInfo = ImageInfo.get(deleteIiIds.get(i));
            if (imageInfo) {
                imageInfo.delete(flush: true);
            }
        }
        return [recode: ReCode.OK, contentsInfo: contentsInfo];
    }
    //内容列表
    def contentList(def params) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //获取参数
        long id = Number.toLong(params.id);
        String title = params.title;
        String author = params.author;
        String content = params.content;
        long contentClassId = Number.toLong(params.contentClassId);
        long blockId=Number.toLong(params.blockId);

        //根据类别获取所有子类别
        List<Long> classIds = null;
        if (contentClassId) {
            classIds = getContentClassIds([contentClassId]);
        }
        ContentBlockInfo contentBlockInfo=ContentBlockInfo.get(blockId);
        if(contentBlockInfo) {
            if(contentBlockInfo.contentClassInfoList){
                classIds=new ArrayList<Long>();
                contentBlockInfo.contentClassInfoList.each {
                    List<Long> classIdsTemp =getContentClassIds([it.id]);
                    classIds.addAll(classIdsTemp);
                }
            }
        }

        String beginTimeStr = params.beginTime;//开始时间
        Date beginTime = null;
        try { beginTime = sdfDate.parse(beginTimeStr); } catch (Exception ex) {}
        String endTimeStr = params.endTime;//截止时间
        Date endTime = null;
        try { endTime = sdfDate.parse(endTimeStr); } catch (Exception ex) {}

        if (!params.max) {
            params.max = 10
        }
        if (!params.offset) {
            params.offset = 0;
        }
        if(!params.sort){
            params.sort="id";
            params.order="desc";
        }

        def condition = {
            if (id) {
                eq("id", id);
            }
            if (title) {
                like("title", "%" + title + "%");
            }
            if (author) {
                like("author", "%" + author + "%");
            }
            if (content) {
                like("title", "%" + content + "%");
            }
            if (classIds) {
                inList("contentClassInfo.id", classIds);
            }
            if (beginTime) {
                ge("time", beginTime);//日期条件
            }
            if (endTime) {
                le("time", endTime);//日期条件
            }
        }

        def contentsInfoList = ContentsInfo.createCriteria().list(params, condition);
        def totalCount = ContentsInfo.createCriteria().count(condition);

        return [recode: ReCode.OK, contentsInfoList: contentsInfoList, totalCount: totalCount];

    }
    //删除内容
    def deleteContents() {
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
        def contentsInfoList = ContentsInfo.findAllByIdInList(idList);
        if (contentsInfoList) {
            contentsInfoList.each {
                if (it.canRemove) {
                    it.delete(flush: true);
                    log.info("删除内容" + it + "成功");
                    msgs += "内容[" + it.title + "]删除成功<br/>";
                } else {
                    log.info("删除内容" + it + "失败");
                    errors += "内容[" + it.title + "]不能删除<br/>";
                }
            }
            return [recode: ReCode.OK, msgs: msgs, errors: errors];
        } else {
            return [recode: ReCode.NO_RESULT];
        }
    }

    /******************************内容类别相关操作方法***********************************/
    //保存内容类别
    def contentClassSave(def params) {
        def request = webUtilService.getRequest();
        //获取参数
        long id = Number.toLong(params.id);
        String name = params.name;
        String description = params.description;
        long parentId = Number.toLong(params.parentId);
        boolean onMenu = TypeConversion.toBoolean(params.onMenu);
        ContentClassInfo parentContentClassInfo = ContentClassInfo.get(parentId);

        ContentClassInfo contentClassInfo = ContentClassInfo.get(id);
        if (contentClassInfo == null) {
            contentClassInfo = new ContentClassInfo();
        }

        contentClassInfo.name = name;
        contentClassInfo.description = description;
        contentClassInfo.parent = parentContentClassInfo;
        if (params.onMenu != null) {
            contentClassInfo.onMenu = onMenu;
        }

        if (!contentClassInfo.save(flush: true)) {
            return [recode: ReCode.SAVE_FAILED, contentClassInfo: contentClassInfo, errors: I18nError.getMessage(g, contentClassInfo.errors.allErrors)]
        }
        contentClassInfo.refresh();
        return [recode: ReCode.OK, contentClassInfo: contentClassInfo];
    }

    //内容类别列表
    def contentClassList(def params) {
        //获取参数
        long id = Number.toLong(params.id);
        String name = params.name;
        String description = params.description;
        long parentId = Number.toLong(params.parentId);
        boolean onMenu = TypeConversion.toBoolean(params.onMenu);
        boolean findAll = TypeConversion.toBoolean(params.findAll);

        if (findAll) {
            def contentClassInfoList = ContentClassInfo.getAll();
            def totalCount = 0;
            if (contentClassInfoList) {
                totalCount = contentClassInfoList.size();
            }
            return [recode: ReCode.OK, contentClassInfoList: contentClassInfoList, totalCount: totalCount];
        } else {
            if (!params.max) {
                params.max = 10
            }
            if (!params.offset) {
                params.offset = 0;
            }

            def condition = {
                if (id) {
                    eq("id", id);
                }
                if (name) {
                    like("name", "%" + name + "%");
                }
                if (description) {
                    like("description", "%" + description + "%");
                }
                if (parentId) {
                    eq("parent.id", parentId);
                }
                if (params.onMenu != null) {
                    eq("onMenu", onMenu);
                }
            }

            def contentClassInfoList = ContentClassInfo.createCriteria().list(params, condition);
            def totalCount = ContentClassInfo.createCriteria().count(condition);

            return [recode: ReCode.OK, contentClassInfoList: contentClassInfoList, totalCount: totalCount];
        }
    }

    def contentClassListWithoutCurrent(Long currentId) {
        def contentClassList = null;
        if (currentId) {
            List<Long> classIds = getContentClassIds([currentId]);
            contentClassList = ContentClassInfo.findAllByIdNotInList(classIds);
        } else {
            contentClassList = ContentClassInfo.list();
        }
        return [recode: ReCode.OK, contentClassList: contentClassList];
    }
    //删除内容类别
    def deleteContentClass() {
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

        //查询出内容类别
        def contentClassInfoList = ContentClassInfo.findAllByIdInList(idList);
        def errors = "";
        def msgs = "";
        boolean hasError = false;
        if (contentClassInfoList) {
            contentClassInfoList.each {
                if (it.canRemove) {
                    ContentClassInfo child = ContentClassInfo.findByParent(it);
                    if (child) {
                        hasError = true;
                        log.info("删除内容类别[" + it + "]有子类，所以不能删除");
                        errors += "内容类别[" + it.name + "]有子类，不能删除<br/>";
                    } else {
                        //检测该内部下是否是否有内容
                        ContentsInfo contentsInfo=ContentsInfo.findByContentClassInfo(it);
                        if(contentsInfo){
                            hasError = true;
                            log.info("删除内容类别[" + it + "]有内容，所以不能删除");
                            errors += "删除属于该类别的所有内容后，才可以删除该类别<br/>";
                        }else{
                            it.delete(flush: true);
                            log.info("删除内容类别[" + it + "]成功");
                            msgs += "内容类别[" + it.name + "]删除成功<br/>";
                        }
                    }
                } else {
                    hasError = true;
                    log.info("删除内容类别[" + it + "]设置为不能删除，所以不能删除");
                    errors += "内容类别[" + it.name + "]不能删除<br/>";
                }
            }
            return [recode: ReCode.OK, errors: errors, msgs: msgs];
        } else {
            return [recode: ReCode.NO_RESULT];
        }
    }
    //获取内容类别树结构
    String getContentClassTreeForBui(ContentClassInfo parent) {
        String treeStr = null;
        def cciList = null;
        if (parent == null) {
            cciList = ContentClassInfo.findAllByParentIsNull();
        } else {
            cciList = ContentClassInfo.findAllByParent(parent);
        }
        if (cciList) {
            treeStr = "";
            cciList.each {
                String cciStr = "";
                String childrenStr = getContentClassTreeForBui(it);
                if (childrenStr) {
                    cciStr = "{text : '" + it.name + "',id : '" + it.id + "',children: [" + childrenStr + "]}";
                } else {
                    cciStr = "{text : '" + it.name + "',id : '" + it.id + "'}";
                }
                treeStr += cciStr + ",";
            }
            treeStr = treeStr.substring(treeStr.length() - 1);
        }
        return treeStr;
    }

    String getContentClassTreeForBui(List<ContentClassInfo> cciList) {
        String treeStr = null;
        if (cciList) {
            treeStr = "";
            cciList.each {
                String cciStr = "";
                def cciChildrenList = ContentClassInfo.findAllByParent(it);
                String childrenStr = getContentClassTreeForBui(cciChildrenList);
                if (childrenStr) {
                    cciStr = "{text : '" + it.name + "',id : '" + it.id + "',children: [" + childrenStr + "]}";
                } else {
                    cciStr = "{text : '" + it.name + "',id : '" + it.id + "'}";
                }
                treeStr += cciStr + ",";
            }
            treeStr = treeStr.substring(0, treeStr.length() - 1);
        }
        return treeStr;
    }

    String getContentClassTreeForBui() {
        def cciList = ContentClassInfo.findAllByParentIsNull();
        String treeStr = "[{text : '没有类别',id : '0'}";
        String childrenStr = getContentClassTreeForBui(cciList);
        if (childrenStr) {
            treeStr += "," + childrenStr;
        }
        treeStr += "]";
        return treeStr;
    }

    //初始化
    def initContent() {
        def errors = "";
        def msgs = "";
        //删除所有类别
        ContentClassInfo.executeUpdate("delete from ContentClassInfo where 1=1");
        def classMapList=[
                [name:"天龙八部",onMenu:true,canRemove:true,children:[
                        [name:"幸福关怀智囊部",onMenu:true,canRemove:true],
                        [name:"幸福文化传承部",onMenu:true,canRemove:true],
                        [name:"幸福标兵指挥部",onMenu:true,canRemove:true],
                        [name:"幸福宣教电视台",onMenu:true,canRemove:true],
                        [name:"幸福公益奉献部",onMenu:true,canRemove:true],
                        [name:"幸福活动俱乐部",onMenu:true,canRemove:true],
                        [name:"幸福之家管理部",onMenu:true,canRemove:true],
                        [name:"幸福志工档案部",onMenu:true,canRemove:true]
                ]],
                [name: "一线天地",onMenu:true,canRemove:true,children:[
                        [name:"活动动态",onMenu:true,canRemove:true],
                        [name:"新闻公告",onMenu:true,canRemove:true]
                ]],
                [name: "志工部落",onMenu:true,canRemove:true],
                [name: "英雄榜",onMenu:true,canRemove:true],
                [name: "滚动新闻",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_GDXW.code],
                [name: "要闻播报",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_YWBB.code],
                [name: "热点关注",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_RDGZ.code],
                [name: "部务公开",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_BWGK.code],
                [name: "活动锦集",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_HDJJ.code],
                [name: "文化传承",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_WHCC.code],
                [name: "国内新闻",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_GNXW.code],
                [name: "福企新闻",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_FQXW.code],
                [name: "资讯速递",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_ZXSD.code],
                [name: "专题频道",onMenu:false,canRemove:false,blockCode:ContentBlockCode.CODE_ZTPD.code]
        ];
        //创建类别
        classMapList.each {
            ContentClassInfo contentClassInfo=new ContentClassInfo();
            contentClassInfo.name=it.name;
            contentClassInfo.onMenu=it.onMenu;
            contentClassInfo.canRemove=it.canRemove;
            contentClassInfo.save(flush: true);
            if(it.children){
                it.children.each { child->
                    ContentClassInfo contentClassInfo1=new ContentClassInfo();
                    contentClassInfo1.name=child.name;
                    contentClassInfo1.onMenu=child.onMenu;
                    contentClassInfo1.canRemove=child.canRemove;
                    contentClassInfo1.parent=contentClassInfo;
                    contentClassInfo1.save(flush: true);
                }
            }
            if(it.blockCode!=null){
                ContentBlockInfo contentBlockInfo=ContentBlockInfo.findByCode(it.blockCode);
                if(contentBlockInfo){
                    contentBlockInfo.contentClassInfoList=new ArrayList<ContentClassInfo>();
                    contentBlockInfo.contentClassInfoList.add(contentClassInfo);
                    contentBlockInfo.save(flush:true);
                }
            }
        }

        //删除所有内容
        ContentsInfo.executeUpdate("delete from ContentsInfo where 1=1");
        //创建内容
        def contentsMapList=[
                [title:"幸福企业工作部简介",author:"system",content:"幸福企业工作部简介",canRemove:false,blockCode:ContentBlockCode.CODE_XFQYGZBJJ.code],
//                [title:"幸福企业工作部三步走",author:"system",content:"幸福企业工作部三步走",canRemove:false,blockCode:ContentBlockCode.CODE_TP.code],
                [title:"通讯录",author:"system",
                        content:"<table bordercolor=\"#337fe5\" border=\"1\"><thead><tr><td>联系人</td><td>电话</td></tr></thead><tbody>" +
                                "<tr><td>某某</td><td>1232455</td></tr></tbody></table>",canRemove:false,
                        blockCode:ContentBlockCode.CODE_TXL.code]
        ];
        contentsMapList.each {
            ContentsInfo contentsInfo=new ContentsInfo();
            contentsInfo.title=it.title;
            contentsInfo.author=it.author;
            contentsInfo.content=it.content;
            contentsInfo.canRemove=it.canRemove;
            contentsInfo.save(flush: true);
            if(it.blockCode!=null){
                ContentBlockInfo contentBlockInfo=ContentBlockInfo.findByCode(it.blockCode);
                if(contentBlockInfo){
                    contentBlockInfo.contentsInfo=contentsInfo;
                    contentBlockInfo.save(flush:true);
                }
            }
        }

        if(true){
            return [recode: ReCode.OK, msgs: msgs, errors: errors];
        } else {
            return [recode: ReCode.SYSTEM_ERROR, errors: ReCode.SYSTEM_ERROR.label];
        }
    }
}
