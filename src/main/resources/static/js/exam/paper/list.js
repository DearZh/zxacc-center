var gradeIds = [];
var gradeNames = [];
var quesIds = []; //存储字符串数组，用于识别重复值
var questions = []; //存储json数组
//渲染grid
layui.use(['table', 'laydate'], function(){
	var table = layui.table;
	var laydate = layui.laydate;

	table.render({
		elem: '#grid',
		id: 'grid',
		url: $.kbase.ctx + '/exam/paper/loadList',
		cellMinWidth: 80,
		cols: [[
		    {type:'checkbox'},
		    {type:'numbers'},
	        {field:'name', title: '试卷名称', sort: true},
	        {field:'gradeName', title: '班级', width: 120, sort: true},
	        {field:'total', title: '总分', width: 80, sort: true},
	        {field:'limit', title: '限时', width: 80, sort: true},
	        {field:'createDate', title: '创建日期', width: 120, sort: true},
	        {field:'createUser', title: '创建人', width: 120, sort: true}
	    ]],
	    page: true
	});
	
});

//新增和保存
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
				if (gradeIds.length==0){
					layer.alert('请选择班级');
					return false;
				}
				
				//处理题目数据
				var _total = 0;
				var _arr = [];
				var _continue = true;
				$('#quesPanel tr').each(function(i, item){
					var json = {
						id: $(item).attr('_id'),
						score: $(item).find('input[name="score"]').val(),
						order: $(item).find('input[name="order"]').val()
					}
					if (json.score==''){
						$(item).find('input[name="score"]').focus();
						_continue = false;
						return false;
					}
					_arr.push(json);
					_total += Number(json.score);
				});
				
				if (!_continue) return false;
				
				if (_total!=Number($('input[name="total"]').val())){
					layer.alert('总分不匹配，请仔细检查分值');
					return false;
				}
				
				var param = {
					id: $('#id').val(),
					name: $('input[name="name"]').val(),
					limit: $('input[name="limit"]').val(),
					total: $('input[name="total"]').val(),
					gradeIds: gradeIds,
					questions: JSON.stringify(_arr)
				}
								
				$.post($.kbase.ctx + '/exam/paper/save', param, function(data){
					if (data.success){
						layer.alert('保存成功');
						table.reload('grid', {page: {curr: 1}});
						layer.close(index);
					}
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
		
		layer.confirm('确定删除吗', function(index){
			var checked = table.checkStatus('grid');
			if (checked.data.length>0){
				var row = checked.data[0];
				var param = {
					id: row.id
				}
				$.post($.kbase.ctx + '/exam/paper/delete', param, function(data){
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

//选择班级
$('#btnPick').click(function(){
	layui.use(['layer'], function(){
		var layer = layui.layer;
		layer.open({
			type: 2,
			btn: ['保存'],
			area: ['1000px', '460px'],
			content: $.kbase.ctx + '/exam/grade/list?_p=paper',
			yes: function(index, layero){
				var _win = $(layero).find("iframe")[0].contentWindow;
				//获取用户grid里选中的数据
				var checked = _win.laytable.checkStatus('grid');
				if (checked.data.length>0){
					$(checked.data).each(function(i, item){
						if ($.inArray(item.id, gradeIds)==-1){
							gradeIds.push(item.id);
							gradeNames.push(item.name);
						}
					});
					$('.zx-grade-panel').html(gradeNames.join(' '));
				}else{
					return false;
				}
				layer.close(index);
			}
		});
	});
});

//选择试题
$('#btnPickQues').click(function(){
	layui.use(['layer'], function(){
		var layer = layui.layer;
		layer.open({
			type: 2,
			btn: ['保存'],
			area: ['1000px', '460px'],
			content: $.kbase.ctx + '/exam/question/view?_p=paper',
			yes: function(index, layero){
				var _win = $(layero).find("iframe")[0].contentWindow;
				//获取用户grid里选中的数据
				var checked = _win.laytable.checkStatus('grid');
				if (checked.data.length>0){
					var arr = [];
					$(checked.data).each(function(i, item){
						if ($.inArray(item.id, quesIds)==-1){
							quesIds.push(item.id);
							questions.push({
								id: item.id,
								name: item.name,
								score: '',
								order: ''
							});
							arr.push({
								id: item.id,
								name: item.name,
								score: '',
								order: ''
							});
						}
					});
					$('#quesPanel').append(template('templateQues', arr));
				}else{
					return false;
				}
				layer.close(index);
			}
		});
	});
});

//数值校验
$('.zx-panel').on('blur', 'input[name="limit"], input[name="total"], input[name="score"]', function(){
	var re = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
	var _this = this;
	var _val = $(this).val().trim();
	if (_val!='' && !re.test(_val)){
		$(_this).focus();
		$(_this).select();
	}
});
//分数累加
$('.zx-panel').on('blur', 'input[name="score"]', function(){
	var _scoreTotal = 0;
	$('.zx-panel input[name="score"]').each(function(i, item){
		if ($(item).val()!='' && !isNaN(Number($(item).val()))){
			_scoreTotal += Number($(item).val());
		}
	});
	layui.use(['layer'], function(){
		var layer = layui.layer;
		layer.msg(_scoreTotal, {
		  offset: 't',
		  anim: 0
		});
	});
});