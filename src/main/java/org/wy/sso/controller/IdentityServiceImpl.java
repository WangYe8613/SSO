package org.wy.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wy.sso.dao.PreparedStatementFacade;
import org.wy.sso.logger.LogUtil;
import org.wy.sso.model.RegisterInventory;
import org.wy.sso.model.Response;
import org.wy.sso.model.SignInInventory;
import org.wy.sso.model.UserEO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller(value = "IdentityService")
@RequestMapping(value = "/sso")
public class IdentityServiceImpl implements IdentityService {

    @Autowired
    private PreparedStatementFacade psf;

    @Autowired
    private LogUtil logger;

    @Override
    @RequestMapping(value = "/signIn")
    @ResponseBody
    @CrossOrigin
    public Response signIn(@RequestParam(value = "userName") String name, @RequestParam(value = "password") String passwd) throws SQLException {
        Response response = new Response();

        if (name == null || passwd == null) {
            response.setMessage("name or passwd could not be null");
            return response;
        }

        UserEO userEO = isExist(name, passwd);
        if (userEO == null) {
            response.setMessage("name or passwd was worng");
            return response;
        }

        SignInInventory inventory = new SignInInventory();
        inventory.valueOf(userEO);
        response.setInventory(inventory);
        return response;
    }

    @Override
    @RequestMapping(value = "/register")
    @ResponseBody
    @CrossOrigin
    public Response register(@RequestParam(value = "userName") String name, @RequestParam(value = "password") String passwd) throws SQLException {
        Response response = new Response();

        if (name == null || passwd == null) {
            logger.error("name or passwd could not be null");
            return response;
        }

        if (isDuplicateName(name)) {
            return response;
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String currentDate = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String sql = "insert into UserEO values (?,?,?,'','',?,?)";
        psf.createPreparedStatement(sql).
                setParams(1, uuid).
                setParams(2, name).
                setParams(3, passwd).
                setParams(4, currentDate).
                setParams(5, currentDate).
                execute();
        RegisterInventory inventory = new RegisterInventory();
        inventory.setUuid(uuid);
        inventory.setName(name);
        inventory.setPasswd(passwd);
        inventory.setToken("abcdefg");
        response.setInventory(inventory);
        return response;
    }

    /**
     * 判断用户是否存在
     *
     * @param name
     * @param passwd
     * @return
     * @throws SQLException
     */
    public UserEO isExist(String name, String passwd) throws SQLException {
        String sql = "select * from UserEO where name = ? and passwd = ?";
        ResultSet res = psf.createPreparedStatement(sql).setParams(1, name).setParams(2, passwd).executeQuery();
        if (res.next()) {
            UserEO userEO = new UserEO();
            userEO.setUuid(res.getString("uuid"));
            userEO.setName(res.getString("name"));
            userEO.setPasswd(res.getString("passwd"));
            userEO.setToken(res.getString("token"));
            return userEO;
        }
        return null;
    }

    /**
     * 判断注册用户名是否重名
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public Boolean isDuplicateName(String name) throws SQLException {
        String sql = "select uuid from UserEO where name = ?";
        ResultSet res = psf.createPreparedStatement(sql).setParams(1, name).executeQuery();
        if (res.next()) {
            return true;
        }
        return false;
    }
}
