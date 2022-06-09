/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.OracleConnection;
import Model.Account;
import Model.Employee;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;


/**
 *
 * @author MyPC
 */
public class EmployeeController {
    public  ArrayList<Employee> getEmployee()
    {
        ArrayList<Employee> ListEmployee = new <Employee>ArrayList();
        try{
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT * FROM Employee";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Employee Employee = new Employee();
                
                Employee.setIdemp(rs.getInt("IDEM"));
                Employee.setUsername(rs.getString("USERNAME"));
                Employee.setName(rs.getString("NAME"));
                Employee.setGender(rs.getInt("Gender"));
                Employee.setPhone(rs.getString("PHONE"));
                Employee.setAddress(rs.getString("ADDRESS"));
                Employee.setStartdate(rs.getDate("STARTDATE"));   
                ListEmployee.add(Employee);  
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
        return ListEmployee;
    }
    
    public static int AddEmployee( Employee Employee, Account account) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_INSERT_EMPLOYEE(?,?,?,?,TO_DATE(?,'dd/MM/yyyy'),?,?)}";
            CallableStatement ps = con.prepareCall(sql);
            
            
            ps.setString(1, Employee.getUsername());
            ps.setString(2, account.getPassword());
            ps.setString(3, Employee.getName());
            ps.setInt(4, Employee.getGender());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String startDate = sdf.format(Employee.getStartdate());
            ps.setString(5, startDate);
            ps.setString(6, Employee.getPhone());           
            ps.setString(7, Employee.getAddress());
            
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
            return 1;

        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        return 0;
        
    }  
     
    public static String getNextValueEmployee() {
        String id = "";
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql1 = "SELECT IDEMP.NEXTVAL  s FROM DUAL";
            Statement statement1 = con.createStatement();
            ResultSet rs1 = statement1.executeQuery(sql1);
                       
            if (rs1.next()) {
                id = rs1.getString("s");
            }
            
            String sql2 = "ALTER SEQUENCE IDEMP INCREMENT BY -1";
            Statement statement2 = con.createStatement();
            ResultSet rs2 = statement2.executeQuery(sql2);
            
            String sql3 = "SELECT IDEMP.NEXTVAL FROM DUAL";
            Statement statement3 = con.createStatement();
            ResultSet rs3 = statement3.executeQuery(sql3);
            
            String sql4 = "ALTER SEQUENCE IDEMP INCREMENT BY 1";
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
    
     public static int DeleteEmployee (int idemp) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_DELETE_EMPLOYEE(?)}";
            CallableStatement ps = con.prepareCall(sql);           
            
            ps.setInt(1, idemp);
            
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
       
            ps.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;    
    }
     
     public static int UpdateEmployee(Employee Employee) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_UPDATE_EMPLOYEE(?,?,?,TO_DATE(?,'dd/MM/yyyy'),?,?)}";
            CallableStatement ps = con.prepareCall(sql);
                     
            ps.setInt(1, Employee.getIdemp());
            ps.setString(2, Employee.getName());
            ps.setInt(3, Employee.getGender());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String startDate = sdf.format(Employee.getStartdate());
            ps.setString(4, startDate);
            ps.setString(5, Employee.getPhone());           
            ps.setString(6, Employee.getAddress());   
            
            ps.execute();
       
            ps.close();
            con.close();
        }catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 1407){
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 20112){
                JOptionPane.showMessageDialog(null, "Nhân viên này không còn tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 1){
                JOptionPane.showMessageDialog(null, "Số điện thoại này đã tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        return 0;
        
    }
     
     
    public  Employee getEmployeeInfo(String username)
    {
        Employee emp = new Employee();
        
         try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT * FROM Employee WHERE Username = ?";
            PreparedStatement  ps = con.prepareStatement(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                emp.setUsername(rs.getString("USERNAME"));
                emp.setName(rs.getString("NAME"));
                emp.setGender(rs.getInt("Gender"));
                emp.setPhone(rs.getString("PHONE"));
                emp.setAddress(rs.getString("ADDRESS"));
                emp.setStartdate(rs.getDate("STARTDATE"));
                return emp;  
            }
            rs.close();
            //st.close();
            con.close();
            return emp;
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return emp;
    }

    public static String getUsernameEmp(int idper){
        String usernameemp="" ;
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT username FROM employee WHERE idem = ?";
            PreparedStatement  ps = con.prepareStatement(sql);
            ps.setInt(1, idper);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                usernameemp=rs.getString("username");
                return usernameemp;
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
        return usernameemp;
    }
}
