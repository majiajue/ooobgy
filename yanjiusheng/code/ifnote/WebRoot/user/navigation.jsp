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
<%String userKey = SecretKey.USER_KEY; %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>导航栏</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  <style type="text/css">
<!--
body {
	font-size: 12;
}
.title1 {
	font-size: 16;
	color: #003;
	background-color: #FFF;
}
table {
	font-size: 12;
}
body,td,th {
	font-size: 12px;
}
a:link {
	color: #000;
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #000;
}
a:hover {
	text-decoration: none;
	color:#906;
}
a:active {
	text-decoration: none;
}
-->
  </style>
  <script type="text/javascript">
  function showhide(tableid){
  	var tableContent = document.getElementById("cate"+tableid).innerHTML;
  	var tempText = document.getElementById("text"+tableid).value;
  	if(tempText == ""){
  		document.getElementById("text"+tableid).value = tableContent;
  		document.getElementById("cate"+tableid).innerHTML = "";
  	}else{
  		document.getElementById("text"+tableid).value = "";
  		document.getElementById("cate"+tableid).innerHTML = tempText;
  	}	
  }
  
  </script>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
  
  <body>
  <table width="100%" border="0">
  <tr>
  <td>
  <bean:define id="user" name="<%=userKey %>" scope="session" type="com.ooobgy.ifnote.entity.User"></bean:define>
  <p>&nbsp;  
  欢迎您！<font color="#CC3399"><bean:write name="user" property="userName"/></font></p>
  <div>ID:&nbsp;<font color="#330066"><bean:write name="user" property="id"/></font></div>
  <div>Email:&nbsp;<font color="#330066"><bean:write name="user" property="email"/></font></div>
  <div>手机:&nbsp;<font color="#330066"><bean:write name="user" property="phoneNum"/></font></div>
  <hr>
      </td>
      </tr>  
   <tr>
    <td align="left" style="cursor:hand;" bgcolor="#FF9CFF"
    	onMouseOver="this.style.backgroundColor='#CC3399';" 
		onmouseout="this.style.backgroundColor='#FF9CFF';"
		onclick="showhide('if');">
    <div align="left">
    个人理财管理</div>
    	<input type="hidden" value="" id="textif"/>
    </td>
  </tr>
  <tr>
    <td>
    <div align="left"  id="cateif">
      <table width="95%" border="0" align="right">
      <tr>
          <td align="left" style="cursor:hand;" bgcolor="#CCFFFF"
          	onMouseOver="this.style.backgroundColor='#A1FFFF';" 
			onmouseout="this.style.backgroundColor='#CCFFFF';"
			onclick="">
          <a href="user/summary.jsp" target="show">
          理财概况</a>
          </td>
        </tr>
        <tr>
        <tr>
          <td align="left" style="cursor:hand;" bgcolor="#CCFFFF"
          	onMouseOver="this.style.backgroundColor='#A1FFFF';" 
			onmouseout="this.style.backgroundColor='#CCFFFF';"
			onclick="">
          <a href="user/cashList.jsp" target="show">
          现金记账</a>
          </td>
        </tr>
        <tr>
          <td align="left" style="cursor:hand;" bgcolor="#CCFFFF"
          	onMouseOver="this.style.backgroundColor='#A1FFFF';" 
			onmouseout="this.style.backgroundColor='#CCFFFF';"
			onclick="">
          <a href="user/depositList.jsp" target="show">
          存款管理</a>
          </td>
        </tr>
        <tr>
          <td align="left" style="cursor:hand;" bgcolor="#CCFFFF"
          	onMouseOver="this.style.backgroundColor='#A1FFFF';" 
			onmouseout="this.style.backgroundColor='#CCFFFF';"
			onclick="">
          <a href="user/loanList.jsp" target="show">
          贷款管理</a>
          </td>
        </tr>
        <tr>
          <td align="left" style="cursor:hand;" bgcolor="#CCFFFF"
          	onMouseOver="this.style.backgroundColor='#A1FFFF';" 
			onmouseout="this.style.backgroundColor='#CCFFFF';"
			onclick="">
          <a href="user/fundList.jsp" target="show">
          基金投资管理</a>
          </td>
        </tr>
        <tr>
          <td align="left" style="cursor:hand;" bgcolor="#CCFFFF"
          	onMouseOver="this.style.backgroundColor='#A1FFFF';" 
			onmouseout="this.style.backgroundColor='#CCFFFF';"
			onclick="">
          <a href="user/stockList.jsp" target="show">
          股票投资管理</a>
          </td>
        </tr>
        <tr>
          <td align="left" style="cursor:hand;" bgcolor="#CCFFFF"
          	onMouseOver="this.style.backgroundColor='#A1FFFF';" 
			onmouseout="this.style.backgroundColor='#CCFFFF';"
			onclick="">
          <a href="user/futuresList.jsp" target="show">
          期货投资管理</a>
          </td>
        </tr>
        </table>
</div>
</td>
</tr>
</table>


<p>
  <div id="title2" class="title1">个人信息管理</div>
<table width="100%" border="0">
  <tr>
    <td align="left" style="cursor:hand;" bgcolor="#FFD5A6"
          	onMouseOver="this.style.backgroundColor='#FEB74B';" 
			onmouseout="this.style.backgroundColor='#FFD5A6';"
			onclick=>
         <a href="back/admin/modifypwd.jsp" target="show">登录密码管理</a>
          </td>
  </tr>
  <tr>
    <td align="left" style="cursor:hand;" bgcolor="#FFD5A6"
          	onMouseOver="this.style.backgroundColor='#FEB74B';" 
			onmouseout="this.style.backgroundColor='#FFD5A6';">
         <a href="back/admin/modifyad.jsp" target="show"> 个人信息管理</a>
          </td>
  </tr>
  <tr>
    <td align="left" style="cursor:hand;" bgcolor="#FFD5A6"
          	onMouseOver="this.style.backgroundColor='#FEB74B';" 
			onmouseout="this.style.backgroundColor='#FFD5A6';">
    <html:link action="/generalAction.do?method=getDetail" target="show">查看管理组资料</html:link>
    </td>
  </tr>
</table>
</body>
</html>
