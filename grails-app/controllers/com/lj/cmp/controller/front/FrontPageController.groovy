package com.lj.cmp.controller.front

class FrontPageController {
    def frontPageService;
    def index() {
        def blockMap=frontPageService.getIndexData();
        log.info("blockMap->"+blockMap);
        render(view: "/cmp/front/index",model:[blockMap:blockMap]);
    }



    def newsList(){
        def reInfo=frontPageService.getListData(params);
        log.info("newsList->"+reInfo);
        render(view: "/cmp/front/newsList",model: reInfo);
    }


    def newsDetail(){
        def reInfo=frontPageService.getDetailData(params);
        log.info("newsDetail->"+reInfo);
        render(view: "/cmp/front/newsDetail",model: reInfo);
    }

    def test(){
        render(view:'../front/test')
    }

    def nDetail(){
        render(view: "/cmp/front/nDetail")
    }

    def nList(){
        render(view: "/cmp/front/nList")
    }
}
