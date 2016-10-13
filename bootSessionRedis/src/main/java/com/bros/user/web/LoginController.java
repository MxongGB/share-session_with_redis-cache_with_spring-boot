package com.bros.user.web;

import com.bros.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("login")
@RestController
public class LoginController {
    @Value("com.bros.user_login_info") private String user_login_info;
    @Autowired private LoginService loginService;

    //用户登录
    @RequestMapping(value = "account/{account}/pass/{password}")
    public Map<String,Object> login(@PathVariable String account, @PathVariable String password, HttpSession session){
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> currentUserMap = (Map<String, Object>) session.getAttribute(user_login_info);
        if( currentUserMap!=null && currentUserMap.size()>0 ){
            result.put("msg","用户已登陆，不必重复登陆。");
            return result;
        }
        Map<String,Object> userMap = loginService.findUserByAccountAndPass(account,password);
        if( userMap==null || userMap.size()==0 ){
            result.put("msg","登陆失败，账户或是密码错误。");
            return result;
        }
        //存储到session
        session.setAttribute(user_login_info,userMap);
        userMap.put("msg","登陆成功");
        return userMap;
    }

    //用户登出
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout (HttpSession session){
        session.removeAttribute(user_login_info);
        return "用户已登出";
    }
}
