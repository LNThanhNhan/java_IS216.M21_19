/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View;

import Process.PersonController;
import Model.Person;
import static View.ChangeValue.PersonStatus;
import static View.ChangeValue.PersonStatusInt;
import static View.ChangeValue.getGender;
import static View.ChangeValue.getProvince;
import java.awt.event.KeyEvent;
import static java.lang.Integer.parseInt;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Nguyen Hoang Trung
 */
public class UpdatePersonScreen extends javax.swing.JDialog {

    /**
     * Creates new form UpdatePersonScreen
     */
    EmployeeScreen emp = new EmployeeScreen();
    public UpdatePersonScreen(java.awt.Frame parent, boolean modal, Person person) {
        super(parent, modal);
        initComponents();
        emp = (EmployeeScreen) parent;
        setView(person);
    }

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

    public void setView(Person person) {

        // set data
        IdperTextField.setText(String.valueOf(person.getIdper()));
        NameTextField.setText(person.getName());
        PhoneTextField.setText(person.getPhone());
        ProvinceComboBox.setModel(new DefaultComboBoxModel(getProvince()));
        ProvinceComboBox.setSelectedItem(person.getProvince());
        DistrictTextField.setText(person.getDistrict());
        TownTextField.setText(person.getTown());
        AddressTextField.setText(person.getAddress());
        StatusTextField.setText(PersonStatus(person.getStatus()));
        if (person.getGender() == 1) {
            MaleGenderRadioButton.setSelected(true);
            FeMaleGenderRadioButton.setSelected(false);
        } else if (person.getGender() == 0) {
            FeMaleGenderRadioButton.setSelected(true);
            MaleGenderRadioButton.setSelected(false);
        }
    }

    public void setEvent() {
        int check = -1;
        if ((PhoneTextField.getText().length()) < 10) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!",
                    "Lỗi!", JOptionPane.ERROR_MESSAGE);
        } else {
            Person person = new Person();
            person.setIdper(parseInt(IdperTextField.getText()));
            person.setName(NameTextField.getText());
            person.setGender(getGender(MaleGenderRadioButton));

            //Xử lý comboBox tỉnh/Thành phố
            if (ProvinceComboBox.getSelectedIndex() != 0) {
                person.setProvince(String.valueOf(ProvinceComboBox.getSelectedItem()));
            } else {
                person.setProvince("");
            }

            person.setDistrict(DistrictTextField.getText());
            person.setTown(TownTextField.getText());
            person.setAddress(AddressTextField.getText());
            person.setStatus(PersonStatusInt(StatusTextField.getText()));
            person.setPhone(PhoneTextField.getText());

            check = PersonController.UpdatePerson(person);
            emp.setTableManagePerson();
            emp.resizeColumnWidth(emp.getPersonTable());
        }

        if (check == 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công!", "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
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

        GendeButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        IdperTextField = new javax.swing.JTextField();
        NameTextField = new javax.swing.JTextField();
        PhoneTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        DistrictTextField = new javax.swing.JTextField();
        TownTextField = new javax.swing.JTextField();
        AddressTextField = new javax.swing.JTextField();
        StatusTextField = new javax.swing.JTextField();
        MaleGenderRadioButton = new javax.swing.JRadioButton();
        FeMaleGenderRadioButton = new javax.swing.JRadioButton();
        ProvinceComboBox = new javax.swing.JComboBox<>();
        ErrorLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(106, 128, 254));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin người cần giúp đỡ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 15))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel1.setText("Mã người dùng");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setText("Họ và tên");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setText("Giới tính");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setText("Số điện thoại");

        IdperTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IdperTextField.setEnabled(false);

        NameTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NameTextFieldKeyPressed(evt);
            }
        });

        PhoneTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PhoneTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PhoneTextFieldKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel6.setText("Tỉnh/Thành phố");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setText("Quận/Huyện");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setText("Phường");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setText("Địa chỉ");

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel10.setText("Trạng thái");

        DistrictTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        DistrictTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DistrictTextFieldKeyPressed(evt);
            }
        });

        TownTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TownTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TownTextFieldKeyPressed(evt);
            }
        });

        AddressTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        AddressTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddressTextFieldKeyPressed(evt);
            }
        });

        StatusTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        StatusTextField.setEnabled(false);

        MaleGenderRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        GendeButtonGroup.add(MaleGenderRadioButton);
        MaleGenderRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MaleGenderRadioButton.setText("Nam");

        FeMaleGenderRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        GendeButtonGroup.add(FeMaleGenderRadioButton);
        FeMaleGenderRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        FeMaleGenderRadioButton.setText("Nữ");

        ProvinceComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProvinceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ErrorLabel.setBackground(new java.awt.Color(255, 255, 255));
        ErrorLabel.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        ErrorLabel.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(ErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(141, 141, 141)
                                        .addComponent(FeMaleGenderRadioButton))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(52, 52, 52)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ProvinceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(PhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(39, 39, 39)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IdperTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MaleGenderRadioButton))))
                        .addGap(112, 112, 112)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AddressTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(StatusTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TownTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DistrictTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(IdperTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(DistrictTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(NameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(TownTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(MaleGenderRadioButton)
                    .addComponent(FeMaleGenderRadioButton)
                    .addComponent(jLabel9)
                    .addComponent(AddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(PhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(StatusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ProvinceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jButton1.setText("Lưu");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(711, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //if(UsernameTextField.getText().equals(null))
        setEvent();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void NameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NameTextFieldKeyPressed
        // TODO add your handling code here:
        ErrorLabel.setText("");
        LimitChar(NameTextField, evt, 30);
    }//GEN-LAST:event_NameTextFieldKeyPressed

    private void PhoneTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PhoneTextFieldKeyPressed
        // TODO add your handling code here:
        ErrorLabel.setText("");
        LimitCharPhone(PhoneTextField, evt, 10);
    }//GEN-LAST:event_PhoneTextFieldKeyPressed

    private void DistrictTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DistrictTextFieldKeyPressed
        // TODO add your handling code here:
        ErrorLabel.setText("");
        LimitChar(DistrictTextField, evt, 30);
    }//GEN-LAST:event_DistrictTextFieldKeyPressed

    private void TownTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TownTextFieldKeyPressed
        // TODO add your handling code here:
        ErrorLabel.setText("");
        LimitChar(TownTextField, evt, 30);
    }//GEN-LAST:event_TownTextFieldKeyPressed

    private void AddressTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddressTextFieldKeyPressed
        // TODO add your handling code here:
        ErrorLabel.setText("");
        LimitChar(AddressTextField, evt, 30);
    }//GEN-LAST:event_AddressTextFieldKeyPressed

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
            java.util.logging.Logger.getLogger(UpdatePersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdatePersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdatePersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdatePersonScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddressTextField;
    private javax.swing.JTextField DistrictTextField;
    private javax.swing.JLabel ErrorLabel;
    private javax.swing.JRadioButton FeMaleGenderRadioButton;
    private javax.swing.ButtonGroup GendeButtonGroup;
    private javax.swing.JTextField IdperTextField;
    private javax.swing.JRadioButton MaleGenderRadioButton;
    private javax.swing.JTextField NameTextField;
    private javax.swing.JTextField PhoneTextField;
    private javax.swing.JComboBox<String> ProvinceComboBox;
    private javax.swing.JTextField StatusTextField;
    private javax.swing.JTextField TownTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
