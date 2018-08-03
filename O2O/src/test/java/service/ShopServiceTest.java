package service;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import commom.BaseTest;
import stephen.entity.Area;
import stephen.entity.PersonInfo;
import stephen.entity.Shop;
import stephen.entity.ShopCategory;
import stephen.service.ShopService;

public class ShopServiceTest extends BaseTest {

	@Autowired
	private ShopService shopService ;
	
	@Test
	public void testAddShop(){
		Shop shop = new Shop();
		
		Area area = new Area();
		area.setId(3L);
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setId(15L);
		PersonInfo owner = new PersonInfo();
		owner.setUserId(8L);
		
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setOwner(owner);
		
		shop.setName("测试店铺5");
		shop.setDescription("测试店铺描述5");
		shop.setPhone("15627521030");
		shop.setAddress("茂名5");
		shop.setPriority(30);
		shop.setAdvice("审核中");
		
		//CommonsMultipartFile file = new CommonsMultipartFile(new File("E:/o2o/image/test.png"));
	}
	
}
