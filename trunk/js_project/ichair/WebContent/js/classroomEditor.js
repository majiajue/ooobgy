/**
 * Author: Frogcherry@ooobgy
 * Created: 20110904
 * Email: frogcherry@gmail.com
 */
function loadClassroom(filename,classroom){
	//获取绝对路径
	var appPath = window.location.href.substr(8).replace(/\//g, "\\");
	appPath = appPath.substr(0,appPath.lastIndexOf( "\\"));
	//制作windows用的path字符
	filename = filename.replace(/\//g,"\\");
	var filePath = appPath + "\\" + filename;
	filePath = filePath.replace("\\","\\\\");
	//alert(filePath);
	try{
	   var fso = new ActiveXObject("Scripting.FileSystemObject");   
       var reading = 1;   
       var f = fso.OpenTextFile(filePath,reading);     
       var r = f.ReadAll(); 
	   document.getElementById(classroom).innerHTML = r;
	   f.close();
	   document.getElementById("cnt").innerHTML = document.getElementById("chairCnt").value;
	}catch(e){
		alert("文件错误！导入失败");
	}
}

function loadData(){
	fileDialog.CancelError=true;
	try{   
        fileDialog.Filter="Data   Files   (*.data)|*.data";   
        fileDialog.ShowOpen();
        var filePath = fileDialog.filename;
        var fso = new ActiveXObject("Scripting.FileSystemObject");   
        var reading = 1;   
        var f = fso.OpenTextFile(filePath,reading);     
        var r = f.ReadAll(); 
 	   	document.getElementById("class_room").innerHTML = r;
 	   	f.close();
 	   	document.getElementById("cnt").innerHTML = document.getElementById("chairCnt").value;
	}catch(e){
		alert("文件错误！导入失败");
	}
}

function saveClassRoom(filename) {  
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
		var classRoom = document.getElementById("class_room").innerHTML;
		
	    f.write(classRoom);   
	    f.Close();   
    }   
    catch(e){
    }
}

function step2(filename){
	saveClassRoom(filename);
	var cnt = document.getElementById("cnt").innerHTML;
	window.location.href = "pages/checkNameList.html?chairCnt=" + cnt;
}

function inRuler(row, col) {
	document.getElementById('rowr_' + row).className = 'rowRulerLight';
	document.getElementById('colr_' + col).className = 'colRulerLight';
}

function outRuler(row, col) {
	document.getElementById('rowr_' + row).className = 'rowRuler';
	document.getElementById('colr_' + col).className = 'colRuler';
}

function changeClassRoom(room){
	var preColCnt = document.getElementById("colCnt").value;
	preColCnt = parseInt(preColCnt);
	var width = 75 * preColCnt + 81;
	//alert(width);
	var classRoom = "<table border=\"1\" width='" + width + "' bordercolor=\"#723DFE\" cellspacing=\"0\"><tbody id=\"room\">" + room + "</tbody></table>";
	document.getElementById("class_show").innerHTML = classRoom;
}

function addRow(){
	var preRowCnt = document.getElementById("rowCnt").value;
	var preRowTag = "str_" + preRowCnt;
	var preStTag = "st_" + preRowCnt;
	var nowRowCnt = preRowCnt;
	nowRowCnt ++;
	var nowStTag = "st_" + nowRowCnt;
	var nowRowTag = "str_" + nowRowCnt;
	var preRow = document.getElementById(preRowTag).innerHTML;
	
	//必须分割才能替换
	var nowRow = "";
	var lines = preRow.split('\n');
	var addChair = 0;
	var preStTagRe = new RegExp(preStTag,"g");
	var preMouseEventRe = new RegExp("Ruler\\(" + preRowCnt,"g");
	var mouseEvent = "Ruler(" + nowRowCnt;
	
    for (i in lines){
		var line = lines[i].replace(preStTagRe, nowStTag);

		//if(addChair==0) alert(line);
		if(line.match('chair') != null){
			addChair ++;
			}
		nowRow = nowRow + line;
		}
	nowRow = "<tr id='" + nowRowTag + "'>\n" + nowRow + "\n</tr>";
	
	nowRow = nowRow.replace(preMouseEventRe,mouseEvent);
	nowRow = nowRow.replace("rowr_" + preRowCnt,"rowr_" + nowRowCnt);
	nowRow = nowRow.replace("rowrn_" + preRowCnt,"rowrn_" + nowRowCnt);
	
	var room = document.getElementById("room").innerHTML + nowRow;
	
	document.getElementById("rowCnt").value = nowRowCnt;
	
	var cnt = document.getElementById("cnt").innerHTML;
	cnt = parseInt(cnt) + addChair;
	document.getElementById("cnt").innerHTML = cnt;
	document.getElementById("chairCnt").value = cnt;
	changeClassRoom(room);
	document.getElementById("rowrn_" + nowRowCnt).innerHTML = nowRowCnt;
}
 
function delRow(){
	var preRowCnt = document.getElementById("rowCnt").value;
	if(preRowCnt < 2){
		return;
		}
	
	var nowRowCnt = parseInt(preRowCnt) - 1;
	var colRuler = document.getElementById("col_ruler").innerHTML;
	colRuler = "<TR id='col_ruler'>" + colRuler + "</TR>";
	var room = colRuler;
	for(i = 1; i < preRowCnt; i++){
		room += "<tr id='str_" + i + "'>";
		room += document.getElementById("str_" + i).innerHTML;
		room += "</tr>";
		}
		
	var delChair = 0;
	var delRow = document.getElementById("str_" + i).innerHTML;
	var cols = delRow.split('\n');
	for(i in cols){
			if(cols[i].match('chair') != null){
			delChair ++;
			}
		}
	
	document.getElementById("rowCnt").value = nowRowCnt;
	var cnt = document.getElementById("cnt").innerHTML;
	cnt = parseInt(cnt) - delChair;
	document.getElementById("cnt").innerHTML = cnt;
	document.getElementById("chairCnt").value = cnt;
	changeClassRoom(room);
	}

function addCol(){
	var preColCnt = document.getElementById("colCnt").value;
	preColCnt = parseInt(preColCnt);
	var rowCnt = document.getElementById("rowCnt").value;
	rowCnt = parseInt(rowCnt);
	var nowColCnt = preColCnt + 1;
	
	var colRuler = document.getElementById("col_ruler").innerHTML;
	colRuler += "<TD id=colr_" + nowColCnt + " class=colRuler><span id='colrn_" + nowColCnt + "'>" + nowColCnt + "</span></TD>";
	colRuler = "<TR id='col_ruler'>" + colRuler + "</TR>";
	var room = colRuler;
	for(i = 1; i <= rowCnt; i++){
		var row = document.getElementById("str_"+i).innerHTML;
		row += "<td class=\"sfloor\" id=\"st_" + i + "_" + nowColCnt + "d\" onmouseover=\"inRuler(" + i + ", " + nowColCnt + ")\" onmouseout=\"outRuler(" + i + ", " + nowColCnt + ")\" onClick=\"modifyChair('st_" + i + "_" + nowColCnt + "');\"><span id=\"st_" + i + "_" + nowColCnt + "\">&nbsp;</span></td>";
		row = "<tr id='str_" + i + "'>" + row + "</tr>";
		room += row;
	}
	document.getElementById("colCnt").value = nowColCnt;
	changeClassRoom(room);
}

function delCol(){
	var cnt = document.getElementById("cnt").innerHTML;
	cnt = parseInt(cnt);
	var preColCnt = document.getElementById("colCnt").value;
	preColCnt = parseInt(preColCnt);
	if (preColCnt < 2) {
		return;
	}
	var rowCnt = document.getElementById("rowCnt").value;
	rowCnt = parseInt(rowCnt);
	var nowColCnt = preColCnt - 1;
	
	var colRuler = "<TR id='col_ruler'><TD>&nbsp; </TD>";
	for ( var i = 1; i < preColCnt; i++) {
		var colr = "<TD id=colr_" + i + " class=colRuler><span id='colrn_" + i + "'>" + i + "</span></TD>";
		colRuler += colr;
	}
	colRuler = colRuler + "</TR>";
	var room = colRuler;
	var delCnt = 0;
	for(i = 1; i <= rowCnt; i++){
		var rowRuler = "<TD id=rowr_" + i + " class=rowRuler><span id='rowrn_" + i + "'>" + i + "</span></TD>";
		var row = rowRuler;
		for ( var j = 1; j < preColCnt; j++) {
			
			var style = document.getElementById("st_" + i + "_" + j + "d").className;
			var col = "<td class=\"" + style + "\" id=\"st_" + i + "_" + j + "d\" onmouseover=\"inRuler(" + i + ", " + j + ")\" onmouseout=\"outRuler(" + i + ", " + j + ")\" onClick=\"modifyChair('st_" + i + "_" + j + "');\"><span id=\"st_" + i + "_" + j + "\">&nbsp;</span></td>";
			row += col;
		}
		row = "<tr id='str_" + i + "'>" + row + "</tr>";
		room += row;
		
		var style = document.getElementById("st_" + i + "_" + preColCnt + "d").className;
		if (style == "chair") {
			delCnt ++;
		}
	}
	document.getElementById("colCnt").value = nowColCnt;
	cnt -= delCnt;
	document.getElementById("cnt").innerHTML = cnt;
	document.getElementById("chairCnt").value = cnt;
	changeClassRoom(room);
}

//tmp test
function modifyChair1(stid){
		var tt = "st1st2st3st4";
		oldstr = "st";
		re=new RegExp(oldstr,"g"); 
		tt = tt.replace(re,"sf");
		alert(tt);
	}

function modifyChair(stid){
	var cnt = document.getElementById("cnt").innerHTML;
	cnt = parseInt(cnt);
	var style = document.getElementById(stid + 'd').className;
	if(style == "chair"){
		document.getElementById(stid + 'd').className = "sfloor";
		cnt --;
	} else if(style == "sfloor"){
		document.getElementById(stid + 'd').className = "chair";
		cnt ++;
	}
	document.getElementById("cnt").innerHTML = cnt;
	document.getElementById("chairCnt").value = cnt;
}
