package com.lj.cmp.data

import com.lj.cmp.util.customenum.ResourceType

class ResourceFileInfo {
    String fileName;
    int type;
    String dirName;
    Date uploadTime=new Date();
    long size=0l;
    String fileFullName;
    static constraints = {
        fileName(nullable: false,blank: false,maxSize: 64);
        type(nullable:false,inList: ResourceType.getCodeList());
        dirName(nullable: false,blank: false,maxSize: 128);
        uploadTime(nullable: false);
        size(nullable:false,min: 0l);
        fileFullName(nullable: false,blank: false,maxSize: 218);
    }
}
