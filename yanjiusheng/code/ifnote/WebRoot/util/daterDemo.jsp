<%@ page contentType="text/html;charset=UTF-8" language="java" 
	pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>  
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<html:html lang="true">
<head>
<title></title>
<html:base/>
</head>
<body>
<script language="javascript" type="text/javascript" src="/ifnote/util/dater/My97DatePicker/WdatePicker.js"></script>
<input id="time1" name="time1" class="Wdate" type="text" onfocus="var time22=$dp.$('time22');WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
到
<input id="time2" name="time2" class="Wdate" type="text" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'time1\')}'})"/>
</body>
</html:html>
