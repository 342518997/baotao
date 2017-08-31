package com.pc.sessiondao;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;

public class Sessions {


    @Test
    public void sessionlist(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SessionDAO dao = context.getBean("sessionDAO",SessionDAO.class);
        Collection<Session> sessions =dao.getActiveSessions();
        System.out.println(sessions.size());
    }
}
