/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Database.OracleConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import java.text.SimpleDateFormat;
/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
public class AdvisoryController {
    public  ArrayList<HashMap> getAdvisory()
    {
        try{
            Connection con = OracleConnection.getOracleConnection();
            String sql = "select name,gender,phone,province,created,yearbirth,height,weight,pastmedicalhistory,detail\n"
                    + "from advisory a\n"
                    + "join person p\n"
                    + "on a.idper=p.idper";
            ArrayList<HashMap> list= new ArrayList<HashMap>(); 
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> ad= new HashMap<String,String>();
                ad.put("name",rs.getString("name"));
                ad.put("gender",Integer.toString(rs.getInt("gender")));
                ad.put("phone",rs.getString("phone"));
                ad.put("province",rs.getString("province"));
                ad.put("created",this.DateToString(rs.getDate("created")));
                ad.put("yearbirth",Integer.toString(rs.getInt("yearbirth")));
                ad.put("height",Integer.toString(rs.getInt("height")));
                ad.put("weight",Integer.toString(rs.getInt("weight")));
                ad.put("pastmedicalhistory",rs.getString("pastmedicalhistory"));
                ad.put("detail",rs.getString("detail"));
                list.add(ad);
            }
            rs.close();
            st.close();
            con.close();
            return list;
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<HashMap>();
    }
    public  ArrayList<HashMap> getAdvisoryByProvince(String province)
    {
        try{
            Connection con = OracleConnection.getOracleConnection();
            String sql = "SELECT name,gender,phone,province,created,yearbirth,height,weight,pastmedicalhistory,detail\n"
                    + "FROM advisory a\n"
                    + "JOIN person p\n"
                    + "ON a.idper=p.idper\n"
                    + "WHERE province= ?";
            ArrayList<HashMap> list= new ArrayList<HashMap>(); 
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,province);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                HashMap<String,String> ad= new HashMap<String,String>();
                ad.put("name",rs.getString("name"));
                ad.put("gender",Integer.toString(rs.getInt("gender")));
                ad.put("phone",rs.getString("phone"));
                ad.put("province",rs.getString("province"));
                ad.put("created",this.DateToString(rs.getDate("created")));
                ad.put("yearbirth",Integer.toString(rs.getInt("yearbirth")));
                ad.put("height",Integer.toString(rs.getInt("height")));
                ad.put("weight",Integer.toString(rs.getInt("weight")));
                ad.put("pastmedicalhistory",rs.getString("pastmedicalhistory"));
                ad.put("detail",rs.getString("detail"));
                list.add(ad);
            }
            rs.close();
            pt.close();
            con.close();
            return list;
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<HashMap>();
    }
    private String DateToString(Date date)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
        return sdf.format(date);
    }
}
