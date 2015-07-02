Ext.namespace('Long');
Long.GoodsGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'goodsgrid',
	title:'退幼苗账单信息管理',
	baseUrl:'return_goods!list',
	pageSize : 30,
	initComponent : function() {
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,{
				header:'退幼苗账单编号',
				dataIndex:'id'
			},{
				header:'供应商全称',
				dataIndex:'fullName'
			},{
				header:'幼苗名称',
				dataIndex:'seedingName'
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
                header:'退幼苗日期',
                dataIndex:'date'
            },{
                header:'联系人',
                dataIndex:'linkmanName'
            },{
                header:'经手人',
                dataIndex:'handlerName'
            },{
                header:'原因描述',
                dataIndex:'des'
            }]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name:'fullName',
				type:'string'
			},{
				name:'seedingName',
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
            },{
            	name:'des',
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
		Long.GoodsGrid.superclass.initComponent.call(this);
	},
    createAddForm:function(){
        var form= new Ext.form.FormPanel({
        defaultType : 'textfield',
        layout:'form',
		labelAlign : 'right',
		labelWidth : 80,
		width : 650,
		frame : true,
		//bodyStyle : 'padding:5px',
        items :[{
                xtype:'hidden',
                name:'id'
            },{
                	xtype:'combo',
                    width:300,
                    mode : 'remote',
                    fieldLabel:'供应商全称',
                    name:'fullName',
                    typeAhead : true,
            		emptyText : '请选择供应商...',
            		allowBlank : false,
            		blankText : '请选择供应商!',
            		displayField : 'provider_fullName',  
                    valueField : 'provider_id',
            		editable : false,
            		triggerAction : 'all',
            		store:new Ext.data.Store({
   	 						proxy : new Ext.data.HttpProxy({
		                            url : 'provider!load'
		                        }),
   	  						reader : new Ext.data.ArrayReader({},[
   	  							{
		                            name : 'provider_id',
                                    type:'int'
		                        },
		                        {
		                            name : 'provider_fullName',
                                    type:'string'
		                        }]),
		                     autoLoad: true
   	  				}),
   	  				
                },{
                	width:300,
                	fieldLabel:'所退幼苗名称',
                	name:'seedingName',
                	allowBlank: false,
                	blankText:'所退幼苗名称不为空'
                },{
                	width:300,
                 	fieldLabel:'单价',
                	name:'price'
            },{
            	width:300,
                fieldLabel:'数量',
                name:'amount'
            },{
            	width:300,
                xtype:'datefield',
                fieldLabel:'退幼苗日期',
                name:'date'
            },{
            	width:300,
                fieldLabel:'联系人',
                name:'linkmanName'
            },{
            	width:300,
                fieldLabel:'经手人',
                name:'handlerName'
            },{
            	width:300,
        		xtype : 'htmleditor',
        		fieldLabel : '原因描述',
        		height : 120,
        		colspan : 3,
        		name : 'des',
        		anchor:'95%'
            }]
        });
        return form;
    },
    add : function(){   
        this.addForm=this.createAddForm();
        var win = new Ext.Window({
            id : 'addOrgWin',
            title : '添加退幼苗账单',
            width : 450,
            height:400,
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
        var return_goodsId = 0;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'return_goods!save',
            params:{
                return_goodsId : return_goodsId
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
            layout:'form',
			labelAlign : 'right',
			labelWidth : 80,
			width : 650,
			frame : true,
            items :[{
                    xtype:'hidden',
                    name:'id'
                },{
                	xtype:'combo',
                    width:300,
                    mode : 'remote',
                    fieldLabel:'供应商全称',
                    name:'fullName',
                    typeAhead : true,
            		emptyText : '请选择供应商...',
            		allowBlank : false,
            		blankText : '请选择供应商!',
            		displayField : 'provider_fullName',  
                    valueField : 'provider_id',
            		editable : false,
            		triggerAction : 'all',
            		store:new Ext.data.Store({
   	 						proxy : new Ext.data.HttpProxy({
		                            url : 'provider!load'
		                        }),
   	  						reader : new Ext.data.ArrayReader({},[
   	  							{
		                            name : 'provider_id',
                                    type:'int'
		                        },
		                        {
		                            name : 'provider_fullName',
                                    type:'string'
		                        }]),
		                     autoLoad: true
   	  				}),
   	  				
                },{
                    width:300,
                    fieldLabel:'所退幼苗名称',
                    name:'seedingName',
                     allowBlank: false,
                    blankText:'所退幼苗名称不为空'
                },{
                    width:300,
                    fieldLabel:'单价',
                    name:'price'
                },{
                    width:300,
                    fieldLabel:'数量',
                    name:'amount'
                },{
                    width:300,
                    xtype:'datefield',
                    fieldLabel:'退幼苗日期',
                    name:'date'
                },{
                    width:300,
                    fieldLabel:'联系人',
                    name:'linkmanName'
                },{
                    width:300,
                    fieldLabel:'经手人',
                    name:'handlerName'
                },{
                	 width:300,
                	 name : 'des',
            		xtype : 'htmleditor',
            		fieldLabel : '原因描述',
            		height : 120,
            		colspan : 3
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
            title : '修改退幼苗账单信息',
            width : 450,
            height : 400,
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
        var return_goodsId = 0;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'return_goods!update',
            params:{
              return_goodsId :return_goodsId  
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
        Ext.Msg.confirm('系统提示', '确定要删除所选退幼苗账单吗？', function(btn){
            if (btn == 'yes') {
                Ext.Ajax.request({
                    url : 'return_goods!delete',
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