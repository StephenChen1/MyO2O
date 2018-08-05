package stephen.dto;

import java.util.List;

import stephen.entity.Shop;
import stephen.enums.ShopStateEnum;

//店铺参数传递值,可用于各种传店铺值得方法
public class ShopExecution {

	//状态
	private int state ;
	//状态标识
	private String stateInfo ;
	
	//店铺总数量（不只list中那么多）
	private int count ;
	
	//操作的店铺
	private Shop shop ;
	
	//店铺列表
	private List<Shop> shopList ;
	
	//无参构造
	public ShopExecution(){
		
	}
	
	//店铺操作失败时构造方法，将状态和状态标识填上，其他不填
	public ShopExecution(ShopStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//店铺操作成功时构造方法，一个店铺的情况
	public ShopExecution(ShopStateEnum stateEnum,Shop shop){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop ;
	}
	
	//店铺操作成功时构造方法，多个店铺的情况
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList ;
		
	}

	public int getState() {
		return state;
	}

	/*//不给单独设置状态
	public void setState(int state) {
		this.state = state;
	}
	//不给单独设置状态信息，只能传入枚举类型同时设置状态和状态信息
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}*/
	public void setStateAndInfo(ShopStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	public String getStateInfo() {
		return stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
	
	
}
