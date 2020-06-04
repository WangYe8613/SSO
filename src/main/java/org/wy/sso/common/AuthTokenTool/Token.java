package org.wy.sso.common.AuthTokenTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.wy.sso.common.CypherTool.Base64;
import org.wy.sso.common.TimeTool.MyTime;
import org.wy.sso.logger.LogUtil;
import org.wy.sso.model.authorization.TokenCache;
import org.wy.sso.model.authorization.TokenInfo;

import java.io.UnsupportedEncodingException;

@Component(value = "GenerateTokenTool")
@PropertySource("classpath:token.properties")
public class Token {

    @Autowired
    private Base64 base64;

    @Autowired
    private TokenCache tokenCache;

    @Autowired
    private LogUtil logger;

    @Autowired
    private MyTime time;

    @Value("${token.lifeCycle}")
    private long tokenLifeCycle;  //  token生命周期

    /**
     * 校验该token是否存在，并且是否过期
     *
     * @param token
     * @return
     */
    public Boolean checkToken(String token) {
        Long currentTime = time.currentTime();
        TokenInfo tokenInfo = tokenCache.get(token);
        if (tokenInfo == null || tokenInfo.getExpiredTime() < currentTime) {
            return false;
        }
        return true;
    }

    /**
     * 生成token，并加入缓存中
     * 如果同一个用户在多个地方登录，则保留最新的token，删除上一次登录的token
     *
     * @param userName
     * @return
     * @throws UnsupportedEncodingException
     */
    public String generateToken(String userUuid, String userName) throws UnsupportedEncodingException {

        // 如果同一个用户在多个地方登录，则保留最新的token，删除上一次登录的token
        // 这样就可以实现异地登录退出上一个账号的功能
        String lastToken = tokenCache.getTokenByUserUuid(userUuid);
        if (lastToken != null) {
            tokenCache.remove(lastToken);
        }

        String currentDate = time.currentDate("yyyy-MM-dd HH:mm:ss");
        String token = base64.getBase64(userName + currentDate);

        if (token == null) {
            logger.error("generate token faild!!!");
        }
        long expiredTime = time.currentTime() + tokenLifeCycle;
        tokenCache.add(token, userUuid, expiredTime);
        return token;
    }

    /**
     * 从缓存中去除指定token
     *
     * @param token
     */
    public void removeToken(String token) {
        tokenCache.remove(token);
    }
}
