//函数定义 测试
function test(){
	alert("test function!");
	//$("#shop-address").val("testAddress");
}

//得到区域和店铺类别信息来初始化界面
function getShopInitInfo(url){
	$.ajax({
		type : "GET",
    	url  : url,
    	success:function(result){
    		//将数据展示到下拉框
    		alert("得到数据");
    		if(result.success){
    			//先把下拉框清空
    			$("#shop-area").find("option").remove();
    			//区域下拉框,遍历result中的areaList,item为值，index为下标
    			result.areaList.map(function(item,index){
    				//构造选项字符串
    				var areaOption = "<option value='"+item.id+"'>"+item.name+"</option>";
    				//添加进下拉框
    				$("#shop-area").append(areaOption);
    			})
    		
    			//店铺类别下拉框
    			//先清空下拉框
    			$("#shop-category").find("option").remove();
    			//遍历result中的shopCategoryList,输出到下拉框
    			result.shopCategoryList.map(function(item,index){
    				//构造选项字符串
    				var shopCategoryOption = "<option value='"+item.id+"'>"+item.name+"</option>";
    				//添加进下拉框
    				$("#shop-category").append(shopCategoryOption);
    			})
    		}else{
    			alert("获取初始出错");
    		}
    	}
		
	});
}

              
$(document).ready(function(){
	
	//定义请求地址（方便查看时知道有哪些请求）
	//1、得到区域和店铺类别来初始化界面
	var initUrl = "../shop/getShopInitInfo";         
	//2、注册店铺提交
	var registerShopUrl = "../shop/registerShop";
	
	//从后台得到店铺注册初始化数据
	getShopInitInfo(initUrl);
	
	//函数调用 测试
	$("#shop-cancel").click(function(){
		//test();
	});
	
	//店铺提交按钮点击事件
	$("#shop-submit").click(function(){
		//定义商店对象存储商店信息（除了照片）
		var shop = new Object();
		
		//得到店铺名
		shop.name = $("#shop-name").val();
		//区域
		shop.area = $("#shop-area").val();
		//类别
		shop.category = $("#shop-category").val();
		//联系方式
		shop.phone = $("#shop-phone").val();
		//描述
		shop.desc = $("#shop-desc").val();
		//地址
		shop.address = $("#shop-address").val();
		//门面照
		var img = $("#shop-img")[0].files[0];
		//alert("img:"+img.length);
		//定义formdata来做提交数据的载体
		var formdata = new FormData();
		//将shop插进formdata
		formdata.append("shopStr",JSON.stringify(shop));
		//将图片流也插进去
		formdata.append("img",img);
		//从后台获取所有会议室详情
		$.ajax({
	    	type : "post",
	    	url:registerShopUrl,
	    	data:formdata,
	    	//由于既传文件，又传文字，因此contentType设为false
	    	contentType:false,
	        //process 如果要发送 DOM 树信息或其它不希望转换的信息，请设置为 false。
	    	processData:false,
	    	//cache 设置为 false 将不会从浏览器缓存中加载请求信息。
	    	cache:false,
	        success:function(result){
	        	if(result.success){
	        		$.toast("提交成功！");
	        	}else{
	        		$.toast("提交失败！"+ result.errMsg);
	        	}
	        }
		});
		
		
	});
	
})