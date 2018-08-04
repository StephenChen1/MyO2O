

//更改验证码
function changeVerifyCode(img){
	//随机数做更改参数
	img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}