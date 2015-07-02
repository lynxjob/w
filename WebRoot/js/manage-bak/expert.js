Ext.namespace('Long');
//专家系统
Long.ExpertSystemPanel = Ext.extend(Ext.Panel,{
    id:'expertsystempanel',
    title:'专家系统',
    iconCls:'expertsystem_icon',
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
            items:[{html:'专家系统'}]
        });
        console.log('初始化组件：expertsystempanel');
        Long.ExpertSystemPanel.superclass.initComponent.call(this);
    }
});