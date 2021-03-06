package com.lj.cmp.controller.front

//图片显示相关控制器
class ImageShowController {
    def resourceFilesService;
    def index() {}
    //显示图片
    def download() {
//        //防盗链处理
//        String Referer = request.getHeader("Referer");
//        String baseUrl = null;
//        def reInfo = shopService.getShopInfo();
//        if (ReCode.OK == reInfo.recode) {
//            baseUrl = reInfo.restaurantInfo?.baseUrl;
//        }
//        if (baseUrl == null) {
//            baseUrl = grailsApplication.config.grails.baseurls.baseUrl;
//        }
//        if (baseUrl==null||(Referer != null && Referer.indexOf(baseUrl) < 0)) {//盗链
//            println("图片盗链");
//            return;
//        }
//        //根据ID查询出图片url
//        String url_str = params.imgUrl;
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        if (url_str) {
//            response.contentType = "image/jpeg";
//            def out = response.outputStream;
//            uploadFileService.download(url_str, out);
//            out.flush();
//            out.close();
//        } else {//显示一张默认图片
//            response.contentType = "image/jpeg";
//            def out = response.outputStream;
//            UploadFile.downloadFromFileSystem("web-app/images/no_image.jpg", out);
//            out.flush();
//            out.close();
//        }
        resourceFilesService.imageDownload(params);
    }
    //显示缩略图
    def downloadThumbnail() {
//        //防盗链处理
//        String Referer = request.getHeader("Referer");
//        String baseUrl = null;
//        def reInfo = shopService.getShopInfo();
//        if (ReCode.OK == reInfo.recode) {
//            baseUrl = reInfo.restaurantInfo?.baseUrl;
//        }
//        if (baseUrl == null) {
//            baseUrl = grailsApplication.config.grails.baseurls.baseUrl;
//        }
//        println("baseUrl-->"+baseUrl);
//        if (baseUrl==null||(Referer != null && Referer.indexOf(baseUrl) < 0)) {//盗链
//            println("图片盗链");
//            return;
//        }
//        //根据ID查询出图片url
//        String url_str = params.imgUrl; ;
//        int width = 100;
//        int height = 100;
//        if (params.width)
//            width = lj.Number.toInteger(params.width);
//        if (params.height)
//            height = lj.Number.toInteger(params.height);
//        if (url_str) {
//            response.contentType = "image/jpeg";
//            def out = response.outputStream;
//            uploadFileService.downloadThumbnail(url_str, out, width, height);
//            //UploadFile.downloadThumbnailFromFileSystem("web-app/images/grails_logo.png",out,width,height);//测试
//            out.flush();
//            out.close();
//        } else {//显示一张默认图片
//            response.contentType = "image/jpeg";
//            def out = response.outputStream;
//            UploadFile.downloadFromFileSystem("web-app/images/no_image.jpg", out);
//            out.flush();
//            out.close();
//        }
        resourceFilesService.imageDownloadThumbnail(params);
    }
}
