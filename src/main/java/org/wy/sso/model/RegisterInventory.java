package org.wy.sso.model;

public class RegisterInventory implements Inventory {

    private String uuid;
    private String name;
    private String passwd;
    private String token;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void valueOf(Object ob) {
        UserEO userEO = (UserEO) ob;
        this.uuid = userEO.getUuid();
        this.name = userEO.getName();
        this.passwd = userEO.getPasswd();
        this.token = userEO.getToken();
    }
}
