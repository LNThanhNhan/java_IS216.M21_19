/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import ConnectDB.OracleConnection;
import Model.Advisory;
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
public class AdvisoryPersonController {
    
    private Connection con;
    //Ham lay cac yeu tu van cua da gui cua nguoi dung do 
    public  ArrayList<HashMap> getAdvisory(int idper)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select idad,name,created,yearbirth,height,weight,status,pastmedicalhistory,detail\n"
                    + "from advisory a\n"
                    + "left join doctor d\n"
                    + "on a.iddoc=d.iddoc\n"
                    + "where idper=" + idper;
            ArrayList<HashMap> list= new ArrayList<HashMap>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> ad= new HashMap<String,String>();
                ad.put("idad",rs.getString(1));
                ad.put("name",rs.getString(2));
                ad.put("created",ChangeValue.DateToString(rs.getDate(3)));
                ad.put("yearbirth",Integer.toString(rs.getInt(4)));
                ad.put("height",Integer.toString(rs.getInt(5)));
                ad.put("weight",Integer.toString(rs.getInt(6)));
                ad.put("status", rs.getString(7));
                ad.put("pastmedicalhistory",rs.getString(8));
                ad.put("detail",rs.getString(9));
                list.add(ad);
            }
            rs.close();
            stat.close();
            con.close();
            return list;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<HashMap>();
    }

     //Ham lay cac yeu tu van da mo cua da gui cua nguoi dung do
    public  ArrayList<HashMap> getAdvisoryHaveStatus1(int idper)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select idad,name,created,yearbirth,height,weight,status,pastmedicalhistory,detail\n"
                    + "from advisory a\n"
                    + "left join doctor d\n"
                    + "on a.iddoc=d.iddoc\n"
                    + "where status=1 and\n"
                    + "idper= " + idper;
            ArrayList<HashMap> list= new ArrayList<HashMap>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> ad= new HashMap<String,String>();
                ad.put("idad",rs.getString(1));
                ad.put("name",rs.getString(2));
                ad.put("created",ChangeValue.DateToString(rs.getDate(3)));
                ad.put("yearbirth",Integer.toString(rs.getInt(4)));
                ad.put("height",Integer.toString(rs.getInt(5)));
                ad.put("weight",Integer.toString(rs.getInt(6)));
                ad.put("status", rs.getString(7));
                ad.put("pastmedicalhistory",rs.getString(8));
                ad.put("detail",rs.getString(9));
                list.add(ad);
            }
            rs.close();
            stat.close();
            con.close();
            return list;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<HashMap>();
    }
    
     public static String getNextValueAdvisory() {
        String id = "";
        try
        {
            Connection con = OracleConnection.getOracleConnection();
            String sql1 = "SELECT IDAD.NEXTVAL  a FROM DUAL";
            Statement statement1 = con.createStatement();
            ResultSet rs1 = statement1.executeQuery(sql1);

            if (rs1.next())
            {
                id = rs1.getString("a");
            }

            String sql2 = "ALTER SEQUENCE IDAD INCREMENT BY -1";
            Statement statement2 = con.createStatement();
            ResultSet rs2 = statement2.executeQuery(sql2);

            String sql3 = "SELECT IDAD.NEXTVAL FROM DUAL";
            Statement statement3 = con.createStatement();
            ResultSet rs3 = statement3.executeQuery(sql3);

            String sql4 = "ALTER SEQUENCE IDAD INCREMENT BY 1";
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
     
     public static int AddAdvisory(Advisory Advisory) {
        try
        {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_INSERT_ADVISORY(?,?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);

            ps.setInt(1, Advisory.getIdper());
            ps.setInt(2, Advisory.getYearbirth());
            ps.setInt(3, Advisory.getHeight());
            ps.setInt(4, Advisory.getWeight());
            ps.setString(5, Advisory.getPastmedicalhistory());
            ps.setString(6, Advisory.getDetail());
            //ps.setInt(6,Advisory.getStatus());

            ps.execute();
            ps.close();
            con.close();
        } 
        catch (SQLException sqlex)
        {
            if (sqlex.getErrorCode() == 20003)
            {
                JOptionPane.showMessageDialog(null, "Không thể gửi thêm yêu cầu khi yêu cầu trước chưa được hoàn thành!",
                        "Lỗi!", JOptionPane.WARNING_MESSAGE);
            }
            return 1;
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        return 0;
    }
      public static int DeleteAdvisory(int Idad) {
        try
        {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_DELETE_ADVISORY(?)}";
            CallableStatement ps = con.prepareCall(sql);

            ps.setInt(1, Idad);

            ps.execute();

            ps.close();
            con.close();
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 20182) {
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu không hợp lệ, không thể xóa!",
                        "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            } else if (sqlex.getErrorCode() == 20153) {
                JOptionPane.showMessageDialog(null, "Yêu cầu không tồn tại trong hệ thống!",
                        "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            }
            return 1;
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return 0;
    }
      
       public static int UpdateAdvisory(Advisory Advisory) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            String sql = "{CALL PROC_UPDATE_ADVISORY(?,?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);
                      
            ps.setInt(1, Advisory.getIdad());
            ps.setInt(2, Advisory.getYearbirth());
            ps.setInt(3, Advisory.getHeight());
            ps.setInt(4, Advisory.getWeight());
            ps.setString(5, Advisory.getPastmedicalhistory());
            ps.setString(6,Advisory.getDetail());
            //ps.setInt(6,Supply.getStatus());
            
            ps.execute();
            
            ps.close();
            con.close();
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 20172){
                JOptionPane.showMessageDialog(null, "Trạng thái đơn yêu cầu không hợp lệ",
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
