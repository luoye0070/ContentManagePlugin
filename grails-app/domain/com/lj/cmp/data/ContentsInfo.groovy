package com.lj.cmp.data

class ContentsInfo {
    String title;
    Date time=new Date();
    String author;
    String content;
    ContentClassInfo contentClassInfo;
    boolean canRemove=true;
    List<ImageInfo> imageInfoList=new ArrayList<ImageInfo>();
    static hasMany = [imageInfoList:ImageInfo];
    static constraints = {
        title(nullable: false,blank: false,maxSize: 1024);
        time(nullable: false);
        author(nullable: true,blank: true,maxSize: 216);
        content(nullable: true,blank: true,maxSize: 102400);
        contentClassInfo(nullable: true);
        canRemove(nullable:false);
    }
}
