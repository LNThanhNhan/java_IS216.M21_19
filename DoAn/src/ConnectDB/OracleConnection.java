/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnectDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
public class OracleConnection {
    
    public static Connection getOracleConnection() {
        try {
            String hostName = "localhost";
            String sid = "orcl";
            //Luu y: can tao 1 user trong oracle voi
            //username: doan
            //pass: 123
            String userName = "doan";
            String password = "123";

            Class.forName("oracle.jdbc.driver.OracleDriver");

            String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" + sid;
            Connection conn = DriverManager.getConnection(connectionURL, userName,
                    password);
            return conn;
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
