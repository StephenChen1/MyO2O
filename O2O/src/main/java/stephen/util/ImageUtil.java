package stephen.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	//得到classpath路径,用于得到水印
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	//时间格式，做文件名用
	private static final SimpleDateFormat sDFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	//随即数对象，用于产生随机数作文件名
	private static Random random = new Random();
	
	
	//将图片输入流thumbnail的内容加上水印，然后输出到  基路径+targetAddr+随机文件名+扩展名  这个全路径下
	//返回值为图片存储的相对路径，存储出错返回空
	public static String generateThumbnail(CommonsMultipartFile thumbnail,String targetAddr){
		//文件名，一个随机数
		String realFileName = getRandomFileName();
		//扩展名，即后缀
		String extension = getFileExtension(thumbnail);
		//创建目录，在基本路径上创建目的文件夹
		makeDirPath(targetAddr);
		//相对路径，目的路径+文件名+文件扩展名
		String relativeAddr = targetAddr + realFileName + extension ;
		//目的路径（文件的全绝对路径,即基路径+相对路径）
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try{
			Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.png")), 0.25f)
			.outputQuality(0.8f).toFile(dest);
		}catch(Exception e){
			e.printStackTrace();
			return null ;
		}
		return relativeAddr ;
	}
	
	//构造随机数作为图片文件名
	private static String getRandomFileName(){
		//获取随机的五位数，即（10000 ~ 99999）
		int randomNum = random.nextInt(89999) + 10000 ;
		//获取当前时间，并转换成相应的格式
		String nowTimeStr = sDFormat.format(new Date());
		
		return nowTimeStr + randomNum ;
	}
	
	
	//得到输入文件流的扩展名，返回.后面的就行
	private static String getFileExtension(CommonsMultipartFile cFile){
		//得到输入流的全名
		String originalFileName = cFile.getOriginalFilename();
		//得到返回后缀名
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	
	
	//创建参数所涉及的路径，比如 /home/stephen/test.png
	//那么home stephen 这两个文件夹都要创建
	private static void makeDirPath(String targetAddr){
		//得到基路径，才能在此基础上建立文件夹
		String realFilePath = PathUtil.getImgBasePath() + targetAddr ;
		//创建文件夹
		File dirPath = new File(realFilePath);
		//判断该文件夹是否存在，不存在则递归创建出来
		if(!dirPath.exists()){
			dirPath.mkdirs();
		}
	}
	
	
	/*public static void main(String[] args) throws Exception{
		//得到当前线程的资源路径，也就是classpath，也是项目的resources目录
		String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		Thumbnails.of(new File("E:/o2o/image/test.png")).size(200, 200)
		.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.png")), 0.25f)
		.outputQuality(0.8f).toFile("E:/o2o/image/test2.png");
		//Thumbnails.of(new File(basePath + "myimg.png")).size(20, 20)
		//.outputQuality(1.0f).toFile("E:/o2o/image/test3.png");
	}*/
	
}
