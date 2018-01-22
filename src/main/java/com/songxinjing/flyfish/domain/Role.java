package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * 角色信息表实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色码
	 */
	@Id
	@Column(length = 4)
	private String code;

	/**
	 * 角色名称
	 */
	@Column(length = 64)
	private String name;

	/**
	 * 角色描述
	 */
	@Column(length = 255)
	private String descp;

	/**
	 * 包含权限列表
	 */
	@ManyToMany
	private Set<Privilege> privileges;

	/**
	 * 包含用户列表
	 */
	@ManyToMany
	private Set<User> users;

	/**
	 * 包含用户组列表
	 */
	@ManyToMany
	private Set<UserGroup> userGroups;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

}