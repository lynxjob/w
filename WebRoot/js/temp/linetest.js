Ext.onReady(function(){
  //getData()
  var getData = (function(){
            var data = [];
            var i = 0;
            var date = new Date();
            date = date.format('Y-m-d H:i:s');
        return function(){
            data = data.slice();
            if(data.length >= 8){
               data.shift();
            }
            data.push(
	            {currenttime:new Date().format('H:i:s'), visits: Math.random()*100, views: Math.random()*1000,vector:Math.random()*500}	      
            );
            return data;
        }
  })();
  //store
  var store = new Ext.data.JsonStore({
    fields:['currenttime','visits','views','vector'],
    data:getData()
  });
  //temp data
   var intr;
  //panel
  new Ext.Panel({
    title:'lineChart',
    renderTo:Ext.getBody(),
    width:500,
    height:300,
    layout:'fit',
    //linechart items
    items:[
	        {
		        xtype:'linechart',
		        store:store,	
		        listeners:{
		            itemclick:function(o){
		                var rec = store.getAt(o.index);
		                Ext.Msg.alert('Select','You chose '+rec.get('name'));
		            }
		        },          
			series:[{  
					type:'line',  
					displayName:'事件个数',  
					xField:'currenttime',
                    yField:'visits',
					style: {  
					  color:0x99BBE8,  
					  size: 5  
					}  
			     },{  
					type:'line',  
					displayName:'告警',  
					xField:'currenttime',
                    yField:'vector', 
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
            }
            /*extraStyle:{   
				  dataTip:   
						{   
						  border: { color: 0x2e434d, size: 2 },   
						  font: { name: "Arial Black", size: 12, color: 0x000000 }   
					}, //提示框显示字体样式   
				  legend: {   
						  display: "bottom",   
						  padding: 5,   
						  spacing: 2,   
						  font: {   
						  color: 0x000000, family: "Arial", size: 12   
					  },   
				  border: {   
				          size: 1, color: 0x999999   
				     }   
                }   

	       }*/
       ],
    bbar:[
            {
	            xtype:'button',
	            text:'开启定时器',
	            handler:function(){
	                intr = setInterval(function(){
                        var gs = getData();
				        store.loadData(gs);
				    }, 1000);
	            }
            },
            {
                xtype:'button',
                text:'关闭定时器',
                handler:function(){
                    clearInterval(intr);
                }
            }
        ]   
  })
});
