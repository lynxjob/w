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
            		xtype:'fieldset',
            		height:200,
            		title:'LED连接',
            		layout:{
		                	type:'table',
		                	columns:4
		                },
            		items:[{
            					xtype:'label',
            					text:'屏号:',
        	 					style:'margin-left:10px'
            		       },{
	            		    	xtype:'textfield',
	            		    	name:'pinghao',
	            		    	style:'margin-left:10px'
		            		},{
            					xtype:'label',
            					text:'IP地址:',
        	 					style:'margin-left:10px'
            		       },{
		            			xtype:'textfield',
		            			name:'IP',
		            			style:'margin:10px 0 0 10px'
			            		},{
            					xtype:'label',
            					text:'显示屏宽:',
        	 					style:'margin:10px 0 0 10px'
            		       },{
		            			xtype:'textfield',
		            			name:'pWidth',
		            			style:'margin:10px 0 0 10px'
			            		},{
            					xtype:'label',
            					text:'显示屏高:',
        	 					style:'margin:10px 0 0 10px'
            		       },{
		            			xtype:'textfield',
		            			name:'pHeight',
		            			style:'margin:10px 0 0 10px'
			            		},{
            					xtype:'label',
            					text:'单双色:',
        	 					style:'margin:50px 0 0 10px'
            		       },{
		            			xtype:'combo',
		            			store:store,
		            			triggerAction:'all',
		            			displayField:'name',
            					valueField:'id',
            					mode:'local',
		            			style:'margin-left:10px'
			            		},{
			            		xtype:'button',
			            		text:'发送屏参',
			            		colspan:2,
			            		style:'margin:20px 0 0 100px',
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
			            		}]
			            	},
			            	{
			            		xtype:'fieldset',
			            		height:300,
			            		title:'LED发送',
			            		layout:{
				                	type:'table',
				                	columns:4
				                },
				                items:[{
            					xtype:'label',
            					text:'起点X:',
        	 					style:'margin-left:30px'
            		       },{
	            		    	xtype:'textfield',
	            		    	name:'start',
	            		    	style:'margin-left:0px'
		            		},{
            					xtype:'label',
            					text:'起点Y:',
        	 					style:'margin-left:10px'
            		       },{
		            			xtype:'textfield',
		            			name:'end',
		            			style:'margin:10px 0 0 10px'
			            		},{
            					xtype:'label',
            					text:'宽度:',
        	 					style:'margin:10px 0 0 30px'
            		       },{
		            			xtype:'textfield',
		            			name:'width',
		            			style:'margin-left:0px'
			            		},{
            					xtype:'label',
            					text:'高度:',
        	 					style:'margin:10px 0 0 10px'
            		       },{
		            			xtype:'textfield',
		            			name:'height',
		            			style:'margin:10px 0 0 10px'
			            		},{
			            		xtype:'textarea',
			            		colspan:4,
			            		width:400,
			            		name:'sendText',
			            		style:'margin:10px 0 0 10px',
			            		forceFit:true
			            		},{
			            		 xtype:'button',
			            		 text:'参数保存',
			            		 colspan:2,
			            		 style:'margin:20px 0 0 80px'
			            		},{
		            			 xtype:'button',
		            			 text:'发送',
		            			 colspan:2,
		            			 style:'margin:20px 0 0 20px'
			            		},{
            					xtype:'label',
            					text:'实时数据发送:',
        	 					style:'margin:40px 0 0 40px'
            		       		},{
            		       		xtype:'radiogroup',
            		            colspan:3,
//            		            hideLabel:false,
            		            style:'margin:20px 0 0 50px',
            		            fieldLabel:'实时数据发送',
            		            items:[
            		            	{boxLabel: '开启', name: 'fasong',inputValue:0},
	                            	{boxLabel: '暂停', name: 'fasong',inputValue:1}  ]
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