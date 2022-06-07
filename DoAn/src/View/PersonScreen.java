/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;


import Model.Account;
import static View.ChangeValue.*;
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

import Model.Advisory;
import Model.AdvisoryPersonTableModel;
import Model.Person;
import Model.Supply;
import Model.SupplyTableModel;
import Process.AdvisoryPersonController;
import static Process.AdvisoryPersonController.getNextValueAdvisory;
import Process.SupplyController;
import static Process.SupplyController.getNextValueSupply;
import static java.lang.Integer.parseInt;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Tran Nhat Sinh
 */
public class PersonScreen extends javax.swing.JFrame {

    /**
     * Creates new form EmployeeScreen
     */
    
    
    private CardLayout cardLayout;
    
    public enum OSType {Windows, MacOS, Linux}
    
    private static OSType detectedOS;
    

    private ArrayList<Advisory> listAdvisory;
    DefaultTableModel modelTableSupply = null;
    DefaultTableModel modelTableAdvisory = null;

    private DefaultTableModel SupplyTableModel;
    private ArrayList<HashMap> SupplyList;
    private ArrayList<HashMap> AdvisoryList;
    
    private SupplyController supcon;
    
    private final Person person;
    
    //Insert data in Jtable     
    //Hien thi du lieu don yeu cau tiep te len bang SupplyTable
    public void setTableManageSupply(int idper) {
        SupplyController supcon=new SupplyController();
        SupplyList=supcon.getSupply(idper);
        modelTableSupply= new SupplyTableModel().setSupplyTable(SupplyList);       
        SupplyTable.setModel(modelTableSupply);
        resizeColumnWidth(SupplyTable);
    }
    
    //Hien thi du lieu don yeu cau tu van len bang AdvisoryTable
    public void setTableManageAdvisory(int idper) {
        AdvisoryPersonController adcon=new AdvisoryPersonController();
        AdvisoryList=adcon.getAdvisory(idper);
        modelTableAdvisory= new AdvisoryPersonTableModel().setAdvisoryTable(AdvisoryList);       
        AdvisoryTable.setModel(modelTableAdvisory);
        resizeColumnWidth(AdvisoryTable);
    }
    //Hien thi du lieu don yeu cau co trang thai da mo len bang SupplyTable
    public void setTableManageSupplyHaveStatus1(int idper) {
        SupplyController supcon=new SupplyController();
        SupplyList=supcon.getSupplyHaveStatus1(idper);
        modelTableSupply= new SupplyTableModel().setSupplyTable(SupplyList);       
        SupplyTable.setModel(modelTableSupply);
        resizeColumnWidth(SupplyTable);
    }
    
    //Hien thi du lieu don yeu cau tu van da mo len bang AdvisoryTable
    public void setTableManageAdvisoryHaveStatus1(int idper) {
        AdvisoryPersonController adcon=new AdvisoryPersonController();
        AdvisoryList=adcon.getAdvisoryHaveStatus1(idper);
        modelTableAdvisory= new AdvisoryPersonTableModel().setAdvisoryTable(AdvisoryList);       
        AdvisoryTable.setModel(modelTableAdvisory);
        resizeColumnWidth(AdvisoryTable);
    }
    //Ham hien thi thong tin chi tiet tiep te cua 1 dong tren table
     public void SetdataforSupplyDetail(){ 
                int RowIndex = SupplyTable.getSelectedRow();
                HashMap<String, String> Supply = SupplyList.get(RowIndex);
                IdsupTextField.setText(Supply.get("idsup"));
                NameTextField.setText(Supply.get("name"));
                CreatedTextField.setText(Supply.get("created"));
                NeedFoodCheckBox.setSelected(getValueCheckBoxBoolean(Integer.valueOf(Supply.get("needfood"))));
                NeedNecessCheckBox.setSelected(getValueCheckBoxBoolean(Integer.valueOf(Supply.get("neednecess"))));
                NeedEquipCheckBox.setSelected(getValueCheckBoxBoolean(Integer.valueOf(Supply.get("needequip"))));
                StatusTextField.setText(ChangeValue.SupplyStatus(Integer.parseInt(Supply.get("status"))));
                DetailTextField.setText(Supply.get("detail"));
    }
    
     //Ham hien thi thong tin chi tiet tu van cua 1 dong tren table
     public void SetdataforAdvisoryDetail(){ 
                int RowIndex = AdvisoryTable.getSelectedRow();
                HashMap<String, String> Advisory = AdvisoryList.get(RowIndex);
                IdadTextField.setText(Advisory.get("idad"));
                NameDoctorTextField.setText(Advisory.get("name"));
                CreatedAdTextField.setText(Advisory.get("created"));
                YearBirthTextField.setText(Advisory.get("yearbirth"));
                HeightTextField.setText(Advisory.get("height"));
                WeightTextField.setText(Advisory.get("weight"));
                PastMedicalHistoryTextField.setText(Advisory.get("pastmedicalhistory"));
                StatusAdTextField.setText(ChangeValue.AdvisoryStatus(Integer.parseInt(Advisory.get("status"))));
                DetailAdTextField.setText(Advisory.get("detail"));
    }
     //Them mot don yeu cau tiep te moi
     public void SetEvenAddSupply(int i){ 
        int check =-1;
        Supply Supply = new Supply();
        //chua được
        Supply.setIdper(i);
        Supply.setNeedfood(getValueCheckBox(NeedFoodCheckBox));
        Supply.setNeednecess(getValueCheckBox(NeedNecessCheckBox));
        Supply.setNeedequip(getValueCheckBox(NeedEquipCheckBox));
        
        Supply.setDetail(DetailTextField.getText());
        
        check =SupplyController.AddSupply(Supply);
        //int check = SupplyController.AddSupply(Supply);
        //EmployeeScreen emp = new EmployeeScreen();
        setTableManageSupply(person.getIdper());
        resizeColumnWidth(SupplyTable);
        
        if(check == 0){ 
            int option =JOptionPane.showConfirmDialog(null, "Thêm thông tin thành công, bạn muốn tiếp tục?",
                    "Thông báo!", JOptionPane.YES_NO_OPTION);
            
        }     
    }
     
     public void LimitCharNumber(JTextField txt, java.awt.event.KeyEvent evt, int lenghth_char_exp) {
        String string = txt.getText();
        int length = string.length();

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {
            if (length < lenghth_char_exp) {
                txt.setEditable(true);
            } else {
                txt.setEditable(false);
            }   
        }
        else{
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                txt.setEditable(true);
            } else {
            txt.setEditable(false);
            }
        }
    }
     
     public void LimitChar(JTextArea txt, java.awt.event.KeyEvent evt, int lenghth_char_exp) {
        String string = txt.getText();

        int length = string.length();
            if (length < lenghth_char_exp) {
                txt.setEditable(true);
            } else {
                txt.setEditable(false);
                if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                    txt.setEditable(true);
                } else {
                    txt.setEditable(false);
                }
            }      
    }
     
     
     //Ham them mot yeu cau tu van moi
    public void SetEvenAddAdvisory(int i) {
        int check = -1;
        Advisory Advisory = new Advisory();
        //chua được
        if (YearBirthTextField.getText().equals("") || HeightTextField.getText().equals("") || WeightTextField.getText().equals("")
                 || DetailAdTextField.getText().equals(""))
        {
            JOptionPane.showConfirmDialog(null, "Không dược để trống các thành phần bắt buộc",
                    "Thông báo!", JOptionPane.WARNING_MESSAGE);
        } else{
        Advisory.setIdper(i);
        Advisory.setYearbirth(Integer.parseInt(YearBirthTextField.getText()));
        Advisory.setHeight(Integer.parseInt(HeightTextField.getText()));
        Advisory.setWeight(Integer.parseInt(WeightTextField.getText()));
        Advisory.setPastmedicalhistory(PastMedicalHistoryTextField.getText());
        Advisory.setDetail(DetailAdTextField.getText());

        check = AdvisoryPersonController.AddAdvisory(Advisory);
        //int check = SupplyController.AddSupply(Supply);
        //EmployeeScreen emp = new EmployeeScreen();
        setTableManageAdvisory(person.getIdper());
        resizeColumnWidth(AdvisoryTable);
        }
        if (check == 0)
        {
            int option = JOptionPane.showConfirmDialog(null, "Thêm thông tin thành công, bạn muốn tiếp tục?",
                    "Thông báo!", JOptionPane.YES_NO_OPTION);

        }
    }
    //Ham lam moi cac o text field yeu cau tiep te de dien thong tin moi
   public void RefeshSupplyDetail(){
       IdsupTextField.setText(getNextValueSupply());
       NameTextField.setText("");
       CreatedTextField.setText("");
       NeedFoodCheckBox.setSelected(false);
       NeedNecessCheckBox.setSelected(false);
       NeedEquipCheckBox.setSelected(false);
       StatusTextField.setText("");
       DetailTextField.setText("");
   }
   
   //Ham lam moi cac o text field yeu cau tu van de dien thong tin moi
   public void RefeshAdvisoryDetail(){
       IdadTextField.setText(getNextValueAdvisory());
       NameDoctorTextField.setText("");
       CreatedTextField.setText("");
       StatusAdTextField.setText("");
       YearBirthTextField.setText("");
       HeightTextField.setText("");
       WeightTextField.setText("");
       PastMedicalHistoryTextField.setText("");
       DetailAdTextField.setText("");
   }
    
    //Ham sua don yeu cau tiep te
    public void setEventUpdateSupply(int i){ 
        int check =-1;
        Supply Supply = new Supply();
     
        Supply.setIdsup(parseInt(IdsupTextField.getText()));
        Supply.setNeedfood(getValueCheckBox(NeedFoodCheckBox));
        Supply.setNeednecess(getValueCheckBox(NeedNecessCheckBox));
        Supply.setNeedequip(getValueCheckBox(NeedEquipCheckBox));
        /*
        if(NeedFoodCheckBox.isSelected()==true)
            Supply.setNeedfood(1);
        else
            Supply.setNeedfood(0);
        
        if(NeedNecessCheckBox.isSelected()==true)
            Supply.setNeednecess(1);
        else
            Supply.setNeednecess(0);
        
        if(NeedEquipCheckBox.isSelected()==true)
            Supply.setNeedequip(1);
        else
            Supply.setNeedequip(0);
        */
        Supply.setDetail(DetailTextField.getText());
        
        check = SupplyController.UpdateSupply(Supply);
        //int check = SupplyController.AddSupply(Supply);
        //EmployeeScreen emp = new EmployeeScreen();
        
        if(check ==0){
            JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
        }
        setTableManageSupply(person.getIdper());
        resizeColumnWidth(SupplyTable);
        
     
    }
 //Ham sua don yeu cau tu van
    public void setEventUpdateAdvisory(int i){ 
        int check =-1;
        Advisory Advisory = new Advisory();
        if (YearBirthTextField.getText().equals("") || HeightTextField.getText().equals("") || WeightTextField.getText().equals("")
                 || DetailAdTextField.getText().equals(""))
        {
            JOptionPane.showConfirmDialog(null, "Không dược để trống các thành phần bắt buộc",
                    "Thông báo!", JOptionPane.WARNING_MESSAGE);
        } else{
        Advisory.setIdad(parseInt(IdadTextField.getText()));
        Advisory.setYearbirth(Integer.valueOf(YearBirthTextField.getText().toString()));
        Advisory.setHeight(Integer.valueOf(HeightTextField.getText().toString()));
        Advisory.setWeight(Integer.valueOf(WeightTextField.getText().toString()));
        Advisory.setPastmedicalhistory(PastMedicalHistoryTextField.getText());
        Advisory.setDetail(DetailAdTextField.getText());
        }
        /*
        if(NeedFoodCheckBox.isSelected()==true)
            Supply.setNeedfood(1);
        else
            Supply.setNeedfood(0);
        
        if(NeedNecessCheckBox.isSelected()==true)
            Supply.setNeednecess(1);
        else
            Supply.setNeednecess(0);
        
        if(NeedEquipCheckBox.isSelected()==true)
            Supply.setNeedequip(1);
        else
            Supply.setNeedequip(0);
        */
        
        check = AdvisoryPersonController.UpdateAdvisory(Advisory);
        //int check = SupplyController.AddSupply(Supply);
        //EmployeeScreen emp = new EmployeeScreen();
        
        if(check ==0){
            JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
        }
        setTableManageAdvisory(person.getIdper());
        resizeColumnWidth(AdvisoryTable);
        
     
    }
    //Serach by 
    public void Search(JTextField txt, JTable table, DefaultTableModel model, java.awt.event.KeyEvent e) { 
        String ID = txt.getText();
        model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        //"(?i)" using for NON Case Sensitive
        //0is cloumn's number is filtered
        //if(ID_PER.equals(""))
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
            trs.setRowFilter(RowFilter.regexFilter("^"+ID+"$", 0));
        else
            //table.setRowSorter(trs);
            trs.setRowFilter(RowFilter.regexFilter(ID.trim(), 0)); 

        table.setRowSorter(trs);
    }
    
       //Function to resize cloumn Width fit context in Jtable
    public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 80; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +5 , width);
        }
        if(width > 600)
            width=600;
        columnModel.getColumn(column).setPreferredWidth(width);
    }

}
    private void setPersonInformation()
    {
        NameLabel.setText(person.getName());
        UsernameLabel.setText(person.getUsername());
        IdperLabel.setText(Integer.toString(person.getIdper()));
        GenderLabel.setText(ChangeValue.Gender(person.getGender()));
        PhoneLabel.setText(person.getPhone());
        ProvinceLabel.setText(person.getProvince());
        DistrictLabel.setText(person.getDistrict());
        TownLabel.setText(person.getTown());
        AddressLabel.setText(person.getAddress());
        StatusLabel.setText(ChangeValue.PersonStatus(person.getStatus()));
    }
    public PersonScreen() {
        initComponents();
        person = new Person(2,"user32","Phạm Duy Anh",0,"0962475039","Gia Lai","Chư Sê","Chư Sê","904B Hùng Vương",0);
      
        SetTabel(AdvisoryTable);
        SetTabel(SupplyTable);
        CheckOSType();
        
        setTableManageSupply(person.getIdper());
        setTableManageAdvisory(person.getIdper());
        
        setPersonInformation();
        resizeColumnWidth(AdvisoryTable);
        resizeColumnWidth(SupplyTable);
        
     }   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SupplyPopupMenu = new javax.swing.JPopupMenu();
        UpdateSupplyMenuItem = new javax.swing.JMenuItem();
        DeleteSupplyMenuItem = new javax.swing.JMenuItem();
        AdvisoryPopupMenu = new javax.swing.JPopupMenu();
        UpdateAdvisoryMenuItem = new javax.swing.JMenuItem();
        DeleteAdvisoryMenuItem = new javax.swing.JMenuItem();
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
        jLabel10 = new javax.swing.JLabel();
        index2Panel = new javax.swing.JPanel();
        ind_index2Panel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        index3Panel = new javax.swing.JPanel();
        ind_index3Panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        index4Panel = new javax.swing.JPanel();
        ind_index4Panel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        RightPanel = new javax.swing.JPanel();
        card1Panel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        IdsupTextField = new javax.swing.JTextField();
        NameTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        StatusTextField = new javax.swing.JTextField();
        CreatedTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        NeedFoodCheckBox = new javax.swing.JCheckBox();
        NeedNecessCheckBox = new javax.swing.JCheckBox();
        NeedEquipCheckBox = new javax.swing.JCheckBox();
        AddSupplyButton = new javax.swing.JButton();
        RefeshSupplyButton = new javax.swing.JButton();
        UpdateSupplyButton = new javax.swing.JButton();
        DeleteSupplyButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        DetailTextField = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        SupplyTable = new javax.swing.JTable();
        SearchSupplyTextField = new javax.swing.JTextField();
        HaveStatus1CheckBox = new javax.swing.JCheckBox();
        card2Panel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        IdadTextField = new javax.swing.JTextField();
        NameDoctorTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        StatusAdTextField = new javax.swing.JTextField();
        CreatedAdTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        AddAdvisoryButton = new javax.swing.JButton();
        RefeshAdvisoryButton = new javax.swing.JButton();
        UpdateAdvisoryButton = new javax.swing.JButton();
        DeleteAdvisoryButton = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        HeightTextField = new javax.swing.JTextField();
        YearBirthTextField = new javax.swing.JTextField();
        WeightTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        PastMedicalHistoryTextField = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        DetailAdTextField = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        AdvisoryTable = new javax.swing.JTable();
        AdvisorySearchTextField = new javax.swing.JTextField();
        HaveStatus1CheckBox1 = new javax.swing.JCheckBox();
        card3Panel = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        NameLabel = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        DistrictLabel = new javax.swing.JLabel();
        TownLabel = new javax.swing.JLabel();
        AddressLabel = new javax.swing.JLabel();
        ProvinceLabel = new javax.swing.JLabel();
        GenderLabel = new javax.swing.JLabel();
        PhoneLabel = new javax.swing.JLabel();
        UsernameLabel = new javax.swing.JLabel();
        IdperLabel = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        card4Panel = new javax.swing.JPanel();

        UpdateSupplyMenuItem.setText("Sửa");
        UpdateSupplyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateSupplyMenuItemActionPerformed(evt);
            }
        });
        SupplyPopupMenu.add(UpdateSupplyMenuItem);

        DeleteSupplyMenuItem.setText("Xóa");
        DeleteSupplyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteSupplyMenuItemActionPerformed(evt);
            }
        });
        SupplyPopupMenu.add(DeleteSupplyMenuItem);

        UpdateAdvisoryMenuItem.setText("Sửa");
        UpdateAdvisoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateAdvisoryMenuItemActionPerformed(evt);
            }
        });
        AdvisoryPopupMenu.add(UpdateAdvisoryMenuItem);

        DeleteAdvisoryMenuItem.setText("Xóa");
        DeleteAdvisoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteAdvisoryMenuItemActionPerformed(evt);
            }
        });
        AdvisoryPopupMenu.add(DeleteAdvisoryMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MÀN HÌNH NGƯỜI DÙNG");
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

        jLabel10.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel10.setText("Yêu cầu tiếp tế");
        index1Panel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

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

        jLabel6.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel6.setText("Yêu cầu tư vấn");
        index2Panel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

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

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel2.setText("Thông tin");
        index3Panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

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

        jLabel7.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel7.setText("Đổi mật khẩu");
        index4Panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setText("THÔNG TIN NGƯỜI DÙNG");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("QUẢN LÝ THÔNG TIN");

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Logo_small.png"))); // NOI18N

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/280330629_550982506429944_1903421778733697740_n.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(index2Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(index3Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(index4Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(index1Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel8))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel21)
                        .addGap(131, 131, 131))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)))
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(index1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(index2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(index3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(index4Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        ParentPanel.add(jPanel2, java.awt.BorderLayout.LINE_START);

        RightPanel.setLayout(new java.awt.CardLayout());

        card1Panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin người cần giúp đỡ"));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Mã yêu cầu tiếp tế");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Tên trung tâm ");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Ngày tạo");

        IdsupTextField.setEnabled(false);

        NameTextField.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Chi tiết");

        StatusTextField.setEnabled(false);

        CreatedTextField.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Trạng thái");

        NeedFoodCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        NeedFoodCheckBox.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        NeedFoodCheckBox.setText("Lương thực");
        NeedFoodCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NeedFoodCheckBoxActionPerformed(evt);
            }
        });

        NeedNecessCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        NeedNecessCheckBox.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        NeedNecessCheckBox.setText("Nhu yếu phẩm");
        NeedNecessCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NeedNecessCheckBoxActionPerformed(evt);
            }
        });

        NeedEquipCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        NeedEquipCheckBox.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        NeedEquipCheckBox.setText("Vật dụng");

        AddSupplyButton.setText("Thêm");
        AddSupplyButton.setPreferredSize(new java.awt.Dimension(76, 22));
        AddSupplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddSupplyButtonActionPerformed(evt);
            }
        });

        RefeshSupplyButton.setText("Làm mới");
        RefeshSupplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefeshSupplyButtonActionPerformed(evt);
            }
        });

        UpdateSupplyButton.setText("Sửa");
        UpdateSupplyButton.setPreferredSize(new java.awt.Dimension(76, 22));
        UpdateSupplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateSupplyButtonActionPerformed(evt);
            }
        });

        DeleteSupplyButton.setText("Xóa");
        DeleteSupplyButton.setPreferredSize(new java.awt.Dimension(76, 22));
        DeleteSupplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteSupplyButtonActionPerformed(evt);
            }
        });

        DetailTextField.setColumns(20);
        DetailTextField.setLineWrap(true);
        DetailTextField.setRows(5);
        DetailTextField.setWrapStyleWord(true);
        DetailTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetailTextFieldKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(DetailTextField);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                                .addComponent(StatusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CreatedTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                    .addComponent(NameTextField)
                                    .addComponent(IdsupTextField))))
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11)
                                    .addComponent(NeedFoodCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                    .addComponent(NeedNecessCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                    .addComponent(NeedEquipCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(AddSupplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DeleteSupplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(UpdateSupplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RefeshSupplyButton)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(IdsupTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(NameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(CreatedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NeedFoodCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NeedNecessCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NeedEquipCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(StatusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddSupplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteSupplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpdateSupplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RefeshSupplyButton))
                .addGap(50, 50, 50))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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

        HaveStatus1CheckBox.setText("Yêu cầu đã mở");
        HaveStatus1CheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HaveStatus1CheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(SearchSupplyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(HaveStatus1CheckBox)
                        .addGap(19, 19, 19))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(HaveStatus1CheckBox)
                    .addComponent(SearchSupplyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout card1PanelLayout = new javax.swing.GroupLayout(card1Panel);
        card1Panel.setLayout(card1PanelLayout);
        card1PanelLayout.setHorizontalGroup(
            card1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        card1PanelLayout.setVerticalGroup(
            card1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1PanelLayout.createSequentialGroup()
                .addGroup(card1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card1PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(card1PanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        RightPanel.add(card1Panel, "card1");

        card2Panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin người cần giúp đỡ"));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Mã yêu cầu tư vấn");

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Tên bác sĩ ");

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Ngày tạo");

        IdadTextField.setEnabled(false);

        NameDoctorTextField.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Chi tiết");

        StatusAdTextField.setEnabled(false);

        CreatedAdTextField.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Trạng thái");

        AddAdvisoryButton.setText("Thêm");
        AddAdvisoryButton.setPreferredSize(new java.awt.Dimension(76, 22));
        AddAdvisoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddAdvisoryButtonActionPerformed(evt);
            }
        });

        RefeshAdvisoryButton.setText("Làm mới");
        RefeshAdvisoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefeshAdvisoryButtonActionPerformed(evt);
            }
        });

        UpdateAdvisoryButton.setText("Sửa");
        UpdateAdvisoryButton.setPreferredSize(new java.awt.Dimension(76, 22));
        UpdateAdvisoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateAdvisoryButtonActionPerformed(evt);
            }
        });

        DeleteAdvisoryButton.setText("Xóa");
        DeleteAdvisoryButton.setPreferredSize(new java.awt.Dimension(76, 22));
        DeleteAdvisoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteAdvisoryButtonActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Tiển sử bệnh án");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Năm sinh");

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Chiều cao");

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("Cân nặng");

        HeightTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HeightTextFieldKeyPressed(evt);
            }
        });

        YearBirthTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YearBirthTextFieldKeyPressed(evt);
            }
        });

        WeightTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WeightTextFieldKeyPressed(evt);
            }
        });

        PastMedicalHistoryTextField.setColumns(20);
        PastMedicalHistoryTextField.setLineWrap(true);
        PastMedicalHistoryTextField.setRows(5);
        PastMedicalHistoryTextField.setWrapStyleWord(true);
        PastMedicalHistoryTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PastMedicalHistoryTextFieldKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(PastMedicalHistoryTextField);

        DetailAdTextField.setColumns(20);
        DetailAdTextField.setLineWrap(true);
        DetailAdTextField.setRows(5);
        DetailAdTextField.setWrapStyleWord(true);
        DetailAdTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetailAdTextFieldKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(DetailAdTextField);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(AddAdvisoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DeleteAdvisoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(UpdateAdvisoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RefeshAdvisoryButton))
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(32, 32, 32)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(HeightTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(IdadTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                            .addComponent(NameDoctorTextField)
                                            .addComponent(CreatedAdTextField)
                                            .addComponent(StatusAdTextField)
                                            .addComponent(WeightTextField)
                                            .addComponent(YearBirthTextField))))
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(IdadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(NameDoctorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(CreatedAdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(StatusAdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(YearBirthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(HeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(WeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddAdvisoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteAdvisoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpdateAdvisoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RefeshAdvisoryButton))
                .addGap(56, 56, 56))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        AdvisoryTable.setModel(new javax.swing.table.DefaultTableModel(
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
        AdvisoryTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        AdvisoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AdvisoryTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(AdvisoryTable);

        AdvisorySearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdvisorySearchTextFieldActionPerformed(evt);
            }
        });
        AdvisorySearchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                AdvisorySearchTextFieldKeyReleased(evt);
            }
        });

        HaveStatus1CheckBox1.setText("Yêu cầu đã mở");
        HaveStatus1CheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HaveStatus1CheckBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(AdvisorySearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(HaveStatus1CheckBox1)
                        .addGap(58, 58, 58))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdvisorySearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HaveStatus1CheckBox1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout card2PanelLayout = new javax.swing.GroupLayout(card2Panel);
        card2Panel.setLayout(card2PanelLayout);
        card2PanelLayout.setHorizontalGroup(
            card2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        card2PanelLayout.setVerticalGroup(
            card2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(card2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(card2PanelLayout.createSequentialGroup()
                        .addGap(0, 14, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        RightPanel.add(card2Panel, "card2");

        card3Panel.setBackground(new java.awt.Color(255, 255, 255));

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
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("THÔNG TIN TÀI KHOẢN"));

        NameLabel.setText("Tên người dùng");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("NGƯỜI DÙNG");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameLabel)
                    .addComponent(jLabel29))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(33, 33, 33)
                .addComponent(NameLabel)
                .addGap(166, 166, 166))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("THÔNG TIN CÁ NHÂN"));

        jLabel25.setText("Mã người dùng");

        jLabel23.setText("Tên tài khoản");

        jLabel24.setText("Giới tính");

        jLabel26.setText("Số điện thoại");

        jLabel27.setText("Tỉnh, thành phố");

        jLabel28.setText("Quận, Huyện, Thị Xã");

        jLabel30.setText("Xã, Phường, Thị trấn");

        jLabel31.setText("Địa chỉ ");

        jLabel32.setText("Tình trạng của tài khoản");

        DistrictLabel.setText("null");

        TownLabel.setText("null");

        AddressLabel.setText("null");

        ProvinceLabel.setText("null");

        GenderLabel.setText("null");

        PhoneLabel.setText("null");

        UsernameLabel.setText("null");

        IdperLabel.setText("null");

        StatusLabel.setText("null");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addGap(33, 33, 33)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StatusLabel)
                    .addComponent(AddressLabel)
                    .addComponent(TownLabel)
                    .addComponent(DistrictLabel)
                    .addComponent(ProvinceLabel)
                    .addComponent(PhoneLabel)
                    .addComponent(GenderLabel)
                    .addComponent(UsernameLabel)
                    .addComponent(IdperLabel))
                .addContainerGap(289, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(IdperLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(UsernameLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(GenderLabel))
                .addGap(23, 23, 23)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(PhoneLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(ProvinceLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(DistrictLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(TownLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(AddressLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(StatusLabel))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout card3PanelLayout = new javax.swing.GroupLayout(card3Panel);
        card3Panel.setLayout(card3PanelLayout);
        card3PanelLayout.setHorizontalGroup(
            card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card3PanelLayout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        card3PanelLayout.setVerticalGroup(
            card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3PanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(card3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        RightPanel.add(card3Panel, "card3");

        javax.swing.GroupLayout card4PanelLayout = new javax.swing.GroupLayout(card4Panel);
        card4Panel.setLayout(card4PanelLayout);
        card4PanelLayout.setHorizontalGroup(
            card4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 820, Short.MAX_VALUE)
        );
        card4PanelLayout.setVerticalGroup(
            card4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        RightPanel.add(card4Panel, "card4");

        ParentPanel.add(RightPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(ParentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Get coordinates.
    int xy, xx;
    private void TopPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TopPanelMouseClicked
        // TODO add your handling code here:
        
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            if (PersonScreen.this.getExtendedState() == MAXIMIZED_BOTH) {
                PersonScreen.this.setExtendedState(JFrame.NORMAL);
            } else {
                PersonScreen.this.setExtendedState(MAXIMIZED_BOTH);
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
        PersonScreen.this.setState(Frame.ICONIFIED);
        
    }//GEN-LAST:event_MinimizeLabelMousePressed

    private void MaximizeLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MaximizeLabelMousePressed
        // TODO add your handling code here:
        
       
        
        if (PersonScreen.this.getExtendedState() == MAXIMIZED_BOTH) {
            PersonScreen.this.setExtendedState(JFrame.NORMAL);
        } else {
            PersonScreen.this.setExtendedState(MAXIMIZED_BOTH);
        }
        
    }//GEN-LAST:event_MaximizeLabelMousePressed

    private void CloseLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloseLabelMousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_CloseLabelMousePressed

    private void index2PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index2PanelMousePressed
        // TODO add your handling code here:
         setColor(index2Panel);
        resetColor(index3Panel);
        resetColor(index4Panel);
        resetColor(index1Panel);
       
        ind_index2Panel.setOpaque(true);
        ind_index1Panel.setOpaque(false);
        ind_index3Panel.setOpaque(false);
        ind_index4Panel.setOpaque(false);

        cardLayout.show(RightPanel, "card2");
    }//GEN-LAST:event_index2PanelMousePressed

    private void index3PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index3PanelMousePressed
        // TODO add your handling code here:
        setColor(index3Panel);
        resetColor(index4Panel);
        resetColor(index1Panel);
        resetColor(index2Panel);
        
        ind_index3Panel.setOpaque(true);
        ind_index4Panel.setOpaque(false);
        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        
        cardLayout.show(RightPanel, "card3");

    }//GEN-LAST:event_index3PanelMousePressed

    private void index4PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index4PanelMousePressed
        // TODO add your handling code here:
        setColor(index4Panel);
        resetColor(index3Panel);
        resetColor(index1Panel);
        resetColor(index2Panel);
        
        ind_index4Panel.setOpaque(true);
        ind_index3Panel.setOpaque(false);
        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        
        cardLayout.show(RightPanel, "card4");
    }//GEN-LAST:event_index4PanelMousePressed

    private void AdvisoryTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdvisoryTableMouseClicked
        SetdataforAdvisoryDetail();
    }//GEN-LAST:event_AdvisoryTableMouseClicked

    private void AdvisorySearchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdvisorySearchTextFieldKeyReleased
        // TODO add your handling code here:
        Search(AdvisorySearchTextField, AdvisoryTable, modelTableAdvisory, evt);
    }//GEN-LAST:event_AdvisorySearchTextFieldKeyReleased

    private void DeleteSupplyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteSupplyMenuItemActionPerformed
        // TODO add your handling code here:
//        DefaultTableModel model = (DefaultTableModel) SupplyTable.getModel();
//        int selectedRowIndex = SupplyTable.getSelectedRow();      
//        selectedRowIndex = SupplyTable.convertRowIndexToModel(selectedRowIndex);
//        SupplyController.DeleteSupply((int) model.getValueAt(selectedRowIndex, 0));
//        setTableManageSupply();
//        resizeColumnWidth(getSupplyTable());
    }//GEN-LAST:event_DeleteSupplyMenuItemActionPerformed

    private void AdvisorySearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdvisorySearchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdvisorySearchTextFieldActionPerformed

    private void SearchSupplyTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchSupplyTextFieldKeyReleased
        // TODO add your handling code here:
         Search(SearchSupplyTextField, SupplyTable, modelTableSupply, evt);
    }//GEN-LAST:event_SearchSupplyTextFieldKeyReleased

    private void AddSupplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddSupplyButtonActionPerformed
        SetEvenAddSupply(person.getIdper());
    }//GEN-LAST:event_AddSupplyButtonActionPerformed

    private void SupplyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SupplyTableMouseClicked
        // TODO add your handling code here:
        SetdataforSupplyDetail();
        
    }//GEN-LAST:event_SupplyTableMouseClicked

    private void UpdateSupplyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateSupplyMenuItemActionPerformed
        // TODO add your handling code here:
//        SetdataforUpdateSupplyScreen();
    }//GEN-LAST:event_UpdateSupplyMenuItemActionPerformed

    private void index1PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index1PanelMousePressed
        // TODO add your handling code here:
        setColor(index1Panel);
        resetColor(index3Panel);
        resetColor(index4Panel);
        resetColor(index2Panel);
        
        ind_index1Panel.setOpaque(true);
        ind_index3Panel.setOpaque(false);
        ind_index4Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        
        cardLayout.show(RightPanel, "card1");
    }//GEN-LAST:event_index1PanelMousePressed

    private void UpdateAdvisoryMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateAdvisoryMenuItemActionPerformed
        // TODO add your handling code here:
//        SetdataforUpdateAdvisoryScreen();
    }//GEN-LAST:event_UpdateAdvisoryMenuItemActionPerformed

    private void DeleteAdvisoryMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteAdvisoryMenuItemActionPerformed
        // TODO add your handling code here:
//        DefaultTableModel model = (DefaultTableModel) AdvisoryTable.getModel();
//        int selectedRowIndex = AdvisoryTable.getSelectedRow();      
//        selectedRowIndex = AdvisoryTable.convertRowIndexToModel(selectedRowIndex);
//        AdvisoryController1.DeleteAdvisory((int) model.getValueAt(selectedRowIndex, 0));
//        setTableManageAdvisory();
//        resizeColumnWidth(getAdvisoryTable());
    }//GEN-LAST:event_DeleteAdvisoryMenuItemActionPerformed

    private void NeedFoodCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NeedFoodCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NeedFoodCheckBoxActionPerformed

    private void DeleteSupplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteSupplyButtonActionPerformed
        // TODO add your handling code here:
        int check=-1;
        DefaultTableModel model = (DefaultTableModel) SupplyTable.getModel();
        int selectedRowIndex = SupplyTable.getSelectedRow();      
        selectedRowIndex = SupplyTable.convertRowIndexToModel(selectedRowIndex);
        check = SupplyController.DeleteSupply(Integer.valueOf(model.getValueAt(selectedRowIndex, 0).toString()) );
        if(check==0){
            JOptionPane.showMessageDialog(null, "Xóa thông tin thành công!",
                        "Thông báo!", JOptionPane.YES_OPTION);}
        setTableManageSupply(person.getIdper());
        resizeColumnWidth(SupplyTable);
        RefeshSupplyDetail();
    }//GEN-LAST:event_DeleteSupplyButtonActionPerformed

    private void HaveStatus1CheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HaveStatus1CheckBoxItemStateChanged
        if(evt.getStateChange()==1)
            setTableManageSupplyHaveStatus1(person.getIdper());
        else
            setTableManageSupply(person.getIdper());
    }//GEN-LAST:event_HaveStatus1CheckBoxItemStateChanged

    private void NeedNecessCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NeedNecessCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NeedNecessCheckBoxActionPerformed

    private void RefeshSupplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefeshSupplyButtonActionPerformed
        // TODO add your handling code here:
        RefeshSupplyDetail();
    }//GEN-LAST:event_RefeshSupplyButtonActionPerformed

    private void UpdateSupplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateSupplyButtonActionPerformed
        setEventUpdateSupply(person.getIdper());
    }//GEN-LAST:event_UpdateSupplyButtonActionPerformed

    private void AddAdvisoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddAdvisoryButtonActionPerformed
        SetEvenAddAdvisory(person.getIdper());
    }//GEN-LAST:event_AddAdvisoryButtonActionPerformed

    private void RefeshAdvisoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefeshAdvisoryButtonActionPerformed
         RefeshAdvisoryDetail();
    }//GEN-LAST:event_RefeshAdvisoryButtonActionPerformed

    private void UpdateAdvisoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateAdvisoryButtonActionPerformed
        setEventUpdateAdvisory(person.getIdper());
    }//GEN-LAST:event_UpdateAdvisoryButtonActionPerformed

    private void DeleteAdvisoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteAdvisoryButtonActionPerformed
         int check=-1;
        DefaultTableModel model = (DefaultTableModel) AdvisoryTable.getModel();
        int selectedRowIndex = AdvisoryTable.getSelectedRow();      
        selectedRowIndex = AdvisoryTable.convertRowIndexToModel(selectedRowIndex);
        check = AdvisoryPersonController.DeleteAdvisory(Integer.valueOf(model.getValueAt(selectedRowIndex, 0).toString()) );
        if(check==0){
            JOptionPane.showMessageDialog(null, "Xóa thông tin thành công!",
                        "Thông báo!", JOptionPane.YES_OPTION);}
        setTableManageAdvisory(person.getIdper());
        resizeColumnWidth(AdvisoryTable);
        RefeshAdvisoryDetail();
    }//GEN-LAST:event_DeleteAdvisoryButtonActionPerformed

    private void HaveStatus1CheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HaveStatus1CheckBox1ItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange()==1)
            setTableManageAdvisoryHaveStatus1(person.getIdper());
        else
            setTableManageAdvisory(person.getIdper());
    }//GEN-LAST:event_HaveStatus1CheckBox1ItemStateChanged

    private void YearBirthTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YearBirthTextFieldKeyPressed
        LimitCharNumber(YearBirthTextField,evt,4);
    }//GEN-LAST:event_YearBirthTextFieldKeyPressed

    private void HeightTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HeightTextFieldKeyPressed
        LimitCharNumber(HeightTextField,evt,3);
    }//GEN-LAST:event_HeightTextFieldKeyPressed

    private void WeightTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WeightTextFieldKeyPressed
        LimitCharNumber(WeightTextField,evt,3);
    }//GEN-LAST:event_WeightTextFieldKeyPressed

    private void PastMedicalHistoryTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PastMedicalHistoryTextFieldKeyPressed
        LimitChar(PastMedicalHistoryTextField,evt,250);
    }//GEN-LAST:event_PastMedicalHistoryTextFieldKeyPressed

    private void DetailAdTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailAdTextFieldKeyPressed
        LimitChar(DetailAdTextField,evt,1000);
    }//GEN-LAST:event_DetailAdTextFieldKeyPressed

    private void DetailTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailTextFieldKeyPressed
        LimitChar(DetailTextField,evt,1000);
    }//GEN-LAST:event_DetailTextFieldKeyPressed

    
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
        
        //PersonTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(32, 136, 203));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setRowHeight(25);
       
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
        PersonScreen.this.getRootPane().setBorder(new LineBorder(new Color(76, 41, 211)));
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
            java.util.logging.Logger.getLogger(PersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PersonScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ActionsPanel;
    private javax.swing.JButton AddAdvisoryButton;
    private javax.swing.JButton AddSupplyButton;
    private javax.swing.JLabel AddressLabel;
    private javax.swing.JPopupMenu AdvisoryPopupMenu;
    private javax.swing.JTextField AdvisorySearchTextField;
    private javax.swing.JTable AdvisoryTable;
    private javax.swing.JLabel CloseLabel;
    private javax.swing.JTextField CreatedAdTextField;
    private javax.swing.JTextField CreatedTextField;
    private javax.swing.JButton DeleteAdvisoryButton;
    private javax.swing.JMenuItem DeleteAdvisoryMenuItem;
    private javax.swing.JButton DeleteSupplyButton;
    private javax.swing.JMenuItem DeleteSupplyMenuItem;
    private javax.swing.JTextArea DetailAdTextField;
    private javax.swing.JTextArea DetailTextField;
    private javax.swing.JLabel DistrictLabel;
    private javax.swing.JLabel GenderLabel;
    private javax.swing.JCheckBox HaveStatus1CheckBox;
    private javax.swing.JCheckBox HaveStatus1CheckBox1;
    private javax.swing.JTextField HeightTextField;
    private javax.swing.JTextField IdadTextField;
    private javax.swing.JLabel IdperLabel;
    private javax.swing.JTextField IdsupTextField;
    private javax.swing.JLabel MaximizeLabel;
    private javax.swing.JLabel MinimizeLabel;
    private javax.swing.JTextField NameDoctorTextField;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JTextField NameTextField;
    private javax.swing.JCheckBox NeedEquipCheckBox;
    private javax.swing.JCheckBox NeedFoodCheckBox;
    private javax.swing.JCheckBox NeedNecessCheckBox;
    private javax.swing.JPanel ParentPanel;
    private javax.swing.JTextArea PastMedicalHistoryTextField;
    private javax.swing.JLabel PhoneLabel;
    private javax.swing.JLabel ProvinceLabel;
    private javax.swing.JButton RefeshAdvisoryButton;
    private javax.swing.JButton RefeshSupplyButton;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JTextField SearchSupplyTextField;
    private javax.swing.JTextField StatusAdTextField;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JTextField StatusTextField;
    private javax.swing.JPopupMenu SupplyPopupMenu;
    private javax.swing.JTable SupplyTable;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JPanel TitlePanel;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JLabel TownLabel;
    private javax.swing.JButton UpdateAdvisoryButton;
    private javax.swing.JMenuItem UpdateAdvisoryMenuItem;
    private javax.swing.JButton UpdateSupplyButton;
    private javax.swing.JMenuItem UpdateSupplyMenuItem;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JTextField WeightTextField;
    private javax.swing.JTextField YearBirthTextField;
    private javax.swing.JPanel card1Panel;
    private javax.swing.JPanel card2Panel;
    private javax.swing.JPanel card3Panel;
    private javax.swing.JPanel card4Panel;
    private javax.swing.JPanel ind_index1Panel;
    private javax.swing.JPanel ind_index2Panel;
    private javax.swing.JPanel ind_index3Panel;
    private javax.swing.JPanel ind_index4Panel;
    private javax.swing.JPanel index1Panel;
    private javax.swing.JPanel index2Panel;
    private javax.swing.JPanel index3Panel;
    private javax.swing.JPanel index4Panel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
