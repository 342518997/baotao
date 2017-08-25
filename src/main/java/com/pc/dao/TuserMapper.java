package com.pc.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;
import com.pc.model.Tuser;
/**
 * @author 彭冲 用户登录 授权 接口
 */
@Repository("UsersMapper")
public interface TuserMapper {
	// 根据账号查询登录
	Tuser Login(String username);

	// 根据账号查询角色信息
	Set<String> RoleName(String username);

	// 根据账号查询权限信息
	Set<String> PermissionName(String username);
	
	//注册账号
	int addRegister(Tuser tuser);
}
