package com.wyy.auth.util;

import com.wyy.easyry.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 20-2-1
 * @Author: wyy
 */
@Component
public class JwtTokenUtil {

    public static boolean rememberMe;

    public static String jwtSecret;

    public static int jwtExpiration;

    public static int jwtRememberMeExpiration;

    @Value("${jwt.rememberMe:false}")
    public void setRememberMe(boolean rememberMe) {
        JwtTokenUtil.rememberMe = rememberMe;
    }

    @Value("${jwt.secret:easyray}")
    public void setJwtSecret(String jwtSecret) {
        JwtTokenUtil.jwtSecret = jwtSecret;
    }

    @Value("${jwt.expiration:3600}")
    public void setJwtExpiration(int jwtExpiration) {
        JwtTokenUtil.jwtExpiration = jwtExpiration;
    }

    @Value(("${jwt.rememberMeExpiration:86400}"))
    public void setJwtRememberMeExpiration(int jwtRememberMeExpiration) {
        JwtTokenUtil.jwtRememberMeExpiration = jwtRememberMeExpiration;
    }

    public static final String USERID = "userId";
    public static final String USERNAME = "username";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static String createToken(User user) {

        return createToken(user, rememberMe);
    }

    public static String createToken(User user, Boolean rememberMe) {
        assert user != null;
        int expiration = getExpiration(rememberMe);

        //添加构成JWT的参数
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERID, user.getId());
        claims.put(USERNAME, user.getUsername());

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(DateUtils.addSeconds(new Date(), expiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    public static int getExpiration(Boolean rememberMe) {
        int expiration = jwtExpiration;

        if (rememberMe != null && rememberMe) {
            expiration = jwtRememberMeExpiration;
        }
        return expiration;
    }

    // 从token中获取用户名
    public static String getUsername(String token) {
        return getTokenBody(token).get(USERNAME, String.class);
    }

    // 是否已过期
    public static boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}
