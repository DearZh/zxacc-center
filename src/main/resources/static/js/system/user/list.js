layui.use(['table', 'laydate'], function(){
	var table = layui.table;
	var laydate = layui.laydate;

	table.render({
		elem: '#grid',
		id: 'grid',
		url: $.kbase.ctx + '/user/loadList',
		cellMinWidth: 80,
		cols: [[
		    {type:'checkbox'},
		    {type:'numbers'},
	        {field:'username', title: '账号', sort: true},
	        {field:'usernameCN', title: '用户名', sort: true},
	        {field:'sexDesc', title: '性别', sort: true},
	        {field:'birthday', title: '生日', sort: true},
	        {field:'phone', title: '手机', sort: true},
	        {field:'email', title: '邮箱', sort: true},
	        {field:'createDate', title: '创建日期', sort: true},
	        {field:'createUser', title: '创建人', sort: true}
	    ]],
	    page: true
	});
	
	laydate.render({
		elem: $('input[name="birthday"]')[0]
	});
	
	window.laytable = table;
});

//新增
$('#btnAdd, #btnEdit').click(function(){
	var _this = this;
	layui.use(['layer', 'table'], function(){
		var layer = layui.layer;
		var table = layui.table;
		
		
		//编辑
		if (_this.id=='btnEdit'){
			var checked = table.checkStatus('grid');
			if (checked.data.length>0){
				var row = checked.data[0];
				
				$('#id').val(row.id);
				$('input[name="username"]').val(row.username);
				$('input[name="usernameCN"]').val(row.usernameCN);
				$('input[name="sex"][value="' + row.sex + '"]').attr('checked', 'checked');
				$('input[name="birthday"]').val(row.birthday);
				$('input[name="phone"]').val(row.phone);
				$('input[name="email"]').val(row.email);
			}else{
				return false;
			}
		}else{
			$('#id').val('');
			$('input[name="username"]').val('');
			$('input[name="usernameCN"]').val('');
			$('input[name="birthday"]').val('');
			$('input[name="phone"]').val('');
			$('input[name="email"]').val('');
		}
		
		layer.open({
			type: 1,
			btn: ['保存'],
			area: ['auto', 'auto'],
			content: $('.zx-panel'),
			yes: function(index, layero){
				
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
						table.reload('grid', {
							page: {
								curr: 1 //重新从第 1 页开始
							},
							where: {
								key: {
									
								}
							}
						});
					}
					layer.close(index);
				});
				
			}
		});
	});
});

$('#btnDel').click(function(){
	layui.use(['layer', 'table'], function(){
		var layer = layui.layer;
		var table = layui.table;
		
		layer.confirm('确定删除吗', function(index){
			var checked = table.checkStatus('grid');
			if (checked.data.length>0){
				var row = checked.data[0];
				var param = {
					id: row.id
				}
				$.post($.kbase.ctx + '/user/delete', param, function(data){
					if (data.success){
						table.reload('grid', {page: {curr: 1}});
					}
				}, 'json');
			}else{
				return false;
			}
			layer.close(index);
		});
	});
});