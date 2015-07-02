Ext.namespace('Long')
//作物信息表单
Long.CropForm = Ext.extend(Ext.form.FormPanel,{
        id:'cropform',
	    border : false,
	    layout : 'tableform',
	    layoutConfig : {
	        columns : 3
	    },
        width:650,
        autoHeight:true,
	    fileUpload : true,
	    defaultType : 'textfield',
	    labelAlign : 'right',
	    labelWidth : 75,
	    frame : true,
        factors : '',
	    defaults : {
	        anchor : '95%'
	    },
    initComponent:function(){
        //存储各生长阶段环境因素
        var getKey = function(o){
            return o.level
        };
        if(!this.factors){
            this.factors = new Ext.util.MixedCollection(false,getKey);
        };
        this.items = [{
        	id:'_name',
            name : 'name',
            fieldLabel : '作物名称',
            allowBlank:false,
            blankText:'请填写作物名称!'
        },{
            name : 'total',
            fieldLabel:'生长周期(天)',
            allowBlank:false,
            blankText:'请填写作物生长周期!'
        },{
            xtype : 'panel',
            layout : 'column',
            rowspan : 4,
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
            xtype : 'combo',
            mode : 'local',
            id : '_level',
            hiddenName : 'level',
            fieldLabel : '生长阶段',
            displayField : 'level_str',
            valueField : 'level_id',
            typeAhead : true,
            emptyText : '请设置作物生长时间段...',
            allowBlank : false,
            blankText : '请设置作物生长时间段!',
            editable : false,
            triggerAction : 'all',
            store:new Ext.data.SimpleStore({
                fields:['level_id','level_str'],
                data :[[1,'发芽期'],[2,'幼苗期'],[3,'初花期'],[4,'结果期']]
            }),
            listeners:{
        		change:function(el,newVal){
        			var name = Ext.getCmp('_name').value;
        			if(name!=''){
        				Ext.Ajax.request({
   		                 url : 'crop!loadData',
   		                 scope : this,
   		                 params :{
   		        		 	 cropName:name,
   		                     level : newVal
   		                 },
   		                 success : function(data) {
   		                     var factor = Ext.util.JSON.decode(data.responseText);
   		                     Ext.getCmp('start').setValue(factor.start);
   		                     Ext.getCmp('end').setValue(factor.end);
   		                     Ext.getCmp('coc').setValue(factor.coc);
   		                     Ext.getCmp('guanghe').setValue(factor.guanghe);
   		                     Ext.getCmp('daqishidu').setValue(factor.daqishidu);
   		                     Ext.getCmp('daqiwendu').setValue(factor.daqiwendu);
   		                     Ext.getCmp('turangshuifen').setValue(factor.turangshuifen);
   		                     Ext.getCmp('dadiwendu').setValue(factor.dadiwendu);
   		                 }           
   		             });
        			}
        		}
        	}
        },{
            id : 'start',
            name : 'start',
            fieldLabel:'起始(天)',
            allowBlank:false,
            blankText:'请填写起始天数!'
        },{
            id : 'end',
            name : 'end',
            fieldLabel:'结束(天)',
            allowBlank:false,
            blankText:'请填写结束天数!'
        },{
            id : 'coc',
            name : 'coc',
            fieldLabel:'CO2适宜浓度(ppm)',
            allowBlank:false,
            blankText:'请填写CO2适宜浓度!'
        },{
            id : 'guanghe',
            name : 'guanghe',
            fieldLabel:'光照强度(W)',
            allowBlank:false,
            blankText:'请填写光照强度!'
        },{
            id : 'daqiwendu',
            name : 'daqiwendu',
            fieldLabel:'大棚温度(摄氏度)',
            allowBlank:false,
            blankText:'请填写大棚温度!'
        },{
            id : 'daqishidu',
            name : 'daqishidu',
            fieldLabel:'大棚湿度(RH)',
            allowBlank:false,
            blankText:'请填写大棚湿度!'
        },{
            id : 'dadiwendu',
            name : 'dadiwendu',
            fieldLabel:'土壤温度(摄氏度)',
            allowBlank:false,
            blankText:'请填写土壤温度!'
        },{
            id : 'turangshuifen',
            name : 'turangshuifen',
            fieldLabel:'土壤湿度(%)',
            allowBlank:false,
            blankText:'请填写土壤湿度!',
            listeners : {
                blur : function(){
                     var factor = {
			            level : isNaN(Ext.getCmp('_level').getValue())==false ? Ext.getCmp('_level').getValue() : 0,
			            start : isNaN(Ext.getCmp('start').getValue())==false ? parseFloat(Ext.getCmp('start').getValue()) : 0,
			            end : isNaN(Ext.getCmp('end').getValue())==false ? parseFloat(Ext.getCmp('end').getValue()) : 0,
			            coc : isNaN(Ext.getCmp('coc').getValue())==false ? parseFloat(Ext.getCmp('coc').getValue()) : 0,
			            guanghe : isNaN(Ext.getCmp('guanghe').getValue())==false ? parseFloat(Ext.getCmp('guanghe').getValue()) : 0,
			            daqiwendu : isNaN(Ext.getCmp('daqiwendu').getValue())==false ? parseFloat(Ext.getCmp('daqiwendu').getValue()) : 0,
			            daqishidu : isNaN(Ext.getCmp('daqishidu').getValue())==false ? parseFloat(Ext.getCmp('daqishidu').getValue()) : 0,
			            dadiwendu : isNaN(Ext.getCmp('dadiwendu').getValue())==false ? parseFloat(Ext.getCmp('dadiwendu').getValue()) : 0,
			            turangshuifen : isNaN(Ext.getCmp('turangshuifen').getValue())==false ? parseFloat(Ext.getCmp('turangshuifen').getValue()) : 0
			        }
                    this.ownerCt.getFactors().add(factor);
                    delete factor;
                }
            }
        },{
            name : 'des',
            xtype : 'htmleditor',
            fieldLabel : '作物描述',
            height : 120,
            colspan : 3
        }];
        Long.CropForm.superclass.initComponent.call(this);
    },
    getFactors : function(){
        return this.factors;
    }
});