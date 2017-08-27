package com.pc.mybatis;

import com.pc.dao.TuserMapper;
import com.pc.model.Tuser;
import com.pc.service.TuserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMybatis {
    @Test
    public void Login(){
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        TuserService dao = applicationContext.getBean("TuserService",TuserService.class);
        Tuser tuser = dao.Login("baba");
        System.out.println(tuser.getPassword());
    }
}
