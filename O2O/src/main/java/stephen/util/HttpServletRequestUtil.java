package stephen.util;

import javax.servlet.http.HttpServletRequest;

//Http请求参数转换工具
public class HttpServletRequestUtil {

	//根据key将request中的值转换成int类型(Integer.decode(String))
	public static int getInt(HttpServletRequest request , String key){
		try{
		    return Integer.decode(request.getParameter(key));
		}catch(Exception e){
			//由于Integer的转换方法会有可能抛出NumberFormatException，所以需要捕获处理
			return -1;
		}
	}
	
	
	//根据key将request中的值转换成long类型(Long.decode(String))
	public static long getLong(HttpServletRequest request , String key){
			try{
			    return Long.valueOf((request.getParameter(key)));
			}catch(Exception e){
				//由于Long的转换方法会有可能抛出NumberFormatException，所以需要捕获处理
				return -1L;
			}
	}
	//根据key将request中的值转换成double类型
	public static double getDouble(HttpServletRequest request , String key){
			try{
			    return Double.valueOf((request.getParameter(key)));
			}catch(Exception e){
				//由于Double的转换方法会有可能抛出NumberFormatException，所以需要捕获处理
				return -1d;
			}
	}
	
	//根据key将request中的值转换成boolean类型
	public static boolean getBoolean(HttpServletRequest request , String key){
			try{
				return Boolean.valueOf((request.getParameter(key)));
			}catch(Exception e){
				//由于Boolean的转换方法会有可能抛出NumberFormatException，所以需要捕获处理
				return false;
			}
	}
	
	//根据key将request中的值转换成String类型
	public static String getString(HttpServletRequest request , String key){
			try{
				//根据key得到值
				String result = request.getParameter(key);
				//System.out.println("result:" + result);
				if(result != null){
					//若结果不为空，则去掉两边的空格
					result = result.trim();
				}
				if("".equals(result)){
					//若果去掉空格后就是空字符串，那就返回null
					result = null ;
				}
				return result ;
			}catch(Exception e){
				//由于Integer的转换方法会有可能抛出NumberFormatException，所以需要捕获处理
				return null;
			}
			
	}
	
}
