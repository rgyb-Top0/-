package there.cumt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    //有效期为
    public static final Long JWT_TTL = 60 * 60 *1000L;// 60 * 60 *1000  一个小时

    //设置秘钥明文（盐）
    public static final String JWT_KEY = "JLL999999999";

    //生成令牌
    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成jtw
     * @param subject token中要存放的数据（json格式） 用户数据
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    //生成jwt的业务逻辑代码
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid)
    {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        SecretKey secretKey = generalKey();

        long nowMillis = System.currentTimeMillis();//获取到系统当前的时间戳

        Date now = new Date(nowMillis);
        if(ttlMillis==null){ //如果过期时间为空则生成一个
            ttlMillis=JwtUtil.JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;//过期时间 = 现在时间 + 1h

        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)         //唯一的ID
                .setSubject(subject) // 主题  可以是JSON数据
                .setIssuer("LLJ")     // 签发者
                .setIssuedAt(now)    // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 创建token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length,
                "AES");
        return key;
    }

    /**
     * 解析jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}

