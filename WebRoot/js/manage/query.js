Ext.namespace('Long');
//查询平台
Long.QuerySystemPanel = Ext.extend(Ext.Panel,{
    id:'querysystempanel',
    title:'查询平台',
    iconCls:'querysystem_icon',
    initComponent : function(){
    	var self = this;
    	this.daqGrid = new Long.DaqGrid({
			region:'center'
		});
    Ext.applyIf(this, {
        border:false,
        layout:'border',
        closable:true,
        frame:true,
        items:[{
		region:'north',  
		layout:'column',
        labelAlign:'right',
        labelWidth:120,
        items:[{
        	xtype:'label',
        	 text:'开始时间:',
        	 style:'margin-left:10px'
        },{
			xtype:'datefield',
            id:'startDate',
            name:'startDate',
            width:190,
            format:'Y-m-d',
            allowBlank:false,
            blankText:'请设置查询开始时间!'
        },{
        	xtype:'label',
        	 text:'结束时间:',
        	 style:'margin-left:10px'
        },{
            xtype:'datefield',
            id:'endDate',
            name:'endDate',
            width:190,
            format:'Y-m-d',
            allowBlank:false,
            blankText:'请设置查询结束时间!'
        },{
        	xtype:'button',
        	text:'查询',
        	style:'margin-left:15px',
        	handler:function(){
        	var startDate = Ext.getCmp('startDate').getValue();
        	var endDate = Ext.getCmp('endDate').getValue();
        	if(startDate==null || endDate==null){
				Ext.ux.Toast.msg('系统提示','请输入查询时间!');
				return ;
        	}
        	startDate = startDate.format('Y-m-d');
        	endDate = endDate.format('Y-m-d');
        	var store = self.daqGrid.getStore();
        	store.setBaseParam('startDate',startDate);
        	store.setBaseParam('endDate',endDate);
        	store.load({
				params : {
					start:0,
              		limit:25,
              		startDate:startDate,
              		endDate:endDate
				},
				callback : function(rs) {
					Ext.getBody().unmask();
					if (!rs || rs.length < 1) {
						Ext.ux.Toast.msg('系统提示','没有找到符合条件的记录！');
					}
				}
			});
        }
        }]},
        this.daqGrid]
        });
        Long.QuerySystemPanel.superclass.initComponent.call(this);
    }
});