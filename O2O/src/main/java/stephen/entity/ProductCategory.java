package stephen.entity;

import java.util.Date;

//商品类别
public class ProductCategory {

	//商品类别id
	private Long id ;
	//类别名称
	private String name ;
	//权重
	private Integer priority ;
	//所属店铺id
	private Long shopId ;
	//创建时间
	private Date createTime ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
