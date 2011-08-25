<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for ModifyUserPswForm form</title>
	</head>
	<body>
		<html:form action="/modifyUserPsw" styleId="modifyUserPswForm">
        <p align="center"><img src="/ifnote/images/modify_user_psw.png" width="304" height="115"></p>
        <hr>

        <table width="100%" border="0">
          <tr>
    <td><div align="center">
      <table width="100%" border="0">
  <tr>
    <td>当前密码：</td>
    <td><html:password property="oldPsw"/><html:errors property="oldPsw"/></td>
  </tr>
  <tr>
    <td>新密码：</td>
    <td><html:password property="psw"/><html:errors property="psw"/></td>
  </tr>
  <tr>
    <td>重复新密码：</td>
    <td><html:password property="psw2"/><html:errors property="psw2"/></td>
  </tr>
</table>

      </div>/td>
    </tr>
  <tr>
    <td><div align="center"><img src="/ifnote/images/submit.png" width="72" height="34"
            style="cursor:hand;" onClick="javascript:document.modifyUserPswForm.submit();"></div></td>
    </tr>
</table>
		</html:form>
	</body>
</html>

