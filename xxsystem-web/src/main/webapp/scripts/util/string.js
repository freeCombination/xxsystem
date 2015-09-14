/**
 * 在字符串的左边填充一些特定的字符
 * @param len
 * @param s
 * @return
 */
String.prototype.lpad = function(len, s) {   
    var a = new Array(this);   
    var n = (len - this.length);   
    for ( var i = 0; i < n; i++) {   
        a.unshift(s);   
    }   
    return a.join("");   
};

/**
 * 判断字符串是否为undefined、""、null或空白
 */
String.prototype.isBlank = function() {
    if (this == undefined || this == "" || this == null || /^\s*$/.test(this)) {
        return true;
    }
    return false;
};

/**
 * 判断字符串长度
 */
String.prototype.charLength = function() {
    var nstr = this.replace(/[^x00-xff]/mg, "JJ");
    return nstr.length;
};

/**
 * 验证是否为合法的手机号码   
 */
String.prototype.isMobile = function() {    
    return /^(?:13\d|15[89])-?\d{5}(\d{3}|\*{3})$/.test(this.trim());    
};   
  
/**
 * 验证是否为合法的电话号码或传真   
 */
String.prototype.isPhone = function() {    
    return /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(this.trim());    
};   
  
/**
 * 验证是否为合法的Email   
 */
String.prototype.isEmail = function() {   
    return /^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/.test(this.trim());   
};   
  
/**
 * 验证是否为合法的邮编
 */   
String.prototype.isPost = function() {   
    return /^\d{6}$/.test(this.trim());   
};   
  
/**
 * 验证是否为合法的网址 
 */  
String.prototype.isUrl = function() {   
    var strRegex = "^((https|http|ftp|rtsp|mms)://)"     
    + "(([0-9a-z_!~*’().&=+$%-]+: )?[0-9a-z_!~*’().&=+$%-]+@)?" //验证ftp的user@     
    + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // 验证IP形式的URL     
    + "|" // 允许IP和DOMAIN（域名）     
    + "([0-9a-z_!~*’()-]+\.)*" // 域名- www.     
    + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名     
    + "[a-z]{2,6})" // 一级域名     
    + "(:[0-9]{1,4})?" // 端口         
    var re = new RegExp(strRegex);     
    return re.test(this.trim());   
};  
