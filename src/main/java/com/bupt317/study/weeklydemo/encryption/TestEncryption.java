package com.bupt317.study.weeklydemo.encryption;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class TestEncryption {
    public static void main(String[] args) {
        /*
        * 注意！salt可以使byte也可以是String，两个是一个东西，但是打印内容不同
        * String转换成byte用ByteSource salt = ByteSource.Util.bytes
        * shiro自动判断只支持byte
        * */
        String password = "1";
        // 盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        //x32isd83V9ymbFlbIFXKpw== : 5803e923c2a957b1710a2b7678f8b090
        //x0ejwfU5Z+E1yJJ4B3cuPg== : f686c358123a16a5cfbe8fb835aee613
//        salt = "YQ==";
//        ByteSource salt = ByteSource.Util.bytes("x0ejwfU5Z+E1yJJ4B3cuPg==");
        System.out.println(salt);
        // 加密次数
        int times = 2;
        // 加密方法
        String algorithmName = "md5";
        // encoding
        String encodedPwd = new SimpleHash(algorithmName, password, salt, times).toString();

        System.out.println(encodedPwd);
    }
}
