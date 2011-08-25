<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteFundForm form</title>
	</head>
	<body>
		<html:form action="/noteFund" styleId="noteFundForm">
        <p align="center"><img src="/ifnote/images/edit_fund.png" width="597" height="121" alt="基金编辑"> </p>
        <hr>
        <table width="100%" border="0">
          <tr>
            <td><div align="center"><table width="100%" border="0">
  <tr>
    <td><div align="right">基金代码：</div></td>
    <td><div align="left">
      <html:text property="fund_code"/>
      <html:errors property="fund_code"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">购买净值：</div></td>
    <td><div align="left">
      <html:text property="npv"/>
      <html:errors property="npv"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">购买股数：</div></td>
    <td><div align="left">
      <html:text property="count"/>
      <html:errors property="count"/>
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
             style="cursor:hand;" onClick="javascript:document.noteFundForm.submit();"></div></td>
          </tr>
        </table>
	</html:form>
	</body>
</html>

