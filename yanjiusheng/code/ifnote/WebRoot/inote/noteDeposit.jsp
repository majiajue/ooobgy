<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for NoteDepositForm form</title>
	</head>
	<body>
		<html:form action="/noteDeposit">
			bank_name : <html:text property="bank_name"/><html:errors property="bank_name"/><br/>
			rate : <html:text property="rate"/><html:errors property="rate"/><br/>
			sum : <html:text property="sum"/><html:errors property="sum"/><br/>
			comment : <html:text property="comment"/><html:errors property="comment"/><br/>
			type : <html:text property="type"/><html:errors property="type"/><br/>
			dep_time : <html:text property="dep_time"/><html:errors property="dep_time"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

