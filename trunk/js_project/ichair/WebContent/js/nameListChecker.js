/**
 * Author: Frogcherry@ooobgy Created: 20110905 Email: frogcherry@gmail.com
 */
var PARAMTER_VALUE = null;    
function getParamter(paramName) {    
    if(!PARAMTER_VALUE) {   //第一次初始化    
        PARAMTER_VALUE = new Array();    
        var paramStr = location.search.substring(1);    
        var paramArr = paramStr.split("&");    
        var len = paramArr.length;    
        var tempArr;    
        for(var i = 0; i < len; i++) {    
            tempArr = paramArr[i].split("=");    
            PARAMTER_VALUE[tempArr[0]] = tempArr[1];    
        }    
    }    
    var paramValue = PARAMTER_VALUE[paramName];    
    if(paramValue) {    
        return paramValue;    
    }    
}

function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
 }

 String.prototype.ltrim=function(){
    return this.replace(/(^\s*)/g,"");
 }
 
 String.prototype.rtrim=function(){
    return this.replace(/(\s*$)/g,"");
 }

function setChairCnt(){
	var cnt = getParamter("chairCnt");
	if (cnt == '') {
		cnt = 0;
	}
	document.getElementById("chiarCnt").innerHTML = cnt;
}

function loadNameList(filePath){
	try {
		var fso = new ActiveXObject("Scripting.FileSystemObject");   
        var reading = 1;   
        var f = fso.OpenTextFile(filePath,reading);     
        var r = f.ReadAll(); 
 	   	document.getElementById("nameList").value = r;
 	   	f.close();
	} catch (e) {
		alert("文件错误！导入失败");
	}
}

function loadNameListURL(fileURL){
	var appPath = window.location.href.substr(8).replace(/\//g, "\\");
	appPath = appPath.substr(0,appPath.lastIndexOf( "\\"));
	//制作windows用的path字符
	filename = fileURL.replace(/\//g,"\\");
	var filePath = appPath + "\\" + filename;
	filePath = filePath.replace("\\","\\\\");
	loadNameList(filePath);
}

function countStudents(){
	var names = document.getElementById("nameList").value;
	var nameList = names.split('\n');
	var cnt = 0;
//	var beautyList = "";
	var lineNumReg = new RegExp("^[0-9]+\\.","g");
	for (var i in nameList) {
//		if (beautyList != "") {
//			beautyList = beautyList + "\n";
//		}
		var name = nameList[i].trim();
		if (name != '') {
			cnt ++;
//			if (name.match(lineNumReg) == null) {
//				name = cnt + ". " + name;
//			} else {
//				name = name.replace(lineNumReg, cnt + ".");
//			}
			//beautyList = beautyList + name;
		} 
//		else {
//			beautyList = beautyList + "\n";
//		}
	}
	document.getElementById("studentCnt").innerHTML = cnt;
	//document.getElementById("nameList").value = beautyList;
}

function checkStudentsCnt(){
	var chairCnt = document.getElementById("chiarCnt").innerHTML;
	chairCnt = parseInt(chairCnt);
	var studentCnt = document.getElementById("studentCnt").innerHTML;
	studentCnt = parseInt(studentCnt);
	if (studentCnt > chairCnt) {
		var warning = "警告！学生人数超出教室容量";
		document.getElementById("warning").innerHTML = warning;
	} else {
		document.getElementById("warning").innerHTML = " ";
	}
}

function pageLoad(){
	setChairCnt();
	loadNameListURL("../data/nameList.txt");
	countStudents();
	checkStudentsCnt();
}

function saveNameListURL(filename) {  
	//获取绝对路径
	var appPath = window.location.href.substr(8).replace(/\//g, "\\");
	appPath = appPath.substr(0,appPath.lastIndexOf( "\\"));
	//制作windows用的path字符
	filename = filename.replace(/\//g,"\\");
	var filePath = appPath + "\\" + filename;
	filePath = filePath.replace("\\","\\\\");
    try{     
	    var fso = new ActiveXObject("Scripting.FileSystemObject");   
	    var f = fso.CreateTextFile(filePath,true); 
		var nameList = document.getElementById("nameList").value;
		
	    f.write(nameList);   
	    f.Close();   
    }   
    catch(e){
    }
}

function loadNameListFile(){
	fileDialog.CancelError=true;
	try{   
        fileDialog.Filter="NameList   Files   (*.txt)|*.txt";   
        fileDialog.ShowOpen();
        var filePath = fileDialog.filename;
        loadNameList(filePath);
        countStudents();
    	checkStudentsCnt();
	}catch(e){
		alert("文件错误！导入失败");
	}
}

function step1(){
	window.location.href = "../ichair.html";
}

function step3(){
	saveNameListURL("../data/nameList.txt");
	window.location.href("arrangeChair.html");
}

function nameListChange(){
	//alert("change");
	countStudents();
	checkStudentsCnt();
}
