package com.bao.community.controller;

import com.bao.community.dto.AccesstokenDTO;
import com.bao.community.dto.GitHubUser;
import com.bao.community.mapper.UserMapper;
import com.bao.community.model.User;
import com.bao.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @auther Bao
 * @date 2019/11/30 19:34
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.clinet.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    HashMap hashMap = new HashMap();

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request) {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accesstokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);

        if (gitHubUser != null) {

            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));//long类型的id,这里强转为string类型
            user.setGmtCreate(System.currentTimeMillis());//使用当前时间
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

            //登录成功,写cookie和session
            request.getSession().setAttribute("user",gitHubUser);
            return "redirect:/";
        } else {
            //登录失败,重新登录
            return "redirect:/";
        }

    }
}
