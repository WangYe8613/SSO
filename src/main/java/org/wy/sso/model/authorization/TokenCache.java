package org.wy.sso.model.authorization;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Component(value = "TokenCache")
@Scope(value = "singleton") //  设置为单例模式
public class TokenCache {

    // <token, <userUuid, expiredTime>>
    private Map<String, TokenInfo> tokenInfos = new HashMap<String, TokenInfo>();

    public void add(String token, String userUuid, Long timestamp) {
        tokenInfos.put(token, new TokenInfo(userUuid, timestamp));
    }

    public void remove(String token) {
        tokenInfos.remove(token);
    }

    public TokenInfo get(String token) {
        return tokenInfos.get(token);
    }

    public Boolean isEmpty() {
        return tokenInfos.size() == 0;
    }

    public void clear() {
        tokenInfos.clear();
    }

    public String getTokenByUserUuid(String userUuid) {
        if (tokenInfos != null) {
            int index = 0;
            List<String> tokens = new ArrayList<String>(tokenInfos.keySet());
            for (TokenInfo tokenInfo : tokenInfos.values()) {
                if (userUuid.equals(tokenInfo.getUserUuid())) {
                    return tokens.get(index);
                } else {
                    ++index;
                }
            }
        }
        return null;
    }
}
