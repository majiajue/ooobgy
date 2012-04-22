Ext.define('FcSVNAdmin.model.StaffModel', {
    extend: 'Ext.data.Model',
    fields: [
		{name: 'id', type: 'string'},
		{name: 'name', type: 'string'},
		{name: 'login', type: 'string'},
		{name: 'psw', type: 'string'},
		{name: 'domainl1_name', type: 'string'},
		{name: 'domainl2_name', type: 'string'},
		{name: 'domainl3_name', type: 'string'},
		{name: 'email', type: 'string'},
		{name: 'role_name', type: 'string'},
		{name: 'level_name', type: 'string'},
		{name: 'salary', type: 'string'},
		{name: 'location', type: 'string'},
		{name: 'phone', type: 'string'},
		{name: 'gender', type: 'string'},
		{name: 'age', type: 'string'},
		{name: 'checkin', type: 'string'}
    ]
});