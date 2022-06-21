/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import ConnectDB.OracleConnection;
import Model.*;
import View.UpdateDoctorScreen;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class DoctorController {
    
    //Lấy dữ liệu bác sĩ
    public  ArrayList<Doctor> getDoctor()
    {
        ArrayList<Doctor> ListDoctor = new <Doctor>ArrayList();
        try{
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT * FROM Doctor";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Doctor doctor = new Doctor();
                
                doctor.setIddoc(rs.getInt("IDDOC"));
                doctor.setUsername(rs.getString("USERNAME"));
                doctor.setName(rs.getString("NAME"));
                doctor.setGender(rs.getInt("Gender"));
                doctor.setPhone(rs.getString("PHONE"));
                doctor.setProvince(rs.getString("PROVINCE"));
                doctor.setAccademicrank(rs.getInt("ACADEMICRANK"));
                doctor.setSubject(rs.getInt("SUBJECT"));
                doctor.setWorkunits(rs.getString("WORKUNITS"));
                

                ListDoctor.add(doctor);  
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
        return ListDoctor;
    }
    
    //Thêm bác sĩ
     public static int  AddDoctor( Doctor doctor, Account account) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_INSERT_DOCTOR(?,?,?,?,?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);
            
            
            ps.setString(1, doctor.getUsername());
            ps.setString(2, account.getPassword());
            ps.setString(3, doctor.getName());
            ps.setInt(4, doctor.getGender());
            ps.setString(5, doctor.getPhone());           
            ps.setInt(6, doctor.getAccademicrank());
            ps.setInt(7, doctor.getSubject());
            ps.setString(8, doctor.getWorkunits());
            ps.setString(9, doctor.getProvince());
            
            ps.execute();
            ps.close();
            con.close();
            
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 1400 ){
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 1){
                JOptionPane.showMessageDialog(null, "Số điện thoại hoặc tên người dùng này đã tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            
            sqlex.printStackTrace();
            return 1;

        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
       return 0;       
    }  
     
    //Lấy giá trị ID tiếp theo của bác sĩ
    public static String getNextValueDoctor() {
        String id = "";
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql1 = "SELECT IDDOC.NEXTVAL  s FROM DUAL";
            Statement statement1 = con.createStatement();
            ResultSet rs1 = statement1.executeQuery(sql1);
                       
            if (rs1.next()) {
                id = rs1.getString("s");
            }
            
            String sql2 = "ALTER SEQUENCE IDDOC INCREMENT BY -1";
            Statement statement2 = con.createStatement();
            ResultSet rs2 = statement2.executeQuery(sql2);
            
            String sql3 = "SELECT IDDOC.NEXTVAL FROM DUAL";
            Statement statement3 = con.createStatement();
            ResultSet rs3 = statement3.executeQuery(sql3);
            
            String sql4 = "ALTER SEQUENCE IDDOC INCREMENT BY 1";
            Statement statement4 = con.createStatement();
            ResultSet rs4 = statement4.executeQuery(sql4);
            
            
            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
            con.close();
            return id;
        } 
        
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    } 
    
    //Xóa bác sĩ
     public static int DeleteDoctor (int Iddoc) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_DELETE_DOCTOR(?)}";
            CallableStatement ps = con.prepareCall(sql);           
            
            ps.setInt(1, Iddoc);
            
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(2);
            }
            ps.close();
            con.close();
            return generatedKey;
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 20122){
                JOptionPane.showMessageDialog(null, "Bác sĩ này còn yêu cầu chưa hoàn thành",
                        "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            }
            return 1;
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;    
    }
     
     //Cập nhât thông tin bác sĩ
     public static int UpdateDoctor(Doctor doctor) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_UPDATE_DOCTOR(?,?,?,?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);
            
            
            ps.setInt(1, doctor.getIddoc());
            ps.setString(2, doctor.getName());
            ps.setInt(3, doctor.getGender());
            ps.setString(4, doctor.getPhone());
            ps.setInt(5, doctor.getAccademicrank());
            ps.setInt(6, doctor.getSubject());
            ps.setString(7, doctor.getWorkunits());
            ps.setString(8, doctor.getProvince());
            
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            int generatedKey = 0;
          
            ps.close();
            con.close();
            
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 1407){
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 20112){
                JOptionPane.showMessageDialog(null, "Bác sĩ này không còn tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 1){
                JOptionPane.showMessageDialog(null, "Số điện thoại này đã tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            return 1;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        return 0;
        
    }
     
    //Xuất thông kê danh sách bác sĩ 
    public static void exportDoctorToPdf() {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String source = "src/Resource/report1_DSBacSi.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(source);

            JasperPrint jp = JasperFillManager.fillReport(jr, new HashMap(), con);

            JasperExportManager.exportReportToPdfFile(jp, "test.pdf" );
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    //Lấy thông tin bac sĩ lên card thông tin
    public  Doctor getDoctorInfo(String username)
    {
        Doctor doctor = new Doctor();
        
         try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT * FROM Doctor WHERE Username = ?";
            PreparedStatement  ps = con.prepareStatement(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                System.out.println("Vào lấy thông tin");
                doctor.setIddoc(rs.getInt("IDDOC"));
                doctor.setUsername(rs.getString("USERNAME"));
                doctor.setName(rs.getString("NAME"));
                doctor.setGender(rs.getInt("Gender"));
                doctor.setPhone(rs.getString("PHONE"));
                doctor.setProvince(rs.getString("PROVINCE"));
                doctor.setAccademicrank(rs.getInt("ACADEMICRANK"));
                doctor.setSubject(rs.getInt("SUBJECT"));
                doctor.setWorkunits(rs.getString("WORKUNITS"));
                return doctor;  
            }
            rs.close();
            //st.close();
            con.close();
            return doctor;
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return doctor;
    }
}
