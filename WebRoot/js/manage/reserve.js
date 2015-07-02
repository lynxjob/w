Ext.namespace('Long');
Long.ReserveGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'reservegrid',
	title:'库存信息管理',
	baseUrl:'reserve!list',
	pageSize : 30,
	initComponent : function() {
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.grid.ColumnModel([this.sm,{
				header:'作物编号',
				dataIndex:'id'
			},{
				header:'作物名称',
				dataIndex:'goodsName'
			},{
               header:'产量',
               dataIndex:'amount'
            },{
                header:'单价',
                dataIndex:'price'
            },{
                header:'剩余数量',
                dataIndex:'remainder'
            },{
                header:'总收入',
                dataIndex:'total'
            }]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name:'goodsName',
				type:'string'
			},{
				name:'amount',
				type:'string'
			},{
                name:'price',
                type:'string'
            },{
                name:'remainder',
                type:'string'
            },{
                name:'total',
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
		Long.ReserveGrid.superclass.initComponent.call(this);
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
                    width:170,
                    fieldLabel:'作物名称',
                    name:'goodsName',
                    allowBlank: false,
                    blankText:'作物名称不为空'
                },{
                    width:170,
                    fieldLabel:'产量',
                    name:'amount'
                },{
                    width:170,
                    fieldLabel:'单价',
                    name:'price'
                }]
        });
        return form;
    },
    add : function(){   
        this.addForm=this.createAddForm();
        var win = new Ext.Window({
            id : 'addOrgWin',
            title : '作物入库',
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
        var reserveId = 0;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'reserve!save',
            params:{
                reserveId : reserveId
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
            labelWidth:60,
            frame:true,
            items :[{
                    xtype:'hidden',
                    name:'id'
                },{
                    width:170,
                    fieldLabel:'作物名称',
                    name:'goodsName',
                    allowBlank: false,
                    blankText:'作物名称不为空'
                },{
                    width:170,
                    fieldLabel:'产量',
                    name:'amount'
                },{
                    width:170,
                    fieldLabel:'单价',
                    name:'price'
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
            title : '修改库存信息',
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
        var clientId = 0;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'reserve!update',
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
        Ext.Msg.confirm('系统提示', '确定要删除所选作物吗？', function(btn){
            if (btn == 'yes') {
                Ext.Ajax.request({
                    url : 'reserve!delete',
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