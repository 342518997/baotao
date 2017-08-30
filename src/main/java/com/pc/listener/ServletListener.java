package com.pc.listener;

import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public final class ServletListener implements ServletContextListener {
    public static final Logger logger = Logger.getLogger(ServletListener.class);

    Timer timer;

    public ServletListener() {
        logger.info("constructor starting");

        logger.info("constructor ends");
    }

    public void contextInitialized(ServletContextEvent e) {
        logger.info("contextInitialized starting");

        this.timer = new Timer(true);
        this.timer.schedule(new TimerTask() {
            public void run() {
                try {
                    logger.info("hello world");
                } catch (Exception e) {
                    ServletListener.logger.error("", e);
                }

            }
        }, 0L, (long) (5 * 60) * 1000L);

        logger.info("contextInitialized ends");
    }

    public void contextDestroyed(ServletContextEvent e) {
        logger.info("contextDestroyed starting");
        timer.cancel();
        logger.info("tomcat关闭.");

    }
}  