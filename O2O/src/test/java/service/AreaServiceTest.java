package service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import commom.BaseTest;
import stephen.entity.Area;
import stephen.service.AreaService;


public class AreaServiceTest extends BaseTest{

	
	@Autowired
	private AreaService areaService ;
	
	@Test
	public void testGetAllArea(){
		List<Area> areaList = areaService.getAllArea();
		assertEquals(4,areaList.size());
	}
	
}
