<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteLoanForm form</title>
	</head>
	<body>
		<html:form action="/noteLoan" styleId="noteLoanForm">
        <p align="center"><img src="/ifnote/images/edit_loan.png" width="453" height="121"></p>
        <hr>
        <table width="100%" border="0">
          <tr>
            <td><div align="center">
            <table width="100%" border="0">
  <tr>
    <td><div align="right">贷款金额：</div></td>
    <td><div align="left">
      <html:text property="sum"/>
      <html:errors property="sum"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">银行：</div></td>
    <td><div align="left">
      <html:text property="bank_name"/>
      <html:errors property="bank_name"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">当前年利率：</div></td>
    <td><div align="left">
      <html:text property="rate"/>
      <html:errors property="rate"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">贷款类型：</div></td>
    <td><div align="left">
      <html:text property="type"/>
      <html:errors property="type"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">贷款时间：</div></td>
    <td><div align="left">
      <html:text property="dep_time"/>
      <html:errors property="dep_time"/>
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
            style="cursor:hand;" onClick="javascript:document.noteLoanForm.submit();"></div></td>
          </tr>
        </table>
		</html:form>
	</body>
</html>

