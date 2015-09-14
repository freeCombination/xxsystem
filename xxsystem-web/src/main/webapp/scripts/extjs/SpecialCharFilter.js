Ext.apply(Ext.form.VTypes, {
	inputCharFilter : function(val, field) {
		this.inputCharFilterText ='含有特殊字符<,&amp;nbsp等';
        var regExp=new RegExp('<.*',"igm");
        var error=false;
        error=regExp.test(val);
        if(error){
        	return false;
        }
        regExp=new RegExp('&nbsp',"igm");
        error=regExp.test(val);
        if(error){
        	return false;
        }
        if(val!=null&&typeof(val)!=undefined&&Ext.String.trim(val)==""){
        	this.inputCharFilterText='不能只添加空格';
        	return false;
        }
        return !error;
              
      
    },

    inputCharFilterText : '含有特殊字符<,&amp;nbsp等'
});