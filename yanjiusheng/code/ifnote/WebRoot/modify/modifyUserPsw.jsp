<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for ModifyUserPswForm form</title>
	</head>
	<body>
		<html:form action="/modifyUserPsw">
			oldPsw : <html:password property="oldPsw"/><html:errors property="oldPsw"/><br/>
			psw : <html:password property="psw"/><html:errors property="psw"/><br/>
			psw2 : <html:password property="psw2"/><html:errors property="psw2"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

