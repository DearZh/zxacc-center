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

$.post($.kbase.ctx + '/user/loadData', function(user){
	$('.zx-username').text(user.userInfo.username);
}, 'json');

//左侧导航
$('.layui-nav').on('click', '.layui-nav-child a', function(){
	var _this = this;
	layui.use('element', function(){
		var element = layui.element;
		
	});
});

/**
 * 创建 tab 页签，如果存在则直接打开
 * @param opts
 */
function addTab(opts){
	layui.use('element', function(){
		var element = layui.element;
		if ($('[lay-id="' + opts.id + '"]').length==0){
			element.tabAdd('zxTab', {
			    id: opts.id,
			    title: opts.title || 'Unnamed',
			    content: '<iframe src="' + opts.url + '"></iframe>'
			});
		}
		element.tabChange('zxTab', opts.id);
	});
}

//
layui.use('element', function(){
	var element = layui.element;
	
	//监听Y轴左侧的导航
	element.on('nav(zxYNav)', function(elem){
		if ($(elem).attr('_url')==undefined) return false;
		var _this = $(elem);
		
		addTab({
			id: $(_this).attr('id'),
			title: $(_this).text(),
			url: $.kbase.ctx + $(_this).attr('_url')
		});
		
		/*if ($('[lay-id="' + $(_this).attr('id') + '"]').length==0){
			element.tabAdd('zxTab', {
			    title: $(_this).text(),
			    content: '<iframe src="' + $.kbase.ctx + $(_this).attr('_url') + '"></iframe>',
			    id: $(_this).attr('id')
			});
		}
		element.tabChange('zxTab', $(_this).attr('id'));*/
	});
	
	element.on('tabDelete(zxTab)', function(data){
		if (data.index==0){
			return false;
		}
	});
	
	//首页Tab不删除
	$('.layui-tab-close').hide();
});

//
$('#navInfo, #navPasswd').click(function(){
	var _this = this;
	layui.use('element', function(){
		var element = layui.element;
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