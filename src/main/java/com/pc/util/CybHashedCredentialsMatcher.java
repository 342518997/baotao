package com.pc.util;

import com.pc.model.Tuser;
import com.pc.service.TuserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author asus 密码匹配
 *
 */
public class CybHashedCredentialsMatcher extends HashedCredentialsMatcher {

	// 创建缓存的对象
	
	private Cache<String, AtomicInteger> passwordRetryCache;


	//读取 ehcache.xml里的配置 初始化session
	
	public CybHashedCredentialsMatcher(CacheManager cacheManager) {

		passwordRetryCache = cacheManager.getCache("passwordRetryCache");


	}

	// 匹配密码
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

		// 拿到账号

		String userName = (String) token.getPrincipal();

		// 判断登录次数

		AtomicInteger retryCount = passwordRetryCache.get(userName);
		
		// 登录错误一次 retryCount + 1

		if (retryCount == null) {

			retryCount = new AtomicInteger(0);

			passwordRetryCache.put(userName, retryCount);

		}
		
		//获取 request对象
		
		HttpServletRequest request = ((ServletRequestAttributes)
				RequestContextHolder.getRequestAttributes()).getRequest();
		
		//获取 Session对象
		
		HttpSession session = request.getSession();
		
		//保存登录次数
		
		session.setAttribute("userName", 5-retryCount.get());
		
		// 超过5次 锁账号

		if (retryCount.incrementAndGet() > 5) {

			// 抛出账号锁定异常

			throw new ExcessiveAttemptsException();

		}
		
		// 登录成功 移除缓存

		boolean matches = super.doCredentialsMatch(token, info);

		if (matches) {

			passwordRetryCache.remove(userName);

		}
		
		return matches;
	}


}
