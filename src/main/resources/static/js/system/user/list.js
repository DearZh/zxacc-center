/* 不区分大小写 */
String.prototype.endWithIgnoreCase = function(end){
	try{
		if (this.toLowerCase().lastIndexOf(end.toLowerCase())==(this.length-end.length)){
			return true;
		}else{
			return false;
		}
	}catch(e){return false;}
}
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
	        {field:'usernameCN', title: '用户名', sort: true, templet: function(row){
	        	return row.userInfo.username;
	        }},
	        {field:'sexDesc', title: '性别', sort: true, templet: function(row){
	        	return row.userInfo.sex==1?'男':'女';
	        }},
	        {field:'birthday', title: '生日', sort: true, templet: function(row){
	        	return row.userInfo.birthday!=null?dateFns.format(row.userInfo.birthday, 'YYYY-MM-DD'):'';
	        }},
	        {field:'phone', title: '手机', sort: true, templet: function(row){
	        	return row.userInfo.phone;
	        }},
	        {field:'email', title: '邮箱', sort: true, templet: function(row){
	        	return row.userInfo.email;
	        }},
	        {field:'createDate', title: '创建日期', sort: true, templet: function(row){
	        	return row.createDate!=null?dateFns.format(row.createDate, 'YYYY-MM-DD'):'';;
	        }},
	        {field:'createUser', title: '创建人', sort: true}
	    ]],
	    done: function(res, curr, count){
	    	$('.layui-table-body').height($(window).height()-130);
	    },
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
	layui.use(['layer', 'table', 'form'], function(){
		var layer = layui.layer;
		var table = layui.table;
		var form = layui.form;
		
		
		//编辑
		if (_this.id=='btnEdit'){
			var checked = table.checkStatus('grid');
			if (checked.data.length>0){
				var row = checked.data[0];
				
				$('#id').val(row.id);
				$('input[name="username"]').val(row.username);
				$('input[name="usernameCN"]').val(row.userInfo.username);
				$('input[name="sex"][value="' + row.userInfo.sex + '"]').attr('checked', 'checked');
				$('input[name="birthday"]').val(row.userInfo.birthday!=null?dateFns.format(row.userInfo.birthday, 'YYYY-MM-DD'):'');
				$('input[name="phone"]').val(row.userInfo.phone);
				$('input[name="email"]').val(row.userInfo.email);
				
				form.render();
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
					console.log(data);
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
					}else{
						layer.alert(data.response.message);
					}
					layer.close(index);
				});
				
			}
		});
	});
});
//用户删除
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
//用户导入
$('#btnImport').click(function(){
	$('#userFile').click();
});
$('#userFile').change(function(){
	var files = this.files;
	if (files.length>0){
		var file = files[0];
		if (!file.name.endWithIgnoreCase('.xlsx')){
			layer.alert('仅支持2003版本以上的excel文档');
			return false;
		}
		var formData = new FormData();
        formData.append('userFile', file);
        $.ajax({
            type: 'POST',
            url: $.kbase.ctx + '/user/import',
            data: formData,
            dataType: 'json',
            contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置(详见：#1)
            processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post，详见：#2)
            success: function(response) {
            	if (response.success){
            		layer.alert('导入成功', function(index){
            			table.reload('grid', {page: {curr: 1}});
            			layer.close(index);
            		});
            	}else{
            		layer.alert('导入失败');
            	}
            }
        });
	}
});
//用户查询
$('#btnQuery').click(function(){
	var keyword = $('#keyword').val();
//	if (keyword=='') {
//		$('#keyword').select();
//		return false;
//	}
	
	layui.use(['table'], function(){
		var table = layui.table;
		table.reload('grid', {
			page: {curr: 1},
			where: {
				keyword: keyword
			}
		});
	});
});
$('#keyword').keyup(function(e){
	if (e.keyCode==13){
		$('#btnQuery').click();
	}
});