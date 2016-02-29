<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-4-1
  Time: 上午12:38
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.lj.cmp.util.common.FormatUtil" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    %{--<meta name="layout" content="cmp_manageMain"/>--}%
    <title>内容预览</title>
    <link rel="stylesheet" type="text/css" href="${resource(dir: "js/image_jiaoben537",file:"jquery.ad-gallery.css")}">
    <script type="text/javascript" src="${resource(dir: "js/image_jiaoben537",file:"jquery.min.js")}"></script>
    <script type="text/javascript" src="${resource(dir: "js/image_jiaoben537",file:"jquery.ad-gallery.js")}"></script>
    <script type="text/javascript">
        $(function() {
            //$('img.image1').data('ad-desc', 'Whoa! This description is set through elm.data("ad-desc") instead of using the longdesc attribute.<br>And it contains <strong>H</strong>ow <strong>T</strong>o <strong>M</strong>eet <strong>L</strong>adies... <em>What?</em> That aint what HTML stands for? Man...');
            //$('img.image1').data('ad-title', 'Title through $.data');
            //$('img.image4').data('ad-desc', 'This image is wider than the wrapper, so it has been scaled down');
            //$('img.image5').data('ad-desc', 'This image is higher than the wrapper, so it has been scaled down');
            var galleries = $('.ad-gallery').adGallery();
//            $('#switch-effect').change(
//                    function() {
//                        galleries[0].settings.effect = $(this).val();
//                        return false;
//                    }
//            );
            $('#toggle-slideshow').click(
                    function() {
                        galleries[0].slideshow.toggle();
                        return false;
                    }
            );
            $('#toggle-description').click(
                    function() {
                        if(!galleries[0].settings.description_wrapper) {
                            galleries[0].settings.description_wrapper = $('#descriptions');
                        } else {
                            galleries[0].settings.description_wrapper = false;
                        }
                        return false;
                    }
            );
        });
    </script>

    <style type="text/css">
    * {
        font-family: "Lucida Grande", "Lucida Sans Unicode", "Lucida Sans", Verdana, Arial, sans-serif;
        color: #333;
        line-height: 140%;
    }
    select, input, textarea {
        font-size: 1em;
    }
    body {
        padding: 30px;
        font-size: 70%;
        width: 1250px;
    }
    h2 {
        margin-top: 1.2em;
        margin-bottom: 0;
        padding: 0;
        border-bottom: 1px dotted #dedede;
    }
    h3 {
        margin-top: 1.2em;
        margin-bottom: 0;
        padding: 0;
    }
    .example {
        border: 1px solid #CCC;
        background: #f2f2f2;
        padding: 10px;
    }
    ul {
        list-style-image:url(${resource(dir: "js/image_jiaoben537",file:"list-style.gif")});
    }
    pre {
        font-family: "Lucida Console", "Courier New", Verdana;
        border: 1px solid #CCC;
        background: #f2f2f2;
        padding: 10px;
    }
    code {
        font-family: "Lucida Console", "Courier New", Verdana;
        margin: 0;
        padding: 0;
    }

    #gallery {
        padding: 30px;
        background: #e1eef5;
    }
    #descriptions {
        position: relative;
        height: 50px;
        background: #EEE;
        margin-top: 10px;
        width: 640px;
        padding: 10px;
        overflow: hidden;
    }
    #descriptions .ad-image-description {
        position: absolute;
    }
    #descriptions .ad-image-description .ad-description-title {
        display: block;
    }

    .NewsCon{width: 700px;}

        /*文件夹细节列举样式*/
    .NewsCon #ReportIDname{height:37px;display:block;width:662px;font-size:18px;font-family:'微软雅黑','黑体';text-align:left;}
    .NewsCon #ReportIDtext{display:block;padding:20px 30px 0px 20px;text-align:left;line-height:150%;color:#666;}
    .NewsCon *{line-height:150%;}
        /*新闻细节列举样式*/
    .NewsCon .Newsfun{text-align:center;}
    .NewsCon .Newsfun div{margin:0 auto;width:650px;height:20px;*height:30px;padding-top:10px;}

    </style>
</head>
<body>
    <div class="NewsCon" align="center">
        <span id="ReportIDname">${contentsInfoInstance?.title}</span>
        <div style="margin: 5px 0px; width: 662px; text-align: left; font-size: 16px;">
        %{--<h6>${contentsInfoInstance?.author}</h6>--}%
            <span id="ReportIDgetSubhead"></span>
        </div>
        <div class="Newsfun"><div>
            【时间：<span id="ReportIDIssueTime">${FormatUtil.dateTimeFormat(contentsInfoInstance?.time)}</span>】
        </div></div>
        <span id="ReportIDtext">
        ${contentsInfoInstance?.content}
        </span>
        <g:if test="${contentsInfoInstance?.imageInfoList}">
            <div align="center">
                <div id="gallery" class="ad-gallery">
                    <div class="ad-image-wrapper">
                    </div>
                    <div class="ad-controls">
                    </div>
                    <div class="ad-nav">
                        <div class="ad-thumbs">
                            <ul class="ad-thumb-list">
                                <g:each in="${contentsInfoInstance?.imageInfoList}" var="imageInfo">
                                    <li>
                                        <a href="${imageInfo.imgUrl}">
                                            <img height="70" width="100" src="${imageInfo.imgUrl}" title="${imageInfo.name}" alt="${imageInfo.decription}" class="image12">
                                        </a>
                                    </li>
                                </g:each>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </g:if>
    </div>
</body>
</html>