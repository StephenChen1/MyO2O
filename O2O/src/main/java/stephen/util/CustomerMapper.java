package stephen.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

//解决mybatis懒加载时报错 Could not write content: No serializer found for class org.apache.ibatis.execut
public class CustomerMapper extends ObjectMapper {

	
	private static final long serialVersionUID = -815230677336572609L;

	public CustomerMapper(){
		this.setSerializationInclusion(Include.NON_NULL);
		this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
	

}
