Ext.namespace('Long');
//大棚列表树形(不带复选框)
Long.ShedTree = Ext.extend(Ext.tree.TreePanel,{
    title:'大棚列表',
    width:180,
    minSize:150,
    maxSize:200,
    split :true,
    border:false,
    collapsible:true,
    rootVisible:true,
    autoScroll: true,
    enableDD: false,
    initComponent : function(){
        this.root=new Ext.tree.AsyncTreeNode({
            id:'0',
            text:'全部大棚',
            expanded : true
        });
        this.loader=new Ext.tree.TreeLoader({
             dataUrl: 'shed!load'
        });
        //防止多次加载时出现mask不消失的情况
        Ext.getBody().unmask();
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
        Long.ShedTree.superclass.initComponent.call(this);
    }
});