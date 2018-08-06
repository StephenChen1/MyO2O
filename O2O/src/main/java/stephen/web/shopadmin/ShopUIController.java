package stephen.web.shopadmin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stephen.util.HttpServletRequestUtil;

//该Controller专门处理转发返回店铺所需界面
@Controller
@RequestMapping("/shopUI")
public class ShopUIController {

	@RequestMapping(value="/shopInfoOperation", method=RequestMethod.GET)
	public String register(){
		//返回店铺注册登记界面（也是修改店铺信息界面）
		return "shop/shopInfoOperation";
	}
	
	@RequestMapping(value="/shopList", method=RequestMethod.GET)
	public String shopList(){
		//返回店铺列表界面
		return "shop/shopList";       
	}
	
	
	@RequestMapping(value="/shopOperation", method=RequestMethod.GET)
	public String shopOperation(HttpServletRequest request){
		//得到传过来的shopId，将其保存在session中
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		System.out.println("UI控制器得到shopId:"+shopId);
		request.getSession().setAttribute("currentShopId", shopId);
		request.getSession().setMaxInactiveInterval(30*60);
		//返回店铺注册登记界面
		return "shop/shopOperation";
	}
	
	//店铺商品类别界面
	@RequestMapping(value = "/prodCateManage",method = RequestMethod.GET)
	public String prodCateManage(){
		return "shop/prodCateManage";
	}
	
}
