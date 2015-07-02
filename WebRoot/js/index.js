Ext.onReady(function(){
    Ext.BLANK_IMAGE_URL = 'ext/resources/images/default/s.gif';
	Ext.QuickTips.init();
    
	/*IE->fieldset兼容问题*/
     if (Ext.isIE) {
        Ext.select('.x-fieldset-body').setHeight(95);
        Ext.select('.x-fieldset-bwrap').setStyle( {
            'position' : 'relative',
            'top' : '-5px',
            'height' : '95px'
        });
    };
  /*  //右下角信息显示窗口
         var tips = new Ext.ux.tipsWin({
            width:200,
            height:280,
            html:'asfdaf'
          });
          tips.show();*/
    var menuTemp= new Ext.tree.TreePanel({
		id:'menuTemp',
        loader: new Ext.tree.TreeLoader({
        	dataUrl: 'auth!loadMenus'
        	}),
        title:'系统菜单',
        iconCls:'menu_icon',
        rootVisible:false,
        region:'west',
        split:true,
        border:false,
        collapsible:true,
        listeners : {
			'click': function(node, event) {
                clickNode(node,event,'centertabs');
            }
		}
    });
    var menuTempRoot = new Ext.tree.AsyncTreeNode({text:'系统菜单'});
    menuTemp.setRootNode(menuTempRoot);
    menuTempRoot.expand();
    
    var tabs = new Ext.TabPanel({
    	id:'centertabs',
    	activeTab : 0, 
    	region: 'center',
    	animScroll : true, 
    	enableTabScroll : true,
    	border : false,
    	collapsible : false,
    	closable : false,
    	autoScroll : true,
    	plugins: new Ext.ux.TabCloseMenu(),
    	items : [ {
    		title : '管理首页',
    		iconCls:'home_icon',
    		html : '<br/><center><image src="images/content/pa2.gif" width="760" height="559" border="0" alt="大棚俯视图"/></center>'
    	} ]
    });
    
    var bottom=new Ext.Panel({
		border : false,
		height : 28,
		bbar :[
		    '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
		    '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
		    '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
		    '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
		    '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
		    'Copyright &copy;2012 精准农业示范项目','->',{
			text:'收展',
			icon : 'images/icons/a_home.png',
			handler:function(){
				var panel = Ext.getCmp('toppanel');
				if (panel.collapsed) {
					panel.expand(true);
				} else {
					panel.collapse(true);
				}
			}
		},'|',{
			xtype : 'themeChange',
			width : 80
		}]
	});
    
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{
        	id:'toppanel',
        	xtype:'panel',
            region: 'north',
			contentEl:'top',
			//导航栏高度
			height: 85
        },{
        	title:'导航',
        	iconCls:'navi_icon',
        	width: 190,
            minSize: 150,
            maxSize: 250,
			collapsible : true,
			split :true,
			border:false,
			layout : {
				type : 'accordion',
				animate : true
			},
        	region:'west',
        	items:[menuTemp]
        },{
            region: 'center',
            split: true,
            border:false,
            layout: 'border',
            items: [tabs]
        },{
			region:'south',
			height : 28,
			border : false,
			items: [bottom]
		}]
    });
   
});

/**
 * 显示日期，周*
 */
function showDate(){
	var weekArray = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];  
	var date=new Date();
	var year = date.getFullYear();//年
    var month = date.getMonth() + 1;//月
    month=month<10?'0'+month : month;
    var day = date.getDate();//日
    day=day<10?'0'+day:day;
    var week=weekArray[date.getDay()];
    document.getElementById('rDate').innerHTML = year + '-' + month + '-' + day+' '+week;
}
/**
 * 显示系统时间，每1秒更新一次
 */
function showTime() {
	var date = new Date();
	var hour = date.getHours();
	hour = hour < 10 ? '0' + hour : hour;
	var minute = date.getMinutes();
	minute = minute < 10 ? '0' + minute : minute;
	var second = date.getSeconds();
	second = second < 10 ? '0' + second : second;
	document.getElementById('rTime').innerHTML = hour + ':' + minute + ':' + second;
}
/**
 * index.html页面载入后运行
 */
window.onload = function() {
	showDate();
	setInterval("showTime()", 1000);
 /* if ((typeof Range !== "undefined") && !Range.prototype.createContextualFragment) {
        Range.prototype.createContextualFragment = function (html) {
        var frag = document.createDocumentFragment(),
        div = document.createElement("div");
        frag.appendChild(div);
        div.outerHTML = html;
        return frag;
    };
 }*/
};