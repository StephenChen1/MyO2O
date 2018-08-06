$(document).ready(function(){
	
	function getProdCate(url){
		//从后台获取该店铺的商品类别列表，展示出来
		$.ajax({
			type : "GET",
			url : url,
			success : function(result){
				if(result.success){
					//先清除原内容
					$(".category-wrap").find("div").remove();
					if(result.prodCateList.length <= 0){
						var str = '<div class="col-100"><a class="button">该店铺暂时没有商品分类</a></div>'
						$(".category-wrap").append(str);
					}
					result.prodCateList.map(function(item,index){
						var str = '<div class="row row-product-category">'+
										'<div class="col-33">'+item.name+'</div>'+
										'<div class="col-33">'+item.priority+'</div>'+
										'<div class="col-33"><a class="button old" data-id="'+item.id+'">删除</a></div>'+
									'</div>';
						$(".category-wrap").append(str);
					})
				}else{
					$.toast("获取数据出错！"+result.errMsg);
				}
			}
		})
		
	}
	
	
	//alert("加载到prodCateManage.js文件了");
	
	//获取商品类别列表
	var getProdCateUrl = "../productCategory/getProdCate";
	//删除某个商品类别url
	var deleteProdCateUrl = "../productCategory/deleteProdCate";
	//提交新增数据的url
	var addProdCateUrl = "../productCategory/batchAddProdCate";
	
	//从后台获取该店铺的商品类别列表，展示出来
	getProdCate(getProdCateUrl);
	
	
	
	//原数据删除按钮点击事件
	$(document).on('click','.old',function(){

		//先标记该点击事件所在的行选择器，方便删除
		var selector = $(this).parents(".row-product-category");
		//得到商品类别id
		var prodCateId = $(this).attr("data-id");
		//alert(prodCateId);
		//confirm('确定删除么1?');
		$.confirm('确定删除么?', function() {
			$.ajax({
				url : deleteProdCateUrl,
				type : 'POST',
				data : {  prodCateId : prodCateId },
				//dataType : 'json',
				success : function(result) {
					if (result.success) {
						$.toast('删除成功！');
						//将该类别从界面移除
						selector.remove();
						
					} else {
						$.toast('删除失败！');
					}
				}
			});
		});
		
		
	});
	
	//新增的数据删除按钮点击事件
	$(document).on('click','.new',function(){
		$(this).parents(".row-product-category").remove();
	});
	//新增类别按钮点击事件
	$("#addProdCate").click(function(){
		//构造数据行字符串
		var str = '<div class="row row-product-category newProdCate">'+
					'<div class="col-33"><input type="text" class="name" placehoder="商品类别名"></div>'+
					'<div class="col-33"><input type="text" class="priority" placehoder="权重级别"></div>'+
					'<div class="col-33"><a class="button new">删除</a></div>'+
				  '</div>';
		//接在后面
		$(".category-wrap").append(str);
	});
	//提交按钮点击事件
	$("#submit").click(function(){
		//定义是否提交标志
		var canCommit = true ;
		//遍历得到新增的类别名和类别权重
		var newArr = $(".newProdCate");
		var prodCateList = [];//类似于 new Array();
		newArr.map(function(index,item){
			var prodCate = {};//类似于new Object();
			prodCate.name = $(item).find(".name").val();
			prodCate.priority = $(item).find(".priority").val();
			//alert(prodCate.name + prodCate.priority);
			//权重和名称都要不为空
			if(prodCate.name && prodCate.priority){
				prodCateList.push(prodCate);
			}else{
				canCommit = false ;
				$.toast("类别名和权重都不能有空值");
				return ;
			}
		});
		//标志为true，则可以提交
		if(canCommit){
			$.ajax({
				type : "POST",
				url : addProdCateUrl,
				contentType:"application/json",
				//控制器用@RequestBody时需要将其转换成字符串传过去
				data : JSON.stringify(prodCateList) ,
				success : function(result){
					 if(result.success){
						 
						 //从后台得到数据重新渲染界面
						 getProdCate(getProdCateUrl);
						 $.toast("提交成功！");
					 }else{
						 $.toast("提交失败！"+ result.errMsg);
					 }
				}
					
			});
		}
		
		
		
	});
	
	
})