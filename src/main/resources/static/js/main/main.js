//登出
$('#navLogout').click(function(){
	layui.use('layer', function(){
		var layer = layui.layer;
		layer.confirm('确认退出吗', function(index){
			layer.close(index);
			location.href = $.kbase.ctx + '/logout';
		});
	});
});

//左侧导航
$('.layui-nav').on('click', '.layui-nav-child a', function(){
	var _this = this;
	layui.use('element', function(){
		var element = layui.element;
		
	});
});


//
layui.use('element', function(){
	var element = layui.element;
	
	//监听Y轴左侧的导航
	element.on('nav(zxYNav)', function(elem){
		var _this = $(elem).find('a');
		if ($('[lay-id="' + $(_this).attr('id') + '"]').length==0){
			element.tabAdd('zxTab', {
			    title: $(_this).text(),
			    content: '<iframe src="' + $.kbase.ctx + $(_this).attr('_url') + '"></iframe>',
			    id: $(_this).attr('id')
			});
		}
		element.tabChange('zxTab', $(_this).attr('id'));
	});
});

