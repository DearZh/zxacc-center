//渲染grid
layui.use(['table', 'laydate', 'layer', 'form'], function(){
	window.laytable = layui.table;
	window.laydate = layui.laydate;
	window.layer = layui.layer;
	window.layform = layui.form;
});

//加载试卷数据
$.post($.kbase.ctx + '/exam/exec/loadPaper', {id: $('#id').val()}, function(data){
	//注意，这里返回的是 Task 对象
	var paper = data.paper;
	$('.zx-paper-title').text(paper.name);
	
	var questions = data.questionList;
	var i=0;
//	for (var key in questions){
//		//console.log(data.questions[key]);
//		var item = questions[key];
//		var _ind = i+1;
//		if (_ind<10) _ind='0'+_ind;
//		primaryClass = item.isReply?'':'layui-btn-primary';
//		$('.zx-ques-panel').append('<button class="layui-btn layui-btn-sm zx-btn ' + primaryClass + '" _id="' + item.id +'" _score="' + item.score + '" _reply="' + item.isReply + '">' + _ind + '</button>');
//		i++;
//	}
	$(questions).each(function(i, question){
		for (var key in question){ //只循环一次
			var item = question[key];
			var _ind = i+1;
			if (_ind<10) _ind='0'+_ind;
			primaryClass = item.isReply?'':'layui-btn-primary';
			$('.zx-ques-panel').append('<button class="layui-btn layui-btn-sm zx-btn ' + primaryClass + '" _id="' + item.id +'" _score="' + item.score + '" _reply="' + item.isReply + '">' + _ind + '</button>');
			i++;
		}
	});
	
	//默认选中第一题
	$('.zx-ques-panel .zx-btn:first').click();
	
	//倒计时
	var limit = data.limit || 120*60; //120分钟 limit单位是秒
	var t = limit; //秒数
	formatSandglass(t);
	window.setInterval(function(){
		formatSandglass(t--);
		window.tmpLimit = t;
	}, 1*1000);
}, 'json');
//沙漏
/*private */function formatSandglass(t){
	var d = Math.floor(t/60/60/24);
	var h = Math.floor(t/60/60%24);
	var m = Math.floor(t/60%60);
	var s = Math.floor(t%60);
	$('.zx-sandglass').text(formatHms(h) + ':' + formatHms(m) + ':' + formatHms(s));
}
//格式化时分秒
/*private */function formatHms(h){
	if (h<10) return '0'+ h;
	return h;
}
/* 答题卡元素单击事件 */
$('.zx-ques-panel').on('click', '.zx-btn', function(){
	var _this = this;
	var paperId = $('#id').val();
	var _id = $(_this).attr('_id'); //问题id
	
//	console.log($('input[name="ans"]:checked').length);
//	console.log($('input[name="ans"]:checked').val());
	//
	$('.zx-ques-panel').find('.zx-btn').removeClass('layui-btn-danger');
	$(_this).addClass('layui-btn-danger');
	$.post($.kbase.ctx + '/exam/exec/loadQues', {paperId: paperId, id: _id}, function(data){
		//isReply 标识当前是已答题
		$('.zx-ques').html(data.name + '(' + data.score + ' 分)');
		$('.zx-ques').attr('_type', data.type);
		layui.use(['form'], function(){
			var form = layui.form;
			if (data.type==0){
				$('.zx-ans').html(template('templateSingle', data.answers));
				if (data.isReply){
					$(data.answers).each(function(i, item){
						//console.log(item);
						if (item.mark){
							$('input[name="ans"][value="' + item.id + '"]').attr('checked', 'checked');
						}
					});
				}
				form.render('radio');
			}else if (data.type==1){
				$('.zx-ans').html(template('templateMulti', data.answers));
				if (data.isReply){
					$(data.answers).each(function(i, item){
						if (item.mark){
							$('input[name="ans"][value="' + item.id + '"]').attr('checked', 'checked');
						}
					});
				}
				form.render('checkbox');
			}else if (data.type==2){
				$('.zx-ans').html(template('templateTof', {}));
				if (data.isReply){
					$('input[name="ans"][value="' + data.keyMark + '"]').attr('checked', 'checked');
				}
				form.render('radio');
			}
			
			////////////////////////////////////////////////
			////////////答题卡保存////////////////////////////
			////////////////////////////////////////////////
			//多选题
			form.on('checkbox(ans)', function(data){
				//获取答案，如果已答题，则进行数据临时保存
				if ($('input[name="ans"]:checked').length>0){
					$(_this).removeClass('layui-btn-primary');
					var ans = [];
					$('input[name="ans"]:checked').each(function(i, item){
						ans.push($(item).val());
					});
					var param = {
						paperId: paperId,
						quesId: _id,
						ans: ans.join(','),
						limit: tmpLimit
					}
					$.post($.kbase.ctx + '/exam/exec/save', param, null, 'json');
				}
			});
			//判断题 和 单选题
			form.on('radio(ans)', function(data){
				$(_this).removeClass('layui-btn-primary');
				//获取答案，如果已答题，则进行数据临时保存
				var ans = [data.value];
				var param = {
					paperId: paperId,
					quesId: _id,
					ans: ans.join(','),
					limit: tmpLimit
				}
				$.post($.kbase.ctx + '/exam/exec/save', param, null, 'json');
			});
			
		});
	}, 'json');
});
/* 上一题 */
$('.zx-prev').click(function(){
	var curr = $('.zx-ques-panel .layui-btn-danger').index();
	if (curr==0) return false;
	curr--;
	$('.zx-ques-panel .zx-btn:eq(' + curr + ')').click();
});
/* 下一题 */
$('.zx-next').click(function(){
	var len = $('.zx-ques-panel .zx-btn').length;
	var curr = $('.zx-ques-panel .layui-btn-danger').index();
	curr++;
	if (curr==len) return false;
	$('.zx-ques-panel .zx-btn:eq(' + curr + ')').click();
});
/* 答题完毕 */
$('.zx-submit').click(function(){
	//校验用户是否有未回答的试题
	if ($('.zx-ques-panel').find('.layui-btn-primary').length>0){
		layer.alert('尚有未完成的试题，请完成后提交');
		return false;
	}
	
	//试题全部完成，则确认是否提交
	layer.confirm('确定提交吗', function(index){
		var param = {
			id: $('#id').val()
		};
		$.post($.kbase.ctx + '/exam/exec/submit', param, function(data){
			var taskId = data.id;
			layer.alert('提交成功', function(){
				location.href = $.kbase.ctx + '/exam/task/read?taskId=' + taskId;
			});
			//layer.close(index);
		}, 'json');
	});
});
//监听 window 离开事件，
//TODO 离开之前自动保存
$(window).on('beforeunload',function(){
//	$.post($.kbase.ctx + '/exam/exec/save', {id: $('#id').val()}, function(data){
//		
//	}, 'json');
	return '确定离开吗';
});


//////////////////////////////////////////////////////////
////////////////////////用户答题////////////////////////////
//////////////////////////////////////////////////////////
