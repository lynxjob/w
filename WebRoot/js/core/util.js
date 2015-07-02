//公用js function
var jsCache = new Array();
function clickNode(node,event,idtext){
	event.stopEvent();
	if(node.leaf==true){
		var tempId=node.id.split('_');
		var entityClass=tempId[0];
		var entityId=tempId[1];
		var tempGrid=null;
		var tabs=Ext.getCmp(idtext);
		var n = tabs.getItem(entityId);
		if (!n){
			$ImportJs(entityClass, function(entity) {
				n = tabs.add(entity);
				tabs.activate(n);
			},entityId);	
			console.log('初始化组件：'+entityClass+',object:'+n+',id:'+entityId);
		}	
		tabs.setActiveTab(n);
	}
	else{
		node.toggle();
	}
}
function newClass(entityName){
	var object = 'new ' + entityName+'();';
	//从字符串中生成对象
	return eval(object);
}
function $ImportJs(entityName,callback,entityId){
	var temp = jsCache[entityId];
	
	if(temp!=null){

		var entity = newClass(entityName);
		callback.call(this,entity);
		
	}
	else{
		var jsArr = eval('Long.importJS.'+entityId);
		
		ScriptMgr.load({
			scripts:jsArr,
			callback : function() {
				jsCache[entityId] = 0;
				var entity = newClass(entityName);
				callback.call(this,entity);
			}
		});
	}
}

function passwordReset(){
	var pForm = new Ext.form.FormPanel({
		defaultType : 'textfield',
		labelAlign : 'right',
		labelWidth : 60,
		frame : true,
		width : 300,
		items : [{
					fieldLabel : '旧密码',
					width:170,
					inputType : 'password',
					name : 'oldPassword',
					allowBlank: false,
					blankText: '密码不能为空',
					msgTarget: 'side'
				}, {
					fieldLabel : '新密码',
					width:170,
					inputType : 'password',
					name : 'newPassword',
					allowBlank: false,
					blankText: '新密码不能为空',
					msgTarget: 'side'
				}, {
					fieldLabel : '确认密码',
					width:170,
					inputType : 'password',
					name : 'newConfirm',
					allowBlank: false,
					blankText: '确认密码不能为空',
					msgTarget: 'side'
				}]
	});
	var win = new Ext.Window({
		id : 'passwordResetWin',
		title : '密码修改',
		width : 300,
		height : 160,
		border : false,
		modal : true,
		closable : false,
		resizable : false,
		closeAction : 'close',
		buttonAlign : 'center',
		items:[pForm],
        buttons: [{
            	text:'确定',
            	icon : 'images/icons/ok.png',
            	handler:function(){
            		var form=pForm.form;
            		if(form.isValid()){
            			var newpassword = form.findField('newPassword').getValue();
            			var newconfirm = form.findField('newConfirm').getValue();
            			if (newpassword != newconfirm) {
            				Ext.ux.Toast.msg('系统提示', '新密码与确认密码不相同！');
            				return;
            			}
            			form.submit({
            				waitTitle : '请稍候',
            				waitMsg : '正在修改密码···',
            				url : 'manage!passwordReset',
            				success : function(form, action) {
            					Ext.ux.Toast.msg('系统提示',action.result.msg);
            					Ext.getCmp('passwordResetWin').close();
            				},
            				failure : function(form, action) {
            					Ext.ux.Toast.msg('系统提示',action.result.msg);
            				}
            			});			
            		}
            	}
        	},{
        		text:'取消',
        		icon : 'images/icons/cancel.png',
        		handler:function(){
        			win.close();
        		}
        	}]
    });
    win.show();
}
function logout(){
	Ext.Msg.confirm('系统提示','确定需要退出系统？',function(btn){
		if(btn == 'yes'){
			Ext.Ajax.request({
				url : 'manage!logout',
				success : function(response,options) {
					var respText = Ext.util.JSON.decode(response.responseText);
//					Ext.ux.Toast.msg('系统提示',respText.msg);
					window.location.href = 'login.jsp';
				},
				failure : function(response, options) {
                    var respText = Ext.util.JSON.decode(response.responseText);
					Ext.ux.Toast.msg('系统提示',respText.errors.msg);
				}	
			});
		}
	});
}