package com.lj.cmp.controller.manage

import com.lj.cmp.util.common.FormatUtil
import com.lj.cmp.util.customenum.FileExts
import grails.converters.JSON
import com.lj.cmp.util.customenum.ReCode
import com.lj.cmp.util.customenum.ResourceType
import com.lj.cmp.data.ResourceFileInfo
import com.lj.cmp.data.WebSiteInfo
import org.json.simple.JSONObject

import java.text.SimpleDateFormat

class ResourceFileController {
    def resourceFilesService;
    def webSiteInfoService;
    def index() {
        redirect(action: "list");
    }
    //资源文件列表
    def list() {
        def reInfo = resourceFilesService.search(params);
//        reInfo<<[errors:errors,warnings:"warnings"]
        log.info("reInfo-->"+reInfo);
        render(view: "/cmp/manage/resourceFile/list", model: reInfo);
    }
    //预览flash文件
    def previewFlash(){
        def errors=null;
        def reInfo = resourceFilesService.search(params);
        log.info("reInfo-->"+reInfo);
        String urlStr=null;
        boolean isSwf=false;
        boolean isMp3=false;
        if(reInfo.resourceFileInfoList!=null&&reInfo.resourceFileInfoList.size()>0){
            ResourceFileInfo resourceFileInfo=reInfo.resourceFileInfoList.get(0);
            urlStr=request.getContextPath()+"/uploadFile/"+resourceFileInfo.fileFullName;
            //确定flash类型
            String fileExt = resourceFileInfo.fileFullName.substring(resourceFileInfo.fileFullName.lastIndexOf(".") + 1).toLowerCase();
            if("swf".equals(fileExt)){
                isSwf=true;
            }
            if("mp3".equals(fileExt)){
                isMp3=true;
            }
        } else{
            errors="没有找到资源文件";
        }
        render(view: "/cmp/manage/resourceFile/previewFlash", model: [errors:errors,urlStr:urlStr,isSwf:isSwf,isMp3:isMp3]);
    }
    //删除资源文件
    def delete() {
        log.info("params->" + params);
        def reInfo = resourceFilesService.deleteFile();
        if (reInfo.recode == ReCode.OK) {
            //flash.success = "删除成功";
            flash.success = reInfo.msgs;
            flash.errors = reInfo.errors;
        } else {
            flash.errors = reInfo.recode.label;
        }
        params.remove("ids");
        redirect(action: "list", params: params);
    }
    //上传资源文件
    def upload() {
        def errors = "";
        def success = "";
        boolean isSaveOk=false;
        if (request.method == "POST") {
            def reInfo = resourceFilesService.uploadFile();
            log.info("reInfo-->" + reInfo);
            if (reInfo.recode == ReCode.OK) {
                int size = reInfo.recodes.size();
                boolean hasError = false;
                for (int i = 0; i < size; i++) {
                    def reObj = reInfo.recodes.get(i);
                    if (reObj.recode != ReCode.OK) {
                        hasError = true;
                        errors += reObj.fileName + ":"
                        if (reObj.recode == ReCode.SAVE_FAILED) {
                            errors += reObj.errors;
                        } else {
                            errors += reObj.recode?.label;
                        }
                        errors += "<br/>";
                    } else {
                        success += reObj.fileName + ":" + "上传成功<br/>";
                    }
                }
                isSaveOk=true;
            } else {
                errors += reInfo.recode.label;
            }
        }
        render(view: "/cmp/manage/resourceFile/upload", model: [isSaveOk:isSaveOk,success: success, errors: errors]);
    }
    def diyUploadPage(){
        long fileLimitSize=100;
        def reInfo=webSiteInfoService.getWebSiteInfo();
        WebSiteInfo webSiteInfo=reInfo?.webSiteInfo;
        if(webSiteInfo) {
            fileLimitSize=webSiteInfo.maxSizeOfFile;
        }
        //fileLimitSize=fileLimitSize+1;
        String fileLimitSizeStr= FormatUtil.byteToExpansion(fileLimitSize);
//        log.info("fileLimitSizeStr-->" + fileLimitSizeStr);
        println fileLimitSizeStr
        render(view: "/cmp/manage/resourceFile/diyUpload", model: [fileLimitSize:fileLimitSizeStr]);
    }
    //diyUPload上传文件
    def diyUpload(){
        render (resourceFilesService.uploadFile() as JSON)
    }
    //在编辑器中上传文件
    def uploadInEditor(){
        def errors = "";
        //def success = "";
        List<String> reStrs=new ArrayList<String>();
        def reInfo = resourceFilesService.uploadFile();
        log.info("reInfo-->" + reInfo);
        if (reInfo.recode == ReCode.OK) {
            int size = reInfo.recodes.size();
            //boolean hasError = false;
            for (int i = 0; i < size; i++) {
                def reObj = reInfo.recodes.get(i);
                if (reObj.recode != ReCode.OK) {
                    //hasError = true;
                    errors = reObj.fileName + ":"
                    if (reObj.recode == ReCode.SAVE_FAILED) {
                        errors += reObj.errors;
                    } else {
                        errors += reObj.recode?.label;
                    }
                    //errors += "<br/>";
                    JSONObject obj = new JSONObject();
                    obj.put("error", 1);
                    obj.put("message", errors);
                    reStrs.add(obj.toJSONString());
                } else {
                   // success += reObj.fileName + ":" + "上传成功<br/>";
                    JSONObject obj = new JSONObject();
                    obj.put("error", 0);
                    obj.put("url", request.getContextPath() + File.separator+"uploadFile"+File.separator + reObj.fileFullName);
                    String fname=reObj.fileName;
                    //fname=fname.substring(0,fname.lastIndexOf("."));
                    obj.put('origin_name',fname);
                    reStrs.add(obj.toJSONString());

                }
            }
        } else {
            //errors += reInfo.recode.label;
            JSONObject obj = new JSONObject();
            obj.put("error", 1);
            obj.put("message", reInfo.recode.label);
            reStrs.add(obj.toJSONString());
        }
        log.info("reStrs->"+reStrs);
        //返回数据
//        response.setContentType("text/html; charset=UTF-8");
//        def out = response.getWriter();
        int size=reStrs.size();
        String str="";
        for(int i=0;i<size;i++){
//            out.println(reStrs.get(i));
            str+=reStrs.get(i);
        }
//        out.flush();
//        out.close();
        render(text: str);
    }
    //在编辑器中管理资源文件
    def manageInEditor(){
        log.info("manageInEditor");

        //图片扩展名
        String[] fileTypes = ["gif", "jpg", "jpeg", "png", "bmp"];
        //获取参数
        String dirName = request.getParameter("dir");
        String path = request.getParameter("path") != null ? request.getParameter("path") : "";
        //排序形式，name or size or type
        String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

        String rootUrl  = request.getContextPath() + File.separator+"uploadFile"+File.separator;
        String currentUrl = rootUrl+dirName+File.separator;
        String currentDirPath = path;
        String moveupDirPath = "";
        //查询数据库
        ResourceType fileType= FileExts.dirTypeMap.get(dirName);
        def resourceList=ResourceFileInfo.findAllByType(fileType.code);
        //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if(resourceList) {
            resourceList.each {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = it.fileFullName;
                int iBegin=fileName.lastIndexOf(File.separator);
                if(iBegin>=0){
                    fileName=fileName.substring(iBegin+1);
                }
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                hash.put("is_dir", false);
                hash.put("has_file", false);
                hash.put("filesize", it.size);
                hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                hash.put("filetype", fileExt);

                hash.put("filename", fileName);
                hash.put("origin_name", it.fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(it.uploadTime));
                fileList.add(hash);
            }

        }

        if ("size".equals(order)) {
            Collections.sort(fileList, new SizeComparator());
        } else if ("type".equals(order)) {
            Collections.sort(fileList, new TypeComparator());
        } else {
            Collections.sort(fileList, new NameComparator());
        }
        JSONObject result = new JSONObject();
        result.put("moveup_dir_path", moveupDirPath);
        result.put("current_dir_path", currentDirPath);
        result.put("current_url", currentUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);

        // response.setContentType("application/json; charset=UTF-8");
        //out.println(result.toJSONString());
        log.info("result.toJSONString()->"+result.toJSONString());
        render(text: result.toJSONString());

    }
    class NameComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable)a;
            Hashtable hashB = (Hashtable)b;
            if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
            }
        }
    }
    class SizeComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable)a;
            Hashtable hashB = (Hashtable)b;
            if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
                return 1;
            } else {
                if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
                    return 1;
                } else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }
    class TypeComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable)a;
            Hashtable hashB = (Hashtable)b;
            if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
            }
        }
    }
}
