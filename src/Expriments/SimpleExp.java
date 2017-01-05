package Expriments;

/* Created by AMXPC on 2017/1/4. */

import DBConnect.ConnectionPool;
import DBConnect.DBConnector;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class SimpleExp implements Runnable {
    private ConnectionPool connectionPool;

    SimpleExp(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void run() {
        try {
            String sql = "SELECT count(1) FROM FINANCIALFLOW WHERE AMOUNT < ?";
            CallableStatement pstmt = connectionPool.getConnection().prepareCall(sql);
            pstmt.setBigDecimal(1, BigDecimal.valueOf((int) (Math.random() * 2000)));
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
