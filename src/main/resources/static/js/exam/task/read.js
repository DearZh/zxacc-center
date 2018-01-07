$.post($.kbase.ctx + '/exam/task/loadTask', {taskId: $('#taskId').val()}, function(task){
	window.title = task.paper.name;
	$('.zx-name').text(task.paper.name);
	$('.zx-total').text(task.score);
	
	var i = 0;
	for (var key in task.questions){
		i++;
		var item = task.questions[key];
		var quesName = i + "、" + item.name;
		if (item.type==0){
			//单选题
			var content = template('templateSingle', item.answers);
			$('#panel').append(template('templateFaq', {quesName: quesName, score: item.score,content: content}));
		}else if (item.type==1){
			//多选题
			var content = template('templateMulti', item.answers);
			$('#panel').append(template('templateFaq', {quesName: quesName, score: item.score, content: content}));
		}else if (item.type==2){
			//判断题
			var content = template('templateTof', item);
			$('#panel').append(template('templateFaq', {quesName: quesName, score: item.score, content: content}));
		}
	}
	layui.use(['form'], function(){
		var form = layui.form;
		form.render();
		
		$('.layui-form-item span').each(function(i, item){
			var text = $(item).text();
			if (text.indexOf('(正确答案)')!=-1){
				$(item).html(text.replace('(正确答案)', '<span class="zx-highlight">(正确答案)</span>'));
			}
		});
	});
	
}, 'json');