package com.spring.security;

import com.spring.user.User;
import com.spring.user.UserService;
import com.spring.user.impl.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义用户加载类，可以替换spring-security默认的UserDetailsService
 * 需要重新配置 authentication-provide
 * UserDetailsService 的作用是根据form表单提供的用户名查找用户信息
 * 在spring-security中授权分为两步：1.身份认证：即根据用户名查找用户信息，密码，权限等，查到了，比较密码是否相等，相等身份认证通过，进行权限认证
 *                               2.权限认证：根据用户所有权限，和访问页面需要的权限进行对比，页面权限在intercept-url中配置
 */
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserService uservice = new UserServiceImpl();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = uservice.findUserByUserName(username);
        if (null == user) {
            throw new UsernameNotFoundException(username + " not found!");
        }
        return user;
    }
}
