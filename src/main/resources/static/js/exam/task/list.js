//渲染grid
layui.use(['table', 'laydate', 'layer'], function(){
	var table = layui.table;
	var laydate = layui.laydate;
	window.layer = layui.layer;

	table.render({
		elem: '#grid',
		id: 'grid',
		url: $.kbase.ctx + '/exam/task/loadList',
		cellMinWidth: 80,
		cols: [[
		    {field:'name', title: '试卷名称', sort: true},
	        {toolbar: '#toolbar', width: 160}
	    ]],
	    page: false
	});
	
	//监听工具条
	table.on('tool(grid)', function(obj){
		var data = obj.data;
		if(obj.event === 'doExam'){
			if (data.task!=null && data.task.status==1){
				//考试完毕
				location.href = $.kbase.ctx + '/exam/task/read?taskId=' + data.task.id;
			}else{
				layer.confirm('倒计时即将开始，确认进入考试吗', function(index){
					location.href = $.kbase.ctx + '/exam/task/exec?id=' + data.id;
				});
			}
		}
	});
});

$('.zx-nav-img').click(function(){
	layer.confirm('确定进入首页吗', function(index, layero){
		location.href = $.kbase.ctx + '/main';
	});
});