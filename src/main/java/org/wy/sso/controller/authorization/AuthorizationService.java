package org.wy.sso.controller.authorization;

import org.wy.sso.model.response.Response;

public interface AuthorizationService {
    Response authorizationValidate(String token);
}
