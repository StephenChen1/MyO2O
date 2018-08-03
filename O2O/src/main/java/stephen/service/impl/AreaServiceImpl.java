package stephen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stephen.dao.AreaDao;
import stephen.entity.Area;
import stephen.service.AreaService;



@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao ;
	
	@Override
	public List<Area> getAllArea() {
		
		return areaDao.getAll();
	}

}
