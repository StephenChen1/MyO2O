package stephen.entity;

import java.util.Date;

public class LocalAuth {

	//本地账号id
	private Long id ;
	//用户名（昵称吧，可能）
	private String username ;
	//密码
	private String password ;
	//创建时间
	private Date createTime ;
	//更新时间
	private Date lastEditTime ;
	//和用户信息关联
	private PersonInfo personInfo ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
	
	
}
