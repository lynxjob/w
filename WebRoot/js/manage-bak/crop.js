Ext.namespace('Long');
//作物管理
Long.CropGrid = Ext.extend(Long.BaseGeneralGrid,{
    id:'cropgrid',
    title:'作物管理',
    iconCls:'querysystem_icon',
    baseUrl:'crop!list',
    initComponent : function(){
        this.sm = new Ext.grid.CheckboxSelectionModel();
        this.cm = new Ext.grid.ColumnModel([this.sm,{
            header:'ID编号',
            dataIndex:'id',
            hidden:true
        },{
            header:'作物名称',
            dataIndex:'name'
        },{
            header:'作物描述',
            dataIndex:'des'
        },{
            header:'相关图片',
            dataIndex:'img_url'
        }]);
        this.record = new Ext.data.Record.create([{
            name:'id',
            type:'int'
        },{
            name:'name',
            type:'string'
        },{
            name:'des',
            type:'string'
        },{
            name:'img_url',
            type:'string'
        }]);
        this.tbar = new Ext.Toolbar([{
            text : '刷新',
            icon : 'images/icons/f5.png',
            handler: this.refresh.createDelegate(this)
        },{
            text:'添加',
            icon : 'images/icons/role+.png',
            handler: this.add.createDelegate(this),
            hidden:!Long.cpAdd
        },{
            text:'修改',
            icon : 'images/icons/role_.png',
            handler: this.edit.createDelegate(this),
            hidden:!Long.cpEdit
        },{
            text : '删除',
            icon : 'images/icons/role-.png',
            handler: this.deleteOp.createDelegate(this),
            hidden:!Long.cpDelete
        }]);
        this.on('rowdblclick',this.edit,this);
        Long.CropGrid.superclass.initComponent.call(this);
    },
    add:function(){
        this.cf = new Long.CropForm();
        var win = new Ext.Window({
            id:'addwin',
            title : '添加作物信息',
		    width : 455,
		    autoHeight : true,
		    closable : false,
            resizable : false,
		    modal : true,
		    border : false,
		    defaults : {
		        border : false
		    },
		    buttonAlign : 'center',
            items:[this.cf],
            buttons:[{
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
    submitAddForm:function(){
        var form = this.cf.form;
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            url : 'crop!save',
            scope : this,
            success : function(form, action) {
                Ext.ux.Toast.msg('系统提示',action.result.msg);
                Ext.getCmp('addwin').close();
                this.refresh();
            },
            failure : function(form, action) {
                Ext.ux.Toast.msg('系统提示', '无法访问后台!');
            }
        });
    },
    edit:function(){
        var rows=this.getSelectedRecord();
        if(rows==false || rows.length>1){
            Ext.ux.Toast.msg('系统提示', '请选择一行进行操作！');
            return;
        }
        this.cf = new Long.CropForm();
        var win = new Ext.Window({
            id:'editwin',
            title : '修改作物信息',
            width : 455,
            autoHeight : true,
            closable : false,
            resizable : false,
            modal : true,
            border : false,
            defaults : {
                border : false
            },
            buttonAlign : 'center',
            items:[this.cf],
            buttons:[{
                        text : '确定',
                        icon : 'images/icons/ok.png',
                        handler : this.submitEditForm.createDelegate(this)
                    }, {
                        text : '取消',
                        icon : 'images/icons/cancel.png',
                        handler : function() {
                            win.close();
                       }
                   }]
        });
        win.show();
        this.cf.form.loadRecord(rows[0]);
        Ext.getCmp('icon1').el.dom.src = rows[0].get('img_url');
    },
    submitEditForm:function(){
        var form=this.cf.form;
        var rows=this.getSelectedRecord();
        if(form.isValid()==false){
            return;
        }
        form.submit({
            waitTitle : '请稍后',
            waitMsg : '正在提交信息···',
            scope : this,
            url : 'crop!update',
            params:{
              id  : rows[0]['id']
            },
            success : function(form, action) {
                Ext.ux.Toast.msg('系统提示',action.result.msg);
                Ext.getCmp('editwin').close();
                this.refresh();
            },
            failure : function(form, action) {
                Ext.ux.Toast.msg('系统提示', '无法访问后台!');
            }
        });
    },
    deleteOp:function(){
       var ids=this.getSelectedIds();
        if(ids==false){
            Ext.ux.Toast.msg('系统提示','请选择一行或多行操作!');
            return;
        }
        Ext.Msg.confirm('系统提示', '确定要删除所选行吗?', function(btn){
            if (btn == 'yes') {
                Ext.Ajax.request({
                    url : 'crop!delete',
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
                        Ext.ux.Toast.msg('系统提示','无法访问后台!');
                    }           
                });
            }
        }, this); 
    }
});