package com.bros.user.service;

import com.bros.user.domain.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "users")
public class UserService {

    @Autowired private UserDao userDao;

    /**
     * @Cacheable 主要的参数
        value	缓存的名称，在 spring 配置文件中定义，必须指定至少一个	例如：@Cacheable(value=”mycache”) 或者@Cacheable(value={”cache1”,”cache2”}
        key	缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合	例如：@Cacheable(value=”testcache”,key=”#userName”)
        condition	缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存	例如：@Cacheable(value=”testcache”,condition=”#userName.lengt
     */
    //查询所有用户，缓存所有用户信息
    @Cacheable(cacheNames = "boot_users")
    public List<Map<String,Object>> findUserList() {
        System.out.println("--"+new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date())+"--查询所有用户--");
        return userDao.findUserList();
    }

    //查询用户
    @Cacheable(cacheNames = "boot_user",key = "#account")
    public Map<String,Object> findUserByAccount(String account) {
        System.out.println("--"+new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date())+"--查找用户,账户："+account+"--");
        return userDao.findUserByAccount(account);
    }

    /**
     * @CachePut 修改缓存中的信息
     * 功能：根据方法返回值中的信息，修改缓存信息
     * @CachePut 主要的参数
        value	缓存的名称，在 spring 配置文件中定义，必须指定至少一个	例如：@Cacheable(value=”mycache”) 或者 @Cacheable(value={”cache1”,”cache2”}
        key	缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合	例如：@Cacheable(value=”testcache”,key=”#userName”)
        condition	缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存	例如：@CachEvict(value=”testcache”,condition=”#userName.length()>2”)
     */
    //修改密码，修改缓存信息
    @CachePut(value = "boot_user",key = "#account")
    public Map<String,Object> updatePass(String account, String password) {
        System.out.println("--"+new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date())+"--修改用户,账户："+account+"--");
        //修改密码
        userDao.updatePass(account,password);
        //查询
        List<Map<String,Object>> users = userDao.findUserByAccountAndPass(account,password);
        if( users==null || users.size()==0 ){
            return null;
        }
        return users.get(0);
    }

    /**
     * @CacheEvict 清空缓存，
     * @CacheEvict 主要的参数
        value	缓存的名称，在 spring 配置文件中定义，必须指定至少一个	例如：@CachEvict(value=”mycache”) 或者@CachEvict(value={”cache1”,”cache2”}
        key	缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合	例如：@CachEvict(value=”testcache”,key=”#userName”)
        condition	缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才清空缓存	例如：@CachEvict(value=”testcache”,condition=”#userName.length()>2”)
        allEntries	是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存	例如：@CachEvict(value=”testcache”,allEntries=true)
        beforeInvocation	是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存	例如：@CachEvict(value=”testcache”，beforeInvocation=true)
     */
    //删除用户
    @CacheEvict(value = "boot_user",key = "#account")
    public void delete(String account) {
        System.out.println("--"+new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date())+"--删除用户,账户："+account+"--");
        userDao.delete(account);
    }
}
