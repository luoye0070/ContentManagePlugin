<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-3-26
  Time: 下午11:14
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    %{--<meta name="layout" content="cmp_manageMain"/>--}%
   <title>上传资源文件</title>
    <link href="${resource(dir: "js/diyUpload/css", file: "webuploader.css")}" rel="stylesheet" type="text/css"/>
    <link href="${resource(dir: "js/diyUpload/css", file: "diyUpload.css")}" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.8.1.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: "js/diyUpload/js", file: "webuploader.html5only.js")}"></script>
    <script type="text/javascript" src="${resource(dir: "js/diyUpload/js", file: "diyUpload.js")}"></script>
    <style>
    *{ margin:0; padding:0;}
    #box{ margin:20px 0 0 20px }
    </style>

    <script type="text/javascript">
    $(function(){
        var upload=$('#fileDiv').diyUpload({
            url:'${createLink(controller:'resourceFile',action:'diyUpload')}',
            success:function( data ) {

            },
            error:function( err ) {
                console.info( err );
            },
            buttonText : '选择文件(可以批量上传)',
            chunked:true,
            chunkSize:(${fileLimitSize}),
            //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
            fileNumLimit:50,
            fileSizeLimit:(${fileLimitSize})*50,
            fileSingleSizeLimit:(${fileLimitSize}),
            accept: {}

        });

        upload.on('uploadFinished',function(){
            if(top.topManager){
                //打开左侧菜单中配置过的页面
                top.topManager.reloadPage("resource_list_menu");
            }
        });
    });
    </script>
</head>
<body>
<div id="box">
    <div id="fileDiv" ></div>
</div>
<div style="margin: 20px 20px;">
    <a href="${createLink(controller: "resourceFile",action: "upload")}">旧版文件上传入口</a>
</div>
</body>
</html>