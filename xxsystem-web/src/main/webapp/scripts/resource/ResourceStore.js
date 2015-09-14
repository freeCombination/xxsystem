/**
 * 资源类型 model
 * */
Ext.define('resourceTypeModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'dictionaryId',type:"int"},
        {name: 'dictionaryName'},
        {name: 'dictionaryValue'}
    ]
});	
/**
 * 资源类型 store
 * */
var parentResourceTypeStore = Ext.create('Ext.data.Store', {
 	model: 'resourceTypeModel',
 	proxy: {
  	   type: 'ajax',
  	   url: basePath + '/user/getSelectionsByType.action',
  	   extraParams:{dictTypeCode:"RESOURCETYPE"},
   	   reader: {
   	      type: 'json',
   	      root: 'list'
  	   }
 	},
 	autoLoad: false,
	listeners:{
		load:function(storeObj,records){
            if(records.length>0){
            	Ext.getCmp("parentResourceId").setValue(records[0].get("dictionaryId"));
            }
		}
	}
});