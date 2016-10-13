package com.bros.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class UserDao {
    @Autowired JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> findUserByAccountAndPass(String account, String password) {
        return jdbcTemplate.queryForList("SELECT * FROM USER WHERE account = ? and PASSWORD = ?",new Object[]{account,password});
    }

    public List<Map<String,Object>> findUserList() {
        return jdbcTemplate.queryForList("SELECT * FROM USER");
    }

    public void updatePass(String account, String password) {
        jdbcTemplate.update("UPDATE USER SET PASSWORD = '"+password+"' WHERE account='"+account+"' ");
    }

    public void delete(String account) {
        jdbcTemplate.update("DELETE FROM USER WHERE account = '"+account+"'");
    }

    public Map<String,Object> findUserByAccount(String account) {
        List<Map<String,Object>> users = jdbcTemplate.queryForList("SELECT * FROM USER WHERE account = ?",new Object[]{account});
        if(users!=null && users.size()>0){
            return users.get(0);
        }else{
            return null;
        }
    }
}
