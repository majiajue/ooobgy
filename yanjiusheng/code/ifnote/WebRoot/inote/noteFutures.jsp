<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteFuturesForm form</title>
	</head>
	<body>
		<html:form action="/noteFutures">
			price : <html:text property="price"/><html:errors property="price"/><br/>
			name : <html:text property="name"/><html:errors property="name"/><br/>
			sum : <html:text property="sum"/><html:errors property="sum"/><br/>
			comment : <html:textarea property="comment"/><html:errors property="comment"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

