Ext.namespace('Long');
//树形结构的JS
Long.ShedTypeTree = Ext.extend(Ext.tree.TreePanel,{
	id:'shedtypetree',
	title:'大棚类型列表',
    width:180,
    minSize:150,
	maxSize:200,
	split :true,
	border:false,
	collapsible : true,
	rootVisible:true,
	autoScroll: true,
	listeners : {
		'click': function(node,event) {
			if(!node.leaf){
				node.toggle();
			}
			var tempGrid=Ext.getCmp('shedgrid');
			var tempStore=tempGrid.getStore();
			tempGrid.setTitle('大棚类型['+node.text+']');
			tempStore.baseParams = {'shedTypeId' :node.id};
			tempStore.removeAll();
			tempStore.reload({
				params : {
					start : 0,
					limit : 30
				},
				callback : function(rs) {
					if (!rs || rs.length < 1) {
						Ext.ux.Toast.msg('系统提示','没有找到符合条件的记录！');
					}
				}
			});
		}
	},
	initComponent : function(){
		this.root=new Ext.tree.AsyncTreeNode({
			id:'0',
       	  	text:'大棚类型',
            expanded : true
        });
		this.loader=new Ext.tree.TreeLoader({
 			 dataUrl: 'shedType!list'
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
 			 }]);
		this.contextMenu = new Ext.menu.Menu({
				items:[{
			            text:'新建',
			            icon:'images/icons/add.png',
			            handler: this.add.createDelegate(this),
			            hidden:!Long.spTAdd
			        },{
			        	text:'编辑',
			        	icon:'images/icons/edit.png',
			        	handler: this.edit.createDelegate(this),
			        	hidden:!Long.spTEdit
			        },{
			        	text:'删除',
			        	icon:'images/icons/delete.png',
			        	handler: this.deleteOp.createDelegate(this),
			        	hidden:!Long.spTDelete
			        }]
	    	});
		this.on('contextmenu',this.contextHandler,this);
		Long.ShedTypeTree.superclass.initComponent.call(this);
	},
	contextHandler : function contextmenu(a,b) {
		this.selectedNode = new Ext.tree.TreeNode( {
			id:a.id,
			text:a.text
		});
		this.contextMenu.showAt(b.getXY());
	},
	createAddForm:function(){
		var form= new Ext.FormPanel({
			defaultType:'textfield',
			labelAlign: 'right',
			labelWidth:60,
			frame:true,
			items :[{
					xtype:'hidden',
					name:'parentId',
					value:this.selectedNode.id
				},{
					width:170,
					fieldLabel:'父类型',
					disabled:true,
					name:'parentName',
					value:this.selectedNode.text
				},{
					width:170,
					fieldLabel:'类型名称',
					name:'name',
					allowBlank: false,
					blankText:'大棚类型名称不为空'
				},{
					width:170,
					fieldLabel:'类型描述',
					name:'des',
					allowBlank: false,
					blankText:'大棚类型描述不为空'
				}]
		});
		return form;
	},
	add : function(){
		this.addForm=this.createAddForm();
		var win = new Ext.Window({
			id : 'addOrgWin',
			title : '添加类型',
			width : 300,
			height : 160,
			closeAction : 'close',
			layout : 'fit',
			buttonAlign : 'center',
			resizable : false,
			closable:false,
			modal:true,
			items:[this.addForm],
			buttons:[{
					text:'确定',
					icon:'images/icons/ok.png',
					handler: this.submitAddForm.createDelegate(this)
				},{
					text : '取消',
					icon:'images/icons/cancel.png',
					handler:function(){
						win.close();
					}
				}]
		});
		win.show();
	},
	submitAddForm : function(){
		var form=this.addForm.form;
		if(form.isValid()==false){
			return;
		}
		form.submit({
			waitTitle : '请稍后',
			waitMsg : '正在提交信息···',
			url : 'shedType!save',
			scope : this,
			success : function(form, action) {
				Ext.ux.Toast.msg('系统提示',action.result.msg);
				Ext.getCmp('addOrgWin').close();
				this.root.reload();
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('系统提示', action.result.msg);
			}
		});
	},
	createEditForm:function(){
		var form= new Ext.FormPanel({
			defaultType:'textfield',
			labelAlign: 'right',
			labelWidth:60,
			frame:true,
			items :[{
					xtype:'hidden',
					name:'id',
					value:this.selectedNode.id
				},{
					width:170,
					fieldLabel:'大棚类型名称',
					name:'name',
					value:this.selectedNode.text,
					allowBlank: false,
					blankText:'大棚类型名称不为空'
				}]
		});
		return form;
	},
	edit : function(){
		this.editForm=this.createEditForm();
		var win = new Ext.Window({
			id : 'editOrgWin',
			title : '修改类型',
			width : 300,
			height : 110,
			closeAction : 'close',
			layout : 'fit',
			buttonAlign : 'center',
			resizable : false,
			closable:false,
			modal:true,
			items:[this.editForm],
			buttons:[{
					text:'确定',
					icon:'images/icons/ok.png',
					handler: this.submitEditForm.createDelegate(this)
				},{
					text : '取消',
					icon:'images/icons/cancel.png',
					handler:function(){
						win.close();
					}
				}]
		});
		win.show();
	},
	submitEditForm : function(){
		var form=this.editForm.form;
		if(form.isValid()==false){
			return;
		}
		form.submit({
			waitTitle : '请稍后',
			waitMsg : '正在提交信息···',
			url : 'shedType!update',
			scope : this,
			success : function(form, action) {
				Ext.ux.Toast.msg('系统提示',action.result.msg);
				Ext.getCmp('editOrgWin').close();
				this.root.reload();
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('系统提示', action.result.msg);
			}
		});
	},
	deleteOp : function(){
		if(this.selectedNode.id==0){
			Ext.ux.Toast.msg('系统提示', '根目录不允许删除！');
			return;
		}
		Ext.Msg.confirm('系统提示', '确定要删除所选大棚类型吗？', function(btn){
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : 'shedType!delete',
					scope : this,
					params :{
						id : this.selectedNode.id
					},
					success : function(response,options) {
						var respText = Ext.util.JSON.decode(response.responseText);
						Ext.ux.Toast.msg('系统提示',respText.msg);
						if(respText.success){
							this.root.reload();
						}
					},
					failure : function(response, options) {
                        Ext.ux.Toast.msg('系统提示','该分类下存在大棚实体,故不予删除!');
					}			
				});
			}
		}, this);
	}
});
Long.ShedGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'shedgrid',
	title:'大棚管理',
	baseUrl:'shed!listGrid',
	pageSize : 30,
	initComponent : function() {
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,{
				header:'大棚编号',
				dataIndex:'id'
			},{
				header:'大棚名称',
				dataIndex:'name'
			},{
				header:'大棚描述',
				dataIndex:'des'
			},{
               header:'大棚类型',
               dataIndex:'shedTypeName'
            },{
                header:'大棚作物',
                dataIndex:'cropName'
            },{
               header:'创建者',
               dataIndex:'creatorName'
            },{
                header:'创建时间',
                dataIndex:'cdate'
            }]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name:'name',
				type:'string'
			},{
				name:'des',
				type:'string'
			},{
                name:'shedTypeName',
                type:'string'
            },{
                name:'cropName',
                type:'string'
            },{
                name:'creatorName',
                type:'string'
            },{
                name:'cdate',
                type:'string'
            }]);
		this.tbar = new Ext.Toolbar([
            {
				text : '刷新',
				icon : 'images/icons/f5.png',
				handler: this.refresh.createDelegate(this)
		    },{
                text:'添加',
                icon : 'images/icons/add.png',
                handler: this.add.createDelegate(this),
                hidden:!Long.spAdd
            },{
                text:'修改',
                icon : 'images/icons/edit.png',
                handler: this.edit.createDelegate(this),
                hidden:!Long.spEdit
            },{
                text : '删除',
                icon : 'images/icons/delete.png',
                handler: this.deleteOp.createDelegate(this),
                hidden:!Long.spDelete
            },{
                text:'移动',
                icon : 'images/icons/move.png',
                handler : this.move.createDelegate(this),
                hidden:!Long.spMove
            }
        ]);
		Long.ShedGrid.superclass.initComponent.call(this);
	},
    createAddForm:function(){
        var form= new Ext.FormPanel({
            defaultType:'textfield',
            labelAlign: 'right',
            labelWidth:60,
            frame:true,
            items :[{
                    xtype:'hidden',
                    name:'id'
                },{
                    xtype:'hidden',
                    name:'shedTypeId',
                    value:this.store.baseParams['shedTypeId']
                },{
                    width:170,
                    fieldLabel:'大棚名称',
                    name:'name',
                    allowBlank: false,
                    blankText:'大棚名称不为空'
                },{
                    width:170,
                    fieldLabel:'大棚描述',
                    name:'des',
                    allowBlank: false,
                    blankText:'大棚描述不为空'
                },{
                    xtype : 'combo',
                    mode : 'remote',
                    hiddenName : 'crop_id',
		            fieldLabel : '大棚作物',
		            displayField : 'crop_name',
		            valueField : 'crop_id',
		            typeAhead : true,
		            emptyText : '请选择大棚作物...',
		            triggerAction : 'all',
		            store : new Ext.data.Store({
		                proxy : new Ext.data.HttpProxy({
		                            url : 'crop!load'
		                        }),
		                reader : new Ext.data.ArrayReader({},[{
		                            name : 'crop_id',
                                    type:'int'
		                        },{
		                            name : 'crop_name',
                                    type:'string'
		                        }])
		                })
                }]
        });
        return form;
    },
    add : function(){   
        var typeId=this.store.baseParams['shedTypeId'];
        if(typeId==undefined ||typeId==null || typeId==0){
            Ext.ux.Toast.msg('系统提示', '请选择一个大棚分类！');
            return;
        };
        this.addForm=this.createAddForm();
        var win = new Ext.Window({
            id : 'addOrgWin',
            title : '添加类型',
            width : 300,
            height : 160,
            closeAction : 'close',
            layout : 'fit',
            buttonAlign : 'center',
            resizable : false,
            closable:false,
            modal:true,
            items:[this.addForm],
            buttons:[{
                    text:'确定',
                    icon:'images/icons/ok.png',
                    handler: this.submitAddForm.createDelegate(this)
                },{
                    text : '取消',
                    icon:'images/icons/cancel.png',
                    handler:function(){
                        win.close();
                    }
                }]
        });
        win.show();
    },
    submitAddForm : function(){
        var form=this.addForm.form;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'shed!save',
            scope : this,
            success : function(form, action) {
                Ext.ux.Toast.msg('系统提示',action.result.msg);
                Ext.getCmp('addOrgWin').close();
                this.store.reload();
            },
            failure : function(form, action) {
                Ext.ux.Toast.msg('系统提示', action.result.msg);
            }
        });
    },
    createEditForm:function(){
        var form= new Ext.FormPanel({
            defaultType:'textfield',
            labelAlign: 'right',
            labelWidth:60,
            frame:true,
            items :[{
                    xtype:'hidden',
                    name:'id'
                },{
                    xtype:'hidden',
                    name:'shedTypeId',
                    value:this.store.baseParams['shedTypeId']
                },{
                    width:170,
                    fieldLabel:'大棚名称',
                    name:'name',
                    allowBlank: false,
                    blankText:'大棚名称不为空'
                },{
                    width:170,
                    fieldLabel:'大棚描述',
                    name:'des',
                    allowBlank: false,
                    blankText:'大棚描述不为空'
                },{
                    xtype : 'combo',
                    mode : 'remote',
                    hiddenName : 'crop_id',
                    fieldLabel : '大棚作物',
                    displayField : 'crop_name',
                    valueField : 'crop_id',
                    typeAhead : true,
                    emptyText : '请选择大棚作物...',
                    triggerAction : 'all',
                    store : new Ext.data.Store({
                        proxy : new Ext.data.HttpProxy({
                                    url : 'crop!load'
                                }),
                        reader : new Ext.data.ArrayReader({},[{
                                    name : 'crop_id',
                                    type:'int'
                                },{
                                    name : 'crop_name',
                                    type:'string'
                                }])
                        })
                }]
        });
        return form;
    },
    edit : function(){
        var rows=this.getSelectedRecord();
        if(rows==false || rows.length>1){
            Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
            return;
        };
        this.editForm=this.createEditForm();
        var win = new Ext.Window({
            id : 'editOrgWin',
            title : '修改类型',
            width : 300,
            height : 160,
            closeAction : 'close',
            layout : 'fit',
            buttonAlign : 'center',
            resizable : false,
            closable:false,
            modal:true,
            items:[this.editForm],
            buttons:[{
                    text:'确定',
                    icon:'images/icons/ok.png',
                    handler: this.submitEditForm.createDelegate(this)
                },{
                    text : '取消',
                    icon:'images/icons/cancel.png',
                    handler:function(){
                        win.close();
                    }
                }]
        });
        win.show();
        this.editForm.form.loadRecord(rows[0]);
    },
    submitEditForm : function(){
        var form=this.editForm.form;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'shed!update',
            scope : this,
            success : function(form, action) {
                Ext.ux.Toast.msg('系统提示',action.result.msg);
                Ext.getCmp('editOrgWin').close();
                this.store.reload();
            },
            failure : function(form, action) {
                Ext.ux.Toast.msg('系统提示', action.result.msg);
            }
        });
    },
    deleteOp : function(){
        var ids=this.getSelectedIds();
        if(ids==false){
            Ext.ux.Toast.msg('系统提示','请选择一行或多行操作！');
            return;
        }
        Ext.Msg.confirm('系统提示', '确定要删除所选大棚吗？', function(btn){
            if (btn == 'yes') {
                Ext.Ajax.request({
                    url : 'shed!delete',
                    scope : this,
                    params :{
                        ids : ids
                    },
                    success : function(response,options) {
                        var respText = Ext.util.JSON.decode(response.responseText);
                        Ext.ux.Toast.msg('系统提示',respText.msg);
                        if(respText.success){
                            this.store.reload();
                        }
                    },
                    failure : function(response, options) {
                        Ext.ux.Toast.msg('系统提示','与后台断开连接！');
                    }           
                });
            }
        }, this);
    },
    move : function(){
        var rows=this.getSelectedRecord();
        if(rows==false || rows.length>1){
            Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
            return;
        }
        var id=rows[0].get('id');
        var win = new Ext.Window({
            id:'bookMoveWin',
            title : '选择移动的目录',
            modal : true,
            width : 400,
            height : 100,
            layout : 'form',
            bodyStyle : 'padding:5px',
            closeAction : 'close',
            buttonAlign : 'center',
            resizable : false,
            items : [ {
                    id :'bookMoveContentId',
                    name : 'bookContentId',
                    xtype : 'hidden',
                    value :''
                }, {
                    valId : 'bookMoveContentId',
                    url : 'shedType!list',
                    xtype : 'treecombo'
                }],
            buttons : [{
                        text : '确定',
                        icon : 'images/icons/ok.png',
                        handler: function(){
                            var contentId=Ext.getCmp('bookMoveContentId').getValue();
                            if(contentId==null || contentId==''){
                                Ext.ux.Toast.msg('系统提示','请选择要移动的目录！');
                                return;
                            }
                            Ext.Ajax.request({
                                url : 'shed!move',
                                scope : this,
                                params :{
                                    id : id,
                                    shedTypeId:contentId
                                },
                                success : function(response,options) {
                                    var respText = Ext.util.JSON.decode(response.responseText);
                                    Ext.ux.Toast.msg('系统提示',respText.msg);
                                    Ext.getCmp('bookMoveWin').close();
                                    Ext.getCmp('shedgrid').getStore().reload();
                                },
                                failure : function(response, options) {
                                    Ext.ux.Toast.msg('系统提示','与后台断开连接！');
                                }           
                            });
                        }
                    },{
                        text : '取消',
                        icon : 'images/icons/cancel.png',
                        handler : function() {
                            win.close();
                        }
                    }]
        });
        win.show();
    }
});

Long.ShedPanel = Ext.extend(Ext.Panel,{
	id:'shedpanel',
	title:'大棚管理',
	iconCls:'shed_icon',
	initComponent : function(){
		this.nav = new Long.ShedTypeTree({
			region:'west'
		});
		this.tab= new Long.ShedGrid({
			region:'center'
		});
		Ext.applyIf(this, {
			border:false,
		    layout:'border',
			closable:true,
			items:[this.nav,this.tab]
		});
		Long.ShedPanel.superclass.initComponent.call(this);
	}
});