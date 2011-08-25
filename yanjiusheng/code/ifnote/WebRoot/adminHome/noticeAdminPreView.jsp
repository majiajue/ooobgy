<%@ page contentType="text/html;charset=UTF-8" language="java" 
	pageEncoding="utf-8"%>
<%@page import="com.loquat.datavo.NoticeAdmin"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>  
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<%@page import="com.loquat.constants.SecretKey"%>
<%String studentKey = SecretKey.ADMIN_KEY;
	if(session.getAttribute(studentKey)==null)
{
 %>

 <jsp:forward page="/error/error.html"/> 
<%

}
 %>

<html:html lang="true">
<head>
<title>管理员通知预览</title>
<html:base/>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">	
    <style type="text/css">
    body   {background-image:   url(/examOnline/images/backpic.jpg); 
			background-position:   center top; 
			background-repeat:   repeat; 
			} 
    </style> 
</head>
<body>
<div align="center">
<font size="7" color="#004080"><strong><font face="华文新魏"><br><br>通知预览</font></strong></font>
	<br/><br/>
	<table width="1000" border="0" align="center">
      <tr>
      	<td width="15%"></td>
        <td width="1000"><div align="right">
        <img src="/examOnline/images/adminlog.gif" alt="lock" height="20" width="20"/> 
        <a   href="noticeList.jsp">
        	<font size="2">通知列表</font></a></div>
        	</td>
        <td width="150"><div align="right">
        	<img src="/examOnline/images/lock.gif" alt="lock" height="20" width="20"/>
        	<a href="/examOnline/adminHome/adminHome.jsp"><font size="2">个人主页</font></a>
        </div></td>
        <td width="150"><div align="right">
        	<img height="20" width="20" src="/examOnline/images/exit.gif" alt="exit">
        	<a href="/examOnline/admin.logout?inf=in"><font size="2">安全退出</font></a>
        </div></td>
      </tr>
    </table>
</div>
<hr>
<p align="center">
  <%
NoticeAdmin notice = (NoticeAdmin)session.getAttribute("adminNoticePreview");
 %>
  <bean:define id="adminNotice" name="adminNoticePreview" type="com.loquat.datavo.NoticeAdmin" scope="request"></bean:define>
</p>
<table width="900" border="1" align="center">
  <tr>
    <td height="60" align="center"><font face="微软雅黑"><strong><font size="5">通知题目：<%=adminNotice.getTitle()%></font></strong></font></td>
  </tr>
  <tr>
    <td height="200" align="left" valign="top"><strong>通知内容：</strong>&nbsp;&nbsp;<%=adminNotice.getContent() %></td>
  </tr>
  <tr>
    <td align="center" height="40"><font size="4"><a href="/examOnline/adminHome/noticeList.jsp">回到通知管理</a></font></td>
  </tr>
</table>
</body>
</html:html>
