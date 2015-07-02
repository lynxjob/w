Ext.ux.ThemeChange = Ext.extend(Ext.form.ComboBox, {
	editable : false,
	displayField : 'theme',
	valueField : 'css',
	typeAhead : true,
	mode : 'local',
	triggerAction : 'all',
	selectOnFocus : true,
	initComponent : function() {
		var themes = [
				['默认样式', 'xtheme-green.css'],
				['棕褐样式', 'xtheme-chocolate.css'],
				['深灰样式', 'xtheme-darkgray.css'],
				['灰白样式', 'xtheme-gray.css'],
				['橄榄样式', 'xtheme-olive.css'],
				['靛紫样式', 'xtheme-indigo.css'],
				['墨橙样式', 'xtheme-access.css'],
				['紫色样式', 'xtheme-purple.css']
		];
		this.store = new Ext.data.SimpleStore( {
			fields : ['theme', 'css'],
			data : themes
		});
		this.value ='默认样式';
		Ext.util.CSS.swapStyleSheet('theme', 'ext/resources/css/xtheme-green.css');
	},
	initEvents : function() {
		this.on('collapse', function() {
			Ext.util.CSS.swapStyleSheet('theme', 'ext/resources/css/'+ this.getValue());
			var themeText=this.getRawValue();
			var themeCss=this.getValue();
			//可以给后台发送Ajax请求，保存用户皮肤的信息
		});
	}
});
Ext.reg('themeChange', Ext.ux.ThemeChange);