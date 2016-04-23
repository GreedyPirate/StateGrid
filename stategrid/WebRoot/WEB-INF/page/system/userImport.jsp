<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
  <head> 
    <title>导入文件</title> 
    <link href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-1.4.2.js"></script>
    <SCRIPT language="javascript">
	    function importExcel(){
			var upload = $("#file").val();    
			var extension = upload.substr(upload.indexOf("."));
			if(upload == ""){
				alert("请选择需要上传的文件！");
				return false; 
			}
			if(extension != ".xls"){
				alert("上传的文件后缀名必须是.xls格式！");
				return false;
			}
			$("form:first").submit(); 
		}
     
    </SCRIPT>
    
  </head>
  
  <body>
    <s:form namespace="/system" action="elecUserAction_importExcel.do" method="post" enctype="multipart/form-data">
      <br>
      <table border="0" width="100%" cellspacing="0" cellpadding="0">
      	<tr>
      		<td class="ta_01" align="center" background="${pageContext.request.contextPath }/images/b-info.gif" colspan="4">
				<font face="宋体" size="2"><strong>Excel文件数据导入</strong></font>
			</td>
		</tr>
		<tr>
			<td class="ta_01" align="left" bgColor="#f5fafe" colspan="4">
				<font face="宋体" size="2">
					说明：只接受.xls扩展名的文件,每一个sheet请以"User1"的形式保存
				</font>
			</td>
		</tr>
		<tr>
          <td width="1%" height=30></td>
          <td width="20%"></td>
          <td width="78%"></td>
          <td width="1%"></td>
        </tr>
        
        <tr>
          <td width="1%"></td>
          <td width="15%" align="center">请选择文件:</td>
          <td width="83%" align="left">
          		<s:file name="file" id="file" cssStyle="width:365px"></s:file>
          </td>
          <td width="1%"></td>
        </tr>
        <tr height=50><td colspan=4 ></td></tr>
	    <tr height=2><td colspan=4 background="${pageContext.request.contextPath }/images/b-info.gif"></td></tr>
	    <tr height=10><td colspan=4 ></td></tr>
        <tr>
          <td align="center" colspan=4>
          	  <input type="button" name="BT_Submit" value="导入"  style="font-size:12px; color:black; height=22;width=55"   onClick="importExcel()">
          	  <INPUT type="button"  name="Reset1" id="save"  value="关闭"  onClick="window.close();" style="width: 60px; font-size:12px; color:black; height=22">
          </td>
        </tr>
      </table>
    </s:form>
      <s:if test="#request.errorList!=null && #request.errorList.size()>0">
          <!-- 显示错误信息 -->
	      <table border="0" width="100%" cellspacing="0" cellpadding="0">
	      	<tr>
	      		<td class="ta_01" align="center" background="${pageContext.request.contextPath }/images/b-info.gif" colspan="4">
					<font face="宋体" size="2"><strong>导入失败！错误信息！</strong></font>
				</td>
			</tr>
			<s:iterator value="#request.errorList">
	      		<tr>
	          		<td width="1%"></td>
	          		<td width="15%" align="center">错误信息:</td>
	          		<td width="83%" align="left">
	         		   <font color="red"><s:property/></font>
	          		</td>
	         		<td width="1%"></td>
	            </tr>
	        </s:iterator>
	
	      </table>
      </s:if>
      
  </body>
</html>
