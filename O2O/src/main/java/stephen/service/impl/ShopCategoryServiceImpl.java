package stephen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stephen.dao.ShopCategoryDao;
import stephen.entity.ShopCategory;
import stephen.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired
	private ShopCategoryDao shopCategoryDao ;
	
	@Override
	public List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition) {
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
		return shopCategoryList;
	}

}
