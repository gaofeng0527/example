package com.spring.template.user.impl;

import com.spring.template.user.Account;
import com.spring.template.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    JdbcOperations jdbcTemplate;

    @Override
    public void save(Account account) {

        String sql = "insert into t_account (id,u_name,u_pwd,email,phone,head_path,reg_time) value(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,account.getId(),account.getUName(),account.getPassword(),account.getEmail(),account.getPhone(),account.getHeadPath(),account.getCreateDate());
    }
}
