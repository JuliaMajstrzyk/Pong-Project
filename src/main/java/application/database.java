package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    private static Connection connection;

    /**
     * this returns the connection to the db, otherwise it creates one by calling createConnection()
     * @return connection to db
     * @throws SQLException if db access error occurs
     * @throws ClassNotFoundException  if db driver class is not found
     */

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if(connection==null || connection.isClosed()){
            createConnection();
        }
        return connection;
    }

    /**
     * this creates a connection to the db
     * @throws SQLException if db access error occurs
     * @throws ClassNotFoundException if db driver class is not found
     */
    private static void createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/gamedb";
        String user="root";
        String pass="password";
        connection = DriverManager.getConnection(url,user,pass);
    }
}
