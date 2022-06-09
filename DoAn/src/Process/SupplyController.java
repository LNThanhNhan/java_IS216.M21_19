/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.OracleConnection;
import Model.Account;
import Model.Supply;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author MyPC
 */
public class SupplyController {
    public  ArrayList<Supply> getSupply()
    {
        ArrayList<Supply> ListSupply = new <Supply>ArrayList();
        try{
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT * FROM Supply";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Supply Supply = new Supply();
                
                Supply.setIdsup(rs.getInt("IDSUP"));
                Supply.setIdchar(rs.getInt("IDCHAR"));
                Supply.setIdper(rs.getInt("IDPER"));
                Supply.setCreated(rs.getDate("CREATED"));
                Supply.setNeedfood(rs.getInt("NEEDFOOD"));
                Supply.setNeednecess(rs.getInt("NEEDNECESS"));
                Supply.setNeedequip(rs.getInt("NEEDEQUIP"));
                Supply.setStatus(rs.getInt("STATUS"));
                Supply.setDetail(rs.getString("DETAIL"));
                

                ListSupply.add(Supply);  
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
        return ListSupply;
    }
    
    public static String getNextValueSupply() {
        String id = "";
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql1 = "SELECT IDSUP.NEXTVAL  s FROM DUAL";
            Statement statement1 = con.createStatement();
            ResultSet rs1 = statement1.executeQuery(sql1);
                       
            if (rs1.next()) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    } 
    
    public static int AddSupply( Supply Supply) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_INSERT_SUPPLY(?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);
                       
            ps.setInt(1, Supply.getIdper());
            ps.setInt(2, Supply.getNeedfood());
            ps.setInt(3, Supply.getNeednecess());
            ps.setInt(4, Supply.getNeedequip());
            ps.setString(5,Supply.getDetail()); 
            //ps.setInt(6,Supply.getStatus());
            
            ps.execute();
            ps.close();
            con.close();
            
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 1400 ){
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 1){
                JOptionPane.showMessageDialog(null, "Số điện thoại này đã tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 2291){
                JOptionPane.showMessageDialog(null, "Người dùng không tồn tại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            
            return 1;
        } 
        catch (Exception ex) {
            ex.printStackTrace();  
            return 1;
        }
        return 0;
    }  
    
    public static int DeleteSupply (int Idsup) {
        try {
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
            } else if (sqlex.getErrorCode() == 20153) {
                JOptionPane.showMessageDialog(null, "Yêu cầu không tồn tại trong hệ thống!",
                        "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            }
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
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
   
    public static int VerifySupply(int Idsup) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_VERIFY_SUPPLY(?)}";
            CallableStatement ps = con.prepareCall(sql);
                      
            ps.setInt(1, Idsup);
            
            ps.execute();
            ps.close();
            con.close();
            
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 20202){
                JOptionPane.showMessageDialog(null, "Trạng thái không hợp lệ ! Không thể xác thực yêu cầu",
                        "cảnh báo", JOptionPane.WARNING_MESSAGE);}
            else if (sqlex.getErrorCode() == 1){
                JOptionPane.showMessageDialog(null, "Yêu cầu này không còn tồn tại trong hệ thống",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            return 1;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }   
        return 0;
    }
    
    public static int DenySupply(int Idsup) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_DENY_SUPPLY(?)}";
            CallableStatement ps = con.prepareCall(sql);
                      
            ps.setInt(1, Idsup);
            
            ps.execute();
            ps.close();
            con.close();
            
        }catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 20202){
                JOptionPane.showMessageDialog(null, "Trạng thái không hợp lệ ! Không thể hủy yêu cầu",
                        "cảnh báo", JOptionPane.WARNING_MESSAGE);}
            else if (sqlex.getErrorCode() == 1){
                JOptionPane.showMessageDialog(null, "Yêu cầu này không còn tồn tại trong hệ thống",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            return 1;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }   
        return 0;
    }
    
    public static int  DeleteOverdueSupply() {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_DELETGGE_OVERDUE()}";
            CallableStatement ps = con.prepareCall(sql);
            
            ps.execute();
            ps.close();
            con.close();
            
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 6550)
                JOptionPane.showMessageDialog(null, "Không có yêu cầu quá hạn nào!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
            return 1;
        }
        
        catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }   
        return 0;
    }
    
    
    public static void exportSupplyToPdf() {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String source = "src/Resource/report4_ThongKeYeuCauTheoTP.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(source);

            //HashMap<String, Object> params = new HashMap<String, Object>();
            //params.put("classId", classId);
            //params.put("className", className);

            //String localDir = System.getProperty("user.dir");
            JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), con);
            OutputStream os = new FileOutputStream("STUDENT_MARK_" +  ".pdf");
            //JasperExportManager.exportReportToPdfStream(jp, "test.pdf" );
            JasperExportManager.exportReportToPdfFile(jp, "test.pdf" );
            JasperViewer.viewReport(jp, false);

            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
     
    
     
}
