Ext.ns('Long');
//公告表单
Long.NoticeForm = Ext.extend(Ext.form.FormPanel, {
	defaultType : 'textfield',
	labelAlign : 'right',
	labelWidth : 60,
	width : 650,
	frame : true,
	bodyStyle : 'padding:5px',
    initComponent: function() {
    	 this.items=[{
    		 	xtype:'hidden',
    		 	name:'id'
    	 	},{
				fieldLabel : '公告标题',
				name : 'title',
				allowBlank: false,
				blankText:'公告标题不为空',
				width:537,
				maxLength: 100,
		        minLength: 4
			},{
				fieldLabel : '公告内容',
				xtype:'htmleditor',
				name : 'content',
				anchor: '98%'
			},{
				xtype:'radiogroup',
				fieldLabel:'公告等级',
				name:'level',
				items:[{
							boxLabel:'一般',
							name:'level',
							inputValue:1,
							checked:true
						},{
							boxLabel:'重要',
							name:'level',
							inputValue:2
						},{
							boxLabel:'紧急',
							name:'level',
							inputValue:3
						},{
							boxLabel:'置顶',
							name:'level',
							inputValue:4
						}]
			}];
    	 Long.NoticeForm.superclass.initComponent.call(this);
    }
});