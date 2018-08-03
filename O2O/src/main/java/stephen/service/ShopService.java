package stephen.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import stephen.dto.ShopExecution;
import stephen.entity.Shop;

public interface ShopService {

	/**
	 * 添加店铺
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	ShopExecution addShop(Shop shop , CommonsMultipartFile shopImg);
	
}
