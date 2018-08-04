package stephen.util;

import java.io.File;

public class PathUtil {

	//得到系统分隔符
	//private static String seperator = File.separator;
	//下面的方法也行
	private static String seperator = System.getProperty("file.separator");
	//System.out.println("seperator:" + seperator );
	
	//得到基地址
	public static String getImgBasePath(){
		//得到操作系统名
		String os = System.getProperty("os.name");
		String basePath = "" ;
		if(os.toLowerCase().startsWith("win")){
			//如果是win操作系统，则文件路径如下
			basePath = "D:/o2o/image/";
		}else{
			//如果是其他系统
			basePath = "/home/stephen/o2o/image";
		}
		System.out.println("seperator:" + seperator );
		//再将路径分隔符/转换成系统的分隔符
		basePath = basePath.replace("/", seperator);
		return basePath ;
	}
	
	//得到店铺图片相对路径，主要是加入店铺id作为路径
	public static String getShopImagePath(long shopId){
		//System.out.println("系统分隔符："+ seperator);
		String imagePath = "/upload/images/item/shop/"+ shopId +"/";
		return imagePath.replace("/", seperator);
	}
}
