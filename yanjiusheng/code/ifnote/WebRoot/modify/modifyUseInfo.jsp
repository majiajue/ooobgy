<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for ModifyUseInfoForm form</title>
	</head>
	<body>
		<html:form action="/modifyUseInfo">
			phone : <html:text property="phone"/><html:errors property="phone"/><br/>
			email : <html:text property="email"/><html:errors property="email"/><br/>
			psw : <html:password property="psw"/><html:errors property="psw"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

