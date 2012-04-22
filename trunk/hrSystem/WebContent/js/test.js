$(document).ready(function(){
	$.ajax({
		cache : false,
		type : "GET",
		dataType : "json",
		url : "action/login.json",
		data : {
			"id" : id,
			"psw" : psw
		},
		error : function (){
			printError("服务器错误，无法登陆");
		},
		success : function(data) {
			if (data.type == "ok") {
				window.location.href = "authz";
			} else if (data.type == "eu") {
				printError("用户名或密码错误");
			} else {
				printError("你没有SVN相关权限");
			}
		}
	});
});

