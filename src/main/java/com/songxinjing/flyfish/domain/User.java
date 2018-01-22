package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * 账户信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Id
	@Column(length = 16)
	private String userId;

	/**
	 * 姓名
	 */
	@Column(length = 16)
	private String name;

	/**
	 * 邮箱
	 */
	@Column(length = 64)
	private String email;

	/**
	 * 手机号码
	 */
	@Column(length = 16)
	private String phone;

	/**
	 * 密码签名
	 */
	@Column(length = 64)
	private String password;

	/**
	 * 用户状态：0-正常；1-冻结；2-删除
	 */
	@Column
	private Byte state;

	/**
	 * 用户所属用户组列表
	 */
	@ManyToMany(mappedBy = "members")
	private Set<UserGroup> groups;

	/**
	 * 用户所属角色列表
	 */
	@ManyToMany(mappedBy = "users")
	private Set<Role> roles;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Set<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<UserGroup> groups) {
		this.groups = groups;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}