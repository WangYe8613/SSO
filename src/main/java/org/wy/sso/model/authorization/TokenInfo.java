package org.wy.sso.model.authorization;

public class TokenInfo {
    private String userUuid;
    private Long expiredTime;

    public TokenInfo(String userUuid, Long timestamp) {
        this.userUuid = userUuid;
        this.expiredTime = timestamp;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
