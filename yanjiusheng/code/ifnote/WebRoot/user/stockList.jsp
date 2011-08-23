<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<html> 
	<head>
		<title>JSP for stockListForm form</title>
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
		<html:form action="/stockList" styleId="stockListForm">
		<script language="javascript" type="text/javascript" src="/ifnote/util/dater/My97DatePicker/WdatePicker.js"></script>
			<p align="center"><img src="/ifnote/images/title_stock.png" width="464" height="131" alt="股票投资管理"></p>
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
			        <td><img src="/ifnote/images/query.png" width="113" height="36" alt="查询" style="cursor:hand;" onClick="javascript:document.stockListForm.submit();"></td>
                    <td align="right"><img src="/ifnote/images/add_ico.jpg" width="113" height="36" alt="新增" style="cursor:hand;" onClick="window.location.href('/ifnote/inote/noteStock.jsp')"></td>
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
		          <bean:define id="inote_Stocks" name="stockListForm" property="inote_Stocks" type="java.util.List"></bean:define>			     
			     <logic:iterate id="inote" name="inote_Stocks" type="com.ooobgy.ifnote.entity.Inote_Stock" >
				<tr>
				<td><bean:write name="inote" property="note_time" /></td>
				<td><bean:write name="inote" property="account" /></td>
				<td><bean:write name="inote" property="comment" /></td>
				<td>
                    	<img src="/ifnote/images/edit.gif" width="16" height="16" alt="修改" style="cursor:hand;" 
                    	onClick="window.location.href('/ifnote/inote/noteStock.jsp?nid=<bean:write name="inote" property="id"/>')"/>
                    </td>
                    <td>
                    	<img src="/ifnote/images/del.gif" width="16" height="16" alt="删除" style="cursor:hand;" 
                    	onClick="submitDel(<bean:write name="inote" property="id"/>)"/>
                    </td>
                    </tr>
				</logic:iterate>
		        </table></td>
		      </tr>
		  </table>
			<p>&nbsp;</p>
        </html:form>
        
		
		<script language="javascript" type="text/javascript">
			function submitDel(nid){
				var conf = confirm("您真的要删除这条记录吗?");
				if   (!conf)   return;
				else{
					var link = '/ifnote/component/delok.jsp?type=stock&nid=' + nid;
					window.location.href(link);
					
				}
			}
		</script>
	</body>
</html>

