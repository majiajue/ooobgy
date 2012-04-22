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
        
        var staffGrid = Ext.create('FcSVNAdmin.view.StaffPanl', {
            renderTo: Ext.get("info_panl"),
            loadMask: true,
            store: store,
			dockedItems : [ {
				xtype : 'toolbar',
				dock : 'top',
				items : [ ]
			} 
			]
        });
        staffGrid.show();
        $("#info_panl").css({"position": "fixed"});
        fcLayoutExtCompCenter("info_panl");
        loadStaff();
        
        function loadStaff(){
        	store.load({params:{path:authzRoot},
        		callback: function(){//刷新初始
        			//TODO
        		}});
        }
    }
});