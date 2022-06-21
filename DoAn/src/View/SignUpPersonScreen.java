/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View;

/**
 *
 * @author MyPC
 */

import Process.PersonController;
import static Process.AccountController.checkSignUpAccount;
import Model.Account;
import Model.Person;
import static View.ChangeValue.getGender;
import static View.ChangeValue.getProvince;
import java.awt.CardLayout;
import java.awt.Color; 
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import static java.lang.Integer.parseInt;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class SignUpPersonScreen extends javax.swing.JDialog {

    /**
     * Creates new form SignUpPersonScreen
     */
    private CardLayout cardLayout;
        
    public SignUpPersonScreen(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        initComponents();
        
        PWPasswordField.setEchoChar((char)8226);
        VerifyPassword.setEchoChar((char)8226);
        this.setLocationRelativeTo(null);
        setLookandFeel();
    }
    
    //giới hạn ký tự cho số điện thoại
    public void LimitCharPhone(JTextField txt, java.awt.event.KeyEvent evt, int lenghth_char_exp) {
        String string = txt.getText();
        ErrorLabel.setText("");

        int length = string.length();

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {
            if (length < lenghth_char_exp) {
                txt.setEditable(true);
            } else {
                txt.setEditable(false);
                ErrorLabel.setText("Nhập quá kí tự cho phép!!");
            }
        } else if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            ErrorLabel.setText("");
            txt.setEditable(true);
        } else {
            txt.setEditable(false);
        }

    }

    //giới hạn ký tư  cho Jtextfield
    public void LimitChar(JTextField txt, java.awt.event.KeyEvent evt, int lenghth_char_exp) {
        String string = txt.getText();
        ErrorLabel.setText("");

        int length = string.length();
        if (length < lenghth_char_exp) {
            txt.setEditable(true);
        } else {
            txt.setEditable(false);
            ErrorLabel.setText("Nhập quá kí tự cho phép!!");
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                ErrorLabel.setText("");
                txt.setEditable(true);
            } else {
                txt.setEditable(false);
            }
        }
    }
    
    //giới hạn ký tự cho JtextField
     public void LimitCharAccountinfo(JTextField txt, java.awt.event.KeyEvent evt, int lenghth_char_exp) {
        String string = txt.getText();
        Error2Label.setText("");

        int length = string.length();
        if (length < lenghth_char_exp) {
            txt.setEditable(true);
        } else {
            txt.setEditable(false);
            Error2Label.setText("Nhập quá kí tự cho phép!!");
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                ErrorLabel.setText("");
                txt.setEditable(true);
            } else {
                txt.setEditable(false);
            }
        }
    }

     //giới hạn cho JpasswordField
    public void LimitPassword(JPasswordField txt, java.awt.event.KeyEvent evt, int lenghth_char_exp) {
        String string = String.valueOf(txt.getPassword());
        Error2Label.setText("");
        

        int length = string.length();
        if (length < lenghth_char_exp) {
            txt.setEditable(true);
        } else {
            txt.setEditable(false);
            Error2Label.setText("Nhập quá kí tự cho phép!!");
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                Error2Label.setText("");
                txt.setEditable(true);
            } else {
                txt.setEditable(false);
            }
        }
    }

    //Tạo sự kiện cho việ nhập tài khoản ( chưa đăng ký)
    public int setEventSignUpAccount() {
        String username = UsernameTextField.getText();
        String password = String.valueOf(PWPasswordField.getPassword());
        String Verify = String.valueOf(VerifyPassword.getPassword());

        if (username.equals("") || password.equals("") || Verify.equals("")) {
            JOptionPane.showMessageDialog(null, "Không được để trống các miền giá trị bắt buộc!", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return 1;
        } else if (password.equals(Verify) == false) {
            JOptionPane.showMessageDialog(null, "Mật khẩu và xác nhận mật khẩu không giống nhau!", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return 1;
        } else if (checkSignUpAccount(UsernameTextField.getText().toString()) != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    //Tạo look and feel thuộc giao diện windows
    public void setLookandFeel() {
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
    
    public void setEvent() {
        int check = -1;

        //Empty
        if (NameTextField.getText().equals("") || ProvinceTextField.getText().equals("")
                || DistrictTextField.getText().equals("") || TownTextField.getText().equals("")
                || AddressTextField.getText().equals("") || PhoneTextField.getText().equals("")) {

            Person person = new Person();
            Account account = new Account();
            check = PersonController.AddPerson(person, account);
        } else if ((PhoneTextField.getText().length()) < 10) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!",
                    "Lỗi!", JOptionPane.ERROR_MESSAGE);
        } else {
            Person person = new Person();
            Account account = new Account();
            person.setUsername(UsernameTextField.getText());
            account.setPassword(String.valueOf(PWPasswordField.getPassword()));
            person.setName(NameTextField.getText());
            person.setGender(getGender(MaleGenderRadioButton));

            //check Province in getProvince()
            String Province[] = getProvince();
            String province = ProvinceTextField.getText();
            for (int i = 0; i < getProvince().length; i++) {
                if (Province[i].equalsIgnoreCase(province)) {
                    person.setProvince(Province[i]);
                    break;
                }
            }
            
            if (person.getProvince() == null) {
                JOptionPane.showMessageDialog(null, "Tỉnh/Thành phố không tồn tại!",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);

            } else {
                person.setDistrict(DistrictTextField.getText());
                person.setTown(TownTextField.getText());
                person.setAddress(AddressTextField.getText());
                person.setPhone(PhoneTextField.getText());

                check = PersonController.AddPerson(person, account);
            }
        }

        if (check == 0) {
            int option = JOptionPane.showConfirmDialog(null, "Đăng kí thành công, bạn có muốn đăng nhập ngay?",
                    "Thông báo!", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                //Back login
                LogInNew.main(null);
                this.dispose();
            } else {
                //Back SignUp
                this.dispose();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField5 = new javax.swing.JPasswordField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        NameTextField3 = new javax.swing.JTextField();
        kGradientPanel1 = new com.k33ptoo.components.KGradientPanel();
        SignUpPanel = new com.k33ptoo.components.KGradientPanel();
        AccountInfoPanel = new com.k33ptoo.components.KGradientPanel();
        ContinueButton = new com.k33ptoo.components.KButton();
        UsernameTextField = new javax.swing.JTextField();
        PWPasswordField = new javax.swing.JPasswordField();
        VerifyPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        BackLoginLabel = new javax.swing.JLabel();
        PersonalnfoPanel = new com.k33ptoo.components.KGradientPanel();
        DistrictTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        MaleGenderRadioButton = new javax.swing.JRadioButton();
        FeMaleGenderRadioButton = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        SignUpButton = new com.k33ptoo.components.KButton();
        ProvinceTextField = new javax.swing.JTextField();
        AddressTextField = new javax.swing.JTextField();
        TownTextField = new javax.swing.JTextField();
        NameTextField = new javax.swing.JTextField();
        PhoneTextField = new javax.swing.JTextField();
        ErrorLabel = new javax.swing.JLabel();
        BackButton = new com.k33ptoo.components.KButton();
        kGradientPanel2 = new com.k33ptoo.components.KGradientPanel();
        Error2Label = new javax.swing.JLabel();

        jPasswordField5.setText("jPasswordField5");

        NameTextField3.setBackground(new Color(0,0,0,0));
        NameTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        NameTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameTextField3ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        kGradientPanel1.setkBorderRadius(0);
        kGradientPanel1.setkEndColor(new java.awt.Color(106, 197, 254));
        kGradientPanel1.setkGradientFocus(25);
        kGradientPanel1.setkStartColor(new java.awt.Color(225, 243, 254));

        SignUpPanel.setBackground(new Color(0,0,0,0));
        SignUpPanel.setkEndColor(new java.awt.Color(106, 197, 254));
        SignUpPanel.setkStartColor(new java.awt.Color(225, 243, 254));
        SignUpPanel.setOpaque(false);
        SignUpPanel.setLayout(new java.awt.CardLayout());

        AccountInfoPanel.setBackground(new Color(0,0,0,0));
        AccountInfoPanel.setkBorderRadius(0);
        AccountInfoPanel.setkEndColor(new java.awt.Color(106, 197, 254));
        AccountInfoPanel.setkGradientFocus(10);
        AccountInfoPanel.setkStartColor(new java.awt.Color(225, 243, 254));
        AccountInfoPanel.setOpaque(false);

        ContinueButton.setText("TIẾP TỤC");
        ContinueButton.setkBorderRadius(40);
        ContinueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContinueButtonActionPerformed(evt);
            }
        });

        UsernameTextField.setBackground(new Color(0,0,0,0));
        UsernameTextField.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        UsernameTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        UsernameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsernameTextFieldKeyPressed(evt);
            }
        });

        PWPasswordField.setBackground(new Color(0,0,0,0));
        PWPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        PWPasswordField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        PWPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PWPasswordFieldKeyPressed(evt);
            }
        });

        VerifyPassword.setBackground(new Color(0,0,0,0));
        VerifyPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        VerifyPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        VerifyPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VerifyPasswordKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel4.setText("Tên đăng nhập");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel5.setText("Mật khẩu");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel6.setText("Nhập lại mật khẩu");

        BackLoginLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        BackLoginLabel.setForeground(new java.awt.Color(255, 255, 255));
        BackLoginLabel.setText("Quay lại đăng nhập");
        BackLoginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BackLoginLabelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout AccountInfoPanelLayout = new javax.swing.GroupLayout(AccountInfoPanel);
        AccountInfoPanel.setLayout(AccountInfoPanelLayout);
        AccountInfoPanelLayout.setHorizontalGroup(
            AccountInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountInfoPanelLayout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addGroup(AccountInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BackLoginLabel)
                    .addComponent(jLabel6)
                    .addComponent(PWPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(ContinueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VerifyPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(142, Short.MAX_VALUE))
        );
        AccountInfoPanelLayout.setVerticalGroup(
            AccountInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AccountInfoPanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PWPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(VerifyPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BackLoginLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ContinueButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SignUpPanel.add(AccountInfoPanel, "card3");

        PersonalnfoPanel.setBackground(new Color(0,0,0,0));
        PersonalnfoPanel.setkEndColor(new java.awt.Color(106, 197, 254));
        PersonalnfoPanel.setkGradientFocus(30);
        PersonalnfoPanel.setkStartColor(new java.awt.Color(225, 243, 254));
        PersonalnfoPanel.setOpaque(false);

        DistrictTextField.setBackground(new Color(0,0,0,0));
        DistrictTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        DistrictTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        DistrictTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DistrictTextFieldActionPerformed(evt);
            }
        });
        DistrictTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DistrictTextFieldKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel1.setText("Họ và tên");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel2.setText("Số điện thoại");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText("Giới tính");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel8.setText("Tỉnh/Thành Phố");

        MaleGenderRadioButton.setBackground(new Color(0,0,0,0));
        buttonGroup1.add(MaleGenderRadioButton);
        MaleGenderRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MaleGenderRadioButton.setText("Nam");
        MaleGenderRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaleGenderRadioButtonActionPerformed(evt);
            }
        });

        FeMaleGenderRadioButton.setBackground(new Color(0,0,0,0));
        buttonGroup1.add(FeMaleGenderRadioButton);
        FeMaleGenderRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        FeMaleGenderRadioButton.setText("Nữ");
        FeMaleGenderRadioButton.setBorder(null);
        FeMaleGenderRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FeMaleGenderRadioButtonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel9.setText("Quận/Huyện");

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel10.setText("Phường");

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel11.setText("Địa chỉ");

        SignUpButton.setText("ĐĂNG KÍ");
        SignUpButton.setkBorderRadius(40);
        SignUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignUpButtonActionPerformed(evt);
            }
        });

        ProvinceTextField.setBackground(new Color(0,0,0,0));
        ProvinceTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProvinceTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        ProvinceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProvinceTextFieldActionPerformed(evt);
            }
        });
        ProvinceTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProvinceTextFieldKeyPressed(evt);
            }
        });

        AddressTextField.setBackground(new Color(0,0,0,0));
        AddressTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        AddressTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        AddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressTextFieldActionPerformed(evt);
            }
        });
        AddressTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddressTextFieldKeyPressed(evt);
            }
        });

        TownTextField.setBackground(new Color(0,0,0,0));
        TownTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TownTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        TownTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TownTextFieldActionPerformed(evt);
            }
        });
        TownTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TownTextFieldKeyPressed(evt);
            }
        });

        NameTextField.setBackground(new Color(0,0,0,0));
        NameTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NameTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        NameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameTextFieldActionPerformed(evt);
            }
        });
        NameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NameTextFieldKeyPressed(evt);
            }
        });

        PhoneTextField.setBackground(new Color(0,0,0,0));
        PhoneTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PhoneTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        PhoneTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhoneTextFieldActionPerformed(evt);
            }
        });
        PhoneTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PhoneTextFieldKeyPressed(evt);
            }
        });

        ErrorLabel.setFont(new java.awt.Font("Sitka Subheading", 3, 15)); // NOI18N
        ErrorLabel.setForeground(new java.awt.Color(255, 51, 51));

        BackButton.setText("QUAY LẠI");
        BackButton.setkBorderRadius(40);
        BackButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackButtonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BackButtonMousePressed(evt);
            }
        });
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PersonalnfoPanelLayout = new javax.swing.GroupLayout(PersonalnfoPanel);
        PersonalnfoPanel.setLayout(PersonalnfoPanelLayout);
        PersonalnfoPanelLayout.setHorizontalGroup(
            PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonalnfoPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonalnfoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addContainerGap(461, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PersonalnfoPanelLayout.createSequentialGroup()
                        .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PersonalnfoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(PersonalnfoPanelLayout.createSequentialGroup()
                                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addGroup(PersonalnfoPanelLayout.createSequentialGroup()
                                                .addComponent(MaleGenderRadioButton)
                                                .addGap(66, 66, 66)
                                                .addComponent(FeMaleGenderRadioButton))
                                            .addComponent(NameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(PersonalnfoPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(195, 195, 195)))
                                    .addComponent(PhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(DistrictTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TownTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(AddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PersonalnfoPanelLayout.createSequentialGroup()
                                .addComponent(ProvinceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PersonalnfoPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SignUpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        PersonalnfoPanelLayout.setVerticalGroup(
            PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonalnfoPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DistrictTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10))
                .addGap(12, 12, 12)
                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MaleGenderRadioButton)
                    .addComponent(FeMaleGenderRadioButton)
                    .addComponent(TownTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProvinceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PersonalnfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SignUpButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        SignUpPanel.add(PersonalnfoPanel, "card2");

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );

        SignUpPanel.add(kGradientPanel2, "card4");

        Error2Label.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        Error2Label.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(SignUpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(298, 298, 298)
                        .addComponent(Error2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(SignUpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Error2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DistrictTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DistrictTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DistrictTextFieldActionPerformed

    private void MaleGenderRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaleGenderRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaleGenderRadioButtonActionPerformed

    private void FeMaleGenderRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FeMaleGenderRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FeMaleGenderRadioButtonActionPerformed

    private void ContinueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinueButtonActionPerformed
        // TODO add your handling code here:
        Error2Label.setText("");
        ErrorLabel.setText("");
        if (setEventSignUpAccount() == 0) {
            SignUpPanel.removeAll();
            SignUpPanel.add(PersonalnfoPanel);
            SignUpPanel.repaint();
            SignUpPanel.revalidate();
        }
    }//GEN-LAST:event_ContinueButtonActionPerformed

    private void BackLoginLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackLoginLabelMousePressed
        // TODO add your handling code here:
        LogInNew.main(null);
        this.dispose();
        
    }//GEN-LAST:event_BackLoginLabelMousePressed

    private void SignUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignUpButtonActionPerformed
        // TODO add your handling code here:
        setEvent();
    }//GEN-LAST:event_SignUpButtonActionPerformed

    private void ProvinceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProvinceTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProvinceTextFieldActionPerformed

    private void AddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressTextFieldActionPerformed

    private void NameTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameTextField3ActionPerformed

    private void TownTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TownTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TownTextFieldActionPerformed

    private void NameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameTextFieldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_NameTextFieldActionPerformed

    private void PhoneTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhoneTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhoneTextFieldActionPerformed

    private void NameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NameTextFieldKeyPressed
        // TODO add your handling code here:
        
        LimitChar(NameTextField, evt, 30);
    }//GEN-LAST:event_NameTextFieldKeyPressed

    private void PhoneTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PhoneTextFieldKeyPressed
        // TODO add your handling code here:
        
        LimitCharPhone(PhoneTextField, evt, 10);
    }//GEN-LAST:event_PhoneTextFieldKeyPressed

    private void ProvinceTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProvinceTextFieldKeyPressed
        // TODO add your handling code here:
        
        LimitChar(ProvinceTextField, evt, 30);
    }//GEN-LAST:event_ProvinceTextFieldKeyPressed

    private void DistrictTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DistrictTextFieldKeyPressed
        // TODO add your handling code here:
        
        LimitChar(DistrictTextField, evt, 30);
    }//GEN-LAST:event_DistrictTextFieldKeyPressed

    private void TownTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TownTextFieldKeyPressed
        // TODO add your handling code here:
        
        LimitChar(TownTextField, evt, 30);
    }//GEN-LAST:event_TownTextFieldKeyPressed

    private void AddressTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddressTextFieldKeyPressed
        // TODO add your handling code here:
        
        LimitChar(AddressTextField, evt, 30);
    }//GEN-LAST:event_AddressTextFieldKeyPressed

    private void UsernameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsernameTextFieldKeyPressed
        // TODO add your handling code here:
        LimitCharAccountinfo(UsernameTextField, evt, 30);
    }//GEN-LAST:event_UsernameTextFieldKeyPressed

    private void PWPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PWPasswordFieldKeyPressed
        // TODO add your handling code here:
        LimitPassword(PWPasswordField, evt, 30);
    }//GEN-LAST:event_PWPasswordFieldKeyPressed

    private void VerifyPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VerifyPasswordKeyPressed
        // TODO add your handling code here:
        LimitPassword(VerifyPassword, evt, 30);
    }//GEN-LAST:event_VerifyPasswordKeyPressed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BackButtonActionPerformed

    private void BackButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BackButtonMouseClicked

    private void BackButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackButtonMousePressed
        // TODO add your handling code here:
         SignUpPanel.removeAll();
         SignUpPanel.add(AccountInfoPanel);
         SignUpPanel.repaint();
         SignUpPanel.revalidate();
    }//GEN-LAST:event_BackButtonMousePressed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignUpPersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUpPersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUpPersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUpPersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SignUpPersonScreen dialog = new SignUpPersonScreen(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KGradientPanel AccountInfoPanel;
    private javax.swing.JTextField AddressTextField;
    private com.k33ptoo.components.KButton BackButton;
    private javax.swing.JLabel BackLoginLabel;
    private com.k33ptoo.components.KButton ContinueButton;
    private javax.swing.JTextField DistrictTextField;
    private javax.swing.JLabel Error2Label;
    private javax.swing.JLabel ErrorLabel;
    private javax.swing.JRadioButton FeMaleGenderRadioButton;
    private javax.swing.JRadioButton MaleGenderRadioButton;
    private javax.swing.JTextField NameTextField;
    private javax.swing.JTextField NameTextField3;
    private javax.swing.JPasswordField PWPasswordField;
    private com.k33ptoo.components.KGradientPanel PersonalnfoPanel;
    private javax.swing.JTextField PhoneTextField;
    private javax.swing.JTextField ProvinceTextField;
    private com.k33ptoo.components.KButton SignUpButton;
    private com.k33ptoo.components.KGradientPanel SignUpPanel;
    private javax.swing.JTextField TownTextField;
    private javax.swing.JTextField UsernameTextField;
    private javax.swing.JPasswordField VerifyPassword;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jPasswordField5;
    private com.k33ptoo.components.KGradientPanel kGradientPanel1;
    private com.k33ptoo.components.KGradientPanel kGradientPanel2;
    // End of variables declaration//GEN-END:variables
}
