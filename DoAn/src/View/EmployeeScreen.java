/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Process.CharityController;
import static Process.CharityController.exportCharityMarkToPdf;
import Process.DoctorController;
import static Process.DoctorController.exportDoctorToPdf;
import Process.EmployeeController;
import Process.PersonController;
import Model.Account;
import Model.Person;
import Model.Charity;
import Model.PersonTableModel;
import static View.ChangeValue.*;
import static View.ChangeValue.IshasInt;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import Process.PersonController;
import Process.SupplyController;
import static Process.SupplyController.exportSupplyToPdf;
import Model.CharityTableModel;
import Model.Doctor;
import Model.DoctorTableModel;
import Model.Employee;
import Model.EmployeeTableModel;
import Model.Supply;
import Model.SupplyTableModel;
import java.awt.Font;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author MyPC
 */
public class EmployeeScreen extends javax.swing.JFrame {

    /**
     * Creates new form EmployeeScreen
     */
    
    //Khai báo cardLayout
    private CardLayout cardLayout;
    
    //enum chứa các loại hệ điều hành, gồm Windows, MacOs, Linux
    public enum OSType {Windows, MacOS, Linux}
    
    
    private static OSType detectedOS;
    
    //Khai báo DefaultTableModel và Arraylist cho việc đổ dữ liệu vào bảng
    DefaultTableModel modelTablePerson = null;
    private ArrayList<Person> listPerson;
    DefaultTableModel modelTableDoctor = null;
    private ArrayList<Doctor> listDoctor;
    DefaultTableModel modelTableCharity = null;
    private ArrayList<Charity> listCharity;
    DefaultTableModel modelTableEmployee = null;
    private ArrayList<Employee> listEmployee;
    DefaultTableModel modelTableSupply = null;
    private ArrayList<Supply> listSupply;
    private String usernameempcur; 
    
    //Đổ dữ liệu lên dialog UpdatePersonScreen
    public void SetdataforanotherJframe(){ 
        DefaultTableModel model = (DefaultTableModel) PersonTable.getModel();
        int selectedRowIndex = PersonTable.getSelectedRow();

        
        selectedRowIndex = PersonTable.convertRowIndexToModel(selectedRowIndex);
        
        Person person = new Person();
        person.setIdper((int) model.getValueAt(selectedRowIndex, 0));
        person.setUsername(String.valueOf(model.getValueAt(selectedRowIndex, 1)));
        person.setName(model.getValueAt(selectedRowIndex, 2).toString());
        person.setGender((int)GenderInt(model.getValueAt(selectedRowIndex, 3).toString()));
        person.setPhone(model.getValueAt(selectedRowIndex, 4).toString());
        person.setProvince(model.getValueAt(selectedRowIndex, 5).toString());
        person.setDistrict(model.getValueAt(selectedRowIndex, 6).toString());
        person.setTown(model.getValueAt(selectedRowIndex, 7).toString());
        person.setAddress(model.getValueAt(selectedRowIndex, 8).toString());
        person.setStatus((int)PersonStatusInt(model.getValueAt(selectedRowIndex, 9).toString()));
        
        //AddPersonScreen aps = new AddPersonScreen(person);
        //update
        UpdatePersonScreen ips = new UpdatePersonScreen(this, true, person);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("Thông tin người cần giúp đỡ");
        ips.setVisible(true);
    }
    
    //Đổ dữ liệu lên dialog AddPersonScreen
    public void SetdataforanotherJframeadd(){ 
        DefaultTableModel model = (DefaultTableModel) PersonTable.getModel();
        int selectedRowIndex = PersonTable.getSelectedRow();
        
        selectedRowIndex = PersonTable.convertRowIndexToModel(selectedRowIndex);
        
        Person person = new Person();
        Account account = new Account();
        person.setIdper(32);
        person.setUsername("");
        person.setName("");
        account.setPassword("");
        person.setGender(-1);
        person.setPhone(" ");
        person.setProvince("");
        person.setDistrict("");
        person.setTown("");
        person.setAddress("");
        person.setStatus(0);
        
        AddPersonScreen ips = new AddPersonScreen(this, true);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("Thêm thông tin ngưươi cần giúp đỡ");
        ips.setVisible(true);
    }
    
    //Đổ dữ liệu lên dialog AddPersonHotLineScreen
    public void SetdataforanotherJframeaddhotline(){ 
        DefaultTableModel model = (DefaultTableModel) PersonTable.getModel();
        int selectedRowIndex = PersonTable.getSelectedRow();
        
        selectedRowIndex = PersonTable.convertRowIndexToModel(selectedRowIndex);
        
        Person person = new Person();
        person.setIdper(31);
        person.setUsername("hotline");
        person.setName("");
        person.setGender(-1);
        person.setPhone(" ");
        person.setProvince("");
        person.setDistrict("");
        person.setTown("");
        person.setAddress("");
        person.setStatus(0);
        
        AddPersonHotlineScreen ips = new AddPersonHotlineScreen(this, true);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("thêm thông tin người cần giúp đỡ");
        ips.setVisible(true);
    }
    
    //Đổ dữ liệu lên dialog UpdateDoctorScreen
     public void SetdataforUpdateDoctorScreen(){ 
        DefaultTableModel model = (DefaultTableModel) DoctorTable.getModel();
        int selectedRowIndex = DoctorTable.getSelectedRow();
        
        selectedRowIndex = DoctorTable.convertRowIndexToModel(selectedRowIndex);
        
        Doctor doctor = new Doctor();
        doctor.setIddoc((int) model.getValueAt(selectedRowIndex, 0));
        doctor.setUsername(String.valueOf(model.getValueAt(selectedRowIndex, 1)));
        doctor.setName(model.getValueAt(selectedRowIndex, 2).toString());
        doctor.setGender((int)GenderInt(model.getValueAt(selectedRowIndex, 3).toString()));
        doctor.setPhone(model.getValueAt(selectedRowIndex, 4).toString());
        doctor.setAccademicrank(AcademicRankInt(model.getValueAt(selectedRowIndex, 5).toString().trim()));
        doctor.setSubject(SubjectInt(model.getValueAt(selectedRowIndex, 6).toString().trim()));
        doctor.setWorkunits(model.getValueAt(selectedRowIndex, 7).toString());
        doctor.setProvince(model.getValueAt(selectedRowIndex, 8).toString());
        
        //AddPersonScreen aps = new AddPersonScreen(person);
        UpdateDoctorScreen ips = new UpdateDoctorScreen(this, true, doctor);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("Cập nhật thông tin bác sĩ");
        ips.setVisible(true);
    }//xong
     
    //Đổ dữ liệu lên dialog AddDoctorScreen
    public void SetdataforAddDoctorScreen(){ 
        AddDoctorScreen ips = new AddDoctorScreen(this, true);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("Thêm thông tin bác sĩ");
        ips.setVisible(true);
    }//xong
    
    //Đổ dữ liệu lên dialog UpdateCharityScreen
    public void SetdataforUpdateCharityScreen(){ 
        DefaultTableModel model = (DefaultTableModel) CharityTable.getModel();
        int selectedRowIndex = CharityTable.getSelectedRow();

        selectedRowIndex = CharityTable.convertRowIndexToModel(selectedRowIndex);
        
        Charity charity = new Charity();
        charity.setIdchar((int) model.getValueAt(selectedRowIndex, 0));
        charity.setUsername(String.valueOf(model.getValueAt(selectedRowIndex, 1)));
        charity.setName(model.getValueAt(selectedRowIndex, 2).toString());
        charity.setPhone(model.getValueAt(selectedRowIndex, 3).toString());
        charity.setProvince(model.getValueAt(selectedRowIndex, 4).toString());
        charity.setDistrict(model.getValueAt(selectedRowIndex, 5).toString().trim());
        charity.setTown(model.getValueAt(selectedRowIndex, 6).toString().trim());
        charity.setAddress(model.getValueAt(selectedRowIndex, 7).toString());
        charity.setHasfood(IshasInt(model.getValueAt(selectedRowIndex, 8).toString().trim()));
        charity.setHasnecess(IshasInt(model.getValueAt(selectedRowIndex, 9).toString().trim()));
        charity.setHasequip(IshasInt(model.getValueAt(selectedRowIndex, 10).toString().trim()));
        charity.setPoint((int) model.getValueAt(selectedRowIndex, 11));
        
        //AddPersonScreen aps = new AddPersonScreen(person);
        UpateCharityScreen ips = new UpateCharityScreen(this, true, charity);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("Cập nhật thông tin trung tâm");
        ips.setVisible(true);
    }
    
    //Đổ dữ liệu lên dialog AddCharityScreen
    public void SetdataforAddCharityScreen(){ 
        AddCharityScreen ips = new AddCharityScreen(this, true);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("Thêm thông tin trung tâm");
        ips.setVisible(true);
    }//testing
    
    //Đổ dữ liệu lên dialog UpdateEmployeeScreen
    public void SetdataforUpdateEmployeeScreen(){ 
        DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
        int selectedRowIndex = EmployeeTable.getSelectedRow();

        selectedRowIndex = EmployeeTable.convertRowIndexToModel(selectedRowIndex);
        
        Employee employee = new Employee();
        employee.setIdemp((int) model.getValueAt(selectedRowIndex, 0));
        employee.setUsername(String.valueOf(model.getValueAt(selectedRowIndex, 1)));
        employee.setName(model.getValueAt(selectedRowIndex, 2).toString());
        employee.setGender((int)GenderInt(model.getValueAt(selectedRowIndex, 3).toString()));
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date;
        try {
            date = (java.util.Date)df.parse(model.getValueAt(selectedRowIndex, 4).toString());
            employee.setStartdate(date);
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }
        
        //employee.setStartdate(date);
        employee.setPhone(model.getValueAt(selectedRowIndex, 5).toString());
        employee.setAddress(model.getValueAt(selectedRowIndex, 6).toString());
           
        UpdateEmployeeScreen ips = new UpdateEmployeeScreen(this, true, employee);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("Cập nhật thông tin nhân viên");
        ips.setVisible(true);
    }
     
    //Đổ dữ liệu lên dialog AddEmployeeScreen
    public void SetdataforAddEmployeeScreen(){ 
        AddEmployeeScreen ips = new AddEmployeeScreen(this, true);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("Thêm thông tin nhân viên");
        ips.setVisible(true);
    }
    
    //Đổ dữ liệu lên dialog UpdateSupplyScreen
    public void SetdataforUpdateSupplyScreen(){ 
        DefaultTableModel model = (DefaultTableModel)SupplyTable.getModel();
        int selectedRowIndex = SupplyTable.getSelectedRow();

        selectedRowIndex = SupplyTable.convertRowIndexToModel(selectedRowIndex);
        
        Supply supply = new Supply();
        supply.setIdsup((int) model.getValueAt(selectedRowIndex, 0));
        supply.setIdper((int) model.getValueAt(selectedRowIndex, 1));
        supply.setNeedfood(IshasInt(model.getValueAt(selectedRowIndex, 4).toString()));
        supply.setNeednecess(IshasInt(model.getValueAt(selectedRowIndex, 5).toString()));
        supply.setNeedequip(IshasInt(model.getValueAt(selectedRowIndex, 6).toString()));
        supply.setStatus(SupplyStatusInt(model.getValueAt(selectedRowIndex, 7).toString().trim()));
        supply.setDetail(model.getValueAt(selectedRowIndex, 8).toString().trim());
        
        if (supply.getStatus() != 1) {
            JOptionPane.showMessageDialog(null, "Trạng thái không hợp lệ, không thể sửa!", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
        } else {
            //AddPersonScreen aps = new AddPersonScreen(person);
            UpdateSupplyScreen ips = new UpdateSupplyScreen(this, true, supply);
            ips.setLocationRelativeTo(null);
            ips.setResizable(false);
            ips.setTitle("Cập nhật thông tin yêu cầu tiếp tế");
            ips.setVisible(true);
        }
    }
     
    //Đổ dữ liệu lên dialog AddSupplyScreen
    public void SetdataforAddSupplyScreen(){ 
        AddSupplyScreen ips = new AddSupplyScreen(this, true);
        ips.setLocationRelativeTo(null);
        ips.setResizable(false);
        ips.setTitle("Thêm thông tin yêu cầu tiếp tế");
        ips.setVisible(true);
    }
    
    //Đổ dữ liệu lên dialog card2 (thông tin nhân viên đăng nhập)
    public void setdataforcard2(Employee employee, Account account){ 
        EmployeeController econ = new EmployeeController();
        Employee emp = new Employee();
        emp = econ.getEmployeeInfo(employee.getUsername());
        
        NameLabel.setText(emp.getName());
        RoleLabel.setText(AccountRole(account.getRole()));
        UsernameLabel.setText(emp.getUsername());
        GenderLabel.setText(Gender(emp.getGender()));
        PhoneLabel.setText(emp.getPhone());
        AddressLabel.setText(emp.getAddress());
        StartdateLabel.setText(emp.getStartdate().toString());
    }

    //Lấy Jtable
    //Để reset dữ liệu trong bảng khi thêm, xóa sửa thành công ở Jdialog
    //Lấy PersonTable
    public JTable getPersonTable(){
        return PersonTable;
    }

    //Lấy DoctorTable
    public JTable getDoctorTable(){
        return DoctorTable;
    }
    
    //Lấy CharityTable
    public JTable getCharityTable(){
        return CharityTable;
    }
    
    //Lấy EmpployeeTable
    public JTable getEmployeeTable(){
        return EmployeeTable;
    }
    
    //Lấy SupplyTable
    public JTable getSupplyTable(){
        return SupplyTable;
    }
    
    //Đổ dữ liệu vào bảng Person
    public void setTableManagePerson() {
        PersonController percon=new PersonController();
        listPerson=percon.getPerson();
        modelTablePerson= new PersonTableModel().setTable(listPerson);       
        PersonTable.setModel(modelTablePerson);
    }
    
    //Đổ dữ liệu vào bảng Person
    public void setTableManageDoctor() {
        DoctorController doccon=new DoctorController();
        listDoctor=doccon.getDoctor();
        modelTableDoctor= new DoctorTableModel().setDoctorTable(listDoctor);       
        DoctorTable.setModel(modelTableDoctor);
    }
 
    //Đổ dữ liệu vào bảng Person
    public void setTableManageCharity() {
        CharityController charcon=new CharityController();
        listCharity=charcon.getCharity();
        modelTableCharity= new CharityTableModel().setCharityTable(listCharity);       
        CharityTable.setModel(modelTableCharity);
    }
    
    //Đổ dữ liệu vào bảng Person
    public void setTableManageEmployee() {
        EmployeeController empcon=new EmployeeController();
        listEmployee=empcon.getEmployee();
        modelTableEmployee= new EmployeeTableModel().setEmployeeTable(listEmployee);       
        EmployeeTable.setModel(modelTableEmployee);
    }
    
    //Đổ dữ liệu vào bảng Person
    public void setTableManageSupply() {
        SupplyController supcon=new SupplyController();
        listSupply=supcon.getSupply();
        modelTableSupply= new SupplyTableModel().setSupplyTable(listSupply);       
        SupplyTable.setModel(modelTableSupply);
    }
      
    //Tìm kiếm theo ID 
    public void Search(JTextField txt, JTable table, DefaultTableModel model, java.awt.event.KeyEvent e) { 
        String ID = txt.getText();
        model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        
        //"^" & "$" dùng để tách ID thành một số riêng
        //VD: Khi nhập 1 và nhấn enter thì chỉ hiển thị ID có số 1, còn không sẽ
        //thị tất cả các số có chữ số bắt đầu bằng chữ số 1 (1x).
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
            trs.setRowFilter(RowFilter.regexFilter("^"+ID+"$", 0));
        else 
            trs.setRowFilter(RowFilter.regexFilter("^"+ID.trim(), 0)); 

        table.setRowSorter(trs);
    }
    
    //Dùng để lọc các từ ngữ không phù hợp trong phần mô tả
    public void FilterSupply(JCheckBox cb, JTable table, DefaultTableModel model) { 
        model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        //"(?i)" dùng dể không phân biệt hoa thường
        //8 là cột để lọc
        if(cb.isSelected())
            trs.setRowFilter(RowFilter.regexFilter("(?i)"+"mẹ mày|cmm|đéo|người yêu|tao", 8));
        table.setRowSorter(trs);
        resizeColumnWidth(SupplyTable);
    }
    
    //Tự động tăng kích thước cột để phù ợp với dữ liệu hiện có và các dữ liệu sẽ được
    //thêm trong tương lai
    public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
//    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
//    rightRenderer.setHorizontalAlignment(5);
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 85; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +5 , width);
        }
        if(width > 600)
            width=600;
        columnModel.getColumn(column).setPreferredWidth(width);
//        table.getColumnModel().getColumn(column).setCellRenderer(rightRenderer);
    }
}
    
    //Set look and feel theo giao diện windows
    //dùng để chỉnh look and feel cho  EmployeeScreen khi được gọi từ Jframe LogInScreen
    public void setLookandFeel(){ 
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    //Hàm khởi tạo có đối số
    //được gọi khi đăng nhập thành công tại màn hình LogInScreen
    public EmployeeScreen(Employee employee, Account account) { 
        setLookandFeel();
        initComponents();
        this.setLocationRelativeTo(null);
        usernameempcur = account.getUsername();
        setdataforcard2(employee, account);
        SetTabel(PersonTable);
        SetTabel(DoctorTable);
        SetTabel(CharityTable);
        SetTabel(EmployeeTable);
        SetTabel(SupplyTable);
        CheckOSType();
        
        setTableManagePerson();
        setTableManageDoctor();
        setTableManageCharity();
        setTableManageEmployee();
        setTableManageSupply();
        
        resizeColumnWidth(PersonTable);
        resizeColumnWidth(DoctorTable);
        resizeColumnWidth(CharityTable);
        resizeColumnWidth(EmployeeTable);
        resizeColumnWidth(SupplyTable);
        
        //test.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Search_new.jpg")));
         
     }    
    
    //Hàm khởi tạo không tham số
    //được gọi tại các dialog Update, Add để làm parent
     public EmployeeScreen() {
     initComponents();
     //initComponents();
        SetTabel(PersonTable);
        SetTabel(DoctorTable);
        SetTabel(CharityTable);
        SetTabel(EmployeeTable);
        SetTabel(SupplyTable);
        CheckOSType();
        
        setTableManagePerson();
        setTableManageDoctor();
        setTableManageCharity();
        setTableManageEmployee();
        setTableManageSupply();
        
        resizeColumnWidth(PersonTable);
        resizeColumnWidth(DoctorTable);
        resizeColumnWidth(CharityTable);
        resizeColumnWidth(EmployeeTable);
        resizeColumnWidth(SupplyTable);

     };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PersonTablePopupMenu = new javax.swing.JPopupMenu();
        UpdateMenuItem = new javax.swing.JMenuItem();
        DeleteMenuItem = new javax.swing.JMenuItem();
        DoctorPopupMenu = new javax.swing.JPopupMenu();
        UpdateDoctorMenuItem = new javax.swing.JMenuItem();
        DeleteDoctorMenuItem = new javax.swing.JMenuItem();
        CharityPopupMenu = new javax.swing.JPopupMenu();
        UpdateCharityMenuItem = new javax.swing.JMenuItem();
        DeleteCharityPopupMenu = new javax.swing.JMenuItem();
        EmployeePopupMenu = new javax.swing.JPopupMenu();
        UpdateEmployeeMenuItem = new javax.swing.JMenuItem();
        DeleteEmployeeMenuItem = new javax.swing.JMenuItem();
        SupplyPopupMenu = new javax.swing.JPopupMenu();
        UpdateSupplyMenuItem = new javax.swing.JMenuItem();
        DeleteSupplyMenuItem = new javax.swing.JMenuItem();
        VerifySupplyMenuItem = new javax.swing.JMenuItem();
        DenySupplyMenuItem = new javax.swing.JMenuItem();
        componentResizerUtil1 = new com.k33ptoo.utils.ComponentResizerUtil();
        TopPanel = new javax.swing.JPanel();
        ActionsPanel = new javax.swing.JPanel();
        MinimizeLabel = new javax.swing.JLabel();
        MaximizeLabel = new javax.swing.JLabel();
        CloseLabel = new javax.swing.JLabel();
        TitlePanel = new javax.swing.JPanel();
        TitleLabel = new javax.swing.JLabel();
        ParentPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        index1Panel = new javax.swing.JPanel();
        ind_index1Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        index6Panel = new javax.swing.JPanel();
        ind_index6Panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        index2Panel = new javax.swing.JPanel();
        ind_index2Panel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        index3Panel = new javax.swing.JPanel();
        ind_index3Panel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        index4Panel = new javax.swing.JPanel();
        ind_index4Panel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        index5Panel = new javax.swing.JPanel();
        ind_index5Panel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        index7Panel = new javax.swing.JPanel();
        ind_index7Panel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        RightPanel = new javax.swing.JPanel();
        card2Panel = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        RoleLabel = new javax.swing.JLabel();
        FemaleLabel = new javax.swing.JLabel();
        MaleLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        UsernameLabel = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        kButton11 = new com.k33ptoo.components.KButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        GenderLabel = new javax.swing.JLabel();
        PhoneLabel = new javax.swing.JLabel();
        AddressLabel = new javax.swing.JLabel();
        StartdateLabel = new javax.swing.JLabel();
        card3Panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PersonTable = new javax.swing.JTable();
        SearchPersonTextField = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        kButton1 = new com.k33ptoo.components.KButton();
        kButton2 = new com.k33ptoo.components.KButton();
        card4Panel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DoctorTable = new javax.swing.JTable();
        DoctorSearchTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        kButton3 = new com.k33ptoo.components.KButton();
        kButton4 = new com.k33ptoo.components.KButton();
        card5CharityPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        CharityTable = new javax.swing.JTable();
        CharitySearchTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        kButton5 = new com.k33ptoo.components.KButton();
        kButton6 = new com.k33ptoo.components.KButton();
        card6Panel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        EmployeeTable = new javax.swing.JTable();
        EmployeeSearchTextField = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        kButton7 = new com.k33ptoo.components.KButton();
        card7Panel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        SupplyTable = new javax.swing.JTable();
        SearchSupplyTextField = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        kButton8 = new com.k33ptoo.components.KButton();
        kButton9 = new com.k33ptoo.components.KButton();
        kButton10 = new com.k33ptoo.components.KButton();
        test = new javax.swing.JCheckBox();
        Card8InforEmployeePanel = new javax.swing.JPanel();

        UpdateMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/edit (1).png"))); // NOI18N
        UpdateMenuItem.setText("Sửa");
        UpdateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateMenuItemActionPerformed(evt);
            }
        });
        PersonTablePopupMenu.add(UpdateMenuItem);

        DeleteMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/bin (1).png"))); // NOI18N
        DeleteMenuItem.setText("Xóa");
        DeleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteMenuItemActionPerformed(evt);
            }
        });
        PersonTablePopupMenu.add(DeleteMenuItem);

        UpdateDoctorMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/edit (1).png"))); // NOI18N
        UpdateDoctorMenuItem.setText("Sửa");
        UpdateDoctorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateDoctorMenuItemActionPerformed(evt);
            }
        });
        DoctorPopupMenu.add(UpdateDoctorMenuItem);

        DeleteDoctorMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/bin (1).png"))); // NOI18N
        DeleteDoctorMenuItem.setText("Xóa");
        DeleteDoctorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteDoctorMenuItemActionPerformed(evt);
            }
        });
        DoctorPopupMenu.add(DeleteDoctorMenuItem);

        UpdateCharityMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/edit (1).png"))); // NOI18N
        UpdateCharityMenuItem.setText("Sửa");
        UpdateCharityMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateCharityMenuItemActionPerformed(evt);
            }
        });
        CharityPopupMenu.add(UpdateCharityMenuItem);

        DeleteCharityPopupMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/bin (1).png"))); // NOI18N
        DeleteCharityPopupMenu.setText("Xóa");
        DeleteCharityPopupMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteCharityPopupMenuActionPerformed(evt);
            }
        });
        CharityPopupMenu.add(DeleteCharityPopupMenu);

        UpdateEmployeeMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/edit (1).png"))); // NOI18N
        UpdateEmployeeMenuItem.setText("Sửa");
        UpdateEmployeeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateEmployeeMenuItemActionPerformed(evt);
            }
        });
        EmployeePopupMenu.add(UpdateEmployeeMenuItem);

        DeleteEmployeeMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/bin (1).png"))); // NOI18N
        DeleteEmployeeMenuItem.setText("Xóa");
        DeleteEmployeeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteEmployeeMenuItemActionPerformed(evt);
            }
        });
        EmployeePopupMenu.add(DeleteEmployeeMenuItem);

        UpdateSupplyMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/edit (1).png"))); // NOI18N
        UpdateSupplyMenuItem.setText("Sửa");
        UpdateSupplyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateSupplyMenuItemActionPerformed(evt);
            }
        });
        SupplyPopupMenu.add(UpdateSupplyMenuItem);

        DeleteSupplyMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/bin (1).png"))); // NOI18N
        DeleteSupplyMenuItem.setText("Xóa");
        DeleteSupplyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteSupplyMenuItemActionPerformed(evt);
            }
        });
        SupplyPopupMenu.add(DeleteSupplyMenuItem);

        VerifySupplyMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/accept (1).png"))); // NOI18N
        VerifySupplyMenuItem.setText("Xác thực");
        VerifySupplyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerifySupplyMenuItemActionPerformed(evt);
            }
        });
        SupplyPopupMenu.add(VerifySupplyMenuItem);

        DenySupplyMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/cancel (1).png"))); // NOI18N
        DenySupplyMenuItem.setText("Hủy");
        DenySupplyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DenySupplyMenuItemActionPerformed(evt);
            }
        });
        SupplyPopupMenu.add(DenySupplyMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MÀN HÌNH NHÂN VIÊN");
        setLocationByPlatform(true);
        setUndecorated(true);

        TopPanel.setBackground(new java.awt.Color(106, 128, 254));
        TopPanel.setPreferredSize(new java.awt.Dimension(1024, 30));
        TopPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                TopPanelMouseDragged(evt);
            }
        });
        TopPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TopPanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TopPanelMousePressed(evt);
            }
        });
        TopPanel.setLayout(new java.awt.BorderLayout());

        ActionsPanel.setBackground(new java.awt.Color(106, 128, 254));
        ActionsPanel.setPreferredSize(new java.awt.Dimension(75, 30));

        MinimizeLabel.setPreferredSize(new java.awt.Dimension(18, 18));
        MinimizeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MinimizeLabelMousePressed(evt);
            }
        });
        ActionsPanel.add(MinimizeLabel);

        MaximizeLabel.setPreferredSize(new java.awt.Dimension(18, 18));
        MaximizeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MaximizeLabelMousePressed(evt);
            }
        });
        ActionsPanel.add(MaximizeLabel);

        CloseLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/button.png"))); // NOI18N
        CloseLabel.setPreferredSize(new java.awt.Dimension(18, 18));
        CloseLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CloseLabelMousePressed(evt);
            }
        });
        ActionsPanel.add(CloseLabel);

        TopPanel.add(ActionsPanel, java.awt.BorderLayout.LINE_END);

        TitlePanel.setBackground(new java.awt.Color(106, 128, 254));
        TitlePanel.add(TitleLabel);

        TopPanel.add(TitlePanel, java.awt.BorderLayout.LINE_START);

        getContentPane().add(TopPanel, java.awt.BorderLayout.PAGE_START);

        ParentPanel.setPreferredSize(new java.awt.Dimension(1050, 570));
        ParentPanel.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(106, 197, 254));
        jPanel2.setPreferredSize(new java.awt.Dimension(230, 570));

        index1Panel.setBackground(new java.awt.Color(106, 197, 254));
        index1Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index1PanelMousePressed(evt);
            }
        });
        index1Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index1Panel.setOpaque(false);
        ind_index1Panel.setPreferredSize(new java.awt.Dimension(6, 41));

        javax.swing.GroupLayout ind_index1PanelLayout = new javax.swing.GroupLayout(ind_index1Panel);
        ind_index1Panel.setLayout(ind_index1PanelLayout);
        ind_index1PanelLayout.setHorizontalGroup(
            ind_index1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index1PanelLayout.setVerticalGroup(
            ind_index1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        index1Panel.add(ind_index1Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel1.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel1.setText("Người cần giúp đỡ");
        index1Panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/youth.png"))); // NOI18N
        index1Panel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 32, 32));
        index1Panel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 32, 32));
        index1Panel.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 32, 32));

        index6Panel.setBackground(new java.awt.Color(106, 197, 254));
        index6Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index6PanelMousePressed(evt);
            }
        });
        index6Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index6Panel.setOpaque(false);
        ind_index6Panel.setPreferredSize(new java.awt.Dimension(6, 41));

        javax.swing.GroupLayout ind_index6PanelLayout = new javax.swing.GroupLayout(ind_index6Panel);
        ind_index6Panel.setLayout(ind_index6PanelLayout);
        ind_index6PanelLayout.setHorizontalGroup(
            ind_index6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index6PanelLayout.setVerticalGroup(
            ind_index6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        index6Panel.add(ind_index6Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel2.setText("Thông tin");
        index6Panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/infomation.png"))); // NOI18N
        index6Panel.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 32, 32));

        index2Panel.setBackground(new java.awt.Color(106, 197, 254));
        index2Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index2PanelMousePressed(evt);
            }
        });
        index2Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index2Panel.setOpaque(false);
        ind_index2Panel.setPreferredSize(new java.awt.Dimension(6, 41));

        javax.swing.GroupLayout ind_index2PanelLayout = new javax.swing.GroupLayout(ind_index2Panel);
        ind_index2Panel.setLayout(ind_index2PanelLayout);
        ind_index2PanelLayout.setHorizontalGroup(
            ind_index2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index2PanelLayout.setVerticalGroup(
            ind_index2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        index2Panel.add(ind_index2Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel3.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel3.setText("Trung tâm");
        index2Panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/shelter (1).png"))); // NOI18N
        index2Panel.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 32, 32));

        index3Panel.setBackground(new java.awt.Color(106, 197, 254));
        index3Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index3PanelMousePressed(evt);
            }
        });
        index3Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index3Panel.setOpaque(false);
        ind_index3Panel.setPreferredSize(new java.awt.Dimension(6, 41));

        javax.swing.GroupLayout ind_index3PanelLayout = new javax.swing.GroupLayout(ind_index3Panel);
        ind_index3Panel.setLayout(ind_index3PanelLayout);
        ind_index3PanelLayout.setHorizontalGroup(
            ind_index3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index3PanelLayout.setVerticalGroup(
            ind_index3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        index3Panel.add(ind_index3Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel4.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel4.setText("Bác sĩ");
        index3Panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/doctor (1).png"))); // NOI18N
        index3Panel.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 32, 32));

        index4Panel.setBackground(new java.awt.Color(106, 197, 254));
        index4Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index4PanelMousePressed(evt);
            }
        });
        index4Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index4Panel.setOpaque(false);
        ind_index4Panel.setPreferredSize(new java.awt.Dimension(6, 41));

        javax.swing.GroupLayout ind_index4PanelLayout = new javax.swing.GroupLayout(ind_index4Panel);
        ind_index4Panel.setLayout(ind_index4PanelLayout);
        ind_index4PanelLayout.setHorizontalGroup(
            ind_index4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index4PanelLayout.setVerticalGroup(
            ind_index4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        index4Panel.add(ind_index4Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel5.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel5.setText("Nhân viên");
        index4Panel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/employees.png"))); // NOI18N
        index4Panel.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 32, 32));

        index5Panel.setBackground(new java.awt.Color(106, 197, 254));
        index5Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index5PanelMousePressed(evt);
            }
        });
        index5Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index5Panel.setOpaque(false);
        ind_index5Panel.setPreferredSize(new java.awt.Dimension(6, 41));

        javax.swing.GroupLayout ind_index5PanelLayout = new javax.swing.GroupLayout(ind_index5Panel);
        ind_index5Panel.setLayout(ind_index5PanelLayout);
        ind_index5PanelLayout.setHorizontalGroup(
            ind_index5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index5PanelLayout.setVerticalGroup(
            ind_index5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        index5Panel.add(ind_index5Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel6.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel6.setText("Yêu cầu tiếp tế");
        index5Panel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/groceries (1).png"))); // NOI18N
        index5Panel.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 32, 32));

        index7Panel.setBackground(new java.awt.Color(106, 197, 254));
        index7Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index7PanelMousePressed(evt);
            }
        });
        index7Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index7Panel.setOpaque(false);
        ind_index7Panel.setPreferredSize(new java.awt.Dimension(6, 41));

        javax.swing.GroupLayout ind_index7PanelLayout = new javax.swing.GroupLayout(ind_index7Panel);
        ind_index7Panel.setLayout(ind_index7PanelLayout);
        ind_index7PanelLayout.setHorizontalGroup(
            ind_index7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index7PanelLayout.setVerticalGroup(
            ind_index7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        index7Panel.add(ind_index7Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel7.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel7.setText("Đăng xuất");
        index7Panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/logout (1).png"))); // NOI18N
        index7Panel.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 32, 32));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setText("THÔNG TIN NGƯỜI DÙNG");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("QUẢN LÝ THÔNG TIN");

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/logo_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(index1Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
            .addComponent(index2Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(index3Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(index4Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(index5Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(index6Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(index7Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(index1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(index2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(index3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(index4Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(index5Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(index6Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(index7Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        ParentPanel.add(jPanel2, java.awt.BorderLayout.LINE_START);

        RightPanel.setLayout(new java.awt.CardLayout());

        card2Panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel20.setBackground(new java.awt.Color(106, 197, 254));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NGƯỜI DÙNG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 15))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel13.setText("Họ và tên");

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel14.setText("Vai trò");

        NameLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        RoleLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        FemaleLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/EmpFemale.png"))); // NOI18N

        MaleLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/EmpMale.png"))); // NOI18N

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(27, 27, 27)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(NameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RoleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(FemaleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                    .addContainerGap(29, Short.MAX_VALUE)
                    .addComponent(MaleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(FemaleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(RoleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel21Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(MaleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(137, Short.MAX_VALUE)))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THÔNG TIN TÀI KHOẢN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 15))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel15.setText("Username");

        UsernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        kButton11.setText("ĐỔI MẬT KHẨU");
        kButton11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        kButton11.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton11.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton11.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton11.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton11.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton11.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton11.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton11.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton11.setkStartColor(new java.awt.Color(102, 255, 204));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel15)
                        .addGap(45, 45, 45)
                        .addComponent(UsernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(kButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(StatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(UsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(kButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THÔNG TIN CÁ NHÂN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 15))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel17.setText("Giới tính");

        jLabel18.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel18.setText("Điên thoại ");

        jLabel19.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel19.setText("Đại chỉ");

        jLabel20.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel20.setText("Ngày vào làm");

        GenderLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        PhoneLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        AddressLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        StartdateLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StartdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PhoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GenderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(GenderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(PhoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(AddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(StartdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout card2PanelLayout = new javax.swing.GroupLayout(card2Panel);
        card2Panel.setLayout(card2PanelLayout);
        card2PanelLayout.setHorizontalGroup(
            card2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(card2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card2PanelLayout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(card2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        card2PanelLayout.setVerticalGroup(
            card2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2PanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(card2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(card2PanelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RightPanel.add(card2Panel, "card2");

        card3Panel.setBackground(new java.awt.Color(255, 255, 255));

        PersonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        PersonTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        PersonTable.setInheritsPopupMenu(true);
        PersonTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PersonTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(PersonTable);

        SearchPersonTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchPersonTextFieldActionPerformed(evt);
            }
        });
        SearchPersonTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SearchPersonTextFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchPersonTextFieldKeyReleased(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(106, 197, 254));
        jPanel6.setPreferredSize(new java.awt.Dimension(800, 70));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setText("Tìm kiếm");

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setText("Tìm kiếm");

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Search_new.jpg"))); // NOI18N

        kButton1.setText("THÊM");
        kButton1.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton1.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton1.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton1.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton1.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton1.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton1.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton1.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton1.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton1ActionPerformed(evt);
            }
        });

        kButton2.setText("THÊM HOTLINE");
        kButton2.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton2.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton2.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton2.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton2.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton2.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton2.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton2.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card3PanelLayout = new javax.swing.GroupLayout(card3Panel);
        card3Panel.setLayout(card3PanelLayout);
        card3PanelLayout.setHorizontalGroup(
            card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card3PanelLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(card3PanelLayout.createSequentialGroup()
                .addGroup(card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(card3PanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1))
                    .addGroup(card3PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(SearchPersonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGap(15, 15, 15))
            .addGroup(card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(card3PanelLayout.createSequentialGroup()
                    .addGap(379, 379, 379)
                    .addComponent(jLabel21)
                    .addContainerGap(380, Short.MAX_VALUE)))
        );
        card3PanelLayout.setVerticalGroup(
            card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card3PanelLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchPersonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23))
                    .addGroup(card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(card3PanelLayout.createSequentialGroup()
                    .addGap(269, 269, 269)
                    .addComponent(jLabel21)
                    .addContainerGap(288, Short.MAX_VALUE)))
        );

        RightPanel.add(card3Panel, "card3");

        card4Panel.setBackground(new java.awt.Color(255, 255, 255));

        DoctorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        DoctorTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        DoctorTable.setInheritsPopupMenu(true);
        DoctorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DoctorTableMouseClicked(evt);
            }
        });
        DoctorTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DoctorTableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(DoctorTable);

        DoctorSearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoctorSearchTextFieldActionPerformed(evt);
            }
        });
        DoctorSearchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DoctorSearchTextFieldKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Tìm kiếm");

        jPanel1.setBackground(new java.awt.Color(106, 197, 254));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 70));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Search_new.jpg"))); // NOI18N

        kButton3.setText("IN THỐNG KÊ");
        kButton3.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton3.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton3.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton3.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton3.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton3.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton3.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton3.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton3.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton3ActionPerformed(evt);
            }
        });

        kButton4.setText("THÊM");
        kButton4.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton4.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton4.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton4.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton4.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton4.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton4.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton4.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton4.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card4PanelLayout = new javax.swing.GroupLayout(card4Panel);
        card4Panel.setLayout(card4PanelLayout);
        card4PanelLayout.setHorizontalGroup(
            card4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card4PanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(card4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(card4PanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(DoctorSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card4PanelLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        card4PanelLayout.setVerticalGroup(
            card4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card4PanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(card4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(card4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(card4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DoctorSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(card4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        RightPanel.add(card4Panel, "card4");

        card5CharityPanel.setBackground(new java.awt.Color(255, 255, 255));

        CharityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        CharityTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        CharityTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CharityTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(CharityTable);

        CharitySearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CharitySearchTextFieldActionPerformed(evt);
            }
        });
        CharitySearchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CharitySearchTextFieldKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("Tìm kiếm");

        jPanel3.setBackground(new java.awt.Color(106, 197, 254));
        jPanel3.setPreferredSize(new java.awt.Dimension(800, 70));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Search_new.jpg"))); // NOI18N

        kButton5.setText("IN THỐNG KÊ");
        kButton5.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton5.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton5.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton5.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton5.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton5.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton5.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton5.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton5.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton5ActionPerformed(evt);
            }
        });

        kButton6.setText("THÊM");
        kButton6.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton6.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton6.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton6.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton6.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton6.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton6.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton6.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton6.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card5CharityPanelLayout = new javax.swing.GroupLayout(card5CharityPanel);
        card5CharityPanel.setLayout(card5CharityPanelLayout);
        card5CharityPanelLayout.setHorizontalGroup(
            card5CharityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card5CharityPanelLayout.createSequentialGroup()
                .addGroup(card5CharityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card5CharityPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane3))
                    .addGroup(card5CharityPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(CharitySearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card5CharityPanelLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        card5CharityPanelLayout.setVerticalGroup(
            card5CharityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card5CharityPanelLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(card5CharityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(card5CharityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CharitySearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(kButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        RightPanel.add(card5CharityPanel, "card5");

        card6Panel.setBackground(new java.awt.Color(255, 255, 255));

        EmployeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        EmployeeTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        EmployeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EmployeeTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(EmployeeTable);

        EmployeeSearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeSearchTextFieldActionPerformed(evt);
            }
        });
        EmployeeSearchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                EmployeeSearchTextFieldKeyReleased(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(106, 197, 254));
        jPanel7.setPreferredSize(new java.awt.Dimension(800, 70));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setText("Tìm kiếm");

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Search_new.jpg"))); // NOI18N

        kButton7.setText("THÊM");
        kButton7.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton7.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton7.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton7.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton7.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton7.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton7.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton7.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton7.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card6PanelLayout = new javax.swing.GroupLayout(card6Panel);
        card6Panel.setLayout(card6PanelLayout);
        card6PanelLayout.setHorizontalGroup(
            card6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card6PanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(card6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
                    .addGroup(card6PanelLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(EmployeeSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card6PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        card6PanelLayout.setVerticalGroup(
            card6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card6PanelLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(card6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(card6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EmployeeSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(kButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        RightPanel.add(card6Panel, "card6");

        card7Panel.setBackground(new java.awt.Color(255, 255, 255));

        SupplyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        SupplyTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        SupplyTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        SupplyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SupplyTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(SupplyTable);

        SearchSupplyTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchSupplyTextFieldKeyReleased(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(106, 197, 254));
        jPanel8.setPreferredSize(new java.awt.Dimension(800, 70));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setText("Tìm kiếm");

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Search_new.jpg"))); // NOI18N

        kButton8.setText("THÊM");
        kButton8.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton8.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton8.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton8.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton8.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton8.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton8.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton8.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton8.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton8ActionPerformed(evt);
            }
        });

        kButton9.setText("XÓA YÊU CẦU QUÁ HẠN");
        kButton9.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton9.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton9.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton9.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton9.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton9.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton9.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton9.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton9.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton9ActionPerformed(evt);
            }
        });

        kButton10.setText("IN THỐNG KÊ");
        kButton10.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        kButton10.setkEndColor(new java.awt.Color(102, 153, 255));
        kButton10.setkForeGround(new java.awt.Color(0, 0, 0));
        kButton10.setkHoverColor(new java.awt.Color(0, 0, 0));
        kButton10.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        kButton10.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        kButton10.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        kButton10.setkSelectedColor(new java.awt.Color(153, 255, 255));
        kButton10.setkStartColor(new java.awt.Color(102, 255, 204));
        kButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton10ActionPerformed(evt);
            }
        });

        test.setBackground(new java.awt.Color(255, 255, 255));
        test.setText("Lọc yêu cầu");
        test.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                testItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout card7PanelLayout = new javax.swing.GroupLayout(card7Panel);
        card7Panel.setLayout(card7PanelLayout);
        card7PanelLayout.setHorizontalGroup(
            card7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card7PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(card7PanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(card7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5)
                    .addGroup(card7PanelLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(SearchSupplyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(card7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(test)
                            .addGroup(card7PanelLayout.createSequentialGroup()
                                .addComponent(kButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(15, 15, 15))
        );
        card7PanelLayout.setVerticalGroup(
            card7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card7PanelLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(card7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card7PanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(card7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SearchSupplyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(kButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(kButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(kButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(card7PanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(test)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        RightPanel.add(card7Panel, "card7");

        javax.swing.GroupLayout Card8InforEmployeePanelLayout = new javax.swing.GroupLayout(Card8InforEmployeePanel);
        Card8InforEmployeePanel.setLayout(Card8InforEmployeePanelLayout);
        Card8InforEmployeePanelLayout.setHorizontalGroup(
            Card8InforEmployeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 820, Short.MAX_VALUE)
        );
        Card8InforEmployeePanelLayout.setVerticalGroup(
            Card8InforEmployeePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );

        RightPanel.add(Card8InforEmployeePanel, "card8");

        ParentPanel.add(RightPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(ParentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Get coordinates.
    int xy, xx;
    private void TopPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TopPanelMouseClicked
        // TODO add your handling code here:
        
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            if (EmployeeScreen.this.getExtendedState() == MAXIMIZED_BOTH) {
                EmployeeScreen.this.setExtendedState(JFrame.NORMAL);
            } else {
                EmployeeScreen.this.setExtendedState(MAXIMIZED_BOTH);
            }
        }
    }//GEN-LAST:event_TopPanelMouseClicked

    private void TopPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TopPanelMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_TopPanelMouseDragged

    
    private void TopPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TopPanelMousePressed
        // TODO add your handling code here:
        xx = evt.getX();
        xy = evt.getY();
        
    }//GEN-LAST:event_TopPanelMousePressed

    private void MinimizeLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinimizeLabelMousePressed
        // TODO add your handling code here:
        EmployeeScreen.this.setState(Frame.ICONIFIED);
        
    }//GEN-LAST:event_MinimizeLabelMousePressed

    private void MaximizeLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MaximizeLabelMousePressed
        // TODO add your handling code here: 
        if (EmployeeScreen.this.getExtendedState() == MAXIMIZED_BOTH) {
            EmployeeScreen.this.setExtendedState(JFrame.NORMAL);
        } else {
            EmployeeScreen.this.setExtendedState(MAXIMIZED_BOTH);
        }
        
    }//GEN-LAST:event_MaximizeLabelMousePressed

    private void CloseLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloseLabelMousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_CloseLabelMousePressed

    private void index1PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index1PanelMousePressed
        // TODO add your handling code here:
        setColor(index1Panel);
        resetColor(index5Panel);
        resetColor(index4Panel);
        resetColor(index2Panel);
        resetColor(index3Panel);
        resetColor(index6Panel);
        resetColor(index7Panel);
        
        ind_index7Panel.setOpaque(false);
        ind_index6Panel.setOpaque(false);
        ind_index1Panel.setOpaque(true);
        ind_index4Panel.setOpaque(false);
        ind_index5Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        ind_index3Panel.setOpaque(false);
        
        cardLayout.show(RightPanel, "card3");

    }//GEN-LAST:event_index1PanelMousePressed

    private void index2PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index2PanelMousePressed
        // TODO add your handling code here:
         setColor(index2Panel);
        resetColor(index5Panel);
        resetColor(index4Panel);
        resetColor(index1Panel);
        resetColor(index3Panel);
        resetColor(index6Panel);
        resetColor(index7Panel);
        
        ind_index7Panel.setOpaque(false);
        ind_index6Panel.setOpaque(false);
        ind_index2Panel.setOpaque(true);
        ind_index4Panel.setOpaque(false);
        ind_index5Panel.setOpaque(false);
        ind_index1Panel.setOpaque(false);
        ind_index3Panel.setOpaque(false);
        
        cardLayout.show(RightPanel, "card5");

    }//GEN-LAST:event_index2PanelMousePressed

    private void index3PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index3PanelMousePressed
        // TODO add your handling code here:
        setColor(index3Panel);
        resetColor(index5Panel);
        resetColor(index4Panel);
        resetColor(index1Panel);
        resetColor(index2Panel);
        resetColor(index6Panel);
        resetColor(index7Panel);
        
        ind_index7Panel.setOpaque(false);
        ind_index6Panel.setOpaque(false);
        ind_index3Panel.setOpaque(true);
        ind_index4Panel.setOpaque(false);
        ind_index5Panel.setOpaque(false);
        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        
        cardLayout.show(RightPanel, "card4");

    }//GEN-LAST:event_index3PanelMousePressed

    private void index4PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index4PanelMousePressed
        // TODO add your handling code here:
        setColor(index4Panel);
        resetColor(index5Panel);
        resetColor(index3Panel);
        resetColor(index1Panel);
        resetColor(index2Panel);
        resetColor(index6Panel);
        resetColor(index7Panel);
        
        ind_index7Panel.setOpaque(false);
        ind_index6Panel.setOpaque(false);
        ind_index4Panel.setOpaque(true);
        ind_index3Panel.setOpaque(false);
        ind_index5Panel.setOpaque(false);
        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        cardLayout.show(RightPanel, "card6");

    }//GEN-LAST:event_index4PanelMousePressed

    private void index5PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index5PanelMousePressed
        // TODO add your handling code here:
         setColor(index5Panel);
        resetColor(index4Panel);
        resetColor(index3Panel);
        resetColor(index1Panel);
        resetColor(index2Panel);
        resetColor(index6Panel);
        resetColor(index7Panel);
        
        ind_index7Panel.setOpaque(false);
        ind_index6Panel.setOpaque(false);
        ind_index5Panel.setOpaque(true);
        ind_index3Panel.setOpaque(false);
        ind_index4Panel.setOpaque(false);
        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);

        cardLayout.show(RightPanel, "card7");
    }//GEN-LAST:event_index5PanelMousePressed

    private void index6PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index6PanelMousePressed
        // TODO add your handling code here:
        setColor(index6Panel);
        resetColor(index4Panel);
        resetColor(index3Panel);
        resetColor(index1Panel);
        resetColor(index2Panel);
        resetColor(index5Panel);
        resetColor(index7Panel);
        
        ind_index7Panel.setOpaque(false);
        ind_index5Panel.setOpaque(false);
        ind_index6Panel.setOpaque(true);
        ind_index3Panel.setOpaque(false);
        ind_index4Panel.setOpaque(false);
        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        
        cardLayout.show(RightPanel, "card2");

    }//GEN-LAST:event_index6PanelMousePressed

    private void index7PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index7PanelMousePressed
        // TODO add your handling code here:
        setColor(index7Panel);
        resetColor(index4Panel);
        resetColor(index3Panel);
        resetColor(index1Panel);
        resetColor(index2Panel);
        resetColor(index6Panel);
        resetColor(index5Panel);
        
        ind_index5Panel.setOpaque(false);
        ind_index6Panel.setOpaque(false);
        ind_index7Panel.setOpaque(true);
        ind_index3Panel.setOpaque(false);
        ind_index4Panel.setOpaque(false);
        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        
       int option = JOptionPane.showConfirmDialog(null, "Bạn sẽ đăng xuất khỏi tài khoản, muốn tiếp tục?",
                    "Thông báo!", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                new LogInNew().setVisible(true);
            } 
        
        

    }//GEN-LAST:event_index7PanelMousePressed

    private void SearchPersonTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchPersonTextFieldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_SearchPersonTextFieldActionPerformed

    private void SearchPersonTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchPersonTextFieldKeyReleased
        // TODO add your handling code here:
        Search(SearchPersonTextField, PersonTable, modelTablePerson, evt);
    }//GEN-LAST:event_SearchPersonTextFieldKeyReleased

    private void SearchPersonTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchPersonTextFieldKeyPressed
        // TODO add your handling code here:
        Search(SearchPersonTextField, PersonTable, modelTablePerson, evt);
    }//GEN-LAST:event_SearchPersonTextFieldKeyPressed

    private void PersonTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PersonTableMouseClicked
        // TODO add your handling code here:
        if(evt.getButton()==MouseEvent.BUTTON3){ 
            int row_point = PersonTable.rowAtPoint(evt.getPoint());
            PersonTable.getSelectionModel().setSelectionInterval(row_point, row_point);
            //popupPersonTable.show(PersonTable, evt.getX(), evt.getY());
            PersonTablePopupMenu.show(PersonTable, evt.getX(), evt.getY());
            }
    }//GEN-LAST:event_PersonTableMouseClicked

    private void DoctorSearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoctorSearchTextFieldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_DoctorSearchTextFieldActionPerformed

    private void DoctorTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DoctorTableKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_DoctorTableKeyReleased

    private void DoctorSearchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DoctorSearchTextFieldKeyReleased
        // TODO add your handling code here:
        Search(DoctorSearchTextField, DoctorTable, modelTableDoctor, evt);
    }//GEN-LAST:event_DoctorSearchTextFieldKeyReleased

    private void DoctorTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DoctorTableMouseClicked
        // TODO add your handling code here:
        if(evt.getButton()==MouseEvent.BUTTON3){ 
            int row_point = DoctorTable.rowAtPoint(evt.getPoint());
            DoctorTable.getSelectionModel().setSelectionInterval(row_point, row_point);
            //popupPersonTable.show(PersonTable, evt.getX(), evt.getY());
            DoctorPopupMenu.show(DoctorTable, evt.getX(), evt.getY());
            }
    }//GEN-LAST:event_DoctorTableMouseClicked

    private void UpdateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateMenuItemActionPerformed
        // TODO add your handling code here:
        SetdataforanotherJframe();
    }//GEN-LAST:event_UpdateMenuItemActionPerformed

    private void DeleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteMenuItemActionPerformed
        // TODO add your handling code here:
        int check=-1;
        DefaultTableModel model = (DefaultTableModel) PersonTable.getModel();
        int selectedRowIndex = PersonTable.getSelectedRow();      
        selectedRowIndex = PersonTable.convertRowIndexToModel(selectedRowIndex);
        check = PersonController.DeletePerson((int) model.getValueAt(selectedRowIndex, 0));
//        setTableManagePerson();
//        resizeColumnWidth(getPersonTable());
        
        if(check==0){
            JOptionPane.showMessageDialog(null, "Xóa thông tin thành công!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
        
        }
        
        if (SearchPersonTextField.getText().equals("") == false) {
            setTableManagePerson();
            resizeColumnWidth(getPersonTable());
            model = (DefaultTableModel) PersonTable.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);

            trs.setRowFilter(RowFilter.regexFilter("^" +SearchPersonTextField.getText().trim(), 0));
            PersonTable.setRowSorter(trs);
        }
        
        
        
    }//GEN-LAST:event_DeleteMenuItemActionPerformed

    private void UpdateDoctorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateDoctorMenuItemActionPerformed
        // TODO add your handling code here:
        SetdataforUpdateDoctorScreen();
        
    }//GEN-LAST:event_UpdateDoctorMenuItemActionPerformed

    private void DeleteDoctorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteDoctorMenuItemActionPerformed
        // TODO add your handling code here:
        int check=-1;
        DefaultTableModel model = (DefaultTableModel) DoctorTable.getModel();
        int selectedRowIndex = DoctorTable.getSelectedRow();      
        selectedRowIndex = DoctorTable.convertRowIndexToModel(selectedRowIndex);
        check = DoctorController.DeleteDoctor((int) model.getValueAt(selectedRowIndex, 0));
        if(check==0){
            JOptionPane.showMessageDialog(null, "Xóa thông tin thành công!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);}
        setTableManageDoctor();
        resizeColumnWidth(getDoctorTable());
    }//GEN-LAST:event_DeleteDoctorMenuItemActionPerformed

    private void UpdateEmployeeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateEmployeeMenuItemActionPerformed
        // TODO add your handling code here:
        SetdataforUpdateEmployeeScreen();
        
    }//GEN-LAST:event_UpdateEmployeeMenuItemActionPerformed

    private void CharityTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CharityTableMouseClicked
        // TODO add your handling code here:
        if(evt.getButton()==MouseEvent.BUTTON3){ 
            int row_point = CharityTable.rowAtPoint(evt.getPoint());
            CharityTable.getSelectionModel().setSelectionInterval(row_point, row_point);
            //popupPersonTable.show(PersonTable, evt.getX(), evt.getY());
            CharityPopupMenu.show(CharityTable, evt.getX(), evt.getY());
            }
    }//GEN-LAST:event_CharityTableMouseClicked

    private void UpdateCharityMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateCharityMenuItemActionPerformed
        // TODO add your handling code here:
        SetdataforUpdateCharityScreen();
    }//GEN-LAST:event_UpdateCharityMenuItemActionPerformed

    private void DeleteCharityPopupMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteCharityPopupMenuActionPerformed
        // TODO add your handling code here:
        int check=-1;
        DefaultTableModel model = (DefaultTableModel) CharityTable.getModel();
        int selectedRowIndex = CharityTable.getSelectedRow();      
        selectedRowIndex = CharityTable.convertRowIndexToModel(selectedRowIndex);
        check = CharityController.DeleteCharity((int) model.getValueAt(selectedRowIndex, 0));
        setTableManageCharity();
        resizeColumnWidth(getCharityTable());
        if(check==0){
            JOptionPane.showMessageDialog(null, "Xóa thông tin thành công!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);}
        setTableManageCharity();
        resizeColumnWidth(getCharityTable());
    }//GEN-LAST:event_DeleteCharityPopupMenuActionPerformed

    private void CharitySearchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CharitySearchTextFieldKeyReleased
        // TODO add your handling code here:
        Search(CharitySearchTextField, CharityTable, modelTableCharity, evt);
    }//GEN-LAST:event_CharitySearchTextFieldKeyReleased

    private void EmployeeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmployeeTableMouseClicked
        // TODO add your handling code here:
        if(evt.getButton()==MouseEvent.BUTTON3){ 
            int row_point = EmployeeTable.rowAtPoint(evt.getPoint());
            EmployeeTable.getSelectionModel().setSelectionInterval(row_point, row_point);
            //popupPersonTable.show(PersonTable, evt.getX(), evt.getY());
            EmployeePopupMenu.show(EmployeeTable, evt.getX(), evt.getY());
            }
    }//GEN-LAST:event_EmployeeTableMouseClicked

    private void DeleteEmployeeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteEmployeeMenuItemActionPerformed
        // TODO add your handling code here:
        int check=-1;
        DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
        int selectedRowIndex = EmployeeTable.getSelectedRow();      
        selectedRowIndex = EmployeeTable.convertRowIndexToModel(selectedRowIndex);
        
        System.out.println(model.getValueAt(selectedRowIndex, 1));
        if (model.getValueAt(selectedRowIndex, 1).equals(usernameempcur)) {
            JOptionPane.showMessageDialog(null, "Không thể xóa thông tin!",
                    "Thông báo!", JOptionPane.WARNING_MESSAGE);
        } else {
            check = EmployeeController.DeleteEmployee((int) model.getValueAt(selectedRowIndex, 0));
            setTableManageEmployee();
            resizeColumnWidth(getEmployeeTable());
            
        };
        
        if(check==0){
            JOptionPane.showMessageDialog(null, "Xóa thông tin thành công!",
                        "Thông báo!", JOptionPane.OK_OPTION);}
        setTableManageEmployee();
        resizeColumnWidth(getEmployeeTable());
    }//GEN-LAST:event_DeleteEmployeeMenuItemActionPerformed

    private void EmployeeSearchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EmployeeSearchTextFieldKeyReleased
        // TODO add your handling code here:
        Search(EmployeeSearchTextField, EmployeeTable, modelTableEmployee, evt);
    }//GEN-LAST:event_EmployeeSearchTextFieldKeyReleased

    private void DeleteSupplyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteSupplyMenuItemActionPerformed
        // TODO add your handling code here:
        int check =-1;
        
        DefaultTableModel model = (DefaultTableModel) SupplyTable.getModel();
        int selectedRowIndex = SupplyTable.getSelectedRow();      
        selectedRowIndex = SupplyTable.convertRowIndexToModel(selectedRowIndex);
        check =SupplyController.DeleteSupply((int) model.getValueAt(selectedRowIndex, 0));
        
        setTableManageSupply();
        resizeColumnWidth(SupplyTable);

        
        if (check == 0) {
            JOptionPane.showMessageDialog(null, "Xóa thông tin thành công!",
                    "Thông báo!", JOptionPane.INFORMATION_MESSAGE);

            if (SearchSupplyTextField.getText().equals("") == false) {
                TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);

                trs.setRowFilter(RowFilter.regexFilter("^" + SearchSupplyTextField.getText() + "$", 0));
                SupplyTable.setRowSorter(trs);
            }
            
            
        }
        
    }//GEN-LAST:event_DeleteSupplyMenuItemActionPerformed

    private void EmployeeSearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeSearchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmployeeSearchTextFieldActionPerformed

    private void SearchSupplyTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchSupplyTextFieldKeyReleased
        // TODO add your handling code here:
         Search(SearchSupplyTextField, SupplyTable, modelTableSupply, evt);
    }//GEN-LAST:event_SearchSupplyTextFieldKeyReleased

    private void SupplyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SupplyTableMouseClicked
        // TODO add your handling code here:
        if(evt.getButton()==MouseEvent.BUTTON3){ 
            int row_point = SupplyTable.rowAtPoint(evt.getPoint());
            SupplyTable.getSelectionModel().setSelectionInterval(row_point, row_point);
            //popupPersonTable.show(PersonTable, evt.getX(), evt.getY());
            SupplyPopupMenu.show(SupplyTable, evt.getX(), evt.getY());
            }
    }//GEN-LAST:event_SupplyTableMouseClicked

    private void UpdateSupplyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateSupplyMenuItemActionPerformed
        // TODO add your handling code here:
        SetdataforUpdateSupplyScreen();
    }//GEN-LAST:event_UpdateSupplyMenuItemActionPerformed

    private void VerifySupplyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerifySupplyMenuItemActionPerformed
        // TODO add your handling code here:
        int check =-1;
        DefaultTableModel model = (DefaultTableModel) SupplyTable.getModel();
        int selectedRowIndex = SupplyTable.getSelectedRow();      
        selectedRowIndex = SupplyTable.convertRowIndexToModel(selectedRowIndex);
        check = SupplyController.VerifySupply((int) model.getValueAt(selectedRowIndex, 0));
        setTableManageSupply();
        resizeColumnWidth(getSupplyTable());
        
        if(check==0){
            JOptionPane.showMessageDialog(null, "Xác thực thành công thông tin thành công!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);}
        setTableManageSupply();
        resizeColumnWidth(getSupplyTable());
    }//GEN-LAST:event_VerifySupplyMenuItemActionPerformed

    private void DenySupplyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DenySupplyMenuItemActionPerformed
        // TODO add your handling code here:
        int check =-1;
        DefaultTableModel model = (DefaultTableModel) SupplyTable.getModel();
        int selectedRowIndex = SupplyTable.getSelectedRow();      
        selectedRowIndex = SupplyTable.convertRowIndexToModel(selectedRowIndex);
        check = SupplyController.DenySupply((int) model.getValueAt(selectedRowIndex, 0));
        setTableManageSupply();
        resizeColumnWidth(getSupplyTable());
        
        if(check==0){
            JOptionPane.showMessageDialog(null, "Hủy yêu cầu thành công!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);}
        setTableManageSupply();
        resizeColumnWidth(getSupplyTable());
        
            
    }//GEN-LAST:event_DenySupplyMenuItemActionPerformed

    private void CharitySearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CharitySearchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CharitySearchTextFieldActionPerformed

    private void testItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_testItemStateChanged
        // TODO add your handling code here:
        //if(evt.getStateChange()==1)
            FilterSupply(test, SupplyTable, modelTableSupply);
            
    }//GEN-LAST:event_testItemStateChanged

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
        // TODO add your handling code here:
        SetdataforanotherJframeadd();
    }//GEN-LAST:event_kButton1ActionPerformed

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed
        // TODO add your handling code here:
        SetdataforanotherJframeaddhotline();
    }//GEN-LAST:event_kButton2ActionPerformed

    private void kButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton3ActionPerformed
        // TODO add your handling code here:
        exportDoctorToPdf();
    }//GEN-LAST:event_kButton3ActionPerformed

    private void kButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton4ActionPerformed
        // TODO add your handling code here:
        SetdataforAddDoctorScreen();
    }//GEN-LAST:event_kButton4ActionPerformed

    private void kButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton6ActionPerformed
        // TODO add your handling code here:
        SetdataforAddCharityScreen();
    }//GEN-LAST:event_kButton6ActionPerformed

    private void kButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton5ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) CharityTable.getModel();
        int selectedRowIndex = CharityTable.getSelectedRow();      
        selectedRowIndex = CharityTable.convertRowIndexToModel(selectedRowIndex);
        
        exportCharityMarkToPdf((int) model.getValueAt(selectedRowIndex, 0));
    }//GEN-LAST:event_kButton5ActionPerformed

    private void kButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton7ActionPerformed
        // TODO add your handling code here:
        SetdataforAddEmployeeScreen(); 
    }//GEN-LAST:event_kButton7ActionPerformed

    private void kButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton9ActionPerformed
        // TODO add your handling code here:
        int check =-1;
        check = SupplyController.DeleteOverdueSupply();
        setTableManageSupply();
        resizeColumnWidth(getSupplyTable());
        
        if(check==0){
            JOptionPane.showMessageDialog(null, "Xóa yêu cầu quá hạn thành công!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);}
        setTableManageSupply();
        resizeColumnWidth(getSupplyTable());
    }//GEN-LAST:event_kButton9ActionPerformed

    private void kButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton10ActionPerformed
        // TODO add your handling code here:
        exportSupplyToPdf();
    }//GEN-LAST:event_kButton10ActionPerformed

    private void kButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton8ActionPerformed
        // TODO add your handling code here:
        SetdataforAddSupplyScreen();
    }//GEN-LAST:event_kButton8ActionPerformed
  
    //Set color for Jpanel being clicked
    void setColor(JPanel panel) {
        panel.setBackground(new Color(106,180,254));
    }

    //reset color for all Jpanel except Jpanel being 
    void resetColor(JPanel panel) {
        panel.setBackground(new Color(106,197,254));
    }
    
    //set desing for Jtable
    public void SetTabel(JTable table) {
        
        //table.setBackground(new Color(0,0,0,0));
        
        //table.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(32, 136, 203));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setRowHeight(30);
        //table.set
        
        //table.setAutoCreateRowSorter(true);
       
    }
    
    //Get Operating System
    public static OSType getOSType() {
        if (detectedOS == null) {
            String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((os.contains("mac")) || os.indexOf("darwinf") >= 0) {
                detectedOS = OSType.MacOS;
            } else {
                if (os.contains("win")) {
                    detectedOS = OSType.Windows;
                } else if (os.contains("nux")) {
                    detectedOS = OSType.Linux;
                }
            }

        }
        return detectedOS;

    }

    //Check Operating System
    public void CheckOSType() {
        EmployeeScreen.this.getRootPane().setBorder(new LineBorder(new Color(76, 41, 211)));
        TitleLabel.setText(this.getTitle());
        cardLayout = (CardLayout) RightPanel.getLayout();

        if (getOSType() == OSType.MacOS) {
            TopPanel.remove(TitlePanel);
            TopPanel.remove(RightPanel);

            TopPanel.add(TitlePanel, BorderLayout.EAST);
            TopPanel.add(ActionsPanel, BorderLayout.WEST);

            ActionsPanel.remove(CloseLabel);
            ActionsPanel.remove(MaximizeLabel);
            ActionsPanel.remove(MinimizeLabel);

            ActionsPanel.add(CloseLabel);
            ActionsPanel.add(MaximizeLabel);
            ActionsPanel.add(MinimizeLabel);

            TitlePanel.remove(TitleLabel);
            TitlePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 8));
            TitlePanel.add(TitleLabel);

        }
        if (getOSType() == OSType.Windows) {
            TopPanel.remove(TitlePanel);
            TopPanel.remove(RightPanel);

            TopPanel.add(TitlePanel, BorderLayout.WEST);
            TopPanel.add(ActionsPanel, BorderLayout.EAST);

            ActionsPanel.remove(CloseLabel);
            ActionsPanel.remove(MaximizeLabel);
            ActionsPanel.remove(MinimizeLabel);

            ActionsPanel.add(MinimizeLabel);
            ActionsPanel.add(MaximizeLabel);
            ActionsPanel.add(CloseLabel);

            TitlePanel.remove(TitleLabel);
            TitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 8));
            TitlePanel.add(TitleLabel);
        }
           
        }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ActionsPanel;
    private javax.swing.JLabel AddressLabel;
    private javax.swing.JPanel Card8InforEmployeePanel;
    private javax.swing.JPopupMenu CharityPopupMenu;
    private javax.swing.JTextField CharitySearchTextField;
    private javax.swing.JTable CharityTable;
    private javax.swing.JLabel CloseLabel;
    private javax.swing.JMenuItem DeleteCharityPopupMenu;
    private javax.swing.JMenuItem DeleteDoctorMenuItem;
    private javax.swing.JMenuItem DeleteEmployeeMenuItem;
    private javax.swing.JMenuItem DeleteMenuItem;
    private javax.swing.JMenuItem DeleteSupplyMenuItem;
    private javax.swing.JMenuItem DenySupplyMenuItem;
    private javax.swing.JPopupMenu DoctorPopupMenu;
    private javax.swing.JTextField DoctorSearchTextField;
    private javax.swing.JTable DoctorTable;
    private javax.swing.JPopupMenu EmployeePopupMenu;
    private javax.swing.JTextField EmployeeSearchTextField;
    private javax.swing.JTable EmployeeTable;
    private javax.swing.JLabel FemaleLabel;
    private javax.swing.JLabel GenderLabel;
    private javax.swing.JLabel MaleLabel;
    private javax.swing.JLabel MaximizeLabel;
    private javax.swing.JLabel MinimizeLabel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JPanel ParentPanel;
    private javax.swing.JTable PersonTable;
    private javax.swing.JPopupMenu PersonTablePopupMenu;
    private javax.swing.JLabel PhoneLabel;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JLabel RoleLabel;
    private javax.swing.JTextField SearchPersonTextField;
    private javax.swing.JTextField SearchSupplyTextField;
    private javax.swing.JLabel StartdateLabel;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JPopupMenu SupplyPopupMenu;
    private javax.swing.JTable SupplyTable;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JPanel TitlePanel;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JMenuItem UpdateCharityMenuItem;
    private javax.swing.JMenuItem UpdateDoctorMenuItem;
    private javax.swing.JMenuItem UpdateEmployeeMenuItem;
    private javax.swing.JMenuItem UpdateMenuItem;
    private javax.swing.JMenuItem UpdateSupplyMenuItem;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JMenuItem VerifySupplyMenuItem;
    private javax.swing.JPanel card2Panel;
    private javax.swing.JPanel card3Panel;
    private javax.swing.JPanel card4Panel;
    private javax.swing.JPanel card5CharityPanel;
    private javax.swing.JPanel card6Panel;
    private javax.swing.JPanel card7Panel;
    private com.k33ptoo.utils.ComponentResizerUtil componentResizerUtil1;
    private javax.swing.JPanel ind_index1Panel;
    private javax.swing.JPanel ind_index2Panel;
    private javax.swing.JPanel ind_index3Panel;
    private javax.swing.JPanel ind_index4Panel;
    private javax.swing.JPanel ind_index5Panel;
    private javax.swing.JPanel ind_index6Panel;
    private javax.swing.JPanel ind_index7Panel;
    private javax.swing.JPanel index1Panel;
    private javax.swing.JPanel index2Panel;
    private javax.swing.JPanel index3Panel;
    private javax.swing.JPanel index4Panel;
    private javax.swing.JPanel index5Panel;
    private javax.swing.JPanel index6Panel;
    private javax.swing.JPanel index7Panel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private com.k33ptoo.components.KButton kButton1;
    private com.k33ptoo.components.KButton kButton10;
    private com.k33ptoo.components.KButton kButton11;
    private com.k33ptoo.components.KButton kButton2;
    private com.k33ptoo.components.KButton kButton3;
    private com.k33ptoo.components.KButton kButton4;
    private com.k33ptoo.components.KButton kButton5;
    private com.k33ptoo.components.KButton kButton6;
    private com.k33ptoo.components.KButton kButton7;
    private com.k33ptoo.components.KButton kButton8;
    private com.k33ptoo.components.KButton kButton9;
    private javax.swing.JCheckBox test;
    // End of variables declaration//GEN-END:variables
}
