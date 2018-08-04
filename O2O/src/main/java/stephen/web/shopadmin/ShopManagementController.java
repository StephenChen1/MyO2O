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
import stephen.entity.PersonInfo;
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
	
}
