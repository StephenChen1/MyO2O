package stephen.util;

public class PageIndexConvertUtil {

	/**
	 * 将页开始下标转换成行下标
	 * @param pageIndex 页码，正常从1开始
	 * @param pageSize  每页最大数据条数
	 * @return
	 */
	public static int pageIndexToRowIndex(int pageIndex,int pageSize){
		
		//页码下标不正常就返回0
		return (pageIndex > 0) ? (pageIndex -1) * pageSize : 0 ;
	}
	
}
