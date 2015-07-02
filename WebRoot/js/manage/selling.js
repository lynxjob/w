Ext.namespace('Long');
Long.SellingGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'sellinggrid',
	title:'销售信息管理',
	baseUrl:'sell!list',
	pageSize : 30,
	initComponent : function() {
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,{
				header:'销售编号',
				dataIndex:'id'
			},{
				header:'客户全称',
				dataIndex:'fullName'
			},{
				header:'销售作物名称',
				dataIndex:'goodsName'
			},{
                header:'单价',
                dataIndex:'price'
            },{
               header:'数量',
               dataIndex:'amount'
            },{
                header:'总价',
                dataIndex:'total'
            },{
                header:'销售日期',
                dataIndex:'date'
            },{
                header:'联系人',
                dataIndex:'linkmanName'
            },{
                header:'经手人',
                dataIndex:'handlerName'
            }]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name:'fullName',
				type:'string'
			},{
				name:'goodsName',
				type:'string'
			},{
                name:'price',
                type:'string'
            },{
                name:'amount',
                type:'string'
            },{
                name:'total',
                type:'string'
            },{
                name:'date',
                type:'string'
            },{
                name:'linkmanName',
                type:'string'
            },{
                name:'handlerName',
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
		Long.SellingGrid.superclass.initComponent.call(this);
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
                	xtype:'combo',
                    width:170,
                    mode : 'remote',
                    fieldLabel:'客户全称',
                    name:'fullName',
                    typeAhead : true,
            		emptyText : '请选择客户...',
            		allowBlank : false,
            		blankText : '请选择客户!',
            		displayField : 'client_fullName',  
                    valueField : 'client_id',
            		editable : false,
            		triggerAction : 'all',
            		store:new Ext.data.Store({
   	 						proxy : new Ext.data.HttpProxy({
		                            url : 'client!load'
		                        }),
   	  						reader : new Ext.data.ArrayReader({},[
   	  							{
		                            name : 'client_id',
                                    type:'int'
		                        },
		                        {
		                            name : 'client_fullName',
                                    type:'string'
		                        }]),
		                     autoLoad: true
   	  				}),
   	  				
                },{
                    width:170,
                    fieldLabel:'销售作物名称',
                    name:'goodsName',
                     allowBlank: false,
                    blankText:'销售作物名称不为空'
                },{
                    width:170,
                    fieldLabel:'单价',
                    name:'price'
                },{
                    width:170,
                    fieldLabel:'数量',
                    name:'amount'
                },{
                	xtype:'datefield',
                    width:170,
                    fieldLabel:'销售日期',
                    name:'date'
                },{
                    width:170,
                    fieldLabel:'联系人',
                    name:'linkmanName'
                },{
                    width:170,
                    fieldLabel:'经手人',
                    name:'handlerName'
                }]
        });
        return form;
    },
    add : function(){   
        this.addForm=this.createAddForm();
        var win = new Ext.Window({
            id : 'addOrgWin',
            title : '添加销售账单',
            width : 300,
            height : 260,
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
        var sellId = 0;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'sell!save',
            params:{
                sellId : sellId
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
                	xtype:'combo',
                    width:170,
                    mode : 'remote',
                    fieldLabel:'客户全称',
                    name:'fullName',
                    typeAhead : true,
            		emptyText : '请选择客户...',
            		allowBlank : false,
            		blankText : '请选择客户!',
            		displayField : 'client_fullName',  
                    valueField : 'client_id',
            		editable : false,
            		triggerAction : 'all',
            		store:new Ext.data.Store({
   	 						proxy : new Ext.data.HttpProxy({
		                            url : 'client!load'
		                        }),
   	  						reader : new Ext.data.ArrayReader({},[
   	  							{
		                            name : 'client_id',
                                    type:'int'
		                        },
		                        {
		                            name : 'client_fullName',
                                    type:'string'
		                        }]),
		                     autoLoad: true
   	  				}),
   	  				
                },{
                    width:170,
                    fieldLabel:'销售作物名称',
                    name:'goodsName',
                     allowBlank: false,
                    blankText:'销售作物名称不为空'
                },{
                    width:170,
                    fieldLabel:'单价',
                    name:'price'
                },{
                    width:170,
                    fieldLabel:'数量',
                    name:'amount'
                },{
                	xtype:'datefield',
                    width:170,
                    fieldLabel:'销售日期',
                    name:'date'
                },{
                    width:170,
                    fieldLabel:'联系人',
                    name:'linkmanName'
                },{
                    width:170,
                    fieldLabel:'经手人',
                    name:'handlerName'
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
            title : '修改销售账单信息',
            width : 300,
            height : 260,
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
        var sellId = 0;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'sell!update',
            params:{
              sellId :sellId  
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
        Ext.Msg.confirm('系统提示', '确定要删除所选销售账单吗？', function(btn){
            if (btn == 'yes') {
                Ext.Ajax.request({
                    url : 'sell!delete',
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