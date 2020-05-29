package org.wy.sso.model.authorization;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component(value = "TokenCache")
@Scope(value = "singleton") //  设置为单例模式
public class TokenCache {

    // <token,过期时间>
    private Map<String, Long> tokenMap = new HashMap<String, Long>();

    public void add(String token, Long timestamp){
        tokenMap.put(token,timestamp);
    }

    public void remove(String token){
        tokenMap.remove(token);
    }

    public Long get(String token){
        return tokenMap.get(token);
    }

    public Boolean isEmpty(){
        return tokenMap.size() == 0;
    }

    public void clear(){
        tokenMap.clear();
    }
}
