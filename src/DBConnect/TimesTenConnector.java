package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* Created by AMXPC on 2017/1/4. */

class TimesTenConnector {
    private static final String DBDRIVER = "com.timesten.jdbc.TimesTenDriver";
    private static final String DBURL = "jdbc:timesten:client:DSN=TT";
    private static final String DBuser = "bankadm";
    private static final String DBPASSWORD = "bankadm";
    private Connection conn = null;

    TimesTenConnector() throws Exception {
        try {
            Class.forName(DBDRIVER);
            conn = DriverManager.getConnection(DBURL, DBuser, DBPASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("jdbc Driver cannot found.");
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    Connection getConnection() {
        return this.conn;
    }

    void close() throws Exception {
        if (this.conn != null) {
            this.conn.close();
        }
    }
}
