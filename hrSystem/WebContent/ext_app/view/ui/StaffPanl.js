

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
				text : '姓名',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'login',
				text : 'alias',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'psw',
				text : '密码',
				width : 100
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'domainl1_name',
				text : '分公司',
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'domainl2_name',
				text : '部门',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'domainl3_name',
				text : '业务线',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'email',
				text : 'email',
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'role_name',
				text : '角色',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'level_name',
				text : '职称',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'salary',
				text : '月薪',
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'location',
				text : '位置',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'phone',
				text : '电话',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'gender',
				text : '性别',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'age',
				text : '年龄',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'checkin',
				text : '入职时间',
				width : 150
			} ],
			viewConfig : {
				forceFit : true
			}
		});

		me.callParent(arguments);
	}
});
