package DBConnect;

/* Created by AMXPC on 2017/1/4. */

import java.sql.Connection;

public class DBConnector {
    public static final int ORACLE = 1;
    public static final int TIMESTEN = 2;

    private OracleConnector oracleConnection;
    private TimesTenConnector timesTenConnection;
    private static DBConnector dbConnector;

    private DBConnector() {
        initConnection();
    }

    public static void init() {
        dbConnector = new DBConnector();
    }

    private void initConnection() {
        try {
            oracleConnection = new OracleConnector();
            System.out.println("Connected To Oracle Successfully");
        } catch (Exception e) {
            System.out.println("Connect To Oracle Failed");
            e.printStackTrace();
        }

        try {
            timesTenConnection = new TimesTenConnector();
            System.out.println("Connected To TimesTen Successfully");
        } catch (Exception e) {
            System.out.println("Connect To TimesTen Failed");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            oracleConnection.close();
        } catch (Exception e) {
            System.out.println("DisConnect Oracle Failed");
            e.printStackTrace();
        }
        try {
            timesTenConnection.close();
        } catch (Exception e) {
            System.out.println("DisConnect TimesTen Failed");
            e.printStackTrace();
        }
    }

    public Connection getOracleConnection() {
        return oracleConnection.getConnection();
    }

    public Connection getTimesTenConnection() {
        return timesTenConnection.getConnection();
    }

    public static DBConnector getDbConnector() {
        return dbConnector;
    }
}
