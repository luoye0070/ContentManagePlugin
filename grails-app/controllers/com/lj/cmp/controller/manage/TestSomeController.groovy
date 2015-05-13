package com.lj.cmp.controller.manage

class TestSomeController {

    def index() {
        redirect(action: "testImage537");
    }
    def testEditor(){
        def contentStr=null;
        if(request.method=="POST"){
            contentStr=params.contentStr;
            log.info("contentStr->"+contentStr);
        }
        render(view: "testEditor",model: [contentStr:contentStr])
    }
    def testImage537(){
        render(view: "testImage537")
    }
    def testImage2582(){
        render(view: "testImage2582")
    }
    def testImage828(){
        render(view: "testImage828")
    }
    def testImage3004(){
        render(view: "testImage3004")
    }
}
