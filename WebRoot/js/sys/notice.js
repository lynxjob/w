Ext.namespace('Long');
//公告表格
Long.NoticeGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'noticegrid',
	title:'系统公告',
	iconCls:'notice_icon',
	baseUrl:'notice!list',
	initComponent : function() {
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,{
				header:'标题',
				dataIndex:'title'
			},{
				header:'查看公告详情',
				dataIndex:'id',
				renderer:function(value){	
					return '<a href="#" onclick="Long.viewNotice('+value+')">查看</a>';
				}
			},{
				header:'发布日期',
				dataIndex:'cdate'
			},{
				header:'发布者',
				dataIndex:'creatorName'
			},{
				header:'公告级别',
				dataIndex:'level',
				renderer:function(value){
					if(value==1){
						return '一般';
					}
					else if(value==2){
						return '重要';
					}
					else if(value==3){
						return '紧急';
					}
					else if(value==4){
						return '置顶';
					}
					else {
						return "<span style='color:red;font-weight:bold;'>错误级别</span>";
					}
				}
			}]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name : 'title',
				type : 'string'
			},{
				name : 'content',
				type : 'string'
			},{
				name : 'cdate',
				type : 'string'
			},{
				name : 'creatorName',
				type : 'string'
			},{
				name : 'level',
				type : 'int'
			}]);
		this.tbar = new Ext.Toolbar([ {
				text : '刷新',
				icon : 'images/icons/f5.png',
				handler: this.refresh.createDelegate(this)
			},{
				text:'添加',
				icon : 'images/icons/notice+.png',
				handler: this.add.createDelegate(this),
				hidden:!Long.ngAdd
			},{
				text:'修改',
				icon : 'images/icons/notice_.png',
				handler: this.edit.createDelegate(this),
				hidden:!Long.ngEdit
			},{
				text : '删除',
				icon : 'images/icons/notice-.png',
				handler: this.deleteOp.createDelegate(this),
				hidden:!Long.ngDelete
			},{
				text:'查看排序',
				icon : 'images/icons/sort.png',
				menu : new Ext.menu.Menu({
					items : [{
						text : '日期排序',
						icon : 'images/icons/date.png',
						handler: this.sortByDate.createDelegate(this)
					}, {
						text : '等级排序',
						icon : 'images/icons/level.png',
						handler: this.sortByLevel.createDelegate(this)
					}]
				})
			}]);
		if(Long.ngEdit){
			this.on('rowdblclick',this.edit,this);
		}
		Long.NoticeGrid.superclass.initComponent.call(this);
	},
	add : function(){
		this.fp = new Long.NoticeForm();
		var win = new Ext.Window({
			id:'addNoticeWin',
			title : '添加公告',
			width : 650,
			height : 450,
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
		if (form.findField('content').getValue().trim()=='<br>') {
			Ext.ux.Toast.msg('系统提示', '请输入公告的内容！');
			return;
		}
		form.submit({
			waitTitle : '请稍后',
			waitMsg : '正在提交信息···',
			url : 'notice!save',
			scope : this,
			success : function(form, action) {
				Ext.ux.Toast.msg('系统提示',action.result.msg);
				Ext.getCmp('addNoticeWin').close();
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
		this.fp = new Long.NoticeForm();
		var win = new Ext.Window({
			id:'eidtNoticeWin',
			title : '修改公告',
			width : 650,
			height : 450,
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
		if (form.findField('content').getValue().trim()=='<br>') {
			Ext.ux.Toast.msg('系统提示', '请输入公告的内容！');
			return;
		}
		form.submit({
			waitTitle : '请稍后',
			waitMsg : '正在提交信息···',
			scope : this,
			url : 'notice!update',
			success : function(form, action) {
				Ext.ux.Toast.msg('系统提示',action.result.msg);
				Ext.getCmp('eidtNoticeWin').close();
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
					url : 'notice!delete',
					scope : this,
					params :{
						ids : ids
					},
					success : function(response,options) {
						var respText = Ext.util.JSON.decode(response.responseText);
						Ext.ux.Toast.msg('系统提示',respText.msg);
						if(respText.success){
							this.refresh();
						}
					},
					failure : function(response, options) {
						Ext.ux.Toast.msg('系统提示',respText.errors.msg);
					}			
				});
			}
		}, this);
	},
	sortByDate : function(){
		this.getStore().baseParams = {
			'orderBy': 'order by id desc'
			};
		this.store.reload({
			params : {
				start:0,
				limit:this.pageSize
			}
		});
	},
	sortByLevel : function(){
		this.getStore().baseParams = {
			'orderBy': 'order by level desc'
			};
		this.store.reload({
			params : {
				start:0,
				limit:this.pageSize
			}
		});
	}
});

/**
 * 查看公告详情
 */
Long.viewNotice=function(id){
	Ext.Ajax.request({
		url : 'notice!get',
		params :{
			id : id
		},
		success : function(response,options) {
			var respText = Ext.util.JSON.decode(response.responseText);
			var notice=respText.notice;
			var tabs=Ext.getCmp('centertabs');
			var tab = tabs.add({
				id:'notice' + id ,
				title:'公告详情',
				iconCls:'notice_icon',
				closable:true,
				autoScroll:true,
				html:'<center><br/><p><font size="5">' + notice.title + '</font></p><br/>发布者：'
						+ notice.creatorName + '&emsp;&emsp;&emsp;&emsp;发布时间：' + notice.cdate
						+ '</center><br/><HR>'
						+ '<br/>' + notice.content
				});
			tabs.setActiveTab(tab);
		},
		failure : function(response, options) {
			Ext.ux.Toast.msg('系统提示','无法访问后台！');
		}			
	});
};