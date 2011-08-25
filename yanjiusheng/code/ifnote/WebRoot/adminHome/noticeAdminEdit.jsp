<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%@page import="com.loquat.constants.SecretKey"%>
<%String studentKey = SecretKey.ADMIN_KEY;
	if(session.getAttribute(studentKey)==null)
{
 %>

 <jsp:forward page="/error/error.html"/> 
<%

}
 %>
 
<html> 
	<head>
		<title>通知管理页面</title>
		<script language="javascript" type="text/javascript" src="/examOnline/util/dater/My97DatePicker/WdatePicker.js"></script>
	  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">	
    <style type="text/css">
    body   {background-image:   url(/examOnline/images/backpic.jpg); 
			background-position:   center top; 
			background-repeat:   repeat; 
			} 
    </style> 
	</head>
	<body>
	<div align="center">
<font size="7" color="#004080"><strong><font face="华文新魏"><br><br>编辑通知</font></strong></font>
	<br/><br/>
	<table width="1000" border="0" align="center">
      <tr>
      	<td width="15%"></td>
        <td width="1000"><div align="right">
        <img src="/examOnline/images/adminlog.gif" alt="lock" height="20" width="20"/> 
        <a   href=#   onclick= "javascript:history.back(-1); ">
        	<font size="2">后退一页</font></a></div>
        	</td>
        <td width="150"><div align="right">
        	<img src="/examOnline/images/lock.gif" alt="lock" height="20" width="20"/>
        	<a href="/examOnline/adminHome/adminHome.jsp"><font size="2">个人主页</font></a>
        </div></td>
        <td width="150"><div align="right">
        	<img height="20" width="20" src="/examOnline/images/exit.gif" alt="exit">
        	<a href="/examOnline/admin.logout?inf=in"><font size="2">安全退出</font></a>
        </div></td>
      </tr>
    </table>
</div>
<hr>
</div>
		<html:form action="/noticeAdminEdit" styleId="editForm">
    <table width="100%" border="0" align="center">
	      <tr>
	        <td><table width="90%" border="0" align="center">
	          <tr>
	            <td width="12%"></td>
	            <td width="30%">标题：<html:text property="title" size="30"/>
                	<font color="#ff0000"><html:errors property="title" /></font>
                </td>
                <td width="23%">有效时间：
                <html:text property="vtime" styleId="vtime" styleClass="Wdate" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>               
                	<font color="#ff0000"><html:errors property="vtime"/></font>
                </td>
	            <td width="33%">编辑：<html:text property="editor"/>
                	<font color="#ff0000"><html:errors property="editor"/></font>
                </td>
              </tr>
            </table></td>
      </tr>
	      <tr>
	        
	        <td align="center"><html:textarea cols="80" rows="10" property="content"/>
            	<font color="#ff0000"><html:errors property="content"/></font>
            	<script type="text/javascript" src="/examOnline/ckeditor/ckeditor.js"></script>
   <script type="text/javascript">   
        CKEDITOR.replace('content',{filebrowserUploadUrl : '/examOnline/ckeditor/uploader?Type=File',   
		filebrowserImageUploadUrl : '/examOnline/ckeditor/uploader?Type=Image',   
		filebrowserFlashUploadUrl : '/examOnline/ckeditor/uploader?Type=Flash', 
		filebrowserBrowseUrl :'/examOnline/ckeditor/filebrowser.jsp?Type=File',
		filebrowserImageBrowseUrl :'/examOnline/ckeditor/filebrowser.jsp?Type=Image',
		filebrowserFlashBrowseUrl :'/examOnline/ckeditor/filebrowser.jsp?Type=Flash'
        });   
</script> 
            </td>
      </tr>
    </table>
    <br>
    <center><img src="/examOnline/images/subdef.gif" onClick="javascript:document.editForm.submit();" alt="添加新信息" style="cursor:hand;" align="center" /></center>
    </html:form>
	</body>
</html>

