Ext.ns('Ext.ux.grid');
Ext.ux.grid.CheckColumn = Ext.extend(Ext.grid.Column, {
    processEvent : function(name, e, grid, rowIndex, colIndex){
        if (name == 'mousedown') {
            var record = grid.store.getAt(rowIndex);
            record.set(this.dataIndex, !record.data[this.dataIndex]);
            return false;
        } else {
            return Ext.grid.ActionColumn.superclass.processEvent.apply(this, arguments);
        }
    },
    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td'; 
        return String.format('<div class="x-grid3-check-col{0}">&#160;</div>', v ? '-on' : '');
    },
    init: Ext.emptyFn
});
Ext.preg('checkcolumn', Ext.ux.grid.CheckColumn);
Ext.grid.CheckColumn = Ext.ux.grid.CheckColumn;
Ext.grid.Column.types.checkcolumn = Ext.ux.grid.CheckColumn;
