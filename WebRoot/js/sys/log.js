Ext.namespace('Long');
//日志信息
Long.LogGrid = Ext.extend(Long.BaseGeneralGrid, {
	id:'loggrid',
	title:'系统日志',
	iconCls:'log_icon',
	baseUrl:'log!list',
	autoScroll : true,
	viewConfig : {
		forceFit:false
	},
	pageSize : 40,
	initComponent : function() {
		this.cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
				header:'编号',
				dataIndex:'id',
				width : 100
			},{
				header:'用户名',
				dataIndex:'userName',
				width : 100
			},{
				header:'IP地址',
				dataIndex:'ip',
				width : 120
			},{
				header:'操作类型',
				dataIndex:'oType',
				width : 140
			},{
				header:'操作时间',
				dataIndex:'oDate',
				width : 170
			},{
				header:'操作详细信息',
				dataIndex:'oDetail',
				width : 600
			}]);
		this.record = Ext.data.Record.create([{
				name : 'id',
				type : 'int'
			},{
				name:'userName',
				type:'string'
			},{
				name:'ip',
				type:'string'
			},{
				name:'oType',
				type:'string'
			},{
				name:'oDate',
				type:'string'
			},{
				name:'oDetail',
				type:'string'
			}]);
		this.tbar = new Ext.Toolbar([ {
			text : '刷新',
			icon : 'images/icons/f5.png',
			handler: this.refresh.createDelegate(this)
		}]);
		Long.LogGrid.superclass.initComponent.call(this);
	}
});