package com.pc.shiro;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/*
*  彭冲
*  登录并发控制 在线统计 登出
* */
public class KickoutSessionFilter extends AccessControlFilter {


    private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("loginCache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);
        //如果没有登录，直接进行之后的流程
        if(!subject.isAuthenticated() && !subject.isRemembered()){
            return true;
        }
        Session session = subject.getSession();
        String userName =(String) subject.getPrincipal();

        Serializable sessionId = session.getId();
        // 同步控制
        Deque<Serializable> deque = cache.get(userName);
        if(deque==null){
            deque = new LinkedList<>();
            cache.put(userName,deque);

        }
        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }
        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size()>maxSession){

            Serializable serializable = null;

            if(kickoutAfter){//如果踢出后者
                serializable = deque.removeFirst();
            }else {//否则踢出前者
                serializable =deque.removeLast();

            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(serializable));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            }catch (Exception e){

            }

        }


        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                //清空缓存
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(servletRequest);
            //获得请求对象
            HttpServletRequest httpRequest = WebUtils.toHttp(servletRequest);
            if (isAjax(httpRequest)) {
                WebUtils.toHttp(servletResponse).sendError(401);
                return false;
            } else {
                WebUtils.issueRedirect(servletRequest, servletResponse, kickoutUrl);
                return false;
            }
        }
        return  true;
    }
    /**
     * 判断ajax请求
     * @param request
     * @return
     */
    boolean isAjax(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())) ;
    }

}


