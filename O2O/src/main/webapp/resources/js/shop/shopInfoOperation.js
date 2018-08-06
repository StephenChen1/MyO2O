//函数定义 测试
function test(){
	alert("test function!");
	//$("#shop-address").val("testAddress");
	var shop = {};
	shop.area = {
			id : $('#shop-area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
	alert(shop.area.id);
}

//将选择的店铺缩略图展示出来
function preview(file){  
    //var prevDiv = document.getElementById('shop-img1');  
    if (file.files && file.files[0]){  
        var reader = new FileReader();  
        reader.onload = function(evt){  
        // prevDiv.innerHTML = '<img width="100%" height="100%" id="qw_img" src="' + evt.target.result + '" />';
        $('#shop-img1').attr('src' , evt.target.result);
    }    
         reader.readAsDataURL(file.files[0]);  
    }else{  
        // prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';  
      $('#shop-img1').attr('src' , file.value);
    }  
} 


//得到区域和店铺类别信息来初始化界面
function getShopInitInfo(url){
	$.ajax({
		type : "GET",
    	url  : url,
    	success:function(result){
    		//将数据展示到下拉框
    		//alert("得到数据");
    		if(result.success){
    			//先把下拉框清空
    			$("#shop-area").find("option").remove();
    			//区域下拉框,遍历result中的areaList,item为值，index为下标
    			result.areaList.map(function(item,index){
    				//构造选项字符串
    				var areaOption = "<option data-id='"+item.id+"'>"+item.name+"</option>";
    				//添加进下拉框
    				$("#shop-area").append(areaOption);
    			})
    		
    			//店铺类别下拉框
    			//先清空下拉框
    			$("#shop-category").find("option").remove();
    			//遍历result中的shopCategoryList,输出到下拉框
    			result.shopCategoryList.map(function(item,index){
    				//构造选项字符串
    				var shopCategoryOption = "<option data-id='"+item.id+"'>"+item.name+"</option>";
    				//添加进下拉框
    				$("#shop-category").append(shopCategoryOption);
    			})
    		}else{
    			$.toast("获取初始出错");
    		}
    	}
		
	});
}

//根据Id得到店铺信息
function getShopInfoById(url){
	$.ajax({
		type : "GET",
    	url  : url,
    	success:function(result){
    		//将数据展示到下拉框
    		//alert("得到数据");
    		if(result.success){
    			
    			//店铺名
    			$("#shop-name").val(result.shop.name);
    			//联系方式
    			$("#shop-phone").val(result.shop.phone);
    			//描述
    			$("#shop-desc").val(result.shop.description);
    			//地址
    			$("#shop-address").val(result.shop.address);
    			//门面照
    			$('#shop-img1').attr('src' , result.shop.img);
    			
    			//先把下拉框清空
    			$("#shop-area").find("option").remove();
    			//区域下拉框,遍历result中的areaList,item为值，index为下标
    			result.areaList.map(function(item,index){
    				//构造选项字符串
    				var areaOption = "<option value='"+item.id+"' data-id='"+item.id+"'>"+item.name+"</option>";
    				//添加进下拉框
    				$("#shop-area").append(areaOption);
    			})
    			//默认值设为店铺原本的地区(下面的写法无效的，菜）
    			//$('#shop-area').attr('data-id',result.shop.area.id);
    			$('#shop-area').val(result.shop.area.id);
    			
    			//店铺类别不可改，因此下拉框形式只有一个option，不可修改
    			var shopCategoryStr = '<option data-id="'
					+ result.shop.shopCategory.id + '" selected>'
					+ result.shop.shopCategory.name + '</option>';
    			//该方法和append类似，但append是追加，这个是替换旧值
    			$('#shop-category').html(shopCategoryStr);
    			
    			
    		}else{
    			$.toast("无法获取店铺信息");
    		}
    	}
		
	});
}

//注册店铺和修改店铺信息军用同一个html和这个js              
$(document).ready(function(){
	
	//定义请求地址（方便查看时知道有哪些请求）
	//1、得到区域和店铺类别来初始化界面（注册界面）
	var initUrl = "../shop/getShopInitInfo";         
	//2、注册店铺提交（注册界面）
	var registerShopUrl = "../shop/registerShop";
	//3、根据店铺id得到店铺信息（修改界面）
	var getShopInfoUrl = "../shop/getShopInfoById?shopId=";
	//4、修改店铺提交（修改界面）
	var modifyShopUrl = "../shop/modifyShop";
	
	//从url得到shopId,如果有id,则是修改界面，否则即是注册界面
	var shopId = getQueryString("shopId");
	if(shopId){
		//从后台得到店铺信息并显示
		var url = getShopInfoUrl + shopId ;
		getShopInfoById(url);
	}else{
	    //从后台得到店铺注册初始化数据
	    getShopInitInfo(initUrl);
	}
	//函数调用 测试
	$("#shop-cancel").click(function(){
		test();
	});
	
	//店铺提交按钮点击事件
	$("#shop-submit").click(function(){
		//获取输入的验证码，不为空则继续
		var verifyCodeInput = $("#verifyCodeInput").val();
		if(!verifyCodeInput){
			//空的话提示用户输入验证码
			$.toast("未输入验证码");
			return ;
		}
		//定义该提交按钮点击时的后台url
		var commitUrl = "";
		//定义商店对象存储商店信息（除了照片）
		var shop = {};
		//alert("提交");
		//根据是哪个界面加入店铺id
		if(shopId){
			//修改店铺界面
			//请求路径为修改店铺路径
			commitUrl = modifyShopUrl ;
			//shop加入店铺id
			shop.id = shopId ;
		}else{
			//否则即是注册页面
			commitUrl = registerShopUrl ;
		}
		//得到店铺名
		shop.name = $("#shop-name").val();
		//alert(shop.name);
		//区域
		//shop.area = $("#shop-area").val();
		//这里得到的是一个Area对象，其中一个属性时id(后台需要的是区域对象）
		shop.area = {
				id : $('#shop-area').find('option').not(function() {
					return !this.selected;
				}).data('id')
			};
		//alert(shop.area.id);
		//类别
		//这里得到的是一个shopCategory对象，其中一个属性时id(因为后台需要的是类别对象）
		shop.shopCategory = {
				id : $('#shop-category').find('option').not(function() {
					return !this.selected;
				}).data('id')
			};
		//联系方式
		shop.phone = $("#shop-phone").val();
		//描述
		shop.description = $("#shop-desc").val();
		//地址
		shop.address = $("#shop-address").val();
		//门面照
		var img = $("#shop-img")[0].files[0];
		//alert("img:"+img.length);
		
		//定义formdata来做提交数据的载体
		var formdata = new FormData();
		//将shop插进formdata
		formdata.append("shopStr",JSON.stringify(shop));
		//验证码
		formdata.append("verifyCodeInput",verifyCodeInput);
		//将图片流也插进去
		formdata.append("shopImg",img);
		//alert("shopStr:" + JSON.stringify(shop));
		//alert(formdata);
		//alert(shop.desc);
		//从后台获取所有会议室详情
		$.ajax({
	    	type : "POST",
	    	url:commitUrl,
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