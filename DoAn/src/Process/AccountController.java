/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import ConnectDB.OracleConnection;
import Model.Person;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author MyPC
 */
public class AccountController {
    public static int getRoleUser(String username){
        int role=-1;
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT role FROM Account WHERE Username = ?";
            PreparedStatement  ps = con.prepareStatement(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                role = rs.getInt("role");
                return role;
            }
            rs.close();
            //st.close();
            con.close();
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return role;
    }
    
   
    
    public static int CheckAccount(String username, String password) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT role FROM Account WHERE Username = ? AND Password = ?";
            PreparedStatement  ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                return 0;
            }
            rs.close();
            //st.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu không đúng, vui lòng thử lại",
                        "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            return 1;
        }
        catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1;
    }
    

     
    public static int checkSignUpAccount(String usename) { 
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT username FROM Account WHERE Username = '"+usename+"'";
            Statement  ps = con.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Tên người dùng đã tồn tại",
                        "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            
               return 1; //exist
            }
                        
            ps.close();
            con.close();
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        return 0;
    }
    
}
