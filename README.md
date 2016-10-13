# share-session_with_redis-cache_with_spring-boot
spring boot 和 redis 实现session共享和集中式缓存

登陆：用户信息保存至redis服务中  
http://localhost:8080/login/account/1001/pass/123
登出：删除redis session缓存
http://localhost:8080/login/logout

查询用户列表：查询所有用户信息，并缓存至redis
http://localhost:8080/user/list

查询单个用户信息：将查询到的用户信息缓存至redis
http://localhost:8080/user/findUser/account/1002

修改密码：修改数据库中账户密码，并更新缓存中的用户信息
http://localhost:8080/user/updatePass/account/1002/pass/789

删除用户：删除数据库中用户，并删除redis缓存
http://localhost:8080/user/deleteUser/account/1002
