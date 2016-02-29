package com.lj.cmp.data

class ContentClassInfo {
    String name;
    String description;
    ContentClassInfo parent;
    boolean onMenu=true;
    boolean canRemove=true;
    static constraints = {
        name(nullable: false,blank: false,maxSize: 64,unique: true);
        description(nullable: true,blank: true,maxSize: 512);
        parent(nullable: true);
        onMenu(nullable:false);
        canRemove(nullable:false);
    }

    @Override
    public java.lang.String toString() {
        return name;
    }
}
