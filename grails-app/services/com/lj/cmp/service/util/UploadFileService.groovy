package com.lj.cmp.service.util

import com.lj.cmp.util.common.UploadFile
import com.lj.cmp.util.customenum.ResourceType


class UploadFileService {
    WebUtilService webUtilService;
    //上传图片
    public String upload(InputStream is, String fileName,String dirName) {
        fileName = fileName.replace("=", "").replace(",", "").replace("&", "").replace("\"", "").replace(" ","_");
        if (UploadFile.bootPath == "") {
            UploadFile.bootPath = webUtilService.getServletContext().getRealPath("/");
        }
        //将文件名限定在64个字符内
        Date now=new Date();

        String nowStr=now.getTime()+"";
        int nowStrLength=nowStr.length();
        if (fileName.length() > (64-nowStrLength)) {
            fileName = fileName.substring(fileName.length() - (64-nowStrLength));
        }
        fileName=nowStr+fileName;
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
