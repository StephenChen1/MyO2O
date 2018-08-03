package stephen.entity;

import java.util.Date;
import java.util.List;

public class Product {

	//商品id
	private Long id ;
	//商品名
	private String name ;
	//描述
	private String description;
	//简略图
	private String imgAddr;
	//原价
	private String nomalPrice ;
	//折扣价
	private String promotionPrice ;
	//状态 -1：不可用 0：下架 1：可展示 
	private Integer enableStatus ;
	//权重
	private Integer priority ;
	//创建时间
	private Date createTime ;
	//更新时间
	private Date lastEditTime ;
	//所属类别
	private ProductCategory productCategory ;
	//所属店铺
	private Shop shop ;
	//所含详情图
	private List<ProductImg> productImgList ;
	
	
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getNomalPrice() {
		return nomalPrice;
	}
	public void setNomalPrice(String nomalPrice) {
		this.nomalPrice = nomalPrice;
	}
	public String getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public ProductCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<ProductImg> getProductImgList() {
		return productImgList;
	}
	public void setProductImgList(List<ProductImg> productImgList) {
		this.productImgList = productImgList;
	}
	
	
}
