package org.wy.sso.model.authorization;

import org.wy.sso.model.Inventory;

public class AuthorizationValidateInventory implements Inventory {

    private String token;
    private Boolean validate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    @Override
    public void valueOf(Object ob) {

    }
}
