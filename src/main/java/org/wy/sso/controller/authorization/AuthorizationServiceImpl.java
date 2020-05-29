package org.wy.sso.controller.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wy.sso.common.AuthTokenTool.Token;
import org.wy.sso.common.TimeTool.MyTime;
import org.wy.sso.logger.LogUtil;
import org.wy.sso.model.authorization.AuthorizationValidateInventory;
import org.wy.sso.model.response.Response;

@Controller(value = "AuthorizationService")
@RequestMapping(value = "/authorization")
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private Token T;

    /**
     * 校验登录信息
     *
     * @param token
     * @return
     */
    @Override
    @RequestMapping(value = "/validate")
    @ResponseBody
    @CrossOrigin
    public Response authorizationValidate(@RequestParam(value = "authToken") String token) {
        Response response = new Response();
        AuthorizationValidateInventory inventory = new AuthorizationValidateInventory();
        Boolean ret = T.checkToken(token);
        inventory.setToken(token);
        inventory.setValidate(ret);
        response.setInventory(inventory);
        return response;
    }
}
