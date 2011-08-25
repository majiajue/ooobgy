<%@ page contentType="text/html;charset=utf-8" language="java" 
	pageEncoding="utf-8"%>
<%@page import="com.loquat.constants.SecretKey"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>  
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<%String studentKey = SecretKey.ADMIN_KEY;
	if(session.getAttribute(studentKey)==null)
{
 %>

 <jsp:forward page="/error/error.html"/> 
<%

}
 %>


<html>
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	
		<title>管理员登陆页面</title>

		
		

    <style type="text/css">

    </style>
	</head>
	<body background="/examOnline/images/backpic.jpg">
		<HR/>
			<br /> 
			<br /> 
    <table width="1000" border="0" align="center">
  <tr>
    <td><img src="/examOnline/images/logo.jpg" alt="logo"/></td>
  </tr>
  <tr>
    <td><table width="1000" border="0" align="center">
      <tr>
        <td width="1000"><div align="right">
        <img src="/examOnline/images/adminlog.gif" alt="lock" height="28" width="30"/> 
        	<a   href=#   onclick= "javascript:history.back(-1); ">
        	后退一页</a>
        	</div>
        	</td>
        <td width="150"><div align="right">
        	<img src="/examOnline/images/lock.gif" alt="lock" height="28" width="30"/>
        	<a href="/examOnline/adminHome/adminHome.jsp">个人主页</a>
        </div></td>
        <td width="150"><div align="right">
        	<img height="28" width="30" src="/examOnline/images/exit.gif" alt="exit">
        	<a href="/examOnline/admin.logout?inf=in">安全退出</a>
        </div></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><font face="宋体" color="#0000a0"><strong>
	欢迎您，管理员</strong></font><font face="微软雅黑" color="#547efc" size="5">
	<bean:write name="<%=SecretKey.ADMIN_KEY%>" property="name"/></font>
	 </td>
  </tr>
</table>
<HR>
	<br><br />
    <table width="1000" border="0" align="center">
  <tr>
    <td><a href="/examOnline/adminHome/noticeList.jsp"><img src="/examOnline/images/adminote.jpg" alt="adminote" border = "0"/></a></td>
  </tr>
</table>
	</body>
</html>