package stephen.enums;

public enum ShopStateEnum {

	CHECK(0,"审核中"),
	OFFLINE(-1,"非法店铺"),
	SUCCESS(1,"操作成功"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_SHOPID(-1002,"shopId为空"),
	NULL_SHOP(-100,"shop内容为空");
	
	
	private int state ;
	private String stateInfo ;
	
	//私有的构造方法，不让外界创建新的枚举
	private ShopStateEnum(int state , String stateInfo){
		this.state = state ;
		this.stateInfo = stateInfo ;
	}
	
	//根据传入的state得到相应的枚举
	public ShopStateEnum stateOf(int state){
		for(ShopStateEnum stateEnum: values()){
			if(stateEnum.state == state){
				return stateEnum ;
			}
		}
		return null ;
	}

	//对属性的访问，不给外界set，只能get
	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	
	
}
