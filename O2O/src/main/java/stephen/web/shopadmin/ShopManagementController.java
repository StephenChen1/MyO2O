package stephen.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import stephen.entity.Area;
import stephen.entity.Shop;
import stephen.entity.ShopCategory;
import stephen.service.AreaService;
import stephen.service.ShopCategoryService;
import stephen.util.HttpServletRequestUtil;

//该controller专门处理提交上来的店铺的数据

@Controller
@RequestMapping("/shop")
public class ShopManagementController {

	@Autowired
	private ShopCategoryService shopCategoryService ;
	@Autowired
	private AreaService areaService ;
	
	/**
	 * 店铺注册，步骤如下：1、得到参数数据 2、注册店铺 3、返回数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registerShop" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> registerShop(HttpServletRequest request){
		//定义返回结果集
		Map<String,Object> modelMap = new HashMap<>();
		//1、接收参数，转换成实体类,以及得到上传的图片
		//得到shop的字符串,须和前端定好shopStr这个名称
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		//先定义JSON转换工具对象
		ObjectMapper mapper = new ObjectMapper();
		//从request得到shop的字符串，并转换为Shop实体类
		try{
			Shop shop = mapper.readValue(shopStr, Shop.class);
		}catch(Exception e){
			//若转换出现异常，则返回数据错误提示
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap ;
		}
	    //得到上传的图片
		
		CommonsMultipartFile shopImg = null ;
		//从request上下文，从而构造文件流
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(
										     request.getSession().getServletContext());
		//判断是否是图片文件流和是否为空
		if(resolver.isMultipart(request)){
			//强制转换request成MultipartHttpServletRequest
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			//得到文件流
			shopImg = (CommonsMultipartFile)multipartRequest.getFile("shopImg");
		}else{
			//为空，则返回数据报告
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
		}
		
		
		return null ;
	}
	
	/**
	 * 得到区域信息和店铺类别信息，供店铺注册时选择
	 * @return
	 */
	@RequestMapping(value = "/getShopInitInfo",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getShopInitInfo(){
		//定义返回的数据结构
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//店铺类别列表
		List<ShopCategory> shopCategoryList = new ArrayList<>();
		//地区列表
		List<Area> areaList = new ArrayList<>();
		try{
			//得到店铺类别列表,传入的参数中parent不为空，因此期望中会返回所有除一级店铺类别之外的类别
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setParent(new ShopCategory());
			shopCategoryList = shopCategoryService.queryShopCategory(shopCategory);
			//得到地区列表
			areaList = areaService.getAllArea();
			/*System.out.println("店铺类别长度:" + shopCategoryList.size());
			for(ShopCategory shopCategory1 : shopCategoryList){
				System.out.println("parentId:" + shopCategory1.getParent());
			}*/
			//写入返回数据模型
			resultMap.put("shopCategoryList", shopCategoryList);
			resultMap.put("areaList", areaList);
			resultMap.put("success", true);//标识
		}catch(Exception e){
			//出现异常就返回异常标识和异常信息
			resultMap.put("success", false);
			resultMap.put("errMsg", e.getMessage());
		}
		
		return resultMap ;
	}
	
}
