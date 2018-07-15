var $$ = Dom7;
var app = new Framework7({
	name: '正欣会计',
	language: 'zh-CN',
	theme: 'ios',
	id: 'com.zhengxinacc.app.login',
	toast: {
		position: 'top',
		closeTimeout: 2000
	}
});
app.loginScreen.open('.login-screen');
$$('input[name="username"]').focus();
$$('form').find('input[type="text"], input[type="password"]').on('keyup', function(e){
	if (e.keyCode==13){
		$$('#btnSubmit').click();
	}
});
$$('#btnSubmit').click(function(){
	if ($$('input[name="username"]').val().trim()==''){
		$$('input[name="username"]').focus();
		return false;
	}
	if ($$('input[name="password"]').val().trim()==''){
		$$('input[name="password"]').focus();
		return false;
	}
//	$$('form').submit();
	return true;
});
var toast = app.toast.create({
	text: '用户名或密码错误'
})
if (location.href.indexOf('?error')>-1){
	toast.open();
}