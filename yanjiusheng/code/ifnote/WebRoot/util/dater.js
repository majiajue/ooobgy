// ˵��������ֵΪ һ���ַ���
// ��ʽ���£�
// ʹ�÷����� 
// var dataString = showModalDialog("calendar.htm", "dd��mm��yyyy��", "dialogWidth:286px;dialogHeight:221px;status:no;help:no;");
var userFormatString;
if(window.dialogArguments ==null)
{
 userFormatString = "yyyy-mm-dd";
}
else
{
 userFormatString = window.dialogArguments;
}
with(new Date()){
 var Nyear = getYear();
 var Nmonth =  getMonth() +1;
 var Ndate =  getDate();
}
window.returnValue = new dataObj(Nyear,Nmonth,Ndate ).getDateString(userFormatString);
window.document.onclick = function(){
 var obj = window.event.srcElement;
 if(obj.tagName.toLowerCase() == "span"   &&   obj.parentNode.className.replace(/Ctable/ig,"star") == "star" )
 {
  try{
   window.currentActiveItem.runtimeStyle.cssText = "";
  }
  catch(e){ }
  Nyear = obj.id.split("-")[0];
  Nmonth = obj.id.split("-")[1];
  Ndate = obj.id.split("-")[2];
  window.currentActiveItem = obj;
  window.currentSelectDate = window.currentActiveItem.id;
  window.currentActiveItem.runtimeStyle.cssText = "background:url(/examOnline/util/daterskin/dateselect.gif) no-repeat 12px 6px;color:#000;padding-top:1px;font-weight:bold";
 }
}
 function dataObj(year,month,date)  
{
 this.year  = year
 this.month = month
 this.date  =  date
 this.getDateString =
  function(formatString)
  {
   return formatString.replace(/yyyy/ig , this.year).replace(/mm/ig , this.month).replace(/dd/ig , this.date)
  }
}
window.onload = function(){
 window.document.attachEvent("onclick" , doCmd);
 window.document.attachEvent("onmouseover" , buttonOver);
 window.document.attachEvent("onmouseout" , buttonOut);
 window.document.attachEvent("onmousedown" , buttonDown);
 window.document.attachEvent("onmouseup" , buttonUp);
 window.document.attachEvent("ondblclick" ,
   function()
   {
     var obj = window.event.srcElement;
     if(obj.tagName.toLowerCase() == "span"   &&   obj.parentNode.className.replace(/Ctable/ig,"star") == "star" )
     {
      var mydate = new dataObj(obj.id.split("-")[0] ,  obj.id.split("-")[1] ,  obj.id.split("-")[2] );
      window.returnValue = mydate.getDateString(userFormatString)
      window.close();
     }
   }
 );
 document.all.titleYear.innerHTML=TranYearMonthTitle(Nyear,Nmonth);
 document.all.weekNameBox.insertAdjacentHTML("afterBegin",makeWeekNameHtmlStr());
 document.all.calendarBox.innerHTML=makeCalendarHtmlStr(Nyear,Nmonth);
 window.currentSelectDate = starCaTran(Nyear,Nmonth,Ndate);
 window.document.all.calendarBox.show = show;
 window.currentActiveItem = window.document.getElementById(currentSelectDate);
 if( window.currentActiveItem )
  window.currentActiveItem.click();
 window.document.all.calendarBox.show();
}
function starCalendar(year,month){
 this.year = year;
 this.month = month;
 this.monthTable = function(){
    var aMonth=new Array();
    for(i=1;i<7;i++)aMonth[i]=new Array(i);
    var dCalDate=new Date(this.year, this.month-1, 1);
    var iDayOfFirst=dCalDate.getDay();
    var iDaysInMonth=new Date(this.year, this.month, 0).getDate();
    var iOffsetLast=new Date(this.year, this.month-1, 0).getDate()-iDayOfFirst+1;
    var iDate = 1;
    var iNext = 1;
    for (d = 0; d < 7; d++)
    aMonth[1][d] = (d<iDayOfFirst)?(-iDayOfFirst+d+1):iDate++;
    for (w = 2; w < 7; w++)
   for (d = 0; d < 7; d++)
    aMonth[w][d] = iDate++;
    return aMonth;
 }
}
function makeWeekNameHtmlStr(){
 var tmpStr="";
 var weekName = ["��","һ","��","��","��","��","��"];
 for(var i=0;i<7;i++)tmpStr+="<span class=weekName>"+weekName[i]+"</span>";
 return tmpStr;
}
function makeCalendarHtmlStr(year,month){
 window.theCalendar = new starCalendar(year,month);
 var theCaArr = theCalendar.monthTable();
 var theDaysInMonth = new Date(year, month, 0).getDate();
 var theCaHtml = "<div class=Ctable>";
 for(var i=1;i<7;i++)
  for(var j=0;j<7;j++)
   theCaHtml = theCaHtml+"<span class="+( (theCaArr[i][j]<1 || theCaArr[i][j]>theDaysInMonth)?"OtherMonthDate":"Cdate")+" id="+starCaTran(year,month,theCaArr[i][j])+">"+starCaTran(year,month,theCaArr[i][j]).split("-")[2]+"</span>";
 return theCaHtml+"</div>";
}
function starCaTran(year,month,date){
 with(new Date(year,month-1,date))
  return getYear() + "-" +(getMonth()+1) + "-" + getDate();
}
function TranYearMonthTitle(year,month){
 with(new Date(year,month-1,1))
  return "<span style='text-decoration:underline;cursor:hand;font-weight:bold;padding:1 2 0 1;width:40px;' onclick=showMore(1940,2050,this.innerHTML) onmouseover=\"this.runtimeStyle.cssText='color:#fff;'\" onmouseout=\"this.runtimeStyle.cssText=''\" onpropertychange=showC()>" + getYear() + "</span>" + "��" + "<span style='text-decoration:underline;cursor:hand;font-weight:bold;padding:1 2 0 1;width:20px;' onclick=showMore(1,12,this.innerHTML) onmouseover=\"this.runtimeStyle.cssText='color:#fff;'\" onmouseout=\"this.runtimeStyle.cssText=''\" onpropertychange=showC()>" + (getMonth()+1) + "</span>" + "��" ;
}
function showC(){
   if(event.propertyName != "innerHTML")return;
   window.theCalendar.year = new Number(document.all.titleYear.getElementsByTagName("span")[0].innerHTML);
   window.theCalendar.month =  new Number(document.all.titleYear.getElementsByTagName("span")[1].innerHTML);
   window.document.all.calendarBox.innerHTML=makeCalendarHtmlStr(window.theCalendar.year,window.theCalendar.month);
   window.document.all.calendarBox.show = show;window.document.all.calendarBox.show();
}
function showMore(starNum,endNum,selectedValue){
 var obj = window.event.srcElement;
 var selectedIndex = selectedValue - starNum;
 if(obj.selectBox){
  obj.selectBox.selectedIndex = selectedIndex;
  return obj.selectBox.show(document.all.calendarBox.offsetHeight + document.all.weekNameBox.offsetHeight );
 }
 var selectBox = window.document.createElement("div");
 selectBox.className = "selectBox";
 selectBox.style.height = 0;
 selectBox.style.top = window.event.clientY - window.event.offsetY + window.event.srcElement.offsetHeight;
 selectBox.style.left = window.event.clientX - window.event.offsetX ;
 selectBox.show  = showBox;
 selectBox.selectedIndex = selectedIndex;
 selectBox.onclick = function(){
  var selectedObj = window.event.srcElement;
  if( "nobr" == selectedObj.tagName.toLowerCase()  &&  selectBox.contains(selectedObj))
  {
   if(obj.innerHTML != selectedObj.innerHTML)obj.innerHTML = selectedObj.innerHTML;
  }
 }
 selectBox.onlosecapture = alert
 var iString = "";
 for(var i=starNum;i<=endNum;i++){
  iString += "<nobr  onmouseover=\"this.parentNode.getElementsByTagName('nobr')[this.parentNode.selectedIndex].style.cssText='';this.style.cssText='background-color:#00006C;color:#fff;'\"  onmouseout=this.style.cssText=''>"+i+"</nobr><br>" 
 }
 selectBox.insertAdjacentHTML ("afterBegin",iString);
 window.document.body.appendChild(selectBox);
 obj.selectBox = selectBox;
 obj.selectBox.show(document.all.calendarBox.offsetHeight + document.all.weekNameBox.offsetHeight );
 
}
function showBox(iHeight)
{
 var box = this;
 box.style.height =1;
 box.style.display = "block";
 window.clearInterval(box.timeHandle);
 box.timeHandle = window.setInterval(interValHandle,1);
 var s = 0,t =1 ;
 function interValHandle()
 {
  box.scrollTop=1000000;
  s = s + t*t;
  t += 0.5;
  box.style.height = parseInt(box.style.height) + Math.floor(s);
  box.style.width = 65 / iHeight * box.offsetHeight;
  if( box.offsetHeight > iHeight )
  {
    window.clearInterval(box.timeHandle);
    box.style.height = iHeight;
    box.scrollTop = box.childNodes[0].offsetHeight*box.selectedIndex;
    box.getElementsByTagName("nobr")[box.selectedIndex].style.cssText='background-color:#00006C;color:#fff;';
    window.document.attachEvent("onclick",
       box.hide=function()
       {
        box.style.display = "none";
        window.document.detachEvent("onclick",box.hide)
       }
    );
  }
 }
}
function buttonOver(){
 var obj = window.event.srcElement;
 if(obj.tagName.toLowerCase() == "span"   &&   obj.className.replace(/controlButton/ig,"star") == "star" )
 {
  obj.runtimeStyle.cssText="border-color:#fff #606060 #808080 #fff;padding:3 0 0 0 ";
 }
 if(obj.tagName.toLowerCase() == "span"   &&   obj.parentNode.className.replace(/Ctable/ig,"star") == "star" )
 {
  obj.style.backgroundColor = "#fff";
 }
}
function buttonOut(){
 var obj = window.event.srcElement;
 if(obj.tagName.toLowerCase() == "span"   &&   obj.className.replace(/controlButton/ig,"star") == "star" )
 {
  obj.runtimeStyle.cssText = "";
 }
 if(obj.tagName.toLowerCase() == "span"   &&   obj.parentNode.className.replace(/Ctable/ig,"star") == "star" )
 {
  window.setTimeout(function(){obj.style.backgroundColor = ""; },300);
 }
}
function buttonDown(){
 var obj = window.event.srcElement;
 if(obj.tagName.toLowerCase() == "span"   &&   obj.className.replace(/controlButton/ig,"star") == "star" )
 {
  obj.setCapture();
  obj.runtimeStyle.borderColor="#808080 #fefefe #fefefe #808080";
 }
}
function buttonUp(){
 var obj = window.event.srcElement;
 if(obj.tagName.toLowerCase() == "span"   &&  obj.className.replace(/controlButton/ig,"star") == "star" )
 {
  obj.releaseCapture();
  obj.runtimeStyle.cssText ="";
 }
}
function doCmd(){
 var obj = window.event.srcElement;
 if(obj.tagName.toLowerCase() == "span"   &&  obj.className.replace(/controlButton/ig,"star") == "star" )
 {
   switch(obj.getAttribute("cmd"))
   {
    case "py":
     window.document.all.titleYear.innerHTML=window.TranYearMonthTitle(window.theCalendar.year-1,window.theCalendar.month);
     window.document.all.calendarBox.innerHTML=makeCalendarHtmlStr(window.theCalendar.year-1,window.theCalendar.month);
     break;
    case "pm":
     window.document.all.titleYear.innerHTML=window.TranYearMonthTitle(window.theCalendar.year,window.theCalendar.month-1);
     window.document.all.calendarBox.innerHTML=makeCalendarHtmlStr(window.theCalendar.year,window.theCalendar.month-1);
     break;
    case "nm":
     window.document.all.titleYear.innerHTML=window.TranYearMonthTitle(window.theCalendar.year,window.theCalendar.month+1);
     window.document.all.calendarBox.innerHTML=makeCalendarHtmlStr(window.theCalendar.year,window.theCalendar.month+1);
     break;
    case "ny":
     window.document.all.titleYear.innerHTML=window.TranYearMonthTitle(window.theCalendar.year+1,window.theCalendar.month);
     window.document.all.calendarBox.innerHTML=makeCalendarHtmlStr(window.theCalendar.year+1,window.theCalendar.month);
     break;
   }
   window.document.all.calendarBox.show();
   window.currentSelectDate = starCaTran(Nyear,Nmonth,Ndate);
   window.currentActiveItem = window.document.getElementById(currentSelectDate);
   if( window.currentActiveItem )window.currentActiveItem.runtimeStyle.cssText = "background:url(choiceit.gif) no-repeat 12px 6px;color:#000;padding-top:1px;font-weight:bold";
 }
}
function show()
{
 var box = this;
 window.clearTimeout(box.timeHandle);
 var CdateBoxs = this.getElementsByTagName("span");
 for(var i=0;i<CdateBoxs.length;i++)
 {
  CdateBoxs[i].defaultValue = new Number( CdateBoxs[i].innerHTML );
  CdateBoxs[i].innerHTML = 0;
 }
 showDate();
 function showDate(){
  for(var i=0;i<CdateBoxs.length;i++){
   if( new Number( CdateBoxs[i].innerHTML ) + 1 <= new Number( CdateBoxs[i].defaultValue ) )
    CdateBoxs[i].innerHTML = new Number( CdateBoxs[i].innerHTML ) + 1 
  }
  box.timeHandle = window.setTimeout(showDate,1);
 }
this.show = show1
}
function show1()
{
 var box = this;
 window.clearTimeout(box.timeHandle);
 var CdateBoxs = this.getElementsByTagName("span");
 for(var i=0;i<CdateBoxs.length;i++)CdateBoxs[i].style.display = "none";
 showDate(CdateBoxs[0]);
 function showDate(obj){
  if( !obj )return;
  obj.style.display = "inline";
  box.timeHandle = window.setTimeout(function(){showDate(obj.nextSibling);},1);
 }
this.show = show
}
/*
function show()
{
}
*/