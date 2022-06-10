/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.Charity;
import Model.SupplyTableModel;
import Process.SupplyController;
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
 * @author Tran Nhat Sinh
 */
public class CharityScreen extends javax.swing.JFrame {

    
    private CardLayout cardLayout;

    public enum OSType {
        Windows, MacOS, Linux
    }

    private static OSType detectedOS;

    //Biến cần thiết cho màn hình bác sĩ
    private SupplyController supcon;
    
    //Biến dùng cho màn hình tìm kiếm yêu cầu
    private ArrayList<String> ComboboxProvinceList;
    private ArrayList<HashMap> SupplyList;
    private DefaultTableModel SupplyTableModel;
    
    //Biến dùng cho màn hình tư vấn yêu cầu đã nhận
    private ArrayList<String> ComboboxProvinceWaitList;
    private ArrayList<HashMap> WaitSupplyList;
    private DefaultTableModel WaitSupplyTableModel;
    
    private final Charity charity;

        //Màn hình tìm kiếm
    //Ham nay duoc goi trong init component, check phia duoi 
    //Hàm cài bảng 
    private DefaultTableModel setSupplyTableModel() 
    {
        SupplyList = supcon.getSupplyCharity();
        SupplyTableModel = new SupplyTableModel().setSupplyTableCharity(SupplyList);
        //Chỉnh cho table chỉ chọn được 1 dòng 1 thời điểm, không chọn nhiều dòng được
        SupplyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return SupplyTableModel;
    }
    
    //Hàm chỉnh size header và column cho table 
    private void setSupplyTableSize()
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
    
    //Từ đây xuống dưới là cài đặt bằng CSDL, xử lý sự kiện
    //Hàm lấy tất cả tỉnh thành phố có người yêu cầu tư ván
    private void getComboboxItemForSearchScreen()
    {
        DefaultComboBoxModel<String> ProvinceComboBoxModel = new DefaultComboBoxModel();
        ProvinceComboBoxModel.addElement("Tất cả tỉnh/thành phố");
        ComboboxProvinceList=supcon.getProvinceFromSup();
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
                if(0==ProvinceComboBox.getSelectedIndex())
                {
                    refreshForSearchScreen();
                    //Dung để thực hiện xử lý truy xuất đồng thời 
                    //Demo phantom read
                    supcon.getSupplyTransaction(SupplyTable,SupplyTableModel,SupplyList);
                    //Hàm phía dưới là hàm bình thường nếu không muốn truy xuất đồng thời
//                    SupplyTable.setModel(setSupplyTableModel());
//                    setSupplyTableSize();
                }
                else
                {
                    refreshForSearchScreen();
                    String provinceString = (String) ProvinceComboBox.getSelectedItem();
                    SupplyList = supcon.getSupplyByProvince(provinceString);
                    SupplyTableModel=new SupplyTableModel().setSupplyTableCharity(SupplyList);
                    SupplyTable.setModel(SupplyTableModel);
                    setSupplyTableSize();
                }
            }
        });
    }
    
    //Hàm thêm xử lý sự kiện khi chọn 1 dòng
    private void addSelectRowEventForSearchScreen()
    {
        ListSelectionModel listSelectionModel=SupplyTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(e ->{
            try {
                int RowIndex = SupplyTable.getSelectedRow();
                HashMap<String, String> Supply = SupplyList.get(RowIndex);
                IdsupValueLabel.setText(Supply.get("idsup"));
                NameValueLabel.setText(Supply.get("name"));
                GenderValueLabel.setText(ChangeValue.Gender(Integer.parseInt(Supply.get("gender"))));
                ProvinceValueLabel.setText(Supply.get("province"));
                CreatedValueLabel.setText(Supply.get("created"));
                NeedFoodValueLabel.setText(ChangeValue.NeedSupply(Integer.parseInt(Supply.get("needfood"))));
                NeedNecessValueLabel.setText(ChangeValue.NeedSupply(Integer.parseInt(Supply.get("neednecess"))));
                NeedEquipValueLabel.setText(ChangeValue.NeedSupply(Integer.parseInt(Supply.get("needequip"))));
                DetailTextArea.setText(Supply.get("detail"));
            } catch (IndexOutOfBoundsException ex) {
                //Lỗi này phát sinh khi nạp lại bảng dữ liệu thì chỉ số 
                //index vẫn còn giữ nguyên khiến gây ra lỗi
                SupplyTable.getSelectionModel().clearSelection();
            }
        });
    }

    
    //Đặt lại thông tin trên chi tiết yêu cầu là null
    private void refreshForSearchScreen()
    {
        IdsupValueLabel.setText(" ");
        NameValueLabel.setText(" ");
        GenderValueLabel.setText(" ");
        ProvinceValueLabel.setText(" ");
        CreatedValueLabel.setText(" ");
        NeedFoodValueLabel.setText(" ");
        NeedNecessValueLabel.setText(" ");
        NeedEquipValueLabel.setText(" ");
        DetailTextArea.setText("");
    }
    
    //========================================================================
    private DefaultTableModel setWaitSupplyTableModel() 
    {
        WaitSupplyList = supcon.getWaitSupply(Integer.toString(charity.getIdchar()));
        WaitSupplyTableModel = new SupplyTableModel().setSupplyTableCharity(WaitSupplyList);
        //Chỉnh cho table chỉ chọn được 1 dòng 1 thời điểm, không chọn nhiều dòng được
        WaitSupplyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return WaitSupplyTableModel;
    }
    
    //Hàm chỉnh size header và column cho table 
    private void setWaitSupplyTableSize()
    {
        //set column size
        WaitSupplyTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        WaitSupplyTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        WaitSupplyTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        WaitSupplyTable.getColumnModel().getColumn(3).setPreferredWidth(40);
        
        
        //set header size
        TableCellRenderer rendererFromHeader2 = WaitSupplyTable.getTableHeader().getDefaultRenderer();
        JLabel headerLabel2 = (JLabel) rendererFromHeader2;
        headerLabel2.setHorizontalAlignment(JLabel.CENTER);
        JTableHeader header2=WaitSupplyTable.getTableHeader();
        Font headerFont2 = new Font("Segoe", Font.PLAIN, 14);
        header2.setFont(headerFont2);
    }
    
    private void getComboboxItemForWaitScreen()
    {
        DefaultComboBoxModel<String> ProvinceComboBoxModel = new DefaultComboBoxModel();
        ProvinceComboBoxModel.addElement("Tất cả tỉnh/thành phố");
        ComboboxProvinceWaitList=supcon.getProvinceFromWaitSup(Integer.toString(charity.getIdchar()));
        for(String i : ComboboxProvinceWaitList)
            ProvinceComboBoxModel.addElement(i);
        ProvinceWaitSupComboBox.setModel(ProvinceComboBoxModel);
    }
    
    private void handleComboBoxEventForWaitScreen()
    {
        ProvinceWaitSupComboBox.addItemListener(e->
        {
            if(e.getStateChange()==ItemEvent.SELECTED)
            {
                if(0==ProvinceWaitSupComboBox.getSelectedIndex())
                {
                    refreshForWaitScreen();
                    WaitSupplyTable.setModel(setWaitSupplyTableModel());
                    setWaitSupplyTableSize();
                }
                else
                {
                    refreshForWaitScreen();
                    String provinceString = (String) ProvinceWaitSupComboBox.getSelectedItem();
                    WaitSupplyList = supcon.getWaitSupplyByProvince(Integer.toString(charity.getIdchar()),provinceString);
                    WaitSupplyTableModel=new SupplyTableModel().setSupplyTableCharity(WaitSupplyList);
                    WaitSupplyTable.setModel(WaitSupplyTableModel);
                    setWaitSupplyTableSize();
                }
            }
        });
    }
    
    //Hàm thêm xử lý sự kiện khi chọn 1 dòng
    private void addSelectRowEventForWaitScreen()
    {
        ListSelectionModel listSelectionModel=WaitSupplyTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(e ->{
            try {
                int RowIndex = WaitSupplyTable.getSelectedRow();
                HashMap<String, String> Supply = WaitSupplyList.get(RowIndex);
                IdsupWaitLabel.setText(Supply.get("idsup"));
                NameWaitLabel.setText(Supply.get("name"));
                GenderWaitLabel.setText(ChangeValue.Gender(Integer.parseInt(Supply.get("gender"))));
                ProvinceWaitLabel.setText(Supply.get("province"));
                CreatedWaitLabel.setText(Supply.get("created"));
                NeedFoodWaitLabel.setText(ChangeValue.NeedSupply(Integer.parseInt(Supply.get("needfood"))));
                NeedNecessWaitLabel.setText(ChangeValue.NeedSupply(Integer.parseInt(Supply.get("neednecess"))));
                NeedEquipWaitLabel.setText(ChangeValue.NeedSupply(Integer.parseInt(Supply.get("needequip"))));
                PhoneWaitLabel.setText(Supply.get("phone"));
                DetailWaitTextArea.setText(Supply.get("detail"));
            } catch (IndexOutOfBoundsException ex) {
                //Lỗi này phát sinh khi nạp lại bảng dữ liệu thì chỉ số 
                //index vẫn còn giữ nguyên khiến gây ra lỗi
                WaitSupplyTable.getSelectionModel().clearSelection();
            }
        });
    }

    private void refreshForWaitScreen()
    {
        IdsupWaitLabel.setText(" ");
        NameWaitLabel.setText(" ");
        GenderWaitLabel.setText(" ");
        ProvinceWaitLabel.setText(" ");
        CreatedWaitLabel.setText(" ");
        NeedFoodWaitLabel.setText(" ");
        NeedNecessWaitLabel.setText(" ");
        NeedEquipWaitLabel.setText(" ");
        PhoneWaitLabel.setText(" ");
        DetailWaitTextArea.setText("");
    }
    
    //Màn hình trung tâm 
    //Cài đặt thông tin cá nhân của trung tam
    private void setCharityInformation()
    {       
        CharityNameLabel.setText(charity.getName());
        IdcharLabel.setText(Integer.toString(charity.getIdchar()));
        UsernameLabel.setText(charity.getUsername());
        PhoneLabel.setText(charity.getPhone());
        ProvinceLabel.setText(charity.getProvince());
        DistrictLabel.setText(charity.getDistrict());
        TownLabel.setText(charity.getTown());
        AddressLabel.setText(charity.getAddress());
        HasFoodLabel.setText(Integer.toString(charity.getHasfood()));
        HasNecessLabel.setText(Integer.toString(charity.getHasnecess()));
        HasEquipLabel.setText(Integer.toString(charity.getHasequip()));
        PointLabel.setText(Integer.toString(charity.getPoint()));
    }
    public CharityScreen() {    
        supcon = new SupplyController();
        charity=new Charity(10,"user20","Tiếp Sức","0967596794","Hồ Chí Minh","Gò Vấp","Phường 4", "12 Nguyễn Văn Bảo",0,1,1,60);
        initComponents();
        setLocationRelativeTo(null);
        SetTableColor(SupplyTable);
        SetTableColor(WaitSupplyTable);
        CheckOSType();
        
        setSupplyTableSize();
        getComboboxItemForSearchScreen();
        handleComboBoxEventForSearchScreen();
        setCharityInformation();
        addSelectRowEventForSearchScreen();
        
        setWaitSupplyTableSize();
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
        TiepTeLabel = new javax.swing.JLabel();
        index3Panel = new javax.swing.JPanel();
        ind_index3Panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        CharityInfoLabel = new javax.swing.JLabel();
        index4Panel = new javax.swing.JPanel();
        ind_index4Panel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        LogoutLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        LogoLabel = new javax.swing.JLabel();
        RightPanel = new javax.swing.JPanel();
        FindSupplyPanel = new javax.swing.JPanel();
        SearchPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SupplyTable = new javax.swing.JTable();
        ProvinceComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        NameValueLabel = new javax.swing.JLabel();
        GenderValueLabel = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        IdsupValueLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        DetailTextArea = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        ProvinceValueLabel = new javax.swing.JLabel();
        CreatedValueLabel = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        RefeshButton = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        NeedNecessValueLabel = new javax.swing.JLabel();
        NeedFoodValueLabel = new javax.swing.JLabel();
        NeedEquipValueLabel = new javax.swing.JLabel();
        AcceptButton = new com.k33ptoo.components.KButton();
        WaitingSupplyPanel = new javax.swing.JPanel();
        WaitingListPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        WaitSupplyTable = new javax.swing.JTable();
        ProvinceWaitSupComboBox = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        DetailPanel = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        NameWaitLabel = new javax.swing.JLabel();
        GenderWaitLabel = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        IdsupWaitLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        DetailWaitTextArea = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        ProvinceWaitLabel = new javax.swing.JLabel();
        CreatedWaitLabel = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        WaitRefeshButton = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        NeedFoodWaitLabel = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        NeedNecessWaitLabel = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        PhoneWaitLabel = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        NeedEquipWaitLabel = new javax.swing.JLabel();
        CancelButtonLabel = new com.k33ptoo.components.KButton();
        FinishButton1 = new com.k33ptoo.components.KButton();
        CharityInformationPanel = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        CharityNameLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        PhoneLabel = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        ProvinceLabel = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        DistrictLabel = new javax.swing.JLabel();
        TownLabel = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        AddressLabel = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        HasFoodLabel = new javax.swing.JLabel();
        PointLabel = new javax.swing.JLabel();
        HasNecessLabel = new javax.swing.JLabel();
        HasEquipLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        IdcharLabel = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        UsernameLabel = new javax.swing.JLabel();
        ChangePasswordButton = new com.k33ptoo.components.KButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("\nHỆ THỐNG HỖ TRỢ BỆNH NHÂN COVID-19");
        setLocationByPlatform(true);
        setName("DoctorFrame"); // NOI18N
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
        ActionsPanel.setMinimumSize(new java.awt.Dimension(200, 30));
        ActionsPanel.setPreferredSize(new java.awt.Dimension(30, 30));

        CloseLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/exit.png"))); // NOI18N
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

        ParentPanel.setPreferredSize(new java.awt.Dimension(1500, 500));
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

        MapLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Timkiem.png"))); // NOI18N
        index1Panel.add(MapLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

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
        jLabel3.setText("Yêu cầu đã nhận");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        index2Panel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, 30));

        TiepTeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/YCtiepte.png"))); // NOI18N
        index2Panel.add(TiepTeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

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
        jLabel2.setText("Thông tin trung tâm");
        index3Panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 140, 30));

        CharityInfoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/trungtam24px.png"))); // NOI18N
        index3Panel.add(CharityInfoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

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

        LogoutLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/logout24px.png"))); // NOI18N
        index4Panel.add(LogoutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setText("THÔNG TIN NGƯỜI DÙNG");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("TIẾP TẾ");

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
                .addContainerGap(83, Short.MAX_VALUE))
        );

        ParentPanel.add(LeftPanel, java.awt.BorderLayout.LINE_START);

        RightPanel.setOpaque(false);
        RightPanel.setPreferredSize(new java.awt.Dimension(1120, 508));
        RightPanel.setLayout(new java.awt.CardLayout());

        FindSupplyPanel.setBackground(new java.awt.Color(255, 255, 255));
        FindSupplyPanel.setPreferredSize(new java.awt.Dimension(1350, 508));

        SearchPanel.setBackground(new java.awt.Color(255, 255, 255));
        SearchPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TÌM KIẾM YÊU CẦU TIẾP TẾ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        SupplyTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SupplyTable.setModel(setSupplyTableModel());
        jScrollPane1.setViewportView(SupplyTable);

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHI TIẾT YÊU CẦU", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 485));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setText("Họ và tên");

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel14.setText("Giới tính");

        NameValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NameValueLabel.setText("null");

        GenderValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        GenderValueLabel.setText("null");

        jLabel19.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel19.setText("Mã tiếp tế");

        IdsupValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IdsupValueLabel.setText("null");

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
        jLabel25.setText("Mô tả ");

        RefeshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/refresh.png"))); // NOI18N
        RefeshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RefeshButtonMouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel28.setText("Vật dụng");

        jLabel30.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel30.setText("Lương thực");

        jLabel32.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel32.setText("Nhu yếu phẩm");

        NeedNecessValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NeedNecessValueLabel.setText("null");

        NeedFoodValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NeedFoodValueLabel.setText("null");

        NeedEquipValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NeedEquipValueLabel.setText("null");

        AcceptButton.setText("Chấp nhận");
        AcceptButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        AcceptButton.setkBackGroundColor(new java.awt.Color(102, 255, 255));
        AcceptButton.setkBorderRadius(30);
        AcceptButton.setkEndColor(new java.awt.Color(102, 102, 255));
        AcceptButton.setkForeGround(new java.awt.Color(0, 0, 0));
        AcceptButton.setkHoverColor(new java.awt.Color(0, 0, 0));
        AcceptButton.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        AcceptButton.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        AcceptButton.setkHoverStartColor(new java.awt.Color(102, 204, 255));
        AcceptButton.setkIndicatorColor(new java.awt.Color(0, 0, 0));
        AcceptButton.setkSelectedColor(new java.awt.Color(153, 255, 255));
        AcceptButton.setkStartColor(new java.awt.Color(102, 255, 204));
        AcceptButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AcceptButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AcceptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(IdsupValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RefeshButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(GenderValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ProvinceValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(CreatedValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(NameValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(NeedFoodValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel25)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(NeedNecessValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(NeedEquipValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RefeshButton)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(IdsupValueLabel))))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameValueLabel)
                    .addComponent(jLabel5))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GenderValueLabel)
                    .addComponent(jLabel14))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProvinceValueLabel)
                    .addComponent(jLabel21))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CreatedValueLabel)
                    .addComponent(jLabel24))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(NeedFoodValueLabel))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NeedNecessValueLabel)
                    .addComponent(jLabel32))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NeedEquipValueLabel)
                    .addComponent(jLabel28))
                .addGap(12, 12, 12)
                .addComponent(jLabel25)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AcceptButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FindSupplyPanelLayout = new javax.swing.GroupLayout(FindSupplyPanel);
        FindSupplyPanel.setLayout(FindSupplyPanelLayout);
        FindSupplyPanelLayout.setHorizontalGroup(
            FindSupplyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FindSupplyPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(SearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addContainerGap())
        );
        FindSupplyPanelLayout.setVerticalGroup(
            FindSupplyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FindSupplyPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(FindSupplyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE))
                .addContainerGap())
        );

        RightPanel.add(FindSupplyPanel, "FindAdvisoryPanel");

        WaitingSupplyPanel.setBackground(new java.awt.Color(255, 255, 255));
        WaitingSupplyPanel.setAutoscrolls(true);
        WaitingSupplyPanel.setPreferredSize(new java.awt.Dimension(1350, 508));

        WaitingListPanel.setBackground(new java.awt.Color(255, 255, 255));
        WaitingListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DANH SÁCH YÊU CẦU TIẾP TẾ ĐÃ NHẬN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        WaitSupplyTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        WaitSupplyTable.setModel(setWaitSupplyTableModel());
        jScrollPane4.setViewportView(WaitSupplyTable);

        ProvinceWaitSupComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProvinceWaitSupComboBox.setMaximumRowCount(5);
        ProvinceWaitSupComboBox.setModel(new DefaultComboBoxModel<>(ChangeValue.getProvince()));

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
                    .addComponent(ProvinceWaitSupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        WaitingListPanelLayout.setVerticalGroup(
            WaitingListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, WaitingListPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ProvinceWaitSupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DetailPanel.setBackground(new java.awt.Color(255, 255, 255));
        DetailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHI TIẾT YÊU CẦU", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        DetailPanel.setPreferredSize(new java.awt.Dimension(332, 508));

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel16.setText("Họ và tên");

        jLabel18.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel18.setText("Giới tính");

        NameWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NameWaitLabel.setText("null");

        GenderWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        GenderWaitLabel.setText("null");

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel22.setText("Mã tiếp tế");

        IdsupWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IdsupWaitLabel.setText("null");

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
        jLabel27.setText("Mô tả ");

        WaitRefeshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/refresh.png"))); // NOI18N
        WaitRefeshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                WaitRefeshButtonMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel13.setText("Lương thực");

        NeedFoodWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NeedFoodWaitLabel.setText("null");

        jLabel29.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel29.setText("Nhu yếu phẩm");

        NeedNecessWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NeedNecessWaitLabel.setText("null");

        jLabel31.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel31.setText("Số điện thoại liên lạc");

        PhoneWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PhoneWaitLabel.setText("null");

        jLabel33.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel33.setText("Vật dụng");

        NeedEquipWaitLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NeedEquipWaitLabel.setText("null");

        CancelButtonLabel.setText("Hủy");
        CancelButtonLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        CancelButtonLabel.setkBackGroundColor(new java.awt.Color(102, 255, 255));
        CancelButtonLabel.setkBorderRadius(30);
        CancelButtonLabel.setkEndColor(new java.awt.Color(102, 102, 255));
        CancelButtonLabel.setkForeGround(new java.awt.Color(0, 0, 0));
        CancelButtonLabel.setkHoverColor(new java.awt.Color(0, 0, 0));
        CancelButtonLabel.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        CancelButtonLabel.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        CancelButtonLabel.setkHoverStartColor(new java.awt.Color(102, 204, 255));
        CancelButtonLabel.setkIndicatorColor(new java.awt.Color(0, 0, 0));
        CancelButtonLabel.setkSelectedColor(new java.awt.Color(153, 255, 255));
        CancelButtonLabel.setkStartColor(new java.awt.Color(102, 255, 204));
        CancelButtonLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CancelButtonLabelMouseClicked(evt);
            }
        });

        FinishButton1.setText("Hoàn thành");
        FinishButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        FinishButton1.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        FinishButton1.setkBorderRadius(30);
        FinishButton1.setkEndColor(new java.awt.Color(102, 153, 255));
        FinishButton1.setkForeGround(new java.awt.Color(0, 0, 0));
        FinishButton1.setkHoverColor(new java.awt.Color(0, 0, 0));
        FinishButton1.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        FinishButton1.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        FinishButton1.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        FinishButton1.setkSelectedColor(new java.awt.Color(153, 255, 255));
        FinishButton1.setkStartColor(new java.awt.Color(102, 255, 204));
        FinishButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FinishButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout DetailPanelLayout = new javax.swing.GroupLayout(DetailPanel);
        DetailPanel.setLayout(DetailPanelLayout);
        DetailPanelLayout.setHorizontalGroup(
            DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DetailPanelLayout.createSequentialGroup()
                        .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ProvinceWaitLabel))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(GenderWaitLabel))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addGap(18, 18, 18)
                                        .addComponent(CreatedWaitLabel))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(NeedFoodWaitLabel))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel33)
                                        .addGap(18, 18, 18)
                                        .addComponent(NeedEquipWaitLabel))
                                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(NameWaitLabel)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(DetailPanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(FinishButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(CancelButtonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(DetailPanelLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addComponent(IdsupWaitLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(WaitRefeshButton)
                        .addContainerGap())
                    .addGroup(DetailPanelLayout.createSequentialGroup()
                        .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(DetailPanelLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(NeedNecessWaitLabel))
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PhoneWaitLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        DetailPanelLayout.setVerticalGroup(
            DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetailPanelLayout.createSequentialGroup()
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DetailPanelLayout.createSequentialGroup()
                        .addComponent(WaitRefeshButton)
                        .addGap(12, 12, 12)
                        .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NameWaitLabel)
                            .addComponent(jLabel16)))
                    .addGroup(DetailPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IdsupWaitLabel)
                            .addComponent(jLabel22))))
                .addGap(12, 12, 12)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(GenderWaitLabel))
                .addGap(12, 12, 12)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProvinceWaitLabel)
                    .addComponent(jLabel23))
                .addGap(12, 12, 12)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CreatedWaitLabel)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(12, 12, 12)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NeedFoodWaitLabel)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(12, 12, 12)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NeedNecessWaitLabel)
                    .addComponent(jLabel29))
                .addGap(12, 12, 12)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(NeedEquipWaitLabel))
                .addGap(12, 12, 12)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(PhoneWaitLabel))
                .addGap(12, 12, 12)
                .addComponent(jLabel27)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CancelButtonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FinishButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout WaitingSupplyPanelLayout = new javax.swing.GroupLayout(WaitingSupplyPanel);
        WaitingSupplyPanel.setLayout(WaitingSupplyPanelLayout);
        WaitingSupplyPanelLayout.setHorizontalGroup(
            WaitingSupplyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WaitingSupplyPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(WaitingListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DetailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        WaitingSupplyPanelLayout.setVerticalGroup(
            WaitingSupplyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WaitingSupplyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(WaitingSupplyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(WaitingListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
                .addContainerGap())
        );

        RightPanel.add(WaitingSupplyPanel, "WaitingAdvisoryPanel");

        CharityInformationPanel.setBackground(new java.awt.Color(255, 255, 255));
        CharityInformationPanel.setPreferredSize(new java.awt.Dimension(1350, 508));

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
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NGƯỜI DÙNG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        jLabel34.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel34.setText("Trung tâm");

        CharityNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        CharityNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CharityNameLabel.setText("Tên trung tâm");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/avatar_Trungtam (2).png"))); // NOI18N

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(34, 34, 34)
                        .addComponent(CharityNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addGap(43, 43, 43))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(CharityNameLabel))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THÔNG TIN CÁ NHÂN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N
        jPanel22.setPreferredSize(new java.awt.Dimension(360, 367));

        jLabel42.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel42.setText("Số điện thoại");

        PhoneLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PhoneLabel.setText("null");

        jLabel44.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel44.setText("Tỉnh,Thành phố");

        ProvinceLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProvinceLabel.setText("null");

        jLabel46.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel46.setText("Quận, Huyện");

        jLabel47.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel47.setText("Xã, Phường, Thị trấn");

        DistrictLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        DistrictLabel.setText("null");

        TownLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TownLabel.setText("null");

        jLabel49.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel49.setText("Tỉnh/ Thành phố");

        AddressLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        AddressLabel.setText("null");

        jLabel39.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel39.setText("Lương thực");

        jLabel41.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel41.setText("Nhu yếu phẩm");

        jLabel43.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel43.setText("Vật dụng");

        jLabel45.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel45.setText("Điểm ");

        HasFoodLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        HasFoodLabel.setText("null");

        PointLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PointLabel.setText("null");

        HasNecessLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        HasNecessLabel.setText("null");

        HasEquipLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        HasEquipLabel.setText("null");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(AddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TownLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PhoneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DistrictLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProvinceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jLabel41)
                            .addComponent(jLabel45)
                            .addComponent(jLabel39))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(HasNecessLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HasEquipLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HasFoodLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PointLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(70, 70, 70))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(PhoneLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(ProvinceLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(DistrictLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(TownLabel)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(HasFoodLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(HasNecessLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(HasEquipLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PointLabel))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel45)))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(AddressLabel))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THÔNG TIN TÀI KHOẢN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        jLabel38.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel38.setText("Mã trung tâm");

        IdcharLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IdcharLabel.setText("null");

        jLabel36.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel36.setText("Tên tài khoản");

        UsernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        UsernameLabel.setText("null");

        ChangePasswordButton.setText("ĐỔI MẬT KHẨU");
        ChangePasswordButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        ChangePasswordButton.setkBackGroundColor(new java.awt.Color(153, 255, 255));
        ChangePasswordButton.setkBorderRadius(30);
        ChangePasswordButton.setkEndColor(new java.awt.Color(102, 153, 255));
        ChangePasswordButton.setkForeGround(new java.awt.Color(0, 0, 0));
        ChangePasswordButton.setkHoverColor(new java.awt.Color(0, 0, 0));
        ChangePasswordButton.setkHoverEndColor(new java.awt.Color(255, 102, 204));
        ChangePasswordButton.setkHoverForeGround(new java.awt.Color(153, 255, 255));
        ChangePasswordButton.setkHoverStartColor(new java.awt.Color(51, 204, 255));
        ChangePasswordButton.setkSelectedColor(new java.awt.Color(153, 255, 255));
        ChangePasswordButton.setkStartColor(new java.awt.Color(102, 255, 204));
        ChangePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePasswordButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UsernameLabel)
                    .addComponent(IdcharLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ChangePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(IdcharLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(UsernameLabel)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(ChangePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CharityInformationPanelLayout = new javax.swing.GroupLayout(CharityInformationPanel);
        CharityInformationPanel.setLayout(CharityInformationPanelLayout);
        CharityInformationPanelLayout.setHorizontalGroup(
            CharityInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CharityInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CharityInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(CharityInformationPanelLayout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CharityInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CharityInformationPanelLayout.setVerticalGroup(
            CharityInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CharityInformationPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(CharityInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(CharityInformationPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        RightPanel.add(CharityInformationPanel, "DoctorInformationPanel");

        ParentPanel.add(RightPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(ParentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Get coordinates.
    int xy, xx;
    private void TopPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TopPanelMouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            if (CharityScreen.this.getExtendedState() == MAXIMIZED_BOTH) {
                CharityScreen.this.setExtendedState(JFrame.NORMAL);
            } else {
                CharityScreen.this.setExtendedState(MAXIMIZED_BOTH);
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
        this.setPreferredSize(new Dimension(1260, 540));
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
        this.setPreferredSize(new Dimension(1260, 540));
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
        this.setPreferredSize(new Dimension(1260, 540));
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
        SupplyTable.getSelectionModel().clearSelection();
        refreshForSearchScreen();
    }//GEN-LAST:event_RefeshButtonMouseClicked

    private void AcceptButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AcceptButtonMouseClicked
        // TODO add your handling code here:
        if (IdsupValueLabel.getText().equals("null"))
        JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 yêu cầu để nhận",
            "Lỗi!", JOptionPane.ERROR_MESSAGE);
        else {
            int choice = JOptionPane.showConfirmDialog(null,
                "Chấp nhận tư vấn yêu cầu này?", "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
            if (choice == 0) {
                int result =supcon.AcceptSupply(Integer.toString(charity.getIdchar()), IdsupValueLabel.getText());
                if(result==1)
                {
                    SupplyTable.getSelectionModel().clearSelection();
                    refreshForSearchScreen();
                    JOptionPane.showMessageDialog(null, "Nhận thành công");

                    SupplyTable.setModel(setSupplyTableModel());
                    setSupplyTableSize();
                    getComboboxItemForSearchScreen();

                    WaitSupplyTable.setModel(setWaitSupplyTableModel());
                    setWaitSupplyTableSize();
                    getComboboxItemForWaitScreen();
                }
            }
        }
    }//GEN-LAST:event_AcceptButtonMouseClicked

    private void FinishButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FinishButton1MouseClicked
        // TODO add your handling code here:
        if (IdsupWaitLabel.getText().equals("null"))
        JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 yêu cầu để hoàn thành",
            "Lỗi!", JOptionPane.ERROR_MESSAGE);
        else {
            int choice = JOptionPane.showConfirmDialog(null,
                "Hoàn thành việc tư vấn yêu cầu này?", "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
            if (choice == 0) {
                int result =supcon.FinishSupply(Integer.parseInt(IdsupWaitLabel.getText()));
                if(result==1)
                {
                    WaitSupplyTable.getSelectionModel().clearSelection();
                    refreshForWaitScreen();
                    JOptionPane.showMessageDialog(null, "Đã hoàn thành việc tư vấn");
                    WaitSupplyTable.setModel(setWaitSupplyTableModel());
                    setWaitSupplyTableSize();
                    getComboboxItemForWaitScreen();
                }
            }
        }
    }//GEN-LAST:event_FinishButton1MouseClicked

    private void CancelButtonLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelButtonLabelMouseClicked
        // TODO add your handling code here:
        if (IdsupWaitLabel.getText().equals("null"))
        JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 yêu cầu để hủy",
            "Lỗi!", JOptionPane.ERROR_MESSAGE);
        else {
            int choice = JOptionPane.showConfirmDialog(null,
                "Hủy tiếp tế yêu cầu này?", "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
            if (choice == 0) {
                int result =supcon.CancelSupply(Integer.toString(charity.getIdchar()), IdsupWaitLabel.getText());
                if(result==1)
                {
                    SupplyTable.getSelectionModel().clearSelection();
                    refreshForSearchScreen();
                    JOptionPane.showMessageDialog(null, "Hủy thành công");

                    SupplyTable.setModel(setSupplyTableModel());
                    setSupplyTableSize();
                    getComboboxItemForSearchScreen();

                    WaitSupplyTable.setModel(setWaitSupplyTableModel());
                    setWaitSupplyTableSize();
                    getComboboxItemForWaitScreen();
                }
            }
        }
    }//GEN-LAST:event_CancelButtonLabelMouseClicked

    private void WaitRefeshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WaitRefeshButtonMouseClicked
        // TODO add your handling code here:
        WaitSupplyTable.getSelectionModel().clearSelection();
        refreshForWaitScreen();
    }//GEN-LAST:event_WaitRefeshButtonMouseClicked

    private void ChangePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangePasswordButtonActionPerformed
        // TODO add your handling code here:
        //this.dispose();
        new ChangePasswordScreen().setVisible(true);
    }//GEN-LAST:event_ChangePasswordButtonActionPerformed

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
        CharityScreen.this.getRootPane().setBorder(new LineBorder(new Color(76, 41, 211)));
        TitleLabel.setText(this.getTitle());
        cardLayout = (CardLayout) RightPanel.getLayout();

        if (getOSType() == OSType.MacOS) {
            TopPanel.remove(TitlePanel);
            TopPanel.remove(RightPanel);

            TopPanel.add(TitlePanel, BorderLayout.EAST);
            TopPanel.add(ActionsPanel, BorderLayout.WEST);

            ActionsPanel.remove(CloseLabel);

            ActionsPanel.add(CloseLabel);

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
            java.util.logging.Logger.getLogger(CharityScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CharityScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CharityScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CharityScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CharityScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton AcceptButton;
    private javax.swing.JPanel ActionsPanel;
    private javax.swing.JLabel AddressLabel;
    private com.k33ptoo.components.KButton CancelButtonLabel;
    private com.k33ptoo.components.KButton ChangePasswordButton;
    private javax.swing.JLabel CharityInfoLabel;
    private javax.swing.JPanel CharityInformationPanel;
    private javax.swing.JLabel CharityNameLabel;
    private javax.swing.JLabel CloseLabel;
    private javax.swing.JLabel CreatedValueLabel;
    private javax.swing.JLabel CreatedWaitLabel;
    private javax.swing.JPanel DetailPanel;
    private javax.swing.JTextArea DetailTextArea;
    private javax.swing.JTextArea DetailWaitTextArea;
    private javax.swing.JLabel DistrictLabel;
    private javax.swing.JPanel FindSupplyPanel;
    private com.k33ptoo.components.KButton FinishButton1;
    private javax.swing.JLabel GenderValueLabel;
    private javax.swing.JLabel GenderWaitLabel;
    private javax.swing.JLabel HasEquipLabel;
    private javax.swing.JLabel HasFoodLabel;
    private javax.swing.JLabel HasNecessLabel;
    private javax.swing.JLabel IdcharLabel;
    private javax.swing.JLabel IdsupValueLabel;
    private javax.swing.JLabel IdsupWaitLabel;
    private javax.swing.JPanel LeftPanel;
    private javax.swing.JLabel LogoLabel;
    private javax.swing.JLabel LogoutLabel;
    private javax.swing.JLabel MapLabel;
    private javax.swing.JLabel NameValueLabel;
    private javax.swing.JLabel NameWaitLabel;
    private javax.swing.JLabel NeedEquipValueLabel;
    private javax.swing.JLabel NeedEquipWaitLabel;
    private javax.swing.JLabel NeedFoodValueLabel;
    private javax.swing.JLabel NeedFoodWaitLabel;
    private javax.swing.JLabel NeedNecessValueLabel;
    private javax.swing.JLabel NeedNecessWaitLabel;
    private javax.swing.JPanel ParentPanel;
    private javax.swing.JLabel PhoneLabel;
    private javax.swing.JLabel PhoneWaitLabel;
    private javax.swing.JLabel PointLabel;
    private javax.swing.JComboBox<String> ProvinceComboBox;
    private javax.swing.JLabel ProvinceLabel;
    private javax.swing.JLabel ProvinceValueLabel;
    private javax.swing.JLabel ProvinceWaitLabel;
    private javax.swing.JComboBox<String> ProvinceWaitSupComboBox;
    private javax.swing.JLabel RefeshButton;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JTable SupplyTable;
    private javax.swing.JLabel TiepTeLabel;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JPanel TitlePanel;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JLabel TownLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JLabel WaitRefeshButton;
    private javax.swing.JTable WaitSupplyTable;
    private javax.swing.JPanel WaitingListPanel;
    private javax.swing.JPanel WaitingSupplyPanel;
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
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
