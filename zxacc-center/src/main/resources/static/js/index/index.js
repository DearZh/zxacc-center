layui.use(['carousel', 'form'], function(){
	var carousel = layui.carousel
	,form = layui.form;

	
	//图片轮播
	carousel.render({
		elem: '#zxLogo',
		width: '100%',
		height: '140px',
		interval: 5000
	});
});