package com.bros.user.service;

import com.bros.user.domain.UserDao;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class LoginService {
    @Autowired private UserDao userDao;

    //登陆，缓存用户登录信息
    public Map<String,Object> findUserByAccountAndPass(String account, String password) {
        System.out.println("--"+new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date())+"--查询登陆用户--");
        List<Map<String,Object>> users = userDao.findUserByAccountAndPass(account,password);
        if( users==null || users.size()==0 ){
            return null;
        }
        return users.get(0);
    }
}
