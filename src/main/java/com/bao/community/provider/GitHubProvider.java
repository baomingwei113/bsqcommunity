package com.bao.community.provider;

import com.alibaba.fastjson.JSON;
import com.bao.community.dto.AccesstokenDTO;
import com.bao.community.dto.GitHubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

/**
 * @auther Bao
 * @date 2019/11/30 19:56
 */
@Component
public class GitHubProvider {
    public String getAccessToken(AccesstokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();

            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
        }
        return null;
    }


    public GitHubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubUser githubUser = JSON.parseObject(string, GitHubUser.class);
            return githubUser;
        } catch (Exception e) {

        }
        return null;
    }

}
