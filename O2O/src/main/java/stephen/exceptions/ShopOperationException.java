package stephen.exceptions;

//店铺操作事务异常抛出的异常，继承RuntimeException才能终止事物的执行，并回滚事务
public class ShopOperationException extends RuntimeException {

	//显式给出序列化ID，不报警告
	private static final long serialVersionUID = 4819708759314375523L;

	//构造方法，调用父类
	public ShopOperationException(String msg){
		super(msg);
	}
	

}
