Ext.namespace('Long');
//大棚列表树形(带复选框)
Long.UserTree = Ext.extend(Ext.tree.TreePanel,{
    id:'usertree',
    title:'大棚列表',
    width:180,
    minSize:150,
    maxSize:200,
    split :true,
    border:false,
    collapsible : true,
    rootVisible:true,
    autoScroll: true,
    enableDD: false,
    listeners : {
        'click': function(node,event) {
            var tempGrid=Ext.getCmp('usergrid');
            var tempStore=tempGrid.getStore();
            tempGrid.setTitle('大棚用户['+node.text+']');
            tempStore.baseParams = {'shedId' :node.id};
            tempStore.removeAll();
            tempStore.reload({
                params : {
                    start : 0,
                    limit : 30
                },
                callback : function(rs) {
                    if (!rs || rs.length < 1) {
                        Ext.ux.Toast.msg('系统提示','没有找到符合条件的记录！');
                    }
                }
            }); 
       }
    },
    initComponent : function(){
        this.root=new Ext.tree.AsyncTreeNode({
            id:'0',
            text:'全部大棚',
            expanded : true
        });
        this.loader=new Ext.tree.TreeLoader({
             dataUrl: 'shed!list'
         });
        this.tbar=new Ext.Toolbar([{
                text : '刷新',
                icon:'images/icons/f5.png',
                scope : this,
                handler : function() {
                    this.root.reload();
                }
             },{
                text:'展开',
                icon:'images/icons/expand.png',
                scope : this,
                handler : function() {
                    this.expandAll();
                }
             },{
                text:'收起',
                icon:'images/icons/collapse.png',
                scope : this,
                handler : function() {
                    this.collapseAll();
                }
             }]);
        Long.UserTree.superclass.initComponent.call(this);
    }
});
