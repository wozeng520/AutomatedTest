package com.dtxy.model;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author 272420251
 * @create 2019-12-06-15:21
 */
@Data
public class GetUserListCase {
    private int id;
    private String userName;
    private String age;
    private String sex;
    private String expected;
}
