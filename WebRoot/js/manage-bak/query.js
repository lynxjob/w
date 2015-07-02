Ext.namespace('Long');
//查询平台
Long.QuerySystemPanel = Ext.extend(Ext.Panel,{
    id:'querysystempanel',
    title:'查询平台',
    iconCls:'querysystem_icon',
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
            items:[{html:'查询平台'}]
        });
        Long.QuerySystemPanel.superclass.initComponent.call(this);
    }
});