package course.academy.jdbc;

import course.academy.entities.Administrator;
import course.academy.entities.enums.Gender;
import course.academy.entities.enums.Role;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class JdbcSimpleDemo {
    public static final String SELECT_ALL_ADMINS = "select * from `administrators`;";

    public void run() throws IOException, ClassNotFoundException, SQLException {
        Properties props = new Properties();
        String dbConfigPath = JdbcSimpleDemo.class.getClassLoader()
                .getResource("jdbc.properties").getPath();
        props.load(new FileInputStream(dbConfigPath));
        log.info("jdbc.properties: {}", props);

        try {
            Class.forName(props.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            log.error("DB driver class not found", e);
            throw e;
        }
        log.info("DB driver loaded successfully: {}", props.getProperty("driver"));

        try (Connection con = DriverManager.getConnection(props.getProperty("url"), props);
             PreparedStatement statement = con.prepareStatement(SELECT_ALL_ADMINS)) {
            log.info("DB Connection established successfully to schema: {}", con.getCatalog());

            ResultSet rs = statement.executeQuery();

            for (Administrator admin : toAdmins(rs)) {
                System.out.println(admin);
            }
        } catch (SQLException e) {
            log.error("Error creating connection to DB", e);
            throw e;
        }
    }

    public List<Administrator> toAdmins(ResultSet rs) throws SQLException {
        List<Administrator> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Administrator(
                    rs.getLong(1),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("tel_number"),
                    rs.getString("username"),
                    rs.getString("password"),
                    Gender.valueOf(rs.getString("gender")),
                    Role.valueOf(rs.getString("role"))
            ));
        }
        return results;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        var demo = new JdbcSimpleDemo();
        demo.run();
    }
}
