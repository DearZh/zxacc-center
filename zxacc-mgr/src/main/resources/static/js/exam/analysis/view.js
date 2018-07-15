$.extend(echarts, {
	zxInit: function(elem){
		return echarts.init(elem, 'macarons');
	}
});
// 基于准备好的dom，初始化echarts实例
var pieChart = echarts.zxInit($('#piePanel')[0]);

$.get($.kbase.ctx + '/exam/analysis', {paperId: $('#paperId').val()}, function(data){
	var pieOption = {
	    title : {
	        text: '考试结果分析',
	        subtext: data.paper.name,
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c}人 ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: ['及格', '未及格', '未完成', '未参与']
	    },
	    series : [
	        {
	            name: '学员占比',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                {key: 'pass', value: data.passUserList.length, name:'及格', bind: data.passUserList},
	                {key: 'fail', value: data.failUserList.length, name:'未及格', bind: data.failUserList},
	                {key: 'unfinished', value: data.unfinishedUserList.length, name:'未完成', bind: data.unfinishedUserList},
	                {key: 'miss', value: data.missUserList.length, name:'未参与', bind: data.missUserList}
	            ],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
	//使用刚指定的配置项和数据显示图表。
	pieChart.setOption(pieOption);
	pieChart.on('click', function (params) {
		//开始写数据
		var xAxisData = []; //学生名称
		var yAxisData = []; //学生成绩
		$(params.data.bind).each(function(index, item){
			if (params.data.key!='miss'){
				xAxisData.push(item.createUser);
				yAxisData.push(item.score);
			}else{
				xAxisData.push(item);
			}
		});
		
		//及格或不及格显示成绩
		if (params.data.key=='pass' || params.data.key=='fail'){
			
			//开始画柱状图
			var barChart = echarts.zxInit($('#barPanel')[0]);
			
			var barOption = {
				title: {
			        text: '成绩详情',
			        subtext: params.data.name
			    },
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			        	name: '学生',
			        	nameLocation: 'end',
			            type: 'category',
			            data: xAxisData,
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name: '成绩',
			            type: 'bar',
			            barWidth: '60%',
			            data: yAxisData
			        }
			    ]
			};
			
			barChart.setOption(barOption);
		}else{
			//如果是未完成和未参与，则给出学员清单
			layui.use(['layer'], function(){
				var layer = layui.layer;
				layer.msg(xAxisData.join(', '), {
					offset: 't',
					anim: 6
				});
			});
		}
	});

	var xtmpAxisData = [];
	var ytmpAxisData = [];
	var yTimeData = [];
	//==============================================================
	//成绩佼佼者
	//==============================================================
	var scoreChart = echarts.zxInit($('#scorePanel')[0]);
	$(data.scoreList).each(function(index, item){
		xtmpAxisData.push(item.createUser);
		ytmpAxisData.push(item.score);
	});
	
	
	var scoreOption = {
		title: {
	        text: '成绩 Top 10'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	        	name: '学生',
	        	nameLocation: 'end',
	            type: 'category',
	            data: xtmpAxisData,
	            axisTick: {
	                alignWithLabel: true
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name: '成绩',
	            type: 'bar',
	            barWidth: '60%',
	            data: ytmpAxisData
	        }
	    ]
	};
	scoreChart.setOption(scoreOption);
	
	//==============================================================
	//考试完成积极者
	//==============================================================
	var finishChart = echarts.zxInit($('#finishPanel')[0]);
	xtmpAxisData = [];
	ytmpAxisData = [];
	$(data.firstFinishList).each(function(index, item){
		xtmpAxisData.push(item.createUser);
		ytmpAxisData.push(item.score);
		//alert(dateFns.format(item.modifyDate, 'YYYY-MM-DD HH:mm'));
	});
	
	
	var finishOption = {
		title: {
	        text: '交卷 Top 10'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
	        },
	        formatter: function (params, ticket, callback) {
	        	var param = params[0];
	        	var item = data.firstFinishList[param.dataIndex];
	        	var date = dateFns.format(item.modifyDate, 'YYYY-MM-DD HH:mm');
	        	return param.seriesName + ': ' + param.value + ' <br/> 交卷时间: ' + date;
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	        	name: '学生',
	        	nameLocation: 'end',
	            type: 'category',
	            data: xtmpAxisData,
	            axisTick: {
	                alignWithLabel: true
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name: '成绩',
	            type: 'bar',
	            barWidth: '60%',
	            data: ytmpAxisData
	        }
	    ]
	};
	finishChart.setOption(finishOption);
	
}, 'json');
