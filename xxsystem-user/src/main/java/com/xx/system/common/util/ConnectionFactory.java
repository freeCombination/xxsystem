package com.xx.system.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 数据库连接工具类
 * 
 * @version V1.20,2013-12-6 下午4:56:13
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
public class ConnectionFactory {
    /**
     * 取链接
     * 
     * @Title getConnection
     * @author wanglc
     * @date 2013-12-6
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            String driver = PropertyUtil.get("jdbc.driver");
            String url = PropertyUtil.get("jdbc.url");
            String username = PropertyUtil.get("jdbc.username");
            String password = PropertyUtil.get("jdbc.password");
            try {
                // 注册JDBC驱动
                Class.forName(driver);
            }
            catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionFactory.class.getName())
                    .log(Level.SEVERE, null, ex);
            }
            try {
                con = DriverManager.getConnection(url, username, password);
            }
            catch (SQLException ex) {
                Logger.getLogger(ConnectionFactory.class.getName())
                    .log(Level.SEVERE, null, ex);
            }
            
        }
        catch (Exception ex) {
            Logger.getLogger(ConnectionFactory.class.getName())
                .log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
