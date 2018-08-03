package stephen.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stephen.entity.Area;
import stephen.service.AreaService;



@Controller
@RequestMapping("/superadmin")
public class AreaController {

	//日志(引入的是slf4j的Logger类）
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	private AreaService areaService ;
	
	
	@RequestMapping(value = "/listarea" , method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAllArea(){
		logger.info("========start=======");
		long startTime = System.currentTimeMillis();
		
		Map<String,Object> modelMap = new HashMap<>();
		List<Area> areaList = new ArrayList<>();
		try{
			areaList = areaService.getAllArea();
			//至于为什么是rows和total，是为了前端分页方便
			modelMap.put("rows", areaList);
			modelMap.put("total", areaList.size());
		}catch(Exception e){
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		
		logger.error("test Error");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime:[{}ms]" ,(endTime - startTime));
		logger.info("======end========");
		return modelMap;
	}
	
}