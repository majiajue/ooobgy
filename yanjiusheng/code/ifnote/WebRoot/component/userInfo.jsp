<%@ page contentType="text/html;charset=UTF-8" language="java" 
	pageEncoding="utf-8"%>
<%@page import="com.loquat.constants.SecretKey"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>  
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<html:html lang="true">
<head>
<title>管理员信息页面</title>
<html:base/>
</head>
<body>
<%String adminKey = SecretKey.ADMIN_KEY; %>
<logic:present name="<%=adminKey %>" scope="session">
<bean:define id="admin" name="<%=adminKey %>" scope="session" type="com.loquat.datavo.SimpleAdmin"></bean:define>
<logic:notEmpty name="admin">
<table width="100%" border="0" align="center">
  <tr>
    <td><div align="center">欢迎您，管理员</div></td>
  </tr>
  <tr>
    <td><div align="center">
      <table width="100%" border="0">
        <tr>
          <td><div align="right">工号：</div></td>
          <td><div align="left"><bean:write name="admin" property="userId"/></div></td>
        </tr>
        <tr>
          <td><div align="right">姓名：  </div></td>
          <td><div align="left"><bean:write name="admin" property="name"/></div></td>
        </tr>
      </table>
    </div></td>
  </tr>
  <tr>
    <td><div align="center">
      <table width="100%" border="0">
        <tr>
          <td><div align="right"><a href="/examOnline/adminHome/adminHome.jsp"  target="_parent">
            <img src="/examOnline/images/sysAdmin.gif" width="130" height="32" alt="进入管理系统" border="0">
          </a></div></td>
          <td>
          
          <div align="left">
          <a href="admin.logout">
          <img src="/examOnline/images/logout.gif" width="130" height="32" alt="logout" border="0" style="cursor:hand;" >
          </a>
          </div>
          
          </td>
        </tr>
      </table>
    </div></td>
  </tr>
</table>
</logic:notEmpty>
</logic:present>
</body>
</html:html>
