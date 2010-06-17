<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for MakeDataForm form</title>
	</head>
	<body>
		<html:form action="/makeData">
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

