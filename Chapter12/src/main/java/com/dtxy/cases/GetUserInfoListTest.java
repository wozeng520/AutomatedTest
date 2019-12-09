package com.dtxy.cases;

import com.dtxy.config.TestConfig;
import com.dtxy.model.GetUserListCase;
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
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 272420251
 * @create 2019-12-06-16:42
 */
public class GetUserInfoListTest {
    @Test(dependsOnGroups = "loginTrue", description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserListCase getUserListCase = sqlSession.selectOne("getUserListCase",1);
        System.out.println(getUserListCase);
        System.out.println(TestConfig.getUserListUrl);
        JSONArray resultJson = getJsonResult(getUserListCase);

        List<User> userList = sqlSession.selectList(getUserListCase.getExpected(), getUserListCase);
        for (User user : userList) {
            System.out.println("获取的User:"+ user.toString());
        }
        JSONArray userListJson = new JSONArray(userList);
        Assert.assertEquals(userListJson.length(), resultJson.length());
        for (int i = 0; i < resultJson.length(); i++) {
            JSONObject result = (JSONObject) resultJson.get(i);
            JSONObject expect = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.get("id"),result.get("id"));
            Assert.assertEquals(expect.get("isDelete"),result.get("isDelete"));
            Assert.assertEquals(expect.get("sex"),result.get("sex"));
            Assert.assertEquals(expect.get("permission"),result.get("permission"));
            Assert.assertEquals(expect.get("userName"),result.get("userName"));
            Assert.assertEquals(expect.get("age"),result.get("age"));
        }

    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("userName", getUserListCase.getUserName());
        param.put("sex", getUserListCase.getSex());
        param.put("age", getUserListCase.getAge());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String s = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONArray jsonArray = new JSONArray(s);
        return jsonArray;
    }

}
