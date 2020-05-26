package org.wy.sso.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository(value = "DBUtil") // 将DatabaseUtil存入Spring IOC容器中，id设为DatabaseUtil
@PropertySource("classpath:JDBCConfig.properties")
public class DBUtil {

    // 根据JDBCConfig.properties配置中设置的值来给成员变量注入数据
    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.connectionTimeout}")
    private long connectionTimeout;

    @Value("${jdbc.idleTimeout}")
    private long idleTimeout;

    @Value("${jdbc.maximumPoolSize}")
    private int maximumPoolSize;

    @Bean(name = "DataSource") //将该方法的返回值作为bean存入Spring IOC容器，id设为DataSource
    public DataSource createDataSource() {
        // 这里使用HikariConfig数据库连接池
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setConnectionTimeout(connectionTimeout);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);

        DataSource ds = new HikariDataSource(hikariConfig);
        return ds;
    }

//    public static void main(String[] args){
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
//        hikariConfig.setJdbcUrl("jdbc:mysql://47.93.44.20:3306/SSO");
//        hikariConfig.setUsername("root");
//        hikariConfig.setPassword("123");
//        hikariConfig.setConnectionTimeout(1000);
//        hikariConfig.setIdleTimeout(60000);
//        hikariConfig.setMaximumPoolSize(10);
//        DataSource ds = new HikariDataSource(hikariConfig);
//        return;
//    }
}
