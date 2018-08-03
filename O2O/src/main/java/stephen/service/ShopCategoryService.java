package stephen.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import stephen.entity.ShopCategory;

public interface ShopCategoryService {

	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
	
}
