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

import stephen.dto.ShopExecution;
import stephen.entity.Area;
import stephen.entity.Shop;
import stephen.entity.ShopCategory;
import stephen.enums.ShopStateEnum;
import stephen.service.AreaService;
import stephen.service.ShopCategoryService;
import stephen.service.ShopService;
import stephen.util.HttpServletRequestUtil;
import stephen.util.VerifyCodeUtil;

//该controller专门处理提交上来的店铺的数据

@Controller
@RequestMapping("/shop")
public class ShopManagementController {

	@Autowired
	private ShopCategoryService shopCategoryService ;
	@Autowired
	private AreaService areaService ;
	@Autowired
	private ShopService shopService ;
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
		
		//先对验证码进行验证
		if(!VerifyCodeUtil.checkVerifyCode(request)){
			//不通过则返回
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			System.out.println("验证码不正确");
			return modelMap ;
		}
		//1、接收参数，转换成实体类,以及得到上传的图片
		//得到shop的字符串,须和前端定好shopStr这个名称
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		//System.out.println(shopStr);
		//先定义JSON转换工具对象
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null ;
		//从request得到shop的字符串，并转换为Shop实体类
		try{
			shop = mapper.readValue(shopStr, Shop.class);
		}catch(Exception e){
			//若转换出现异常，则返回数据错误提示
			System.out.println("转换店铺实例失败");
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			//System.out.println(e.getMessage());
			return modelMap ;
		}
	    //得到上传的图片
		MultipartHttpServletRequest multipartRequest = null ;
		CommonsMultipartFile shopImg = null ;
		//System.out.println("0000000777");
		//从request上下文，从而构造文件流
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(
										     request.getSession().getServletContext());
		
		//System.out.println("0000000888");
		//判断是否是图片文件流和是否为空
		if(resolver.isMultipart(request)){
			//强制转换request成MultipartHttpServletRequest
			 multipartRequest = (MultipartHttpServletRequest)request;
			 
			//得到文件流
			shopImg = (CommonsMultipartFile)multipartRequest.getFile("shopImg");
			
		}else{
			//为空，则返回数据报告
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//检查图片，不能上传为空
		if(shopImg == null){
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//将数据存入后台
		if(shop != null && shopImg != null){
			try{
				//System.out.println("开始存入");
				//从session中得到注册者的id，之前登录就已经在session保存personInfo
				//PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
				//将用户id作为店铺主人
				shop.setOwnerId(8L);
				ShopExecution se = shopService.addShop(shop, shopImg);
				//当添加成功，则se的状态为CHECK
				if(se.getState() == ShopStateEnum.CHECK.getState()){
					//设置返回值成功标志
					modelMap.put("success", true);
					//System.out.println("添加成功");
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
					return modelMap;
					//System.out.println("添加失败");
				}
			}catch(Exception e){
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
				//System.out.println("添加异常");
				//e.printStackTrace();
			}
		}
		
		
		return modelMap ;
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
	
	
	/**
	 * 根据id得到店铺信息，id在url
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getShopInfoById", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getShopInfoById(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//得到店铺id
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		//判断id是否为-1,-1说明转换失败,返回出错信息
		if(shopId == -1){
			modelMap.put("success", false);
			modelMap.put("errMsg", "店铺id参数出错");
			return modelMap ;
		}
		try{
			//调用service获取
			ShopExecution shopExecution = shopService.getShopById(shopId);
			//如果该id有对应店铺
			if(shopExecution.getShop() != null){
				//获取所有地区列表
				List<Area> areaList = areaService.getAllArea();
				modelMap.put("success", true);
				modelMap.put("shop", shopExecution.getShop());
				modelMap.put("areaList", areaList);
			}
			//获取所有区域信息
		}catch(Exception e){
			modelMap.put("success", false);
			modelMap.put("errMsg", "获取店铺数据异常");
			return modelMap ;
		}
		
		return modelMap ;
		
	}

	
	/**
	 * 修改店铺信息
	 * @param rquest
	 * @return
	 */
	@RequestMapping(value = "/modifyShop" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyShop(HttpServletRequest request) {
		// 定义返回结果集
		Map<String, Object> modelMap = new HashMap<>();

		// 先对验证码进行验证
		if (!VerifyCodeUtil.checkVerifyCode(request)) {
			// 不通过则返回
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			System.out.println("验证码不正确");
			return modelMap;
		}
		// 1、接收参数，转换成实体类,以及得到上传的图片
		// 得到shop的字符串,须和前端定好shopStr这个名称
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// System.out.println(shopStr);
		// 先定义JSON转换工具对象
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		// 从request得到shop的字符串，并转换为Shop实体类
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			// 若转换出现异常，则返回数据错误提示
			System.out.println("转换店铺实例失败");
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			// System.out.println(e.getMessage());
			return modelMap;
		}
		// 得到上传的图片
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		// System.out.println("0000000777");
		// 从request上下文，从而构造文件流
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

		// System.out.println("0000000888");
		// 判断是否是图片文件流和是否为空
		if (resolver.isMultipart(request)) {
			// 强制转换request成MultipartHttpServletRequest
			multipartRequest = (MultipartHttpServletRequest) request;

			// 得到文件流
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");

		} else {
			// 为空，则返回数据报告
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		
		// 将数据存入后台
		if (shop != null && shop.getId() != null) {
			try {
			
				// 从session中得到注册者的id，之前登录就已经在session保存personInfo
				// PersonInfo user =
				// (PersonInfo)request.getSession().getAttribute("user");
				// 将用户id作为店铺主人
				shop.setOwnerId(8L);
				System.out.println("shop.area.id:"+ shop.getArea().getId());
				//System.out.println("shop.area.name:"+ shop.getArea().getName());
				
				ShopExecution se = shopService.modifyShop(shop, shopImg);
				// 当添加成功，则se的状态为CHECK
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					// 设置返回值成功标志
					modelMap.put("success", true);
					// System.out.println("添加成功");     
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
					return modelMap;
					// System.out.println("添加失败");
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
				// System.out.println("添加异常");
				// e.printStackTrace();
			}
		}

		return modelMap;

	}

	/**
	 * 得到某个用户的店铺，用户id根据session获取
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getShopList" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		
		try {
			//这里先手动给个id，实际上应该在session中获取
			Long userId = 8L;
			Shop shopCondition = new Shop();
			shopCondition.setOwnerId(userId);
			System.out.println("得到页码");
			// 得到页码
			int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
			System.out.println("pageIndex:"+pageIndex);
			// 得到每页最大容量
			int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

			ShopExecution shopExecution = shopService.getShopList(shopCondition, pageIndex, pageSize);
			// 获取成功
			if (shopExecution.getState() == 1) {
				modelMap.put("shopList", shopExecution.getShopList());
				modelMap.put("count", shopExecution.getCount());
				// 成功标志
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", shopExecution.getStateInfo());
			}
		}catch (Exception e) {
			//异常则返回提示
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap ;
		}
		return modelMap;
	}
	
}
