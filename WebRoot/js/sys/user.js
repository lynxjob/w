Ext.namespace('Long');
//用户表格
Long.UserGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'usergrid',
	title:'所有用户',
	baseUrl:'user!list',
	initComponent : function() {
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm, {
				header : 'ID编号',
				dataIndex : 'id',
				hidden:true
			},{
				header : '姓名',
				dataIndex : 'name'
			},{
				header : '年龄',
				dataIndex : 'age'
			},{
				header:'联系电话',
				dataIndex:'tel'
			},{
				header:'状态',
				dataIndex:'state',
				renderer:function(value){
					if(value==1){
						return '启用';
					}
					else if(value==0){
						return '禁用';
					}
                    alert(value);
				}
			},{
				header:'创建者',
				dataIndex:'creatorName',
                renderer:function(value){
                    if(value == 'null'){
                        return '系统初始值';
                    }else{
                        return value;
                    }
                }
			},{
                header:'创建时间',
                dataIndex:'cdate'
            }]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name : 'name',
				type : 'string'
			},{
				name:'password',
				type:'string'
			},{
				name : 'age',
				type : 'int'
			},{
				name : 'tel',
				type : 'string'
			},{
				name:'state',
				type:'int'
			},{
				name:'creatorName',
				type:'string'
			},{
                name:'cdate',
                type:'string'
            }]);
		this.tbar = new Ext.Toolbar([ {
				text : '刷新',
				icon : 'images/icons/f5.png',
				handler: this.refresh.createDelegate(this)
			},{
				text:'添加',
				icon : 'images/icons/user+.png',
				handler: this.add.createDelegate(this),
                hidden:!Long.ugAdd
			},{
				text:'修改',
				icon : 'images/icons/user_.png',
				handler: this.edit.createDelegate(this),
                hidden:!Long.ugEdit
			},{
				text : '删除',
				icon : 'images/icons/user-.png',
				handler: this.deleteOp.createDelegate(this),
                hidden:!Long.ugDelete
			}/*,{
				text : '用户角色',
				icon : 'images/icons/role.png',
				handler: this.giveRole.createDelegate(this),
                hidden:!Long.ugRole
			}*/,{
				text : '额外权限',
				icon : 'images/icons/key.png',
				handler: this.giveAuthority.createDelegate(this),
                hidden:!Long.ugPower
			}]);
		this.on('rowdblclick',this.edit,this);
		Long.UserGrid.superclass.initComponent.call(this);
	},
	add : function(){
		var shedId=this.store.baseParams['shedId'];
        var shedIds=Ext.getCmp('usertree').getChecked();
        if(shedIds==null || shedIds.length == 0){
            Ext.ux.Toast.msg('系统提示', '请选择该用户管理的大棚!');
            return;
        }
        var data = [];
        Ext.each(shedIds,function(node){
            data.push(node.id);
        });
        this.store.baseParams = {'shedIds':data};
		this.fp = new Long.UserForm();
		var win = new Ext.Window({
			id:'addUserWin',
			title : '添加用户',
			width : 300,
			height : 210,
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
			url : 'user!save',
			scope : this,
			params : {
				shedId : this.store.baseParams['shedId'],
                shedIds : this.store.baseParams['shedIds']
			},
			success : function(form, action) {
				Ext.ux.Toast.msg('系统提示',action.result.msg);
				Ext.getCmp('addUserWin').close();
				this.refresh();
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('系统提示',action.result.msg);
			}
		});
	},
	edit : function(){
        var shedIds=Ext.getCmp('usertree').getChecked();
        if(shedIds=!null && shedIds.length > 0){
            Ext.ux.Toast.msg('系统提示', '只能修改用户的基本信息,不能修改该用户管理的大棚!');
            return;
        }
		var rows=this.getSelectedRecord();
		if(rows==false || rows.length>1){
			Ext.ux.Toast.msg('系统提示', '请选择一行进行操作!');
			return;
		}
		this.fp = new Long.UserForm();
//		this.fp.form.loadRecord(rows[0]);
		var win = new Ext.Window({
			id:'eidtUserWin',
			title : '修改用户',
			width : 300,
			height : 210,
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
			url : 'user!update',
			success : function(form, action) {
				Ext.ux.Toast.msg('系统提示',action.result.msg);
				Ext.getCmp('eidtUserWin').close();
				this.refresh();
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
		Ext.Msg.confirm('系统提示', '确定要删除所选行吗？', function(btn){
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : 'user!delete',
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
	giveRole : function(){
		var rows=this.getSelectedRecord();
		if(rows==false || rows.length>1){
			Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
			return;
		}
		var userId=rows[0].get('id');
		var userName=rows[0].get('name');
        console.log("id:"+userId);
        console.log("name:"+userName);
		//用户授权，从后台读取，往后台传值都需要userId
		var tempGrid=new Long.ShowRoleGrid({
			params : '?userId=' + userId,
			userId : userId
		});
        console.log("tempGrid init!");
		var win = new Ext.Window({
			id:'giveRoleWin',
			title: '授予用户 ['+userName+'] 角色',
			layout: 'fit',
	       	width: 560,
	        height: 350,
	        modal:true,
	        maximizable : false,
			resizable : false,
	        closable: false,
	        items: [tempGrid]
	    });
	    win.show();
	},
	/**
	 * 授予用户额外权限
	 */
	giveAuthority : function(){
		var rows=this.getSelectedRecord();
		if(rows==false || rows.length>1){
			Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
			return;
		}
		var userId=rows[0].get('id');
		var userName=rows[0].get('name');
		var tempTree=new Long.ShowUserPowerTree({
			params : '?userId=' + userId,
			userId : userId
		});
		var win = new Ext.Window({
			id:'giveUserPowerWin',
			title: '授予用户 ['+userName+'] 额外权限',
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

Long.UserPanel = Ext.extend(Ext.Panel,{
	id:'userpanel',
	title:'用户账户',
	iconCls:'user_icon',
	initComponent : function(){
		this.nav = new Long.UserTree({
			region:'west'
		});
		this.tab= new Long.UserGrid({
			region:'center'
		});
		Ext.applyIf(this, {
			border:false,
		    layout:'border',
			closable:true,
			items:[this.nav,this.tab]
		});
        console.log('初始化组件：userpanel');
		Long.UserPanel.superclass.initComponent.call(this);
	}
});