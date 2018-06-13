//渲染grid
layui.use(['table', 'laydate'], function(){
	var table = layui.table;
	var laydate = layui.laydate;

	table.render({
		elem: '#grid',
		id: 'grid',
		url: $.kbase.ctx + '/exam/task/loadMyList',
		cellMinWidth: 80,
		cols: [[
		    {field:'paperName', title: '试卷名称', sort: true},
		    {field:'score', title: '得分', sort: true},
		    {field:'createDate', title: '开始时间', sort: true},
		    {field:'modifyDate', title: '完成时间', sort: true},
		    {field:'statusDesc', title: '状态', sort: true},
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
				location.href = $.kbase.ctx + '/exam/task/read?taskId=' + data.id;
			}else{
				layer.confirm('倒计时即将开始，确认进入考试吗', function(index){
					location.href = $.kbase.ctx + '/exam/task/exec?id=' + data.paper.id;
				});
			}
		}
	});
});