package com.spring.config;

import com.spring.units.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import java.util.Arrays;

/**
 * 自定义的oauth2认证的客户端信息
 */
public class MyClientDetailsService implements ClientDetailsService {
    RedisTemplate redisTemplate;
    RedisConnectionFactory connectionFactory;

    public MyClientDetailsService() {
    }

    public MyClientDetailsService(RedisTemplate redisTemplate, RedisConnectionFactory connectionFactory) {
        this.redisTemplate = redisTemplate;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId("admin-app");
        String salt = "Uqx1VXLYFmgMeFaEmcmjD699UgaZlk1m";
        String secret = "B1538757C07E6A6AD2D4F71A4986E6B6";
        secret = Md5Utils.md5ToDB(secret, salt);
        clientDetails.setClientSecret(secret + "_" + salt);
        String splitter = ",";
        clientDetails.setResourceIds(Arrays.asList(StringUtils.split("admin", splitter)));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(StringUtils.split("password,refresh_token", splitter)));
        clientDetails.setScope(Arrays.asList(StringUtils.split("ALL_ADMIN,ALL_ACTIVITI,ALL_COUPON,ALL_JOB,ALL_CARD", splitter)));
//        clientDetails.setRegisteredRedirectUri(new HashSet(Arrays.asList(StringUtils.split(clientDetailEntity.getWebServerRedirectUri(), splitter))));
//        clientDetails.setAccessTokenValiditySeconds(clientDetailEntity.getAccessTokenValidity());
//        clientDetails.setRefreshTokenValiditySeconds(clientDetailEntity.getRefreshTokenValidity());
//        //附加信息
//        Map<String, String> additionalInfo = new HashMap<>();
//        //客户端名称
//        additionalInfo.put("clientName", clientDetailEntity.getClientName());
//        clientDetails.setAdditionalInformation(additionalInfo);
        return clientDetails;
    }
}
