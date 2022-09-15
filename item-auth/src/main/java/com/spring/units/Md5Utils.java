package com.spring.units;

import org.springframework.util.DigestUtils;

import java.security.SecureRandom;
import java.util.*;

public class Md5Utils {
    private static final String SIGN_KEY = "sign";
    private static final Random RANDOM = new SecureRandom();
    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Md5Utils() {
    }

    public static String md5(String s) {
        try {
            return md5(s.getBytes("utf-8")).toUpperCase();
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String md5(byte[] bytes) {
        try {
            return DigestUtils.md5DigestAsHex(bytes);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String md5FromPwd(String pwd, String salt) {
        String str = salt.charAt(0) + String.valueOf(salt.charAt(3)) + pwd + salt.charAt(5) + salt;
        return md5(str);
    }

    public static String md5ToDB(String pwd, String salt) {
        String str = salt.charAt(0) + String.valueOf(salt.charAt(3)) + pwd + salt.charAt(5) + salt;
        return md5(str);
    }

    private static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        } else {
            char[] charr = new char[len];
            System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());

            for(int i = 0; i < diff; ++i) {
                charr[i] = ch;
            }

            return new String(charr);
        }
    }

    public static String createSignByDefault(SortedMap<String, Object> parame) {
        String apikey = "anysignkey2020";
        return createSign(parame, apikey);
    }

    public static String createSignByDefault(Map<String, Object> parame) {
        String apikey = "anysignkey2020";
        return createSign(parame, apikey);
    }

    public static String createSign(Map<String, Object> parame, String apikey) {
        SortedMap sortedMap = null;
        if (parame instanceof HashMap) {
            sortedMap = new TreeMap(parame);
        } else if (parame instanceof SortedMap) {
            sortedMap = (SortedMap)parame;
        } else {
            //R.throwException("不支持的签名参数类型");
        }

        StringBuffer buffer = new StringBuffer();
        Set set = ((SortedMap)sortedMap).entrySet();
        Iterator iterator = set.iterator();

        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String key = (String)entry.getKey();
            Object value = entry.getValue();
            if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
                buffer.append(key + "=" + value + "&");
            }
        }

        buffer.append("key=" + apikey);
        //log.debug("签名参数字符串：" + buffer.toString());
        String sign = md5(buffer.toString()).toUpperCase();
        parame.put("sign", sign);
       // log.debug("签名参数：" + sign);
        return sign;
    }

    public static boolean checkSignByDefault(Map<String, Object> params) {
        String sign = getSign(params);
        boolean s = createSignByDefault(params).equals(sign);
        if (!s) {
           // R.throwException("参数验签失败！");
        }

        return true;
    }

    public static boolean checkSign(Map<String, Object> params, String apikey) {
        String sign = getSign(params);
        boolean s = createSign(params, apikey).equals(sign);
        if (!s) {
           // R.throwException("参数验签失败！");
        }

        return true;
    }

    private static String getSign(Map<String, Object> params) {
        Object signObj = params.get("sign");
        if (signObj == null) {
           // R.throwException("参数未包含签名字符串");
        }

        String sign = signObj.toString();
        return sign;
    }

    public static String getNonce() {
        char[] nonceChars = new char[32];

        for(int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(RANDOM.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length()));
        }

        return new String(nonceChars);
    }
}
