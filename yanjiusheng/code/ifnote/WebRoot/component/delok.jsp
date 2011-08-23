<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除成功</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script language="JavaScript" type="text/javascript">

function delayURL(url) {
var delay = document.getElementById("time").innerHTML;//取到id="time"的对象，.innerHTML取到对象的值
//alert(delay);
if(delay > 0) {
   delay--;
   document.getElementById("time").innerHTML = delay;
} else {
				document.delNoteForm.submit();
    }
    setTimeout("delayURL('" + url + "')", 1000);    //delayURL() 就是每间隔1000毫秒 调用delayURL(url);
}


</script>
  </head>
  
  <body>
  <html:form action="/delNote" styleId="delNoteForm">
		<html:hidden property="delType" styleId="delType"/>
		<html:hidden property="delNid" styleId="delNid"/>
		
  <div align="center"><img height="135" width="315" src="/ifnote/images/del_ok.png" alt="成功删除"></div>
  
  <hr>
  <%String type = (String)session.getAttribute("del_type"); %>
  <div align="center">
<a href="/ifnote/user/<%=type %>List.jsp">
<font size="5" color="#ff0000"><span id="time">3</span></font>秒钟后自动跳转，如果不跳转，请点我
</a>
</div>
<script type="text/javascript">
delayURL("/ifnote/user/<%=type %>List.jsp"); 
</script>
</html:form>
  </body>
</html>
