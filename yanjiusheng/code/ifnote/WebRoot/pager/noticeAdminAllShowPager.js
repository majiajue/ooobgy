/**
 * @author : CHE.R.RY (周晓龙)
 * @Email : frogcherry@gmail.com
 * @Date : 2010-6-1
 */
var pagerinfo;//分页信息


// 初始化分页,请在body的onload中调用
function pageinit() {
	// 初始化pager工具栏
	initpagerctr();
	noticeAdminPagerServer.initPager(generatetable);
}

// 下一页
function nextpage() {
	noticeAdminPagerServer.nextPage(pagerinfo, generatetable);
}

// 上一页
function prevpage() {
	noticeAdminPagerServer.prevPage(pagerinfo, generatetable);
}

// 首页
function firstpage() {
	noticeAdminPagerServer.firstPage(pagerinfo, generatetable);
}

// 前十页
function prevTENpage() {
	var pageid = pagerinfo.nowPage - 10;
	if (pageid < 1) {// 越界，超过第一页
		pageid = 1;
	}
	noticeAdminPagerServer.gotoPage(pageid, pagerinfo, generatetable);
}

// 后十页
function nextTENpage() {
	var pageid = pagerinfo.nowPage + 10;
	if (pageid > pagerinfo.pageCount) {// 越界，超过最后一页
		pageid = pagerinfo.pageCount;
	}
	noticeAdminPagerServer.gotoPage(pageid, pagerinfo, generatetable);
}

// 末页
function lastpage() {
	noticeAdminPagerServer.lastPage(pagerinfo, generatetable);
}

// goto某一页pagebtn快速按钮
function gotoapage(pid) {
	var nowpage = pagerinfo.nowPage;
	var pagebase = parseInt((nowpage - 1) / 10) * 10;
	var pageid = pagebase + pid;
	noticeAdminPagerServer.gotoPage(pageid, pagerinfo, generatetable);
}

// goto某一页快速go
function gotopage() {
	var pageid = parseInt(document.getElementById("gotopagenum").value);
	noticeAdminPagerServer.gotoPage(pageid, pagerinfo, generatetable);
}

// 改变分页大小
function changepagesize(size) {
	var psize = parseInt(size);
	noticeAdminPagerServer.changePageSize(psize, pagerinfo, generatetable);
}

// 初始化pager工具栏
function initpagerctr() {
	var i = 1;
	var psize = 11;
	// 获得工具栏
	var ctr = document.getElementById("pagerctr");
	// 设置工具栏
	var tools = "<td><a id=\"prevpagebtn\" href=\"javascript:void(0)\" onClick=\"prevpage()\"><font size=\"2\">上一页</font></a>&nbsp;</td>";

	tools = tools
			+ "<td><font size=\"2\"><a id=\"nextpagebtn\" href=\"javascript:void(0)\" onClick=\"nextpage()\">下一页</a>&nbsp;</font></td>";
	tools = tools
			+ "<td><font size=\"2\"><a id=\"firstpagebtn\" href=\"javascript:void(0)\" onClick=\"firstpage()\">首页</a>&nbsp;</font></td>";
	tools = tools
			+ "<td><font size=\"2\"><a id=\"prevTENpagebtn\" href=\"javascript:void(0)\" onClick=\"prevTENpage()\"><<<</a>&nbsp;</font></td>";

	// 快速页面导航
	while (i < psize) {
		tools = tools + "<td>";
		tools = tools + "<font size=\"2\"><a id=\"pagebtn" + i
				+ "\" href=\"javascript:void(0)\" onClick=\"gotoapage(" + i
				+ ")\">" + i + "&nbsp;</a></font></td>";
		i = i + 1;
		tools = tools + "</td>";
	}

	// alert(tools);
	tools = tools
			+ "<td><font size=\"2\"><a id=\"nextTENpagebtn\" href=\"javascript:void(0)\" onClick=\"nextTENpage()\">>>></a>&nbsp;</font></td>";
	tools = tools
			+ "<td><font size=\"2\"><a id=\"lastpagebtn\" href=\"javascript:void(0)\" onClick=\"lastpage()\">尾页</a>&nbsp;</font></td>";
	tools = tools + "<td><font size=\"2\">转到";

	tools = tools
			+ "<input type=\"text\" id=\"gotopagenum\" size=\"1\" value=\""
			+ "1"
			+ "\" onpropertychange=\"javascript:CheckInputInt(this);\"></input>";

	tools = tools
			+ "页&nbsp;<input type=\"button\" onClick=\"gotopage()\" value=\"Go\"></input></font></td>";
	// 更改工具栏
	ctr.innerHTML = tools;
	// alert(tools);
	// 一些按钮需要失效
	document.getElementById("firstpagebtn").disabled = true;
	document.getElementById("prevpagebtn").disabled = true;
	document.getElementById("pagebtn1").disabled = true;
	document.getElementById("prevTENpagebtn").disabled = true;

	// 初始化页面大小编辑器
	var psizetool = "";
	psizetool = "<font size=\"2\">每页显示<select name=\"chosepageSize\" id=\"chosepageSize\" onchange=\"changepagesize(this.value)\">";
	psizetool = psizetool + "<option value=\"5\">5</option>";
	psizetool = psizetool
			+ "<option value=\"10\" selected=\"selected\">10</option>";
	psizetool = psizetool + "<option value=\"15\">15</option>";
	psizetool = psizetool + "<option value=\"20\">20</option>";
	psizetool = psizetool + "<option value=\"30\">30</option>";
	psizetool = psizetool + "<option value=\"50\">50</option>";
	psizetool = psizetool + "<option value=\"100\">100</option>";
	psizetool = psizetool + "</select>条记录</font>";

	var chgpagersize = document.getElementById("cgpagerpsize");
	chgpagersize.innerHTML = psizetool;
}

// 更新pager工具栏
function refreshpagerctr(tpagerinfo) {
	var nowpage = tpagerinfo.nowPage;
	var temppsize = tpagerinfo.pageSize;
	var pagebase = parseInt((nowpage - 1) / 10) * 10;
	var lastbase = parseInt((tpagerinfo.pageCount - 1) / 10) * 10;
	var nowpagetail = nowpage - pagebase;
	// 1.enable所有工具
	document.getElementById("prevpagebtn").disabled = false;
	document.getElementById("nextpagebtn").disabled = false;
	document.getElementById("firstpagebtn").disabled = false;
	document.getElementById("prevTENpagebtn").disabled = false;
	document.getElementById("nextTENpagebtn").disabled = false;
	document.getElementById("lastpagebtn").disabled = false;
	// 2.更新快速按钮
	for ( var i = 1; i < 11; i = i + 1) {
		//document.getElementById("pagebtn" + i).innerHTML = pagebase + i;
		document.getElementById("pagebtn" + i).disabled = false;
	}
	// 3.判断特殊:第一页，最前10页，最后10页，最后一页
	if (nowpage == 1) {// 已经第一页
		document.getElementById("firstpagebtn").disabled = true;
		document.getElementById("prevpagebtn").disabled = true;
		document.getElementById("prevTENpagebtn").disabled = true;
	}
	if (pagebase == 0) {// 最前10页
		document.getElementById("prevTENpagebtn").disabled = true;

	}
	if (nowpage == tpagerinfo.pageCount) {// 最后一页
		document.getElementById("lastpagebtn").disabled = true;
		document.getElementById("nextpagebtn").disabled = true;
		document.getElementById("nextTENpagebtn").disabled = true;
	}
	if (pagebase == lastbase) {// 最后10页
		document.getElementById("nextTENpagebtn").disabled = true;
	}
	// 4.快速按钮
	document.getElementById("pagebtn" + nowpagetail).disabled = true;
	// 5.快速跳转栏
	document.getElementById("gotopagenum").value = tpagerinfo.nowPage;
	if (tpagerinfo.pageCount < pagebase + 11) {
		i = 1;
		while (i < tpagerinfo.pageCount - pagebase + 1) {
			document.getElementById("pagebtn" + i).innerHTML = (pagebase+i)+"&nbsp;";
			i = i + 1;
		}
		while(i<11){
			document.getElementById("pagebtn" + i).innerHTML = "";
			i = i + 1;
		}
		document.getElementById("nextTENpagebtn").disabled = true;
	} else {
		i = 1;
		while (i < 11) {
			document.getElementById("pagebtn" + i).innerHTML = (pagebase+i)+"&nbsp;";
			i = i + 1;
		}
	}
}

// 生成列表
function generatetable(pagerRes) {
	// 设置分页信息
	pagerinfo = pagerRes[0];
	var tempPagerinfo = pagerRes[0];
	// 获得列表
	var infoList = pagerRes[1];
	// 输出信息
	document.getElementById("pagermsg").innerHTML = "<font size=\"2\">页次："
			+ pagerinfo.nowPage + "/" + pagerinfo.pageCount + "页   每页"
			+ pagerinfo.pageSize + "条  共" + pagerinfo.infoCount + "条</font>";
	// 获取列表table
	var table = document.getElementById("infotable");
	// 清空数据
	while (table.rows.length > 0) {
		table.deleteRow(0);
	}
	// 画表头
	var tablehead = table.insertRow();
	tablehead.style.backgroundColor = "#dfbfff";
	tablehead.align = "center";
	// 表头内容
	var hth1 = tablehead.insertCell();
	var hth2 = tablehead.insertCell();
	var hth3 = tablehead.insertCell();
	var hth4 = tablehead.insertCell();
	var hth5 = tablehead.insertCell();
	var hth6 = tablehead.insertCell();
	var hth7 = tablehead.insertCell();
	
	hth1.style.width="6%";
	hth2.style.width="50%";
	hth3.style.width="14%";
	hth4.style.width="14%";
	hth5.style.width="8%";
	hth6.style.width="4%";
	hth7.style.width="4%";

	hth1.innerHTML = "<div align=\"center\">NoticeId</div>";
	hth2.innerHTML = "<div align=\"center\">标题</div>";
	hth3.innerHTML = "<div align=\"center\">发布时间</div>";
	hth4.innerHTML = "<div align=\"center\">有效时间</div>";
	hth5.innerHTML = "<div align=\"center\">发表者</div>";
	hth6.innerHTML = "<div align=\"center\">删除</div>";
	hth7.innerHTML = "<div align=\"center\">编辑</div>";

	// 加入当前页的列表
	for ( var i = 0; i < infoList.length; i++) {
		// 获取数据
		var id = infoList[i].noticeId;
		var title = infoList[i].title;
		var editor = infoList[i].editor;
		var time = infoList[i].time;
		var validTime = infoList[i].validTime;
		// 添加行
		var newTr = table.insertRow();
		newTr.style.backgroundColor = "#f0fff8";
		newTr.align = "center";
		
		var th1 = newTr.insertCell();
		var th2 = newTr.insertCell();
		var th3 = newTr.insertCell();
		var th4 = newTr.insertCell();
		var th5 = newTr.insertCell();
		var th6 = newTr.insertCell();
		var th7 = newTr.insertCell();
		
		th1.innerHTML = id;
		th2.innerHTML = title;
		th3.innerHTML = time;
		th4.innerHTML = validTime;
		th5.innerHTML = editor;
		th6.innerHTML = "<div align=\"center\"><img style=\"cursor:hand;\" onclick=\"delNoticeAdmin("+id+")\" src=\"/examOnline/pager/pagerskin/del.gif\" width=\"16\" height=\"16\" alt=\"删除\"></div>";
		th7.innerHTML = "<div align=\"center\"><img style=\"cursor:hand;\" onclick=\"editNoticeAdmin("+id+")\" src=\"/examOnline/pager/pagerskin/edit.gif\" width=\"16\" height=\"16\" alt=\"编辑\"></div>"
		}
	refreshpagerctr(tempPagerinfo);
}

function CheckInputInt(oInput) {
	if ('' != oInput.value.replace(/\d{1,5}/, '')) {
		oInput.value = oInput.value.match(/\d{1,5}/) == null ? ''
				: oInput.value.match(/\d{1,5}/, '');
	}	
}

function viewNoticeAdmin(Did){

}

function delNoticeAdmin(Did){
	
}

function editNoticeAdmin(Did){
	var url = "/examOnline/adminHome/noticeAdminEdit.jsp?noticeId=" + Did;
	window.location.href(url);
}
