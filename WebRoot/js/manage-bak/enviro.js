Ext.namespace('Long');
//环境监测
Long.EnviroMonPanel = Ext.extend(Ext.Panel,{
    id:'enviromonpanel',
    title:'环境监测',
    iconCls:'enviromon_icon',
    initComponent : function(){
        this.enviro_nav = new Long.ShedTree({
            id:'enviro_shedtree',
            region:'west',
            listeners:{
                'click': function(node,event) {
                    Ext.getCmp('enviro_content').setTitle('环境监测['+node.text+']');
                }
            }
        });
        
        //create some portlet tools using built in Ext tool ids
		var tools = [{
		    id:'gear',
		    handler: function(){
		        Ext.Msg.alert('系统设置', '此模块处于开发中...');
		      }
		    },{
		    id:'close',
		    handler: function(e, target, panel){
		        panel.ownerCt.remove(panel, true);
		      }
		}];
		//getData()
		/**
		 * * 'coc','dadiwendu','daqishidu','daqiwendu','daqiyali','guanghe','jiangyu',
		 * 'turangshuifen','zhengfa','fengli','sfengxiang','sampleDate'
		 */
		var getData = (function(){
		            var data = [];
		        return function(){
		            data = data.slice();
		            if(data.length > 7){
		               data.shift();
		            }
		            Ext.Ajax.request({
		                url:'enviro!loadDaqData',
		                success:function(response){
		                    var msg = Ext.util.JSON.decode(response.responseText);
		                    /*if(false == msg.success){
		                    Ext.ux.Toast.msg('系统提示',msg.msg);
		                    }else{
		                        data.push(msg);
		                    }*/
		                    data.push(msg);
		                }
		            })
		            return data;
		        }
		  })();
		//store
		var store = new Ext.data.JsonStore({
		    fields:['sampleDate','coc','fcoc','daqishidu','fdaqishidu','turangshuifen','fturangshuifen','guanghe','fguanghe','daqiwendu','fdaqiwendu','dadiwendu','fdadiwendu'],
		    data:getData()
		  });
		//temp data
		var intr;
    this.enviro_content= new Ext.Panel({
        title:'环境监测',
        id:'enviro_content',
        layout:'column',
        region:'center',
        autoScroll:true,
        items:[
            {
            xtype:'portal', 
            border:false,
            items:[{
                    columnWidth:.33,
                    style:'padding:10px 0 10px 0',
                    items:[{
                        title: 'Co2浓度实时曲线',
                        width:400,
                        height:300,
                        tools: tools,
                        items:[
                            {
					            xtype:'linechart',
					            store:store,    
					            series:[{  
					                    type:'line',  
					                    displayName:'监测数据',  
					                    xField:'sampleDate',
					                    yField:'coc',
					                    style: {  
					                      color:0x99BBE8,  
					                      size: 5  
					                    }  
					                 },{  
					                    type:'line',  
					                    displayName:'阀值',  
					                    xField:'sampleDate',
					                    yField:'fcoc', 
					                    style: {  
					                      color: 0x15428B  
					                    }  
					                }],
					            xAxis: new Ext.chart.CategoryAxis({
					                title: '采集时间'
					            }),
					            yAxis: new Ext.chart.NumericAxis({
					                title: '实时数据'
					            })
                        }]
                    },{
                        title: '光照强度实时曲线',
                        tools: tools,
                        width:400,
                        height:300,
                        items:[
                            {
                                xtype:'linechart',
                                store:store,    
                                series:[{  
                                        type:'line',  
                                        displayName:'监测数据',  
                                        xField:'sampleDate',
                                        yField:'guanghe',
                                        style: {  
                                          color:0x99BBE8,  
                                          size: 5  
                                        }  
                                     },{  
                                        type:'line',  
                                        displayName:'阀值',  
                                        xField:'sampleDate',
                                        yField:'fguanghe', 
                                        style: {  
                                          color: 0x15428B  
                                        }  
                                    }],
                                xAxis: new Ext.chart.CategoryAxis({
                                    title: '采集时间'
                                }),
                                yAxis: new Ext.chart.NumericAxis({
                                    title: '实时数据'
                                })
                        }]
                    }]
                },{
                    columnWidth:.33,
                    style:'padding:10px 0 10px 10px',
                    items:[{
                        title: '大气湿度实时曲线',
                        tools: tools,
                        width:400,
                        height:300,
                        items:[
                            {
                                xtype:'linechart',
                                store:store,    
                                series:[{  
                                        type:'line',  
                                        displayName:'监测数据',  
                                        xField:'sampleDate',
                                        yField:'daqishidu',
                                        style: {  
                                          color:0x99BBE8,  
                                          size: 5  
                                        }  
                                     },{  
                                        type:'line',  
                                        displayName:'阀值',  
                                        xField:'sampleDate',
                                        yField:'fdaqishidu', 
                                        style: {  
                                          color: 0x15428B  
                                        }  
                                    }],
                                xAxis: new Ext.chart.CategoryAxis({
                                    title: '采集时间'
                                }),
                                yAxis: new Ext.chart.NumericAxis({
                                    title: '实时数据'
                                })
                            }]
                    },{
                        title: '大气温度实时曲线',
                        tools: tools,
                        width:400,
                        height:300,
                        items:[
                            {
                                xtype:'linechart',
                                store:store,    
                                series:[{  
                                        type:'line',  
                                        displayName:'监测数据',  
                                        xField:'sampleDate',
                                        yField:'daqiwendu',
                                        style: {  
                                          color:0x99BBE8,  
                                          size: 5  
                                        }  
                                     },{  
                                        type:'line',  
                                        displayName:'阀值',  
                                        xField:'sampleDate',
                                        yField:'fdaqiwendu', 
                                        style: {  
                                          color: 0x15428B  
                                        }  
                                    }],
                                xAxis: new Ext.chart.CategoryAxis({
                                    title: '采集时间'
                                }),
                                yAxis: new Ext.chart.NumericAxis({
                                    title: '实时数据'
                                })
                            }]
                    }]
                },{
                    columnWidth:.33,
                    style:'padding:10px',
                    items:[{
                        title: '土壤水分实时曲线',
                        tools: tools,
                        width:400,
                        height:300,
                        items:[
                            {
                                xtype:'linechart',
                                store:store,    
                                series:[{  
                                        type:'line',  
                                        displayName:'监测数据',  
                                        xField:'sampleDate',
                                        yField:'turangshuifen',
                                        style: {  
                                          color:0x99BBE8,  
                                          size: 5  
                                        }  
                                     },{  
                                        type:'line',  
                                        displayName:'阀值',  
                                        xField:'sampleDate',
                                        yField:'fturangshuifen', 
                                        style: {  
                                          color: 0x15428B  
                                        }  
                                    }],
                                xAxis: new Ext.chart.CategoryAxis({
                                    title: '采集时间'
                                }),
                                yAxis: new Ext.chart.NumericAxis({
                                    title: '实时数据'
                                })
                            }]
                    },{
                        title: '土壤温度实时曲线',
                        tools: tools,
                        width:400,
                        height:300,
                        items:[
                            {
                                xtype:'linechart',
                                store:store,    
                                series:[{  
                                        type:'line',  
                                        displayName:'监测数据',  
                                        xField:'sampleDate',
                                        yField:'dadiwendu',
                                        style: {  
                                          color:0x99BBE8,  
                                          size: 5  
                                        }  
                                     },{  
                                        type:'line',  
                                        displayName:'阀值',  
                                        xField:'sampleDate',
                                        yField:'fdadiwendu', 
                                        style: {  
                                          color: 0x15428B  
                                        }  
                                    }],
                                xAxis: new Ext.chart.CategoryAxis({
                                    title: '采集时间'
                                }),
                                yAxis: new Ext.chart.NumericAxis({
                                    title: '实时数据'
                                })
                            }]
                    }]  
            }]
          }],
       bbar:[
        {
            id:'opentime',
            xtype:'button',
            text:'开启定时器',
            handler:function(){
                Ext.Ajax.request({
                    url:'enviro!startTimer',
                    success:function(response){
                        intr = setInterval(function(){
                            var gs = getData();
                            store.loadData(gs);
                        }, 1000); 
                        var msg = Ext.util.JSON.decode(response.responseText);
                        Ext.ux.Toast.msg('系统提示',msg.msg);
                  },
                  failure:function(response){
                    Ext.ux.Toast.msg('系统提示','开启定时器失败!');
                  }
                });
               Ext.getCmp('opentime').setDisabled(true);
             }
        },
        {
	        xtype:'button',
	        text:'关闭定时器',
	        handler:function(){
                Ext.Ajax.request({
                    url:'enviro!stopTimer',
                    success:function(response){
                        clearInterval(intr);
                        var msg = Ext.util.JSON.decode(response.responseText);
                        Ext.ux.Toast.msg('系统提示',msg.msg);
                  }
                });
                Ext.getCmp('opentime').setDisabled(false);
	        }
        }]   
    });
    Ext.applyIf(this, {
        border:false,
        layout:'border',
        closable:true,
        items:[this.enviro_nav,this.enviro_content]
    });
    Long.EnviroMonPanel.superclass.initComponent.call(this);
}
});