/**
 * 去掉数组中重复的值
 * @return
 */
Array.prototype.unique = function() {   
    var data = this || [];   
    var a = {}; //声明一个对象，javascript的对象可以当哈希表用   
    for (var i = 0; i < data.length; i++) {   
        a[data[i]] = true;  //设置标记，把数组的值当下标，这样就可以去掉重复的值   
    }   
    data.length = 0;    
       
    for (var i in a) { //遍历对象，把已标记的还原成数组   
        this[data.length] = i;    
    }    
    return data;   
};

/**
 * 
 * 删除数组中指定的值
 * @param val   要删除的值
 * @return
 */
Array.prototype.remove = function(val) {    
    for(var i = 0; i < this.length; i++) {    
        if(this[i] == val) {    
            for(var j = i; j < this.length - 1; j++)   
                this[j] = this[j + 1];    
            	this.length -= 1;   
        }   
    }       
}; 

/**
 * 判断数组是否含有相同元素
 */
Array.prototype.contains = function (element) {    
	for (var i = 0; i < this.length; i++) {    
		if (this[i] == element) {    
			return true;    
		}    
	}    
	return false;    
};   
