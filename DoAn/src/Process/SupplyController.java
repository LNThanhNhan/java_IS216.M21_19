/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import ConnectDB.OracleConnection;
import Model.Person;
import Model.Supply;
import View.ChangeValue;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Tran Nhat Sinh
 */
public class SupplyController {

    private Connection con;

    //Ham lay cac yeu tiep te cua da gui cua nguoi dung do 
    public ArrayList<HashMap> getSupply(int idper) {
        try
        {
            con = OracleConnection.getOracleConnection();
            String sql = "select idsup,name,created,needfood,neednecess,needequip,status,detail\n"
                    + "from supply s\n"
                    + "left join charity p\n"
                    + "on s.idchar=p.idchar\n"
                    + "where idper=" + idper;
            ArrayList<HashMap> list = new ArrayList<HashMap>();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next())
            {
                HashMap<String, String> sup = new HashMap<String, String>();
                sup.put("idsup", rs.getString(1));
                sup.put("name", rs.getString(2));
                sup.put("created", ChangeValue.DateToString(rs.getDate(3)));
                sup.put("needfood", Integer.toString(rs.getInt(4)));
                sup.put("neednecess", Integer.toString(rs.getInt(5)));
                sup.put("needequip", Integer.toString(rs.getInt(6)));
                sup.put("status", Integer.toString(rs.getInt(7)));
                sup.put("detail", rs.getString(8));
                list.add(sup);
            }
            rs.close();
            stat.close();
            con.close();
            return list;
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        } catch (UnsupportedOperationException ex)
        {
            ex.printStackTrace();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return new ArrayList<HashMap>();
    }
    
    
    
    

    //Ham lay cac yeu cau tiep te da mo cua nguoi dung do
    public ArrayList<HashMap> getSupplyHaveStatus1(int idper) {
        try
        {
            con = OracleConnection.getOracleConnection();
            String sql = "select idsup,name,created,needfood,neednecess,needequip,status,detail\n"
                    + "from supply s\n"
                    + "left join charity p\n"
                    + "on s.idchar=p.idchar\n"
                    + "where status=1 and\n"
                    + "idper= " + idper;
            ArrayList<HashMap> list = new ArrayList<HashMap>();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next())
            {
                HashMap<String, String> sup = new HashMap<String, String>();
                sup.put("idsup", rs.getString(1));
                sup.put("name", rs.getString(2));
                sup.put("created", ChangeValue.DateToString(rs.getDate(3)));
                sup.put("needfood", Integer.toString(rs.getInt(4)));
                sup.put("neednecess", Integer.toString(rs.getInt(5)));
                sup.put("needequip", Integer.toString(rs.getInt(6)));
                sup.put("status", Integer.toString(rs.getInt(7)));
                sup.put("detail", rs.getString(8));
                list.add(sup);
            }
            rs.close();
            stat.close();
            con.close();
            return list;
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        } catch (UnsupportedOperationException ex)
        {
            ex.printStackTrace();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return new ArrayList<HashMap>();
    }

    public static String getNextValueSupply() {
        String id = "";
        try
        {
            Connection con = OracleConnection.getOracleConnection();
            String sql1 = "SELECT IDSUP.NEXTVAL  s FROM DUAL";
            Statement statement1 = con.createStatement();
            ResultSet rs1 = statement1.executeQuery(sql1);

            if (rs1.next())
            {
                id = rs1.getString("s");
            }

            String sql2 = "ALTER SEQUENCE IDSUP INCREMENT BY -1";
            Statement statement2 = con.createStatement();
            ResultSet rs2 = statement2.executeQuery(sql2);

            String sql3 = "SELECT IDSUP.NEXTVAL FROM DUAL";
            Statement statement3 = con.createStatement();
            ResultSet rs3 = statement3.executeQuery(sql3);

            String sql4 = "ALTER SEQUENCE IDSUP INCREMENT BY 1";
            Statement statement4 = con.createStatement();
            ResultSet rs4 = statement4.executeQuery(sql4);

            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
            con.close();
            return id;
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return id;
    }

    public static int AddSupply(Supply Supply) {
        try
        {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_INSERT_SUPPLY(?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);

            ps.setInt(1, Supply.getIdper());
            ps.setInt(2, Supply.getNeedfood());
            ps.setInt(3, Supply.getNeednecess());
            ps.setInt(4, Supply.getNeedequip());
            ps.setString(5, Supply.getDetail());
            //ps.setInt(6,Supply.getStatus());

            ps.execute();
            ps.close();
            con.close();
        } catch (SQLException sqlex)
        {
            if (sqlex.getErrorCode() == 1400)
            {
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            } else if (sqlex.getErrorCode() == 20500)
            {
                JOptionPane.showMessageDialog(null, "Yêu cầu về lương thực chưa được hoàn thành",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            } else if (sqlex.getErrorCode() == 20501)
            {
                JOptionPane.showMessageDialog(null, "Yêu cầu về nhu yếu phẩm chưa được hoàn thành",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            } else if (sqlex.getErrorCode() == 20502)
            {
                JOptionPane.showMessageDialog(null, "Yêu cầu về vật dụng chưa được hoàn thành",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            } else if (sqlex.getErrorCode() == 20010)
            {
                JOptionPane.showMessageDialog(null, "Người dùng không tồn tại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            }

            return 1;
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }

    public static int DeleteSupply(int Idsup) {
        try
        {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_DELETE_SUPPLY(?)}";
            CallableStatement ps = con.prepareCall(sql);

            ps.setInt(1, Idsup);

            ps.execute();

            ps.close();
            con.close();
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 20152) {
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu không hợp lệ, không thể xóa!",
                        "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            }
            return 1;
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return 0;
    }


    public static int UpdateSupply(Supply Supply) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_UPDATE_SUPPLY(?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);
                      
            ps.setInt(1, Supply.getIdsup());
            ps.setInt(2, Supply.getNeedfood());
            ps.setInt(3, Supply.getNeednecess());
            ps.setInt(4, Supply.getNeedequip());
            ps.setString(5,Supply.getDetail());
            //ps.setInt(6,Supply.getStatus());
            
            ps.execute();
            
            ps.close();
            con.close();
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 1407){
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 20081){
                JOptionPane.showMessageDialog(null, "Yêu cầu tiếp tế này không còn tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            
            return 1;
        }
        
        catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        return 0;       
    }
}
