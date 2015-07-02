Ext.namespace('Long');
//大棚数据信息
Long.DaqGrid = Ext.extend(Ext.grid.GridPanel, {
	id:'daqgrid',
	layout : 'fit',
	baseUrl:'daq!list',
	autoScroll : true,
	viewConfig : {
		forceFit:true
	},
	closable : true,
	loadMask : true,
	stripeRows : true,
	loadMask : {
		msg : '数据加载中.....'
	},
	pageSize : 25,
	startDate:'2012-00-00',
	endDate:'2100-00-00',
	initComponent : function() {
		var self = this;
		this.cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
				header:'CO2浓度',
				dataIndex:'coc'
			},{
				header:'光照强度',
				dataIndex:'guanghe'
			},{
				header:'大气湿度',
				dataIndex:'daqishidu'
			},{
				header:'大气温度',
				dataIndex:'daqiwendu'
			},{
				header:'大气压力',
				dataIndex:'daqiyali'
			},{
				header:'土壤温度',
				dataIndex:'dadiwendu'
			},{
				header:'土壤水分',
				dataIndex:'dadishuifen'
			},{
				header:'风力',
				dataIndex:'fengli'
			},{
				header:'风向',
				dataIndex:'sfengxiang'
			},{
				header:'风速',
				dataIndex:'fengsu'
			},{
				header:'降雨量',
				dataIndex:'jiangyu'
			},{
				header:'蒸发量',
				dataIndex:'zhengfa'
			},{
				header:'采集时间',
				dataIndex:'sampleDate',
				format:'Y-m-d'
			}]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name:'coc',
				type:'float'
			},{
				name:'guanghe',
				type:'float'
			},{
				name:'dadiwendu',
				type:'float'
			},{
				name:'daqishidu',
				type:'float'
			},{
				name:'daqiwendu',
				type:'float'
			},{
				name:'daqiyali',
				type:'float'
			},{
				name:'fengli',
				type:'string'
			},{
				name:'fengsu',
				type:'float'
			},{
				name:'sfengxiang',
				type:'string'
			},{
				name:'jiangyu',
				type:'float'
			},{
				name:'turangshuifen',
				type:'float'
			},{
				name:'zhengfa',
				type:'float'
			},{
				name:'sampleDate',
				type:'string'
			}]);
		this.store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : this.baseUrl
			}),
			reader : new Ext.data.JsonReader({
				totalProperty : 'rowCount',
				root : 'result'
			}, this.record),
			baseParams:{
				startDate:self.startDate,
				endDate:self.endDate
			}
		});
		this.store.load({
			params : {
				start : 0,
				limit : this.pageSize,
				startDate: this.startDate,
				endDate: this.endDate
			},
			callback : function(rs) {
				Ext.getBody().unmask();
				if (!rs || rs.length < 1) {
					Ext.ux.Toast.msg('系统提示','没有找到符合条件的记录！');
				}
			}
		});
		this.bbar = new Ext.PagingToolbar({
			pageSize : this.pageSize,
			store : self.store,
			displayInfo : true,
			displayMsg :'显示第 {0} 条到 {1} 条，共 {2} 条',
			emptyMsg : '没有数据记录'
		});
		this.tbar = new Ext.Toolbar([ {
			text : '刷新',
			icon : 'images/icons/f5.png',
			handler: this.refresh.createDelegate(this)
		}]);
		Long.DaqGrid.superclass.initComponent.call(this);
	},
	refresh : function() {
		this.store.removeAll();
		this.store.reload();
	}
});