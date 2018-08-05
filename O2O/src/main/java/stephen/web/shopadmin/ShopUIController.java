package stephen.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//该Controller专门处理转发返回店铺所需界面
@Controller
@RequestMapping("/shopUI")
public class ShopUIController {

	@RequestMapping(value="/shopInfoOperation", method=RequestMethod.GET)
	public String register(){
		//返回店铺注册登记界面
		return "shop/shopInfoOperation";
	}
	
	@RequestMapping(value="/shopList", method=RequestMethod.GET)
	public String shopList(){
		//返回店铺注册登记界面
		return "shop/shopList";
	}
	
	
	@RequestMapping(value="/shopOperation", method=RequestMethod.GET)
	public String shopOperation(){
		//返回店铺注册登记界面
		return "shop/shopOperation";
	}
	
}
