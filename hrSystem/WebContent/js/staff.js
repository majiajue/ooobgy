Ext.Loader.setConfig({
    enabled: true
});

Ext.require([
             'Ext.window.*',
             'Ext.grid.*',
             'Ext.data.*',
             'Ext.form.*',
             'Ext.container.*'
         ]);

Ext.application({
    name: 'FcSVNAdmin',

    launch: function() {
        Ext.QuickTips.init();
        
        var store = Ext.create('FcSVNAdmin.store.StaffStore', {
        	model : "FcSVNAdmin.model.StaffModel"
        });
        
        function addRule(){
        	Ext.Msg.confirm('构建中...', '未完成的功能',function(btn){
        	});
        }
        
        
        
        function refreshInfo(){
        	Ext.Msg.confirm('警告', '刷新将丢失未保存的修改！确定？',function(btn){
        		if (btn == 'yes') {
        			loadStaff();
				}
        	});
        }
        
        var staffGrid = Ext.create('FcSVNAdmin.view.StaffPanl', {
            renderTo: Ext.get("info_panl"),
            loadMask: true,
            store: store,
			dockedItems : [ {
				xtype : 'toolbar',
				dock : 'top',
				items : [ {
					xtype : 'button',
					text : '新增员工',
					handler : addRule
				}, {
					xtype : 'tbseparator'
				}, {
					xtype : 'button',
					text : '删除选中',
					handler : addRule
				}, {
					xtype : 'tbseparator'
				}, {
					xtype : 'button',
					text : '修改选中',
					handler : addRule
				}, {
					xtype : 'tbseparator'
				}, {
					xtype : 'button',
					text : '保存修改',
					handler : addRule
				}, {
					xtype : 'tbseparator'
				}, {
					xtype : 'button',
					text : '重新载入',
					handler : refreshInfo
				}, {
					xtype : 'tbseparator'
				} ]
			} 
			]
        });
        staffGrid.show();
        $("#info_panl").css({"position": "fixed"});
        fcLayoutExtCompCenter("info_panl");
        loadStaff();
        
        function loadStaff(){
        	store.load({params:{},
        		callback: function(){//刷新初始
        			//TODO
        		}});
        }
    }
});