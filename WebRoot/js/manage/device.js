Ext.namespace('Long');
//设备管理
var shedId = 0;
Long.DeviceManagePanel = Ext.extend(Ext.Panel,{
    id:'devicemanagepanel',
    title:'设备管理',
    iconCls:'device_icon',
    initComponent : function(){
        this.nav = new Long.ShedTree({
            id:'device_shedtree',
            region:'west',
            listeners:{
                'click':function(node,event) {
                    Ext.getCmp('device_content').setTitle('设备管理['+node.text+']');
                    shedId = node.id;
                }
            }
        });
        this.tab= new Ext.Panel({
            id:'device_content',
            region:'center',
            title:'设备管理',
            layout:{
                type:'hbox',
                align:'stretch'
            },
            items:[{
            xtype:'panel',
            title:'采集仪基本设置',
            flex:1,
            layout:{
                type:'vbox',
                align:'stretch'
            },
            items:[{
                xtype:'panel',
                style:'padding:50px',
                html:'<img src="images/device/lvcjy01.jpg" />',
                flex:3
            },{
                xtype:'form',
                id:'daqform',
                flex:2,
                border:false,
                defaultType:'textfield',
                labelAlign:'right',
                labelWidth:120,
                bodyStyle:'padding:5px',
                buttonAlign:'center',
                items:[{
                    fieldLabel:'串口数据同步周期<br/>(单位:毫秒)',
                    name:'property1',
                    width:190,
                    style:'margin:5px 10px 10px 10px',
                    allowBlank:false,
                    blankText:'请设置串口数据同步周期!'
                },{
                    fieldLabel:'数据库同步周期 <br/>(单位:毫秒)',
                    name:'property2',
                    width:190,
                    style:'margin:5px 10px 10px 10px',
                    allowBlank:false,
                    blankText:'请设置数据库同步周期!'
                },{
                    fieldLabel:'缓冲区最大长度',
                    name:'property3',
                    width:190,
                    style:'margin:0 10px 10px 10px', 
                    allowBlank:false,
                    blankText:'请设置缓冲区大小!'
                }],
                buttons:[{
                    text:'载入设置',
                    style:'margin-right:20px',
                    handler:function(){
                       var daq_form = Ext.getCmp('daqform').getForm();
                       if(daq_form == 'undefined' || daq_form == null){
                        return;
                       }
                       if(shedId == 0){
                        Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
                        return;
                       }
                       daq_form.load({
                        url:'device!load',
                        params:{
                          shed_id:shedId,
                          device_type:1
                        },
                        success : function(response){
                            Ext.ux.Toast.msg('系统提示', '采集仪设置载入成功!');
                        },
                        failure : function(response) {
                            Ext.ux.Toast.msg('系统提示', '采集仪设置载入失败!');
                        }
                       });
                   }
                },{
                    text:'应用设置',
                    style:'margin-left:20px',
                    handler:function(){
                        var daq_form = Ext.getCmp('daqform').getForm();;
                        if(daq_form.isValid()==false){
                            return;
                        }
                        if(shedId == 0){
	                        Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
	                        return;
                        }
                        daq_form.submit({
                            waitTitle : '请稍后',
                            waitMsg : '正在提交信息···',
                            url : 'device!saveOrupdate',
                            params:{
                               shed_id:shedId,
                               device_type:1 
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
            },{
                xtype:'panel',
                border:false,
                flex:1
            }]
            },{
            xtype:'panel',
            title:'摄像头基本设置',
            flex:1,
            layout:{
                type:'vbox',
                align:'stretch'
            },
            items:[{
                xtype:'panel',
                style:'padding:50px',
                html:'<img src="images/device/lvcjy02.jpg" />',
                flex:3
            },{
                xtype:'form',
                id:'videoform',
                flex:2,
                border:false,
                defaultType:'textfield',
                labelAlign:'right',
                labelWidth:120,
                buttonAlign:'center',
                bodyStyle:'padding:5px',
                items:[{
                    fieldLabel:'刻录机IP',
                    name:'property1',
                    width:190,
                    style:'margin:0 0 5px 10px',
                    allowBlank:false,
                    blankText:'请设置刻录机IP!'
                },{
                    fieldLabel:'端口号',
                    name:'property2',
                    width:190,
                    style:'margin:0 0 5px 10px',
                    allowBlank:false,
                    blankText:'请设置刻录机端口号!'
                },{
                    fieldLabel:'注册账户',
                    name:'property3',
                    width:190,
                    style:'margin:0 0 5px 10px', 
                    allowBlank:false,
                    blankText:'请设置注册账户!'
                },{
                    inputType:'password',
                    fieldLabel:'账户密码',
                    name:'property4',
                    width:190,
                    style:'margin:0 0 5px 10px',
                    allowBlank:false,
                    blankText:'请设置账户密码!'
                }],
                buttons:[{
                    text:'载入设置',
                    style:'margin-right:20px',
                    handler:function(){
                       var video_form = Ext.getCmp('videoform').getForm();
                       if(video_form == 'undefined' || video_form == null){
                        return;
                       }
                       if(shedId == 0){
                        Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
                        return;
                       }
                        video_form.load({
                        url:'device!load',
                        params:{
                          shed_id:shedId,
                          device_type:2
                        },
                        success : function(response){
                            Ext.ux.Toast.msg('系统提示', '摄像头设置载入成功!');
                        },
                        failure : function(response) {
                            Ext.ux.Toast.msg('系统提示', '摄像头设置载入失败!');
                        }
                       });
                   }
                },{
                    text:'应用设置',
                    style:'margin-left:20px',
                    handler:function(){
                        var video_form = Ext.getCmp('videoform').getForm();;
                        if(video_form.isValid()==false){
                            return;
                        }
                        if(shedId == 0){
                            Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
                            return;
                        }
                        video_form.submit({
                            waitTitle : '请稍后',
                            waitMsg : '正在提交信息···',
                            url : 'device!saveOrupdate',
                            params:{
                               shed_id:shedId,
                               device_type:2 
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
            },{
                xtype:'panel',
                flex:1,
                border:false
            }]
            },{
            xtype:'panel',
            title:'系统设置',
            flex:1,
            layout:{
                type:'vbox',
                align:'stretch'
            },
            items:[{
                xtype:'panel',
                style:'padding:50px',
                html:'<img src="images/device/lvcjy03.jpg" />',
                flex:3
            },{
                xtype:'form',
                id:'systemform',
                flex:2,
                border:false,
                defaultType:'textfield',
                labelAlign:'right',
                labelWidth:120,
                buttonAlign:'center',
                bodyStyle:'padding:5px',
                items:[{
                    fieldLabel:'采集仪端口号',
                    name:'property1',
                    width:190,
                    style:'margin:0 0 5px 10px',
                    allowBlank:false,
                    blankText:'请设置采集仪端口号!'
                },{
                    fieldLabel:'继电器端口号',
                    name:'property2',
                    width:190,
                    style:'margin:0 0 5px 10px',
                    allowBlank:false,
                    blankText:'请设置采集仪端口号!'
                },{
                    fieldLabel:'LED端口号',
                    name:'property3',
                    width:190,
                    style:'margin:0 0 5px 10px',
                    allowBlank:false,
                    blankText:'请设置LED端口号!'
                }],
                buttons:[{
                    text:'载入设置',
                    style:'margin-right:20px',
                    handler:function(){
                       var sys_form = Ext.getCmp('systemform').getForm();
                       if(sys_form == 'undefined' || sys_form == null){
                        return;
                       }
                       if(shedId == 0){
                        Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
                        return;
                       }
                       sys_form.load({
                        url:'device!load',
                        params:{
                          shed_id:shedId,
                          device_type:0
                        },
                        success : function(response){
                            Ext.ux.Toast.msg('系统提示', '系统设置载入成功!');
                        },
                        failure : function(response) {
                            Ext.ux.Toast.msg('系统提示', '系统设置载入失败!');
                        }
                       });
                   }
                },{
                    text:'应用设置',
                    style:'margin-left:20px',
                    handler:function(){
                        var sys_form = Ext.getCmp('systemform').getForm();;
                        if(sys_form.isValid()==false){
                            return;
                        }
                        if(shedId == 0){
                            Ext.ux.Toast.msg('系统提示','请选择相应大棚!');
                            return;
                        }
                        sys_form.submit({
                            waitTitle : '请稍后',
                            waitMsg : '正在提交信息···',
                            url : 'device!saveOrupdate',
                            params:{
                               shed_id:shedId,
                               device_type:0 
                            },
                            scope : this,
                            success : function(form, action) {
                                Ext.ux.Toast.msg('系统提示',action.result.msg);
                            },
                            failure : function(form, action) {
                                Ext.ux.Toast.msg('系统提示', '系统设置应用失败!');
                            }
                        })
                    }
                }]
            },{
                xtype:'panel',
                flex:1,
                border:false
            }]
          }]
        });
        Ext.applyIf(this, {
            border:false,
            layout:'border',
            closable:true,
            items:[this.nav,this.tab]
        });
        Long.DeviceManagePanel.superclass.initComponent.call(this);
    }
});