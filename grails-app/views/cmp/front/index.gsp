<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-4-6
  Time: 下午2:46
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="lj.FormatUtil; lj.enumCustom.ContentBlockCode" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="cmp_front"/>
    <title>${webSiteInfo?.siteName}</title>
    <link type="text/css" href="${resource(dir: "css/front", file: "index.css")}" rel="stylesheet"/>
    <script src="${resource(dir: "js", file: "jquery-1.8.1.min.js")}"></script>

    %{--图片轮播代码开始--}%
    <link href="${resource(dir: "js/image_jiaoben828/css",file:"jquery.slideBox.css")}" rel="stylesheet" type="text/css" />
    <script src="${resource(dir: "js/image_jiaoben828/js",file: "jquery.slideBox.min.js")}" type="text/javascript"></script>
    <script>
        jQuery(function($){
            $('#ywbb').slideBox({
                width:353,
                height:258
            });
        });
    </script>
    %{--图片轮播代码结束--}%
    %{--福企新闻图片轮播代码开始--}%
    <link href="${resource(dir: "js/image_jiaoben3004/css",file:"style.css")}" rel="stylesheet" type="text/css" />
    <script src="${resource(dir: "js/image_jiaoben3004/js",file: "jquery.SuperSlide2.js")}" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            /* 图片滚动效果 */
            $(".mr_frbox").slide({
                titCell: "",
                mainCell: ".mr_frUl ul",
                autoPage: true,
                effect: "leftLoop",
                autoPlay: true,
                vis: 4
            });
        });
    </script>
    %{--福企新闻图片轮播代码结束--}%

    %{--搜索类别样式开始--}%
    <style type="text/css">
.search *{padding:0; margin:0;}
.search ul{list-style:none; margin:0px 0px; position:relative;}
.search ul li{position:relative; width:83px;height:28px; border-top:1px solid #cacaca;border-bottom:1px solid #cacaca;background-color: #ffffff;}
.search .text{width:76px; height:28px;padding-left: 5px; position:absolute; left:0; top:0;border:0px solid #ccc; line-height:28px; font-size:14px; cursor:default;}
.search .btn{position:absolute;width:16px; height:27px; border-right:1px solid #cacaca;right:1px; top:1px; display:inline-block; background:url(${resource(dir:"images",file:"select_bt_bg.png")}) no-repeat;}
.search .btnhover{background:url(http://www.poluoluo.com/jzxy/UploadFiles_335/201107/20110730193804162.jpg);}
.search .select{border:1px solid #666;width:80px; height:auto; position:absolute; top:30px; left:0;display:none;background:#fff;z-index: 100}
.search .select p{line-height:16px; font-size:13px; cursor:default; position:relative;width:80px;}
.search .select .hover{background:#3399FD;}
    </style>
    <script type="text/javascript">
        function beginSelect(elem){
            if(elem.className == "btn"){
                elem.className = "btn btnhover"
                elem.onmouseup = function(){
                    this.className = "btn"
                }
            }
            var ul = elem.parentNode.parentNode;
            var li = ul.getElementsByTagName("li");
            var selectArea = li[li.length-1];
            if(selectArea.style.display == "block"){
                selectArea.style.display = "none";
            }
            else{
                selectArea.style.display = "block";
                mouseoverBg(selectArea);
            }
        }
        function mouseoverBg(elem1){
            var value=elem1.parentNode.getElementsByTagName("input")[0];
            var input = elem1.parentNode.getElementsByTagName("input")[1];
            var p = elem1.getElementsByTagName("p");
            var pLength = p.length;
            for(var i = 0; i < pLength; i++){
                p[i].onmouseover = showBg;
                p[i].onmouseout = showBg;
                p[i].onclick = postText;
            }
            function showBg(){
                this.className == "hover"?this.className = " ":this.className = "hover";
            }
            function postText(){
                var selected = this.innerHTML;
                //input.setAttribute("value",selected);
                $(input).val(selected);
                var selectedVal= this.getAttribute("value");
                value.setAttribute("value",selectedVal);
                elem1.style.display = "none";
            }
        }
    </script>
    %{--搜索类别样式开始--}%
</head>

<body>
<div class="index_mainw">
<div class="index_main">
    <div class="horizontal_space"></div>

    <div class="flash">
        <EMBED width="960" height="90" id="flashId"
               src="${webSiteInfo?.flashUrl}"></EMBED>
    </div>

    <div class="horizontal_space"></div>

    <div class="gdxw_search">
        %{--滚动新闻--}%
        <div class="gdxw">
            <ul id="gdxwList">
                <div id="gdxwListDetail" style="height: auto;">
                    <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_GDXW.code)?.contentSummaryList}" var="contentSummary">
                        <li title="${contentSummary.title}"><a href="${contentSummary.detailUrl}">${contentSummary.title?.length()>52?contentSummary.title?.substring(0,50)+"...":contentSummary.title}</a></li>
                    </g:each>
                    %{--<li title="来到家乐福降水量减少了几分赛季发牢骚积分联赛积分司法拘留所附加送了几分失落立第三方随风倒垃圾劳动纠纷"><a href="#">来到家乐福降水量减少了几分赛季发牢骚积分联赛积分司法拘留所附加送了几分失落立第三方随风倒垃圾劳动纠纷</a></li>--}%
                </div>
            </ul>
            <script type="text/javascript">
                xS=function(A,B,C,D,E,F){
                    var $=function (id){return document.getElementById(id)},Y=+!!F;
                    (A=$(A)).appendChild((B=$(B)).cloneNode(true));
                    (function (){
                        var m=A.scrollTop%C?(E||0):D;
                        A.scrollTop=[0,B.offsetHeight][Y]==A.scrollTop?[B.offsetHeight-1,1][Y]:(A.scrollTop+[-1,1][Y]);
                        setTimeout(arguments.callee,m);
                    })()
                    return arguments.callee;
                }
                xS('gdxwList','gdxwListDetail',22,3000,10,1);
            </script>
        </div>

        <div class="vertical_space"></div>
        %{--搜索    --}%
        <div class="search">
            <form action="${createLink(controller: "frontPage",action: "newsList")}" method="POST">
                %{--<input type="text" name="title"/>--}%
                <g:textField name="title" value="" data="key"/>
                %{--<select name="contentClassId">--}%
                    %{--<g:each in="${blockMap.get("searchClass")}" var="contentClassSummary">--}%
                        %{--<option value="${contentClassSummary?.value}" >${contentClassSummary?.name}</option>--}%
                    %{--</g:each>--}%
                %{--</select>--}%
                <ul>
                    <li>
                        <input name="contentClassId" type="hidden" value="0" />
                        <input value="全部" type="text" class="text" onclick="beginSelect(this);" />
                        <span class="btn" onmousedown="beginSelect(this)"></span></li>
                    <li class="select">
                        <g:each in="${blockMap.get("searchClass")}" var="contentClassSummary">
                            <p value="${contentClassSummary?.value}">${contentClassSummary?.name}</p>
                        </g:each>
                    </li>
                </ul>
                <input type="submit" value=""/>
            </form>
        </div>
    </div>

    <div class="horizontal_space"></div>

    <div class="content">
        <div class="left">
            <div class="row">
                %{--要闻播报--}%
                <div class="ywbb slideBox" id="ywbb">
                    <ul class="items">
                        <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_YWBB.code)?.contentSummaryList}" var="image">
                            <li><a href="${image.detailUrl}" title="${image.title?.length()>19?image.title?.substring(0,17)+"...":image.title}"><img src="${image.imageUrl}"></a></li>
                        </g:each>
                        %{--<li><a href="http://sc.chianz.com/" title="这里是测试标题五"><img src="${resource(dir: "js/image_jiaoben828/")}img/5.jpg"></a></li>--}%
                    </ul>
                </div>

                <div class="vertical_space"></div>
                %{--热点关注--}%
                <div class="rdgz">
                    <div class="rdgz_top">
                        <g:if test="${blockMap.get("block"+ContentBlockCode.CODE_RDGZ.code)?.contentSummaryList}">
                            <g:set var="contentSummary" value="${blockMap.get("block"+ContentBlockCode.CODE_RDGZ.code)?.contentSummaryList[0]}"></g:set>
                            <div class="title">
                                <a href="${contentSummary.detailUrl}">${contentSummary.title?.length()>17?contentSummary.title?.substring(0,15)+"...":contentSummary.title}</a>
                            </div>
                            <div class="content">
                                ${contentSummary.summary?.length()>44?contentSummary.summary?.substring(0,42)+"...":contentSummary.summary}
                            </div>
                        </g:if>
                    </div>
                    <div class="rdgz_list">
                        <ul>
                            <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_RDGZ.code)?.contentSummaryList}" var="contentSummary" status="i">
                                <g:if test="${i>0}">
                                <li title="${contentSummary.title}"><a href="${contentSummary.detailUrl}">
                                    <span class="title">${contentSummary.title?.length()>18?contentSummary.title?.substring(0,16)+"...":contentSummary.title}</span>
                                    <span class="time">${FormatUtil.dateFormat(contentSummary.time)}</span>
                                </a></li>
                                </g:if>
                            </g:each>
                            %{--<li title="来到家乐福降水量减少了几分赛季发牢骚积分联赛积分司法拘留所附加送了几分失落立第三方随风倒垃圾劳动纠纷"><a href="#">--}%
                                %{--<span class="title">登陆福建电力斯大林分阶段斯拉夫斯蒂芬建立第三方随风倒联赛积分司法拘留所附加联赛积分司法拘留所附加垃圾劳动纠纷</span>--}%
                                %{--<span class="time">2015-02-12</span>--}%
                            %{--</a></li>--}%
                        </ul>
                        <div class="rdgl_more">
                        <a href="${blockMap.get("block"+ContentBlockCode.CODE_RDGZ.code)?.url}">更多></a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="horizontal_space"></div>

            <div class="row">
                %{--部务公开--}%
                <div class="cblist">
                    <div class="top">
                        <div class="title">${blockMap.get("block"+ContentBlockCode.CODE_BWGK.code)?.name}</div>
                        <div class="more">
                            <a href="${blockMap.get("block"+ContentBlockCode.CODE_BWGK.code)?.url}">更多</a>
                        </div>
                    </div>
                    <div class="content">
                        <ul>
                            <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_BWGK.code)?.contentSummaryList}" var="contentSummary" status="i">
                            <li><a href="${contentSummary.detailUrl}">
                                <span class="title">${contentSummary.title?.length()>19?contentSummary.title?.substring(0,17)+"...":contentSummary.title}</span>
                                <span class="time">${FormatUtil.dateFormat(contentSummary.time)}</span>
                            </a></li>
                            </g:each>
                            %{--<li><a href="#">--}%
                                %{--<span class="title">两队交锋两地分居三级地方历史交锋设计</span>--}%
                                %{--<span class="time">2014-09-23</span>--}%
                            %{--</a></li>--}%
                        </ul>
                    </div>
                </div>

                <div class="vertical_space"></div>

                 %{--活动锦集--}%
                <div class="cblist">
                    <div class="top">
                        <div class="title">${blockMap.get("block"+ContentBlockCode.CODE_HDJJ.code)?.name}</div>
                        <div class="more">
                            <a href="${blockMap.get("block"+ContentBlockCode.CODE_HDJJ.code)?.url}">更多</a>
                        </div>
                    </div>
                    <div class="content">
                        <ul>
                            <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_HDJJ.code)?.contentSummaryList}" var="contentSummary" status="i">
                            <li><a href="${contentSummary.detailUrl}">
                                <span class="title">${contentSummary.title?.length()>19?contentSummary.title?.substring(0,17)+"...":contentSummary.title}</span>
                                <span class="time">${FormatUtil.dateFormat(contentSummary.time)}</span>
                            </a></li>
                            </g:each>
                            %{--<li><a href="#">--}%
                                %{--<span class="title">两队交锋两地分居三级地方历史交锋设计</span>--}%
                                %{--<span class="time">2014-09-23</span>--}%
                            %{--</a></li>--}%
                        </ul>
                    </div>
                </div>
            </div>

            <div class="horizontal_space"></div>

            <div class="row">
                <div class="ib">
                    <img src="${webSiteInfo?.imageUrl}" width="650" height="90"/>
                </div>
            </div>

            <div class="horizontal_space"></div>

            <div class="row">
                %{--文化传承--}%
                <div class="cblist">
                    <div class="top">
                        <div class="title">${blockMap.get("block"+ContentBlockCode.CODE_WHCC.code)?.name}</div>
                        <div class="more">
                            <a href="${blockMap.get("block"+ContentBlockCode.CODE_WHCC.code)?.url}">更多</a>
                        </div>
                    </div>
                    <div class="content">
                        <ul>
                            <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_WHCC.code)?.contentSummaryList}" var="contentSummary" status="i">
                                <li><a href="${contentSummary.detailUrl}">
                                    <span class="title">${contentSummary.title?.length()>19?contentSummary.title?.substring(0,17)+"...":contentSummary.title}</span>
                                    <span class="time">${FormatUtil.dateFormat(contentSummary.time)}</span>
                                </a></li>
                            </g:each>
                            %{--<li><a href="#">--}%
                                %{--<span class="title">两队交锋两地分居三级地方历史交锋设计</span>--}%
                                %{--<span class="time">2014-09-23</span>--}%
                            %{--</a></li>--}%
                        </ul>
                    </div>
                </div>

                <div class="vertical_space"></div>

                %{--国内新闻--}%
                <div class="cblist">
                    <div class="top">
                        <div class="title">${blockMap.get("block"+ContentBlockCode.CODE_GNXW.code)?.name}</div>
                        <div class="more">
                            <a href="${blockMap.get("block"+ContentBlockCode.CODE_GNXW.code)?.url}">更多</a>
                        </div>
                    </div>
                    <div class="content">
                        <ul>
                            <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_GNXW.code)?.contentSummaryList}" var="contentSummary" status="i">
                                <li><a href="${contentSummary.detailUrl}">
                                    <span class="title">${contentSummary.title?.length()>19?contentSummary.title?.substring(0,17)+"...":contentSummary.title}</span>
                                    <span class="time">${FormatUtil.dateFormat(contentSummary.time)}</span>
                                </a></li>
                            </g:each>
                            %{--<li><a href="#">--}%
                                %{--<span class="title">两队交锋两地分居三级地方历史交锋设计</span>--}%
                                %{--<span class="time">2014-09-23</span>--}%
                            %{--</a></li>--}%
                        </ul>
                    </div>
                </div>
            </div>

            <div class="horizontal_space"></div>

            <div class="row">
                %{--福企新闻--}%
                <div class="fqxw">
                    <div class="top">
                        <div class="title">${blockMap.get("block"+ContentBlockCode.CODE_FQXW.code)?.name}</div>
                        <div class="more">
                            <a href="${blockMap.get("block"+ContentBlockCode.CODE_FQXW.code)?.url}">更多</a>
                        </div>
                    </div>
                    <div class="content">
                        <div class="mr_frbox">
                            <img class="mr_frBtnL prev" src="${resource(dir: "css/front/image/")}mfrl.png" />
                            <div class="mr_frUl">
                                <ul id="mr_fu">
                                    <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_FQXW.code)?.contentSummaryList}" var="contentSummary" status="i">
                                        <li>
                                            <div class="image">
                                                <a href="${contentSummary.detailUrl}">
                                                    <img src="${contentSummary.imageUrl}" />
                                                </a>
                                            </div>
                                            <div class="info">
                                                <a href="${contentSummary.detailUrl}">
                                                ${contentSummary.title?.length()>12?contentSummary.title?.substring(0,10)+"...":contentSummary.title}
                                                </a>
                                            </div>
                                        </li>
                                    </g:each>
                                    %{--<li>--}%
                                        %{--<div class="image">--}%
                                            %{--<a href="http://www.17sucai.com/">--}%
                                                %{--<img src="${resource(dir: "js/image_jiaoben3004/")}images/i.jpg" />--}%
                                            %{--</a>--}%
                                        %{--</div>--}%
                                        %{--<div class="info">--}%
                                            %{--对方的交流--}%
                                        %{--</div>--}%
                                    %{--</li>--}%
                                </ul>
                            </div>
                            <img class="mr_frBtnR next" src="${resource(dir: "css/front/image/")}mfrr.png" />
                        </div>
                    </div>
                </div>
            </div>

            <div class="horizontal_space"></div>
        </div>

        <div class="vertical_space"></div>

        <div class="right">
            <div class="row">
                %{--咨询速递--}%
                <div class="zxsd">
                    <div class="top">
                        <div class="title">${blockMap.get("block"+ContentBlockCode.CODE_ZXSD.code)?.name}</div>
                        <div class="more">
                            <a href="${blockMap.get("block"+ContentBlockCode.CODE_ZXSD.code)?.url}">更多</a>
                        </div>
                    </div>
                    <div class="content">
                        <ul>
                            <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_ZXSD.code)?.contentSummaryList}" var="contentSummary" status="i">
                            <li><a href="${contentSummary.detailUrl}">
                                <span class="title"> ${contentSummary.title?.length()>17?contentSummary.title?.substring(0,15)+"...":contentSummary.title}</span>
                                <span class="time">${FormatUtil.dateFormat(contentSummary.time)}</span>
                            </a></li>
                            </g:each>
                            %{--<li><a href="#">--}%
                                %{--<span class="title">两队交锋两地分居三级地方历史交锋设计</span>--}%
                                %{--<span class="time">2014-09-23</span>--}%
                            %{--</a></li>--}%
                        </ul>
                    </div>
                </div>
            </div>

            <div class="horizontal_space"></div>

            <div class="row">
                %{--通讯录--}%
                <div class="txl">
                    <div class="logo">
                        <img src="${resource(dir: "css/front/image/")}txl_logo.png" />
                    </div>
                    <div class="title">
                        <a href="${blockMap.get("block"+ContentBlockCode.CODE_TXL.code)?.url}">${blockMap.get("block"+ContentBlockCode.CODE_TXL.code)?.name}&nbsp;&nbsp;Contacts</a>
                    </div>
                </div>
            </div>

            <div class="horizontal_space"></div>

            <div class="row">
                %{--我听你说--}%
                <div class="txl">
                    <div class="logo">
                        <img src="${resource(dir: "images/")}wtns_logo.png" />
                    </div>
                    <div class="title">
                        <a href="mailto:${webSiteInfo?.mailAddr}">我听你说</a>
                    </div>
                </div>
            </div>

            <div class="horizontal_space"></div>

            <div class="row">
                %{--专题频道--}%
                <div class="ztpd">
                    <div class="top">
                        <div class="title">${blockMap.get("block"+ContentBlockCode.CODE_ZTPD.code)?.name}</div>
                        <div class="more">
                            <a href="${blockMap.get("block"+ContentBlockCode.CODE_ZTPD.code)?.url}">更多</a>
                        </div>
                    </div>
                    <div class="content">
                        <ul>
                            <g:each in="${blockMap.get("block"+ContentBlockCode.CODE_ZTPD.code)?.contentSummaryList}" var="contentSummary" status="i">
                                <li>
                                    <div class="image">
                                        <img src="${contentSummary.imageUrl}" />
                                    </div>
                                    <div class="info">
                                        <div class="title"><a href="${contentSummary.detailUrl}">${contentSummary.title?.length()>13?contentSummary.title?.substring(0,11)+"...":contentSummary.title}</a></div>
                                        <div class="description">
                                            ${contentSummary.summary?.length()>26?contentSummary.summary?.substring(0,24)+"...":contentSummary.summary}
                                        </div>
                                    </div>
                                </li>
                            </g:each>
                            %{--<li>--}%
                                %{--<div class="image">--}%
                                    %{--<img src="${resource(dir: "js/image_jiaoben828/img",file:"1.jpg")}" />--}%
                                %{--</div>--}%
                                %{--<div class="info">--}%
                                    %{--<div class="title"><a href="#">两队交锋两地分居三级地方历史交锋设计</a></div>--}%
                                    %{--<div class="description">--}%
                                        %{--两队交锋两地分居三级地方历史交锋设计两--}%
                                        %{--队交锋两地分居三级地方历史交锋设计两队交锋两地分居三级地方历史交锋设计两队交锋两地分居三级地方历史交锋设计两队交锋两地分居三级地方历史交锋设计--}%
                                    %{--</div>--}%
                                %{--</div>--}%
                            %{--</li>--}%
                        </ul>
                    </div>
                </div>
            </div>

            <div class="horizontal_space"></div>
        </div>
    </div>
</div>
</div>
</body>
</html>