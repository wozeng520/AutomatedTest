package com.dtxy.model;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author 272420251
 * @create 2019-12-06-15:19
 */
@Data
public class AddUserCase {

    private String userName;
    private String password;
    private String sex;
    private String age;
    private String permission;
    private String isDelete;
    private String expected;
}
