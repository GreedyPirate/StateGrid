var menu = {
	setting: {
        isSimpleData: true,
        treeNodeKey: "mid",
        treeNodeParentKey: "pid",
        showLine: true,
        root: {
            isRoot: true,
            nodes: []
        }
    },
	loadMenuTree:function(){
		//用AJAX来动态的加载zTree
		$.post("elecMenuAction_loadMenu.do",{},function(privilegeDate){
			$("#menuTree").zTree(menu.setting, privilegeDate);
		});
	}
};

$().ready(function(){
	menu.loadMenuTree();
});
