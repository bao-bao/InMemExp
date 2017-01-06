package Expriments;

/* Created by AMXPC on 2017/1/4. */

import DBConnect.ConnectionPool;
import DBConnect.DBConnector;
import DataAccess.DataAccessor;
import oracle.jdbc.internal.OracleTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class DepositExp implements Runnable {
    private ConnectionPool connectionPool;
    private int count;
    private String cardid;
    private String pwd;

    DepositExp(int i, ConnectionPool connectionPool) {
        this.count = i;
        this.connectionPool = connectionPool;
    }

    public void run() {
        try {
            if (connectionPool.getType() == DBConnector.ORACLE) {
                cardid = DataAccessor.getDataAccessor().getOracleCardidArray().get(count);
                pwd = DataAccessor.getDataAccessor().getOraclePwdArray().get(count);
            } else if (connectionPool.getType() == DBConnector.TIMESTEN) {
                cardid = DataAccessor.getDataAccessor().getTimesTenCardidArray().get(count);
                pwd = DataAccessor.getDataAccessor().getTimesTenPwdArray().get(count);
            }
            String sql = "{?=call CHANGE_DEPOSIT(?,?,?,?)}";
            CallableStatement pstmt = connectionPool.getConnection().prepareCall(sql);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, cardid);
            pstmt.setString(3, pwd);
            pstmt.setBigDecimal(4, BigDecimal.valueOf((int)(Math.random()*400)));
            pstmt.registerOutParameter(5, OracleTypes.INTEGER);
            pstmt.execute();
            pstmt.getConnection().close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("SQL Failed");
            e.printStackTrace();
        }
    }
}
