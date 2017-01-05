/*
package Expriments;

*/
/* Created by AMXPC on 2017/1/4. *//*


import DBConnect.DBConnector;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class SerialSimpleExp implements Runnable {
    String sql = "SELECT count(1) FROM FINANCIALFLOW WHERE AMOUNT < ?";
    private CallableStatement pstmt;

    SerialSimpleExp(int i, int databaseType) {
        try {
            if (databaseType == DBConnector.ORACLE) {
                pstmt = DBConnector.getDbConnector().getOracleConnection().prepareCall(sql);
            } else if (databaseType == DBConnector.TIMESTEN) {
                pstmt = DBConnector.getDbConnector().getTimesTenConnection().prepareCall(sql);
            }
        } catch (SQLException e) {
            System.out.println("SQL Failed");
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            pstmt.setBigDecimal(1, BigDecimal.valueOf((int)(Math.random()*2000)));
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
*/
