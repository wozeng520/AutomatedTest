package com.dtxy.code.controller;

import com.dtxy.code.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ${DESCRIPTION}
 *
 * @author 272420251
 * @create 2019-12-08-20:52
 */
@RestController
@Api(value = "v1", description = "用户管理系统")
@RequestMapping("v1")
public class UserController {
    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登录接口", httpMethod = "POST")
    @PostMapping("/login")
    public boolean login(HttpServletResponse response, @RequestBody User user) {
        int i = template.selectOne("login", user);
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
		int j = 0;
        if (i == 1) {
            return true;
        }
        return false;
    }

    @ApiOperation(value = "添加用户接口", httpMethod = "POST")
    @PostMapping("addUser")
    public boolean addUser(HttpServletRequest request, @RequestBody User user) {
        Boolean flag = verifyCookies(request);
        int result = 0;
        if (flag) {
            result = template.insert("addUser", user);
            System.out.println("添加用户的数量是:" + result);
        }
        return flag;
    }

    @ApiOperation(value = "获取用户（列表）信息接口", httpMethod = "POST")
    @PostMapping(value = "getUserInfo")
    public List<User> getUserInfo(HttpServletRequest request, @RequestBody User user) {
        Boolean flag = verifyCookies(request);
        List<User> users = new ArrayList<>();
        if (flag) {
            users = template.selectList("getUserInfo", user);
        }
        return users;
    }

    @ApiOperation(value = "更新/删除用户接口", httpMethod = "POST")
    @PostMapping("/updateUserInfo")
    public int updateUser(HttpServletRequest request, @RequestBody User user) {
        Boolean flag = verifyCookies(request);
        int i = 0;
        if (flag) {
            i = template.update("updateUserInfo", user);
        }
        return i;
    }

    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return false;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                return true;
            }
        }
        return false;
    }
}
