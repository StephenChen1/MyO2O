package stephen.dao;

import stephen.entity.Shop;

public interface ShopDao {

	/**
	 * 增加店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
