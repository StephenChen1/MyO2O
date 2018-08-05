package service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import commom.BaseTest;
import stephen.dto.ShopExecution;
import stephen.entity.Area;
import stephen.entity.PersonInfo;
import stephen.entity.Shop;
import stephen.entity.ShopCategory;
import stephen.service.ShopService;

public class ShopServiceTest extends BaseTest {

	@Autowired
	private ShopService shopService ;
	
	@Test
	@Ignore
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
		shop.setOwnerId(10L);
		
		shop.setName("测试店铺5");
		shop.setDescription("测试店铺描述5");
		shop.setPhone("15627521030");
		shop.setAddress("茂名5");
		shop.setPriority(30);
		shop.setAdvice("审核中");
		
		//CommonsMultipartFile file = new CommonsMultipartFile(new File("E:/o2o/image/test.png"));
	}
	
	
	@Test
	@Ignore
	public void testGetShopById(){
		Long shopId = 18L;
		ShopExecution s = shopService.getShopById(shopId);
		System.out.println(s.getStateInfo());
		System.out.println(s.getShop().getName());
	}
	
	
	
	@Test
	public void testGetShopList(){
		
		Shop shopCondition = new Shop();
		shopCondition.setOwnerId(8L);
		int pageIndex = 1 ;
		int pageSize = 30 ;
		ShopExecution shopExecution = shopService.getShopList(shopCondition, pageIndex, pageSize);
		if(shopExecution.getState() == 1){
			System.out.println(shopExecution.getStateInfo());
			System.out.println(shopExecution.getShopList().size());
		}
	}

}
