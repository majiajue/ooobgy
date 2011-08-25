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
    
    <title>修改成功</title>
    
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
				window.location.href(url);
    }
    setTimeout("delayURL('" + url + "')", 500);    //delayURL() 就是每间隔500毫秒 调用delayURL(url);
}


</script>
  </head>
  
  <body>
  <div align="center"><img src="/ifnote/images/modify_ok.png" alt="成功删除"></div>
  
  <hr>
  <div align="center">
<a href="/ifnote/user/summary.jsp">
<font size="5" color="#ff0000"><span id="time">3</span></font>秒钟后自动跳转到个人中心主页，如果不跳转，请点我
</a>
</div>
<script type="text/javascript">
delayURL("/ifnote/user/summary.jsp"); 
</script>
  </body>
</html>
