<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<html> 
	<head>
		<title>JSP for CashListForm form</title>
    <style type="text/css">
<!--
td {
	font-size: 14px;
	text-overflow:ellipsis;
	overflow:hidden;
	white-space: nowrap;
}
.diantable {
	table-layout:fixed;
	word-wrap:break-word;
	word-break:break-all;
	text-align:center;
}
-->
    </style>
	</head>
	<body>
		<html:form action="/cashList" styleId="cashListForm">
		<script language="javascript" type="text/javascript" src="/ifnote/util/dater/My97DatePicker/WdatePicker.js"></script>
			<p align="center"><img src="/ifnote/images/title_cash.png" width="464" height="131" alt="现金交易管理"></p>
			<hr>
			<table width="100%" border="0">
			  <tr>
			    <td><table width="100%" border="0">
			      <tr>
			        <td width="100">记录时间：</td>
			        <td width="180">
                    	<input id="startTime" name="startTime" class="Wdate" type="text" value='<nested:write property="startTime"/>' onFocus="var time22=$dp.$('time22');WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                    </td>
			        <td width="25">到</td>
			        <td width="180">
                    	<input id="endTime" name="endTime" class="Wdate" type="text" value='<nested:write property="endTime"/>' onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})"/>
                    </td>
			        <td><img src="/ifnote/images/query.png" width="113" height="36" alt="查询" style="cursor:hand;" onClick="javascript:document.cashListForm.submit();"></td>
		          </tr>
		        </table></td>
		      </tr>
			  <tr>
			    <td><table width="400" border="1" class="diantable">
			      <tr>
			        <td width="180">记录时间</td>
			        <td width="100">金额</td>
			        <td width="400">说明</td>
			        <td width="40">修改</td>
			        <td width="40">删除</td>
		          </tr>
			     <nested:iterate id="inote_cash" property="inote_Cashs">
　　				　　<tr>
					<td><nested:write property="note_time"/></td>
　　　　　　				<td><nested:write property="account"/></td>
　　　				　　 　<td><nested:write property="comment"/></td>
					<td>修改</td>
                    <td>删除</td>
　　		　		  </tr>
　　				</nested:iterate>
		        </table></td>
		      </tr>
		  </table>
			<p>&nbsp;</p>
        </html:form>
		
	</body>
</html>

