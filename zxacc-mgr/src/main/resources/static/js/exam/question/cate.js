layui.use(['layer', 'table', 'form'], function(){
	window.layer = layui.layer;
	window.laytable = layui.table;
	window.layform = layui.form;

});

/***********************  左侧分类目录树  ****************************/
var setting = {
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
