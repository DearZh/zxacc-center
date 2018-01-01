var ansCount = 0;
layui.use(['layer', 'table', 'form'], function(){
	window.layer = layui.layer;
	window.laytable = layui.table;
	window.layform = layui.form;

	//监听题目类型切换，每次切换题目类型都要清空答案面板
	layform.on('radio(type)', function(data){
		$('.zx-ans-panel').empty();
		ansCount = 0;
		if (data.value==2){
			//如果是判断题，则展示 zx-tof-panel，隐藏 zx-ans-add
			$('.zx-tof-panel').show();
			$('.zx-ans-add').hide();
		}else{
			//隐藏 zx-tof-panel， 展示 新增答案 zx-ans-add
			$('.zx-tof-panel').hide();
			$('.zx-ans-add').show();
		}
	});
	//Fixme 不明白为什么这里要手动写监听事件
	layform.on('checkbox(multi-ans)', function(data){
		if (data.elem.checked){
			data.elem.checked = false;
			$(data.elem).parent('label').siblings('div').find('input[name="answer"]').attr('_checked', true);
		}else{
			data.elem.checked = true;
			$(data.elem).parent('label').siblings('div').find('input[name="answer"]').attr('_checked', false);
		}
	});
	//单选题监听
	layform.on('radio(single-ans)', function(data){
		$('.zx-ans-panel').find('input[name="answer"]').attr('_checked', false);
		$(data.elem).parent('label').siblings('div').find('input[name="answer"]').attr('_checked', true);
	});
	
	/***********************  右侧试题面板  ****************************/
	laytable.render({
		elem: '#grid',
		id: 'grid',
		url: $.kbase.ctx + '/exam/question/loadList',
		cellMinWidth: 80,
		cols: [[
		    {type:'checkbox'},
		    {type:'numbers'},
	        {field:'name', title: '问题名称', sort: true},
	        {field:'typeDesc', title: '类型', width: 100, sort: true},
	        {field:'createDate', title: '创建日期', width: 120, sort: true},
	        {field:'createUser', title: '创建人', width: 100, sort: true}
	    ]],
	    page: true
	});
	/***********************  右侧试题面板  ****************************/
	
});

/***********************  左侧分类目录树  ****************************/
var setting = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom
    },
    async: {
		enable: true,
		url: $.kbase.ctx + '/exam/question/loadCate',
		autoParam: ['id', 'name', 'level'],
		otherParam: {},
		dataFilter: function(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
	},
	callback: {
		onAsyncSuccess: function(event, treeId, treeNode, msg){
			if (treeNode==undefined){
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				var rootNode = zTree.getNodes()[0];
				zTree.expandNode(rootNode, true);
			}
		},
		onClick: function(event, treeId, treeNode){
			var cateId = treeNode.id;
			cateId = cateId=='ROOT'?'':cateId;
			laytable.reload('grid', {page: {curr: 1}, where: {cateId: cateId}});
		}
	}
};
$.fn.zTree.init($("#tree"), setting);

/* ztree 节点 hover 显示toolbar */
function addHoverDom(treeId, treeNode) {
	if (treeNode.id=="ROOT") return false;
    var nodeObj = $("#" + treeNode.tId + "_span");
    if ($(nodeObj).siblings('.zx-toolbar').show().length>0) return false;
    
    var toolbar = template('templateToolbar', {tid: treeNode.tId});
    nodeObj.after(toolbar);
};
/* ztree 节点 remove hover 隐藏 toolbar */
function removeHoverDom(treeId, treeNode) {
	var nodeObj = $("#" + treeNode.tId + "_span");
	$(nodeObj).siblings('.zx-toolbar').hide();
};
/* 监听 ztree toolbar 操作 */
$('body').on('click', '.zx-toolbar', function(){
	var _this = this;
	var zTree = $.fn.zTree.getZTreeObj("tree");
	var tid = $(_this).attr('tid');
	var treeNode = zTree.getNodeByTId(tid);
	
	if ($(_this).hasClass('add')){
		layer.prompt({title: '请输入分类名称', formType: 0}, function(pass, index){
			$.post($.kbase.ctx + '/exam/question/saveCate', {pid: treeNode.id, name: pass}, function(data){
				zTree.addNodes(treeNode, {id:data.id, pId:treeNode.id, name:pass});
				layer.close(index);
			}, 'json');
		});
	}else if ($(_this).hasClass('edit')){
		layer.prompt({title: '请输入分类名称', formType: 0, value: treeNode.name}, function(pass, index){
			$.post($.kbase.ctx + '/exam/question/saveCate', {id: treeNode.id, pid: treeNode.pid, name: pass}, function(data){
				var nodeObj = $("#" + treeNode.tId + "_span");
				$(nodeObj).text(pass);
				layer.close(index);
			}, 'json');
		});
	}else if ($(_this).hasClass('remove')){
		layer.confirm('确定删除吗', function(index, layero){
			$.post($.kbase.ctx + '/exam/question/delCate', {id: treeNode.id}, function(data){
				if (!data.success){
					layer.alert(data.response.message);
				}else{
					zTree.removeNode(treeNode);
				}
				layer.close(index);
			}, 'json');
		});
	}

	//单击操作后隐藏 toolbar
	var nodeObj = $("#" + treeNode.tId + "_span");
	$(nodeObj).siblings('.zx-toolbar').hide();	
});
/***********************  左侧分类目录树  ****************************/
/****************************************************************/

/**************************右侧面板操作*****************************/
$('#btnAdd, #btnEdit').click(function(){
	var _this = this;
	if ($(_this).attr('id')=='btnAdd'){
		var zTree = $.fn.zTree.getZTreeObj("tree");
		var treeNodes = zTree.getSelectedNodes();
		if (treeNodes.length==0){
			layer.alert('请选择所属分类');
			return false;
		}
		var treeNode = treeNodes[0];
		if (treeNode.id=='ROOT'){
			layer.alert('请选择子分类');
			return false;
		}
		$('input[name="catename"]').val(treeNode.name);
		$('input[name="cateid"]').val(treeNode.id);
		$('input[name="name"]').val('');
		$('.zx-ans-panel').html('');
	}else{
		var checked = laytable.checkStatus('grid');
		if (checked.data.length>0){
			var row = checked.data[0];
			$('input[name="catename"]').val(row.catename);
			$('input[name="cateid"]').val(row.cateid);
			$('input[name="name"]').val(row.name);
			$('input[name="type"][value="' + row.type + '"]').attr('checked', 'checked');
		}
	}
	
	
	layer.open({
		type: 1,
		btn: ['保存'],
		area: ['auto', 'auto'],
		content: $('.zx-panel'),
		yes: function(index, layero){
			//TODO 保存问题和答案
			
			var type = $('input[name="type"]:checked').val();
			
			if ($('input[name="name"]').val().trim()==''){
				$('input[name="name"]').focus();
				return false;
			}
			if (type!=2){
				if ($('input[name="answer"]').length==0){
					layer.alert('请新增答案');
					return false;
				}
				//判断答案为空的input，如果存在，需要全部填满才能提交
				if ($('input[name="answer"]').filter(function(index){
					return $(this).val()=='';
				}).length>0){
					$('input[name="answer"]').filter(function(index){
						return $(this).val()=='';
					}).focus();
					return false;
				}
			}

			var param = {
				id: $('input[name="id"]').val(),
				cateid: $('input[name="cateid"]').val(),
				catename: $('input[name="catename"]').val(),
				name: $('input[name="name"]').val(),
				type: type
			}
			
			if (type==2){
				param.key = $('input[name="tof"]:checked').val();
			}else{
				var checked = $('input[name="order"]:checked').val();
				if (checked==undefined){
					layer.alert('请勾选正确的答案');
					return false;
				}
			}
			
			var answers = [];
			$('input[name="answer"]').each(function(i, item){
				answers.push({id: $(item).attr('_id'), content: $(item).val(), key: $(item).attr('_checked')});
			});
			param.answers = JSON.stringify(answers);
			$.post($.kbase.ctx + '/exam/question/save', param, function(data){
				console.log(data);
				laytable.reload('grid', {
					page: {
						curr: 1 //重新从第 1 页开始
					}
				});
				layer.close(index);
			}, 'json');
		}
	});
});
//删除题目
$('#btnDel').click(function(){
	var checked = laytable.checkStatus('grid');
	if (checked.data.length==0) return false;
	
	layer.confirm('确定删除吗', function(index){
		var row = checked.data[0];
		var param = {
			id: row.id
		}
		$.post($.kbase.ctx + '/exam/question/delete', param, function(data){
			if (data.success){
				laytable.reload('grid', {page: {curr: 1}});
			}
		}, 'json');
		layer.close(index);
	});
});
//增加答案
$('body').on('click', '.zx-ans-add', function(){
	var type = $('input[name="type"]:checked').val();
	$('.zx-ans-panel').append(template('templateAns', {type: type, value: ansCount}));
	ansCount++;
	if (type==0){	//单选题
		layform.render('radio');
	}else{	//多选题和判断题
		layform.render('checkbox');
	}
});