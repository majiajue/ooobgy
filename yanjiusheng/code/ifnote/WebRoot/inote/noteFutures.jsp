<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteFuturesForm form</title>
	</head>
	<body>
		<html:form action="/noteFutures" styleId="noteFuturesForm">
        <p align="center"><img src="/ifnote/images/edit_future.gif" width="599" height="114"></p>
        <hr>
        <table width="100%" border="0">
          <tr>
            <td><div align="center">
            <table width="100%" border="0">
  <tr>
    <td><div align="right">名称：</div></td>
    <td><div align="left">
      <html:text property="name"/>
      <html:errors property="name"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">购买单价：</div></td>
    <td><div align="left">
      <html:text property="price"/>
      <html:errors property="price"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">购买数目：</div></td>
    <td><div align="left">
      <html:text property="sum"/>
      <html:errors property="sum"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">当前单价：</div></td>
    <td><div align="left">
      <html:text property="now_price"/>
      <html:errors property="now_price"/>
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
</div>
</td>
</tr>
  <tr>
            <td><div align="center"><img src="/ifnote/images/submit.png" width="72" height="34"
             style="cursor:hand;" onClick="javascript:document.noteFuturesForm.submit();"></div></td>
          </tr>

</table>
		</html:form>
	</body>
</html>

