package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    public static Connection createDbConnection(Properties props) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(props.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw e;
        }
        return DriverManager.getConnection(props.getProperty("url"), props);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
