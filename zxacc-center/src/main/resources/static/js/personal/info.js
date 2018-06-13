layui.use(['layer', 'form', 'laydate'], function(){
	window.layer = layui.layer;
	var form = layui.form;
	var laydate = layui.laydate;
	
	$.post($.kbase.ctx + '/user/loadData', function(user){
		$('#id').val(user.id);
		$('input[name="username"]').val(user.username);
		$('input[name="usernameCN"]').val(user.userInfo.username);
		$('input[name="sex"][value="' + user.userInfo.sex + '"]').attr('checked', 'checked');
		if (user.userInfo.birthday){
			var birthday = new Date();
			birthday.setTime(user.userInfo.birthday);
			$('input[name="birthday"]').val(dateFns.format(birthday, 'YYYY-MM-DD'));
		}
		$('input[name="phone"]').val(user.userInfo.phone);
		$('input[name="email"]').val(user.userInfo.email);

		form.render();
		laydate.render({
			elem: $('input[name="birthday"]')[0]
		});
	}, 'json');
	
});
$('#btnSave').click(function(){
	if ($('input[name="username"]').val().trim()==''){
		$('input[name="username"]').focus();
		return false;
	}
	if ($('input[name="usernameCN"]').val().trim()==''){
		$('input[name="usernameCN"]').focus();
		return false;
	}
	if ($('input[name="phone"]').val().trim()==''){
		$('input[name="phone"]').focus();
		return false;
	}
	
	var param = {
		id: $('#id').val(),
		username: $('input[name="username"]').val(),
		usernameCN: $('input[name="usernameCN"]').val(),
		sex: $('input[name="sex"]:checked').val(),
		birthday: $('input[name="birthday"]').val(),
		phone: $('input[name="phone"]').val(),
		email: $('input[name="email"]').val()
	}
	
	$.post($.kbase.ctx + '/user/save', param, function(data){
		if (data.success){
			layer.alert('保存成功');
		}else{
			layer.alert(data.response.message);
		}
	});
	
	return false;
});