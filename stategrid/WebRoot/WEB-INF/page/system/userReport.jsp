<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户按照所属单位分报表统计</title>
<LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">


</head>

<body>

<fieldset style="width: 600px; height: 600px; padding: 1 background:${pageContext.request.contextPath }/images/back1.JPG"><legend>
<font color="#0000FF">
<img border="0" src="${pageContext.request.contextPath }/images/zoom.gif" width="14" height="14"> 报表统计</font></legend>		
	<img src="${pageContext.request.contextPath}/chart/${filename}" border="0">
</fieldset>
</body>
</html>