package com.bao.community.controller;

import com.bao.community.dto.AccesstokenDTO;
import com.bao.community.dto.GitHubUser;
import com.bao.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

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

    HashMap hashMap=new HashMap();
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accesstokenDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
