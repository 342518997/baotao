package com.pc.model;

/**
 * @author asus
 * 权限实体类
 */
public class Tpermission {
	private Integer id;
	private String  permissionName;
	private Integer roleId;
	public Tpermission(){
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	

}
