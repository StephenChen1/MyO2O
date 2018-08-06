package stephen.enums;

public enum ProdCateStateEnum {

	SUCCESS(1,"操作成功"),
	INNER_ERROR(-1001,"操作失败"),
	EMPTY_SHOPID(-1002,"店铺id为空"),
	EMPTY_PRODCATE(-1003,"商品类别列表为空");
	
	
	private int state ;
	
	private String stateInfo ;
	
	private ProdCateStateEnum(int state , String stateInfo){
		this.state = state ;
		this.stateInfo = stateInfo ;
	}
	
	//根据传入的state得到相应的枚举
	public static ProdCateStateEnum stateOf(int state){
		for(ProdCateStateEnum enum1 : ProdCateStateEnum.values()){
			if(enum1.state == state){
				return enum1 ;
			}
		}
		return null ;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	
	
	
}
