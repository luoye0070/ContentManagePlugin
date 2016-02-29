package com.lj.cmp.util.customenum

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-24
 * Time: 上午12:18
 * To change this template use File | Settings | File Templates.
 */
public enum ResourceType {
    IMAGE(1,'图片'),
    FLASH(2,'flash'),
    MEDIA(3,'音频/视频'),
    FILE(4,'文件');

    public int code
    public String label

    ResourceType(Integer code,String label){
        this.code=code
        this.label=label
    }
    public static String getLable(int code){
        switch (code){
            case IMAGE.code:
                return  IMAGE.label;
            case FLASH.code:
                return  FLASH.label;
            case MEDIA.code:
                return  MEDIA.label;
            case FILE.code:
                return  FILE.label;
            default:
                return "未知类型"
        }
    }
    public static def getCodeList(){
        return [
                IMAGE.code,
                FLASH.code,
                MEDIA.code,
                FILE.code
        ];
    }
    public static ResourceType[] types=[
            IMAGE,
            FLASH,
            MEDIA,
            FILE
    ];
}