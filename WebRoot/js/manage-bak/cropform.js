Ext.namespace('Long')
//作物信息表单
Long.CropForm = Ext.extend(Ext.form.FormPanel,{
        id:'cropform',
	    border : false,
	    layout : 'tableform',
	    layoutConfig : {
	        columns : 2
	    },
        width:450,
        autoHeight:true,
	    fileUpload : true,
	    defaultType : 'textfield',
	    labelAlign : 'right',
	    labelWidth : 75,
	    frame : true,
	    defaults : {
	        anchor : '95%'
	    },
    initComponent:function(){
        this.items = [{
            name : 'name',
            fieldLabel : '作物名称'
        },{
            xtype : 'panel',
            layout : 'column',
            bodyStyle:'margin-bottom:5px',
            items : [ {
                columnWidth : 0.62,
                xtype : 'fieldset',
                title : '上传图片',
                style : ' padding:0 0 0 0; margin:0 0 0 0;!important',
                width : 120,
                height : 95,
                items : [{
                    id : 'icon1',
                    xtype : 'image',
                    src : 'images/upload/default.jpg',
                    style : ' margin:0 0 0 3px;',
                    width : 120,
                    height : 92
                }]
            },{
                columnWidth : 0.38,
                height : 95,
                xtype : 'panel',
                layout : 'table',
                layoutConfig : {
                    columns : 1
                },
                items : [{
                    name:'upload',
		            xtype:'fileuploadfield',
		            height : 30,
                    cellCls : 'bbb',
		            buttonOnly : true,
		            buttonText : '浏览',
		            listeners : {
		                'fileselected' : function(fb, v) {
//		                    Ext.getCmp('icon1').el.dom.src = v;
		                    Ext.getCmp('uploadbtn').enable();
		                }
		            }
                },{
		            id : 'uploadbtn',
		            height : 30,
                    cellCls : 'aaa',
		            disabled : true,
		            xtype : 'button',
		            text : '上传',
		            handler : function() {
		                Ext.getCmp('cropform').getForm().submit( {
		                    url : 'upload!upload',
		                    method:'POST',
		                    waitMsg : '正在提交，请稍等...',
		                    success : function(f, a) {
		                        Ext.getCmp('icon1').el.dom.src = a.result.url;
		                        Ext.getCmp('uploadbtn').disable();
		                    },
                            failure:function(f,a){
                                Ext.ux.Toast.msg('系统提示','上传图片失败!');
                            }
		                });
		            }
                }]
            }]
        },{
            name : 'des',
            xtype : 'htmleditor',
            fieldLabel : '作物描述',
            height : 100,
            colspan : 2
        }];
        Long.CropForm.superclass.initComponent.call(this);
    }
});