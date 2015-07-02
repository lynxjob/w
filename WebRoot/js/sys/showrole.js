Ext.namespace('Long');
//授予用户角色的表格
Long.ShowRoleGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	id:'showrolegrid',
	baseUrl:'auth!getUserRoles',
	pageSize : 30,
	layout : 'fit',
	closable : true,
	loadMask : true,
	stripeRows : true,
	viewConfig : {
		forceFit : true
	},
	loadMask : {
		msg : '数据加载中.....'
	},
	closable : true,
	initComponent : function() {
		Ext.getBody().mask('数据加载中.....', 'x-mask-loading');
		//构造一个只能包含checkbox的列
	   	var checkColumn = new Ext.grid.CheckColumn({
		    header: '是否拥有角色?',
		    dataIndex: 'check',
	       	width: 55
	    });
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm, {
				header : 'ID编号',
				dataIndex : 'id',
				hidden:true
			},{
				header : '角色',
				dataIndex : 'name'
			},{
				header : '角色描述',
				dataIndex : 'des',
                width:180
			},{
				header: '是否拥有角色?',
				xtype:'checkcolumn',
		    	dataIndex: 'check',
	       		editor:new Ext.form.TextField()
			}
            ]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			}, {
				name : 'name',
				type : 'string'
			}, {
				name : 'des',
				type : 'string'
			}, {
				name : 'check',
				type : 'bool'        
			}]);
		this.store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : this.baseUrl+ this.params
			}),
			reader : new Ext.data.JsonReader({
				totalProperty : 'rowCount',
				root : 'result'
			}, this.record)
		});
		this.tbar = new Ext.Toolbar([{
				text : '刷新',
				icon : 'images/icons/f5.png',
				handler : this.refresh.createDelegate(this)
			},{
				text : '保存',
				icon : 'images/icons/save.png',
				handler : this.save.createDelegate(this)
			},{
				text : '关闭',
				icon :'images/icons/cancel.png',
				handler : this.closeWin.createDelegate(this)
			}]);
		this.contextmenu = new Ext.menu.Menu({
			items : [{
				text : '刷新',
				icon : 'images/icons/f5.png',
				handler : this.refresh.createDelegate(this)
			},{
				text : '保存',
				icon : 'images/icons/save.png',
				handler : this.save.createDelegate(this)
			},{
				text : '关闭',
				icon :'images/icons/cancel.png',
				handler : this.closeWin.createDelegate(this)
			}]
		});
		this.bbar = new Ext.PagingToolbar({
			pageSize : this.pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg :'显示第 {0} 条到 {1} 条，共 {2} 条',
			emptyMsg : '没有数据记录'
		});
		this.store.load({
			params : {
				start : 0,
				limit : this.pageSize
			},
			callback : function(rs) {
				Ext.getBody().unmask();
				if (!rs || rs.length < 1) {
					Ext.ux.Toast.msg('系统提示','没有找到符合条件的记录！');
				}
			}
		});
		this.on('contextmenu', function(e) {
			e.preventDefault();
			this.contextmenu.showAt(e.getXY());
		});
		Long.ShowRoleGrid.superclass.initComponent.call(this);
	},
	refresh : function() {
		var m = this.store.modified.slice(0);
		if(m.length>0){
			Ext.Msg.confirm('系统提示', '是否要保存修改和添加的信息？',function(btn) {
				if (btn == 'yes') {
					return;
				}
				else{
					Ext.each(m, function(item) {
						if (item.dirty) {
							item.commit();
						}
					});
					this.store.removeAll();
					this.store.reload();
				}
			}, this);
		}
		else{
			this.store.removeAll();
			this.store.reload();
		}
	},
	/**
	 * 用户拥有角色保存
	 */
	save : function(){
		var m = this.store.modified.slice(0);
		if (m.length == 0) {
			Ext.ux.Toast.msg('系统提示', '数据未有变化！');
			return;
		}
		var jsonArray = [];
		Ext.each(m, function(item) {
			if (item.dirty) {
				item.commit();
			}
			jsonArray.push(item.data);
		});
		Ext.Ajax.request({
			url : 'auth!saveUserRoles',
			scope : this,
			params :{
				jsonString : Ext.encode(jsonArray),
				userId : this.userId
			},
			success : function(response,options) {
				var respText = Ext.util.JSON.decode(response.responseText);
				Ext.ux.Toast.msg('系统提示',respText.msg);
				this.refresh();
			},
			failure : function(response, options) {
				Ext.ux.Toast.msg('系统提示',respText.errors.msg);
			}			
		});
	},
	closeWin : function(){
		var m = this.store.modified.slice(0);
		if(m.length>0){
			Ext.Msg.confirm('系统提示', '是否要保存修改和添加的信息？',function(btn) {
				if (btn == 'yes') {
					return;
				}
				else{
					Ext.getCmp('giveRoleWin').close();
				}
			}, this);
		}
		else{
			Ext.getCmp('giveRoleWin').close();
		}
	}
});