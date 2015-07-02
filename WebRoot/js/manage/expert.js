Ext.namespace('Long');
//专家系统
var shedId = 0;
Long.ExpertSystemPanel = Ext.extend(Ext.Panel,{
    id:'expertsystempanel',
    title:'LED控制',
    iconCls:'expertsystem_icon',
    initComponent : function(){
        this.nav = new Long.ShedTree({
        	id:'LED_shedtree',
            region:'west',
            listeners:{
	            'click': function(node,event) {
	                Ext.getCmp('LED_control').setTitle('LED控制['+node.text+']');
	                shedId = node.id;
	                //刷新store未添加
	            }
	        }
        });
        var store = new Ext.data.ArrayStore({
        fields:['id','name'],
        data:[[1,'单色'],[2,'双色']]
    	});
        this.LED_control= new Ext.Panel({
        	title:'LED控制',
        	id:'LED_control',
            region:'center',
            autoScroll:true,
            items:[
            	{
            		xtype:'form',
//            		height:300,
            		title:'LED连接',
            		id:'Ledform',
            		bodyStyle:'padding:10px',
//            		layout:{
//		                	type:'table',
//		                	columns:4
//		                },
            		items:[{
	            		    	xtype:'textfield',
	            		    	fieldLabel:'屏号',
	            		    	
	            		    	name:'pinghao',
	            		    	style:'margin:10px 0 0 10px',
	            		    	allowBlank:false,
                    			blankText:'请输入屏号!'
		            		},{
		            			xtype:'textfield',
		            			name:'IP',
		            			fieldLabel:'IP地址',
		            			style:'margin:10px 0 0 10px',
		            			allowBlank:false,
                   				blankText:'请输入IP地址!'
			            		},{
		            			xtype:'textfield',
		            			fieldLabel:'显示屏宽',
		            			name:'pWidth',
		            			style:'margin:10px 0 0 10px',
		            			allowBlank:false,
                    			blankText:'请输入屏宽!'
			            		},{
		            			xtype:'textfield',
		            			fieldLabel:'显示屏高',
		            			name:'pHeight',
		            			style:'margin:10px 0 0 10px',
		            			allowBlank:false,
                    			blankText:'请输入屏高!'
			            		},{
		            			xtype:'combo',
		            			store:store,
		            			triggerAction:'all',
		            			displayField:'name',
            					valueField:'id',
            					mode:'local',
            					fieldLabel:'单双色',
		            			style:'margin-left:10px'
			            		},/*{
			            		xtype:'button',
			            		text:'发送屏参',
			            		colspan:2,
			            		style:'margin:10px 0 0 10px',
			            		handler:function(){
			            			 if(shedId == 0){
                        				Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
				                        return;
				                       }
			                           this.submit({
				                            waitTitle : '请稍后',
				                            waitMsg : '正在提交信息···',
				                            url : 'led!save',
				                            params:{
				                               shed_id:shedId,
				                               pinghao: 1
				                            },
				                            scope : this,
				                            success : function(form, action) {
				                                Ext.ux.Toast.msg('系统提示',action.result.msg);
				                            },
				                            failure : function(form, action) {
				                                Ext.ux.Toast.msg('系统提示', '采集仪设置应用失败!');
				                            }
				                        })
			            		}
			            		},*/
			            		{
	            		    	xtype:'textfield',
	            		    	name:'start',
	            		    	fieldLabel:'起点X',
	            		    	style:'margin:10px 0 0 10px',
	            		    	allowBlank:false,
                    			blankText:'请输入起点X!'
		            			},{
		            			xtype:'textfield',
		            			name:'end',
		            			fieldLabel:'起点Y',
		            			style:'margin:10px 0 0 10px',
		            			allowBlank:false,
                    			blankText:'请输入起点Y!'
			            		},{
		            			xtype:'textfield',
		            			name:'width',
		            			fieldLabel:'宽度',
		            			style:'margin:10px 0 0 10px',
		            			allowBlank:false,
                    			blankText:'请输入宽度!'
			            		},{
		            			xtype:'textfield',
		            			name:'height',
		            			fieldLabel:'高度',
		            			style:'margin:10px 0 0 10px',
		            			allowBlank:false,
                    			blankText:'请输入高度!'
			            		},{
			            		xtype:'textarea',
			            		width:400,
			            		name:'sendText',
			            		style:'margin:10px 0 0 10px',
			            		forceFit:true,
			            		allowBlank:false,
                    			blankText:'请输入要发送的数据!'
			            		},{
            		       		xtype:'radiogroup',
            		            style:'margin:10px 0 0 10px',
            		            fieldLabel:'实时数据发送',
            		            items:[
            		            	{boxLabel: '开启', name: 'fasong',id:'fasong1',inputValue:0},
	                            	{boxLabel: '暂停', name: 'fasong',id:'fasong2',inputValue:1}  ]
            		       		}],
            		       		buttons:[{
			            		 text:'参数保存',
			            		 style:'margin-right:200px',
			            		 handler:function(){
			            		 	var Ledform=Ext.getCmp("Ledform").getForm();
//			            		 	alert(Ledform);
			            		 	if(Ledform == 'undefined' || Ledform == null){
				                        return;
				                       }
			            			 if(shedId == 0){
                        				Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
				                        return;
				                       }
			                           this.submit({
				                            waitTitle : '请稍后',
				                            waitMsg : '正在提交信息···',
				                            url : 'led!save',
				                            params:{
				                               shed_id:shedId,
				                               pinghao: 1
				                            },
				                            scope : this,
				                            success : function(form, action) {
				                                Ext.ux.Toast.msg('系统提示',action.result.msg);
				                            },
				                            failure : function(form, action) {
				                                Ext.ux.Toast.msg('系统提示', '采集仪设置应用失败!');
				                            }
				                        })
			            		}
			            		},{
		            			 text:'发送',
 								 style:'margin-right:250px'
			            		}]
			            	
			            	}]
			            	
        });
        this.text=new Ext.Panel({
        	title:'发送数据',
        	id:'LED_text',
            region:'east',
            autoScroll:true,
            width:400
        });
        Ext.applyIf(this, {
            border:false,
            layout:'border',
            closable:true,
            items:[this.nav,this.LED_control,this.text]
        });
        console.log('初始化组件：expertsystempanel');
        Long.ExpertSystemPanel.superclass.initComponent.call(this);
    }
});