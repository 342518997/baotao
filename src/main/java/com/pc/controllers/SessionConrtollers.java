package com.pc.controllers;

import net.sf.json.JSONArray;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@Controller
@RequestMapping("/")
public class SessionConrtollers {

    @Autowired
    private SessionDAO  sessionDAO;

    @RequestMapping("sessionlist")
    public void sessionlist(HttpServletResponse response){

        PrintWriter out =null;
        try {
             response.getWriter();

            Collection<Session> sessions = sessionDAO.getActiveSessions();

            System.out.println(sessions.size());

            JSONArray array = JSONArray.fromObject(sessions);

            System.out.println(array.size());

            out.println(array);

            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
