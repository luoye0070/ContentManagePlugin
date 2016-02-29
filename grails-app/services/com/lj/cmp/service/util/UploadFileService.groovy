package com.lj.cmp.service.util

import com.lj.cmp.util.common.UploadFile
import com.lj.cmp.util.common.ValidationCode
import com.lj.cmp.util.customenum.ResourceType


class UploadFileService {
    WebUtilService webUtilService;
    //上传图片
    public String upload(InputStream is, String fileName,String dirName) {
        fileName = fileName.replace("=", "").replace(",", "").replace("&", "").replace("\"", "").replace(" ","_");
        if (UploadFile.bootPath == "") {
            UploadFile.bootPath = webUtilService.getServletContext().getRealPath("/");
        }

        //获取扩展名
        String fileExt ="";
        if(fileName.lastIndexOf(".")!=-1){
            fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        }
        //文件名有时间加8位随机数组成
        Date now=new Date();
        String nowStr=now.getTime()+"";
        String randStr= ValidationCode.getAuthCodeStr(8,ValidationCode.LAWERCASE_NUMBER);
        fileName=nowStr+randStr+fileExt;

        BufferedInputStream bis = new BufferedInputStream(is);
        bis.mark(bis.available() + 1);
        //String fileFullName= UploadFile.uploadToYuPan(bis,fileName);
        String fileFullName = null;
        bis.reset();
        fileFullName = UploadFile.uploadToFileSystem(bis,dirName,fileName);
        bis.close();
        bis = null;
        return fileFullName;
    }
    //删除图片
    public boolean delete(String fileFullName) {
        if (UploadFile.bootPath == "") {
            UploadFile.bootPath = webUtilService.getServletContext().getRealPath("/");
        }
        return UploadFile.deleteFromFileSystem(fileFullName);
    }
//    //获取图片URL
//    public String getUrl(String fileFullName) {
//
//    }
    //图片下载
    public int download(String fileFullName, OutputStream os) {
        if (UploadFile.bootPath == "") {
            UploadFile.bootPath = webUtilService.getServletContext().getRealPath("/");
        }
        return UploadFile.downloadFromFileSystem(fileFullName, os);
    }
    //缩略图片下载
    public int downloadThumbnail(String fileFullName, OutputStream os, int width, int height) {
        if (UploadFile.bootPath == "") {
            UploadFile.bootPath = webUtilService.getServletContext().getRealPath("/");
        }
        return UploadFile.downloadThumbnailFromFileSystem(fileFullName, os, width, height);
    }
    //获取文件大小
    public long getFileSize(String fileFullName) {
        if (UploadFile.bootPath == "") {
            UploadFile.bootPath = webUtilService.getServletContext().getRealPath("/");
        }
        return UploadFile.getFileSize(fileFullName);
    }
}
