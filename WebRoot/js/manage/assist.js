Long.AssistSystemPanel = Ext.extend(Ext.Panel,{
	id:'assistsystempanel',
	title:'辅助功能管理',
	iconCls:'assist_icon',
	initComponent : function(){
	
	var assistmenu= new Ext.tree.TreePanel({
		id:'assistmenu',
		title:'辅助功能',
        iconCls:'menu_icon',
        width: 190,
        rootVisible:false,
        region:'west',
        split:true,
        animate : true,
        border:false,
        collapsible:true,
 		loader: new Ext.tree.TreeLoader({
        	dataUrl: 'assistMenu!list'
			}),
			tbar:new Ext.Toolbar([{
 				text:'展开',
 				icon:'images/icons/expand.png',
 				scope : this,
 				handler : function() {
					assistmenu.expandAll();
				}
 			 },{
 				text:'收起',
 				icon:'images/icons/collapse.png',
 				scope : this,
 				handler : function() {
					assistmenu.collapseAll();
				}
 			 }]),
        
        
        listeners : {
			'click': function(node, event) {
                clickNode(node,event,'assisttabs');
            }
		}
		
    });
    var assistmenuRoot = new Ext.tree.AsyncTreeNode({text:'辅助功能'});
    assistmenu.setRootNode(assistmenuRoot);
    assistmenuRoot.expand();
    
    var tabs = new Ext.TabPanel({
    	id:'assisttabs',
    	activeTab : 0, 
    	region: 'center',
    	animScroll : true, 
    	enableTabScroll : true,
    	border : false,
    	collapsible : false,
    	closable : false,
    	autoScroll : true,
    	plugins: new Ext.ux.TabCloseMenu(),
    	items : new Long.ReserveGrid()
    });

	
	Ext.applyIf(this, {
		border:false,
	    layout:'border',
		closable:true,
		items:[{
        	title:'导航',
        	
        	iconCls:'navi_icon',
        	width: 190,
            minSize: 150,
            maxSize: 250,
			collapsible : true,
			split :true,
			border:false,
			layout : {
				type : 'accordion',
				animate : true
			},
        	region:'west',
        	items:[assistmenu]
        },{
            region: 'center',
            split: true,
            border:false,
            layout: 'border',
            items: [tabs]
        }]
	});
   

	Long.AssistSystemPanel.superclass.initComponent.call(this);
	}
});