package com.dtxy.code.model;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author 272420251
 * @create 2019-12-08-20:46
 */
@Data
public class User {
    private int id;
    private String userName;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;
}
