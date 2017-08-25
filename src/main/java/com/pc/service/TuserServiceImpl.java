package com.pc.service;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pc.dao.TuserMapper;
import com.pc.model.Tuser;

/**
 * @author asus service接口的实现
 *
 */
@Service("TuserService")
public class TuserServiceImpl implements TuserService{
	@Resource
	private TuserMapper dao;
	@Override
	public Tuser Login(String username) {
		// TODO Auto-generated method stub
		return this.dao.Login(username);
	}

	@Override
	public Set<String> RoleName(String username) {
		// TODO Auto-generated method stub
		return this.dao.RoleName(username);
	}

	@Override
	public Set<String> PermissionName(String username) {
		// TODO Auto-generated method stub
		return this.dao.PermissionName(username);
	}

	@Override
	public int addRegister(Tuser tuser) {
		// TODO Auto-generated method stub
		return this.dao.addRegister(tuser);
	}

}
