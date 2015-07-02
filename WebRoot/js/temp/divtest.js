Ext.onReady(function(){
   new Ext.Viewport({
        layout:'border',
        items:[{ 
            xtype:'portal',
            region:'center',
            margins:'35 5 5 0',
            items:[{
                columnWidth:.33,
                style:'padding:10px 0 10px 10px',
                items:[{
                    title: 'Grid in a Portlet',
                    layout:'fit'
                },{
                    title: 'Another Panel 1',
                    html: 'asdfasf'
                }]
            },{id:"abc",
                columnWidth:.33,
                style:'padding:10px 0 10px 10px',
                items:[{
                    title: 'Panel 21',
                    html: 'asdfasf'
                },{
                    title: 'Another Panel 22',
                    html: 'asdfasf'
                }]
            },{id:"ccc",
                columnWidth:.33,
                style:'padding:10px',
                items:[{
                    title: 'Panel 31',
                    html: 'asdfasf'
                },{
                    title: 'Panel 32',
                    html: 'asdfasf'
                },{id:"bb",
                    title: 'Another Panel 33',
                    html: 'asdfasf'
                }]
            }]
        }]
    });

 var pan1 = new Ext.Panel({
        title : 'new panel',
        html : "新panel，不能正常拖动",
        anchor : '100%',
        frame : true,
        collapsible : true,
        draggable : true,
        cls : 'x-portlet'
    });


  Ext.getCmp('abc').add(pan1);
  Ext.getCmp('abc').doLayout();
});
