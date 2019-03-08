package com.spring.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User extends org.springframework.security.core.userdetails.User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private Set<String> roles = new HashSet<>();//角色

    public User() {
        //因为父类没有参数为空的构造方法，给他默认一些值，就可以默认
        super("non-exist-username", "", new HashSet<>());
    }

    /**
     * 根据用户名，密码，是否禁用，权限列表创建用户对象
     *
     * @param username
     * @param password
     * @param enabled
     * @param roles
     */
    public User(String username, String password, boolean enabled, String... roles) {
        super(username, password, enabled, true, true, true, AuthorityUtils.createAuthorityList(roles));
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles.addAll(Arrays.asList(roles));

    }

    /**
     * 根据用户名，密码，角色创建用户对象
     * @param username
     * @param password
     * @param roles
     */
    public User(String username, String password, String... roles) {
        this(username, password, true, roles);
    }


}
