<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteStockForm form</title>
	</head>
	<body>
		<html:form action="/noteStock" styleId="noteStockForm">
        <p align="center"><img src="/ifnote/images/edit_stock.png" width="595" height="120"> 
        </p>
        <hr>
        <table width="100%" border="0">
          <tr>
            <td><div align="center">
            <table width="100%" border="0">
  <tr>
    <td><div align="right">股票代码：</div></td>
    <td><div align="left">
      <html:text property="stock_code"/>
      <html:errors property="stock_code"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">购买净值：</div></td>
    <td><div align="left">
      <html:text property="smv"/>
      <html:errors property="smv"/>
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
            style="cursor:hand;" onClick="javascript:document.noteStockForm.submit();"></div></td>
          </tr>
        </table>
		</html:form>
	</body>
</html>

