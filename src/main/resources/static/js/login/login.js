$('input[name="username"]').focus();
$('#btnSubmit').click(function(){
	if ($('input[name="username"]').val().trim()==''){
		$('input[name="username"]').focus();
		return false;
	}
	if ($('input[name="password"]').val().trim()==''){
		$('input[name="password"]').focus();
		return false;
	}
	return true;
});