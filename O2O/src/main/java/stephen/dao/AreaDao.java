package stephen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import stephen.entity.Area;



public interface AreaDao {

	//得到所有区域
	List<Area> getAll();
	
	/**
	 * 根据区域id得到区域
	 * @param id
	 * @return
	 */
	Area getAreaById(@Param("areaId")int id);
	
}
