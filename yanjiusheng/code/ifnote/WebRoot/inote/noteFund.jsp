<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteFundForm form</title>
	</head>
	<body>
		<html:form action="/noteFund">
			fund_code : <html:text property="fund_code"/><html:errors property="fund_code"/><br/>
			count : <html:text property="count"/><html:errors property="count"/><br/>
			npv : <html:text property="npv"/><html:errors property="npv"/><br/>
			comment : <html:text property="comment"/><html:errors property="comment"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

