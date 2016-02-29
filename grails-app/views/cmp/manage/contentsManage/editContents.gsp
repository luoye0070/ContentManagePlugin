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
                langType: 'zh_CN',
                items:[
                    'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
                    'flash', 'media','insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                    'anchor', 'link', 'unlink'
                ],
                width:'700px',
                height:"500px"
            });

            uploadImageEditor = K.editor({
                uploadJson: '${createLink(controller: "resourceFile",action: "uploadInEditor")}',
                fileManagerJson: '${createLink(controller: "resourceFile",action: "manageInEditor")}',
                allowFileManager : true
            });
        });

        var imageCount =${imageCount};
        $(function () {
            $("#addFile").click(function (event) {
                var htmlStr = '<div>' +
                        '<input type="hidden" name="imageNums" value="' + imageCount.toString() + '"/>' +
                        '<input type="hidden" name="imageId' + String(imageCount) + '"/>' +
                        '标题：<input type="text" name="name' + imageCount + '" value=""/>&nbsp;&nbsp;' +
                        'Url：<input type="text" data="imgUrl" name="imgUrl' + imageCount + '" value=""/>&nbsp;' +
                        '<input type="button" onclick="uploadImage(this)" value="上传图片"/>&nbsp;&nbsp;' +
                        '描述：<input type="text" name="decription' + imageCount + '" value=""/>&nbsp;&nbsp;' +
                        '<input type="button" onclick="del(this)" value="删除"/>' +
                        '<br/> <br/>' +
                        '</div>';
                $("#fileList").append(htmlStr);
                imageCount++;
            });
        });
        function del(obj) {
            $(obj).parent().remove();
            var diiIds=$("#deleteImageInfoIds").val()+","+$(obj).parent().find("input[data='imageId']").val();
            $("#deleteImageInfoIds").val(diiIds);
        }

        $(function(){
            <g:if test="${isSaveOk}">
            if(top.topManager){
                //打开左侧菜单中配置过的页面
                top.topManager.reloadPage("contents_list_menu");
            }
            </g:if>
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
</head>

<body>
<div style="margin: 10px;">
    %{--<div style="margin: 10px;" class="row show-grid">--}%
    %{--<div class="span4">&nbsp;</div>--}%
    %{--<div class="span24">--}%
    <div class="">
        <g:form action="editContents" name="form1" method="post" class="form-horizontal">
            <input type="hidden" name="id" value="${contentsInfoInstance?.id}"/>
        %{--<input type="hidden" name="contentClassId" value="${contentsInfoInstance?.contentClassInfo?.id}"/>--}%
            <div class="control-group">
                <label class="control-label"><g:message code="contentsInfo.title.label" default="Title"/>：</label>

                <div class="controls control-row-auto">
                    <g:textArea name="title" style="width: 500px;" cols="40" rows="2" maxlength="1024" required=""
                                value="${contentsInfoInstance?.title}"/>
                </div>
            </div>

        %{--<div class="control-group">--}%
        %{--<label class="control-label"><g:message code="contentsInfo.time.label" default="Time" />：</label>--}%
        %{--<div class="controls">--}%
        %{--<g:datePicker name="time" precision="day"  value="${contentsInfoInstance?.time}"  />--}%
        %{--</div>--}%
        %{--</div>--}%

            <div class="control-group">
                <label class="control-label"><g:message code="contentsInfo.author.label" default="Author"/>：</label>

                <div class="controls">
                    <g:textField name="author" maxlength="216" value="${contentsInfoInstance?.author}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">
                    <g:message code="contentsInfo.contentClassInfo.label" default="Content Class Info"/>

                </label>

                <div class="controls control-row-auto">
                    <input type="text" id="show" value="${contentsInfoInstance?.contentClassInfo?.name?:"没有类别"}"/>
                    <input type="hidden" id="hide" value="${contentsInfoInstance?.contentClassInfo?.id?:"0"}" name="contentClassId"/>
                    %{--<g:select id="contentClassInfo" name="contentClassId" from="${lj.picc.data.ContentClassInfo.list()}" optionKey="id" value="${contentsInfoInstance?.contentClassInfo?.id}" class="many-to-one" noSelection="['0': '没有类别']"/>--}%
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="contentsInfo.content.label" default="Content"/>：</label>

                <div class="controls control-row-auto">
                    <g:textArea name="content" id="content_editor" cols="40" rows="10" maxlength="102400"
                                value="${contentsInfoInstance?.content}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="contentsInfo.imageInfoList.label"
                                                        default="Image Info List"/>：</label>

                <div class="controls control-row-auto">
                    <input type="hidden" name="deleteImageInfoIds" id="deleteImageInfoIds"/>
                    <div id="fileList">
                        <g:each in="${contentsInfoInstance?.imageInfoList}" status="i" var="imageInfo">
                            <div>
                                <input type="hidden" name="imageNums" value="${i}"/>
                                <input type="hidden" data="imageId" name="imageId${i}" value="${imageInfo.id}"/>
                                标题：<input type="text" name="name${i}" value="${imageInfo.name}"/>&nbsp;&nbsp;
                            Url：<input type="text" data="imgUrl" name="imgUrl${i}" value="${imageInfo.imgUrl}"/>&nbsp;
                                <input type="button" onclick="uploadImage(this)" value="上传图片"/>&nbsp;&nbsp;
                            描述：<input type="text" name="decription${i}"
                                              value="${imageInfo.decription}"/>&nbsp;&nbsp;
                            <input type="button" onclick="del(this)" value="删除"/>
                                <br/> <br/>
                            </div>
                        </g:each>
                    </div>
                    <input id="addFile" type="button" value="添加"/>
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