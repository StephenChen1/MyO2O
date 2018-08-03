package dao;



import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import commom.BaseTest;
import stephen.dao.AreaDao;
import stephen.entity.Area;


public class AreaDaoTest extends BaseTest{

	@Autowired
	private AreaDao areaDao ;
	
	@Test
	public void testGetAllArea(){
		List<Area> areaList = areaDao.getAll();
		assertEquals(4,areaList.size());
	}
	
}
