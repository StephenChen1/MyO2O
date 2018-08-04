package stephen.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

//店铺类别
public class ShopCategory implements Serializable{

	
	private static final long serialVersionUID = 4204968394193672466L;
	//类别id
	private Long id ;
	//类别名称
	private String name ;
	//描述
	private String description ;
	//图片
	private String img ;
	//权重
	private Integer priority ;
	//创建时间
	private Date createTime ;
	//更新时间
	private Date lastEditTime ;
	//关联上级类别,全部类别均在一个表，当parent为空时，说明是顶级类别 
	private ShopCategory parent ;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
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
	public ShopCategory getParent() {
		return parent;
	}
	public void setParent(ShopCategory parent) {
		this.parent = parent;
	}
	
	
}
