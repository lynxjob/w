Ext.namespace('Long');
var shedId = 0;
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
                  width:666,
                  items:[{
                    html:"<image src='images/content/pa1.gif' title='大棚内部情境图' alt='大棚内部情境图' usemap='#Map' />" +
					"<map name='Map' id='Map'>" +
					"<area shape='rect' coords='233,264,260,291' title='CO2传感器1号' onclick=alert('建设中...') />" +
					"<area shape='rect' coords='643,280,660,298' title='光照传感器1号' onclick=alert('建设中...') />" +
					"<area shape='rect' coords='67,282,79,299' title='温度传感器1号' onclick=alert('建设中...') />" +
					"<area shape='rect' coords='563,203,598,227' title='摄像机4号' onclick=alert('建设中...') />" +
					"<area shape='rect' coords='571,145,588,174' title='喷水器3号' onclick=alert('喷水器3号现在处于关闭状态，是否打开?') />" +
                    "<area shape='rect' coords='435,94,449,122' title='喷水器2号' onclick=alert('喷水器2号现在处于关闭状态,是否打开?') />" +
                    "<area shape='rect' coords='321,250,347,263' title='摄像机3号' onclick=alert('建设中...') />" +
                    "<area shape='rect' coords='210,130,266,157' title='摄像机1号' onclick=alert('建设中...') />" +
                    "<area shape='rect' coords='187,2,207,37' title='喷水器1号' onclick=alert('喷水器1号现在处于关闭状态,是否打开?') />" +
                    "<area shape='rect' coords='51,221,88,242' title='摄像机2号' onclick=alert('建设中...') />" +
                    "<area shape='rect' coords='195,135,205,161' title='喷水器4号' onclick=alert('喷水器4号现在处于关闭状态,是否打开?') />" +
					"</map>"           
                  }]             
                },{
                region:'center',
                layout:'table',
                layoutConfig: {columns:2},
                bodyStyle:'padding:10px',
                items:[{
                    xtype: 'fieldset',
                    title: '控制操作',
                    width:250,
                    style:'margin:10px',
                    autoHeight: true,
                    items: [
                        {
	                        id:'rb1',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '外光帘',
	                        items: [
	                            {boxLabel: '打开', name: 'rb1-auto',id:'rb1-auto-1', inputValue: 0},
	                            {boxLabel: '收拢', name: 'rb1-auto',id:'rb1-auto-2',inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                }
                            }
                        },{
	                        id:'rb3',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '内光帘',                     
	                        items: [
	                            {boxLabel: '打开', name: 'rb3-auto',id:'rb3-auto-1', inputValue: 0},
	                            {boxLabel: '收拢', name: 'rb3-auto',id:'rb3-auto-2', inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
                                }
                            }
                        },{
	                        id:'rb5',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '外侧窗',
	                        items: [
	                            {boxLabel: '打开', name: 'rb5-auto',id:'rb5-auto-1', inputValue: 0},
	                            {boxLabel: '收拢', name: 'rb5-auto',id:'rb5-auto-2', inputValue: 1}       
	                        ],
                            listeners:{
                                'change':function(radiofield,checked){
                                    var id = this.id.substring(2);
                                    radioChange(id,radiofield,checked);
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
                                }
                            }
                        },{
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
                                    var s=this.id.substring(9);
                                    radioChange(id,radiofield,checked);
                                    if(s==1)
                                    	{
                                    		oper.Message();
                                    	}
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
                                }
                            }
                        }],
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
                    xtype: 'fieldset',
                    title: '控制策略',
                    width:220,
                    height:427,
                    style:'margin:10px',
                    items: [
                        {
                        id:'rb-1',
                        xtype: 'radiogroup',
                        itemCls: 'x-check-group-alt',
                        fieldLabel: '升温',
                        items: [
                            {boxLabel: '开启', name: 'rb1-man',id:'rb1-man-1', inputValue: 0, checked: true},
                            {boxLabel: '关闭', name: 'rb1-man',id:'rb1-man-2', inputValue: 1}       
                        ]
                    },{
	                    id:'rb-2',
	                        xtype: 'radiogroup',
	                        itemCls: 'x-check-group-alt',
	                        fieldLabel: '降温',
	                        items: [
	                            {boxLabel: '开启', name: 'rb2-man',id:'rb2-man-1', inputValue: 0, checked: true},
	                            {boxLabel: '关闭', name: 'rb2-man',id:'rb2-man-2', inputValue: 1}       
	                        ]
                    },{
                        id:'rb-3',
                        xtype: 'radiogroup',
                        itemCls: 'x-check-group-alt',
                        fieldLabel: '加湿',
                        items: [
                            {boxLabel: '开启', name: 'rb3-man',id:'rb3-man-1', inputValue: 0, checked: true},
                            {boxLabel: '关闭', name: 'rb3-man',id:'rb3-man-2', inputValue: 1}       
                        ]
                    },{
                        id:'rb-4',
                        xtype: 'radiogroup',
                        itemCls: 'x-check-group-alt',
                        fieldLabel: '除湿',
                        items: [
                            {boxLabel: '开启', name: 'rb4-man',id:'rb4-man-1', inputValue: 0, checked: true},
                            {boxLabel: '关闭', name: 'rb4-man',id:'rb4-man-2', inputValue: 1}       
                        ]
                    },{
                        id:'rb-5',
                        xtype: 'radiogroup',
                        itemCls: 'x-check-group-alt',
                        fieldLabel: '补光',
                        items: [
                            {boxLabel: '开启', name: 'rb5-man',id:'rb5-man-1', inputValue: 0, checked: true},
                            {boxLabel: '关闭', name: 'rb5-man',id:'rb5-man-2', inputValue: 1}       
                        ]
                    }] 
                },{
	                xtype:'button',
	                text:'手动控制',
	                style:'margin-left:100px',
                    handler:function(){
                        if(shedId <=0 ){
                            Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
                            return;
                        }
                        for(var i=1; i<=13; i++){
                            Ext.getCmp('rb'+i).setDisabled(false);
                        }
                        for(var i=1; i<=5; i++){
                            Ext.getCmp('rb-'+i).setDisabled(false);
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
                         for(var i=1; i<=13; i++){
                            Ext.getCmp('rb'+i).setDisabled(true);
                        }
                        for(var i=1; i<=5; i++){
                            Ext.getCmp('rb-'+i).setDisabled(true);
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
                    xtype: 'fieldset',
                    title: '操作记录',
                    layout:'fit',
                    width:'46%',
                    autoHeight:true,
                    style:'padding:10px;margin-right:10px',
                    contentEl:'oper'
                },{
                    xtype: 'fieldset',
                    title: '异常记录',
                    layout:'fit',
                    width:'46%',
                    autoHeight:true,
                    style:'padding:10px',
                    items:[{html:'异常记录'}]
                }]
            }]
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