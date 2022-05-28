/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;
import ConnectDB.OracleConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import javax.swing.JOptionPane;
/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
public class AccountController {
    private Connection con;

    public int ChangePassword(String Username,String CurrentPass, String NewPassString)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String CallProc = "{call PROC_CHANGE_PASSWORD(?,?,?)}";
            CallableStatement callSt=con.prepareCall(CallProc);;
            callSt.setString(1,Username);
            callSt.setString(2,CurrentPass);
            callSt.setString(3,NewPassString);
            callSt.execute();
            callSt.close();
            con.close();
            return 1;
        }catch (SQLException sqle) {
            if (sqle.getErrorCode() == 20103)
                JOptionPane.showMessageDialog(null, "Sai mật khẩu hiện tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqle.getErrorCode() == 20104)
                JOptionPane.showMessageDialog(null, "Mật khẩu mới trùng với mật khẩu cũ!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            sqle.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
