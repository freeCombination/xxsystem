/**
 * $('body').messagebox('成功提示', 1, 2000) 第一个参数为消息内容 第二个参数为消息类型，1：成功，0：失败，2：警告
 * 第三个参数为延时时间，不填默认5秒后自动隐藏
 * 
 */

var messagebox_timer;
$.fn.messagebox = function(message, type, delay) {
	clearTimeout(messagebox_timer);
	$("#msgprint").remove();
	var m_body = $(this);
	delay = (typeof delay == "undefined" ? 5000 : delay);
	var box_style = 'position:absolute;display:none;z-index:1000;padding:10px 30px 10px 40px;';
	switch (type) {
	case 1:
		box_style += 'border:1px solid Green;color:#090;background:url(../images/ok.png) 10px 10px no-repeat #F1FEF2;';
		break;
	case 0:
		box_style += 'border:1px solid Red;color:#EE1010;background:url(../images/error.png) 10px 10px no-repeat #FDF8E8;';
		break;
	default:
		box_style += 'border:1px solid Orange;color:Orange;background:url(../images/warning.png) 10px 10px no-repeat #FEFDE9;';
		break
	}
	var str = "<div id=\"msgprint\" style=\"" + box_style + "\">" + message
			+ "</div>";
	m_body.append(str);
	var dom_obj = document.getElementById("msgprint");
	var ext_width = $("#msgprint").width();
	dom_obj.style.top = "10px"; // (document.documentElement.scrollTop +
								// (document.documentElement.clientHeight -
								// dom_obj.offsetHeight -
								// $("#msgprint").height()) / 2) + "px";
	dom_obj.style.left = ((document.body.clientWidth - $("#msgprint").width()) / 2)
			+ "px";
	$("#msgprint").fadeIn(1000, function() {
		messagebox_timer = setTimeout(messagebox_out, delay)
	});
};
function messagebox_out() {
	$("#msgprint").fadeOut(1000);
}