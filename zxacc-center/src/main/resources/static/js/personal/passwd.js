layui.use(['layer'], function(){
	window.layer = layui.layer;
});
$('#btnSave').click(function(){
	if ($('input[name="oldPwd"]').val()==''){
		$('input[name="oldPwd"]').focus();
		return false;
	}
	if ($('input[name="newPwd"]').val()==''){
		$('input[name="newPwd"]').focus();
		return false;
	}
	if ($('input[name="confirmPwd"]').val()==''){
		$('input[name="confirmPwd"]').focus();
		return false;
	}
	if ($('input[name="newPwd"]').val()!=$('input[name="confirmPwd"]').val()){
		layer.alert('密码确认不一致，请重新输入');
		return false;
	}
	var param = {
		oldPwd: $('input[name="oldPwd"]').val(),
		newPwd: $('input[name="newPwd"]').val()
	}
	$.post($.kbase.ctx + '/personal/changePwd', param, function(data){
		layer.alert(data.response.message, function(index){
			parent.location.href = $.kbase.ctx + '/logout';
		});
	}, 'json');
	return false;
});