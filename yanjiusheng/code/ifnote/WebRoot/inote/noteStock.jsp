<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteStockForm form</title>
	</head>
	<body>
		<html:form action="/noteStock">
			count : <html:text property="count"/><html:errors property="count"/><br/>
			smv : <html:text property="smv"/><html:errors property="smv"/><br/>
			stock_code : <html:text property="stock_code"/><html:errors property="stock_code"/><br/>
			comment : <html:textarea property="comment"/><html:errors property="comment"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

