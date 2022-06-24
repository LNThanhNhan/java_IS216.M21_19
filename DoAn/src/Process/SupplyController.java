/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import ConnectDB.OracleConnection;
import Model.Account;
import Model.Supply;
import Model.SupplyTableModel;
import View.ChangeValue;
import View.PersonScreen;
import java.awt.Component;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
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
        private Connection con;
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
    
//    public static int AddSupply( Supply Supply) {
//        try {
//            Connection con = OracleConnection.getOracleConnection();
//            String sql = "{CALL PROC_INSERT_SUPPLY(?,?,?,?,?)}";
//            CallableStatement ps = con.prepareCall(sql);
//                       
//            ps.setInt(1, Supply.getIdper());
//            ps.setInt(2, Supply.getNeedfood());
//            ps.setInt(3, Supply.getNeednecess());
//            ps.setInt(4, Supply.getNeedequip());
//            ps.setString(5,Supply.getDetail()); 
//            //ps.setInt(6,Supply.getStatus());
//            
//            ps.execute();
//            ps.close();
//            con.close();
//            
//        } catch (SQLException sqlex) {
//            if (sqlex.getErrorCode() == 1400 ){
//                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
//                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
//            else if (sqlex.getErrorCode() == 1){
//                JOptionPane.showMessageDialog(null, "Số điện thoại này đã tồn tại",
//                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
//            else if (sqlex.getErrorCode() == 2291){
//                JOptionPane.showMessageDialog(null, "Người dùng không tồn tại!",
//                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
//            
//            return 1;
//        } 
//        catch (Exception ex) {
//            ex.printStackTrace();  
//            return 1;
//        }
//        return 0;
//    }  
    
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
     
//     public static int UpdateSupply(Supply Supply) {
//        try {
//            Connection con = OracleConnection.getOracleConnection();
//            String sql = "{CALL PROC_UPDATE_SUPPLY(?,?,?,?,?)}";
//            CallableStatement ps = con.prepareCall(sql);
//                      
//            ps.setInt(1, Supply.getIdsup());
//            ps.setInt(2, Supply.getNeedfood());
//            ps.setInt(3, Supply.getNeednecess());
//            ps.setInt(4, Supply.getNeedequip());
//            ps.setString(5,Supply.getDetail());
//            
//            ps.execute();
//            
//            ps.close();
//            con.close();
//        } catch (SQLException sqlex) {
//            if (sqlex.getErrorCode() == 1407){
//                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
//                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
//            else if (sqlex.getErrorCode() == 20081){
//                JOptionPane.showMessageDialog(null, "Yêu cầu tiếp tế này không còn tồn tại",
//                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
//   
//            
//            return 1;
//        }
//        
//        catch (Exception ex) {
//            ex.printStackTrace();
//            return 1;
//        }
//        return 0;       
//    }
   
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
            if (sqlex.getErrorCode() == 20192){
                JOptionPane.showMessageDialog(null, "Trạng thái không hợp lệ ! Không thể xác thực yêu cầu",
                        "cảnh báo", JOptionPane.WARNING_MESSAGE);}
            else if (sqlex.getErrorCode() == 1){
                JOptionPane.showMessageDialog(null, "Yêu cầu này không còn tồn tại trong hệ thống",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            sqlex.printStackTrace();
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
            String sql = "{CALL PROC_DELETE_OVERDUE()}";
            CallableStatement ps = con.prepareCall(sql);
            
            ps.execute();
            ps.close();
            con.close();
        
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 6550)
                JOptionPane.showMessageDialog(null, "Không có yêu cầu quá hạn nào!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
            sqlex.printStackTrace();
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
                        "Lỗi!", JOptionPane.WARNING_MESSAGE);
            }
            else if (sqlex.getErrorCode() == 2290)
            {
                JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!",
                        "Lỗi!", JOptionPane.WARNING_MESSAGE);
            }else if (sqlex.getErrorCode() == 20500)
            {
                JOptionPane.showMessageDialog(null, "Yêu cầu về lương thực chưa được hoàn thành",
                        "Lỗi!", JOptionPane.WARNING_MESSAGE);
            } else if (sqlex.getErrorCode() == 20501)
            {
                JOptionPane.showMessageDialog(null, "Yêu cầu về nhu yếu phẩm chưa được hoàn thành",
                        "Lỗi!", JOptionPane.WARNING_MESSAGE);
            } else if (sqlex.getErrorCode() == 20502)
            {
                JOptionPane.showMessageDialog(null, "Yêu cầu về vật dụng chưa được hoàn thành",
                        "Lỗi!", JOptionPane.WARNING_MESSAGE);
            } else if (sqlex.getErrorCode() == 20010 )
            {
                JOptionPane.showMessageDialog(null, "Người dùng không tồn tại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            } else if (sqlex.getErrorCode() == 1403 ){
                JOptionPane.showMessageDialog(null, "Người dùng không tồn tại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            }
                
            sqlex.printStackTrace();
            return 1;
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }

//    public static int DeleteSupply(int Idsup) {
//        try
//        {
//            Connection con = OracleConnection.getOracleConnection();
//            String sql = "{CALL PROC_DELETE_SUPPLY(?)}";
//            CallableStatement ps = con.prepareCall(sql);
//
//            ps.setInt(1, Idsup);
//
//            ps.execute();
//
//            ps.close();
//            con.close();
//        } catch (SQLException sqlex) {
//            if (sqlex.getErrorCode() == 20152) {
//                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu không hợp lệ, không thể xóa!",
//                        "Cảnh báo!", JOptionPane.ERROR_MESSAGE);
//            }else if (sqlex.getErrorCode() == 20153) {
//                JOptionPane.showMessageDialog(null, "Vui lòng chọn một yêu cầu trước khi xóa!",
//                        "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
//            } 
//            return 1;
//        } catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//
//        return 0;
//    }


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
            if (sqlex.getErrorCode() == 20142){
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu không hợp lệ!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
//            else if (sqlex.getErrorCode() == 1407){
//                JOptionPane.showMessageDialog(null, "Vui lòng chọn tối thiểu một danh mục cần tiếp tế!",
//                        "Lỗi!", JOptionPane.WARNING_MESSAGE);}
            else if (sqlex.getErrorCode() == 20081){
                JOptionPane.showMessageDialog(null, "Yêu cầu tiếp tế này không còn tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 2290){
                JOptionPane.showMessageDialog(null, "Vui lòng chọn tối thiểu một danh mục cần tiếp tế!",
                        "Lỗi!", JOptionPane.WARNING_MESSAGE);}
            return 1;
        }
        
        catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
        return 0;       
    }
    
    ////Controller cho charity///////////////////////
    
    
    //Ham lay tat ca yeu tiep te
    public  ArrayList<HashMap> getSupplyCharity(int x, int y, int z)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select idsup,name,gender,phone,province,district,town,address,created,needfood,neednecess,needequip,detail\n"
                    + "from supply s\n"
                    + "join person p\n"
                    + "on s.idper=p.idper\n"
                    + "where s.status=2 "
                    + "and needfood <="+x
                    + "and neednecess <="+y
                    + "and needequip <="+z;
            ArrayList<HashMap> list= new ArrayList<HashMap>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> sup= new HashMap<String,String>();
                sup.put("idsup",rs.getString(1));
                sup.put("name",rs.getString(2));
                sup.put("gender",Integer.toString(rs.getInt(3)));
                sup.put("phone",rs.getString(4));
                sup.put("province",rs.getString(5));
                sup.put("district",rs.getString(6));
                sup.put("town",rs.getString(7));
                sup.put("address",rs.getString(8));
                sup.put("created",ChangeValue.DateToString(rs.getDate(9)));
                sup.put("needfood",Integer.toString(rs.getInt(10)));
                sup.put("neednecess",Integer.toString(rs.getInt(11)));
                sup.put("needequip",Integer.toString(rs.getInt(12)));
                sup.put("detail",rs.getString(13));
                list.add(sup);
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
//    public  void getSupplyTransaction(JTable SupplyTable,DefaultTableModel SupplyTableModel,ArrayList<HashMap> SupplyList)
//    {
//        try {
//            Connection con = OracleConnection.getOracleConnection();
//            int i = -1;
//            i = Connection.TRANSACTION_READ_COMMITTED;
////            i = Connection.TRANSACTION_SERIALIZABLE;
//            con.setAutoCommit(false);
//            con.setTransactionIsolation(i);
//            SupplyTable.getSelectionModel().clearSelection();
//            SupplyTableModel=getSupplyModelTransaction(con, SupplyList);
//            SupplyTable.setModel(SupplyTableModel);
//            setSupplyTableSize(SupplyTable);
//            //Thông báo đã xong lần 1
//            System.out.println("1");
//            //Hàm 1 thread mới và thực hiện 2 giao tác trong 
//            //Phần này là xử lý trường hợp phantom read
//            new SwingWorker() {
//                @Override
//                protected Object doInBackground() throws Exception {
////                                SwingUtilities.invokeLater(new Runnable() {
////                                    @Override
////                                    public void run()  {
//                    try {
//                        //Ngu 10 giay
//                        Thread.sleep(10000);
//                        //Bat dau thuc hien lan 2
//                        System.out.println("2");
//                        SupplyTable.getSelectionModel().clearSelection();
//                        SupplyTable.setModel(getSupplyModelTransaction(con, SupplyList));
//                        setSupplyTableSize(SupplyTable);
//                        con.commit();
//                    } catch (SQLException ex) {
//                        Logger.getLogger(CharityScreen.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(CharityScreen.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (Exception ex) {
//                        Logger.getLogger(CharityScreen.class.getName()).log(Level.SEVERE, null, ex);
//                    }
////                                    }
////                                });
//                    return null;
//                }
//            }.execute();
//        } catch (SQLException ex) {
//            Logger.getLogger(CharityScreen.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(CharityScreen.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(CharityScreen.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    //Hàm này dùng để set dữ liệu cho model bảng trong khi thực hiện transaction
//    public static DefaultTableModel getSupplyModelTransaction (Connection con,ArrayList<HashMap> list) 
//    throws SQLException,UnsupportedOperationException,Exception
//    {
//            String sql = "select idsup,name,gender,phone,province,created,needfood,neednecess,needequip,detail\n"
//                    + "from supply s\n"
//                    + "join person p\n"
//                    + "on s.idper=p.idper\n"
//                    + "where s.status=2 ";
//            list.clear();
//            Statement stat = con.createStatement();
//            ResultSet rs = stat.executeQuery(sql);
//            while (rs.next()) {
//                HashMap<String,String> sup= new HashMap<String,String>();
//                sup.put("idsup",rs.getString(1));
//                sup.put("name",rs.getString(2));
//                sup.put("gender",Integer.toString(rs.getInt(3)));
//                sup.put("phone",rs.getString(4));
//                sup.put("province",rs.getString(5));
//                sup.put("created",ChangeValue.DateToString(rs.getDate(6)));
//                sup.put("needfood",Integer.toString(rs.getInt(7)));
//                sup.put("neednecess",Integer.toString(rs.getInt(8)));
//                sup.put("needequip",Integer.toString(rs.getInt(9)));
//                sup.put("detail",rs.getString(10));
//                list.add(sup);
//            }
//            DefaultTableModel SupplyTableModel= new SupplyTableModel().setSupplyTableCharity(list);
//            return SupplyTableModel;
//    }
//    
    //Hàm lấy tất cả yêu cầu tư vấn theo tỉnh/thành phố đó
    public ArrayList<HashMap> getSupplyByProvince(String province,int x, int y, int z)
    {
        try{
            con =OracleConnection.getOracleConnection();
            String sql = "select idsup,name,gender,phone,province,district,town,address,created,needfood,neednecess,needequip,detail\n"
                    + "from supply s\n"
                    + "join person p\n"
                    + "on s.idper=p.idper\n"
                    + "where s.status=2 and \n"
                    + "province= '"+province+"'"
                    + "and needfood <="+x
                    + "and neednecess <="+y
                    + "and needequip <="+z;
            ArrayList<HashMap> list= new ArrayList<>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> sup= new HashMap<>();
                sup.put("idsup",rs.getString(1));
                sup.put("name",rs.getString(2));
                sup.put("gender",Integer.toString(rs.getInt(3)));
                sup.put("phone",rs.getString(4));
                sup.put("province",rs.getString(5));
                sup.put("district",rs.getString(6));
                sup.put("town",rs.getString(7));
                sup.put("address",rs.getString(8));
                sup.put("created",ChangeValue.DateToString(rs.getDate(9)));
                sup.put("needfood",Integer.toString(rs.getInt(10)));
                sup.put("neednecess",Integer.toString(rs.getInt(11)));
                sup.put("needequip",Integer.toString(rs.getInt(12)));
                sup.put("detail",rs.getString(13));
                list.add(sup);
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
    
    //Hàm này dùng để lấy tất cả tỉnh thành phố mà có người muốn tiếp tế
    public ArrayList<String> getProvinceFromSup(int x, int y, int z)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select distinct province\n"
                    + "from supply s\n"
                    + "join person p\n"
                    + "on s.idper=p.idper\n"
                    + "where s.status=2 \n"
                    + "and needfood <="+x
                    + "and neednecess <="+y
                    + "and needequip <="+z;;
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
    public int AcceptSupply(String idchar,String idsup)
    {
        System.out.println(idchar);
        System.out.println(idsup);
        try{
            con = OracleConnection.getOracleConnection();
            String CallProc = "{call PROC_ACCEPT_SUPPLY(?,?)}";
            CallableStatement callSt=con.prepareCall(CallProc);
            callSt.setString(1,idchar);
            callSt.setString(2,idsup);
            callSt.execute();
            callSt.close();
            con.close();
            return 1;
        }catch (SQLException sqle) {
            if (sqle.getErrorCode() == 20212)
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu này không hợp lệ để nhận, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE); 
            else if (sqle.getErrorCode() == 20213)
                JOptionPane.showMessageDialog(null, "Không đủ điều kiện chấp nhận yêu cầu này!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqle.getErrorCode() == 20214)
                JOptionPane.showMessageDialog(null, "Yêu cầu này không còn tồn tại, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            //sqle.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    //Phần này là chỉnh sửa cột và và header cho bảng vì không thể lấy
    // hàm từ bên màn hình trung tâm qua được
    private void setSupplyTableSize(JTable SupplyTable)
    {
        //column
        SupplyTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        SupplyTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        SupplyTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        SupplyTable.getColumnModel().getColumn(3).setPreferredWidth(40);
       
        //header
        TableCellRenderer rendererFromHeader = SupplyTable.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JTableHeader header=SupplyTable.getTableHeader();
        Font headerFont = new Font("Segoe", Font.PLAIN, 14);
        header.setFont(headerFont);
    }
    
    //=======================================================================
    //Hàm cho màn hình tiep te
    //Hàm lấy tất cả yêu cầu tư vấn đã nhận cua 1 trung tâm
    public  ArrayList<HashMap> getWaitSupply(String idchar)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select idsup,name,gender,phone,province,district,town,address,created,needfood,neednecess,needequip,detail\n"
                    + "from supply s\n"
                    + "join person p\n"
                    + "on s.idper=p.idper\n"
                    + "where s.status=3 and \n"
                    + "idchar= "+idchar;
            ArrayList<HashMap> list= new ArrayList<HashMap>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> sup= new HashMap<String,String>();
                sup.put("idsup",rs.getString(1));
                sup.put("name",rs.getString(2));
                sup.put("gender",Integer.toString(rs.getInt(3)));
                sup.put("phone",rs.getString(4));
                sup.put("province",rs.getString(5));
                sup.put("district",rs.getString(6));
                sup.put("town",rs.getString(7));
                sup.put("address",rs.getString(8));
                sup.put("created",ChangeValue.DateToString(rs.getDate(9)));
                sup.put("needfood",Integer.toString(rs.getInt(10)));
                sup.put("neednecess",Integer.toString(rs.getInt(11)));
                sup.put("needequip",Integer.toString(rs.getInt(12)));
                sup.put("detail",rs.getString(13));
                list.add(sup);
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
    public ArrayList<HashMap> getWaitSupplyByProvince(String idchar,String province)
    {
        try{
            con =OracleConnection.getOracleConnection();
            String sql = "select idsup,name,gender,phone,province,district,town,address,created,needfood,neednecess,needequip,detail\n"
                    + "from supply s\n"
                    + "join person p\n"
                    + "on s.idper=p.idper\n"
                    + "where s.status=3 and \n"
                    + "province= '"+province+"' and\n"
                    + "idchar= "+idchar;
            ArrayList<HashMap> list= new ArrayList<>(); 
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                HashMap<String,String> sup= new HashMap<>();
                sup.put("idsup",rs.getString(1));
                sup.put("name",rs.getString(2));
                sup.put("gender",Integer.toString(rs.getInt(3)));
                sup.put("phone",rs.getString(4));
                sup.put("province",rs.getString(5));
                sup.put("district",rs.getString(6));
                sup.put("town",rs.getString(7));
                sup.put("address",rs.getString(8));
                sup.put("created",ChangeValue.DateToString(rs.getDate(9)));
                sup.put("needfood",Integer.toString(rs.getInt(10)));
                sup.put("neednecess",Integer.toString(rs.getInt(11)));
                sup.put("needequip",Integer.toString(rs.getInt(12)));
                sup.put("detail",rs.getString(13));
                list.add(sup);
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
    public ArrayList<String> getProvinceFromWaitSup(String idchar)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String sql = "select distinct province\n"
                    + "from supply s\n"
                    + "join person p\n"
                    + "on s.idper=p.idper\n"
                    + "where s.status=3 and \n"
                    + "idchar= "+idchar;
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
    
    //Hàm gọi thủ tục chấp nhận yêu cầu tiếp tế
    public int FinishSupply(int idsup)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String CallProc = "{call PROC_FINISH_SUPPLY(?)}";
            CallableStatement callSt=con.prepareCall(CallProc);;
            callSt.setInt(1, idsup);
            callSt.execute();
            callSt.close();
            con.close();
            return 1;
        }catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }catch (SQLException sqle) {
            if (sqle.getErrorCode() == 20232)
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu này không hợp lệ để nhận, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqle.getErrorCode() == 20232)
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
    
    
    //Hàm gọi thủ tục hủy yêu cầu tiếp tế
    public int CancelSupply(String idchar,String idsup)
    {
        try{
            con = OracleConnection.getOracleConnection();
            String CallProc = "{call PROC_CANCEL_SUPPLY(?,?)}";
            CallableStatement callSt=con.prepareCall(CallProc);
            callSt.setString(1,idchar);
            callSt.setString(2,idsup);
            callSt.execute();
            callSt.close();
            con.close();
            return 1;
        }catch (SQLException sqle) {
            if (sqle.getErrorCode() == 20222)
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu này không hợp lệ để nhận, vui lòng tải lại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
            else if (sqle.getErrorCode() == 20212)
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
     
     //Phần này là demo non-repeatable reads
    public void getSupplyTransaction(JTable SupplyTable, DefaultTableModel SupplyTableModel, ArrayList<HashMap> SupplyList, int idper) {
        try {
            Connection con = OracleConnection.getOracleConnection();
            int i = -1;
            //i = Connection.TRANSACTION_READ_COMMITTED;
            i = Connection.TRANSACTION_READ_COMMITTED;
            con.setAutoCommit(false);
            con.setTransactionIsolation(i);
            SupplyTable.getSelectionModel().clearSelection();
            SupplyTableModel = getSupplyModelTransaction(con, SupplyList, idper);

            SupplyTable.setModel(SupplyTableModel);
            resizeColumnWidth(SupplyTable);
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

                        //Ngu 60 giay
                        Thread.sleep(60000);
                        //Bat dau thuc hien lan 2
                        System.out.println("2");
                        SupplyTable.getSelectionModel().clearSelection();
                        SupplyTable.setModel(getSupplyModelTransaction(con, SupplyList, idper));
                        resizeColumnWidth(SupplyTable);
                        con.commit();
                    } catch (SQLException ex) {
                        Logger.getLogger(PersonScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PersonScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(PersonScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                                    }
//                                });
                    return null;
                }
            }.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PersonScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersonScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersonScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Hàm này dùng để set dữ liệu cho model bảng trong khi thực hiện transaction
    public static DefaultTableModel getSupplyModelTransaction(Connection con, ArrayList<HashMap> list, int idper)
            throws SQLException, UnsupportedOperationException, Exception {
        String sql = "select idsup,name,created,needfood,neednecess,needequip,status,detail\n"
                + "from supply s\n"
                + "left join charity p\n"
                + "on s.idchar=p.idchar\n"
                + "where idper=" + idper;
        list.clear();
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()) {
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
        DefaultTableModel SupplyTableModel = new SupplyTableModel().setSupplyTableByPerson(list);
        return SupplyTableModel;
    }

    //Phần này là chỉnh sửa cột và và header cho bảng vì không thể lấy
    // hàm từ bên màn hình trung tâm qua được
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 80; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 5, width);
            }
            if (width > 600) {
                width = 600;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
    public static int UpdateSupplyTransactionNonRepeatableReads(Connection con, Supply Supply) {
        try {
            String sql = "{CALL PROC_UPDATE_SUPPLY(?,?,?,?,?)}";
            CallableStatement ps = con.prepareCall(sql);
                      
            ps.setInt(1, Supply.getIdsup());
            ps.setInt(2, Supply.getNeedfood());
            ps.setInt(3, Supply.getNeednecess());
            ps.setInt(4, Supply.getNeedequip());
            ps.setString(5,Supply.getDetail());
            //ps.setInt(6,Supply.getStatus());
            
            ps.execute();
            
            //ps.close();
            return 0;
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 20142){
                JOptionPane.showMessageDialog(null, "Trạng thái yêu cầu không hợp lệ!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
//            else if (sqlex.getErrorCode() == 1407){
//                JOptionPane.showMessageDialog(null, "Vui lòng chọn tối thiểu một danh mục cần tiếp tế!",
//                        "Lỗi!", JOptionPane.WARNING_MESSAGE);}
            else if (sqlex.getErrorCode() == 20081){
                JOptionPane.showMessageDialog(null, "Yêu cầu tiếp tế này không còn tồn tại",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);}
            else if (sqlex.getErrorCode() == 2290){
                JOptionPane.showMessageDialog(null, "Vui lòng chọn tối thiểu một danh mục cần tiếp tế!",
                        "Lỗi!", JOptionPane.WARNING_MESSAGE);}
            sqlex.printStackTrace();
            return 1;
        }
        
        catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }
    }
}
