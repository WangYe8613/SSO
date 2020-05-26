package org.wy.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wy.sso.dao.DBUtil;
import org.wy.sso.dao.PreparedStatementFacade;
import org.wy.sso.logger.LogUtil;

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
    public void signIn(@RequestParam(value = "name") String name, @RequestParam(value = "passwd") String passwd) {

    }

    @Override
    @RequestMapping(value = "/register")
    public void register(@RequestParam(value = "name") String name, @RequestParam(value = "passwd") String passwd) {

        if (name == null || passwd == null) {
            logger.error("name or passwd could not be null");
            return;
        }
        if (isExist(name)) {
            logger.error("用户已存在！！！");
            System.out.println("用户已存在！！！");
            return;
        }
        String uuid = UUID.randomUUID().toString().replace("-","");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String currentDate = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String sql = "insert into UserEO values (?,?,?,'','',?,?)";
        psf.createPreparedStatement(sql).
                setParams(1,uuid).
                setParams(2,name).
                setParams(3,passwd).
                setParams(4,currentDate).
                setParams(5,currentDate).
                execute();
    }

    public Boolean isExist(String name) {
        String sql = "select uuid from UserEO where name = ?";
        ResultSet res = psf.createPreparedStatement(sql).setParams(1, name).executeQuery();
        try {
            if (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(String.format("executeQuery faild, because ", e.toString()));
            e.printStackTrace();
        }
        return false;
    }
}
