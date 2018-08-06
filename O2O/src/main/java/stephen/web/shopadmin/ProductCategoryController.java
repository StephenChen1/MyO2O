package stephen.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stephen.dto.ProdCateExecution;
import stephen.entity.ProductCategory;
import stephen.enums.ProdCateStateEnum;
import stephen.service.ProductCategoryService;
import stephen.util.HttpServletRequestUtil;

//接受对商品类别的操作请求
@RequestMapping("/productCategory")
@Controller
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService prodCateService ;
	
	//得到某个店铺下的所有商品类别
	@RequestMapping(value = "/getProdCate" ,method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getProductCategory(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			System.out.println("进到获取的控制器了");
			// 从session得到currentShopId
			Long shopId = (Long) request.getSession().getAttribute("currentShopId");
			if (shopId == null) {
				System.out.println("转换店铺id为空");
				//返回要跳转到的页面字符串，此处是跳转到shopList页面
				modelMap.put("success", false);
				//重定向标志
				modelMap.put("isLcation", true);
				modelMap.put("url", "../shopUI/shopList");
				return modelMap ;
			}
			//调用service，获取shopId下的商品类别列表
			ProdCateExecution prodCateExecution = prodCateService.getProdCateByShopId(shopId);
			if(prodCateExecution.getState() == ProdCateStateEnum.SUCCESS.getState()){
				modelMap.put("prodCateList", prodCateExecution.getProductCategoryList());
				modelMap.put("success", true);
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", prodCateExecution.getStateInfo());
			}
		
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "控制器异常");
			return modelMap ;
		}
		
		return modelMap ;
	}
	
	
	//批量增加商品类别
	@RequestMapping(value = "/batchAddProdCate",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> batchAddProdCate(@RequestBody List<ProductCategory> prodCateList,HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		try {
			// 从session得到店铺id
			Long shopId = (Long) request.getSession().getAttribute("currentShopId");
			// shopId为空的话，就跳转回shopList界面
			if (shopId == null) {
				System.out.println("店铺id为空");
				modelMap.put("success", false);
				modelMap.put("errMsg", "店铺id为空");
				return modelMap ;
			}
			
			//遍历，将shopId填进对象
			for(ProductCategory p : prodCateList){
				System.out.println(p.getName());
				p.setShopId(shopId);
			}
			//调用service
			ProdCateExecution pe = prodCateService.batchAddProdCate(prodCateList);
			if(pe.getState() == ProdCateStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", pe.getStateInfo());
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "控制器异常");      
			return modelMap ;
		}
		return modelMap ;
	}
	
	
	//删除某个商品类别
	@RequestMapping(value = "deleteProdCate",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteProdCate(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		
		//得到类别id
		Long id = HttpServletRequestUtil.getLong(request, "prodCateId");
		//做空值判断
		if(id == -1){
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品类别id为空");
			return modelMap ;
		}
		//得到店铺id
		Long shopId = (Long)request.getSession().getAttribute("currentShopId");
		if(shopId == null){
			modelMap.put("success", false);
			modelMap.put("errMsg", "店铺id为空");
			return modelMap ;
		}
		//调用service
		try{
			ProdCateExecution pe = prodCateService.deleteProdCate(id, shopId);
			if(pe.getState() == ProdCateStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);
			}
		}catch(Exception e){
			modelMap.put("success", false);
			modelMap.put("errMsg", "控制器异常");
			return modelMap ;
		}
		
		return modelMap ;
	}
	
	
}
