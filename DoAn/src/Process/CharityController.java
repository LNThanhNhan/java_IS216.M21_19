/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import ConnectDB.OracleConnection;
import Model.Account;
import Model.Charity;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import static oracle.net.aso.b.i;
import java.sql.PreparedStatement;

/**
 *
 * @author MyPC
 */
public class CharityController {
    
    //Lấy dữ liệu charity
    public  ArrayList<Charity> getCharity()
    {
        ArrayList<Charity> ListCharity = new <Charity>ArrayList();
        try{
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT * FROM Charity";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Charity Charity = new Charity();
                
                Charity.setIdchar(rs.getInt("IDCHAR"));
                Charity.setUsername(rs.getString("USERNAME"));
                Charity.setName(rs.getString("NAME"));
                Charity.setPhone(rs.getString("PHONE"));
                Charity.setProvince(rs.getString("PROVINCE"));
                Charity.setDistrict(rs.getString("DISTRICT"));
                Charity.setTown(rs.getString("TOWN"));
                Charity.setAddress(rs.getString("ADDRESS"));
                Charity.setHasfood(rs.getInt("HASFOOD"));
                Charity.setHasnecess(rs.getInt("HASNECESS"));
                Charity.setHasequip(rs.getInt("HASEQUIP"));
                Charity.setPoint(rs.getInt("POINT"));

                ListCharity.add(Charity);  
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
        return ListCharity;
    }
    
    //Lấy giá trị ID tiếp theo của trung tâm
    public static String getNextValueCharity() {
        String id = "";
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql1 = "SELECT IDCHAR.NEXTVAL  s FROM DUAL";
            Statement statement1 = con.createStatement();
            ResultSet rs1 = statement1.executeQuery(sql1);
                       
            if (rs1.next()) {
                id = rs1.getString("s");
            }
            
            String sql2 = "ALTER SEQUENCE IDCHAR INCREMENT BY -1";
            Statement statement2 = con.createStatement();
            ResultSet rs2 = statement2.executeQuery(sql2);
            
            String sql3 = "SELECT IDCHAR.NEXTVAL FROM DUAL";
            Statement statement3 = con.createStatement();
            ResultSet rs3 = statement3.executeQuery(sql3);
            
            String sql4 = "ALTER SEQUENCE IDCHAR INCREMENT BY 1";
            Statement statement4 = con.createStatement();
            ResultSet rs4 = statement4.executeQuery(sql4);
            
            
            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
            con.close();
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    } 
    
    //Thêm trung tâm
    public static int AddCharity(Charity Charity, Account account) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_INSERT_CHARITY(?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);

            ps.setString(1, Charity.getUsername());
            ps.setString(2, account.getPassword());
            ps.setString(3, Charity.getName());
            ps.setString(4, Charity.getPhone());
            ps.setString(5, Charity.getProvince());
            ps.setString(6, Charity.getDistrict());
            ps.setString(7, Charity.getTown());
            ps.setString(8, Charity.getAddress());
            ps.setInt(9, Charity.getHasfood());
            ps.setInt(10, Charity.getHasnecess());
            ps.setInt(11, Charity.getHasequip());

            ps.execute();
            ps.close();
            con.close();
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 1400) {
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            } else if (sqlex.getErrorCode() == 1) {
                JOptionPane.showMessageDialog(null, "Số điện thoại hoặc tên tài khoản này đã tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            }
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        return 0;

    }
    
    //Xóa trung tâm
    public static int DeleteCharity (int Idchar) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_DELETE_CHARITY(?)}";
            CallableStatement ps = con.prepareCall(sql);           
            
            ps.setInt(1, Idchar);
            
            ps.execute();
            ps.close();
            con.close();
            
        }catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 20093)
                JOptionPane.showMessageDialog(null, "Trung tâm này còn yêu cầu chưa hoàn thành",
                        "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            return 1;
        }  
        catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        return 0;    
    }
     
    //Dùng để miêu tả Lost updated
     public static int UpdateCharityLostUpdate(Connection con, Charity Charity) {
        try {
            String sql = "{CALL PROC_UPDATE_CHARITY(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);
                      
            ps.setInt(1, Charity.getIdchar());
            ps.setString(2, Charity.getName());
            ps.setString(3, Charity.getPhone());
            ps.setString(4, Charity.getProvince());
            ps.setString(5, Charity.getDistrict());
            ps.setString(6, Charity.getTown());
            ps.setString(7, Charity.getAddress());
            ps.setInt(8, Charity.getHasfood());
            ps.setInt(9, Charity.getHasnecess());
            ps.setInt(10, Charity.getHasequip());
            
            
            ps.execute();
            //ps.close();
            //con.close();
            
            
        }catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 1407)
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqlex.getErrorCode() == 20081)
                JOptionPane.showMessageDialog(null, "Trung tâm này không còn tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqlex.getErrorCode() == 1)
                JOptionPane.showMessageDialog(null, "Số điện thoại này đã tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            sqlex.printStackTrace();
            return 1;
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        
        return 0;
    }
     
     // Thêm trung tâm
    public static int UpdateCharity(Charity Charity) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_UPDATE_CHARITY(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);
                      
            ps.setInt(1, Charity.getIdchar());
            ps.setString(2, Charity.getName());
            ps.setString(3, Charity.getPhone());
            ps.setString(4, Charity.getProvince());
            ps.setString(5, Charity.getDistrict());
            ps.setString(6, Charity.getTown());
            ps.setString(7, Charity.getAddress());
            ps.setInt(8, Charity.getHasfood());
            ps.setInt(9, Charity.getHasnecess());
            ps.setInt(10, Charity.getHasequip());
            
            ps.execute();
            
            ps.close();
            con.close();
            
        }catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 1407)
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqlex.getErrorCode() == 20081)
                JOptionPane.showMessageDialog(null, "Trung này không còn tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqlex.getErrorCode() == 1)
                JOptionPane.showMessageDialog(null, "Số điện thoại này đã tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            sqlex.printStackTrace();
            return 1;
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        
        return 0;
    }
     
    //Xuất thống kê
     public static void exportCharityMarkToPdf(int idchar) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String source = "src/Resource/report3_HoatDongCuaTrungTamThienNguyen.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(source);

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("MaTrungTam", idchar);
            
            JasperPrint jp = JasperFillManager.fillReport(jr, params, con);
            
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     //Lấy thông tin nhân viên để load lên card thông tin nhân viien
     public  Charity getCharityInfo(String username)
    {
        Charity Charity = new Charity();
        
         try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT * FROM Charity WHERE Username = ?";
            PreparedStatement  ps = con.prepareStatement(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Charity.setIdchar(rs.getInt("IDCHAR"));
                Charity.setUsername(rs.getString("USERNAME"));
                Charity.setName(rs.getString("NAME"));
                Charity.setPhone(rs.getString("PHONE"));
                Charity.setProvince(rs.getString("PROVINCE"));
                Charity.setDistrict(rs.getString("DISTRICT"));
                Charity.setTown(rs.getString("TOWN"));
                Charity.setAddress(rs.getString("ADDRESS"));
                Charity.setHasfood(rs.getInt("HASFOOD"));
                Charity.setHasnecess(rs.getInt("HASNECESS"));
                Charity.setHasequip(rs.getInt("HASEQUIP"));
                Charity.setPoint(rs.getInt("POINT"));
                return Charity;  
            }
            rs.close();
            //st.close();
            con.close();
            return Charity;
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return Charity;
    }
}
