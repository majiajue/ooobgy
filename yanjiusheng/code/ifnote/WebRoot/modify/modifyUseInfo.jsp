<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for ModifyUseInfoForm form</title>
	</head>
	<body>
		<html:form action="/modifyUseInfo" styleId="modifyUseInfoForm">
        <p align="center"><img src="/ifnote/images/modify_user_info.png" width="440" height="106"></p>
        <hr>
   
        <table width="100%" border="0">
          <tr>
    <td><div align="center">
      <table width="100%" border="0">
        <tr>
          <td><div align="right">当前密码：</div></td>
          <td><div align="left">
            <html:password property="psw"/>
            <html:errors property="psw"/>
            </div></td>
          </tr>
        <tr>
          <td><div align="right">Email：</div></td>
          <td><div align="left">
            <html:text property="email"/>
            <html:errors property="email"/>
            </div></td>
          </tr>
        <tr>
          <td><div align="right">Phone：</div></td>
          <td><div align="left">
            <html:text property="phone"/>
            <html:errors property="phone"/>
            </div></td>
          </tr>
  </table>
      
      </div></td>
    </tr>
  <tr>
    <td><div align="center"><img src="/ifnote/images/submit.png" width="72" height="34"
            style="cursor:hand;" onClick="javascript:document.modifyUseInfoForm.submit();"></div></td>
    </tr>
</table>
		</html:form>
	</body>
</html>

