package com.lj.cmp.util.customenum

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-24
 * Time: 下午11:18
 * To change this template use File | Settings | File Templates.
 */
class FileExts {
    //定义允许上传的文件扩展名
    static HashMap<String, String> extMap = new HashMap<String, String>();
    static HashMap<String, ResourceType> dirTypeMap = new HashMap<String, ResourceType>();
    static {
    extMap.put("image", "gif,jpg,jpeg,png,bmp");
    extMap.put("flash", "swf,flv");
    //extMap.put("flash", "swf");
    //extMap.put("media", "flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
    extMap.put("media", "flv,mp3,wmv,avi,mpg,asf,3gp,mov,mp4,asx");
    extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

    dirTypeMap.put("image", ResourceType.IMAGE);
    dirTypeMap.put("flash", ResourceType.FLASH);
    dirTypeMap.put("media", ResourceType.MEDIA);
    dirTypeMap.put("file", ResourceType.FILE);
    }
}
