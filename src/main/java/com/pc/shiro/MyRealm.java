package com.pc.shiro;


import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.pc.model.Tuser;
import com.pc.service.TuserService;


/**
 * @author 彭冲  shiro 身份认证授权
 *
 */
public class MyRealm extends AuthorizingRealm{

	@Resource
	private TuserService service;


	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// TODO Auto-generated method stub
		if(principalCollection == null){

			return null;

		}
		String username=(String)principalCollection.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		//设置角色
		authorizationInfo.setRoles(service.RoleName(username));
		//设置权限
		authorizationInfo.setStringPermissions(service.PermissionName(username));

		return authorizationInfo;
	}
	//身份认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username=(String)token.getPrincipal();
		Tuser user=service.Login(username);
		if(user==null){
			 throw new UnknownAccountException("账号密码错误!");			
		}
		//密码匹配
		AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(),
				ByteSource.Util.bytes(user.getSalt()),getName());

		return authenticationInfo;
		
	}

}
