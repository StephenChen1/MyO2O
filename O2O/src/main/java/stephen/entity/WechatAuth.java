package stephen.entity;

import java.util.Date;

public class WechatAuth {

	//微信账号id
	private Long id ;
	//微信的openId
	private String openId ;
	//创建时间
	private Date createTime ;
	//和用户关联
	private PersonInfo personInfo ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
	
	
}
