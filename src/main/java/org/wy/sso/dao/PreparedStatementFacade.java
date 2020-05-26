package org.wy.sso.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository(value = "PreparedStatementFacade")
public class PreparedStatementFacade {

    private Connection conn;
    private PreparedStatement ps;

    @Autowired
    public PreparedStatementFacade(DataSource ds) {
        try {
            this.conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatementFacade createPreparedStatement(String sql) {
        try {
            this.ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PreparedStatementFacade setParams(int index, String param) {
        try {
            this.ps.setObject(index, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ResultSet executeQuery() {
        ResultSet resultSet = null;
        try {
            resultSet = this.ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public Boolean execute() {
        Boolean resultSet = null;
        try {
            resultSet = this.ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
