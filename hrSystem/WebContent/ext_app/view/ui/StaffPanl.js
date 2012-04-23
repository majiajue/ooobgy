

Ext.define('FcSVNAdmin.view.ui.StaffPanl', {
	extend : 'Ext.grid.Panel',

	height : 400,
	width : 1000,
	title : '员工人事资料',

	initComponent : function() {
		var me = this;
		
		function getGender(value){
			if (value == 1) {
				return "男";
			} else {
				return "女";
			}
		}
	    
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
				dataIndex : 'domainl1_name',
				text : '分公司',
				renderer : fcDataFilterNull,
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'domainl2_name',
				renderer : fcDataFilterNull,
				text : '部门',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'domainl3_name',
				renderer : fcDataFilterNull,
				text : '业务线',
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'email',
				renderer : fcDataFilterNull,
				text : 'email',
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'role_name',
				text : '角色',
				renderer : fcDataFilterNull,
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'level_name',
				text : '职称',
				renderer : fcDataFilterNull,
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'salary',
				text : '月薪',
				renderer : fcDataFilterNull,
				width : 250
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'location',
				text : '位置',
				renderer : fcDataFilterNull,
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'phone',
				text : '电话',
				renderer : fcDataFilterNull,
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'gender',
				text : '性别',
				renderer : getGender,
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'age',
				text : '年龄',
				renderer : fcDataFilterNull,
				width : 150
			}, {
				xtype : 'gridcolumn',
				dataIndex : 'checkin',
				text : '入职时间',
				renderer : fcDataFilterNull,
				width : 150
			} ],
			viewConfig : {
				forceFit : true
			}
		});

		me.callParent(arguments);
	}
});
