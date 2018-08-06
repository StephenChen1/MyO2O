$(document).ready(function(){
	
	//将转态码转换成文字
	function showStatusText(status){
		var result = "";
		if(status == -1){
			result = "审核不通过"
		}else if(status == 0){
			result = "审核中";
		}else if(status == 1){
			result = "正常";
		}else{
			result = "异常店铺";
		}
		return result ;
	}
	
	
	//根据状态码决定能否对店铺操作
	function showStatusOperation(status,shopId){
		var result = "";
		if(status == 0 || status == 1){
			//似乎在<a>里放href,链接新页面后加载不进自定义的css文件，需要在那个页面上刷新一次才行
			//result = "<a class = 'btn btn-success shopOper' href = '../shopUI/shopOperation?shopId="+shopId+"'>操作</a> ";
			result = "<a class = 'btn btn-sm btn-info shopOper' id ='"+shopId+"'>操作</a> ";
		}
		return result ;
	}
	
	
	
	//从后台得到我的店铺列表，id由后台从session获取，
	//要是session里没有我的id，则跳转到登陆界面 TODO
	
	//1、获取我的店铺列表url
	var getShopListUrl = "../shop/getShopList";
	
	//后台获取，参数页下标及也容量，后台从session获取用户id
	var pageIndex = 1 ;
	var pageSize = 20;
	var data = {
			pageIndex : pageIndex,
			pageSize : pageSize
	};
	
	$.ajax({
    	type : "POST",
    	url:getShopListUrl,
    	data:data,
        success:function(result){
        	if(result.success){
        		//先清除原本数据
        		$("#shopListDiv").find(".row").remove();
        		//将数据展示在界面上
        		result.shopList.map(function(item,index){
    				
    				//每行显示店铺名称，店铺状态，以及操作链接(审核通过才有操作链接）
        			//奇怪，没有text就撑不开div
    				var shopRow = '<div class="row row-shop">'+
						'<div class="col col-40">'+item.name+'</div>'+
						'<div class="col-40">'+showStatusText(item.enableStatus)+'</div>'+
						'<div class="col-20">'+showStatusOperation(item.enableStatus,item.id)+'</div>'+
					'</div>';
    				//alert(item.name);
    				//添加进下拉框
    				$("#shopListDiv").append(shopRow);
    			})
        	}else{
        		$.toast("获取我的店铺出错！"+ result.errMsg);
        	}
        }
	});
	
	
	//每行的操作按钮加上点击跳转页面事件
	$(document).on('click','.shopOper',function(){
		//得到操作的店铺id
		var shopId = $(this).attr('id');
		//alert(shopId);
		//带参数跳转
		var url = "../shopUI/shopOperation?shopId="+shopId ;
		
		window.location.href = url ;
	});
	
	
})