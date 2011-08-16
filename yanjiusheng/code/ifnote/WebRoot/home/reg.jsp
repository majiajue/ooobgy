<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for RegForm form</title>
	</head>
	<body>
		<html:form action="/reg">
			phone : <html:text property="phone"/><html:errors property="phone"/><br/>
			username : <html:text property="username"/><html:errors property="username"/><br/>
			email : <html:text property="email"/><html:errors property="email"/><br/>
			psw : <html:password property="psw"/><html:errors property="psw"/><br/>
			psw2 : <html:password property="psw2"/><html:errors property="psw2"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

