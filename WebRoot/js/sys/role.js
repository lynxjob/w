Ext.namespace('Long');
//角色表格
Long.RoleGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'rolegrid',
	title:'角色信息',
	iconCls:'role_icon',
	baseUrl:'role!list',
	initComponent : function() {
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm, {
				header : 'ID编号',
				dataIndex : 'id',
				hidden:true
			},{
				header : '名称',
				dataIndex : 'name'
			},{
				header : '描述',
				dataIndex : 'des'
			},{
				header :'创建日期',
				dataIndex :'cdate'
			},{
				header:'创建人',
				dataIndex:'creatorName'
			}]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name : 'name',
				type : 'string'
			},{
				name : 'des',
				type : 'string'
			},{
				name : 'cdate',
				type : 'string'
			},{
				name : 'creatorName',
				type : 'string'
			}]);
		this.tbar = new Ext.Toolbar([ {
			text : '刷新',
			icon : 'images/icons/f5.png',
			handler: this.refresh.createDelegate(this)
		},{
			text:'添加',
			icon : 'images/icons/role+.png',
			handler: this.add.createDelegate(this),
            hidden:!Long.rgAdd
		},{
			text:'修改',
			icon : 'images/icons/role_.png',
			handler: this.edit.createDelegate(this),
            hidden:!Long.rgEdit
		},{
			text : '删除',
			icon : 'images/icons/role-.png',
			handler: this.deleteOp.createDelegate(this),
            hidden:!Long.rgDelete
		},{
			text : '角色授权',
			icon : 'images/icons/key.png',
			handler: this.giveAuthority.createDelegate(this),
            hidden:!Long.rgRole
		}]);
		this.on('rowdblclick',this.edit,this);
        console.log('初始化组件：rolegrid');
		Long.RoleGrid.superclass.initComponent.call(this);
	},
	add : function(){
		this.fp = new Long.RoleForm();
		var win = new Ext.Window({
			id:'addRoleWin',
			title : '添加角色',
			width : 270,
			height : 140,
			closeAction : 'close',
			layout : 'fit',
			buttonAlign : 'center',
			resizable : false,
			closable : false,
			modal : true,
			items : [this.fp],
			buttons : [{
						text : '确定',
						icon : 'images/icons/ok.png',
						handler : this.submitAddForm.createDelegate(this)
					}, {
						text : '取消',
						icon : 'images/icons/cancel.png',
						handler : function() {
							win.close();
						}
					}]
		});
		win.show();
	},
	submitAddForm : function(){
		var form=this.fp.form;
		if(form.isValid()==false){
			return;
		}
		form.submit({
			waitTitle : '请稍后',
			waitMsg : '正在提交信息···',
			url : 'role!save',
			scope : this,
			success : function(form, action) {
				Ext.ux.Toast.msg('系统提示',action.result.msg);
				Ext.getCmp('addRoleWin').close();
				this.refresh();
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('系统提示', '无法访问后台！');
			}
		});
	},
	edit : function(){
		var rows=this.getSelectedRecord();
		if(rows==false || rows.length>1){
			Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
			return;
		}
		this.fp = new Long.RoleForm();
		var win = new Ext.Window({
			id:'editRoleWin',
			title : '修改角色',
			width : 270,
			height : 140,
			closeAction : 'close',
			layout : 'fit',
			buttonAlign : 'center',
			resizable : false,
			closable : false,
			modal : true,
			items : [this.fp],
			buttons : [{
						text : '确定',
						icon : 'images/icons/ok.png',
						handler : this.submitEditForm.createDelegate(this)
					},{
						text : '取消',
						icon : 'images/icons/cancel.png',
						handler : function() {
							win.close();
						}
					}]
		});
		win.show();
        //先显示窗体再加载数据
        this.fp.form.loadRecord(rows[0]);
	},
	submitEditForm : function(){
		var form=this.fp.form;
		if(form.isValid()==false){
			return;
		}
		form.submit({
			waitTitle : '请稍后',
			waitMsg : '正在提交信息···',
			scope : this,
			url : 'role!update',
			success : function(form, action) {
				Ext.ux.Toast.msg('系统提示',action.result.msg);
				Ext.getCmp('editRoleWin').close();
				this.refresh();
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('系统提示', '无法访问后台！');
			}
		});
	},
	deleteOp : function(){
		var ids=this.getSelectedIds();
		if(ids==false){
			Ext.ux.Toast.msg('系统提示','请选择一行或多行操作！');
			return;
		}
		Ext.Msg.confirm('系统提示', '确定要删除所选行吗？', function(btn){
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : 'role!delete',
					scope : this,
					params :{
						ids : ids
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
			}
		}, this);
	},
	/**
	 * 授予用户角色
	 */
	giveAuthority : function(){
		var rows=this.getSelectedRecord();
		if(rows==false || rows.length>1){
			Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
			return;
		}
		var roleId=rows[0].get('id');
		var roleName=rows[0].get('name');
		var tempTree=new Long.ShowRolePowerTree({
			params : '?roleId=' + roleId,
			roleId : roleId
		});
		var win = new Ext.Window({
			id:'giveRolePowerWin',
			title: '授予角色 ['+roleName+'] 权限',
			layout: 'fit',
			width: 300,
	        height: 400,
	        modal:true,
	        maximizable : false,
			resizable : false,
	        closable: false,
	        items: [tempTree]
	    });
	    win.show();
	}
});