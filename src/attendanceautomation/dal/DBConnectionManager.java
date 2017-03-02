package attendanceautomation.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DBConnectionManager {

    private static DBConnectionManager instance;

    private static final String CONFIG_FILE_NAME = "MyTunesDB.cfg";
    private final SQLServerDataSource ds;

    public static DBConnectionManager getInstance() throws IOException {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    private DBConnectionManager() throws IOException {
        Properties props = new Properties();
        props.load(new FileReader(CONFIG_FILE_NAME));

        ds = new SQLServerDataSource();
        ds.setServerName(props.getProperty("SERVER"));
        ds.setDatabaseName(props.getProperty("DATABASE"));
        ds.setPortNumber(Integer.parseInt(props.getProperty("PORT")));
        ds.setUser(props.getProperty("USER"));
        ds.setPassword(props.getProperty("PASSWORD"));
    }

    public Connection getConnection() throws SQLServerException {
        return ds.getConnection();
    }
}
