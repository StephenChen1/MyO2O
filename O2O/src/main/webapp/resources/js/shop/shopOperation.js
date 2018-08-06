$(document).ready(function(){
	//alert("加载到shopOperation.js文件了");
	//从当前url得到ShopID
	var shopId = getQueryString("shopId");
	
	//给各个操作按钮绑定点击跳转事件
	//修改店铺
	$("#shopInfoLink").click(function(){
		window.location.href = "../shopUI/shopOperation?shopId=" + shopId;
	});
	
	//商品类别管理
	$("#shopCategoryLink").click(function(){
		//alert("点击了类别管理");
		window.location.href = "../shopUI/prodCateManage";
	});
	$("#").click(function(){
		window.location.href = "../shopUI/shopOperation?shopId=" + shopId;
	});
	$("#").click(function(){
		window.location.href = "../shopUI/shopOperation?shopId=" + shopId;
	});
	$("#").click(function(){
		window.location.href = "../shopUI/shopOperation?shopId=" + shopId;
	});
	//新增店铺
	$("#").click(function(){
		window.location.href = "../shopUI/shopOperation";
	});
	
	
})