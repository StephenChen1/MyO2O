package dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import commom.BaseTest;
import stephen.dao.ProdCateDao;
import stephen.entity.ProductCategory;

public class ProdCateDaoTest extends BaseTest {

	@Autowired
	private ProdCateDao prodCateDao ;
	
	@Test
	public void testQueryProdCateByShopId(){
		Long shopId = 20L ;
		List<ProductCategory> list = prodCateDao.queryProdCateByShopId(shopId);
		System.out.println(list.size());
	}
	
	@Test
	@Ignore
	public void testBatchAddProdCate(){
		ProductCategory p1 = new ProductCategory();
		p1.setName("测试类别1");
		p1.setPriority(99);
		p1.setShopId(20L);
		ProductCategory p2 = new ProductCategory();
		p2.setName("测试类别2");
		p2.setPriority(88);
		List<ProductCategory> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		p2.setShopId(20L);
		int effectedNum = prodCateDao.batchInsertProdCate(list);
		System.out.println("影响行数：" + effectedNum);
	}
	
	@Test
	@Ignore
	public void testDeleteProdCate(){
		ProductCategory p1 = new ProductCategory();
		p1.setName("测试类别1");
		p1.setPriority(99);
		p1.setShopId(20L);
		int effectedNum = prodCateDao.deleteProdCate(22L, 20L);
		System.out.println("影响行数：" + effectedNum);
	}
	
	
	
}
