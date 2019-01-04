/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopuser.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员管理Entity
 * @author zhb
 * @version 2018-12-01
 */
public class ShopUser extends DataEntity<ShopUser> {
	
	private static final long serialVersionUID = 1L;
	private String username;		// 会员名称
	private String usernickname;		// 会员昵称
	private String password;		// 密码
	private String email;		// 邮箱
	private String phone;		// 电话
	private String question;		// 找回密码的问题
	private String answer;		// 找回密码的答案
	private Date lastlogintime;		// 最后一次登陆时间
	private String lastloginip;		// 最后一次登录ip
	private Date regeistdate;		// 注册时间
	private String userlevel;		// 会员等级
	private String description;		// 描述
	private String userlevelname;
	private Integer islock;
	
	public Integer getIslock() {
		return islock;
	}

	public void setIslock(Integer islock) {
		this.islock = islock;
	}

	public String getUserlevelname() {
		return userlevelname;
	}

	public void setUserlevelname(String userlevelname) {
		this.userlevelname = userlevelname;
	}

	public ShopUser() {
		super();
	}

	public ShopUser(String id){
		super(id);
	}

	@Length(min=1, max=255, message="会员名称长度必须介于 1 和 255 之间")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=255, message="会员昵称长度必须介于 0 和 255 之间")
	public String getUsernickname() {
		return usernickname;
	}

	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}
	
	@Length(min=0, max=255, message="密码长度必须介于 0 和 255 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=255, message="邮箱长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=1, max=255, message="电话长度必须介于 1 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="找回密码的问题长度必须介于 0 和 255 之间")
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	@Length(min=0, max=255, message="找回密码的答案长度必须介于 0 和 255 之间")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	
	@Length(min=0, max=255, message="最后一次登录ip长度必须介于 0 和 255 之间")
	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegeistdate() {
		return regeistdate;
	}

	public void setRegeistdate(Date regeistdate) {
		this.regeistdate = regeistdate;
	}
	
	@Length(min=1, max=255, message="会员等级长度必须介于 1 和 255 之间")
	public String getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}