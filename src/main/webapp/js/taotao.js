var YT = YOUTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("YT_TOKEN");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://sso.youtao.com/user/query/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
					var html =data.username+"，欢迎来到优淘！<a href=\"http://sso.youtao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	YT.checkLogin();
});