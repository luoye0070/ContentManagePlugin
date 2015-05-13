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
    <title>内容列表</title>
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
                alert("请选择要删除的内容！");
            });
        });
    </script>
    <link type="text/css" href="${resource(dir: "js/dateTimePicker/css", file: "jquery-ui-1.8.17.custom.css")}" rel="stylesheet" />

    <link type="text/css" href="${resource(dir: "js/dateTimePicker/css", file: "jquery-ui-timepicker-addon.css")}" rel="stylesheet" />

    <script type="text/javascript" src="${resource(dir: "js/dateTimePicker/js", file: "jquery-1.7.1.min.js")}"></script>

    <script type="text/javascript" src="${resource(dir: "js/dateTimePicker/js", file: "jquery-ui-1.8.17.custom.min.js")}"></script>

    <script type="text/javascript" src="${resource(dir: "js/dateTimePicker/js", file: "jquery-ui-timepicker-addon.js")}"></script>

    <script type="text/javascript" src="${resource(dir: "js/dateTimePicker/js", file: "jquery-ui-timepicker-zh-CN.js")}"></script>

    <script type="text/javascript">
        $(function () {
            $("#beginTime").datetimepicker({
                showSecond: true,
                timeFormat: 'hh:mm:ss',
                stepHour: 1,
                stepMinute: 1,
                stepSecond: 1
            });

            $("#endTime").datetimepicker({
                showSecond: true,
                timeFormat: 'hh:mm:ss',
                stepHour: 1,
                stepMinute: 1,
                stepSecond: 1
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

    <!--搜索条件-->
    <div>
        <g:form name="searchForm" action="contentsList" class="form-horizontal">
            <div class="row">
                <div class="control-group span8">
                    <label class="control-label"><g:message code="contentsInfo.contentClassInfo.label" default="Content Class Info" />：</label>
                    <div class="controls">
                        <input type="text" id="show" value="${params?.contentClassName?:"没有类别"}" name="contentClassName"/>
                        <input type="hidden" id="hide" value="${params?.contentClassId?:"0"}" name="contentClassId"/>
                        %{--<g:select id="contentClassInfo" name="contentClassId" from="${lj.picc.data.ContentClassInfo.list()}" optionKey="id"--}%
                                  %{--value="${params?.contentClassId}" class="many-to-one" noSelection="['0': '没有类别']"/>--}%
                    </div>
                </div>
                <div class="control-group span8">
                    <label class="control-label"><g:message code="contentsInfo.title.label" default="Title" />：</label>
                    <div class="controls">
                        <g:textField name="title" maxlength="216" value="${params?.title}"/>
                    </div>
                </div>
                <div class="control-group span8">
                    <label class="control-label"><g:message code="contentsInfo.author.label" default="Author" />：</label>
                    <div class="controls">
                        <g:textField name="author" maxlength="216" value="${params?.author}"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="control-group span12">
                    <label class="control-label"><g:message code="contentsInfo.time.label" default="Time" />：</label>
                    <div class="controls">
                        <g:textField name="beginTime" maxlength="216" value="${params?.beginTime}"/>-
                        <g:textField name="endTime" maxlength="216" value="${params?.endTime}"/>
                    </div>
                </div>
                <div class="control-group span12">
                    <label class="control-label"><g:message code="contentsInfo.content.label" default="Content" />：</label>
                    <div class="controls">
                        <g:textField name="content" style="width: 300px;" maxlength="216" value="${params?.content}"/>
                    </div>
                </div>
            </div>

            <div class="row form-actions actions-bar">
                <div class="span13 offset3 ">
                    <button type="submit" class="button button-primary">
                        ${message(code: 'default.button.search.label', default: 'Search')}
                    </button>
                </div>
            </div>
        </g:form>
    </div>
    <!--内容列表-->
    <g:if test="${contentsInfoList}">
        <div>
            <!--订单列表-->
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <g:sortableColumn property="id" title="${message(code: 'contentsInfo.id.label', default: 'id')}"
                                      params="${params}"/>

                    <th><g:message code="contentsInfo.contentClassInfo.label" default="Content Class Info" /></th>

                    <g:sortableColumn property="title" title="${message(code: 'contentsInfo.title.label', default: 'Title')}"  params="${params}"/>

                    <g:sortableColumn property="time" title="${message(code: 'contentsInfo.time.label', default: 'Time')}"  params="${params}"/>

                    <g:sortableColumn property="author" title="${message(code: 'contentsInfo.author.label', default: 'Author')}"  params="${params}"/>

                    %{--<g:sortableColumn property="content" title="${message(code: 'contentsInfo.content.label', default: 'Content')}"  params="${params}"/>--}%

                    <th>操作</th>
                </tr>
                </thead>
                <g:form name="form1" action="contentsDelete" params="${params}">
                    <tbody>
                    <g:each in="${contentsInfoList}" status="i" var="contentsInfoInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            <td>
                                <input type="checkbox" name="ids" value="${contentsInfoInstance.id}"/>
                            </td>

                            <td>${fieldValue(bean: contentsInfoInstance, field: "contentClassInfo")}</td>

                            <td>
                                %{--<g:link action="contentsShow" id="${contentsInfoInstance.id}">--}%
                                    %{--${fieldValue(bean: contentsInfoInstance, field: "title")}</g:link>--}%
                                <a class="page-action" href="#" data-href="${createLink(controller: "contentsManage",action: "contentsShow",params: [id:contentsInfoInstance.id])}"
                                   title="内容预览-[${contentsInfoInstance?.title}]" data-id="contents_show_menu${contentsInfoInstance.id}">${fieldValue(bean: contentsInfoInstance, field: "title")}</a>
                            </td>

                            <td><g:formatDate date="${contentsInfoInstance.time}" /></td>

                            <td>${fieldValue(bean: contentsInfoInstance, field: "author")}</td>

                            %{--<td>${fieldValue(bean: contentsInfoInstance, field: "content")}</td>--}%

                            <td>
                                %{--<g:link action="editContents" id="${contentsInfoInstance.id}">编辑</g:link>--}%
                                <a class="page-action" href="#" data-href="${createLink(controller: "contentsManage",action: "editContents",params: [id:contentsInfoInstance.id])}"
                                   title="内容编辑-[${contentsInfoInstance?.title}]" data-id="contents_edit_menu${contentsInfoInstance.id}">编辑</a>
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
                    <lj:paginateInBui action="contentsList" total="${totalCount ?: 0}" prev="&larr;" next="&rarr;"
                                      params="${params}"/>
                </div>
            </div>
        </div>
    </g:if>
    <g:else>
        <div class="tips tips-large tips-info tips-no-icon" style="text-align: center">
            <div class="tips-content">
                <h2>还没有内容哦</h2>

                <p class="auxiliary-text">
                    你可以：
                    <a class="page-action" href="#" data-href="${createLink(controller: "contentsManage",action: "editContents")}"
                       title="添加内容" data-id="contents_add_menu">添加内容</a>
                    %{--<a class="direct-lnk" title="添加内容" href="${createLink(controller: "contentsManage",action: "editContents")}">添加内容</a>--}%
                </p>
            </div>
        </div>
    </g:else>
</div>
%{--<script src="http://g.alicdn.com/bui/seajs/2.3.0/sea.js"></script>--}%
%{--<script src="http://g.alicdn.com/bui/bui/1.1.10/config-min.js"></script>--}%
<script type="text/javascript">
    BUI.use(['bui/extensions/treepicker', 'bui/tree'], function (TreePicker, Tree) {
        //树节点数据，
        //text : 文本，
        //id : 节点的id,
        //leaf ：标示是否叶子节点，可以不提供，根据childern,是否为空判断
        //expanded ： 是否默认展开
        var data = ${cciTreeStr},
        //由于这个树，不显示根节点，所以可以不指定根节点
                tree = new Tree.TreeList({
                    nodes: data,
                    //checkType : 'all',
                    showLine: true //显示连接线
                });
        var picker = new TreePicker({
            trigger: '#show',
            valueField: '#hide', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
            //selectStatus : 'checked',//设置勾选作为状态改变的事件
            filter: function (node) { //过滤非叶子节点
                return node.leaf;
            },
            width: 150,  //指定宽度
            children: [tree] //配置picker内的列表
        });
        picker.render();
    });
</script>
</body>
</html>