<%@ page contentType="text/html;charset=UTF-8" language="java" 
	pageEncoding="utf-8"%>
<%@ page import="com.ooobgy.ifnote.constants.SecretKey" import="java.util.*" import="com.loquat.datavo.*" import="com.loquat.datacontroller.dao.*" import="com.loquat.datacontroller.daoimpl.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>  
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<html:html lang="true">
<head>
<title>ooobgy ifnote</title>
<SCRIPT LANGUAGE=javascript>
	var munehided = false;
	function loadResize(){
		setTimeout(doreSize,500);
	}
	function reSize(){
		setTimeout(doreSize,80);
	}
	var doreSize = function()
	{
	//alert("resize!");
 		try{ 
 			var oBody = show.document.body;
 			var oFrame = document.all("logframe");
 			var oheight = oBody.scrollHeight>oBody.clientHeight?oBody.scrollHeight:oBody.clientHeight;
 			//var mBody = menu.document.body;
 			//var mFrame = document.all("menu");
 			//var mheight =  mBody.scrollHeight>mBody.clientHeight?mBody.scrollHeight:mBody.clientHeight;
 			//var maxheight = oheight;
 			//if(maxheight < mheight){
 			//	maxheight = mheight;
 			//}
 			oFrame.style.height = oheight;
 			//mFrame.style.height = maxheight;
 		}
 //An error is raised if the IFrame domain != its container's domain
 		catch(e)
 		{
 			window.status = 'Error: ' + e.number + '; ' + e.description;
 		}
	}
	</SCRIPT>
<html:base/>
<!--
	背景色
-->
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"><style type="text/css">
<!--
body {
	background-color: #D4DFFF;
	
}
-->
</style></head>
<body>
<table width="100%" border="0">
  <tr>
  <!--
  	logo = flownew.jpg
  -->
    <td width="75%" height="320"><font face="Times New Roman"><img height="320" width="916" src="/ifnote/images/flownew.jpg" alt="ifnote"></font></td>
    <td width="25%">
		<font face="Times New Roman"><iframe align="left" width="315" height="320" frameborder="0" src="/ifnote/home/login.jsp" name="logframe" id="logframe" onload="resize();"> 
		</iframe><br></font>
	</td>
    </tr>
  <tr>
    <td><font face="Times New Roman">&nbsp;</font></td>
    <td><font face="Times New Roman">&nbsp;</font></td>
    </tr>
</table>
<hr>
<DIV style="FILTER: glow(color=#000000 ,strength=1); COLOR: #6600ff; HEIGHT: 60px"><FONT size=3 face="Times New Roman">
<MARQUEE id=scrollArea style="WIDTH: 1235px; HEIGHT: 40px" onmouseover=scrollArea.stop() onmouseout=scrollArea.start() scrollAmount="2" scrollDelay="5" direction= width="100%" height="50">
<P align=center>ooobgy个人理财系统欢迎您！ <BR>感谢您的支持O(∩_∩)O~  <BR></P></MARQUEE></FONT></DIV>


<br><br><HR/>
	<img src="/ifnote/images/logo.jpg" alt="logo"/>   


</body>
</html:html>
