function init(){
	window.permissionIds = [];
	window.permissionNames = [];
	window.userIds = [];
	window.userNames = [];
}
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
		url: $.kbase.ctx + '/role/loadList',
		cellMinWidth: 80,
		cols: [[
		    {type:'checkbox'},
		    {type:'numbers'},
	        {field:'name', title: '名称', sort: true},
	        {field:'key', title: '标识', sort: true},
	        {field:'permissionNames', title: '权限', sort: true, templet: function(row){
	        	var desc = [];
	        	$(row.permissions).each(function(index, item){
	        		desc.push(item.name);
	        	});
	        	return desc.join(', ');
	        }},
	        {field:'userNames', title: '用户', sort: true, templet: function(row){
	        	var desc = [];
	        	$(row.users).each(function(index, item){
	        		desc.push(item.userInfo.username);
	        	});
	        	return desc.join(', ');
	        }},
	        {field:'createDate', title: '创建日期', sort: true, templet: function(row){
	        	return dateFns.format(row.createDate, 'YYYY-MM-DD');
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
	init();
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
				$('input[name="name"]').val(row.name);
				$('input[name="key"]').val(row.key);
				
				if (row.permissions){
					$(row.permissions).each(function(i, item){
						if ($.inArray(item.id, permissionIds)==-1){
							permissionIds.push(item.id);
							permissionNames.push(item.name);
						}
					});
					$('.zx-permission-panel').html(permissionNames.join(' '));
				}
				
				if (row.users){
					$(row.users).each(function(i, item){
						if ($.inArray(item.id, userIds)==-1){
							userIds.push(item.id);
							userNames.push(item.userInfo.username);
						}
					});
					$('.zx-user-panel').html(userNames.join(' '));
				}
				
			}else{
				return false;
			}
		}else{
			$('#id').val('');
			$('input[name="name"]').val('');
			$('input[name="key"]').val('SYS_');
		}
		
		layer.open({
			type: 1,
			btn: ['保存'],
			area: ['auto', 'auto'],
			content: $('.zx-panel'),
			yes: function(index, layero){
				
				if ($('input[name="name"]').val().trim()==''){
					$('input[name="name"]').focus();
					return false;
				}
				if ($('input[name="key"]').val().trim()==''){
					$('input[name="key"]').focus();
					return false;
				}
				
				var param = {
					id: $('#id').val(),
					name: $('input[name="name"]').val(),
					key: $('input[name="key"]').val(),
					permissionIds: permissionIds.join(','),
					userIds: userIds.join(',')
				}
				
				$.post($.kbase.ctx + '/role/save', param, function(data){
					if (data.success){
						layer.alert('保存成功');
						table.reload('grid', {page: {curr: 1}});
					}
					layer.close(index);
				});
				
			}
		});
	});
});
//删除
$('#btnDel').click(function(){
	layui.use(['layer', 'table'], function(){
		var layer = layui.layer;
		var table = layui.table;
		
		var checked = table.checkStatus('grid');
		if (checked.data.length>0){
			layer.confirm('确定删除吗', function(index){
				var rows = checked.data;
				var ids = [];
				$(rows).each(function(index, item){
					ids.push(item.id);
				});
				var param = {
					ids: ids
				}
				$.post($.kbase.ctx + '/role/delete', param, function(data){
					if (data.success){
						table.reload('grid', {page: {curr: 1}});
					}
					layer.close(index);
				}, 'json');
			});
			
		}
	});
});
//用户查询
$('#btnQuery').click(function(){
	var keyword = $('#keyword').val();
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
//选择权限
$('#btnPick').click(function(){
	layui.use(['layer'], function(){
		var layer = layui.layer;
		layer.open({
			type: 2,
			btn: ['保存'],
			area: ['1000px', '460px'],
			content: $.kbase.ctx + '/permission/list?_p=role',
			yes: function(index, layero){
				var _win = $(layero).find("iframe")[0].contentWindow;
				//获取用户grid里选中的数据
				var checked = _win.laytable.checkStatus('grid');
				if (checked.data.length>0){
					$(checked.data).each(function(i, item){
						if ($.inArray(item.id, permissionIds)==-1){
							permissionIds.push(item.id);
							permissionNames.push(item.name);
						}
					});
					$('.zx-permission-panel').html(permissionNames.join(' '));
				}else{
					return false;
				}
				layer.close(index);
			}
		});
	});
});
$('#btnClear').click(function(){
	permissionIds = [];
	permissionNames = [];
	$('.zx-permission-panel').empty();
});

//选择用户
$('#btnPickUser').click(function(){
	layui.use(['layer'], function(){
		var layer = layui.layer;
		layer.open({
			type: 2,
			btn: ['保存'],
			area: ['1000px', '460px'],
			content: $.kbase.ctx + '/user/list?_p=role',
			yes: function(index, layero){
				var _win = $(layero).find("iframe")[0].contentWindow;
				//获取用户grid里选中的数据
				var checked = _win.laytable.checkStatus('grid');
				if (checked.data.length>0){
					$(checked.data).each(function(i, item){
						if ($.inArray(item.id, userIds)==-1){
							userIds.push(item.id);
							userNames.push(item.usernameCN);
						}
					});
					$('.zx-user-panel').html(userNames.join(' '));
				}else{
					return false;
				}
				layer.close(index);
			}
		});
	});
});
$('#btnClearUser').click(function(){
	userIds = [];
	userNames = [];
	$('.zx-user-panel').empty();
});