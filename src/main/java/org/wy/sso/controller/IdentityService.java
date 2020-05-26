package org.wy.sso.controller;

public interface IdentityService {
    void signIn(String name, String passwd);

    void register(String name, String passwd);
}
