<%@ page contentType="text/html;charset=UTF-8" language="java" 
	pageEncoding="utf-8"%>
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
<title>管理员通知管理</title>
<html:base/>
<script type='text/javascript' src='/examOnline/dwr/interface/noticeAdminPagerServer.js'></script>
  <script type='text/javascript' src='/examOnline/dwr/engine.js'></script>
  <script type='text/javascript' src='/examOnline/dwr/util.js'></script>
  <script type="text/javascript" src='/examOnline/pager/noticeAdminEditPager.js'></script>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">	
    <style type="text/css">
    body   {background-image:   url(/examOnline/images/backpic.jpg); 
			background-position:   center top; 
			background-repeat:   repeat; 
			} 
    </style>  
</head>
<body onLoad="pageinit();">
<div align="center">
<font size="7" color="#004080"><strong><font face="华文新魏"><br><br>通知管理</font></strong></font>
	<br/><br/>
	<table width="1000" border="0" align="center">
      <tr>
      	<td width="15%"></td>
        <td width="1000"><div align="right">
        <img src="/examOnline/images/adminlog.gif" alt="lock" height="20" width="20"/> 
        <a   href=#   onclick= "javascript:history.back(-1); ">
        	<font size="2">后退一页</font></a></div>
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
<table width="84%" border="0" align="center">
  <tr>
    <td width="45%"><div align="right"></div></td>
    <td width="35%"><div align="right"></div></td>
    <td width="20%"><div align="right">
    	<img src="/examOnline/pager/pagerskin/add_ico.jpg" width="93" height="30" alt="添加新信息" style="cursor:hand;" onclick="window.location.href('/examOnline/adminHome/noticeAdminEdit.jsp')">
    </div></td>
  </tr>
</table>
<table id="infotable" width="84%" border="0" align="center">
  <tr>
    <td bgcolor="#f0fcfd" align="center"><strong>Loading...</strong></td>
  </tr>
</table>

<table width="84%" border="0" align="center">
  <tr>
    <td width="25%" bgcolor="#dffeff" align="center"><div id="pagermsg" align="left"><strong>Loading...</strong></div></td>
    <td width="55%" bgcolor="#d0e3fd" align="center"><div id="pagerctr" align="right"><strong>Loading...</strong></div></td>
    <td width="20%" bgcolor="#7e85fe" align="center"><div id="cgpagerpsize" align="right"><strong>Loading...</strong></div></td>
  </tr>
</table>

</body>
</html:html>
