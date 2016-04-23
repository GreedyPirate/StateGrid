<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
<!--
fieldset div {
	float:left;
	width:24%;
	text-align:left;
	line-height:25px;
}
td div {
	float:left;
	width:24%;
	text-align:left;
	line-height:25px;
}
-->
</style>
<HTML>
	<HEAD>
		<title>角色权限管理</title>		
		<LINK href="${pageContext.request.contextPath }/css/Style.css"  type="text/css" rel="stylesheet">
		<script language="javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/script/pub.js"></script>
		<script language="javascript" src="${pageContext.request.contextPath }/script/jquery-1.4.2.js"></script>
		<script language="javascript">
		 /*
		 function saveRole(){
           document.Form2.roleID.value=document.Form1.roleID.value;
		   document.Form2.action="saveRole.do";
		   document.Form2.submit();
		}*/
		//jQuery
		 function saveRole(){
			 $("input[name='roleID']").val($("select[name='roleID']").val());
		     $("#Form2").attr("action","${pageContext.request.contextPath }/system/elecRoleAction_save.do");
		     $("#Form2").submit();
		 }
		
       
       function selectRole(){
    	   /* if(document.Form1.roleID.value=="0"){
          
             document.Form1.action="roleIndex.jsp";
             document.Form1.submit();            
          }else{
            Pub.submitActionWithForm('Form2','./roleEdit.jsp','Form1');
          }*/
          /**jquery对象*/
    	  if($("select[name='roleID']").val()=="0"){
    		  $("#Form1").attr("action","${pageContext.request.contextPath }/system/elecRoleAction_home.do");
   		      $("#Form1").submit();        
          }else{
              Pub.submitActionWithForm('Form2','${pageContext.request.contextPath }/system/elecRoleAction_edit.do','Form1');
          }
       }
       /**
	   function checkAllOper(oper){
          var selectoper = document.getElementsByName("selectoper");
          for(var i=0;i<selectoper.length;i++){
          	selectoper[i].checked = oper.checked;
          }
       }
	   function checkAllUser(user){
          var selectuser = document.getElementsByName("selectuser");
          for(var i=0;i<selectuser.length;i++){
          	selectuser[i].checked = user.checked;
          }
       }
       **/
       /**
       function displayuser(){
			if(document.getElementById("dataUser").style.display == "none"){
			    document.getElementById("userflag").innerText = "用户分配 关闭";
				document.getElementById("dataUser").style.display = "";
			}
			else{
				document.getElementById("userflag").innerText = "用户分配 打开";
				document.getElementById("dataUser").style.display = "none";
			}
		}
		function displaypermission(){
			if(document.getElementById("dataPopedom").style.display == "none"){
				document.getElementById("permissionflag").innerText = "权限分配 关闭";
				document.getElementById("dataPopedom").style.display = "";
			}
			else{
				document.getElementById("permissionflag").innerText = "权限分配 打开";
				document.getElementById("dataPopedom").style.display = "none";
			}
		}
		**/
		function displayuser(){
			if($("#dataUser").css("display")== "none"){
			    $("#userflag").text("用户分配 关闭");
				$("#dataUser").css("display","");
			}
			else{
				 $("#userflag").text("用户分配 打开");
				 $("#dataUser").css("display","none");
			}
		}
		function displaypermission(){
			if($("#dataPopedom").css("display") == "none"){
				$("#permissionflag").text("权限分配 关闭");
				$("#dataPopedom").css("display","");
			}
			else{
				$("#permissionflag").text("权限分配 打开");
				$("#dataPopedom").css("display","none");
			}
		}
		//权限：全部选中/不选中
		function checkAllOper(oper){
			$("input[type='checkbox'][name='selectoper']").attr("checked",oper.checked);
		}
		//用户：全部选中/不选中
		function checkAllUser(user){
			$("input[type='checkbox'][name='selectuser']").attr("checked",user.checked);
		}
		//选中复选框，触发事件
		function goSelect(id){
			//按照_符号分隔
			var array = id.split("_");
			if(array[0] == array[1]){//此时说明操作的（父）节点
				//选中父
				if($("#"+id)[0].checked){
					//子都选中
					$("input[type='checkbox'][name='selectoper'][id^='"+array[0]+"']").attr("checked",true);
				}
				//取消父
				else{
					//子都取消
					$("input[type='checkbox'][name='selectoper'][id^='"+array[0]+"']").attr("checked",false);
				}
			}
			else{//说明此时操作的子设置中的一个(子)
				//当选中子设置中的一个，则父一定被选中
				if($("#"+id)[0].checked){
					$("input[type='checkbox'][name='selectoper'][id^='"+array[0]+"'][id$='"+array[0]+"']").attr("checked",true);
				}
				//当取消子设置中的一个
				else{
					//先查找子设置的对象
					var $check = $("input[type='checkbox'][name='selectoper'][id^='"+array[0]+"']:not([id$='"+array[0]+"'])");
					//遍历子设置的对象
					/**
					 * flag:用于判断当前子设置的对象是否有被选中
					 *   * flag=false，子对象都没有被选中，此时父要被取消
					 *   * flag=true，子对象中至少有一个被选中，此时不做任何操作
					 */
					var flag = false;
					$check.each(function(index,domEle){
						if(domEle.checked){
							flag = true;
							return false;
						}
					})
					if(!flag){
						$("input[type='checkbox'][name='selectoper'][id^='"+array[0]+"'][id$='"+array[0]+"']").attr("checked",false);
					}
				}
			}
		}
		</script>
	</HEAD>
		
	<body>
	 <Form name="Form1" id="Form1"  method="post" style="margin:0px;">
			<table cellSpacing="1" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" colspan=2 align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
							<font face="宋体" size="2"><strong>角色管理</strong></font>
						</td>
					</tr>	
					<tr>
					   <td class="ta_01" colspan=2 align="right" width="100%"  height=10>
					   </td>
					</tr>		
					<tr>
						<td class="ta_01" align="right" width="35%" >角色类型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="ta_01" align="left"  width="65%" >
							<s:select list="#request.roleList" listKey="roleID" listValue="roleName"
								headerKey="" headerValue="---请选择---"
								 name="roleID" id="Form1_roleID" class="bg" cssStyle="width:180px" onchange="selectRole()"
							></s:select>
						</td>				
					</tr>
				    <tr>
					   <td class="ta_01" align="right" colspan=2 align="right" width="100%"  height=10></td>
					</tr>
				</TBODY>
			  </table>
	 </form>
	 
	<form id="Form2" name="Form2" action="${pageContext.request.contextPath }/system/elecRoleAction_home.do" method="post" style="margin:0px;">
	 
	  <table cellSpacing="1" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">
		 <tr>
		  <td>
		   <fieldset style="width:100%; border : 1px solid #73C8F9;text-align:left;COLOR:#023726;FONT-SIZE: 12px;"><legend align="left">权限分配</legend>
		 
		     <table cellSpacing="0" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">			 
					  <tr>
						 <td class="ta_01" colspan=2 align="left" width="100%" > 
						        <table cellspacing="0" align="center" width="100%" cellpadding="1" rules="all" bordercolor="gray" border="1" 
									style="BORDER-RIGHT:gray 1px solid; BORDER-TOP:gray 1px solid; BORDER-LEFT:gray 1px solid; WORD-BREAK:break-all; BORDER-BOTTOM:gray 1px solid; BORDER-COLLAPSE:collapse; BACKGROUND-COLOR:#f5fafe; WORD-WRAP:break-word">
									<!-- 遍历所有的父节点 -->	
									<s:if test="#request.popedomList!=null && #request.popedomList.size()>0">
										<s:iterator value="#request.popedomList" >
											<tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">
												<td class="ta_01"  align="left" width="18%" height="22" background="../images/tablehead.jpg" >
													<input type="checkbox"  name="selectoper" id="<s:property value="mid"/>_<s:property value="mid"/>" value="<s:property value='pid'/>_<s:property value='mid'/>" onClick='goSelect(this.id)' >
													<s:property value="name"/>
												</td>
												<td class="ta_01"  align="left" width="82%" height="22">
													<!-- 遍历所有的子节点 -->
													<s:if test="subNodes!=null && subNodes.size()>0">
														<s:iterator value="subNodes">
																<div>
																	<input type="checkbox"  name="selectoper" id="<s:property value="pid"/>_<s:property value="mid"/>" value="<s:property value='pid'/>_<s:property value='mid'/>" onClick='goSelect(this.id)' >
																	<s:property value="name"/>
																</div>
														</s:iterator>
													</s:if>
												</td>
											</tr>
										</s:iterator>
									</s:if>
						     	</table> 
						   </td>
						</tr>						
					 <input type="hidden" id="roleID" name="roleID" >						
				 </table>	
		        </fieldset>
			  </td>
			</tr>					
	  </table>			    
	</Form>
	</body>
</HTML>
