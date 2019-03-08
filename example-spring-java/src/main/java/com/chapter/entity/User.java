package com.chapter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class User {
    private Long id;

    @NotBlank
    @NotNull(message = "第一昵称不能为空")
    @Size(min = 5, max = 10, message = "第一昵称最小5位，最长10位")
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String head;
}
