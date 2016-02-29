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
    <meta name="layout" content="cmp_manageMain"/>
    <title>内容类别编辑</title>
    <script type="text/javascript">
     $(function(){
         <g:if test="${isSaveOk}">
         if(top.topManager){
             //打开左侧菜单中配置过的页面
             top.topManager.reloadPage("contentClass_list_menu");
         }
         </g:if>
     });
    </script>
</head>
<body>
<div style="margin: 10px;">
%{--<div style="margin: 10px;" class="row show-grid">--}%
    %{--<div class="span4">&nbsp;</div>--}%
    %{--<div class="span24">--}%
    <div class="">
        <g:form action="editContentClass" name="form1" method="post" class="form-horizontal">
            <input type="hidden" name="id" value="${contentClassInfoInstance?.id}"/>
            <div class="control-group">
                <label class="control-label"><g:message code="contentClassInfo.name.label" default="Name" />：</label>
                <div class="controls">
                    <g:textField style="width: 280px;" name="name" maxlength="64" required="" value="${contentClassInfoInstance?.name}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="contentClassInfo.description.label" default="Description" />：</label>
                <div class="controls control-row4">
                    <g:textArea style="width: 280px;" name="description" cols="40" rows="4" maxlength="512" value="${contentClassInfoInstance?.description}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="contentClassInfo.parent.label" default="Parent" />：</label>
                <div class="controls">
                    <g:select style="width: 280px;" id="parent" name="parentId" from="${contentClassListWithoutCurrent}" optionKey="id" value="${contentClassInfoInstance?.parent?.id}" class="many-to-one" noSelection="['0': '没有上级类别']"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="contentClassInfo.onMenu.label" default="On Menu" />：</label>
                <div class="controls">
                    <g:checkBox name="onMenu" value="${contentClassInfoInstance?.onMenu}" />
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">&nbsp;</label>
                <div class="controls">
                    <button type="submit" class="button">
                        ${message(code: 'default.button.save.label', default: 'Save')}
                    </button>
                </div>
            </div>

        </g:form>
    </div>
    %{--<div class="span4">&nbsp;</div>--}%
</div>
</body>
</html>