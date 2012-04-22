

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
				dataIndex : 'id',
				text : 'id',
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'name',
				text : 'name',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'login',
				text : 'login',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'psw',
				text : 'psw',
				width : 100
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'domainl1_name',
				text : 'domainl1_name',
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'domainl2_name',
				text : 'domainl2_name',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'domainl3_name',
				text : 'domainl3_name',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'email',
				text : 'email',
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'role_name',
				text : 'role_name',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'level_name',
				text : 'level_name',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'salary',
				text : 'salary',
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'location',
				text : 'location',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'phone',
				text : 'phone',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'gender',
				text : 'gender',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'age',
				text : 'age',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'checkin',
				text : 'checkin',
				width : 150
			} ],
			viewConfig : {
				forceFit : true
			}
		});

		me.callParent(arguments);
	}
});
