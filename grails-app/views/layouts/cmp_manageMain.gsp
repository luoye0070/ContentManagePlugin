<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-3-25
  Time: 下午10:46
  To change this template use File | Settings | File Templates.
--%>

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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="${resource(dir: "js", file: "jquery-1.8.1.min.js")}"></script>
    <g:render template="/layouts/cmp_bui"></g:render>
    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body>
<!--消息-->
<div style="margin: 10px;">
    <g:render template="/layouts/msgs_and_errors"/>
</div>
<g:layoutBody/>
</body>
</html>