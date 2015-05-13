package com.lj.cmp.data

class WebSiteInfo {
    String siteName;//网站名称
    String logoUrl;
    String flashUrl; //首页Flash地址
    String imageUrl;//首页Image地址
    long maxSizeOfFile;//上传资源文件最大字节数
    List<LinkInfo> linkInfoList=new ArrayList<LinkInfo>();
    String rightInfo;//版权和备案信息
    String mailAddr;//邮箱地址
    static hasMany = [linkInfoList:LinkInfo];
    static constraints = {
        siteName(nullable: true,blank: true,maxSize: 128);
        logoUrl(nullable: false,blank: true,maxSize: 256);
        flashUrl(nullable: false,blank: true,maxSize: 256);
        imageUrl(nullable: false,blank: true,maxSize: 256);
        maxSizeOfFile(nullable: false);
        rightInfo(nullable: true,blank: true,maxSize: 256);
        mailAddr(nullable: true,blank: true,maxSize: 128);
    }
}
