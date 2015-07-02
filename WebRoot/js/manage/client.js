Ext.namespace('Long');
Long.ClientGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'clientgrid',
	title:'客户信息管理',
	baseUrl:'client!list',
	pageSize : 30,
	initComponent : function() {
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,{
				header:'客户编号',
				dataIndex:'id'
			},{
				header:'公司全称',
				dataIndex:'fullName'
			},{
				header:'公司简称',
				dataIndex:'shortName'
			},{
                header:'公司地址',
                dataIndex:'address'
            },{
               header:'公司电话',
               dataIndex:'telephone'
            },{
                header:'邮政编码',
                dataIndex:'postcode'
            },{
                header:'E-mail',
                dataIndex:'e_mail'
            },{
                header:'联系人',
                dataIndex:'linkmanName'
            },{
                header:'联系人电话',
                dataIndex:'linkmanPhone'
            }]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name:'fullName',
				type:'string'
			},{
				name:'shortName',
				type:'string'
			},{
                name:'address',
                type:'string'
            },{
                name:'telephone',
                type:'string'
            },{
                name:'postcode',
                type:'string'
            },{
                name:'e_mail',
                type:'string'
            },{
                name:'linkmanName',
                type:'string'
            },{
                name:'linkmanPhone',
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
            }
        ]);
		Long.ClientGrid.superclass.initComponent.call(this);
	},
    createAddForm:function(){
        var form= new Ext.FormPanel({
            defaultType:'textfield',
            labelAlign: 'right',
            labelWidth:80,
            frame:true,
            items :[{
                    xtype:'hidden',
                    name:'id'
                },{
                    width:170,
                    fieldLabel:'公司全称',
                    name:'fullName',
                    allowBlank: false,
                    blankText:'公司全称不为空'
                },{
                    width:170,
                    fieldLabel:'公司简称',
                    name:'shortName'
                },{
                    width:170,
                    fieldLabel:'公司地址',
                    name:'address'
                },{
                    width:170,
                    fieldLabel:'公司电话',
                    name:'telephone'
                },{
                    width:170,
                    fieldLabel:'邮政编码',
                    name:'postcode'
                },{
                    width:170,
                    fieldLabel:'E-mail',
                    name:'e_mail'
                },{
                    width:170,
                    fieldLabel:'联系人',
                    name:'linkmanName'
                },{
                    width:170,
                    fieldLabel:'联系人电话',
                    name:'linkmanPhone'
                }]
        });
        return form;
    },
    add : function(){   
        this.addForm=this.createAddForm();
        var win = new Ext.Window({
            id : 'addOrgWin',
            title : '添加客户',
            width : 300,
            height : 290,
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
        var clientId = 0;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'client!save',
            params:{
                clientId : clientId
            },
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
            labelWidth:80,
            frame:true,
            items :[{
                    xtype:'hidden',
                    name:'id'
                },{
                    width:170,
                    fieldLabel:'公司全称',
                    name:'fullName',
                    allowBlank: false,
                    blankText:'公司全称不为空'
                },{
                    width:170,
                    fieldLabel:'公司简称',
                    name:'shortName'
                },{
                    width:170,
                    fieldLabel:'公司地址',
                    name:'address'
                },{
                    width:170,
                    fieldLabel:'公司电话',
                    name:'telephone'
                },{
                    width:170,
                    fieldLabel:'邮政编码',
                    name:'postcode'
                },{
                    width:170,
                    fieldLabel:'E-mail',
                    name:'e_mail'
                },{
                    width:170,
                    fieldLabel:'联系人',
                    name:'linkmanName'
                },{
                    width:170,
                    fieldLabel:'联系人电话',
                    name:'linkmanPhone'
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
            title : '修改客户信息',
            width : 300,
            height : 290,
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
        var clientId = 0;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'client!update',
            params:{
              clientId : clientId  
            },
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
        Ext.Msg.confirm('系统提示', '确定要删除所选客户吗？', function(btn){
            if (btn == 'yes') {
                Ext.Ajax.request({
                    url : 'client!delete',
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
                        Ext.ux.Toast.msg('系统提示','后台出现错误!');
                    }           
                });
            }
        }, this);
    }
   
  
});