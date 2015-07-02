Ext.onReady(function(){
	Ext.QuickTips.init();
	var loginForm=new Ext.form.FormPanel({
		defaultType : 'textfield',
		columnWidth:0.75,
		labelAlign: 'right',
		labelWidth:60,
		border : false,
		layout : 'form',
		defaults : {
			style : 'margin:0 0 0 0',
			anchor : '90%,120%'
		},
		items: [{
			name: 'username',
			fieldLabel: '账        号',
			allowBlank: false,
			iconCls:'username',
			blankText: '账号不能为空'
		},{
			name:'password',
			fieldLabel:'密        码',
			allowBlank:false,
			iconCls:'password',
			blankText:'密码不能为空',
			inputType:'password'
		}]
	});
	var form=loginForm.form;
    var loginWin = new Ext.Window({
    	id:'loginWin',
    	iconCls:'loginWin',
    	bodyStyle:'background-color: green',
        title: '用户登录',
        height : 275,
		width : 465,
        closeAction: 'close',
        closable: false,
        resizable : false,
        boder:true,
        frame:true,
        buttonAlign:'center',
        layout: {
			type: 'vbox',
			align: 'stretch'
		},
		//响应回车事件
		keys: {
        	key: [13],
	        fn: function(){
	        	if(form.isValid()==false){
        			return;
        		}      
        		form.submit({
        			waitTitle : '请稍后',
        			waitMsg : '正在提交登录信息···',
        			url : 'manage!login',
        			success : function(form, action) {
        				window.location.href = 'manage';
        			},
        			failure : function(form, action) {
        				Ext.getBody().unmask();
        				Ext.ux.Toast.msg('系统提示', action.result.msg);
        			}
        		});
		 	}
      	},
        items:[{
			xtype : 'panel',
			border : false,
			html : '<img src="images/login/top.jpg" />',
			height : 100
		},{
			xtype : 'panel',
			border : false,
			layout : 'column',
			items : [loginForm, {
				xtype : 'panel',
				border : false,
				columnWidth : 0.25,
				html : '<img src="images/login/users.jpg"/>'
			}]
		}],
        buttons: [{
            text:'登录',
            width:80,
            height:28,
            iconCls : 'login',
            handler:function(){
            	if(form.isValid()==false){
        			return;
        		}
            	Ext.getBody().mask('<img src="images/loading.gif" style="margin-right:8px;" align="absmiddle"/>数据加载中.....');
            	form.submit({
                    waitTitle : '请稍后',
                    waitMsg : '正在提交登录信息···',
                    url : 'manage!login',
                    success : function(form, action) {
                        Ext.getBody().unmask();
                        window.location.href = 'manage';
                    },
                    failure : function(form, action) {
                        Ext.getBody().unmask();
                        Ext.ux.Toast.msg('系统提示', action.result.msg);
                    }
                });
            }
        },{
        	text:'重置',
        	 width:80,
             height:28,
        	iconCls:'reset',
        	handler: function(){
        		form.reset();
        	}
        }]
    });
    loginWin.show();
});