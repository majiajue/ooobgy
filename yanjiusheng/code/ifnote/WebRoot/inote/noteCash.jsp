<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteCashForm form</title>
	</head>
	<body>
		<html:form action="/noteCash" styleId="noteCashForm">
        <p align="center"><img src="/ifnote/images/edit_cash.png" width="601" height="119"></p>
        <hr>
        <table width="100%" border="0">
          <tr>
            <td><div align="center">
            <table width="100%" border="0">
  <tr>
    <td><div align="right">金额：</div></td>
    <td><div align="left">
      <html:text property="account"/>
      <html:errors property="account"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">说明：</div></td>
    <td><div align="left">
      <html:textarea property="comment"/>
      <html:errors property="comment"/>
    </div></td>
  </tr>
</table>

            </div></td>
          </tr>
          <tr>
            <td><div align="center"><img src="/ifnote/images/submit.png" width="72" height="34"
            style="cursor:hand;" onClick="javascript:document.noteCashForm.submit();"></div></td>
          </tr>
        </table>
 
		</html:form>
	</body>
</html>

