<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-3-27
  Time: 下午11:44
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>flash预览</title>
</head>
<body style="margin: 0px;">
<!--消息-->
<div style="margin: 10px;">
    <g:render template="/layouts/msgs_and_errors"/>
</div>
<g:if test="${urlStr}">
    <g:if test="${isSwf}">
        <EMBED id="flashId" src="${urlStr}"></EMBED>
    </g:if>
    <g:else>
        <g:if test="${isMp3}">
            <embed src="${resource(dir: "js",file: "audioplayer.swf")}?soundFile=${urlStr}"
                   quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer"
                   type="application/x-shockwave-flash" width="290" height="24">
            </embed>
        </g:if>
        <g:else>
        <embed
                src="${resource(dir: "js",file: "vcastr22.swf")}?vcastr_file=${urlStr}"
                quality="high"
                pluginspage="http://www.macromedia.com/go/getflashplayer"
                allowFullScreen="true"
                type="application/x-shockwave-flash" width="480" height="280">
        </embed>
        </g:else>
    </g:else>
</g:if>
</body>
</html>