/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import ConnectDB.OracleConnection;
import Model.AdvisoryTableModel;
import View.ChangeValue;
import View.DoctorScreen;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
public class AdvisoryController {
    private Connection con;
    
    //Ham lay tat ca yeu tu van
    public  ArrayList<HashMap> getAdvisory()
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select idad, name,gender,phone,province,created,yearbirth,height,weight,pastmedicalhistory,detail\n"
                    + "from advisory a\n"
                    + "join person p\n"
                    + "on a.idper=p.idper\n"
                    + "where a.status=1 ";
            ArrayList<HashMap> list= new ArrayList<HashMap>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> ad= new HashMap<String,String>();
                ad.put("idad",rs.getString(1));
                ad.put("name",rs.getString(2));
                ad.put("gender",Integer.toString(rs.getInt(3)));
                ad.put("phone",rs.getString(4));
                ad.put("province",rs.getString(5));
                ad.put("created",ChangeValue.DateToString(rs.getDate(6)));
                ad.put("yearbirth",Integer.toString(rs.getInt(7)));
                ad.put("height",Integer.toString(rs.getInt(8)));
                ad.put("weight",Integer.toString(rs.getInt(9)));
                ad.put("pastmedicalhistory",rs.getString(10));
                ad.put("detail",rs.getString(11));
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
    
    //Phần này là demo PHANTOM READ
    public  void getAdvisoryTransaction(JTable AdvisoryTable,DefaultTableModel AdvisoryTableModel,ArrayList<HashMap> AdvisoryList)
    {
        try {
            Connection con = OracleConnection.getOracleConnection();
            int i = -1;
            i = Connection.TRANSACTION_READ_COMMITTED;
//            i = Connection.TRANSACTION_SERIALIZABLE;
            con.setAutoCommit(false);
            con.setTransactionIsolation(i);
            AdvisoryTable.getSelectionModel().clearSelection();
            AdvisoryTableModel=getAdvisoryModelTransaction(con, AdvisoryList);
            AdvisoryTable.setModel(AdvisoryTableModel);
            setAdvisoryTableSize(AdvisoryTable);
            //Thông báo đã xong lần 1
            System.out.println("1");
            //Hàm 1 thread mới và thực hiện 2 giao tác trong 
            //Phần này là xử lý trường hợp phantom read
            new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
//                                SwingUtilities.invokeLater(new Runnable() {
//                                    @Override
//                                    public void run()  {
                    try {
                        //Ngu 10 giay
                        Thread.sleep(30000);
                        //Bat dau thuc hien lan 2
                        System.out.println("2");
                        AdvisoryTable.getSelectionModel().clearSelection();
                        AdvisoryTable.setModel(getAdvisoryModelTransaction(con, AdvisoryList));
                        setAdvisoryTableSize(AdvisoryTable);
                        con.commit();
                    } catch (SQLException ex) {
                        Logger.getLogger(DoctorScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DoctorScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(DoctorScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                                    }
//                                });
                    return null;
                }
            }.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DoctorScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DoctorScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DoctorScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Hàm này dùng để set dữ liệu cho model bảng trong khi thực hiện transaction
    public static DefaultTableModel getAdvisoryModelTransaction (Connection con,ArrayList<HashMap> list) 
    throws SQLException,UnsupportedOperationException,Exception
    {
            String sql = "select idad, name,gender,phone,province,created,yearbirth,height,weight,pastmedicalhistory,detail\n"
                    + "from advisory a\n"
                    + "join person p\n"
                    + "on a.idper=p.idper\n"
                    + "where a.status=1 ";
            list.clear();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> ad= new HashMap<String,String>();
                ad.put("idad",rs.getString(1));
                ad.put("name",rs.getString(2));
                ad.put("gender",Integer.toString(rs.getInt(3)));
                ad.put("phone",rs.getString(4));
                ad.put("province",rs.getString(5));
                ad.put("created",ChangeValue.DateToString(rs.getDate(6)));
                ad.put("yearbirth",Integer.toString(rs.getInt(7)));
                ad.put("height",Integer.toString(rs.getInt(8)));
                ad.put("weight",Integer.toString(rs.getInt(9)));
                ad.put("pastmedicalhistory",rs.getString(10));
                ad.put("detail",rs.getString(11));
                list.add(ad);
            }
            DefaultTableModel AdvisoryTableModel= new AdvisoryTableModel().setAdvisoryTable(list);
            return AdvisoryTableModel;
    }
    
    //Hàm lấy tất cả yêu cầu tư vấn theo tỉnh/thành phố đó
    public ArrayList<HashMap> getAdvisoryByProvince(String province)
    {
        try{
            con =OracleConnection.getOracleConnection();
            String sql = "select idad, name,gender,phone,province,created,yearbirth,height,weight,pastmedicalhistory,detail\n"
                    + "from advisory a\n"
                    + "join person p\n"
                    + "on a.idper=p.idper\n"
                    + "where a.status=1 and \n"
                    + "province= '"+province+"'";
            ArrayList<HashMap> list= new ArrayList<>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> ad= new HashMap<>();
                ad.put("idad",rs.getString(1));
                ad.put("name",rs.getString(2));
                ad.put("gender",Integer.toString(rs.getInt(3)));
                ad.put("phone",rs.getString(4));
                ad.put("province",rs.getString(5));
                ad.put("created",ChangeValue.DateToString(rs.getDate(6)));
                ad.put("yearbirth",Integer.toString(rs.getInt(7)));
                ad.put("height",Integer.toString(rs.getInt(8)));
                ad.put("weight",Integer.toString(rs.getInt(9)));
                ad.put("pastmedicalhistory",rs.getString(10));
                ad.put("detail",rs.getString(11));
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
        return new ArrayList<>();
    }
    
    //Hàm này dùng để lấy tất cả tỉnh thành phố mà có người muốn tư vấn
    public ArrayList<String> getProvinceFromAd()
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select distinct province\n"
                    + "from advisory a\n"
                    + "join person p\n"
                    + "on a.idper=p.idper\n"
                    + "where a.status=1 \n";
            ArrayList<String> list= new ArrayList<>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            rs.close();
            stat.close();
            con.close();
            return list;
        }catch (SQLException ex) {
            ex.printStackTrace();
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<String>();
    }
    
    //Hàm gọi thủ tục chấp nhận yêu cầu tư vấn
    public int AcceptAdvisory(String iddoc,String idad)
    {
        try{
            con = OracleConnection.getOracleConnection();
            con.setAutoCommit(false);
            String CallProc = "{call PROC_ACCEPT_ADVISORY(?,?)}";
            CallableStatement callSt=con.prepareCall(CallProc);;
            callSt.setString(1,iddoc);
            callSt.setString(2,idad);
            callSt.execute();
            callSt.close();
            con.commit();
            con.close();
            return 1;
        }catch (SQLException sqle) {
            if (sqle.getErrorCode() == 20241)
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu này không hợp lệ để nhận, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqle.getErrorCode() == 20242)
                JOptionPane.showMessageDialog(null, "Yêu cầu này không còn tồn tại, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            sqle.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    //DEMO DEADLOCK
    public int AcceptDLAdvisory(String iddoc,String idad1,String idad2)
    {
        try{
            con = OracleConnection.getOracleConnection();
            con.setAutoCommit(false);
            String CallProc1 = "{call PROC_ACCEPT_ADVISORY(?,?)}";
            CallableStatement callSt1=con.prepareCall(CallProc1);;
            callSt1.setString(1,iddoc);
            callSt1.setString(2,idad1);
            callSt1.execute();
            System.err.println("2");
            Thread.sleep(5000);
            
            String CallProc2 = "{call PROC_ACCEPT_ADVISORY(?,?)}";
            CallableStatement callSt2=con.prepareCall(CallProc2);;
            callSt2.setString(1,iddoc);
            callSt2.setString(2,idad2);
            //try{
                callSt2.execute();
            //}
//            catch (SQLException sqle) {
//                if (sqle.getErrorCode() == 60) {
//                    JOptionPane.showMessageDialog(null, "Đã có Deadlock xảy ra!",
//                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
//                    con.rollback();
//                    sqle.printStackTrace();
//                }
//            }
            callSt1.close();
            callSt2.close();
            con.commit();
            con.close();
            return 1;
        }catch (SQLException sqle) {
            if (sqle.getErrorCode() == 20241)
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu này không hợp lệ để nhận, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            if (sqle.getErrorCode() == 20242)
                JOptionPane.showMessageDialog(null, "Yêu cầu này không còn tồn tại, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            sqle.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    //Phần này là chỉnh sửa cột và và header cho bảng vì không thể lấy
    // hàm từ bên màn hình bác sĩ qua được
    private void setAdvisoryTableSize(JTable AdvisoryTable)
    {
        //column
        AdvisoryTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        AdvisoryTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        AdvisoryTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        AdvisoryTable.getColumnModel().getColumn(3).setPreferredWidth(40);
        AdvisoryTable.getColumnModel().getColumn(4).setPreferredWidth(50);
        AdvisoryTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        //header
        TableCellRenderer rendererFromHeader = AdvisoryTable.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JTableHeader header=AdvisoryTable.getTableHeader();
        Font headerFont = new Font("Segoe", Font.PLAIN, 14);
        header.setFont(headerFont);
    }
    
    //=======================================================================
    //Hàm cho màn hình tư vấn bệnh nhân
    //Hàm lấy tất cả yêu cầu tư vấn đã nhận cua 1 bác sĩ
    public  ArrayList<HashMap> getWaitAdvisory(String iddoc)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select idad, name,gender,phone,province,created,yearbirth,height,weight,pastmedicalhistory,detail\n"
                    + "from advisory a\n"
                    + "join person p\n"
                    + "on a.idper=p.idper\n"
                    + "where a.status=2 and \n"
                    + "iddoc= "+iddoc;
            ArrayList<HashMap> list= new ArrayList<HashMap>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> ad= new HashMap<String,String>();
                ad.put("idad",rs.getString(1));
                ad.put("name",rs.getString(2));
                ad.put("gender",Integer.toString(rs.getInt(3)));
                ad.put("phone",rs.getString(4));
                ad.put("province",rs.getString(5));
                ad.put("created",ChangeValue.DateToString(rs.getDate(6)));
                ad.put("yearbirth",Integer.toString(rs.getInt(7)));
                ad.put("height",Integer.toString(rs.getInt(8)));
                ad.put("weight",Integer.toString(rs.getInt(9)));
                ad.put("pastmedicalhistory",rs.getString(10));
                ad.put("detail",rs.getString(11));
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
    
    //Hàm lấy tất cả yêu cầu tư vấn đang chờ theo tỉnh/thành phố đó
    public ArrayList<HashMap> getWaitAdvisoryByProvince(String iddoc,String province)
    {
        try{
            con =OracleConnection.getOracleConnection();
            String sql = "select idad, name,gender,phone,province,created,yearbirth,height,weight,pastmedicalhistory,detail\n"
                    + "from advisory a\n"
                    + "join person p\n"
                    + "on a.idper=p.idper\n"
                    + "where a.status=2 and \n"
                    + "province= '"+province+"' and\n"
                    + "iddoc= "+iddoc;
            ArrayList<HashMap> list= new ArrayList<>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> ad= new HashMap<>();
                ad.put("idad",rs.getString(1));
                ad.put("name",rs.getString(2));
                ad.put("gender",Integer.toString(rs.getInt(3)));
                ad.put("phone",rs.getString(4));
                ad.put("province",rs.getString(5));
                ad.put("created",ChangeValue.DateToString(rs.getDate(6)));
                ad.put("yearbirth",Integer.toString(rs.getInt(7)));
                ad.put("height",Integer.toString(rs.getInt(8)));
                ad.put("weight",Integer.toString(rs.getInt(9)));
                ad.put("pastmedicalhistory",rs.getString(10));
                ad.put("detail",rs.getString(11));
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
        return new ArrayList<>();
    }
    
    //Hàm này dùng để lấy tất cả tỉnh thành phố mà của người muốn tư vấn
    public ArrayList<String> getProvinceFromWaitAd(String iddoc)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select distinct province\n"
                    + "from advisory a\n"
                    + "join person p\n"
                    + "on a.idper=p.idper\n"
                    + "where a.status=2 and \n"
                    + "iddoc= "+iddoc;
            ArrayList<String> list= new ArrayList<>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            rs.close();
            stat.close();
            con.close();
            return list;
        }catch (SQLException ex) {
            ex.printStackTrace();
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<String>();
    }
    
    //Hàm gọi thủ tục chấp nhận yêu cầu tư vấn
    public int FinishAdvisory(int idad)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String CallProc = "{call PROC_FINISH_ADVISORY(?)}";
            CallableStatement callSt=con.prepareCall(CallProc);;
            callSt.setInt(1, idad);
            callSt.execute();
            callSt.close();
            con.close();
            return 1;
        }catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }catch (SQLException sqle) {
            if (sqle.getErrorCode() == 20241)
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu này không hợp lệ để nhận, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqle.getErrorCode() == 20242)
                JOptionPane.showMessageDialog(null, "Yêu cầu này không còn tồn tại, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            sqle.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
