<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-4-4
  Time: 上午1:57
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="cmp_manageMain"/>
    <title>网站信息</title>
    <script type="text/javascript"
            src="${resource(dir: "js/kindeditor-4.1.10", file: "kindeditor-all-min.js")}"></script>
    <script type="text/javascript" src="${resource(dir: "js/kindeditor-4.1.10/lang", file: "zh_CN.js")}"></script>
    <script type="text/javascript" src="${resource(dir: "js", file: "flashinsert.js")}"></script>
    <script type="text/javascript">
        var uploadImageEditor;
        var uploadFlashEditor;
        var uploadLogoEditor;
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
            uploadLogoEditor = K.editor({
                uploadJson: '${createLink(controller: "resourceFile",action: "uploadInEditor")}',
                fileManagerJson: '${createLink(controller: "resourceFile",action: "manageInEditor")}',
                allowFileManager : true
            });
            uploadImageEditor = K.editor({
                uploadJson: '${createLink(controller: "resourceFile",action: "uploadInEditor")}',
                fileManagerJson: '${createLink(controller: "resourceFile",action: "manageInEditor")}',
                allowFileManager : true
            });
            uploadFlashEditor = K.editor({
                uploadJson: '${createLink(controller: "resourceFile",action: "uploadInEditor")}',
                fileManagerJson: '${createLink(controller: "resourceFile",action: "manageInEditor")}',
                allowFileManager : true
            });
        });
        function uploadLogo(obj) {
            uploadLogoEditor.loadPlugin('image', function() {
                uploadLogoEditor.plugin.imageDialog({
                    imageUrl : $(obj).parent().find("input[data='logoUrl']").val(),
                    clickFn : function(url, title, width, height, border, align) {
                        $(obj).parent().find("input[data='logoUrl']").val(url);
                        uploadLogoEditor.hideDialog();
                    }
                });
            });
        }
        function uploadImage(obj) {
            uploadImageEditor.loadPlugin('image', function() {
                uploadImageEditor.plugin.imageDialog({
                    imageUrl : $(obj).parent().find("input[data='imageUrl']").val(),
                    clickFn : function(url, title, width, height, border, align) {
                        $(obj).parent().find("input[data='imageUrl']").val(url);
                        uploadImageEditor.hideDialog();
                    }
                });
            });
        }
        function uploadFlash(obj) {
            uploadFlashEditor.loadPlugin('flash', function() {
                uploadFlashEditor.plugin.flashDialog({
                    flashUrl : $(obj).parent().find("input[data='flashUrl']").val(),
                    clickFn : function(url, width, height) {
                        //alert(height+","+width );
                        $(obj).parent().find("input[data='flashUrl']").val(url);
                        uploadFlashEditor.hideDialog();
                    }
                });
            });
        }

        var linkCount =${linkCount};
        $(function () {
            $("#addFile").click(function (event) {
                var htmlStr = '<div>' +
                        '<input type="hidden" name="linkNums" value="' + linkCount.toString() + '"/>' +
                        '<input type="hidden" name="linkId' + String(linkCount) + '"/>' +
                        '标题：<input type="text" name="name' + linkCount + '" value=""/>&nbsp;&nbsp;' +
                        'Url：<input type="text" data="url" name="url' + linkCount + '" value=""/>&nbsp;' +
                        '<input type="button" onclick="del(this)" value="删除"/>' +
                        '<br/> <br/>' +
                        '</div>';
                $("#fileList").append(htmlStr);
                linkCount++;
            });
        });
        function del(obj) {
            $(obj).parent().remove();
            var diiIds=$("#deleteLinkInfoIds").val()+","+$(obj).parent().find("input[data='linkId']").val();
            $("#deleteLinkInfoIds").val(diiIds);
        }
    </script>
</head>

<body>
<div style="margin: 10px;">
    <div class="" align="center">
        <g:form action="editWebSiteInfo" name="form1" method="post" class="form-horizontal">
            <div class="control-group">
                <label class="control-label"><g:message code="webSiteInfo.siteName.label" default="Site Name" />：</label>

                <div class="controls control-row-auto">
                    <g:textField name="siteName" style="width: 500px;" maxlength="128" value="${webSiteInfoInstance?.siteName}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="webSiteInfo.logoUrl.label" default="Logo Url" />(960*73)：</label>

                <div class="controls control-row-auto">
                    <g:textField  name="logoUrl" style="width: 500px;" data='logoUrl' maxlength="256" value="${webSiteInfoInstance?.logoUrl}"/>
                    &nbsp;<input type="button" onclick="uploadLogo(this)" value="上传LOGO背景"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">
                    <g:message code="webSiteInfo.flashUrl.label" default="Flash Url" />
                    (960*90)：
                </label>

                <div class="controls control-row-auto">
                    <g:textField  name="flashUrl" style="width: 500px;"  data='flashUrl' maxlength="256" value="${webSiteInfoInstance?.flashUrl}"/>
                    &nbsp;<input type="button" onclick="uploadFlash(this)" value="上传Flash"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">
                    <g:message code="webSiteInfo.imageUrl.label" default="Image Url" />
                    (650*90)：
                </label>

                <div class="controls control-row-auto">
                    <g:textField  name="imageUrl" style="width: 500px;"  data='imageUrl' maxlength="256" value="${webSiteInfoInstance?.imageUrl}"/>
                    &nbsp;<input type="button" onclick="uploadImage(this)" value="上传图片"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="webSiteInfo.maxSizeOfFile.label" default="Max Size Of File" />：</label>

                <div class="controls control-row-auto">
                    <g:field name="maxSizeOfFile" type="number" value="${(int)((webSiteInfoInstance?.maxSizeOfFile?:0)/1024/1024)}" required=""/>MB
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="webSiteInfo.linkInfoList.label"
                                                        default="Link Info List"/>：</label>

                <div class="controls control-row-auto">
                    <input type="hidden" name="deleteLinkInfoIds" id="deleteLinkInfoIds"/>
                    <div id="fileList">
                        <g:each in="${webSiteInfoInstance?.linkInfoList}" status="i" var="linkInfo">
                            <div>
                                <input type="hidden" name="linkNums" value="${i}"/>
                                <input type="hidden" data="linkId" name="linkId${i}" value="${linkInfo.id}"/>
                                标题：<input type="text" name="name${i}" value="${linkInfo.name}"/>&nbsp;&nbsp;
                            Url：<input type="text" data="url" name="url${i}" value="${linkInfo.url}"/>&nbsp;
                                <input type="button" onclick="del(this)" value="删除"/>
                                <br/> <br/>
                            </div>
                        </g:each>
                    </div>
                    <input id="addFile" type="button" value="添加"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="webSiteInfo.rightInfo.label" default="Right Info" />：</label>

                <div class="controls control-row-auto">
                    <g:textField name="rightInfo" style="width: 500px;" maxlength="256" required="" value="${webSiteInfoInstance?.rightInfo}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"><g:message code="webSiteInfo.mailAddr.label" default="Mail Address" />：</label>

                <div class="controls control-row-auto">
                    <g:textField name="mailAddr" style="width: 500px;" maxlength="256" required="" value="${webSiteInfoInstance?.mailAddr}"/>
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
</div>
</body>
</html>