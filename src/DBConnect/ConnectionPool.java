package DBConnect;

/* Created by AMXPC on 2017/1/5. */

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static DataSource dataSource;
    private String propertiesPath;
    private int Type;

    public ConnectionPool(String dbcpPropertiesPath, int type) {
        this.propertiesPath = dbcpPropertiesPath;
        this.Type = type;
        initDataSource();
    }

    private void initDataSource() {
        FileInputStream is = null;
        Properties properties = new Properties();

        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;

        int initialSize = 0;
        int minIdle = 0;
        int maxIdle = 0;
        int maxTotal = 0;

        try {
            is = new FileInputStream(propertiesPath);
            properties.load(is);

            driverClassName = properties.getProperty("dbcp.driverClassName");
            url = properties.getProperty("dbcp.url");
            username = properties.getProperty("dbcp.username");
            password = properties.getProperty("dbcp.password");
            initialSize = Integer.parseInt((properties.getProperty("dbcp.initialSize").trim()));
            minIdle = Integer.parseInt((properties.getProperty("dbcp.minIdle")).trim());
            maxIdle = Integer.parseInt((properties.getProperty("dbcp.maxIdle")).trim());
            maxTotal = Integer.parseInt((properties.getProperty("dbcp.maxTotal")).trim());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }finally{
            try {
                assert is != null;
                is.close();
            } catch (IOException e) {
                System.out.println("Can't Close File");
                e.printStackTrace();
            }
        }
        BasicDataSource bds = new BasicDataSource();

        bds.setUrl(url);
        bds.setDriverClassName(driverClassName);
        bds.setUsername(username);
        bds.setPassword(password);
        bds.setInitialSize(initialSize);
        bds.setMaxTotal(maxTotal);
        bds.setMinIdle(minIdle);
        bds.setMaxIdle(maxIdle);

        dataSource = bds;
        System.out.println("Connection Pool Ready");
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            System.out.println("Database Connection Pool Failed");
        }
        Connection conn = null;
        if (dataSource != null) {
            conn = dataSource.getConnection();
        }
        return conn;
    }

    public int getType() {
        return Type;
    }
}
