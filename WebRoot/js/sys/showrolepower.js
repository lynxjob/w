Ext.namespace('Long');
//角色权限记录
Long.ShowRolePowerTree = Ext.extend(Ext.tree.TreePanel,{
	id:'showrolepowertree',
    width:160,
	border:false,
	closable:true,
	enableDD: false,
	rootVisible:false,
	autoScroll: true,
	/**
	 * 监听器：单机非叶子节点，选中反选中叶子节点
	 */
	listeners : {
		'click': function(node,event) {
			if(!node.leaf){
				node.toggle();
			}
		},
		'checkchange': function(node, checked){
            if(checked){
            	/**
            	 * 角色权限记录选中，添加1条记录
            	 */
            	Ext.Ajax.request({
					url : 'auth!saveRolePower',
					scope : this,
					params :{
						roleId : this.roleId,
						powerId : node.id
					},
					success : function(response,options) {
						var respText = Ext.util.JSON.decode(response.responseText);
						Ext.ux.Toast.msg('系统提示',respText.msg);
					},
					failure : function(response, options) {
						Ext.ux.Toast.msg('系统提示',respText.errors.msg);
					}			
				});
            }
            else{
            	/**
            	 * 角色权限记录反选中，删除1条记录
            	 */
            	Ext.Ajax.request({
					url : 'auth!deleteRolePower',
					scope : this,
					params :{
						roleId : this.roleId,
						powerId : node.id
					},
					success : function(response,options) {
						var respText = Ext.util.JSON.decode(response.responseText);
						Ext.ux.Toast.msg('系统提示',respText.msg);
					},
					failure : function(response, options) {
						Ext.ux.Toast.msg('系统提示',respText.errors.msg);
					}			
				});
            }
        }
	},
	initComponent : function(){
		this.root=new Ext.tree.AsyncTreeNode({
			id:'0',
       	  	text:'总权限',
            expanded : true
        });
		this.loader=new Ext.tree.TreeLoader({
 			 dataUrl: 'auth!getRolePowers'+this.params
         });
		this.tbar=new Ext.Toolbar([{
				text : '刷新',
				icon:'images/icons/f5.png',
				scope : this,
 				handler : function() {
					this.root.reload();
				}
 			 },{
 				text:'展开',
 				icon:'images/icons/expand.png',
 				scope : this,
 				handler : function() {
					this.expandAll();
				}
 			 },{
 				text:'收起',
 				icon:'images/icons/collapse.png',
 				scope : this,
 				handler : function() {
					this.collapseAll();
				}
 			 },{
				text : '关闭',
				icon :'images/icons/cancel.png',
				handler : this.closeWin.createDelegate(this)
			}]);
        console.log('初始化组件：showrolepowertree');
		Long.ShowRolePowerTree.superclass.initComponent.call(this);
	},
	closeWin : function(){
		Ext.getCmp('giveRolePowerWin').close();
	}
});