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
    <title>内容类别列表</title>
    <script type="text/javascript">
        $(function(){
            $("#allCheck").on("change",function(event){
                if($(this).attr("checked")){
                    //alert("checked");
                    $("input[name='ids']").attr("checked",true);
                }else{
                    //alert("unchecked");
                    $("input[name='ids']").attr("checked",false);
                }
            });
            $("#deleteBt").click(function(event){
                //alert("delete");
                for(i=0;i<$("input[name='ids']").length;i++){
                    if($($("input[name='ids']")[i]).attr("checked")){
                        if(confirm("确定要删除吗？")){
                            $("#form1").submit();
                        }
                        return;
                    }
                }
                alert("请选择要删除的内容类别！");
            });
        });
    </script>
</head>

<body>
<!--列表-->
<div style="margin: 10px;">
    %{--<div class="form-links">--}%
        %{--<ul class="nav-tabs">--}%
            %{--<li class="${(lj.Number.toInteger(params.type)==0)?"active":""}"><a href="${createLink(controller: "resourceFile",action: "list",params: [type:0])}">全部</a></li>--}%
            %{--<g:each in="${lj.enumCustom.ResourceType.types}" var="resourceType">--}%
                %{--<li class="${(lj.Number.toInteger(params.type)==resourceType.code)?"active":""}"><a href="${createLink(controller: "resourceFile",action: "list",params: [type:resourceType.code])}">${resourceType.label}</a></li>--}%
            %{--</g:each>--}%
        %{--</ul>--}%
    %{--</div>--}%
    <g:if test="${contentClassInfoList}">
        <div>
            <!--订单列表-->
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <g:sortableColumn property="id" title="${message(code: 'contentClassInfo.id.label', default: 'id')}"
                                      params="${params}"/>
                    <g:sortableColumn property="name" title="${message(code: 'contentClassInfo.name.label', default: 'Name')}" params="${params}"/>

                    <g:sortableColumn property="description" title="${message(code: 'contentClassInfo.description.label', default: 'Description')}" params="${params}"/>

                    <th><g:message code="contentClassInfo.parent.label" default="Parent" params="${params}"/></th>

                    <g:sortableColumn property="onMenu" title="${message(code: 'contentClassInfo.onMenu.label', default: 'On Menu')}" params="${params}"/>

                    <th>操作</th>
                </tr>
                </thead>
                <g:form name="form1" action="contentClassDelete" params="${params}">
                    <tbody>
                    <g:each in="${contentClassInfoList}" status="i" var="contentClassInfoInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            <td>
                                <input type="checkbox" name="ids" value="${contentClassInfoInstance.id}"/>
                            </td>
                            <td>${fieldValue(bean: contentClassInfoInstance, field: "name")}</td>

                            <td>${fieldValue(bean: contentClassInfoInstance, field: "description")}</td>

                            <td>${fieldValue(bean: contentClassInfoInstance, field: "parent")}</td>

                            <td><g:formatBoolean boolean="${contentClassInfoInstance.onMenu}" /></td>
                            <td>
                                <a class="page-action" href="#" data-href="${createLink(controller: "contentsManage",action: "editContentClass",params: [id:contentClassInfoInstance.id])}" title="类别编辑-[${contentClassInfoInstance.name}]" data-id="contentClass_edit_menu${contentClassInfoInstance.id}">编辑</a>
                                %{--<g:link action="editContentClass" id="${contentClassInfoInstance.id}">编辑</g:link>--}%
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </g:form>
            </table>

            <div>
                <ul class="toolbar pull-left">
                    <li><label class="checkbox"><input type="checkbox" id="allCheck"><a href="#">全选</a></label></li>
                    <li><button class="button button-danger" id="deleteBt"><i class="icon-white icon-trash"></i>批量删除</button></li>
                </ul>

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
                <h2>还没有内容类别哦</h2>

                <p class="auxiliary-text">
                    你可以：
                    <a class="page-action" href="#" data-href="${createLink(controller: "contentsManage",action: "editContentClass")}" title="添加内容类别" data-id="contentClass_add_menu">添加内容类别</a>
                    %{--<a class="direct-lnk" title="添加内容类别" href="${createLink(controller: "contentsManage",action: "editContentClass")}">添加内容类别</a>--}%
                </p>
            </div>
        </div>
    </g:else>
</div>
</body>
</html>