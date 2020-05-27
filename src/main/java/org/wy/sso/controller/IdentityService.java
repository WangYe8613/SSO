package org.wy.sso.controller;

import java.sql.SQLException;

public interface IdentityService {
    void signIn(String name, String passwd) throws SQLException;

    void register(String name, String passwd) throws SQLException;
}
