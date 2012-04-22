

Ext.define('FcSVNAdmin.view.ui.StaffPanl', {
	extend : 'Ext.grid.Panel',

	height : 400,
	width : 1000,
	title : '员工人事资料',

	initComponent : function() {
		var me = this;
	    
		Ext.applyIf(me, {
			columns : [ {
				xtype : 'gridcolumn',
				dataIndex : 'path',
				text : 'Path',
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'user',
				text : 'UserId',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'name',
				text : '姓名',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'authz',
				text : '权限',
				width : 100
			} ],
			viewConfig : {
				forceFit : true
			}
		});

		me.callParent(arguments);
	}
});
