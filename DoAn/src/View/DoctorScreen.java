/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Process.AdvisoryController;
import Model.AdvisoryTableModel;
import Model.Doctor;
import View.ChangeValue;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
public class DoctorScreen extends javax.swing.JFrame {

    
    private CardLayout cardLayout;

    public enum OSType {
        Windows, MacOS, Linux
    }

    private static OSType detectedOS;

    //Biến cần thiết cho màn hình bác sĩ
    private AdvisoryController adcon;
    
    //Biến dùng cho màn hình tìm kiếm yêu cầu
    private ArrayList<String> ComboboxProvinceList;
    private ArrayList<HashMap> AdvisoryList;
    private DefaultTableModel AdvisoryTableModel;
    
    //Biến dùng cho màn hình tư vấn yêu cầu đã nhận
    private ArrayList<String> ComboboxProvinceWaitList;
    private ArrayList<HashMap> WaitAdvisoryList;
    private DefaultTableModel WaitAdvisoryTableModel;
    
    private final Doctor doctor;

        //Màn hình tìm kiếm
    //Ham nay duoc goi trong init component, check phia duoi 
    //Hàm cài bảng 
    private DefaultTableModel setAdvisoryTableModel() 
    {
        AdvisoryList = adcon.getAdvisory();
        AdvisoryTableModel = new AdvisoryTableModel().setAdvisoryTable(AdvisoryList);
        //Chỉnh cho table chỉ chọn được 1 dòng 1 thời điểm, không chọn nhiều dòng được
        AdvisoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return AdvisoryTableModel;
    }
    
    //Hàm chỉnh size header và column cho table 
    private void setAdvisoryTableSize()
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
    
    //Từ đây xuống dưới là cài đặt bằng CSDL, xử lý sự kiện
    //Hàm lấy tất cả tỉnh thành phố có người yêu cầu tư ván
    private void getComboboxItemForSearchScreen()
    {
        DefaultComboBoxModel<String> ProvinceComboBoxModel = new DefaultComboBoxModel();
        ProvinceComboBoxModel.addElement("Tất cả tỉnh/thành phố");
        ComboboxProvinceList=adcon.getProvinceFromAd();
        for(String i : ComboboxProvinceList)
            ProvinceComboBoxModel.addElement(i);
        ProvinceComboBox.setModel(ProvinceComboBoxModel);
    }
    
    //Xử lý sự kiện combobox
    private void handleComboBoxEventForSearchScreen()
    {
        ProvinceComboBox.addItemListener(e->
        {
            if(e.getStateChange()==ItemEvent.SELECTED)
            {
                if(0!=ProvinceComboBox.getSelectedIndex())
                {
                    refreshForSearchScreen();
                    //Dung để thực hiện xử lý truy xuất đồng thời 
                    //Demo phantom read
                    adcon.getAdvisoryTransaction(AdvisoryTable,AdvisoryTableModel,AdvisoryList);
                    //Hàm phía dưới là hàm bình thường nếu không muốn truy xuất đồng thời
//                    AdvisoryTable.setModel(setAdvisoryTableModel());
//                    setAdvisoryTableSize();
                }
                else
                {
                    refreshForSearchScreen();
                    String provinceString = (String) ProvinceComboBox.getSelectedItem();
                    AdvisoryList = adcon.getAdvisoryByProvince(provinceString);
                    AdvisoryTableModel=new AdvisoryTableModel().setAdvisoryTable(AdvisoryList);
                    AdvisoryTable.setModel(AdvisoryTableModel);
                    setAdvisoryTableSize();
                }
            }
        });
    }
    
    //Hàm thêm xử lý sự kiện khi chọn 1 dòng
    private void addSelectRowEventForSearchScreen()
    {
        ListSelectionModel listSelectionModel=AdvisoryTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(e ->{
            try {
                int RowIndex = AdvisoryTable.getSelectedRow();
                HashMap<String, String> Advisory = AdvisoryList.get(RowIndex);
                IdadValueLabel.setText(Advisory.get("idad"));
                NameValueLabel.setText(Advisory.get("name"));
                GenderValueLabel.setText(ChangeValue.Gender(Integer.parseInt(Advisory.get("gender"))));
                YearbirthValueLabel.setText(Advisory.get("yearbirth"));
                ProvinceValueLabel.setText(Advisory.get("province"));
                CreatedValueLabel.setText(Advisory.get("created"));
                DetailTextArea.setText(Advisory.get("detail"));
            } catch (IndexOutOfBoundsException ex) {
                //Lỗi này phát sinh khi nạp lại bảng dữ liệu thì chỉ số 
                //index vẫn còn giữ nguyên khiến gây ra lỗi
                AdvisoryTable.getSelectionModel().clearSelection();
            }
        });
    }

    
    //Đặt lại thông tin trên chi tiết yêu cầu là null
    private void refreshForSearchScreen()
    {
        IdadValueLabel.setText("null");
        NameValueLabel.setText("null");
        GenderValueLabel.setText("null");
        YearbirthValueLabel.setText("null");
        ProvinceValueLabel.setText("null");
        CreatedValueLabel.setText("null");
        DetailTextArea.setText("");
    }
    
    //========================================================================
    private DefaultTableModel setWaitAdvisoryTableModel() 
    {
        WaitAdvisoryList = adcon.getWaitAdvisory(Integer.toString(doctor.getIddoc()));
        WaitAdvisoryTableModel = new AdvisoryTableModel().setAdvisoryTable(WaitAdvisoryList);
        //Chỉnh cho table chỉ chọn được 1 dòng 1 thời điểm, không chọn nhiều dòng được
        WaitAdvisoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return WaitAdvisoryTableModel;
    }
    
    //Hàm chỉnh size header và column cho table 
    private void setWaitAdvisoryTableSize()
    {
        //set column size
        WaitAdvisoryTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        WaitAdvisoryTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        WaitAdvisoryTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        WaitAdvisoryTable.getColumnModel().getColumn(3).setPreferredWidth(40);
        WaitAdvisoryTable.getColumnModel().getColumn(4).setPreferredWidth(50);
        WaitAdvisoryTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        //set header size
        TableCellRenderer rendererFromHeader2 = WaitAdvisoryTable.getTableHeader().getDefaultRenderer();
        JLabel headerLabel2 = (JLabel) rendererFromHeader2;
        headerLabel2.setHorizontalAlignment(JLabel.CENTER);
        JTableHeader header2=WaitAdvisoryTable.getTableHeader();
        Font headerFont2 = new Font("Segoe", Font.PLAIN, 14);
        header2.setFont(headerFont2);
    }
    
    private void getComboboxItemForWaitScreen()
    {
        DefaultComboBoxModel<String> ProvinceComboBoxModel = new DefaultComboBoxModel();
        ProvinceComboBoxModel.addElement("Tất cả tỉnh/thành phố");
        ComboboxProvinceWaitList=adcon.getProvinceFromWaitAd(Integer.toString(doctor.getIddoc()));
        for(String i : ComboboxProvinceWaitList)
            ProvinceComboBoxModel.addElement(i);
        ProvinceWaitAdComboBox.setModel(ProvinceComboBoxModel);
    }
    
    private void handleComboBoxEventForWaitScreen()
    {
        ProvinceWaitAdComboBox.addItemListener(e->
        {
            if(e.getStateChange()==ItemEvent.SELECTED)
            {
                if(0==ProvinceWaitAdComboBox.getSelectedIndex())
                {
                    refreshForWaitScreen();
                    WaitAdvisoryTable.setModel(setWaitAdvisoryTableModel());
                    setWaitAdvisoryTableSize();
                }
                else
                {
                    refreshForWaitScreen();
                    String provinceString = (String) ProvinceWaitAdComboBox.getSelectedItem();
                    WaitAdvisoryList = adcon.getWaitAdvisoryByProvince(Integer.toString(doctor.getIddoc()),provinceString);
                    WaitAdvisoryTableModel=new AdvisoryTableModel().setAdvisoryTable(WaitAdvisoryList);
                    WaitAdvisoryTable.setModel(WaitAdvisoryTableModel);
                    setWaitAdvisoryTableSize();
                }
            }
        });
    }
    
    //Hàm thêm xử lý sự kiện khi chọn 1 dòng
    private void addSelectRowEventForWaitScreen()
    {
        ListSelectionModel listSelectionModel=WaitAdvisoryTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(e ->{
            try {
                int RowIndex = WaitAdvisoryTable.getSelectedRow();
                HashMap<String, String> Advisory = WaitAdvisoryList.get(RowIndex);
                IdadWaitLabel.setText(Advisory.get("idad"));
                NameWaitLabel.setText(Advisory.get("name"));
                GenderWaitLabel.setText(ChangeValue.Gender(Integer.parseInt(Advisory.get("gender"))));
                YearbirthWaitLabel.setText(Advisory.get("yearbirth"));
                ProvinceWaitLabel.setText(Advisory.get("province"));
                CreatedWaitLabel.setText(Advisory.get("created"));
                HeightWaitLabel.setText(Advisory.get("height"));
                WeightWaitLabel.setText(Advisory.get("weight"));
                PhoneWaitLabel.setText(Advisory.get("phone"));
                pmhTextArea.setText(Advisory.get("pastmedicalhistory"));
                DetailWaitTextArea.setText(Advisory.get("detail"));
            } catch (IndexOutOfBoundsException ex) {
                //Lỗi này phát sinh khi nạp lại bảng dữ liệu thì chỉ số 
                //index vẫn còn giữ nguyên khiến gây ra lỗi
                WaitAdvisoryTable.getSelectionModel().clearSelection();
            }
        });
    }

    private void refreshForWaitScreen()
    {
        IdadWaitLabel.setText("null");
        NameWaitLabel.setText("null");
        GenderWaitLabel.setText("null");
        YearbirthWaitLabel.setText("null");
        ProvinceWaitLabel.setText("null");
        CreatedWaitLabel.setText("null");
        HeightWaitLabel.setText("null");
        WeightWaitLabel.setText("null");
        PhoneWaitLabel.setText("null");
        pmhTextArea.setText("");
        DetailWaitTextArea.setText("");
    }
    
    //Màn hình bác sĩ
    //Cài đặt thông tin cá nhân của bác sĩ
    private void setDoctorInformation()
    {
        DoctorNameLabel.setText(doctor.getName());
        UsernameLabel.setText(doctor.getUsername());
        IddocLabel.setText(Integer.toString(doctor.getIddoc()));
        GenderLabel.setText(ChangeValue.Gender(doctor.getGender()));
        PhoneLabel.setText(doctor.getPhone());
        AccademicRankLabel.setText(ChangeValue.AcademicRank(doctor.getAccademicrank()));
        WorkUnitLabel.setText(doctor.getWorkunits());
        SubjectLabel.setText(ChangeValue.Subject(doctor.getSubject()));
        DoctorProvinceLabel.setText(doctor.getProvince());
    }
    public DoctorScreen() {
        adcon = new AdvisoryController();
        doctor=new Doctor(10,"user10","Trần Văn B",0,"0937976863",1,2,"Bệnh viện K","Hà Nội");
        initComponents();
        setLocationRelativeTo(null);
        SetTableColor(AdvisoryTable);
        SetTableColor(WaitAdvisoryTable);
        CheckOSType();
        
        setAdvisoryTableSize();
        getComboboxItemForSearchScreen();
        handleComboBoxEventForSearchScreen();
        setDoctorInformation();
        addSelectRowEventForSearchScreen();

        if(doctor.getGender()==0)
        DoctorAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/doctorNu.png")));

        //=============================================================
        //Bỏ chi tiết yêu cầu đang chờ vào scrollpane
        jScrollPane2.getViewport().setView(DetailPanel);
        
        setWaitAdvisoryTableSize();
        getComboboxItemForWaitScreen();
        handleComboBoxEventForWaitScreen();
        addSelectRowEventForWaitScreen();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TopPanel = new javax.swing.JPanel();
        ActionsPanel = new javax.swing.JPanel();
        MinimizeButton = new javax.swing.JLabel();
        MaximizeButton = new javax.swing.JLabel();
        CloseLabel = new javax.swing.JLabel();
        TitlePanel = new javax.swing.JPanel();
        TitleLabel = new javax.swing.JLabel();
        ParentPanel = new javax.swing.JPanel();
        LeftPanel = new javax.swing.JPanel();
        index1Panel = new javax.swing.JPanel();
        ind_index1Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        MapLabel = new javax.swing.JLabel();
        index2Panel = new javax.swing.JPanel();
        ind_index2Panel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        TuVanLabel = new javax.swing.JLabel();
        index3Panel = new javax.swing.JPanel();
        ind_index3Panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        DoctorInfoLabel = new javax.swing.JLabel();
        index4Panel = new javax.swing.JPanel();
        ind_index4Panel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        LogoutLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        LogoLabel = new javax.swing.JLabel();
        RightPanel = new javax.swing.JPanel();
        FindAdvisoryPanel = new javax.swing.JPanel();
        SearchPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AdvisoryTable = new javax.swing.JTable();
        ProvinceComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        NameValueLabel = new javax.swing.JLabel();
        GenderValueLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        YearbirthValueLabel = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        IdadValueLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        DetailTextArea = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        ProvinceValueLabel = new javax.swing.JLabel();
        CreatedValueLabel = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        AcceptButton = new javax.swing.JLabel();
        RefeshButton = new javax.swing.JLabel();
        DoctorInformationPanel = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        ChangePasswordButton = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        DoctorAvatar = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        DoctorNameLabel = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        UsernameLabel = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        IddocLabel = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        GenderLabel = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        PhoneLabel = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        AccademicRankLabel = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        SubjectLabel = new javax.swing.JLabel();
        WorkUnitLabel = new javax.swing.JLabel();
        DoctorProvinceLabel = new javax.swing.JLabel();
        WaitingAdvisoryPanel = new javax.swing.JPanel();
        WaitingListPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        WaitAdvisoryTable = new javax.swing.JTable();
        ProvinceWaitAdComboBox = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DetailPanel = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        NameWaitLabel = new javax.swing.JLabel();
        GenderWaitLabel = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        YearbirthWaitLabel = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        IdadWaitLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        DetailWaitTextArea = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        ProvinceWaitLabel = new javax.swing.JLabel();
        CreatedWaitLabel = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        FinishButton = new javax.swing.JLabel();
        WaitRefeshButton = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        HeightWaitLabel = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        WeightWaitLabel = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        PhoneWaitLabel = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        pmhTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HỆ THỐNG HỖ TRỢ BỆNH NHÂN COVID-19");
        setLocationByPlatform(true);
        setName("DoctorFrame"); // NOI18N
        setUndecorated(true);

        TopPanel.setBackground(new java.awt.Color(106, 128, 254));
        TopPanel.setPreferredSize(new java.awt.Dimension(1024, 40));
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
        ActionsPanel.setMinimumSize(new java.awt.Dimension(200, 30));
        ActionsPanel.setPreferredSize(new java.awt.Dimension(150, 30));

        MinimizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/web-page.png"))); // NOI18N
        MinimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MinimizeButtonMousePressed(evt);
            }
        });
        ActionsPanel.add(MinimizeButton);

        MaximizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/maximize.png"))); // NOI18N
        MaximizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MaximizeButtonMousePressed(evt);
            }
        });
        ActionsPanel.add(MaximizeButton);

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

        ParentPanel.setPreferredSize(new java.awt.Dimension(1050, 500));
        ParentPanel.setLayout(new java.awt.BorderLayout());

        LeftPanel.setBackground(new java.awt.Color(106, 197, 254));
        LeftPanel.setPreferredSize(new java.awt.Dimension(230, 570));

        index1Panel.setBackground(new java.awt.Color(106, 197, 254));
        index1Panel.setPreferredSize(new java.awt.Dimension(174, 50));
        index1Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index1PanelMousePressed(evt);
            }
        });
        index1Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index1Panel.setOpaque(false);
        ind_index1Panel.setPreferredSize(new java.awt.Dimension(6, 50));

        javax.swing.GroupLayout ind_index1PanelLayout = new javax.swing.GroupLayout(ind_index1Panel);
        ind_index1Panel.setLayout(ind_index1PanelLayout);
        ind_index1PanelLayout.setHorizontalGroup(
            ind_index1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index1PanelLayout.setVerticalGroup(
            ind_index1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        index1Panel.add(ind_index1Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel1.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel1.setText("Tìm kiếm yêu cầu");
        index1Panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 17, -1, 20));

        MapLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/map.png"))); // NOI18N
        index1Panel.add(MapLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        index2Panel.setBackground(new java.awt.Color(106, 197, 254));
        index2Panel.setPreferredSize(new java.awt.Dimension(216, 50));
        index2Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index2PanelMousePressed(evt);
            }
        });
        index2Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index2Panel.setOpaque(false);
        ind_index2Panel.setPreferredSize(new java.awt.Dimension(6, 50));

        javax.swing.GroupLayout ind_index2PanelLayout = new javax.swing.GroupLayout(ind_index2Panel);
        ind_index2Panel.setLayout(ind_index2PanelLayout);
        ind_index2PanelLayout.setHorizontalGroup(
            ind_index2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index2PanelLayout.setVerticalGroup(
            ind_index2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        index2Panel.add(ind_index2Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel3.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel3.setText("Tư vấn cho bệnh nhân");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        index2Panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, 30));

        TuVanLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/tuvan_small.png"))); // NOI18N
        index2Panel.add(TuVanLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        index3Panel.setBackground(new java.awt.Color(106, 197, 254));
        index3Panel.setPreferredSize(new java.awt.Dimension(190, 50));
        index3Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index3PanelMousePressed(evt);
            }
        });
        index3Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index3Panel.setOpaque(false);
        ind_index3Panel.setPreferredSize(new java.awt.Dimension(6, 50));

        javax.swing.GroupLayout ind_index3PanelLayout = new javax.swing.GroupLayout(ind_index3Panel);
        ind_index3Panel.setLayout(ind_index3PanelLayout);
        ind_index3PanelLayout.setHorizontalGroup(
            ind_index3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index3PanelLayout.setVerticalGroup(
            ind_index3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        index3Panel.add(ind_index3Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel2.setText("Thông tin bác sĩ");
        index3Panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 130, 30));

        DoctorInfoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/doctorThongTin.png"))); // NOI18N
        index3Panel.add(DoctorInfoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        index4Panel.setBackground(new java.awt.Color(106, 197, 254));
        index4Panel.setPreferredSize(new java.awt.Dimension(146, 50));
        index4Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                index4PanelMousePressed(evt);
            }
        });
        index4Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_index4Panel.setOpaque(false);
        ind_index4Panel.setPreferredSize(new java.awt.Dimension(6, 50));

        javax.swing.GroupLayout ind_index4PanelLayout = new javax.swing.GroupLayout(ind_index4Panel);
        ind_index4Panel.setLayout(ind_index4PanelLayout);
        ind_index4PanelLayout.setHorizontalGroup(
            ind_index4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        ind_index4PanelLayout.setVerticalGroup(
            ind_index4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        index4Panel.add(ind_index4Panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel7.setFont(new java.awt.Font("Sitka Small", 0, 13)); // NOI18N
        jLabel7.setText("Đăng xuất");
        index4Panel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, 30));

        LogoutLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/logout_small.png"))); // NOI18N
        index4Panel.add(LogoutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setText("THÔNG TIN NGƯỜI DÙNG");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("TƯ VẤN");

        LogoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Logo_small.png"))); // NOI18N

        javax.swing.GroupLayout LeftPanelLayout = new javax.swing.GroupLayout(LeftPanel);
        LeftPanel.setLayout(LeftPanelLayout);
        LeftPanelLayout.setHorizontalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(index1Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(index2Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
            .addComponent(index3Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(index4Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LeftPanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel8))
                    .addGroup(LeftPanelLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(LogoLabel))
                    .addGroup(LeftPanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        LeftPanelLayout.setVerticalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(LogoLabel)
                .addGap(10, 10, 10)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(index1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(index2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addGap(15, 15, 15)
                .addComponent(index3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(index4Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        ParentPanel.add(LeftPanel, java.awt.BorderLayout.LINE_START);

        RightPanel.setOpaque(false);
        RightPanel.setPreferredSize(new java.awt.Dimension(1120, 508));
        RightPanel.setLayout(new java.awt.CardLayout());

        FindAdvisoryPanel.setBackground(new java.awt.Color(255, 255, 255));
        FindAdvisoryPanel.setPreferredSize(new java.awt.Dimension(1350, 508));

        SearchPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("TÌM KIẾM YÊU CẦU TƯ VẤN"));

        AdvisoryTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        AdvisoryTable.setModel(setAdvisoryTableModel());
        jScrollPane1.setViewportView(AdvisoryTable);

        ProvinceComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProvinceComboBox.setMaximumRowCount(5);
        ProvinceComboBox.setModel(new DefaultComboBoxModel<>(ChangeValue.getProvince()));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setText("Chọn tỉnh/thành phố để tìm kiếm");

        javax.swing.GroupLayout SearchPanelLayout = new javax.swing.GroupLayout(SearchPanel);
        SearchPanel.setLayout(SearchPanelLayout);
        SearchPanelLayout.setHorizontalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProvinceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SearchPanelLayout.setVerticalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SearchPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ProvinceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CHI TIẾT YÊU CẦU"));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 485));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setText("Họ và tên");

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel14.setText("Giới tính");

        NameValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NameValueLabel.setText("null");

        GenderValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        GenderValueLabel.setText("null");

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel17.setText("Năm sinh");

        YearbirthValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        YearbirthValueLabel.setText("null");

        jLabel19.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel19.setText("Mã tư vấn");

        IdadValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IdadValueLabel.setText("null");

        DetailTextArea.setColumns(20);
        DetailTextArea.setLineWrap(true);
        DetailTextArea.setRows(5);
        DetailTextArea.setWrapStyleWord(true);
        jScrollPane3.setViewportView(DetailTextArea);

        jLabel21.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel21.setText("Tỉnh/Thành phố");

        ProvinceValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProvinceValueLabel.setText("null");

        CreatedValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CreatedValueLabel.setText("null");

        jLabel24.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel24.setText("Ngày tạo");

        jLabel25.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel25.setText("Mô tả vấn đề cần tư vấn");

        AcceptButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/ChapNhanButton.png"))); // NOI18N
        AcceptButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AcceptButtonMouseClicked(evt);
            }
        });

        RefeshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/refresh.png"))); // NOI18N
        RefeshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RefeshButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(NameValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(GenderValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(YearbirthValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(25, 25, 25)
                                    .addComponent(ProvinceValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(CreatedValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(IdadValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RefeshButton)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(AcceptButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(IdadValueLabel)))
                    .addComponent(RefeshButton))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameValueLabel)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(GenderValueLabel)
                    .addComponent(jLabel17)
                    .addComponent(YearbirthValueLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProvinceValueLabel)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(CreatedValueLabel))
                .addGap(20, 20, 20)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AcceptButton)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout FindAdvisoryPanelLayout = new javax.swing.GroupLayout(FindAdvisoryPanel);
        FindAdvisoryPanel.setLayout(FindAdvisoryPanelLayout);
        FindAdvisoryPanelLayout.setHorizontalGroup(
            FindAdvisoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FindAdvisoryPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(SearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );
        FindAdvisoryPanelLayout.setVerticalGroup(
            FindAdvisoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FindAdvisoryPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(FindAdvisoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        RightPanel.add(FindAdvisoryPanel, "FindAdvisoryPanel");

        DoctorInformationPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel20.setBackground(new java.awt.Color(106, 197, 254));

        ChangePasswordButton.setBackground(new java.awt.Color(106, 197, 254));
        ChangePasswordButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ChangePasswordButton.setPreferredSize(new java.awt.Dimension(200, 50));
        ChangePasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChangePasswordButtonMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setText("Đổi mật khẩu");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/reset-password.png"))); // NOI18N

        javax.swing.GroupLayout ChangePasswordButtonLayout = new javax.swing.GroupLayout(ChangePasswordButton);
        ChangePasswordButton.setLayout(ChangePasswordButtonLayout);
        ChangePasswordButtonLayout.setHorizontalGroup(
            ChangePasswordButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ChangePasswordButtonLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel6)
                .addGap(26, 26, 26)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        ChangePasswordButtonLayout.setVerticalGroup(
            ChangePasswordButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChangePasswordButtonLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(ChangePasswordButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 460, Short.MAX_VALUE)
                .addComponent(ChangePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChangePasswordButton, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("THÔNG TIN TÀI KHOẢN"));

        DoctorAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DoctorAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/doctorNam.png"))); // NOI18N

        jLabel34.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel34.setText("BÁC SĨ");

        DoctorNameLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        DoctorNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DoctorNameLabel.setText("Tên bác sĩ");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(DoctorAvatar))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(jLabel34)))
                        .addGap(0, 75, Short.MAX_VALUE))
                    .addComponent(DoctorNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(DoctorAvatar)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addComponent(DoctorNameLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("THÔNG TIN CÁ NHÂN"));
        jPanel22.setPreferredSize(new java.awt.Dimension(360, 367));

        jLabel36.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel36.setText("Tên tài khoản");

        UsernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        UsernameLabel.setText("null");

        jLabel38.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel38.setText("Mã bác sĩ");

        IddocLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IddocLabel.setText("null");

        jLabel40.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel40.setText("Giới tính");

        GenderLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        GenderLabel.setText("null");

        jLabel42.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel42.setText("Số điện thoại");

        PhoneLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PhoneLabel.setText("null");

        jLabel44.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel44.setText("Học hàm/Học vị");

        AccademicRankLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        AccademicRankLabel.setText("null");

        jLabel46.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel46.setText("Đối tượng");

        jLabel47.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel47.setText("Đơn vị công tác");

        jLabel48.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel48.setText("Tỉnh/ Thành phố");

        SubjectLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SubjectLabel.setText("null");

        WorkUnitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        WorkUnitLabel.setText("null");

        DoctorProvinceLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        DoctorProvinceLabel.setText("null");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel22Layout.createSequentialGroup()
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(IddocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel22Layout.createSequentialGroup()
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(UsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel22Layout.createSequentialGroup()
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(GenderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel22Layout.createSequentialGroup()
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(PhoneLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel22Layout.createSequentialGroup()
                            .addComponent(jLabel46)
                            .addGap(27, 27, 27)
                            .addComponent(SubjectLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(AccademicRankLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(WorkUnitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(DoctorProvinceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(IddocLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(UsernameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(GenderLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(PhoneLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(AccademicRankLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46)
                    .addComponent(SubjectLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(WorkUnitLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(DoctorProvinceLabel))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DoctorInformationPanelLayout = new javax.swing.GroupLayout(DoctorInformationPanel);
        DoctorInformationPanel.setLayout(DoctorInformationPanelLayout);
        DoctorInformationPanelLayout.setHorizontalGroup(
            DoctorInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DoctorInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DoctorInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DoctorInformationPanelLayout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 370, Short.MAX_VALUE))
        );
        DoctorInformationPanelLayout.setVerticalGroup(
            DoctorInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DoctorInformationPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(DoctorInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)


                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))

                .addContainerGap())
        );

        RightPanel.add(DoctorInformationPanel, "DoctorInformationPanel");

        WaitingAdvisoryPanel.setBackground(new java.awt.Color(255, 255, 255));
        WaitingAdvisoryPanel.setPreferredSize(new java.awt.Dimension(1350, 508));

        WaitingListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("DANH SÁCH YÊU CẦU TƯ VẤN ĐÃ NHẬN"));

        WaitAdvisoryTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        WaitAdvisoryTable.setModel(setWaitAdvisoryTableModel());
        jScrollPane4.setViewportView(WaitAdvisoryTable);

        ProvinceWaitAdComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProvinceWaitAdComboBox.setMaximumRowCount(5);
        ProvinceWaitAdComboBox.setModel(new DefaultComboBoxModel<>(ChangeValue.getProvince()));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setText("Chọn tỉnh/thành phố để tìm kiếm");

        javax.swing.GroupLayout WaitingListPanelLayout = new javax.swing.GroupLayout(WaitingListPanel);
        WaitingListPanel.setLayout(WaitingListPanelLayout);
        WaitingListPanelLayout.setHorizontalGroup(
            WaitingListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WaitingListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(WaitingListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProvinceWaitAdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        WaitingListPanelLayout.setVerticalGroup(
            WaitingListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, WaitingListPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ProvinceWaitAdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setPreferredSize(new java.awt.Dimension(300, 485));

        DetailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("CHI TIẾT YÊU CẦU"));
        DetailPanel.setPreferredSize(new java.awt.Dimension(332, 750));

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel16.setText("Họ và tên");

        jLabel18.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel18.setText("Giới tính");

        NameWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NameWaitLabel.setText("null");

        GenderWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        GenderWaitLabel.setText("null");

        jLabel20.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel20.setText("Năm sinh");

        YearbirthWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        YearbirthWaitLabel.setText("null");

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel22.setText("Mã tư vấn");

        IdadWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IdadWaitLabel.setText("null");

        DetailWaitTextArea.setEditable(false);
        DetailWaitTextArea.setColumns(20);
        DetailWaitTextArea.setLineWrap(true);
        DetailWaitTextArea.setRows(5);
        DetailWaitTextArea.setWrapStyleWord(true);
        jScrollPane5.setViewportView(DetailWaitTextArea);

        jLabel23.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel23.setText("Tỉnh/Thành phố");

        ProvinceWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProvinceWaitLabel.setText("null");

        CreatedWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CreatedWaitLabel.setText("null");

        jLabel26.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel26.setText("Ngày tạo");

        jLabel27.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel27.setText("Mô tả vấn đề cần tư vấn");

        FinishButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/HoanThanhButton.png"))); // NOI18N
        FinishButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FinishButtonMouseClicked(evt);
            }
        });

        WaitRefeshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/refresh.png"))); // NOI18N
        WaitRefeshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                WaitRefeshButtonMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel13.setText("Chiều cao(cm)");

        HeightWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        HeightWaitLabel.setText("null");

        jLabel29.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel29.setText("Cân nặng(kg)");

        WeightWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        WeightWaitLabel.setText("null");

        jLabel31.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel31.setText("Số điện thoại liên lạc");

        PhoneWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PhoneWaitLabel.setText("null");

        jLabel33.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel33.setText("Tiền sử bệnh án");

        pmhTextArea.setEditable(false);
        pmhTextArea.setColumns(20);
        pmhTextArea.setLineWrap(true);
        pmhTextArea.setRows(5);
        pmhTextArea.setWrapStyleWord(true);
        jScrollPane6.setViewportView(pmhTextArea);

        javax.swing.GroupLayout DetailPanelLayout = new javax.swing.GroupLayout(DetailPanel);
        DetailPanel.setLayout(DetailPanelLayout);
        DetailPanelLayout.setHorizontalGroup(
            DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DetailPanelLayout.createSequentialGroup()
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                        .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DetailPanelLayout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(FinishButton))
                            .addGroup(DetailPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(DetailPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6)
                            .addComponent(jScrollPane5)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(IdadWaitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(WaitRefeshButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(HeightWaitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(WeightWaitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(NameWaitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(GenderWaitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(YearbirthWaitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(DetailPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(25, 25, 25)
                                            .addComponent(ProvinceWaitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(CreatedWaitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 3, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PhoneWaitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        DetailPanelLayout.setVerticalGroup(
            DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetailPanelLayout.createSequentialGroup()
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DetailPanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(IdadWaitLabel)))
                    .addComponent(WaitRefeshButton))
                .addGap(13, 13, 13)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameWaitLabel)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(GenderWaitLabel)
                    .addComponent(jLabel20)
                    .addComponent(YearbirthWaitLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProvinceWaitLabel)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CreatedWaitLabel)
                    .addComponent(jLabel26))
                .addGap(13, 13, 13)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29)
                        .addComponent(WeightWaitLabel))
                    .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(HeightWaitLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(PhoneWaitLabel))
                .addGap(13, 13, 13)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FinishButton)
                .addGap(7, 7, 7))
        );

        jScrollPane2.setViewportView(DetailPanel);

        javax.swing.GroupLayout WaitingAdvisoryPanelLayout = new javax.swing.GroupLayout(WaitingAdvisoryPanel);
        WaitingAdvisoryPanel.setLayout(WaitingAdvisoryPanelLayout);
        WaitingAdvisoryPanelLayout.setHorizontalGroup(
            WaitingAdvisoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WaitingAdvisoryPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(WaitingListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        WaitingAdvisoryPanelLayout.setVerticalGroup(
            WaitingAdvisoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WaitingAdvisoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(WaitingAdvisoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, WaitingAdvisoryPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(WaitingListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        RightPanel.add(WaitingAdvisoryPanel, "WaitingAdvisoryPanel");

        ParentPanel.add(RightPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(ParentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Get coordinates.
    int xy, xx;
    private void TopPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TopPanelMouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            if (DoctorScreen.this.getExtendedState() == MAXIMIZED_BOTH) {
                DoctorScreen.this.setExtendedState(JFrame.NORMAL);
            } else {
                DoctorScreen.this.setExtendedState(MAXIMIZED_BOTH);
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

    private void MinimizeButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinimizeButtonMousePressed
        // TODO add your handling code here:
        DoctorScreen.this.setState(Frame.ICONIFIED);

    }//GEN-LAST:event_MinimizeButtonMousePressed

    private void MaximizeButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MaximizeButtonMousePressed
        // TODO add your handling code here:

        if (DoctorScreen.this.getExtendedState() == MAXIMIZED_BOTH) {
            DoctorScreen.this.setExtendedState(JFrame.NORMAL);
        } else {
            DoctorScreen.this.setExtendedState(MAXIMIZED_BOTH);
        }

    }//GEN-LAST:event_MaximizeButtonMousePressed

    private void CloseLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CloseLabelMousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_CloseLabelMousePressed

    private void index1PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index1PanelMousePressed
        // TODO add your handling code here:
        setColor(index1Panel);
        resetColor(index2Panel);
        resetColor(index3Panel);
        resetColor(index4Panel);

        ind_index1Panel.setOpaque(true);
        ind_index2Panel.setOpaque(false);
        ind_index3Panel.setOpaque(false);
        ind_index4Panel.setOpaque(false);
        cardLayout.show(RightPanel, "FindAdvisoryPanel");
        this.setPreferredSize(new Dimension(1270, 540));
        this.pack();
    }//GEN-LAST:event_index1PanelMousePressed

    private void index2PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index2PanelMousePressed
        // TODO add your handling code here:
        setColor(index2Panel);
        resetColor(index1Panel);
        resetColor(index3Panel);
        resetColor(index4Panel);

        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(true);
        ind_index3Panel.setOpaque(false);
        ind_index4Panel.setOpaque(false);
        this.setPreferredSize(new Dimension(1290, 540));
        this.pack();
        cardLayout.show(RightPanel, "WaitingAdvisoryPanel");
    }//GEN-LAST:event_index2PanelMousePressed

    private void index3PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index3PanelMousePressed
        // TODO add your handling code here:
        setColor(index3Panel);
        resetColor(index1Panel);
        resetColor(index2Panel);
        resetColor(index4Panel);

        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        ind_index3Panel.setOpaque(true);
        ind_index4Panel.setOpaque(false);
        cardLayout.show(RightPanel, "DoctorInformationPanel");
        this.setPreferredSize(new Dimension(910, 540));
        this.pack();
    }//GEN-LAST:event_index3PanelMousePressed

    private void index4PanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_index4PanelMousePressed
        // TODO add your handling code here:
        setColor(index4Panel);
        resetColor(index1Panel);
        resetColor(index2Panel);
        resetColor(index3Panel);

        ind_index1Panel.setOpaque(false);
        ind_index2Panel.setOpaque(false);
        ind_index3Panel.setOpaque(false);
        ind_index4Panel.setOpaque(true);

    }//GEN-LAST:event_index4PanelMousePressed

    private void RefeshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefeshButtonMouseClicked
        // TODO add your handling code here:
        AdvisoryTable.getSelectionModel().clearSelection();
        refreshForSearchScreen();
    }//GEN-LAST:event_RefeshButtonMouseClicked

    //Hàm xử lý sự kiện nhận yêu cầu tư vấn
    private void AcceptButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AcceptButtonMouseClicked
        // TODO add your handling code here:
        if (IdadValueLabel.getText().equals("null"))
            JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 yêu cầu để nhận",
                    "Lỗi!", JOptionPane.ERROR_MESSAGE);
        else {
            int choice = JOptionPane.showConfirmDialog(null,
                    "Chấp nhận tư vấn yêu cầu này?", "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
            if (choice == 0) {
                int result =adcon.AcceptAdvisory(Integer.toString(doctor.getIddoc()), IdadValueLabel.getText());
                if(result==1)
                {
                    AdvisoryTable.getSelectionModel().clearSelection();
                    refreshForSearchScreen();
                    JOptionPane.showMessageDialog(null, "Nhận thành công");
                    
                    AdvisoryTable.setModel(setAdvisoryTableModel());
                    setAdvisoryTableSize();
                    getComboboxItemForSearchScreen();
                    
                    WaitAdvisoryTable.setModel(setWaitAdvisoryTableModel());
                    setWaitAdvisoryTableSize();
                    getComboboxItemForWaitScreen();
                }
            }
        }
    }//GEN-LAST:event_AcceptButtonMouseClicked

    private void WaitRefeshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WaitRefeshButtonMouseClicked
        // TODO add your handling code here:
        WaitAdvisoryTable.getSelectionModel().clearSelection();
        refreshForWaitScreen();
    }//GEN-LAST:event_WaitRefeshButtonMouseClicked

    private void FinishButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FinishButtonMouseClicked
        // TODO add your handling code here:
        if (IdadWaitLabel.getText().equals("null"))
            JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 yêu cầu để hoàn thành",
                    "Lỗi!", JOptionPane.ERROR_MESSAGE);
        else {
            int choice = JOptionPane.showConfirmDialog(null,
                    "Hoàn thành việc tư vấn yêu cầu này?", "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
            if (choice == 0) {
                int result =adcon.FinishAdvisory(Integer.parseInt(IdadWaitLabel.getText()));
                if(result==1)
                {
                    WaitAdvisoryTable.getSelectionModel().clearSelection();
                    refreshForWaitScreen();
                    JOptionPane.showMessageDialog(null, "Đã hoàn thành việc tư vấn");
                    WaitAdvisoryTable.setModel(setWaitAdvisoryTableModel());
                    setWaitAdvisoryTableSize();
                    getComboboxItemForWaitScreen();
                }
            }
        }
    }//GEN-LAST:event_FinishButtonMouseClicked

    private void ChangePasswordButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChangePasswordButtonMouseClicked
        // TODO add your handling code here:
        ChangePasswordScreen cps=new ChangePasswordScreen(doctor.getUsername());
        cps.setVisible(true);
    }//GEN-LAST:event_ChangePasswordButtonMouseClicked

    //Set color for Jpanel being clicked
    void setColor(JPanel panel) {
        panel.setBackground(new Color(106, 180, 254));
    }

    //reset color for all Jpanel except Jpanel being 
    void resetColor(JPanel panel) {
        panel.setBackground(new Color(106, 197, 254));
    }

    //set desing for Jtable
    public void SetTableColor(JTable table) {

        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(32, 136, 203));
        table.getTableHeader().setForeground(new Color(255, 255, 255));
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
        DoctorScreen.this.getRootPane().setBorder(new LineBorder(new Color(76, 41, 211)));
        TitleLabel.setText(this.getTitle());
        cardLayout = (CardLayout) RightPanel.getLayout();

        if (getOSType() == OSType.MacOS) {
            TopPanel.remove(TitlePanel);
            TopPanel.remove(RightPanel);

            TopPanel.add(TitlePanel, BorderLayout.EAST);
            TopPanel.add(ActionsPanel, BorderLayout.WEST);

            ActionsPanel.remove(CloseLabel);
            ActionsPanel.remove(MaximizeButton);
            ActionsPanel.remove(MinimizeButton);

            ActionsPanel.add(CloseLabel);
            ActionsPanel.add(MaximizeButton);
            ActionsPanel.add(MinimizeButton);

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
            ActionsPanel.remove(MaximizeButton);
            ActionsPanel.remove(MinimizeButton);

            ActionsPanel.add(MinimizeButton);
            ActionsPanel.add(MaximizeButton);
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
            java.util.logging.Logger.getLogger(DoctorScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoctorScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoctorScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoctorScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DoctorScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AccademicRankLabel;
    private javax.swing.JLabel AcceptButton;
    private javax.swing.JPanel ActionsPanel;
    private javax.swing.JTable AdvisoryTable;
    private javax.swing.JPanel ChangePasswordButton;
    private javax.swing.JLabel CloseLabel;
    private javax.swing.JLabel CreatedValueLabel;
    private javax.swing.JLabel CreatedWaitLabel;
    private javax.swing.JPanel DetailPanel;
    private javax.swing.JTextArea DetailTextArea;
    private javax.swing.JTextArea DetailWaitTextArea;
    private javax.swing.JLabel DoctorAvatar;
    private javax.swing.JLabel DoctorInfoLabel;
    private javax.swing.JPanel DoctorInformationPanel;
    private javax.swing.JLabel DoctorNameLabel;
    private javax.swing.JLabel DoctorProvinceLabel;
    private javax.swing.JPanel FindAdvisoryPanel;
    private javax.swing.JLabel FinishButton;
    private javax.swing.JLabel GenderLabel;
    private javax.swing.JLabel GenderValueLabel;
    private javax.swing.JLabel GenderWaitLabel;
    private javax.swing.JLabel HeightWaitLabel;
    private javax.swing.JLabel IdadValueLabel;
    private javax.swing.JLabel IdadWaitLabel;
    private javax.swing.JLabel IddocLabel;
    private javax.swing.JPanel LeftPanel;
    private javax.swing.JLabel LogoLabel;
    private javax.swing.JLabel LogoutLabel;
    private javax.swing.JLabel MapLabel;
    private javax.swing.JLabel MaximizeButton;
    private javax.swing.JLabel MinimizeButton;
    private javax.swing.JLabel NameValueLabel;
    private javax.swing.JLabel NameWaitLabel;
    private javax.swing.JPanel ParentPanel;
    private javax.swing.JLabel PhoneLabel;
    private javax.swing.JLabel PhoneWaitLabel;
    private javax.swing.JComboBox<String> ProvinceComboBox;
    private javax.swing.JLabel ProvinceValueLabel;
    private javax.swing.JComboBox<String> ProvinceWaitAdComboBox;
    private javax.swing.JLabel ProvinceWaitLabel;
    private javax.swing.JLabel RefeshButton;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JLabel SubjectLabel;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JPanel TitlePanel;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JLabel TuVanLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JTable WaitAdvisoryTable;
    private javax.swing.JLabel WaitRefeshButton;
    private javax.swing.JPanel WaitingAdvisoryPanel;
    private javax.swing.JPanel WaitingListPanel;
    private javax.swing.JLabel WeightWaitLabel;
    private javax.swing.JLabel WorkUnitLabel;
    private javax.swing.JLabel YearbirthValueLabel;
    private javax.swing.JLabel YearbirthWaitLabel;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea pmhTextArea;
    // End of variables declaration//GEN-END:variables
}
