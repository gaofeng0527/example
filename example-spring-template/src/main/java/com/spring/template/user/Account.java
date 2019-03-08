package com.spring.template.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Account {
    private String id;
    private String uName;
    private String password;
    private String email;
    private String phone;
    private String headPath;
    private Date createDate;
}
