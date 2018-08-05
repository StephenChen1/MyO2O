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
	
	/**
	 * 根据店铺id查询店铺信息
	 * @param shopId
	 * @return
	 */
	ShopExecution getShopById(Long shopId);
	
	/**
	 * 修改店铺信息
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	ShopExecution modifyShop(Shop shop , CommonsMultipartFile shopImg);
	
	
	/**
	 * 根据传进来的shopCondition，调用dao获取数据
	 * @param shopCondition
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex , int pageSize);
}
