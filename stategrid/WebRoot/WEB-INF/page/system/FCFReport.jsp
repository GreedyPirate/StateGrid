<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户按照性别分报表统计</title>
<LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
<!-- 1:加载JS -->
<script language="JavaScript" src="${pageContext.request.contextPath }/script/FusionCharts.js"></script>


</head>

<body>

<fieldset style="width: 600px; height: 600px; padding: 1 background:${pageContext.request.contextPath }/images/back1.JPG"><legend>
<font color="#0000FF">
<img border="0" src="${pageContext.request.contextPath }/images/zoom.gif" width="14" height="14"> 报表统计</font></legend>		
	<!-- FCF报表加载数据 -->
	<!-- 2:添加div标签 -->
	<div id="chartdiv" align="center">图形将出现这个DIV里，到时这里的字将被图形替代。</div>  
	<!-- 3:使用隐藏域获取XML数据 -->
	<s:hidden id="data" name="data" value="%{#request.chart}"></s:hidden>
	<!-- 4:使用js完成加载数据 -->
	<script type="text/javascript">   
        var myChart = new FusionCharts("${pageContext.request.contextPath }/fusionCharts/FCF_Pie2D.swf", "myChartId", "600", "500");  
        var di = document.getElementById("data").value;
        myChart.setDataXML(di);   
        myChart.render("chartdiv");    
    </script>  
	
</fieldset>
</body>
</html>
