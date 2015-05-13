<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="cmp_front"/>
    <title>${contentBlockInfo?.name}${contentClassInfo?.name}</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'wg.css')}" type="text/css">
    <style>
    .pages{ width:100%; text-align:center; padding:10px 0; clear:both;}
    .pages span,.pages a,.pages b{ font-size:12px; font-family:Arial, Helvetica,
    sans-serif; margin:0 2px;}
    .pages span font{ color:#f00; font-size:12px;}
    .pages a,.pages b{ border:1px solid #5FA623; background:#fff; padding:2px
    6px; text-decoration:none}
    .pages span { padding-right:10px }
    .pages b,.pages a:hover{ background:#7AB63F; color:#fff;}
    </style>
</head>

<body>
<div class="content">
    <div class="detail_middle">
        <div class="detail">
            <g:if test="${leftNavItems}">
                <div class="left">
                <ul>
                    <li style=" line-height: 50px;color:red;text-align: left;margin:0;padding:0 0 0 15px;float:left;border-top:none;border-bottom: 2px solid red;height:50px;width:100px;font-size: 16px;font-weight: bold">
                        ${contentBlockInfo?.name}${contentClassInfo?.name}
                    </li>

                    <div class="clear"></div>
                    <g:each in="${leftNavItems}" var="navItem">
                        <li>
                            <a href="${navItem.url}">${navItem.name}</a>
                        </li>
                    </g:each>
                </ul>
            </div>
            </g:if>

            <div class="right" ${leftNavItems?"":"style='width:948px;'"}>
                <div class="nav" style="padding-left: 0px;">
                    <a href="${request.getContextPath()}" style="float: left;margin-right: 15px;">&nbsp;&nbsp;</a>
                    %{--<g:if test="${topNavItems}">--}%
                    <img width="2" height="13" class='first_img' src="../images/shuline.jpg">
                    <ul>

                        <g:each in="${topNavItems}" var="navItem">
                            <li>
                                <a href="${navItem.url}">${navItem.name}</a>  <img width="7" height="11" class='middle_img'
                                                           src="../images/jiantou.png">
                            </li>
                        </g:each>

                        <li>
                            <span class='last_nav'>
                                <g:if test="${contentBlockInfo?.name||contentClassInfo?.name}">
                                    ${contentBlockInfo?.name}${contentClassInfo?.name}
                                </g:if>
                                <g:else>
                                    内容搜索
                                </g:else>
                            </span>
                        </li>
                    </ul>
                    %{--</g:if>--}%
                </div>

                <p class="clear"></p>

                <div class='importan_news'>
                    <ul>
                        <g:each in="${contentSummaryList}" var="contentSummary" status="i">
                            %{--<g:if test="${contentSummary.imageUrl&&contentSummary.summary&&i==0}">--}%
                                %{--<li class="import_img_tip">--}%
                                    %{--<div class="import_title">--}%
                                        %{--<span class="fr" style="margin-right:40px;">--}%
                                            %{--${lj.FormatUtil.dateFormat(contentSummary.time)}--}%
                                        %{--</span>--}%
                                        %{--<a href="${contentSummary.detailUrl}">${contentSummary.title}</a>--}%
                                    %{--</div>--}%
                                    %{--<div class="important_detail">--}%
                                        %{--<a class="important_pic fl"--}%
                                           %{--href="${contentSummary.detailUrl}">--}%
                                            %{--<img alt="" src="${contentSummary.imageUrl}">--}%
                                        %{--</a>--}%
                                        %{--<span class="important_artical fl">--}%
                                            %{--${contentSummary.summary}--}%
                                            %{--<div style="float:right;">--}%
                                                %{--<a href="${contentSummary.detailUrl}">详情>></a>--}%
                                            %{--</div>--}%
                                        %{--</span>--}%

                                        %{--<p class="clear"></p>--}%
                                    %{--</div>--}%
                                %{--</li>--}%
                            %{--</g:if>--}%
                            %{--<g:else>--}%
                            <li class="important_tip">
                                <span class="fr">${lj.FormatUtil.dateFormat(contentSummary.time)}</span>
                                <a href="${contentSummary.detailUrl}">${contentSummary.title}</a>
                            </li>
                            %{--</g:else>--}%
                        </g:each>
                    </ul>
                    <lj:paginateFront total="${totalCount}" action="newsList" params="${params}"/>
                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>