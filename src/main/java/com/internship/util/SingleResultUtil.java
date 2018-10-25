package com.internship.util;

import com.internship.mvc.pojo.User;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * 加密解密工具类
 */
public class SingleResultUtil {
    //加密
    public static void entryptPassword(User user) {
        String salt = UUID.randomUUID().toString();
        String temPassword = salt + user.getPlainPassword();
        String md5Password = DigestUtils.md5DigestAsHex(temPassword.getBytes());
        user.setSalt(salt);
        user.setPassword(md5Password);
    }

    //解密
    public static boolean decryptPassword(User user, String plainPassword) {
        String temPassword = user.getSalt() + plainPassword;
        String md5Password = DigestUtils.md5DigestAsHex(temPassword.getBytes());
        return user.getPassword().equals(md5Password);
    }

    //获取当前时间
    public static String getCurrentDateTime() {
        TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(zone);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
