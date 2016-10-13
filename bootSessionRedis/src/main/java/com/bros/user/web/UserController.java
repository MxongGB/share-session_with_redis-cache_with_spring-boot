package com.bros.user.web;

import com.bros.user.service.LoginService;
import com.bros.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {
    @Value("com.bros.user_login_info") private String user_login_info;
    @Autowired private UserService userService;
    @Autowired private LoginService loginService;

    //查询所有用户
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public List<Map<String,Object>> list(HttpSession session){
        Map<String,Object> currentUserMap = (Map<String, Object>) session.getAttribute(user_login_info);
        if( currentUserMap==null || currentUserMap.size()==0 ){
            System.out.println("用户没登陆");
            //
            List<Map<String,Object>> reslus = new ArrayList<>();
            Map<String,Object> map = new HashMap<>();
            map.put("msgCode","用户没登陆");
            reslus.add(map);
            return reslus;
        }
        List<Map<String,Object>> users = userService.findUserList();
        return users;
    }

    //修改密码
    @RequestMapping(value = "updatePass/account/{account}/pass/{password}",method = RequestMethod.GET)
    public Map<String,Object> updatePass(@PathVariable String account,@PathVariable String password){
        Map<String,Object> newUserMap = userService.updatePass(account,password);
        return newUserMap;
    }

    //删除用户
    @RequestMapping(value = "deleteUser/account/{account}",method = RequestMethod.GET)
    public String deleteUser(@PathVariable String account){
        userService.delete(account);
        return "已删除用户，账户："+account;
    }

    @RequestMapping(value = "findUser/account/{account}",method = RequestMethod.GET)
    public Map<String,Object> findUser(@PathVariable String account){
        return userService.findUserByAccount(account);
    }
}
