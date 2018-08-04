package dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import commom.BaseTest;
import stephen.dao.ShopDao;
import stephen.entity.Area;
import stephen.entity.PersonInfo;
import stephen.entity.Shop;
import stephen.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{

	@Autowired
	private ShopDao shopDao ;
	
	@Test
	@Ignore
	public void testInsertShop(){
		
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
		shop.setImg("test");
		shop.setName("测试店铺3");
		shop.setDescription("测试店铺描述3");
		shop.setPhone("15627521030");
		shop.setAddress("茂名2");
		shop.setPriority(30);
		shop.setEnableStatus(1);
		shop.setCreateTime(new Date());
		//shop.setLastEditTime();
		shop.setAdvice("审核中");
		
		int effectedNum = shopDao.insertShop(shop);
		System.out.println("店铺id:" + shop.getId());
		assertEquals(1,effectedNum);
	}
	
	
	@Test
	public void testUpdateShop(){
		Shop shop = new Shop();
		shop.setId(32L);
		shop.setName("stephen");
		shop.setAddress("电白区");
		shop.setDescription("新描述");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1,effectedNum);
	}
	
	
	
}
