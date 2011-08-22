<%@ page contentType="text/html;charset=UTF-8" language="java" 
	pageEncoding="utf-8"%>
<%@ page import="com.ooobgy.ifnote.constants.SecretKey"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>  
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<html:html lang="true">
<head>
<title>用户信息页面</title>
<html:base/>
</head>
<body>
<%String userKey = SecretKey.USER_KEY; %>
<logic:present name="<%=userKey %>" scope="session">
<bean:define id="user" name="<%=userKey %>" scope="session" type="com.ooobgy.ifnote.entity.User"></bean:define>
<logic:notEmpty name="user">
<table width="100%" border="0" align="center">
  <tr>
    <td><div align="center">欢迎您，<bean:write name="user" property="userName"/></div></td>
  </tr>
  <tr>
    <td><div align="center">
      <table width="100%" border="0">
        <tr>
          <td><div align="right">会员号：</div></td>
          <td><div align="left"><bean:write name="user" property="id"/></div></td>
        </tr>
        <tr>
          <td><div align="right">姓名：  </div></td>
          <td><div align="left"><bean:write name="user" property="userName"/></div></td>
        </tr>
      </table>
    </div></td>
  </tr>
  <tr>
    <td><div align="center">
      <table width="100%" border="0">
        <tr>
          <td><div align="right"><a href="/ifnote/user/userIndex.jsp"  target="_parent">
            <img src="/ifnote/images/sysuser.gif" width="130" height="32" alt="进入管理系统" border="0">
          </a></div></td>
          <td>
          
          <div align="left">
          <a href="user.logout">
          <img src="/ifnote/images/logout.gif" width="130" height="32" alt="logout" border="0" style="cursor:hand;" >
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
