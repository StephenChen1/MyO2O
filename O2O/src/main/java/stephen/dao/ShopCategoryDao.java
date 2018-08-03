package stephen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import stephen.entity.ShopCategory;

public interface ShopCategoryDao {

	/**
	 * 根据shopCategory的条件得到所需店铺类别
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
	
	/**
	 * 根据id得到该id对应的店铺类别
	 * @param id
	 * @return
	 */
	ShopCategory getCategoryById(@Param("pid") Long id);
}
