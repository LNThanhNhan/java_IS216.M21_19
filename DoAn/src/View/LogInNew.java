/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import static Process.AccountController.CheckAccount;
import static Process.AccountController.getRoleUser;
import Process.EmployeeController;
import static Process.PersonController.getStatusPerson;
import Model.Account;
import Model.Charity;
import Model.Doctor;
import Model.Employee;
import Model.Person;
import Process.PersonController;
import java.awt.Color; 
import java.awt.Color; 
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author MyPC
 */
public class LogInNew extends javax.swing.JFrame {

    /**
     * Creates new form LogInNew
     */
    public LogInNew() {
        setLookandFeel();
        initComponents();
        this.setLocationRelativeTo(null);
        PasswordField.setEchoChar((char)8226);
        hidepasswordLabel.setVisible(false);
        showpasswordLabel.setVisible(true);
    }
    
    //Set look and feel theo giao diện windows
    //dùng để chỉnh look and feel cho  EmployeeScreen khi được gọi từ Jframe LogInScreen
    public void setLookandFeel(){ 
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
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
    
    //Gioi hạn kí tự cho JtextField
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
    
    //Gioi hạn kí tự cho JPaswordField
    public void LimitPassword(JPasswordField txt, java.awt.event.KeyEvent evt, int lenghth_char_exp) {
        String string = String.valueOf(txt.getPassword());
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
    
    //Đặt sự kiện
    public void setEvent(){ 
        if(UsernameTextField.getText().equals("") || String.valueOf(PasswordField.getPassword()).equals("")) {
            JOptionPane.showMessageDialog(null,"Không được để trống các miền giá trị bắt buộc!","Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
        }
        else if (CheckAccount(UsernameTextField.getText().toString(), String.valueOf(PasswordField.getPassword()) )==0){
            int roleuser = getRoleUser(UsernameTextField.getText().toString());
            switch(roleuser) {
                case 1: {
                    //Người cần giúp đỡ
                    PersonController percon = new PersonController();
                    Person person = new Person();
                    person = percon.getPersonInfo(UsernameTextField.getText().toString());
                    //new PersonScreen(person).setVisible(true);
                     //a.setVisible(true);
                    this.dispose();
                    
                }
                break;
                case 2: {
                    //Nhân Viên
                    Account account = new Account();
                    Employee employee = new Employee();
                    employee.setUsername(UsernameTextField.getText().toString());
                    account.setPassword(String.valueOf(PasswordField.getPassword()));
                    account.setUsername(UsernameTextField.getText().toString());
                    account.setRole(2);
                    //employee = EmployeeController.getEmployeeInfo();
                    //EmployeeScreen a = new EmployeeScreen();
                    new EmployeeScreen(employee, account).setVisible(true);
                     //a.setVisible(true);
                    this.dispose();
                    //EmployeeScreen a = new EmployeeScreen();                    
                }
                break;
                case 3: {
                    //Trung Tâm
                    Charity charity = new Charity();
                    charity.setUsername(UsernameTextField.getText().toString());
                    //new CharityScreen(charity).setVisible(true);
                    this.dispose();
                }
                break;
                case 4: {
                    //Bác sĩ
                    Doctor doctor = new Doctor();
                    doctor.setUsername(UsernameTextField.getText().toString());
                    //new DoctorScreen(doctor).setVisible(true);
                    this.dispose();
                }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        kGradientPanel1 = new com.k33ptoo.components.KGradientPanel();
        UsernameTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PasswordField = new javax.swing.JPasswordField();
        LoginButton = new com.k33ptoo.components.KButton();
        ToSignUpLabel = new javax.swing.JLabel();
        SignUpLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        showpasswordLabel = new javax.swing.JLabel();
        hidepasswordLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ErrorLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        kGradientPanel1.setkBorderRadius(0);
        kGradientPanel1.setkEndColor(new java.awt.Color(106, 197, 254));
        kGradientPanel1.setkGradientFocus(50);
        kGradientPanel1.setkStartColor(new java.awt.Color(225, 243, 254));
        kGradientPanel1.setPreferredSize(new java.awt.Dimension(776, 442));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        UsernameTextField.setBackground(new java.awt.Color(0,0,0,0));
        UsernameTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        UsernameTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        UsernameTextField.setCaretColor(new java.awt.Color(225, 243, 254));
        UsernameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsernameTextFieldKeyPressed(evt);
            }
        });
        kGradientPanel1.add(UsernameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 300, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel1.setText("Tên đăng nhập");
        kGradientPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel2.setText("Mật khẩu");
        kGradientPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 230, -1, -1));

        PasswordField.setBackground(new java.awt.Color(0,0,0,0));
        PasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PasswordField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        PasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordFieldKeyPressed(evt);
            }
        });
        kGradientPanel1.add(PasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, 300, 30));

        LoginButton.setBackground(new java.awt.Color(106, 128, 254));
        LoginButton.setForeground(new java.awt.Color(106, 128, 254));
        LoginButton.setText("ĐĂNG NHẬP");
        LoginButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LoginButton.setkBorderRadius(40);
        LoginButton.setkEndColor(new java.awt.Color(106, 128, 254));
        LoginButton.setkHoverEndColor(new java.awt.Color(106, 197, 254));
        LoginButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        LoginButton.setkHoverStartColor(new java.awt.Color(255, 255, 255));
        LoginButton.setkIndicatorColor(new java.awt.Color(106, 197, 254));
        LoginButton.setkPressedColor(new java.awt.Color(106, 128, 254));
        LoginButton.setkSelectedColor(new java.awt.Color(106, 128, 254));
        LoginButton.setkStartColor(new java.awt.Color(99, 131, 224));
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });
        kGradientPanel1.add(LoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 330, 300, -1));

        ToSignUpLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ToSignUpLabel.setText("Chưa có tài khoản?");
        kGradientPanel1.add(ToSignUpLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 390, 140, -1));

        SignUpLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SignUpLabel.setText("Đăng kí ngay");
        SignUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SignUpLabelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SignUpLabelMousePressed(evt);
            }
        });
        kGradientPanel1.add(SignUpLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 390, 100, -1));
        kGradientPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 260, -1, 30));

        showpasswordLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/icons8_eye_20px_1.png"))); // NOI18N
        showpasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showpasswordLabelMouseClicked(evt);
            }
        });
        kGradientPanel1.add(showpasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 260, 40, 30));

        hidepasswordLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/icons8_invisible_20px_1.png"))); // NOI18N
        hidepasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hidepasswordLabelMouseClicked(evt);
            }
        });
        kGradientPanel1.add(hidepasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 260, 50, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/icons8_user_20px_1.png"))); // NOI18N
        kGradientPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 150, 30, 40));

        ErrorLabel.setFont(new java.awt.Font("Sitka Subheading", 3, 14)); // NOI18N
        ErrorLabel.setForeground(new java.awt.Color(255, 51, 51));
        kGradientPanel1.add(ErrorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 430, 290, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/logologin.png"))); // NOI18N
        kGradientPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 350, 300));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        kGradientPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 360, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        // TODO add your handling code here:
        setEvent();
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void hidepasswordLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hidepasswordLabelMouseClicked
        // TODO add your handling code here:
        PasswordField.setEchoChar((char)8226);
        hidepasswordLabel.setVisible(false);
        hidepasswordLabel.setEnabled(false);
        showpasswordLabel.setEnabled(true);
        showpasswordLabel.setVisible(true);
    }//GEN-LAST:event_hidepasswordLabelMouseClicked

    private void showpasswordLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showpasswordLabelMouseClicked
        // TODO add your handling code here:
        PasswordField.setEchoChar((char)0);
        hidepasswordLabel.setVisible(true);
        hidepasswordLabel.setEnabled(true);
        showpasswordLabel.setEnabled(false);
        showpasswordLabel.setVisible(false);
    }//GEN-LAST:event_showpasswordLabelMouseClicked

    private void UsernameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsernameTextFieldKeyPressed
        // TODO add your handling code here:
        LimitChar(UsernameTextField, evt, 30);
    }//GEN-LAST:event_UsernameTextFieldKeyPressed

    private void PasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordFieldKeyPressed
        // TODO add your handling code here:
        LimitPassword(PasswordField, evt, 30);
    }//GEN-LAST:event_PasswordFieldKeyPressed

    private void SignUpLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SignUpLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_SignUpLabelMouseClicked

    private void SignUpLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SignUpLabelMousePressed
        // TODO add your handling code here:
        this.dispose();
        new SignUpPersonScreen(this, true).setVisible(true);
    }//GEN-LAST:event_SignUpLabelMousePressed

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
            java.util.logging.Logger.getLogger(LogInNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogInNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogInNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogInNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogInNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ErrorLabel;
    private com.k33ptoo.components.KButton LoginButton;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JLabel SignUpLabel;
    private javax.swing.JLabel ToSignUpLabel;
    private javax.swing.JTextField UsernameTextField;
    private javax.swing.JLabel hidepasswordLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.k33ptoo.components.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel showpasswordLabel;
    // End of variables declaration//GEN-END:variables
}
