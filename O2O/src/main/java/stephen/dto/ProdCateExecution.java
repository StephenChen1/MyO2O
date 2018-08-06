package stephen.dto;

import java.util.List;

import stephen.entity.ProductCategory;
import stephen.enums.ProdCateStateEnum;

public class ProdCateExecution {

	private int state ;
	
	private String stateInfo ;
	
	private List<ProductCategory> productCategoryList ;
	
	private ProductCategory productCategory ;
	
	private int count ;
	
	//无参构造函数
	public ProdCateExecution(){
		
	}
	
	//成功得到商品类别列表的构造函数
	public ProdCateExecution(ProdCateStateEnum stateEnum , List<ProductCategory> list ){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo() ;
		this.productCategoryList = list ;
	}
	
	//成功得到某个商品类别的构造函数
	public ProdCateExecution(ProdCateStateEnum stateEnum , ProductCategory productCategory){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo() ;
		this.productCategory = productCategory;
	}
	
	//操作失败时的构造函数，只有枚举一个参数
	public ProdCateExecution(ProdCateStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo() ;
	}
	
	//通过枚举设置状态码和状态标识信息
	public void setStateEnum(ProdCateStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public int getState() {
		return state;
	}

	

	public String getStateInfo() {
		return stateInfo;
	}

	

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
