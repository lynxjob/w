Ext.namespace('Long');
//封装combo
Long.ComboBox1 = Ext.extend(Ext.form.ComboBox,{
	initComponent : function() {
		Ext.apply(this, {
			mode : 'remote',
			triggerAction : 'all',
			valueField : 'value',
			displayField : 'value',
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : this.baseUrl
						}),
				reader : new Ext.data.ArrayReader({},[{
							name : 'value'
						}])
			})
		});
		Long.ComboBox1.superclass.initComponent.call(this);
	}
});
Ext.reg('combobox1', Long.ComboBox1);

Long.ComboBox2 = Ext.extend(Ext.form.ComboBox,{
	initComponent : function() {
		Ext.apply(this, {
			mode : 'remote',
			triggerAction : 'all',
			valueField : 'value',
			displayField : 'text',
			hiddenName : 'value',
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : this.baseUrl
						}),
				reader : new Ext.data.ArrayReader({},[{
							name : 'value'
						},{
							name : 'text'
						}])
				})
			});
		Long.ComboBox2.superclass.initComponent.call(this);
	}
});
Ext.reg('combobox2', Long.ComboBox2);

Long.TreeCombo = Ext.extend(Ext.form.ComboBox, {
	constructor : function(b) {
		var c = {
			store : new Ext.data.SimpleStore({
				fields : [],
				data : [ [] ]
			}),
			editable : false,
			mode : 'local',
			emptyText : '请选择一个目录',
			triggerAction : 'all',
			maxHeight : 200,
			tpl : "<tpl for='.'><div style='height:200px'><div id='" + b.id
					+ "tree'></div></div></tpl>",
			onSelect : Ext.emptyFn
		}
				|| b;
		Ext.apply(this, c);
		Long.TreeCombo.superclass.constructor.call(this, c);
		var a = new Ext.tree.TreePanel({
			id : b.id + 'Tree',
			height : 200,
			autoScroll : true,
			split : true,
			loader : new Ext.tree.TreeLoader({
				url : b.url
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true,
				text:'大棚类型列表',
           	  	id:'0'
			}),
			rootVisible : true
		});
		var d = this;
		d.on('collapse', function(f) {
			var e = a.getSelectionModel().getSelectedNode();
			if (e == null) {
				d.expand();
				return;
			} else {
				a.getSelectionModel().clearSelections();
			}
		}, this);
		a.on('click', function(f) {
			var e = Ext.getCmp(b.valId);
			if (f.id != null && f.id != "") {
				d.setValue(f.text);
				d.id = f.id;
				d.collapse();
				if (e != null) {
					e.setValue(f.id);
				}
			}
		});
		this.on('expand', function() {
			a.render(b.id + "tree");
		});
	}
});
Ext.reg('treecombo',Long.TreeCombo);