/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;
import Process.AccountController;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
public class ChangePasswordScreen extends javax.swing.JFrame {
    private AccountController acccon;
    /**
     * Creates new form ChangePasswordScreen
     */
    
    //java.awt.Color
    public ChangePasswordScreen() {
        acccon=new AccountController();
        initComponents();
        this.getContentPane().setBackground(new Color(106,197,254));
        ShowUserNameLabel.setText("user10");
        setLocationRelativeTo(null);
        ErrorLabel.setText("");
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }
    
    public ChangePasswordScreen(String username) {
        acccon=new AccountController();
        initComponents();
        this.getContentPane().setBackground(new Color(106,197,254));
        ShowUserNameLabel.setText(username);
        setLocationRelativeTo(null);
        ErrorLabel.setText("");
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        CurrentPasswordLabel = new javax.swing.JLabel();
        CurrentPasswordField = new javax.swing.JPasswordField();
        NewPasswordLabel = new javax.swing.JLabel();
        NewPasswordField = new javax.swing.JPasswordField();
        ShowUserNameLabel = new javax.swing.JLabel();
        ConfirmPasswordLabel = new javax.swing.JLabel();
        ConfirmPasswordField = new javax.swing.JPasswordField();
        ChangPasswordButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ErrorLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ĐỔI MẬT KHẨU");
        setBackground(new java.awt.Color(106, 197, 254));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đổi mật khẩu của bạn");

        usernameLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        usernameLabel.setText("Tên tài khoản");

        CurrentPasswordLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        CurrentPasswordLabel.setText("Mật khẩu hiện tại");

        CurrentPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CurrentPasswordFieldKeyPressed(evt);
            }
        });

        NewPasswordLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        NewPasswordLabel.setText("Mật khẩu mới");

        NewPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NewPasswordFieldKeyPressed(evt);
            }
        });

        ShowUserNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ShowUserNameLabel.setText("username");

        ConfirmPasswordLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        ConfirmPasswordLabel.setText("Xác nhận mật khẩu");

        ConfirmPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ConfirmPasswordFieldKeyPressed(evt);
            }
        });

        ChangPasswordButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        ChangPasswordButton.setText("Đổi mật khẩu");
        ChangPasswordButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChangPasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangPasswordButtonActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/change_password_bg.png"))); // NOI18N

        ErrorLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        ErrorLabel.setForeground(new java.awt.Color(255, 0, 0));
        ErrorLabel.setText("ERROR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ShowUserNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(CurrentPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NewPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ConfirmPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ConfirmPasswordField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(NewPasswordField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(CurrentPasswordField, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(ChangPasswordButton)))
                .addContainerGap(38, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(152, 152, 152))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usernameLabel)
                            .addComponent(ShowUserNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CurrentPasswordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CurrentPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(NewPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(ConfirmPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ConfirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(ErrorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(ChangPasswordButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ChangPasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangPasswordButtonActionPerformed
        // TODO add your handling code here:
        if(!(new String(NewPasswordField.getPassword()).equals(new String(ConfirmPasswordField.getPassword()))))
            JOptionPane.showMessageDialog(null, "Mật xác nhận không trùng với mật khẩu mới",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
        else if(new String(CurrentPasswordField.getPassword()).equals("")||
                new String(NewPasswordField.getPassword()).equals("")||
                new String(ConfirmPasswordField.getPassword()).equals(""))
        {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đủ thông tin",
                        "Lỗi!", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            int result=acccon.ChangePassword(ShowUserNameLabel.getText(), 
                                            new String(CurrentPasswordField.getPassword()), 
                                            new String(NewPasswordField.getPassword()));
            if(result==1)
            {
                JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công");
                CurrentPasswordField.setText("");
                NewPasswordField.setText("");
                ConfirmPasswordField.setText("");
            }
            else
            {
                CurrentPasswordField.setText("");
                NewPasswordField.setText("");
                ConfirmPasswordField.setText("");
            }
        }
    }//GEN-LAST:event_ChangPasswordButtonActionPerformed

    private void CurrentPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CurrentPasswordFieldKeyPressed
        // TODO add your handling code here:
        LimitPassword(CurrentPasswordField, evt, 30);
    }//GEN-LAST:event_CurrentPasswordFieldKeyPressed

    private void NewPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NewPasswordFieldKeyPressed
        // TODO add your handling code here:
        LimitPassword(NewPasswordField, evt, 30);
    }//GEN-LAST:event_NewPasswordFieldKeyPressed

    private void ConfirmPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ConfirmPasswordFieldKeyPressed
        // TODO add your handling code here:
        LimitPassword(ConfirmPasswordField, evt, 30);
    }//GEN-LAST:event_ConfirmPasswordFieldKeyPressed

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
            java.util.logging.Logger.getLogger(ChangePasswordScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChangePasswordScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChangePasswordScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChangePasswordScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChangePasswordScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChangPasswordButton;
    private javax.swing.JPasswordField ConfirmPasswordField;
    private javax.swing.JLabel ConfirmPasswordLabel;
    private javax.swing.JPasswordField CurrentPasswordField;
    private javax.swing.JLabel CurrentPasswordLabel;
    private javax.swing.JLabel ErrorLabel;
    private javax.swing.JPasswordField NewPasswordField;
    private javax.swing.JLabel NewPasswordLabel;
    private javax.swing.JLabel ShowUserNameLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
