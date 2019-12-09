package com.dtxy.model;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author 272420251
 * @create 2019-12-06-15:22
 */
@Data
public class LoginCase {

    private int id;
    private String userName;
    private String password;
    private String expected;
}
