package jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreatJWT {
    public static void main(String[] args) {
        JwtBuilder huahuaCommunity = Jwts.builder().setId("123456789")
                .setSubject("1608V")//使用者
                .setIssuedAt(new Date())//时间
                .claim("roles","admin,teacher")
                .claim("telphone","13918570832")
                .signWith(SignatureAlgorithm.ES256,"huahuaCommunity");//加密算法ES256      加密签名huahuaCommunity

                 System.out.println(huahuaCommunity.compact());
                 //通过加盐的规则  huahuaCommunity.compact()获取token令牌

    }
}
