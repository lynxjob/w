Ext.namespace('Long');
//公用父类，普通表格
Long.BaseGeneralGrid = Ext.extend(Ext.grid.GridPanel, {
	pageSize : 30,
	layout : 'fit',
	closable : true,
	loadMask : true,
	stripeRows : true,
	viewConfig : {
		forceFit : true
	},
	loadMask : {
		msg : '数据加载中.....'
	},
	closable : true,
	initComponent : function() {
		Ext.getBody().mask('数据加载中.....', 'x-mask-loading');
		this.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : this.baseUrl
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'rowCount',
					root : 'result'
				}, this.record)
			});
		this.contextmenu = new Ext.menu.Menu({
			items : [{
					text : '刷新',
					icon : 'images/icons/f5.png',
					handler: this.refresh.createDelegate(this)
				},{
					text : '重置列',
					icon : 'images/icons/column.png',
					handler : this.resetColumn.createDelegate(this)
				}]
		});
		this.bbar = new Ext.PagingToolbar({
			pageSize : this.pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg :'显示第 {0} 条到 {1} 条，共 {2} 条',
			emptyMsg : '没有数据记录'
		});
		this.store.load({
			params : {
				start : 0,
				limit : this.pageSize
			},
			callback : function(rs) {
				Ext.getBody().unmask();
				if (!rs || rs.length < 1) {
					Ext.ux.Toast.msg('系统提示','没有找到符合条件的记录！');
				}
			}
		});
		this.on('contextmenu', function(e) {
			e.preventDefault();
			this.contextmenu.showAt(e.getXY());
		});
		Long.BaseGeneralGrid.superclass.initComponent.call(this);
	},
	getSelectedRecord : function() {
		var rows = this.getSelectionModel().getSelections();
		if (rows.length == 0) {
			return false;
		} else {
			return rows;
		}
	},
	getSelectedIds : function() {
		var rows = this.getSelectedRecord();
		if (rows != false) {
			var ids = new Array();
			Ext.each(rows, function(item) {
				ids.push(item.data.id);
			});
			return Ext.encode(ids);
		} 
		else
			return false;
	},
	refresh : function() {
		this.store.removeAll();
		this.store.reload();
	},
	resetColumn : function(){
		var cms = this.getColumnModel();
		if (cms.getColumnCount() == cms.getColumnCount(true)) {
			Ext.ux.Toast.msg('系统提示', '所有列已显示！');
			return;
		}
		for (var i = 1; i < cms.getColumnCount(); i++) {
			cms.setHidden(i, false);
		}
		Ext.ux.Toast.msg('系统提示', '重置列完成！');
	}
});