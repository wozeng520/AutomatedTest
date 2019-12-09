package com.dtxy.cases;

import com.dtxy.config.TestConfig;
import com.dtxy.model.GetUserInfoCase;
import com.dtxy.model.User;
import com.dtxy.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 272420251
 * @create 2019-12-06-16:47
 */
public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue", description = "获取userId为1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = sqlSession.selectOne("getUserInfoCase", 1);
        System.out.println(getUserInfoCase);
        System.out.println(TestConfig.getUserInfoUrl);
        JSONArray resultJson = getResult(getUserInfoCase);
        User user = sqlSession.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);

        List<User> userList = new ArrayList<User>();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject expect = (JSONObject) jsonArray.get(i);
            JSONObject result = (JSONObject) resultJson.get(i);
            Assert.assertEquals(expect.get("id"),result.get("id"));
            Assert.assertEquals(expect.get("isDelete"),result.get("isDelete"));
            Assert.assertEquals(expect.get("sex"),result.get("sex"));
            Assert.assertEquals(expect.get("permission"),result.get("permission"));
            Assert.assertEquals(expect.get("userName"),result.get("userName"));
            Assert.assertEquals(expect.get("age"),result.get("age"));
        }
    }

    private JSONArray getResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id", getUserInfoCase.getUserId());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        post.setHeader("content-type","application/json");
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String s = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONArray array = new JSONArray(s);
        return array;
    }
}
