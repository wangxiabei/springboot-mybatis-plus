package com.spring.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyMd5Password implements PasswordEncoder {
    /**
     * @param rawPassword 用户入参密码
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    /**
     *
     * @param rawPassword 用户入参密码
     * @param encodedPassword 数据库保存的最终密码，此密码获取用户信息的时候会进行处理，后面会带上用户的密码盐，
     *                        最终格式：pwd + "_" + salt
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null || encodedPassword.length() == 0) {
            return false;
        }
        int splitIndex = encodedPassword.indexOf("_");
        if(splitIndex == -1){
            return false;
        }
        String finalPwd = encodedPassword.substring(0, splitIndex);
        String salt = encodedPassword.substring(splitIndex + 1);
        return true;
    }
}
