<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="cmp_front"/>
    <title>${contentsInfo?.title}</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'wg.css')}" type="text/css">

    %{--/***图片展示脚本**/--}%
    <link rel="stylesheet" type="text/css" href="${resource(dir: "js/image_jiaoben537",file:"jquery.ad-gallery.css")}">
    <script type="text/javascript" src="${resource(dir: "js/image_jiaoben537",file:"jquery.min.js")}"></script>
    <script type="text/javascript" src="${resource(dir: "js/image_jiaoben537",file:"jquery.ad-gallery.js")}"></script>
    <script type="text/javascript">
        $(function() {
            var galleries = $('.ad-gallery').adGallery();
            $('#switch-effect').change(
                    function() {
                        galleries[0].settings.effect = $(this).val();
                        return false;
                    }
            );
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
        list-style-image:url(list-style.gif);
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
    </style>
    %{--/***图片展示脚本结束**/--}%
    <lj:mediaTagConvertor/>
</head>

<body>
<div class="content">
    <div class="detail_middle">
        <div class="detail">
            <g:if test="${leftNavItems&&leftNavItems.size()>1}">
                <div class="left">
                    <ul>
                        <li style=" line-height: 50px;color:red;text-align: left;margin:0;padding:0 0 0 15px;float:left;border-top:none;border-bottom: 2px solid red;height:50px;width:100px;font-size: 16px;font-weight: bold">
                            <a href="${leftNavItems.get(0)?.url}">${leftNavItems.get(0)?.name}</a>
                        </li>

                        <div class="clear"></div>
                        <g:each in="${leftNavItems}" var="navItem" status="i">
                            <g:if test="${i>0}">
                            <li>
                                <a href="${navItem.url}">${navItem.name}</a>
                            </li>
                            </g:if>
                        </g:each>
                    </ul>
                </div>
            </g:if>


            <div class="right" ${(leftNavItems&&leftNavItems.size()>1)?"":"style='width:948px;'"}>
                <div class="nav" style="padding-left: 0px;">
                    <a href="${request.getContextPath()}" style="float: left;margin-right: 15px;">&nbsp;&nbsp;&nbsp;</a>
                    <g:if test="${topNavItems}">
                    <img width="2" height="13" class='first_img' src="../images/shuline.jpg">
                    <ul>
                        <g:set var="itemCount" value="${topNavItems.size()}" />
                        <g:each in="${topNavItems}" var="navItem" status="i">
                            <li>
                                <a href="${navItem.url}" style="line-height:24px;">${navItem.name}</a>
                                <g:if test="${i<(itemCount-1)}">
                                <img width="7" height="11" class='middle_img'
                                                                                   src="../images/jiantou.png">
                                </g:if>
                            </li>
                        </g:each>
                    </ul>
                    </g:if>
                </div>

                <p class="clear"></p>

                <div class="information_list">
                    <ul>
                        <li class="information_font">${contentsInfo?.title}</li>
                        <li class="information_time">发布时间：${lj.FormatUtil.dateTimeFormat(contentsInfo?.time)}
                            %{--内容来源：办公室 --}%
                            &nbsp;&nbsp;信息员：${contentsInfo?.author?:"佚名"}</li>
                        <li class="information_operate">
                            <span class="fl">字号：</span>
                            <span class="information_word fl information_bor"
                                  onclick="javascript:doZoom(this, 12, 22);">
                                <img alt="" src="../images/information_font_icon1.gif">
                            </span>
                            <span class="information_word fl" onclick="javascript:doZoom(this, 14, 24);">
                                <img alt="" src="../images/information_font_icon.gif">
                            </span>
                        </li>
                        <li class="information_pic"></li>
                        <li id="news_txt" class="information_article" style="text-align: left;">
                            <title></title>
                            ${contentsInfo?.content}

                            <g:if test="${contentsInfo?.imageInfoList}">
                                <br/> <br/>
                                <div align="center">
                                    <div id="gallery" class="ad-gallery">
                                        <div class="ad-image-wrapper">
                                        </div>
                                        <div class="ad-controls">
                                        </div>
                                        <div class="ad-nav">
                                            <div class="ad-thumbs">
                                                <ul class="ad-thumb-list">
                                                    <g:each in="${contentsInfo?.imageInfoList}" var="imageInfo">
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

                        </li>
                    </ul>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>