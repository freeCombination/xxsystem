Ext.define('orgTypeModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'dictionaryId'
	}, {
		name : 'dictionaryName'
	}, {
		name : 'dictionaryValue'
	} ]
});	
var orgTypeStore = Ext.create('Ext.data.Store', {
	model: 'orgTypeModel',
	proxy: {
	type: 'ajax',
	url: basePath+'/user/getSelectionsByType.action',
	extraParams:{dictTypeCode:"ORGTYPE"},
	reader: {
  		type: 'json',
  		root: 'list'
	}
	},
	autoLoad: false,
	listeners:{
		load:function(storeObj,records){
            if(records.length>0){
            	Ext.getCmp("addOrgSelectionId").setValue(records[0].get("dictionaryId"));
            }
		}
	}
});