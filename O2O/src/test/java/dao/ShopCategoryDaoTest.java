package dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import commom.BaseTest;
import stephen.dao.ShopCategoryDao;
import stephen.entity.ShopCategory;
import stephen.service.ShopCategoryService;

public class ShopCategoryDaoTest extends BaseTest{

	@Autowired
	private ShopCategoryDao shopCategoryDao ;
	@Autowired
	private ShopCategoryService shopCategoryService ;
	
	@Test
	public void testGetCategoryById(){
		ShopCategory s = shopCategoryDao.getCategoryById(10L);
		//System.out.println(s.getId());
		//System.out.println(s.getName());
	}
	
	@Test
	@Ignore
	public void testqueryShopCategory(){
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setParent(new ShopCategory());
		List<ShopCategory> shopCategoryList = shopCategoryService.queryShopCategory(shopCategory);
		System.out.println(shopCategoryList.size());
		for(ShopCategory s : shopCategoryList){
			System.out.println(s.getParent().getId());
		}
	}
	
}
