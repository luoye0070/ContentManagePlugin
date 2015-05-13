package com.lj.cmp.data

class LinkInfo {
    String name;
    String url;
    static constraints = {
        name(nullable: true,blank: true,maxSize: 256);
        url(nullable: false,blank: false,maxSize: 256);
    }
}
