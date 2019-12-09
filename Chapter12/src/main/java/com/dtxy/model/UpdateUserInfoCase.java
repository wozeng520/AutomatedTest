package com.dtxy.model;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author 272420251
 * @create 2019-12-06-15:22
 */
@Data
public class UpdateUserInfoCase {
    private int id;
    private int userId;
    private String userName;
    private String sex;
    private String age;
    private String permission;
    private String isDelete;
    private String expected;
}
