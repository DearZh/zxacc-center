var $$ = Dom7;
var app = new Framework7({
	root: '#app',
	name: '正欣会计',
	language: 'zh-CN',
	theme: 'ios',
	id: 'com.zhengxinacc.exam.task',
	panel: {
		swipe: 'left'
	},
	dialog: {
		buttonOk: '确定',
		buttonCancel: '取消'
	},
	toast: {
		position: 'center',
		closeTimeout: 2000,
	},
	routes: [
	     {
	    	 path: '/task/:id/:taskid/:status/',
	    	 //url: __ctx + '/exam/task/exec?id={{id}}',
	    	 async(routeTo, routeFrom, resolve, reject) {
	    		 var status = routeTo.params.status;
	    		 var paperId = routeTo.params.id;
	    		 var taskId = routeTo.params.taskid;
	    		 if (status==1) {
	    			 //考试完毕，则进入只读页面
	    			 resolve({ url: __ctx + '/exam/task/read?taskId=={{taskid}}' })
	    		 } else {
	    			 //进入考试页面
	    			 app.dialog.confirm('倒计时即将开始，确认进入考试吗', function(){
	    				 resolve({ url: __ctx + '/exam/task/exec?id={{id}}' })
	    			 })
	    		 }
	    	 },
	    	 on: {
	    		 pageInit: function (event, page) {
	    			 if (event.detail.route.params.status==1){
	    				 AppRead.pageInit(event, page);
	    			 }else{
	    				 AppExec.pageInit(event, page);
	    				 window.__clientX = 0;
	    				 app.on('touchstart:active', function(event){
	    					 window.__clientX = event.changedTouches[0].clientX;
	    				 });
	    				 app.on('touchend:active', function(event){
	    					 if (event.changedTouches[0].clientX - window.__clientX>100){
	    						 //从左至右，触发zx-prev
	    						 $$('.zx-prev').click();
	    					 }else if (event.changedTouches[0].clientX - window.__clientX<-100){
	    						 //从右至左，触发 zx-next
	    						 $$('.zx-next').click();
	    					 }
	    				 });
	    			 }
	    		 }, //end pageInit
	    		 pageAfterOut: function(event, page) {
	    			 if (event.detail.route.params.status==0){
	    				 AppExec.pageAfterOut(event, page);
	    				 app.off('touchstart:active');
	    				 app.off('touchend:active');
	    			 }
	     		 }
	    	 }
	     },
	],
	lazy: {
		threshold: 500,
		sequential: false,
	},
	on: {
		init: function(){
			var taskListCompile = Template7.compile($$('#templetTaskList').html());
			axios.get(__ctx + '/exam/task/loadList')
			.then(function (response) {
				$$('#taskList').html(taskListCompile(response.data));
			})
			.catch(function (error) {
				console.log(error);
			});
			axios.post(__ctx + '/user/loadData')
			.then(function (response) {
				$$('.zx-username').text(response.data.userInfo.username);
			})
			.catch(function (error) {
				console.log(error);
			});
			
			//
			$$('.zx-version').on('click', function () {
				app.dialog.alert('v1.0.0.Alpha');
			});
			$$('.zx-logout').on('click', function () {
				app.dialog.confirm('确认退出吗', function(){
					location.href = __ctx + '/logout';
				}, function(){
					window.history.pushState({}, 'title');
				});
			});

		} //end init
	}
});
//app.panel.open('left');
var swiper = app.swiper.create('.swiper-container', {
	speed: 400,
	spaceBetween: 10,
	loop: true,
	autoplay: {
		delay: 5000,
	},
});
var view = app.views.create('.view');
var toastLimit = app.toast.create({
	text: '距离考试结束仅剩10分钟，请抓紧时间'
});
var toastLimitLast = app.toast.create({
	text: '距离考试结束仅剩1分钟，请抓紧时间'
});

var taskPopup = app.popup.create({
	el: '.popup-task',
	backdrop: false,
	closeByBackdropClick: false,
	on: {
		opened: function (popup) {
			//开始倒计时
			console.log('Popup opened');
		},
		close: function (popup) {
			
		}
	}
});

//TODO 安卓监听手机物理返回键
window.history.pushState({}, 'title');
window.addEventListener("popstate", function(e) {
	$$('.zx-logout').click();
}, false);
