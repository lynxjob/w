Ext.ns('Long');
//用户表单
Long.UserForm = Ext.extend(Ext.form.FormPanel, {
	defaultType : 'textfield',
	labelAlign : 'right',
	labelWidth : 60,
	frame : true,
	width : 300,
	bodyStyle : 'padding:5px',
    initComponent: function() {
    	 this.items=[{
    		 	xtype:'hidden',
    		 	name:'id'
    	 	},{
				fieldLabel : '姓名',
				name : 'name',
				width:190,
				allowBlank: false,
				blankText:'姓名不为空',
				msgTarget: 'side'
			},{
				fieldLabel : '密码',
				width:190,
				inputType : 'password',
				name : 'password',
				blankText:'密码不为空',
				allowBlank: false
			},{
				fieldLabel : '年龄',
				width:190,
				xtype:'numberfield',
				name : 'age',
				blankText:'年龄不为空',
				allowBlank: false
			},{
				fieldLabel : '联系电话',
				width:190,
				name : 'tel',
				blankText:'联系电话不为空',
				allowBlank: false
			},{
				xtype : 'radiogroup',
				width:190,
				fieldLabel : '状态',
				name : 'state',
				items : [{
							boxLabel : '启用',
							name : 'state',
							inputValue : 1,
							checked : true
						}, {
							boxLabel : '禁用',
							name : 'state',
							inputValue : 0
						}]
			}];
         console.log('初始化组件：userform');
    	 Long.UserForm.superclass.initComponent.call(this);
    }
});