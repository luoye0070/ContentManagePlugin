<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <link rel="Shortcut Icon" href="${resource(dir: 'images', file: 'favicon_picc.ico')}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.8.1.min.js')}"></script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'front.css')}" type="text/css">
    <script language="javascript">



        function doZoom(obj, size, lineheight) {
            $(".information_bor").removeClass("information_bor");
            $(obj).addClass("information_bor");
            $("#news_txt").css("font-size", size);
            $("#news_txt p").css("font-size", size);
            $("#news_txt p").css("line-height", lineheight + "px");
        }

        var tabIndex;

        function SelectMenu(index) {

            for (i = 1; i <= 9; i++)
            {
                if (i == index)
                    continue;
                if (document.getElementById("sub_menu_" + i) && document.getElementById("sub_menu_" + i).style.display != "none")
                    document.getElementById("sub_menu_" + i).style.display = "none";

                if(i == tabIndex)
                    continue;

                if (document.getElementById("menu_" + i) && document.getElementById("menu_" + i).className != "mainmenu")
                    document.getElementById("menu_" + i).className = "mainmenu";
            }

            if (document.getElementById("sub_menu_" + index))
                document.getElementById("sub_menu_" + index).style.display = "block";
            else //if(tabIndex == 0)
                document.getElementById("sub_menu_1").style.display = "block";

            if (document.getElementById("menu_" + index) && document.getElementById("menu_" + index).className != "CurMenu") {
                document.getElementById("menu_" + index).className = "CurMenu";
            }
        }

        function HideAllMenu()
        {
            for (i = 1; i <= 9; i++) {
                if (i == tabIndex)
                    continue;
                if (document.getElementById("sub_menu_" + i) && document.getElementById("sub_menu_" + i).style.display == "block")
                    document.getElementById("sub_menu_" + i).style.display = "none";
                if (document.getElementById("menu_" + i) && document.getElementById("menu_" + i).className != "mainmenu")
                    document.getElementById("menu_" + i).className = "mainmenu";
            }
            if (document.getElementById("sub_menu_" + tabIndex))
                document.getElementById("sub_menu_" + tabIndex).style.display = "block";
            else
                document.getElementById("sub_menu_1").style.display = "block";

            if (document.getElementById("menu_" + tabIndex) && document.getElementById("menu_" + tabIndex).className != "CurMenu")
                document.getElementById("menu_" + tabIndex).className = "CurMenu";
        }

        function HideMenu(e, subMenuElementID)
        {
            if(!isMouseToSubMenu(e, subMenuElementID))
                HideAllMenu();
        }

        function HideSubMenu(e, handler)
        {
            if(isMouseLeaveOrEnter(e, handler))
            {
                HideAllMenu();
            }
        }


        function isMouseLeaveOrEnter(e, handler)
        {
            if (e.type != 'mouseout' && e.type != 'mouseover') return false;
            var reltg = e.relatedTarget ? e.relatedTarget : e.type == 'mouseout' ? e.toElement : e.fromElement;
            while (reltg && reltg != handler)
                reltg = reltg.parentNode;
            return (reltg != handler);
        }

        function isMouseToSubMenu(e, subMenuElementID)
        {
            if (e.type != 'mouseout')
                return false;
            var reltg = e.relatedTarget ? e.relatedTarget : e.toElement;
            while(reltg && reltg.id != subMenuElementID)
                reltg = reltg.parentNode;

            return reltg;
        }

    </script>
    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body class="wg">
<div class="logo" style="background-image:url('${webSiteInfo?.logoUrl}');background-repeat:no-repeat; ">
    <div class="logo_left">
        %{--<img src='${resource(dir:"images",file:"picc.jpg")}'/>--}%
        %{--<span class="line"></span>--}%
        <span class="text">${webSiteInfo?.siteName}</span>
    </div>
    <div class="logo_right">
        %{--<img src='${webSiteInfo?.logoUrl}' height="73"/>--}%
    </div>
</div>

<div class="clear"></div>

<div class="menubg">
    <div class="main_menu">
        <ul>
            <li class="MenuLeftspace">&nbsp;</li>

            <li class="MenuLi" onmouseout="HideMenu(event, 'subMenuItems')" onmouseover="SelectMenu(1)">
                <a href="${request.getContextPath()}" id="menu_1" class="${menuMark=="home"?"CurMenu":"mainmenu"}">主 页</a>
            </li>

            <script> tabIndex = 0;</script>
            <g:if test="${menuMark=="home"}">
                <script> tabIndex = 1;</script>
            </g:if>

            <g:each in="${menuItems}" var="menuItem" status="i">
                <g:if test="${i<8}">
                    %{--${menuMark}-${menuItem.menuMark}--}%
                    <g:if test="${menuMark+""==menuItem.menuMark+""}">
                        <script> tabIndex = ${i+2};</script>
                    </g:if>
                <li>
                    <div class="mainMenu_Spacer">
                    </div>
                </li>

                <li class="MenuLi" onmouseout="HideMenu(event, 'subMenuItems')" onmouseover="SelectMenu(${i+2})" >
                    <a class="${menuMark==menuItem.menuMark+""?"CurMenu":"mainmenu"}" id="menu_${i+2}" href="${menuItem.url}"  >${menuItem.name}</a>
                </li>
                </g:if>
            </g:each>

            %{--<li>--}%
                %{--<div class="mainMenu_Spacer">--}%
                %{--</div>--}%
            %{--</li>--}%

            %{--<li class="MenuLi" onmouseout="HideMenu(event, 'subMenuItems')" ><a class="mainmenu" href="/personal"  >工作部简介</a></li>--}%

            %{--<li>--}%
                %{--<div class="mainMenu_Spacer">--}%
                %{--</div>--}%
            %{--</li>--}%

            %{--<li class="MenuLi" onmouseout="HideMenu(event, 'subMenuItems')" onmouseover="SelectMenu(3)"><a class="mainmenu" href="/corporate"  id="menu_3" >天龙八部</a></li>--}%

            %{--<li>--}%
                %{--<div class="mainMenu_Spacer">--}%
                %{--</div>--}%
            %{--</li>--}%

            %{--<li class="MenuLi" onmouseout="HideMenu(event, 'subMenuItems')" ><a class="mainmenu" href="/corporate/sme" >一线天地</a></li>--}%

            %{--<li>--}%
                %{--<div class="mainMenu_Spacer">--}%
                %{--</div>--}%
            %{--</li>--}%
            %{--<li class="MenuLi" onmouseout="HideMenu(event, 'subMenuItems')" ><a class="mainmenu" href="http://cc.cmbchina.com" >志工部落</a></li>--}%
            %{--<li>--}%
                %{--<div class="mainMenu_Spacer">--}%
                %{--</div>--}%
            %{--</li>--}%
            %{--<li class="MenuLi"><a class="mainmenu" href="http://i.cmbchina.com">英雄榜</a></li>--}%
            %{--<li>--}%
                %{--<div class="mainMenu_Spacer">--}%
                %{--</div>--}%
            %{--</li>--}%
            %{--<li class="MenuLi"><a class="mainmenu" href="http://trip.cmbchina.com" >我听你说</a></li>--}%
            %{--<li>--}%
                %{--<div class="mainMenu_Spacer">--}%
                %{--</div>--}%
            %{--</li>--}%
        </ul>
    </div>

</div>
<div class="submenubg" id="subMenuItems" onmouseout="HideSubMenu(event, this)">
    <div class="sub_menu" id="sub_menu_1">

        <ul>
            <li>
                <p class="submenu_title">
                    快速导航：</p>
            </li>
            <g:each in="${navItems}" var="navItem">
                <li><a href="${navItem.url}" class="submenu" >${navItem.name}</a> |</li>
            </g:each>
            %{--<li><a href="http://gongyi.cmbchina.com/Client/CommonWealActs.aspx?index=4" target="_blank" class="submenu" >导航1</a> |</li>--}%
            %{--<li><a href="http://e.cmbchina.com/" target="_blank" class="submenu" >导航2</a> |</li>--}%
        </ul>

    </div>

    <g:each  in="${menuItems}" var="menuItem" status="i">
        <g:if test="${i<8}">
        <g:if test="${menuItem.children}">
            <div class="sub_menu" id="sub_menu_${i+2}" style="display: none">
                <ul>
                    <g:each in="${menuItem.children}" var="childItem" status="j">
                        <li>
                            <a href="${childItem.url}" class="submenu" >${childItem.name}</a> |</li>
                        <li>
                    </g:each>
                </ul>
            </div>
        </g:if>
        </g:if>
    </g:each>

    %{--<div class="sub_menu" id="sub_menu_3" style="display: none">--}%

        %{--<ul>--}%

            %{--<li>--}%
                %{--<a href="" class="submenu" >幸福关怀智囊部</a> |</li>--}%
            %{--<li>--}%
            %{--<li>--}%
                %{--<a href="" class="submenu" >幸福文化传承部</a> |</li>--}%
            %{--<li>--}%
            %{--<li>--}%
                %{--<a href="" class="submenu" >幸福标兵指挥部</a> |</li>--}%
            %{--<li>--}%
            %{--<li>--}%
                %{--<a href="" class="submenu" >幸福宣教电视台</a> |</li>--}%
            %{--<li>--}%
            %{--<li>--}%
                %{--<a href="" class="submenu" >幸福公益奉献部</a> |</li>--}%
            %{--<li>--}%
            %{--<li>--}%
                %{--<a href="" class="submenu" >幸福活动俱乐部</a> |</li>--}%
            %{--<li>--}%
            %{--<li>--}%
                %{--<a href="" class="submenu" >幸福之家管理部</a> |</li>--}%
            %{--<li>--}%
            %{--<li>--}%
                %{--<a href="" class="submenu" >幸福志工档案部</a> |</li>--}%
            %{--<li>--}%

        %{--</ul>--}%

    %{--</div>--}%

</div>
<div class="submenubottom">
</div>
</div>

<script type="text/javascript">
    SelectMenu(tabIndex);
</script>


<g:layoutBody/>





<div class="clear"></div>

<div class="bottom_list">
    <div class="bottom_content">
        %{--<ul class="bottom_content_first">--}%
            %{--<li class="bottom_color" style="width:200px;">关于中国人民保险寿险</li>--}%
            %{--<li>--}%
                %{--<a href="/corporationStatus/index.jhtml">公司简介</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/aboutUsBranch.jhtml">分支机构</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/organization/index.jhtml">组织架构</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/corporateCulture/index.jhtml">企业文化</a>--}%
            %{--</li>--}%
        %{--</ul>--}%
        %{--<ul class="bottom_content_fl">--}%
            %{--<li class="bottom_color">销售支持系统</li>--}%
            %{--<li>--}%
                %{--<a href="/procurementNotice/index.jhtml">集中采购</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/insuranceAgent/index.jhtml">代理人专区</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="#">团体营销支持</a>--}%
            %{--</li>--}%
        %{--</ul>--}%
        %{--<ul class="bottom_content_fr">--}%
            %{--<li class="bottom_color">--}%
                %{--<a href="/newMessage/index.jhtml" style="font-size:14px;color:#333;">加入我们</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/campusRecruitment/index.jhtml">校园招聘</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/openRecruitment/index.jhtml">社会招聘</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/branchRecruitment/index.jhtml">分公司招聘</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/onlineJob/index.jhtml">在线投递简历</a>--}%
            %{--</li>--}%
        %{--</ul>--}%
        %{--<ul class="bottom_content_fr">--}%
            %{--<li class="bottom_color">友情链接</li>--}%
            %{--<li>--}%
                %{--<a href="/siteMap/index.jhtml">网站地图</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/cooperateOrganization/index.jhtml">合作机构</a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<a href="/fellowLink/index.jhtml">相关链接</a>--}%
            %{--</li>--}%
        %{--</ul>--}%
        <ul>
            <li class="bottom_color" style="font-weight: bolder;">友情链接：</li>
            <g:each in="${webSiteInfo?.linkInfoList}" var="linkInfo">
                <li>
                    <a target="_blank" href="${linkInfo?.url}">${linkInfo?.name}</a>
                </li>
            </g:each>
            %{--<li>--}%
                %{--<a href="/fellowLink/index.jhtml">相关链接</a>--}%
            %{--</li>--}%
        </ul>
    </div>
</div>

<div class="clear"></div>

<div class="bottom_log">
    <div style="width:100%;text-align:center;height:100%;color: #333;line-height: 48px;">
        ${webSiteInfo?.rightInfo}
    </div>
</div>

<div id="goTopBtn" class="hideTopBtnDiv" style="left: 1275px; bottom: 60px; display: block;">
    <img border="0" src="${resource(dir:"images",file:"scrollTop.png")}">
</div>
<script type="text/javascript">
    function b() {
        t = $(document).scrollTop();
        if (t > 0) {
            $('#goTopBtn').fadeIn('fast');
        } else {
            $('#goTopBtn').fadeOut('slow');
        }
    }
    function a(x, y) {
        left = $('.content').offset().left;
        width = $('.content').width();
        if (left < 35) {
            $('#goTopBtn').removeAttr("style");
            $('#goTopBtn').css('right', 4 + 'px');
            $('#ShowAD').removeAttr("style");
            $('#ShowAD').css('right', 4 + 'px');
        } else {
            $('#goTopBtn').removeAttr("style");
            $('#goTopBtn').css('left', (left + width + x) + 'px');
            $('#ShowAD').removeAttr("style");
            $('#ShowAD').css('left', (left + width + x) + 'px');
        }
        $('#goTopBtn').css('bottom', y + 'px');
    }
    $(document).ready(function (e) {
        a(0, 60);//#tbox的div距浏览器底部和页面内容区域右侧的距离
        b();
        $('#goTopBtn').click(function () {
            $(document).scrollTop(0);
        })
    });
    $(window).resize(function () {
        a(0, 60);//#tbox的div距浏览器底部和页面内容区域右侧的距离
        b();
    });
    $(window).scroll(function (e) {
        b();
    })
</script>
<r:layoutResources/>
</body>
</html>
