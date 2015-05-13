package com.lj.cmp.data

class ContentBlockInfo {
    ContentsInfo contentsInfo;//一个内容，设置后显示这个内容
    List<ContentsInfo> contentsInfoList;//一组相关内容，设置后将列出这组内容
    List<ContentClassInfo> contentClassInfoList;//一组内容类别，设置后将列出类别中所有内容
    String name;//内容块名称
    int code;//内容块编码,不能编辑
    String logoUrl;//内容块logo
    boolean inMenu=false;//放菜单里
    boolean inNav=true;//放快速导航里
    int maxCount=10;//最多显示记录数,0是全部显示
    static hasMany = [contentsInfoList:ContentsInfo,contentClassInfoList:ContentClassInfo];
    static constraints = {
        contentsInfo(nullable: true);
        //contentsInfoList();
        //contentClassInfoList();
        name(nullable: false);
        code(nullable: false,unique: true);
        logoUrl(nullable: true);
        inMenu(nullable: false);
        inNav(nullable: false);
        maxCount(nullable:false);
    }
    static mapping = {
        contentsInfo(lazy:false);
        contentsInfoList(lazy:false); //也可以这样去配置
        contentClassInfoList(lazy:false); //也可以这样去配置
    }
}
