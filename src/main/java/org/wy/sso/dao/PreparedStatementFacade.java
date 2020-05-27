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
    public PreparedStatementFacade(DataSource ds) throws SQLException {
        this.conn = ds.getConnection();
    }

    public PreparedStatementFacade createPreparedStatement(String sql) throws SQLException {
        this.ps = conn.prepareStatement(sql);
        return this;
    }

    public PreparedStatementFacade setParams(int index, String param) throws SQLException {
        this.ps.setObject(index, param);
        return this;
    }

    public ResultSet executeQuery() throws SQLException {
        ResultSet resultSet = null;
        resultSet = this.ps.executeQuery();
        return resultSet;
    }

    public Boolean execute() throws SQLException {
        Boolean resultSet = null;
        resultSet = this.ps.execute();
        return resultSet;
    }
}
