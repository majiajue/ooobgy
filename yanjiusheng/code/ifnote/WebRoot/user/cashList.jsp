<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<html> 
	<head>
		<title>JSP for CashListForm form</title>
	</head>
	<body>
		<html:form action="/cashList">
		<script language="javascript" type="text/javascript" src="/ifnote/util/dater/My97DatePicker/WdatePicker.js"></script>
startTime : <input id="startTime" name="startTime" class="Wdate" type="text" value='<nested:write property="startTime"/>' onfocus="var time22=$dp.$('time22');WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
endTime : <input id="endTime" name="endTime" class="Wdate" type="text" value='<nested:write property="endTime"/>' onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})"/>
			
			<html:submit/><html:cancel/>
			<table border="1">
			<nested:iterate id="inote_cash" property="inote_Cashs">
　　　　<tr>
			<td><nested:write property="note_time"/></td>
　　　　　　<td><nested:write property="account"/></td>
　　　　　　<td><nested:write property="comment"/></td>
　　　　</tr>
　　　　</nested:iterate>
			</table>
			
			
		</html:form>
		
	</body>
</html>

