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
	text-align:center;
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
		<html:form action="/summary" styleId="summaryForm">
<bean:define id="disp_Cashs" property="disp_Cashs" type="java.util.List" name="summaryForm"></bean:define>
<bean:define id="disp_Deposits" property="disp_Deposits" type="java.util.List" name="summaryForm"></bean:define>
<bean:define id="disp_Funds" property="disp_Funds" type="java.util.List" name="summaryForm"></bean:define>
<bean:define id="disp_Futures" property="disp_Futures" type="java.util.List" name="summaryForm"></bean:define>
<bean:define id="disp_Loans" property="disp_Loans" type="java.util.List" name="summaryForm"></bean:define>
<bean:define id="disp_Stocks" property="disp_Stocks" type="java.util.List" name="summaryForm"></bean:define>

<bean:define id="sum_cash" property="sum_cash" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="sum_deposit" property="sum_deposit" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="earn_deposit" property="earn_deposit" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="sum_fund" property="sum_fund" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="earn_fund" property="earn_fund" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="sum_futures" property="sum_futures" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="earn_futures" property="earn_futures" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="sum_loan" property="sum_loan" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="earn_loan" property="earn_loan" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="sum_stock" property="sum_stock" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="earn_stock" property="earn_stock" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="sum_total" property="sum_total" type="java.lang.Double" name="summaryForm"></bean:define>
<bean:define id="earn_total" property="earn_total" type="java.lang.Double" name="summaryForm"></bean:define>

<bean:define id="sumPieData" property="sumPieData" type="java.lang.String" name="summaryForm"></bean:define>
<bean:define id="earnPieData" property="earnPieData" type="java.lang.String" name="summaryForm"></bean:define>
<bean:define id="sumPieTitle" property="sumPieTitle" type="java.lang.String" name="summaryForm"></bean:define>
<bean:define id="earnPieTitle" property="earnPieTitle" type="java.lang.String" name="summaryForm"></bean:define>

		
		
			<p align="center"><img src="/ifnote/images/title_sum.png" width="454" height="117" alt="理财概况"></p>
			<hr>
			<table width="1000" border="0">
              <tr>
                <td align="center" width="350" height="320"><img src="/ifnote/sum.PieChart?title=<bean:write name="sumPieTitle"/>&width=300&height=300&data=<bean:write name="sumPieData"/>" width="300" height="300"></td>
                <td align="center">
                	<table width="280" border="1">
  <tr>
    <td width="60">&nbsp;</td>
    <td width="120">资产</td>
    <td width="100">盈亏</td>
  </tr>
  <tr>
    <td><font size="4">合计</font></td>
    <td><font size="4"><bean:write name="sum_total"/></font></td>
    <td><font size="4"><bean:write name="earn_total"/></font></td>
  </tr>
  <tr>
    <td><a href="#cash">现金</a></td>
    <td><a href="#cash"><bean:write name="sum_cash"/></a></td>
    <td><a href="#cash">——</a></td>
  </tr>
  <tr>
    <td><a href="#deposit">存款</a></td>
    <td><a href="#deposit"><bean:write name="sum_deposit"/></a></td>
    <td><a href="#deposit"><bean:write name="earn_deposit"/></a></td>
  </tr>
  <tr>
    <td><a href="#loan">贷款</a></td>
    <td><a href="#loan"><bean:write name="sum_loan"/></a></td>
    <td><a href="#loan"><bean:write name="earn_loan"/></a></td>
  </tr>
  <tr>
    <td><a href="#stock">股票</a></td>
    <td><a href="#stock"><bean:write name="sum_stock"/></a></td>
    <td><a href="#stock"><bean:write name="earn_stock"/></a></td>
  </tr>
  <tr>
    <td><a href="#fund">基金</a></td>
    <td><a href="#fund"><bean:write name="sum_fund"/></a></td>
    <td><a href="#fund"><bean:write name="earn_fund"/></a></td>
  </tr>
  <tr>
    <td><a href="#futures">期货</a></td>
    <td><a href="#futures"><bean:write name="sum_futures"/></a></td>
    <td><a href="#futures"><bean:write name="earn_futures"/></a></td>
  </tr>
</table>
<div align="right">单位：元</div>

                </td>
                <td align="center" width="350" height="320"><img src="/ifnote/sum.PieChart?title=<bean:write name="earnPieTitle"/>&width=300&height=300&data=<bean:write name="earnPieData"/>" width="300" height="300"></td>
              </tr>
            </table>
<hr>

<a name="cash"></a> 
<table width="100%" border="0">
  <tr>
    <td><font size="6" face="微软雅黑">现金概况</font></td>
  </tr>
  <tr>
    <td><table width="100%" border="0">
  <tr>
    <td>&nbsp;</td>
    <td><font face="微软雅黑" size="4" color="#ff0000">现金资产：&nbsp;&nbsp;<bean:write name="sum_cash"/></font></td>
    <td><font size="4" face="微软雅黑">现金盈亏：&mdash;&mdash;</font></td>
  </tr>
  <tr>
    <td><font size="4" face="微软雅黑">最近交易记录：</font></td>
    <td>&nbsp;</td>
    <td><a href="/ifnote/user/cashList.jsp">查看更多</a></td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td>
    <table width="100%" border="1">
  <tr>
        <td width="180">记录时间</td>
                        <td width="100">金额</td>
                        <td width="400">说明</td>           
  </tr>
  
<bean:define id="disp_Cashs" name="summaryForm" property="disp_Cashs" type="java.util.List"></bean:define>			     
			     <logic:iterate id="inote" name="disp_Cashs" type="com.ooobgy.ifnote.dispbeans.Disp_Cash" >
				<tr>
				<td><bean:write name="inote" property="note_time" /></td>
				<td><bean:write name="inote" property="account" /></td>
				<td><bean:write name="inote" property="comment" /></td>
                </tr>
				</logic:iterate>
  
</table>
    </td>
  </tr>
</table>
<hr>

<a name="deposit"></a> 
<table width="100%" border="0">
  <tr>
    <td><font size="6" face="微软雅黑">存款概况</font></td>
  </tr>
  <tr>
    <td><table width="100%" border="0">
  <tr>
    <td>&nbsp;</td>
    <td><font face="微软雅黑" size="4" color="#ff0000">存款资产：&nbsp;&nbsp;<bean:write name="sum_deposit"/></font></td>
    <td><font size="4" face="微软雅黑">存款盈亏：&nbsp;&nbsp;<bean:write name="earn_deposit"/></font></td>
  </tr>
  <tr>
    <td><font size="4" face="微软雅黑">最近交易记录：</font></td>
    <td>&nbsp;</td>
    <td><a href="/ifnote/user/depositList.jsp">查看更多</a></td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td>
    <table border="1" class="diantable">
			      <tr>
			        <td width="170">记录时间</td>
			        <td width="100">金额</td>
			        <td width="80">类型</td>
			        <td width="60">年利率</td>
			        <td width="160">银行</td>
			        <td width="300">说明</td>
		          </tr>
		          <bean:define id="disp_Deposits" name="summaryForm" property="disp_Deposits" type="java.util.List"></bean:define>			     
			     <logic:iterate id="inote" name="disp_Deposits" type="com.ooobgy.ifnote.dispbeans.Disp_Deposit" >
				<tr>
				<td><bean:write name="inote" property="note_time" /></td>
				<td><bean:write name="inote" property="sum" /></td>
				<td><bean:write name="inote" property="type" /></td>
				<td><bean:write name="inote" property="rate" /></td>
				<td><bean:write name="inote" property="bank_name" /></td>
				<td><bean:write name="inote" property="comment" /></td>
			
                    </tr>
				</logic:iterate>
		        </table>
    </td>
  </tr>
</table>
<hr>

<a name="loan"></a> 
<table width="100%" border="0">
  <tr>
    <td><font size="6" face="微软雅黑">贷款概况</font></td>
  </tr>
  <tr>
    <td><table width="100%" border="0">
  <tr>
    <td>&nbsp;</td>
    <td><font face="微软雅黑" size="4" color="#ff0000">贷款资产：&nbsp;&nbsp;<bean:write name="sum_loan"/></font></td>
    <td><font size="4" face="微软雅黑">贷款盈亏：&nbsp;&nbsp;<bean:write name="earn_loan"/></font></td>
  </tr>
  <tr>
    <td><font size="4" face="微软雅黑">最近交易记录：</font></td>
    <td>&nbsp;</td>
    <td><a href="/ifnote/user/loanList.jsp">查看更多</a></td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td>
    <table border="1" class="diantable">
			      <tr>
			        <td width="170">记录时间</td>
			        <td width="100">金额</td>
			        <td width="80">类型</td>
			        <td width="60">年利率</td>
			        <td width="160">银行</td>
			        <td width="300">说明</td>
		          </tr>
		          <bean:define id="disp_Loans" name="summaryForm" property="disp_Loans" type="java.util.List"></bean:define>			     
			     <logic:iterate id="inote" name="disp_Loans" type="com.ooobgy.ifnote.dispbeans.Disp_Loan" >
				<tr>
				<td><bean:write name="inote" property="note_time" /></td>
				<td><bean:write name="inote" property="sum" /></td>
				<td><bean:write name="inote" property="type" /></td>
				<td><bean:write name="inote" property="rate" /></td>
				<td><bean:write name="inote" property="bank_name" /></td>
				<td><bean:write name="inote" property="comment" /></td>
                    </tr>
				</logic:iterate>
		        </table>
    </td>
  </tr>
</table>
<hr>

<a name="fund"></a> 
<table width="100%" border="0">
  <tr>
    <td><font size="6" face="微软雅黑">基金概况</font></td>
  </tr>
  <tr>
    <td><table width="100%" border="0">
  <tr>
    <td>&nbsp;</td>
    <td><font face="微软雅黑" size="4" color="#ff0000">基金资产：&nbsp;&nbsp;<bean:write name="sum_fund"/></font></td>
    <td><font size="4" face="微软雅黑">基金盈亏：&nbsp;&nbsp;<bean:write name="earn_fund"/></font></td>
  </tr>
  <tr>
    <td><font size="4" face="微软雅黑">最近交易记录：</font></td>
    <td>&nbsp;</td>
    <td><a href="/ifnote/user/fundList.jsp">查看更多</a></td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td>
    <table border="1" class="diantable">
			      <tr>
			        <td width="180">记录时间</td>
			        <td width="80">基金代码</td>
			        <td width="100">基金名称</td>
			        <td width="60">购入净值</td>
                    <td width="60">当前净值</td>
			        <td width="60">购入股数</td>
                    <td width="100">投资额</td>
                    <td width="100">盈亏</td>
			        <td width="200">说明</td>
		          </tr>
		          <bean:define id="disp_Funds" name="summaryForm" property="disp_Funds" type="java.util.List"></bean:define>			     
			     <logic:iterate id="inote" name="disp_Funds" type="com.ooobgy.ifnote.dispbeans.Disp_Fund" >
				<tr>
				<td><bean:write name="inote" property="note_time" /></td>
				<td><bean:write name="inote" property="fund_code" /></td>
				<td><bean:write name="inote" property="name" /></td>
				<td><bean:write name="inote" property="npv" /></td>
                <td><bean:write name="inote" property="now_npv" /></td>
				<td><bean:write name="inote" property="count" /></td>
                <td><bean:write name="inote" property="asset" /></td>
				<td><bean:write name="inote" property="profit" /></td>
				<td><bean:write name="inote" property="comment" /></td>
                    </tr>
				</logic:iterate>
		        </table>
    </td>
  </tr>
</table>
<hr>

<a name="stock"></a> 
<table width="100%" border="0">
  <tr>
    <td><font size="6" face="微软雅黑">股票概况</font></td>
  </tr>
  <tr>
    <td><table width="100%" border="0">
  <tr>
    <td>&nbsp;</td>
    <td><font face="微软雅黑" size="4" color="#ff0000">股票资产：&nbsp;&nbsp;<bean:write name="sum_stock"/></font></td>
    <td><font size="4" face="微软雅黑">股票盈亏：&nbsp;&nbsp;<bean:write name="earn_stock"/></font></td>
  </tr>
  <tr>
    <td><font size="4" face="微软雅黑">最近交易记录：</font></td>
    <td>&nbsp;</td>
    <td><a href="/ifnote/user/stockList.jsp">查看更多</a></td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td>
    <table border="1" class="diantable">
			      <tr>
			        <td width="180">记录时间</td>
			        <td width="80">股票代码</td>
			        <td width="100">股票名称</td>
			        <td width="60">购入净值</td>
                    <td width="60">当前净值</td>
			        <td width="60">购入股数</td>
                    <td width="100">投资额</td>
			        <td width="100">盈亏</td>
			        <td width="200">说明</td>
		          </tr>
		          <bean:define id="disp_Stocks" name="summaryForm" property="disp_Stocks" type="java.util.List"></bean:define>			     
			     <logic:iterate id="inote" name="disp_Stocks" type="com.ooobgy.ifnote.dispbeans.Disp_Stock" >
				<tr>
				<td><bean:write name="inote" property="note_time" /></td>
				<td><bean:write name="inote" property="stock_code" /></td>
				<td><bean:write name="inote" property="name" /></td>
				<td><bean:write name="inote" property="smv" /></td>
                <td><bean:write name="inote" property="now_smv" /></td>
				<td><bean:write name="inote" property="count" /></td>
                <td><bean:write name="inote" property="asset" /></td>
				<td><bean:write name="inote" property="profit" /></td>
				<td><bean:write name="inote" property="comment" /></td>
                    </tr>
				</logic:iterate>
		        </table>
    </td>
  </tr>
</table>
<hr>

<a name="futures"></a> 
<table width="100%" border="0">
  <tr>
    <td><font size="6" face="微软雅黑">期货概况</font></td>
  </tr>
  <tr>
    <td><table width="100%" border="0">
  <tr>
    <td>&nbsp;</td>
    <td><font face="微软雅黑" size="4" color="#ff0000">期货资产：&nbsp;&nbsp;<bean:write name="sum_futures"/></font></td>
    <td><font size="4" face="微软雅黑">期货盈亏：&nbsp;&nbsp;<bean:write name="earn_futures"/></font></td>
  </tr>
  <tr>
    <td><font size="4" face="微软雅黑">最近交易记录：</font></td>
    <td>&nbsp;</td>
    <td><a href="/ifnote/user/futuresList.jsp">查看更多</a></td>
  </tr>
</table>
</td>
  </tr>
  <tr>
    <td>
    <table border="1" class="diantable">
			      <tr>
			        <td width="180">记录时间</td>
			        <td width="120">名称</td>
			        <td width="80">购入单价</td>
                    <td width="80">当前单价</td>
			        <td width="80">购入数量</td>
                    <td width="100">投资额</td>
			        <td width="100">盈亏</td>
			        <td width="200">说明</td>
		          </tr>
		          <bean:define id="disp_Futures" name="summaryForm" property="disp_Futures" type="java.util.List"></bean:define>			     
			     <logic:iterate id="inote" name="disp_Futures" type="com.ooobgy.ifnote.dispbeans.Disp_Futures" >
				<tr>
				<td><bean:write name="inote" property="note_time" /></td>
				<td><bean:write name="inote" property="name" /></td>
				<td><bean:write name="inote" property="price" /></td>
                <td><bean:write name="inote" property="now_price" /></td>
				<td><bean:write name="inote" property="sum" /></td>
                <td><bean:write name="inote" property="asset" /></td>
				<td><bean:write name="inote" property="profit" /></td>
				<td><bean:write name="inote" property="comment" /></td>
                    </tr>
				</logic:iterate>
		        </table>
    </td>
  </tr>
</table>
<hr>

</html:form>
	</body>
</html>

