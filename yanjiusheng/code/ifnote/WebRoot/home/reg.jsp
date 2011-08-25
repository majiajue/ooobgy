<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>注册</title>
	</head>
	<body>
		<html:form action="/reg" styleId="regForm">
        <table width="100%" border="0">
  <tr>
    <td align="center"><font face="微软雅黑" size="5" color="#0000ff">快速注册</font></td>
  </tr>
  <tr>
    <td><table width="100%" border="0">
  <tr>
    <td><div align="right">用户名：</div></td>
    <td><div align="left">
      <html:text property="username"/>
      <html:errors property="username"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">密码：</div></td>
    <td><div align="left">
      <html:password property="psw"/>
      <html:errors property="psw"/>
    </div></td>
  </tr>
  <tr>
    <td><div align="right">确认密码：</div></td>
    <td><div align="left">
      <html:password property="psw2"/>
      <html:errors property="psw2"/>
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
    <td><div align="right">手机：</div></td>
    <td><div align="left">
      <html:text property="phone"/>
      <html:errors property="phone"/>
    </div></td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td align="center">同意服务条款并<img src="/ifnote/images/submit.png" width="72" height="34" alt="提交" style="cursor:hand;" 
    onClick="javascript:document.regForm.submit();"></td>
  </tr>
</table>
		</html:form>
	</body>
</html>

