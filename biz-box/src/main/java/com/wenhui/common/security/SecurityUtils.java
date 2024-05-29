package com.wenhui.common.security;

import com.wenhui.common.security.jwt.JwtUser;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import io.jsonwebtoken.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * jwt工具类 生成、解析、校验token
 */
public class SecurityUtils {
    /**
     * 用户登录成功后生成jwt
     * 使用Hs256算法
     * 三部分组成 头部+荷载+签证信息
     *
     * @param ttlMillis jwt过期时间
     * @param user      登录成功的user对象
     * @return
     */
    public static String createJwt(long ttlMillis, JwtUser user) {
        // header部分，jwt已经封装好了
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // jwt生成时间 当前时间
        long nowMillis = System.currentTimeMillis();
        Date date = new Date(nowMillis);

        // payload 荷载部分（存放有效信息的地方，包含标准中注册的声明、公共声明、私有声明）
        // 创建私有声明
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", String.valueOf(user.getUserId()));
        claims.put("username", user.getUserName());
        claims.put("password", user.getUserPassword());

        // 生成秘钥secret用
//        String key = user.getUserPassword();
        byte[] bytes = user.getUserPassword().getBytes();
        // 生成签发人
        String subject = user.getUserName();

        // 为payload添加标准声明和私有声明（new一个JwtBuilder，设置jwt的body）
        JwtBuilder jwtBuilder = Jwts.builder()
                // 先设置自己创建的私有声明，要是写在标准声明后面，会覆盖掉标准声明
                .setClaims(claims)
                // 设置jti（jwt id），主要用来作为一次性token，从而回避重放攻击
                .setId(UUID.randomUUID().toString())
                // 设置iat jwt签发时间
                .setIssuedAt(date)
                // 设置jwt的所有人
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, bytes);
        // 设置jwt的过期时间
        if (ttlMillis >= 0) {
            long expMillis = ttlMillis * 1000 + nowMillis;
            Date expDate = new Date(expMillis);
            jwtBuilder.setExpiration(expDate);
        }
        return jwtBuilder.compact();
    }

    /**
     * 解密jwt
     *
     * @param token 需要被解密的token
     * @param user  用户的对象
     * @return
     */
    public static Claims parseJWT(String token, JwtUser user) {
        // 签名秘钥（与生成签名的秘钥一样）
        String key = user.getUserPassword();

        // 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(key)
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }


    /**
     * 校验jwt
     * 判断token携带的密码跟数据库里的是否一致（也可用官方的校验方法）
     *
     * @param token
     * @param user
     * @return
     */
    public static Boolean isVerify(String token, JwtUser user) {
        // 秘钥
        byte[] bytes = user.getUserPassword().getBytes();
        Claims claims;

        try {
            // 得到DefaultJwtParser
            claims = Jwts.parser()
                    // 设置签名的秘钥
                    .setSigningKey(bytes)
                    // 设置需要解析的jwt
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
//            Date expiration = claims.getExpiration();
            //和当前时间进行对比来判断是否过期
//            new Date(System.currentTimeMillis()).after(expiration);
            throw new BusinessException(String.valueOf(ErrorCode.LOGIN_CHECK_FAILED.getCode()), "登录过期，请重新登录！");
        }
        // 判断密码是否一致
        if (claims.get("password").equals(user.getUserPassword())) {
            return true;
        }
        return false;
    }


    public static String getPassEncryption(String password, String name) {
        //加密方式
        String algorithmName = "MD5";

        //盐值，用于和密码混合起来用
        ByteSource salt = ByteSource.Util.bytes(name);

        //加密的次数,可以进行多次的加密操作
        int hashIterations = 10;

        //通过SimpleHash 来进行加密操作
        SimpleHash hash = new SimpleHash(algorithmName, password, salt, hashIterations);

        return hash.toString();
    }
}
