Ext.namespace('Long');
//辅助系统
Long.AssistSystemPanel = Ext.extend(Ext.Panel,{
    id:'assistsystempanel',
    title:'辅助系统',
    iconCls:'assist_icon',
    initComponent : function(){
        /*this.nav = new Long.ShedTree({
            region:'west'
        });
        this.tab= new Long.ShedGrid({
            region:'center'
        });*/
        Ext.applyIf(this, {
            border:false,
            layout:'fit',
            closable:true,
            items:[{html:'辅助系统'}]
        });
        console.log('初始化组件：assistsystempanel');
        Long.AssistSystemPanel.superclass.initComponent.call(this);
    }
});
