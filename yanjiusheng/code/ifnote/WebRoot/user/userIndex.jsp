<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ooobgy.ifnote.constants.SecretKey"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ifnote用户理财中心</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	记得测试结束后加上认证管理员已经登录
	-->
	<SCRIPT LANGUAGE=javascript>
	var munehided = false;
	function loadResize(){
		setTimeout(doreSize,500);
	}
	function reSize(){
		setTimeout(doreSize,80);
	}
	var doreSize = function()
	{
	//alert("resize!");
 		try{ 
 			var oBody = show.document.body;
 			var oFrame = document.all("show");
 			var oheight = oBody.scrollHeight>oBody.clientHeight?oBody.scrollHeight:oBody.clientHeight;
 			var mBody = menu.document.body;
 			var mFrame = document.all("menu");
 			var mheight =  mBody.scrollHeight>mBody.clientHeight?mBody.scrollHeight:mBody.clientHeight;
 			var maxheight = oheight;
 			if(maxheight < mheight){
 				maxheight = mheight;
 			}
 			oFrame.style.height = maxheight;
 			mFrame.style.height = maxheight;
 		}
 //An error is raised if the IFrame domain != its container's domain
 		catch(e)
 		{
 			window.status = 'Error: ' + e.number + '; ' + e.description;
 		}
	}
	</SCRIPT>
	<SCRIPT LANGUAGE=javascript>
	function showmenu(){
		if(munehided){
			document.getElementById("menuspan").style.width = "15%";
			document.getElementById("showspan").style.width = "85%";
			document.getElementById("menuimg").src = "images/hidden.png";
			munehided = false;
		}else{
			document.getElementById("menuspan").style.width="0%";
			document.getElementById("showspan").style.width="100%";
			document.getElementById("menuimg").src = "images/show.png";
			munehided = true;
		}
	}
</SCRIPT>
  </head>
  
  <body onload="loadResize();">
  <%String userKey = SecretKey.USER_KEY; %>
<logic:present name="<%=userKey %>" scope="session">
<bean:define id="user" name="<%=userKey %>" scope="session" type="com.ooobgy.ifnote.entity.User"></bean:define>
<logic:notEmpty name="user">
  <div align="center"> 
    <font size="5" face="微软雅黑">ifnote 用户理财中心</font><br> 
  </div>
  <hr width="100%" size="2">
  <table width="100%" border="0" id="admintable">
  <tr>
    <td id="menuspan" width="15%">
    	<iframe width="100%" frameborder="0" src="user/navigation.jsp" name="menu" id="menu" onload="reSize();"></iframe>
    </td>
  
    <td id="mtool" style="cursor:hand;" bgcolor="#FFFFCC" onclick="showmenu();" onMouseOver="this.style.backgroundColor='#FFCCFF';" 
onmouseout="this.style.backgroundColor='#FFFFCC';"><img id="menuimg" align="middle" src="images/hidden.png"/></td>
   
    <td id="showspan" width="85%">
    	<iframe width="100%" frameborder="0" src="user/summary.jsp" name="show" id="show" onload="reSize();"></iframe>
    </td>
  </tr>
</table>

  </logic:notEmpty>
</logic:present>
  </body>
</html>
