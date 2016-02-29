<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-3-26
  Time: 上午12:05
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.lj.cmp.util.customenum.ResourceType; com.lj.cmp.util.common.FormatUtil" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="cmp_manageMain"/>
    <title>内容块列表</title>
    <script type="text/javascript">
        $(function(){
//            $("#allCheck").on("change",function(event){
//                if($(this).attr("checked")){
//                    //alert("checked");
//                    $("input[name='ids']").attr("checked",true);
//                }else{
//                    //alert("unchecked");
//                    $("input[name='ids']").attr("checked",false);
//                }
//            });
//            $("#deleteBt").click(function(event){
//                //alert("delete");
//                for(i=0;i<$("input[name='ids']").length;i++){
//                    if($($("input[name='ids']")[i]).attr("checked")){
//                        if(confirm("确定要删除吗？")){
//                            $("#form1").submit();
//                        }
//                        return;
//                    }
//                }
//                alert("请选择要删除的内容类别！");
//            });
        });
    </script>
</head>

<body>
<!--列表-->
<div style="margin: 10px;">
    <g:if test="${contentBlockInfoList}">
        <div>
            <!--订单列表-->
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    %{--<th><g:message code="contentBlockInfo.contentsInfo.label" default="Contents Info" /></th>--}%

                    <g:sortableColumn property="name" title="${message(code: 'contentBlockInfo.name.label', default: 'Name')}" />

                    %{--<g:sortableColumn property="code" title="${message(code: 'contentBlockInfo.code.label', default: 'Code')}" />--}%

                    %{--<g:sortableColumn property="logoUrl" title="${message(code: 'contentBlockInfo.logoUrl.label', default: 'Logo Url')}" />--}%

                    <g:sortableColumn property="inMenu" title="${message(code: 'contentBlockInfo.inMenu.label', default: 'In Menu')}" />

                    <g:sortableColumn property="maxCount" title="${message(code: 'contentBlockInfo.maxCount.label', default: 'Max Count')}" />

                    <th>操作</th>
                </tr>
                </thead>
                <g:form name="form1" action="contentClassDelete" params="${params}">
                    <tbody>
                    <g:each in="${contentBlockInfoList}" status="i" var="contentBlockInfoInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            %{--<td><g:link action="show" id="${contentBlockInfoInstance.id}">${fieldValue(bean: contentBlockInfoInstance, field: "contentsInfo")}</g:link></td>--}%

                            <td>${fieldValue(bean: contentBlockInfoInstance, field: "name")}</td>

                            %{--<td>${fieldValue(bean: contentBlockInfoInstance, field: "code")}</td>--}%

                            %{--<td>${fieldValue(bean: contentBlockInfoInstance, field: "logoUrl")}</td>--}%

                            <td><g:formatBoolean boolean="${contentBlockInfoInstance.inMenu}" /></td>

                            <td>${fieldValue(bean: contentBlockInfoInstance, field: "maxCount")}</td>

                            <td>
                                <a class="page-action" href="#" data-href="${createLink(controller: "siteManage",action: "editContentBlock",params: [id:contentBlockInfoInstance.id])}" title="内容块编辑-[${contentBlockInfoInstance.name}]" data-id="contentBlock_edit_menu${contentBlockInfoInstance.id}">编辑</a>
                                %{--<g:link action="editContentBlock" id="${contentBlockInfoInstance.id}">编辑</g:link>--}%
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </g:form>
            </table>

            <div>
                %{--<ul class="toolbar pull-left">--}%
                    %{--<li><label class="checkbox"><input type="checkbox" id="allCheck"><a href="#">全选</a></label></li>--}%
                    %{--<li><button class="button button-danger" id="deleteBt"><i class="icon-white icon-trash"></i>批量删除</button></li>--}%
                %{--</ul>--}%

                <div class="pagination pull-right">
                    <!--分页-->
                    <lj:paginateInBui action="contentClassList" total="${totalCount ?: 0}" prev="&larr;" next="&rarr;"
                                      params="${params}"/>
                </div>
            </div>
        </div>
    </g:if>
    <g:else>
        <div class="tips tips-large tips-info tips-no-icon" style="text-align: center">
            <div class="tips-content">
                <h2>还没有内容块哦</h2>

                %{--<p class="auxiliary-text">--}%
                    %{--你可以：--}%
                    %{--<a class="page-action" href="#" data-href="${createLink(controller: "contentsManage",action: "editContentClass")}" title="添加内容类别" data-id="contentClass_add_menu">添加内容类别</a>--}%
                    %{--<a class="direct-lnk" title="添加内容类别" href="${createLink(controller: "contentsManage",action: "editContentClass")}">添加内容类别</a>--}%
                %{--</p>--}%
            </div>
        </div>
    </g:else>
</div>
</body>
</html>