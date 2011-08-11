<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ooobgy.ifnote.constants.SecretKey"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %> 
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %> 
<html> 
	<head>
		<title>登录页面</title>
	</head>
<style type="text/css">
#pic{
	
	background-position:left;
	background-repeat:no-repeat;
}

    body   {background-image:   url(/examOnline/images/backtest.PNG); 
			background-position:   center top; 
			background-repeat:   repeat; 
			} 
    </style> 
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"><style type="text/css">
<!--
body {
	background-color: #D4DFFF;
	
}
-->
</style>	
	
	<div id="pic" width = "340" height = "300"><div>
	<div><width = 260><font color="#0000cc"><em><font face="华文行楷" size="5"><center></center></font></em></font></width></div>
	<%
		String adminKey = SecretKey.ADMIN_KEY;
		String studentKey = SecretKey.STUDENT_KEY;
		String teacherKey = SecretKey.TEACHER_KEY;
	%>
	
	<br />
	 <font color="#0000cc"><font face="华文行楷" size="5"><center>用户登录</center></font></font>
	 <logic:notPresent name="<%=adminKey %>" scope="session">
	<logic:notPresent name="<%=studentKey %>" scope="session">
	<logic:notPresent name="<%=teacherKey %>" scope="session">
	
    <html:form action="/login" styleId="loginForm">
<table width="100%" >
    <tr><td width="100%">
    <div>
    <table width="100%" height = 150 border="0">
  <tr>
    <td width="30%"><div align="right"><font face="微软雅黑" size="2">用户名：</font></div></td>
    <td><font face="微软雅黑"><html:text property="userId"></html:text></font><br></td>
  </tr>
  <tr>
    <td><div align="right"><font size="2" face="微软雅黑">密 码：</font></div></td>
    <td><html:password property="userPwd"/></td>
  </tr>
  <tr>
    <td><div align="right"><font size="2" face="微软雅黑">验证码：</font></div></td>
    <td><font size="2" face="华文细黑"><html:text property="iCode" size="5" maxlength="4"></html:text> 
     <script type="text/javascript">
   function changeImg()
   {    document.getElementById("validatecodeimg").src=Math.round(Math.random()*10000)+".ConfirmationCode";
   }
	</script><img align="top" id="validatecodeimg" alt="" src="login.ConfirmationCode"><br></font>
      
	<font face="华文细黑" size="2" color="#0001cb"><a href="javascript:changeImg()">看不清？换一张O(&cap;_&cap;)<font face="华文行楷">O</font><font face="华文彩云">~</font></a></font>  
    </td>
  </tr>
</table>
</div>   
</td></tr>
<tr><td align="center">
<font face="微软雅黑" size="2"><html:radio property="userType" value="student">学生</html:radio></font>
		    <font size="2" face="微软雅黑"><html:radio property="userType" value="teacher">教师</html:radio></font>
		   <font size="2" face="微软雅黑"> <html:radio property="userType" value="admin" >管理员</html:radio>
		    <html:errors property="userType"/>
</td></tr>
<tr><td>
<table width="100%"  align="center">
<tr>
<td align="center">
  <a><img src="/examOnline/images/login.gif" width="62" height="22" alt="登录" style="cursor:hand;" onClick="javascript:document.loginForm.submit();">
  </a>
  </td>
  </tr>
  </table>
  </td></tr>
  <tr><td width="100%" align="center"><font size="4" face="微软雅黑" color="#ff0000"><html:errors/>
  </font>
  </td></tr>
</table>
</html:form>
</logic:notPresent>
	</logic:notPresent>
	</logic:notPresent>


<tiles:insert page='/component/adminInfo.jsp'></tiles:insert>
<tiles:insert page='/component/studentInfo.jsp'></tiles:insert>
<tiles:insert page='/component/teacherInfo.jsp'></tiles:insert>
    </body>
</html>

