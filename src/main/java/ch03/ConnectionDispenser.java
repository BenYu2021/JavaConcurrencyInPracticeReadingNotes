package ch03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: ymm
 * @date: 2021/7/29
 * @version: 1.0.0
 * @description: 3-10 使用ThreadLocal来维持线程封闭性
 * dispenser：(取款 、 售货等用的)自动取物装置;
 */
public class ConnectionDispenser {

    static String DB_URL = "jdbc:mysql://localhost/mydatabase";

    private ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            try {
                return DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                throw new RuntimeException("Unable to acquire Connection, e");
            }
        }
    };

    public Connection getConnection() {
        return connectionHolder.get();
    }
}
