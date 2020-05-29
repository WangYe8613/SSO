package org.wy.sso.controller.identity;

import org.wy.sso.model.response.Response;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface IdentityService {
    Response signIn(String name, String passwd) throws SQLException, UnsupportedEncodingException;

    Response signOut(String token) throws SQLException, UnsupportedEncodingException;

    Response register(String name, String passwd) throws SQLException;
}
