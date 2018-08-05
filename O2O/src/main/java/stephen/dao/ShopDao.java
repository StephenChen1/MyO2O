package stephen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * 根据id获取店铺id,id为null则查询全部
	 * @param shopId
	 * @return
	 */
	Shop queryShop(Long shopId);
	
	/**
	 * 根据条件查询店铺列表,有开始下标和最大长度限制
	 * @param shopCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex , @Param("pageSize")int pageSize);
	
	/**
	 * 查询符合条件的店铺总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
}
