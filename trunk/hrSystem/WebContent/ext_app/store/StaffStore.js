Ext.define('FcSVNAdmin.store.StaffStore', {
    extend: 'Ext.data.Store',

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {autoLoad: true};
        me.callParent([Ext.apply({
            storeId: 'StaffStore',
            model: 'Staff',
            proxy: {
                type: 'ajax',
                url: 'get/staff',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});