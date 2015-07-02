Ext.onReady(function()
    {
        var viewport = new Ext.Viewport({
            layout: 'border',
            items: [
                {
                    region: 'center',
                    title: 'Toast Windows',
                    items: [
                        {
                            xtype: 'button',
                            text: 'showMessage-success',
                            style: 'margin: 20px',
                            listeners: {
                                click: function(){
                                    new Ext.ux.Notification({
                                        autoHide:   false,
                                        hideDelay:  3000//autoHide为true，hideDelay起作用
                                    }).showMessage('成功信息','<h1>操作成功</h1>3s之后自动隐藏窗口',true); 
                                }
                            }
                        },{
                            xtype: 'button',
                            text: 'showMessage-failure',
                            style: 'margin: 20px',
                            listeners: {
                                click: function(){
                                    new Ext.ux.Notification(
                                    ).showMessage('失败信息','<h1>操作失败</h1>需要手动关闭窗口',false); 
                                }
                            }
                        },{
                            xtype: 'button',
                            text: 'showSuccess',
                            style: 'margin: 20px',
                            listeners: {
                                click: function(){
                                    new Ext.ux.Notification({
                                        autoHide:   true,
                                        hideDelay:  10000//autoHide为true，hideDelay起作用
                                    }).showSuccess('无提示版成功信息','<h1>操作成功,不隐藏窗口</h1>根据Notification初始化参数来确定是否隐藏窗口'); 
                                }
                            }
                        },{
                            xtype: 'button',
                            text: 'showFailure',
                            style: 'margin: 20px',
                            listeners: {
                                click: function(){
                                    new Ext.ux.Notification({
                                        autoHide:   true,
                                        hideDelay:  2000
                                    }).showFailure('无提示音版失败信息','<h1>操作失败</h1>2秒隐藏窗口'); 
                                }
                            }
                        }
                    ]
                }
            ]
        });
    });