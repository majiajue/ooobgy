<%@ page contentType="text/html;charset=UTF-8" language="java" 
	pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>  
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<html:html lang="true">
<head>
<title>daterDemo</title>
<html:base/>
</head>
<body>
<script type="text/javascript">
function getdate(obj){
	var topy = obj.offsetTop + 125;
	var leftx = obj.offsetLeft + 10;
	var topoff = obj.offsetHeight;
	topy = topy + topoff;
	var styleData = "dialogWidth:286px;dialogHeight:221px;status:no;help:no;center:no;resizable:no;dialogLeft:" + leftx + ";dialogTop:" + topy;
	//alert(styleData);
	var dateValue = showModalDialog("/examOnline/util/dater.htm", dateValue, styleData);
	obj.value = dateValue;
}
</script>
<input id="date" name="date" type="text" onFocus="getdate(this);"></input>
</body>
</html:html>
