/**
 * Author: Frogcherry@ooobgy
 * Created: 20110907
 * Email: frogcherry@gmail.com
 */
var nameList = new Array();
var chairList = new Array();

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
 }

 String.prototype.ltrim=function(){
    return this.replace(/(^\s*)/g,"");
 }
 
 String.prototype.rtrim=function(){
    return this.replace(/(\s*$)/g,"");
 }

function loadDataFilePath(filePath){
	try {
		var fso = new ActiveXObject("Scripting.FileSystemObject");   
        var reading = 1;   
        var f = fso.OpenTextFile(filePath,reading);     
        var r = f.ReadAll(); 
 	   	f.close();
 	   	return r;
	} catch (e) {
		alert("文件错误！导入失败");
	}
}

function loadDataFileURL(fileURL){
	try {
		//获取绝对路径
		var appPath = window.location.href.substr(8).replace(/\//g, "\\");		
		appPath = appPath.substr(0,appPath.lastIndexOf( "\\"));		
		//制作windows用的path字符
		var filename = fileURL.replace(/\//g,"\\");
		var filePath = appPath + "\\" + filename;
		filePath = filePath.replace("\\","\\\\");
		return loadDataFilePath(filePath);
	} catch (e) {
		alert("文件错误！导入失败");
	}
}

/**
 * 返回读取到的原始教室布局数据
 * @param filePath: 数据在文件系统中的位置
 */
function loadClassRoomPath(filePath){
	return loadDataFilePath(filePath);
}

/**
 * 返回读取到的原始教室布局数据
 * @param fileURL: 数据在URL中的相对位置
 */
function loadClassRoomURL(fileURL){
	return loadDataFileURL(fileURL);
}

function initClassRoom(){
	var classRoomData = loadClassRoomURL("../data/classroom.data");
	document.getElementById("class_room").innerHTML = classRoomData;
	document.getElementById("chairCntDisp").innerHTML = document.getElementById("chairCnt").value;
	loadNameListURL("../data/nameList.txt");
	loadChairList(classRoomData);
	randomChair();
}

function inRuler(row, col) {
	document.getElementById('rowr_' + row).className = 'rowRulerLight';
	document.getElementById('colr_' + col).className = 'colRulerLight';
}

function outRuler(row, col) {
	document.getElementById('rowr_' + row).className = 'rowRuler';
	document.getElementById('colr_' + col).className = 'colRuler';
}

function prepareNameList(nameListData){
	var names = nameListData.split('\n');
	var name;
	var cnt = 0;
	for ( var i in names) {
		name = names[i].trim();
		if (name != '') {
			cnt ++;
			nameList.push(name);
		}
	}
	
	document.getElementById("studentCntDisp").innerHTML = cnt;
}

function loadNameListPath(filePath){
	var nameListData = loadDataFilePath(filePath);
	prepareNameList(nameListData);
}

function loadNameListURL(fileURL){
	var nameListData = loadDataFileURL(fileURL);
	prepareNameList(nameListData);
}

/**
 * 读取座位列表
 * @param data
 * @return
 */
function loadChairList(data){
	var colCnt = document.getElementById("colCnt").value;
	colCnt = parseInt(colCnt);
	var rowCnt = document.getElementById("rowCnt").value;
	rowCnt = parseInt(rowCnt);
	var sid;
	var sStyle;
	for ( var i = 1; i <= rowCnt; i++) {
		for ( var j = 1; j <= rowCnt; j++) {
			sid = "st_" + i + "_" + j;
			sStyle = document.getElementById(sid + "d").className;
			if (sStyle == "chair") {
				chairList.push(sid);
			}
		}
	}
}

/**
 * 返回大于等于floor，小于upper的随机整数
 * @param floor
 * @param upper
 * @return
 */
function randomInt(floor, upper){
	return parseInt(Math.random() * (upper - floor) + floor);
}

/**
 * 返回floor到upper之间的随机数组，每个数字不同
 * size <= upper - floor
 * @param floor
 * @param upper
 * @param size
 * @return
 */
function randomArray(floor, upper, size){
	var randArray = new Array();
	var tpl = new Array();
	var range = upper - floor;
	for ( var i = 0; i < range; i++) {
		tpl.push(floor + i);
	}
	
	var index;
	for ( var i = 0; i < size; i++) {
		index = randomInt(0, range - i);
		randArray.push(tpl[index]);
		tpl[index] = tpl[range - i - 1];
	}
	
	return randArray;
}

function randomChair(){
	var chairCnt = chairList.length;
	var studentCnt = nameList.length;
	var cnt = chairCnt < studentCnt ? chairCnt : studentCnt;
	var randIndexes = randomArray(0, studentCnt, cnt);
	var name;
	for ( var i = 0; i < cnt; i++) {
		name = nameList[randIndexes[i]];
		document.getElementById(chairList[i]).innerHTML = name;
	}
	if (chairCnt < studentCnt) {//座位不够
		chairWarning(randIndexes);
	}
}

function chairWarning(randIndexes){
	var ROW_MAX = 5;
	
	var unames = nameList.toString().split(',');
	for ( var i in randIndexes) {
		delete unames[randIndexes[i]];
	}
	//alert(nameList.length);
	var unChairedSts = new Array();
	for ( var i in unames) {
		if (unames[i] != null) {
			unChairedSts.push(unames[i]);
		}
	}
	var untable = "<table align='center' border='1'><tr>";
	var j = -1;
	for ( var i in unChairedSts) {
		j++;
		if (j == ROW_MAX) {
			j = 0;
			untable += "</tr><tr>"
		}
		untable += "<td align='center' width='75'>" + unChairedSts[i] + "</td>";
	}
	while(j<ROW_MAX-1){
		untable += "<td>&nbsp;</td>";
		j ++;
	}
	untable += "</tr></table>";
	document.getElementById('noChairWarning').innerHTML = "警告！由于教室容量小于学生人数，以下学生无法安排座位：";
	document.getElementById('unChairSts').innerHTML = untable;
}

/**
 * 响应位置的点击事件，注意判定格子性质
 * @param chairId
 * @return
 */
function modifyChair(chairId){
	var st1 = document.getElementById("st1").value;
	var st2 = document.getElementById("st2").value;
	var stType = document.getElementById(chairId + "d").className;
	if (stType == 'stchosed') {
		if (st1 == chairId) {
			document.getElementById("st1").value = "";
		} else if (st2 == chairId){
			document.getElementById("st2").value = "";
		}
		document.getElementById(chairId + "d").className = "chair";
		document.getElementById('exchangeBtn').innerHTML = "<img src=\"../images/exchange_dis.png\" width=\"148\" height=\"48\">";
	} else if (stType == 'chair') {
		if (st1 == "") {
			document.getElementById("st1").value = chairId;
			document.getElementById(chairId + "d").className = "stchosed";
		} else if (st2 == "") {
			document.getElementById("st2").value = chairId;
			document.getElementById(chairId + "d").className = "stchosed";
		}
	}
	
	st1 = document.getElementById("st1").value;
	st2 = document.getElementById("st2").value;
	if ( (st1 != "") && (st2 != "") ) {
		document.getElementById('exchangeBtn').innerHTML = "<img src=\"../images/exchange.png\" width=\"148\" height=\"48\" onClick=\"exchangeChair();\" style=\"cursor:hand;\">";
	}
}

/**
 * 交换座位事件
 * @return
 */
function exchangeChair() {
	var st1 = document.getElementById("st1").value;
	var st2 = document.getElementById("st2").value;
	if ( (st1 != "") && (st2 != "") ) {
		var tmp = document.getElementById(st1).innerHTML;
		document.getElementById(st1).innerHTML = document.getElementById(st2).innerHTML;
		document.getElementById(st2).innerHTML = tmp;
		document.getElementById('exchangeBtn').innerHTML = "<img src=\"../images/exchange_dis.png\" width=\"148\" height=\"48\">";
		document.getElementById("st1").value = "";
		document.getElementById("st2").value = "";
		document.getElementById(st1 + "d").className = "chair";
		document.getElementById(st2 + "d").className = "chair";
	}
}

function step2(){
	var cnt = document.getElementById("chairCnt").value;
	window.location.href = "checkNameList.html?chairCnt=" + cnt;
}

function saveAsExcel(){
    var curTbl = document.getElementById("classroom");
//    alert(curTbl);
//    return;
    var oXL = new ActiveXObject("Excel.Application");
    //创建AX对象excel
    var oWB = oXL.Workbooks.Add();
    //获取workbook对象
    var oSheet = oWB.ActiveSheet;
    //激活当前sheet
    var sel = document.body.createTextRange();
    sel.moveToElementText(curTbl);
    //把表格中的内容移到TextRange中
    sel.select();
    //全选TextRange中内容
    sel.execCommand("Copy");
    //复制TextRange中内容 
    oSheet.Paste();
    //粘贴到活动的EXCEL中  
    //设置excel可见属性
//    oXL.Visible = true;
    
    try{ 
    	var fname = oXL.Application.GetSaveAsFilename("classroom.xlsx", "Excel Spreadsheets (*.xlsx), *.xlsx"); 
    	if(fname){ 
    		oWB.SaveAs(fname); 
    	} 
    }catch(e){
    	alert("导出失败！请检查你是否按照microsoft office.我们建议的版本是2007以上，低版本也可能出现该错误！" +
    			"\n下一条是错误日志，期望您的反馈:frogcherry@gmail.com");
    	alert("Nested catch caught " + e); 
    }finally{ 
    	oWB.Close(savechanges=false); 
    	oXL.Quit(); 
    	oXL=null; 
    //结束excel进程，退出完成 
    	idTmr = window.setInterval("Cleanup();",1); 
    }
}







