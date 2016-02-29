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
    <title>内容编辑</title>
    <script type="text/javascript"
            src="${resource(dir: "js/kindeditor-4.1.10", file: "kindeditor-all-min.js")}"></script>
    <script type="text/javascript" src="${resource(dir: "js/kindeditor-4.1.10/lang", file: "zh_CN.js")}"></script>
    <script type="text/javascript">
        var uploadImageEditor;
        KindEditor.ready(function (K) {
            window.editor = K.create('#content_editor', {
                uploadJson: '${createLink(controller: "resourceFile",action: "uploadInEditor")}',
                fileManagerJson: '${createLink(controller: "resourceFile",action: "manageInEditor")}',
                allowFileManager: true,
                afterBlur: function () {
                    this.sync();
                },
                langType: 'zh_CN'
            });
            uploadImageEditor = K.editor({
                uploadJson: '${createLink(controller: "resourceFile",action: "uploadInEditor")}',
                fileManagerJson: '${createLink(controller: "resourceFile",action: "manageInEditor")}',
                allowFileManager : true
            });
        });
        function uploadImage(obj) {
            uploadImageEditor.loadPlugin('image', function() {
                uploadImageEditor.plugin.imageDialog({
                    imageUrl : $(obj).parent().find("input[data='imgUrl']").val(),
                    clickFn : function(url, title, width, height, border, align) {
                        $(obj).parent().find("input[data='imgUrl']").val(url);
                        uploadImageEditor.hideDialog();
                    }
                });
            });
        }
    </script>
    <script type="text/javascript">
        $(function () {
            <g:if test="${isSaveOk}">
            if (top.topManager) {
                //打开左侧菜单中配置过的页面
                top.topManager.reloadPage("contentBlock_list_menu");
            }
            </g:if>
        });
        function del(obj){
            $(obj).parent().remove();
        }
    </script>
</head>

<body>
<div style="margin: 10px;">
    %{--<div style="margin: 10px;" class="row show-grid">--}%
    %{--<div class="span4">&nbsp;</div>--}%
    %{--<div class="span24">--}%
    <div class="">
        <g:form action="editContentBlock" name="form1" method="post" class="form-horizontal">
            <input type="hidden" name="id" value="${contentBlockInfoInstance?.id}"/>
        %{--<input type="hidden" name="contentClassId" value="${contentsInfoInstance?.contentClassInfo?.id}"/>--}%
            <div class="control-group">
                <label class="control-label"><g:message code="contentBlockInfo.name.label" default="Name"/>：</label>

                <div class="controls">
                    <g:textField name="name" value="${contentBlockInfoInstance?.name}"/>
                </div>
            </div>

            <input type="hidden" name="code" value="${contentBlockInfoInstance?.code}"/>
            %{--<div class="control-group">--}%
                %{--<label class="control-label"><g:message code="contentBlockInfo.code.label" default="Code"/>：</label>--}%

                %{--<div class="controls">--}%
                    %{--<g:field name="code" type="number" value="${contentBlockInfoInstance?.code}" required=""--}%
                             %{--readonly="true"/>--}%
                %{--</div>--}%
            %{--</div>--}%

            %{--<div class="control-group">--}%
                %{--<label class="control-label"><g:message code="contentBlockInfo.logoUrl.label"--}%
                                                        %{--default="Logo Url"/>：</label>--}%

                %{--<div class="controls control-row-auto">--}%
                    %{--<g:textField name="logoUrl" style="width: 500px;" data='imgUrl' value="${contentBlockInfoInstance?.logoUrl}"/>&nbsp;--}%
                    %{--<input type="button" onclick="uploadImage(this)" value="上传图片"/>--}%
                %{--</div>--}%
            %{--</div>--}%

            <div class="control-group">
                <label class="control-label"><g:message code="contentBlockInfo.inMenu.label"
                                                        default="In Menu"/>：</label>

                <div class="controls control-row-auto">
                    <g:checkBox name="inMenu" value="${contentBlockInfoInstance?.inMenu}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="contentBlockInfo.inNav.label"
                                                        default="In Nav"/>：</label>

                <div class="controls control-row-auto">
                    <g:checkBox name="inNav" value="${contentBlockInfoInstance?.inNav}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="contentBlockInfo.maxCount.label" default="Max Count" />：</label>

                <div class="controls control-row-auto">
                    <g:field name="maxCount" type="number" value="${contentBlockInfoInstance.maxCount}" required=""/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">
                    <g:message code="contentBlockInfo.contentsInfo.label" default="Contents Info"/>：
                </label>

                <div class="controls control-row-auto">
                    <input type="text" id="contentsInfoTitle" style="width: 500px;" value="${contentBlockInfoInstance?.contentsInfo?.title}" readonly="true"/>
                    <input type="hidden" id="contentsInfoId" name="contentsId" value="${contentBlockInfoInstance?.contentsInfo?.id}"/>
                    <input type="button" id="contentsInfoSelect" value="选择"/>
                    <input type="button" id="contentsInfoClear" value="清空"/>
                    %{--<g:select id="contentsInfo" name="contentsId" from="${lj.picc.data.ContentsInfo.list()}"--}%
                              %{--optionKey="id" value="${contentBlockInfoInstance?.contentsInfo?.id}" class="many-to-one"--}%
                              %{--noSelection="['0': '']"/>--}%
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="contentBlockInfo.contentsInfoList.label"
                                                        default="Contents Info List"/>：</label>
                <div class="controls control-row-auto">
                    <div style="border:1px #cccccc solid;padding: 5px;">
                    <div id="contentsInfos">
                        <g:if test="${contentBlockInfoInstance?.contentsInfoList}">
                            <g:each in="${contentBlockInfoInstance?.contentsInfoList}" var="contentsInfoInstance">
                                <div>
                                    <input type="hidden" name="contentsInfoIds" value="${contentsInfoInstance.id?:0}"/>
                                    <input type="text" style="width: 500px;" name="contentsInfoTitles" value="${contentsInfoInstance.title?:""}" readonly="true"/>&nbsp;&nbsp;
                                    <input type="button" onclick="del(this)" value="删除"/>
                                    <br/> <br/>
                                </div>
                            </g:each>
                        </g:if>
                    </div>
                    <input type="button" id="contentsInfosSelect" value="添加"/>
                    </div>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">
                    <g:message code="contentBlockInfo.contentClassInfoList.label" default="Content Class Info List"/>
                </label>

                <div class="controls control-row-auto">
                    <input type="text" id="show" value="${cciNamesStr}"/>
                    <input type="hidden" id="hide" value="${cciIdsStr}" name="contentClassIds"/>
                    %{--<g:select name="contentClassInfoList" from="${lj.picc.data.ContentClassInfo.list()}" multiple="multiple" optionKey="id" size="5" value="${contentBlockInfoInstance?.contentClassInfoList*.id}" class="many-to-many"/>--}%
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
                    checkType: 'all',
                    showLine: true //显示连接线
                });
        var picker = new TreePicker({
            trigger: '#show',
            valueField: '#hide', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
            selectStatus: 'checked',//设置勾选作为状态改变的事件
//            filter: function (node) { //过滤非叶子节点
//                return node.leaf;
//            },
            width: 150,  //指定宽度
            children: [tree] //配置picker内的列表
        });
        picker.render();
    });
</script>
<script type="text/javascript">
    var contentsInfoTitle='${contentBlockInfoInstance?.contentsInfo?.title}';
    var contentsInfoId='${contentBlockInfoInstance?.contentsInfo?.id?:0}';
    var selectContentsInfo=function(title,id){
        contentsInfoTitle=title;
        contentsInfoId=id;
    }
    $("#contentsInfoClear").click(function(){
        contentsInfoTitle="";
        contentsInfoId="0";
        $("#contentsInfoTitle").val("");
        $("#contentsInfoId").val("0");
    });

    var selectContentsInfos=function(title,id){
        //查询是否已经存在
        for(i=0;i<$("input[name='contentsInfoIds']").length;i++){
            if($($("input[name='contentsInfoIds']")[i]).val()==id){
                return;
            }
        }
        var htmlStr='<div>'+
                        '<input type="hidden" name="contentsInfoIds" value="'+id+'"/>'+
                        '<input type="text" style="width: 500px;" name="contentsInfoTitles" value="'+title+'" readonly="true"/>&nbsp;&nbsp;'+
                        '<input type="button" onclick="del(this)" value="删除"/>'+
                        '<br/> <br/>'+
                     '</div>';
        $("#contentsInfos").append(htmlStr);
    }

    BUI.use('bui/overlay',function(Overlay){
        var contentsInfoIframeStr="<iframe id='contentsInfoSelectIframe'" +
                " src='${createLink(controller: "contentsManage", action: "contentsListForSelect",params: [selectType:"one"])}'" +
        " width=900 height=300></iframe>";
        var contentsInfoDialog = new Overlay.Dialog({
            title:'内容选择',
            width:950,
            height:360,
            bodyContent:contentsInfoIframeStr,
            success:function () {
                $("#contentsInfoTitle").val(contentsInfoTitle);
                $("#contentsInfoId").val(contentsInfoId);
                this.close();
            }
        });
        $('#contentsInfoSelect').on('click',function () {
            contentsInfoDialog.show();
        });


        var contentsInfosIframeStr="<iframe id='contentsInfoSelectIframe'" +
                " src='${createLink(controller: "contentsManage", action: "contentsListForSelect",params: [selectType:"multi"])}'" +
                " width=900 height=300></iframe>";
        var contentsInfosDialog = new Overlay.Dialog({
            title:'内容选择',
            width:950,
            height:360,
            buttons:[
                {
                    text:'关闭',
                    elCls : 'button',
                    handler : function(){
                        this.close();
                    }
                }
            ],
            bodyContent:contentsInfosIframeStr
        });
        $('#contentsInfosSelect').on('click',function () {
            contentsInfosDialog.show();
        });
    });
</script>
</body>
</html>