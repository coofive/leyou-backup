package com.leyou.test;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Simple to Introduction
 *
 * @author: cooFive
 * @CreateDate: 2018/8/5 16:51
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class jwtTest {

    private static final String pubKeyPath = "C:\\test\\rsa\\rsa.pub";

    private static final String priKeyPath = "C:\\test\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "123");
    }

    @Test
    public void testKey() throws Exception {
        PrivateKey privateKey = RsaUtils.getPrivateKey(priKeyPath);
        System.out.println("privateKey.toString() = " + privateKey.toString());

        PublicKey publicKey = RsaUtils.getPublicKey(pubKeyPath);
        System.out.println("publicKey.toString() = " + publicKey.toString());
    }

    @Test
    public void testGenerateToken() throws Exception {
        String token = JwtUtils.generateToken(new UserInfo(20L, "rose"), RsaUtils.getPrivateKey(priKeyPath), 15);
        System.out.println("token = " + token);
    }


    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoicm9zZSIsImV4cCI6MTUzMzQ2NDcxMX0.Hdjt5_LGGC46agDAiW9PHE2xd_8TuBxrLdqtkei4BTsnlsmClkduYEBBt_nGXLGvn4yWBFRfojwMvuxF_Yc11WRthbuQ-nDiwWqJ5yxP19ErTjB9jX4yj1nlB7LkFrO3B7PSBD3zHvhdIY9JbwKbwQo6-eT2vt8Kb9Y7YMy5kEQ";
        // 解析token
        UserInfo userInfo = JwtUtils.getInfoFromToken(token,RsaUtils.getPublicKey(pubKeyPath));
        System.out.println("userInfo.getUsername() = " + userInfo.getUsername());
        System.out.println("userInfo.getId() = " + userInfo.getId());
    }


}
