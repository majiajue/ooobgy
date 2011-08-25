<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.frogcherry.util.fileuploadforcdeditor.searchdir.TravelDir"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>图片服务器</title>
<style type="text/css">
body {
	font: 100% 宋体, 新宋体;
	background: #DDDDDD;
	margin: 0; /* 最好将 body 元素的边距和填充设置为 0 以覆盖不同的浏览器默认值 */
	padding: 0;
	text-align: center; /* 在 IE 5* 浏览器中，这会将容器居中。文本随后将在 #container 选择器中设置为默认左对齐 */
	color: #000000;
}
</style>
</head>
<body  scroll=yes>

<%
String baseUrl = "\\UserFiles";
String realPath = application.getRealPath(baseUrl); 

String baseFileUrl = request.getContextPath() + "/UserFiles/";
int col = 0;//计数显示的列数
final int MAX_COL = 6;
int row = 0;
String callback = request.getParameter("CKEditorFuncNum");
String type = request.getParameter("Type");
if(type == null){
	type = "File";
}
baseFileUrl = baseFileUrl + type + "/";
realPath = realPath + "\\" + type;
int typeId = 2;
if(type.equals("File")){
	typeId = 0;
}else if(type.equals("Image")){
	typeId = 1;
}
ArrayList<String> imagelist = (new TravelDir()).getURLResult(realPath,baseUrl);
%>
<div id="header">
<font face="微软雅黑" size="5"> 
文件服务器<br /> 
</font>
<div align="left">
<font face="微软雅黑" size="3">
<%=type %>文件夹路径:<%=baseFileUrl %><br />
您选择了文件：<a id="chosed">????</a><br/></font>
</div>
</div>
<div id="images">
<input type="hidden" id="imgurl" name="imgurl" value="null"/>
<input type="hidden" id="FuncNum" name="FuncNum" value="<%=callback %>"/>
<input type="hidden" id="appUrl" name="appUrl" value="<%=request.getContextPath() %>"/>
<script type="text/javascript"> 
	function transurl(imguname){
		var oldchosed = document.all["imgurl"].value;
		//alert(oldchosed != "null");
		if(oldchosed!= "null"){
			document.all[oldchosed].bgColor = "#EEEFEE";
		}			
		document.all["imgurl"].value = imguname;
		document.all[imguname].bgColor = "#FFFF99";
		document.all["chosed"].innerHTML = imguname;
		//window.parent.CKEDITOR.tools.callFunction(CKEditorFuncNum,"'"+imgunam+"'","''");
	}
	</script>
<script type="text/javascript"> 
	function nowsubmit(imguname){	
		transurl(imguname);
		var callnum = document.all["FuncNum"].value;
		var imgCmpUrl = document.all["appUrl"].value + imguname;
		//alert(imgurlpath);
		//document.imgbrowser.submit();
		window.close();
		window.top.opener.CKEDITOR.tools.callFunction(callnum,imgCmpUrl,"");
		
		//alert("in");
	}    
</script>
<table border="0" align="center">
<tr>
<%for(int i = 0;i<imagelist.size();i++){
	String imgname = imagelist.get(i);
	if(col > MAX_COL){
%>
  <tr>
  <%} %>
  <td  width="126" align="center">
  <table border="0" align="center" id="<%=imgname%>" bgcolor="#EEEFEE" style="word-wrap: break-word; word-break: break-all;">
  <tr>
    <td width="120"  height="106" align="center">
    <a> 
    <div align="center">
    <%if(typeId == 1){ %>  
    <img src="/examOnline/ckeditor/zoomimg?url=<%=imgname%>&targetW=100&targetH=100" style="cursor:hand;" ondblclick="javascript:nowsubmit('<%=imgname%>');" onclick="javascript:transurl('<%=imgname%>');"/></div></a>
   <%}else if(typeId == 0){ %>
   <img src="/examOnline/ckeditor/images/fileicon.jpg" style="cursor:hand;" ondblclick="javascript:nowsubmit('<%=imgname%>');" onclick="javascript:transurl('<%=imgname%>');"/></div></a>
   <%}else if(typeId == 2){ %>
   <img src="/examOnline/ckeditor/images/flashicon.jpg" style="cursor:hand;" ondblclick="javascript:nowsubmit('<%=imgname%>');" onclick="javascript:transurl('<%=imgname%>');"/></div></a>
   <%} %>
</td>
  </tr>
  <tr>
    <td align="center" height="52">
    <a style="cursor:hand;" ondblclick="javascript:nowsubmit('<%=imgname%>');" onclick="javascript:transurl('<%=imgname%>');">
    <font size="2"><%=imgname %>
    </font></a></td>
  </tr>
</table>
  </td>
<%  
	col++;
  	if(col > MAX_COL){
  	col = 0;
%>
  </tr>
  <%} %>
  <%} %>
</table>

</div>
<script type="text/javascript"> 
	function endsub(){	
		var imguname = document.all["imgurl"].value;
		var callnum = document.all["FuncNum"].value;
		var imgCmpUrl = document.all["appUrl"].value + imguname;
		if(imguname != "null"){
			window.close();
			window.top.opener.CKEDITOR.tools.callFunction(callnum,imgCmpUrl,"");
		}
	}    
</script>
<div align="center"><img src="/examOnline/ckeditor/images/chosefile.png" style="cursor:hand;" onclick="javascript:endsub();"/></div>

</body>
</html>