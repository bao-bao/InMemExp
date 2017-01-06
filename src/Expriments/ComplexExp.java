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
    private ConnectionPool connectionPool;


    ComplexExp(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    // no index key in SQL
//    public void run() {
//        try {
//            String sql = "SELECT DISTINCT NAME FROM (SELECT ACD.USERID FROM (SELECT BC.USERID FROM BANKCARD BC WHERE BC.DEPOSIT < 2000) ACD JOIN (SELECT FF.USERID FROM FINANCIALFLOW FF WHERE TO_CHAR(FF.FLOWTIME, 'YYYY-MM') = '2017-01') FF2 ON ACD.USERID = FF2.USERID) AA JOIN BANKUSER BU ON AA.USERID = BU.USERID";
//            CallableStatement pstmt =connectionPool.getConnection().prepareCall(sql);
//            pstmt.execute();
//            pstmt.getConnection().close();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    // index key in SQL
    public void run() {
        try {
            String sql = "SELECT BC.CARDID,BC.PWD FROM BANKCARD BC JOIN (SELECT DISTINCT CARDID FROM FINANCIALFLOW FF WHERE EXISTS (SELECT AMOUNT FROM FINANCIALFLOW WHERE ABS(AMOUNT) > 100)) FC ON BC.PWD > '356378'";
            CallableStatement pstmt =connectionPool.getConnection().prepareCall(sql);
            pstmt.execute();
            pstmt.getConnection().close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
