Ext.ns('Long');
//角色表单
Long.RoleForm = Ext.extend(Ext.form.FormPanel, {
	defaultType : 'textfield',
	labelAlign : 'right',
	labelWidth : 30,
	frame : true,
	width : 300,
	bodyStyle : 'padding:5px',
    initComponent: function() {
    	 this.items=[{
    		 	xtype:'hidden',
    		 	name:'id'
    	 	},{
				fieldLabel : '名称',
				name : 'name',
				width:190,
				allowBlank: false,
				blankText:'角色名称不能为空'
			},{
				fieldLabel : '描述',
				name : 'des',
				width:190,
				blankText:'角色描述不能为空',
				allowBlank: false
			}];
    	 Long.RoleForm.superclass.initComponent.call(this);
    }
});