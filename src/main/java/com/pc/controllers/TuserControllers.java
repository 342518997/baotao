package com.pc.controllers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pc.model.Tuser;
import com.pc.service.TuserService;
import com.pc.util.EndecryptUtils;
import com.pc.util.ValidateCode;
import net.sf.json.JSONObject;

/**
 * @author asus 登录 注册 授权 控制器
 *
 */
@Controller
@RequestMapping(value  = "/")
public class TuserControllers {
	
	@Resource
	private TuserService service;

	//登录
	@RequestMapping("/login")
	public void Login(Tuser user,String verification,boolean rememberMe, HttpServletRequest request,HttpServletResponse response) throws IOException {

		Subject subject = SecurityUtils.getSubject();

		Session session = subject.getSession();

		String errorMsg = "";
		
		boolean account=false;
		
		JSONObject json = new JSONObject();
		
		PrintWriter out =response.getWriter();

		Tuser user1=null;

         user1 = (Tuser) session.getAttribute(user.getUserName());

		//拿到验证码
		
		String code = (String) request.getSession().getAttribute("validateCode");
		
		//拿到用户输入的验证码
		
		String submitCode =(String) WebUtils.getCleanParam(request, "verification");
		
		
		//判断验证码
		
		if(code == null || !code.equals(submitCode)){
			
			errorMsg = "验证码错误！";
						
		}else if(code.equals(submitCode)) {

			UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());

                try {

                    subject.login(token);

                } catch (UnknownAccountException e) {

                    errorMsg = "用户名或密码错误！";

                    account = true;

                } catch (IncorrectCredentialsException e) {

                    errorMsg = "用户名或密码错误！";

                } catch (ExcessiveAttemptsException e) {

                    errorMsg = "登录失败多次，账户锁定10分钟";

                } catch (AuthenticationException e) {

                    errorMsg = "其他错误" + e.getMessage();

                }

            //判断账号是否已经登录

            if (errorMsg == "" && user1 != null && user.getUserName().equals(user1.getUserName())) {

                errorMsg = "账号已经登录，请勿重复登录！";

                account = true;

            }

        }

		if (errorMsg != null && errorMsg != "") {
						
			if(code.equals(submitCode) && !account){
									
				int temp = (int)session.getAttribute("userName");
				
				if(temp > 0){
										
					json.element("row", "还可以登录:"+temp+"次!");
					
				}
				
			}
			
			json.element("booe", false);
			
			json.element("errorMsg", errorMsg);
						
			out.print(json);
		
			
		}else{

            json.element("booe", true);

			session.setAttribute(user.getUserName(), user);


	/*		System.out.println("SESSION ID = " + SecurityUtils.getSubject().getSession().getId());
			System.out.println("用户名：" + SecurityUtils.getSubject().getPrincipal());
			System.out.println("HOST：" + SecurityUtils.getSubject().getSession().getHost());
			System.out.println("TIMEOUT ：" + SecurityUtils.getSubject().getSession().getTimeout());
			System.out.println("START：" + SecurityUtils.getSubject().getSession().getStartTimestamp());
			System.out.println("LAST：" + SecurityUtils.getSubject().getSession().getLastAccessTime());
*/
			out.println(json);
			
		}
				
	}
	
	//注册
	@RequestMapping("/register")
	public void Stdent(Tuser tuser,HttpServletResponse response) throws IOException{
		
		Tuser user=service.Login(tuser.getUserName());
		
		PrintWriter out=response.getWriter();
		
		JSONObject json = new JSONObject();
		
		if(user!=null){
			
			json.element("boole", false);
			
			json.element("userName", "账号已经注册了!");		
					
		}else{
			
			Tuser tuser2=EndecryptUtils.EncryptUser(tuser);
						
			int row=service.addRegister(tuser2);
			
			if(row>0){
				
				json.element("boole", true);
				
				json.element("userName", "注册成功!");
				
			}
			
		}
		
		out.println(json);
		
		out.close();
						
	}
	
	//生成验证码
	
    @RequestMapping(value = "/validateCode")    
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {        
    	response.setHeader("Cache-Control", "no-cache");
        
    	String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 4, null);
        
    	request.getSession().setAttribute("validateCode", verifyCode);
        
    	response.setContentType("image/jpeg");
        
    	BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
        
    	ImageIO.write(bim, "JPEG", response.getOutputStream());
   
    }
    
    //获取登录对象
    
    @RequestMapping(value = "/Byusername")
    public void Byusername(String userName,HttpServletResponse response,HttpServletRequest request) throws IOException{

		Subject subject = SecurityUtils.getSubject();

		Session session = null;

		session = subject.getSession();

    	PrintWriter out = response.getWriter();
    	    	   	
    	Tuser user = (Tuser)session.getAttribute(userName);
    	
    	JSONObject json = new JSONObject();
    	
    	if(user != null){
    		
    		json.element("user", user);
    		
    		json.accumulate("booe", true);
    		
    	}else{
    		
    		json.accumulate("booe", false);
    		
    	}
    	
    	out.println(json);
    	    	
    }

}
