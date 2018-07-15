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
		url: $.kbase.ctx + '/permission/loadList',
		cellMinWidth: 80,
		cols: [[
		    {type:'checkbox'},
		    {type:'numbers'},
	        {field:'name', title: '名称', sort: true},
	        {field:'key', title: '标识', sort: true},
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
			}else{
				return false;
			}
		}else{
			$('#id').val('');
			$('input[name="name"]').val('');
			$('input[name="key"]').val('');
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
				}
				
				$.post($.kbase.ctx + '/permission/save', param, function(data){
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
				$.post($.kbase.ctx + '/permission/delete', param, function(data){
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