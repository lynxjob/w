Ext.namespace('Long');
//数据备份
Long.BackupGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'backupgrid',
	title:'数据备份',
	iconCls:'db_icon',
	baseUrl:'backup!list',
	pageSize : 100,
	initComponent : function() {
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,{
				header:'文件名字',
				dataIndex:'name'
			},{
				header:'文件大小',
				dataIndex:'size'
			},{
				header:'备份日期',
				dataIndex:'cdate'
			}]);
		this.record = Ext.data.Record.create([{
				name : 'name',
				type : 'string'
			},{
				name:'size',
				type:'string'
			},{
				name:'cdate',
				type:'string'
			}]);
		this.tbar = new Ext.Toolbar([{
				text : '刷新',
				icon : 'images/icons/f5.png',
				handler: this.refresh.createDelegate(this)
			},{
				text : '备份',
				icon : 'images/icons/back.png',
				handler: this.backup.createDelegate(this),
                hidden:!Long.bgBack
			},{
				text : '还原',
				icon : 'images/icons/restore.png',
				handler: this.restore.createDelegate(this),
                hidden:!Long.bgReStore
			},{
				text : '下载',
				icon : 'images/icons/download.png',
				handler: this.download.createDelegate(this),
                hidden:!Long.bgDownLoad
			},{
				text : '删除',
				icon : 'images/icons/delete.png',
				handler: this.deleteOp.createDelegate(this),
                hidden:!Long.bgDelete
			}]);
        console.log('初始化组件：backgrid');
		Long.BackupGrid.superclass.initComponent.call(this);
	},
	backup : function(){
		Ext.getBody().mask('正在备份数据,请稍候.....', 'x-mask-loading');
		Ext.Ajax.request({
			url : 'backup!backup',
			success : function(response,options) {
				Ext.getBody().unmask();
				var respText = Ext.util.JSON.decode(response.responseText);
				Ext.ux.Toast.msg('系统提示',respText.msg);
				if(respText.success){
					this.refresh();
				}
			},
			failure : function(response,options) {
				Ext.ux.Toast.msg('系统提示','与后台断开连接！');
			},
			scope : this
		});
	},
	restore : function(){
		var rows = this.getSelectedRecord();
		if(rows==false || rows.length>1){
			Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
			return;
		}
		Ext.Msg.confirm('系统提示', '确定要还原所选行的备份数据吗？', function(btn) {
			if (btn == 'yes') {
				var name = rows[0].get('name');
				Ext.getBody().mask('正在还原系统数据,请稍候.....', 'x-mask-loading');
				Ext.Ajax.request({
					url : 'backup!restore',
					params : {
						name : name
					},
					success : function(response,options) {
						Ext.getBody().unmask();
						var respText = Ext.util.JSON.decode(response.responseText);
						Ext.ux.Toast.msg('系统提示',respText.msg);
						if(respText.success){
							this.refresh();
						}
					},
					failure : function(response,options) {
						Ext.ux.Toast.msg('系统提示','与后台断开连接！');
					},
					scope : this
				});
			}
		}, this);
	},
	download : function(){
		var rows = this.getSelectedRecord();
		if(rows==false || rows.length>1){
			Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
			return;
		}
		var name = rows[0].get('name');
		var exportForm = new Ext.form.FormPanel({
			fileUpload : true,
			hidden : true,
			items : {}
		});
		var fe = document.createElement('div');
		document.body.appendChild(fe);
		exportForm.render(fe);
		exportForm.form.submit({
			url : 'backup!download',
			params : Ext.apply({
					name: name
				})
		});
	},
	deleteOp : function(){
		var rows = this.getSelectedRecord();
		if(rows==false || rows.length>1){
			Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
			return;
		}
		Ext.Msg.confirm('系统提示', '确定要删除所选行的备份数据吗？', function(btn) {
			if (btn == 'yes') {
				var name = rows[0].get('name');
				Ext.Ajax.request({
					url : 'backup!delete',
					params : {
						name : name
					},
					success : function(response,options) {
						var respText = Ext.util.JSON.decode(response.responseText);
						Ext.ux.Toast.msg('系统提示',respText.msg);
						if(respText.success){
							this.refresh();
						}
					},
					failure : function(response,options) {
						Ext.ux.Toast.msg('系统提示','与后台断开连接！');
					},
					scope : this
				});
			}
		}, this);
	}
});