package com.pc.service;

import java.util.Set;

import com.pc.model.Tuser;

/**
 * @author asus 用户登录 授权 service接口
 *
 */
public interface TuserService {
	// 根据账号查询登录
	Tuser Login(String username);

	// 根据账号查询角色信息
	Set<String> RoleName(String username);

	// 根据账号查询权限信息
	Set<String> PermissionName(String username);
	
	//注册账号
	int addRegister(Tuser tuser);

}
