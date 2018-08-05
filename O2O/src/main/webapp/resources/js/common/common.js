

//更改验证码
function changeVerifyCode(img){
	//随机数做更改参数
	img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

//从当前url得到参数值
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}
	return '';
}