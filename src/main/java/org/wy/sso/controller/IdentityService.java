package org.wy.sso.controller;

import org.wy.sso.model.Response;

import java.sql.SQLException;

public interface IdentityService {
    Response signIn(String name, String passwd) throws SQLException;

    Response register(String name, String passwd) throws SQLException;
}
