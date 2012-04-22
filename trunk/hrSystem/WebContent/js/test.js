$(document).ready(function(){
	$.ajax({
		cache : false,
		type : "GET",
		dataType : "json",
		url : "action/hello",
		data : {
			"id" : "12",
			"psw" : {key1: "v1"}
		},
		error : function (){
			printError("服务器错误，无法登陆");
		},
		success : function(data) {
			$("body").html(data);
		}
	});
});

