Ext.namespace('Long');
var shedId = 0;
//控制中心操作记录
function OperMsg(msg,tag){
    var mNowTime =  new Date().format('Y-m-d H:i:s');
    Ext.getDom('monitorOper').innerHTML = mNowTime + " --> " + msg+(tag==0?'打开':'关闭')+ "<br>" 
    									+ Ext.getDom('monitorOper').innerHTML; 
}
//控制中心异常信息记录
function LogMsg(msg){
    var mNowTime =  new Date().format('Y-m-d H:i:s');
    Ext.getDom('monitorLog').innerHTML = mNowTime + " --> " + msg+ "<br>" 
    									+ Ext.getDom('monitorLog').innerHTML; 
}
//radioChange
function radioChange(id,radiofield,checked){
    var type = 'realControl';
    var status;
    if(checked.inputValue == 0){
        status = 0;
    }else{
        status = 1;
    }
	Ext.Ajax.request({
	 url:'enviro!loadRelayData',
	 params:{
	    relay_id : id,
	    relay_type : type,
	    relay_status : status,
        shedId : shedId
	 },
	 success:function(response){
	    var msg = Ext.util.JSON.decode(response.responseText);
	    if('success' == msg.rs){
	        Ext.ux.Toast.msg('系统提示','设备开启完成!');
	    }else if('close' == msg.rs){
	        Ext.ux.Toast.msg('系统提示','请打开串口!');
	    }else if('fail' == msg.rs){
	        Ext.ux.Toast.msg('系统提示','继电器无响应!');
	    }
	 }
	});
};
//控制中心
Long.MonitorPanel = Ext.extend(Ext.Panel,{
    id:'monitorpanel',
    title:'控制中心',
    iconCls:'monitor_icon',
    initComponent : function(){
       this.monitor_nav = new Long.ShedTree({
            id:'monitor_shedtree',
            region:'west',
            listeners:{
                'click': function(node,event) {
                    Ext.getCmp('monitor_contentpanel').setTitle('控制中心['+node.text+']');
                    shedId = node.id;
                }
            }
        });
        this.monitor_content= new Ext.Panel({
            id:'monitor_contentpanel',
            title:'控制中心',
            layout:'border',
            region:'center',
            items:[
                {
                  region:'west',
                  width:556,
                  items:[{
                     xtype:'flash',
                     url:'flash/fengji.swf'          
                  }]             
                },{
                region:'center',
                layout:{
                	type:'table',
                	columns:2
                },
//                layoutConfig: {columns:2},
                bodyStyle:'padding:5px',
                items:[{
                    xtype: 'fieldset',
//                    title: '控制操作',
//                    style:'margin:10px',
                    colspan:2,
                    width:500,
                    layout:'column',
                    autoHeight: true,
                    items: [
                    	{
                    	    xtype:'fieldset',
                    		title: '控制操作',
                    		width:230,
                    		autoHeight: true,
                    		items:[
                    		{
	                        id:'rb1',
	                        xtype: 'radiogroup',
	                        hideLabel:false,
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '外光帘展开',
	                        items: [
	                            {boxLabel: '开启', name: 'rb1-auto',id:'rb1-auto-1', inputValue: 0},
	                            {boxLabel: '暂停', name: 'rb1-auto',id:'rb1-auto-2',inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('外光帘展开',checked.inputValue);
                                }
                            }
                        },{
	                        id:'rb2',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '外光帘收拢',
	                        items: [
	                            {boxLabel: '开启', name: 'rb2-auto',id:'rb2-auto-1', inputValue: 0},
	                            {boxLabel: '暂停', name: 'rb2-auto',id:'rb2-auto-2',inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('外光帘收拢',checked.inputValue);
                                }
                            }
                        },{
	                        id:'rb3',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '内光帘展开',                     
	                        items: [
	                            {boxLabel: '开启', name: 'rb3-auto',id:'rb3-auto-1', inputValue: 0},
	                            {boxLabel: '暂停', name: 'rb3-auto',id:'rb3-auto-2', inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('内光帘展开',checked.inputValue);
                                }
                            }
                        },{
	                        id:'rb4',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '内光帘收拢',                     
	                        items: [
	                            {boxLabel: '开启', name: 'rb4-auto',id:'rb4-auto-1', inputValue: 0},
	                            {boxLabel: '暂停', name: 'rb4-auto',id:'rb4-auto-2', inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('内光帘收拢',checked.inputValue);
                                }
                            }
                        },{
	                        id:'rb5',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '外侧窗展开',
	                        items: [
	                            {boxLabel: '开启', name: 'rb5-auto',id:'rb5-auto-1', inputValue: 0},
	                            {boxLabel: '暂停', name: 'rb5-auto',id:'rb5-auto-2', inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('外侧窗展开',checked.inputValue);
                                }
                            }
                        },{
	                        id:'rb6',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '外侧窗收拢',
	                        items: [
	                            {boxLabel: '开启', name: 'rb6-auto',id:'rb6-auto-1', inputValue: 0},
	                            {boxLabel: '暂停', name: 'rb6-auto',id:'rb6-auto-2', inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('外侧窗收拢',checked.inputValue);
                                }
                            }
                        },{
	                        id:'rb10',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '补光灯',
	                        items: [
	                            {boxLabel: '打开', name: 'rb10-auto',id:'rb10-auto-1', inputValue: 0},
	                            {boxLabel: '关闭', name: 'rb10-auto',id:'rb10-auto-2', inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                  	OperMsg('补光灯',checked.inputValue);  
                                }
                            }
                        },{
	                        id:'rb8',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '风机',
	                        items: [
	                            {boxLabel: '打开', name: 'rb8-auto',id:'rb8-auto-1', inputValue: 0},
	                            {boxLabel: '关闭', name: 'rb8-auto',id:'rb8-auto-2', inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('风机',checked.inputValue);  
                                }
                            }
                        }
                    		]
                    	},{
                    		xtype:'fieldset',
                    		title:'控制操作',
                    		bodyStyle:'padding:5px',
                    		width:250,
                    		autoHeight:true,
                    		items:[
                    			{
	                        id:'rb9',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '水泵',
	                        items: [
	                            {boxLabel: '打开', name: 'rb9-auto',id:'rb9-auto-1', inputValue: 0},
	                            {boxLabel: '关闭', name: 'rb9-auto',id:'rb9-auto-2', inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('水泵',checked.inputValue);  
                                }
                            }
                        },{
                            id:'rb7',
                            xtype: 'radiogroup',    
                            itemCls: 'x-check-group-alt',
                            fieldLabel: '湿帘',
                            items: [
                                {boxLabel: '打开', name: 'rb7-auto',id:'rb7-auto-1', inputValue: 0},
                                {boxLabel: '收拢', name: 'rb7-auto',id:'rb7-auto-2', inputValue: 1}       
                            ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('湿帘',checked.inputValue);  
                                }
                            }
                        },{
                            id:'rb15',
                            xtype: 'radiogroup',
                            itemCls: 'x-check-group-alt',
                            fieldLabel: '滴灌',
                            items: [
                                {boxLabel: '打开', name: 'rb15-auto',id:'rb15-auto-1', inputValue: 0},
                                {boxLabel: '关闭', name: 'rb15-auto',id:'rb15-auto-2', inputValue: 1}       
                            ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('滴灌',checked.inputValue);  
                                }
                            }
                        },{
                            id:'rb16',
                            xtype: 'radiogroup',
                            itemCls: 'x-check-group-alt',
                            fieldLabel: '雾喷',
                            items: [
                                {boxLabel: '打开', name: 'rb16-auto',id:'rb16-auto-1', inputValue: 0},
                                {boxLabel: '关闭', name: 'rb16-auto',id:'rb16-auto-2', inputValue: 1}       
                            ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('雾喷',checked.inputValue);  
                                }
                            }
                        },{
                            id:'rb11',
                            xtype: 'radiogroup',
                            itemCls: 'x-check-group-alt',
                            fieldLabel: '第一路喷灌',
                            items: [
                                {boxLabel: '打开', name: 'rb11-auto',id:'rb11-auto-1', inputValue: 0},
                                {boxLabel: '关闭', name: 'rb11-auto',id:'rb11-auto-2', inputValue: 1}       
                            ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('第一路喷灌',checked.inputValue);  
                                }
                            }
                        },{
                            id:'rb12',
                            xtype: 'radiogroup',
                            itemCls: 'x-check-group-alt',
                            fieldLabel: '第二路喷灌',
                            items: [
                                {boxLabel: '打开', name: 'rb12-auto',id:'rb12-auto-1', inputValue: 0},
                                {boxLabel: '关闭', name: 'rb12-auto',id:'rb12-auto-2', inputValue: 1}       
                            ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('第二路喷灌',checked.inputValue); 
                                }
                            }
                        },{
                            id:'rb13',
                            xtype: 'radiogroup',
                            itemCls: 'x-check-group-alt',
                            fieldLabel: '第三路喷灌',
                            items: [
                                {boxLabel: '打开', name: 'rb13-auto',id:'rb13-auto-1', inputValue: 0},
                                {boxLabel: '关闭', name: 'rb13-auto',id:'rb13-auto-2', inputValue: 1}       
                            ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('第三路喷灌',checked.inputValue); 
                                }
                            }
                        },{
                            id:'rb14',
                            xtype: 'radiogroup',
                            itemCls: 'x-check-group-alt',
                            fieldLabel: '第四路喷灌',
                            items: [
                                {boxLabel: '打开', name: 'rb14-auto',id:'rb14-auto-1', inputValue: 0},
                                {boxLabel: '关闭', name: 'rb14-auto',id:'rb14-auto-2', inputValue: 1}       
                            ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                    OperMsg('第四路喷灌',checked.inputValue); 
                                }
                            }
                        }
                    		]
                    	}
                        ],
                        listeners:{
                            'render':function(){
                                Ext.Ajax.request({
                                    url:'enviro!loadRelayData',
                                    params:{
                                        relay_type:'readIO'
                                    },
                                    success:function(response){
                                        var status;
                                        var msg = Ext.util.JSON.decode(response.responseText);
                                        var lRouter = parseInt(msg.lRouter);
                                        var hRouter = parseInt(msg.hRouter);
							            for(var i=1,j=1;i<17;i++,j*=2)
							            {
							                if(i<9)
							                {
							                    status = lRouter&j;
							                } else if(i>8 && i<17)
							                {
							                    if(i == 9)  j=1;
							                    status = hRouter&j;
							                }
							                if(status == 0)
							                {
                                                Ext.getCmp('rb'+i+'-auto-2').setValue(true);
							                }else{
							                    Ext.getCmp('rb'+i+'-auto-1').setValue(true);
							                }
							            }
                                    }
                                });
                            }
                        }
                },{
	                xtype:'button',
	                text:'手动控制',
	                style:'margin-left:10px',
                    handler:function(){
                        if(shedId <=0 ){
                            Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
                            return;
                        }
                        OperMsg('手动控制',0); 
                        for(var i=1; i<=16; i++){
                            Ext.getCmp('rb'+i).setDisabled(false);
                        }
                        Ext.Ajax.request({
                            url:'enviro!setManual',
                            params:{
                                shedId : shedId
                            },
                            success:function(response){
                                var result = Ext.util.JSON.decode(response.responseText);
                                if(result.success){
                                    Ext.ux.Toast.msg('系统提示',result.msg);
                                }
                            }
                        });
                    }
	              },{
	                xtype:'button',
	                text:'自动控制',
	                style:'margin-left:100px',
                    handler:function(){
                        if(shedId <=0 ){
                            Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
                            return;
                        }
                         OperMsg('专家系统',0); 
                         for(var i=1; i<=16; i++){
                            Ext.getCmp('rb'+i).setDisabled(true);
                        }
                        Ext.Ajax.request({
                            url:'enviro!setAuto',
                            params:{
                                shedId : shedId
                            },
                            success:function(response){
                                var result = Ext.util.JSON.decode(response.responseText);
                                if(result.success){
                                    Ext.ux.Toast.msg('系统提示',result.msg);
                                }
                            }
                        });
                    }
	              }]
                },{
                region:'south',
                height:126,
                layout:'column',
                layoutConfig:{columns:2},
                bodyStyle:'padding:10px',
                items:[
                    {
                    title: '手动操作记录',
                    width:'53%',
                    height:170,
                    autoScroll:true,
                    contentEl:'monitorOper'
                },{
                    title: '大棚运行状况',
                    width:'46%',
                    height:170,
                    autoScroll:true,
                    style:'margin-left:10px',
                    contentEl:'monitorLog'
                }]
            }]
        });
        Ext.TaskMgr.start({
            interval: 500, //runs every 1 minute
            run: function(){
            	Ext.Ajax.request({
                    url:'warn!list',
                    success:function(response){
                       	LogMsg(response.responseText);
                  }
                });        
			},
            scope: this
        });
        Ext.applyIf(this, {
            border:false,
            layout:'border',
            closable:true,
            items:[this.monitor_nav,this.monitor_content]
        });
        Long.MonitorPanel.superclass.initComponent.call(this);
    }
});