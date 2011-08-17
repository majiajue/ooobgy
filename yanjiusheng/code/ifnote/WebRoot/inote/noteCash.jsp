<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteCashForm form</title>
	</head>
	<body>
		<html:form action="/noteCash">
			account : <html:text property="account"/><html:errors property="account"/><br/>
			comment : <html:textarea property="comment"/><html:errors property="comment"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

