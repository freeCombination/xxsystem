/**
 * 动态显示用户输入多少字符
 * 
 * @param maxNum 需要输入的最多字符
 * @param inputId 输入框的ID
 * @param spId 显示信息的元素ID
 * @return any
 */
function showNum(maxNum, inputId, spId) {
	var strTemp = document.getElementById(inputId).value;
	var sum = 0;
	for ( var i = 0; i < strTemp.length; i++) {
		var flag = true; // 英文标志
		if ((strTemp.charCodeAt(i) >= 0) && (strTemp.charCodeAt(i) <= 255)) {
			sum = sum + 1;
		} else {
			sum = sum + 2;
			flag = false;
		}
		if (sum > maxNum) {
			var str = strTemp.substring(0, i);
			document.getElementById(inputId).value = str;
			if (flag) {
				sum -= 1;
			} else {
				sum -= 2;
			}
			break;
		}
	}
	document.getElementById(spId).innerHTML = "(您还可以输入" + (maxNum - sum)+ "字符)";
};
