package com.lj.cmp.data

class ImageInfo {
    String name;
    String imgUrl;
    String decription;
    static constraints = {
        name(nullable: true,blank: true,maxSize: 1024);
        imgUrl(nullable: false,blank: false,maxSize: 256);
        decription(nullable: true,blank: true,maxSize: 10240);
    }
}
