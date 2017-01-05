package Expriments;

/* Created by AMXPC on 2017/1/4. */

import DBConnect.ConnectionPool;
import DBConnect.DBConnector;
import DataAccess.DataAccessor;
import oracle.jdbc.internal.OracleTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class ComplexExp implements Runnable {
    ConnectionPool connectionPool;


    ComplexExp(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void run() {
        try {
            String sql = "{SELECT NAME " +
                    "FROM BANKCARD BC JOIN " +
                    "BANKUSER BU JOIN (" +
                    "SELECT * FROM FINANCIALFLOW WHERE TO_CHAR(FLOWTIME, 'YYYY-MM') = '2017-01') FF " +
                    "WHERE BC.DEPOSIT < 2000}";
            CallableStatement pstmt =connectionPool.getConnection().prepareCall(sql);
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
