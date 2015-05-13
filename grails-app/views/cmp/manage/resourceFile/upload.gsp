<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-4-20
  Time: 上午1:12
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="cmp_manageMain"/>
    <title>上传资源文件</title>
    <script type="text/javascript">
        $(function(){
            $("#addFile").click(function(event){
                var htmlStr="<div>"+
                        "<input type=\"file\" name=\"upFile\"/>&nbsp;&nbsp;<input type=\"button\" onclick=\"del(this)\" value=\"删除\"/>"+
                        "<br/> <br/>"+
                        "</div>";
                $("#fileList").append(htmlStr);
            });
        });
        function del(obj){
            $(obj).parent().remove();
        }
    </script>
</head>
<body>
<div style="margin: 10px;">
    <g:form action="upload" name="form1" method="post" enctype="multipart/form-data">
        <div id="fileList">
            <div>
                <input type="file" name="upFile"/>&nbsp;&nbsp;<input type="button" onclick="del(this)" value="删除"/>
                <br/> <br/>
            </div>
        </div>
        <input id="addFile" type="button" value="添加"/>&nbsp;&nbsp;<input type="submit" value="上传"/>
    </g:form>
</div>
</body>
</html>