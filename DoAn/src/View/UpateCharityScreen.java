/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View;

import Process.CharityController;
import View.*;
import static Process.CharityController.getNextValueCharity;
import ConnectDB.OracleConnection;
import Model.*;
import static View.ChangeValue.*;
import java.awt.event.KeyEvent;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;


/**
 *
 * @author MyPC
 */
public class UpateCharityScreen extends javax.swing.JDialog {

    /**
     * Creates new form AddcharityScreen
     */
    EmployeeScreen  emp = new EmployeeScreen();
    public UpateCharityScreen(java.awt.Frame parent, boolean modal, Charity charity) {
        super(parent, modal);
        initComponents();
        emp = (EmployeeScreen) parent;
        setView(charity);
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
        }
        else
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE ){
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
    
    public void setView(Charity charity) {
        
        // set data
        IdcharTextField.setText(String.valueOf(charity.getIdchar()));
        
        ProvinceComboBox.setModel(new DefaultComboBoxModel(getProvince()));
        ProvinceComboBox.setSelectedItem(charity.getProvince()); 
        
        NameTextField.setText(charity.getName());
        PhoneTextField.setText(charity.getPhone());
        DistrictTextField.setText(charity.getDistrict());      
        TownTextField.setText(charity.getTown());
        AddressTextField.setText(charity.getAddress());
        PointTextField.setText(String.valueOf(charity.getPoint()));
        HasFoodCheckBox.setSelected(getValueCheckBoxBoolean(charity.getHasfood()));
        HasNecessCheckBox.setSelected(getValueCheckBoxBoolean(charity.getHasnecess()));
        HasEquipCheckBox.setSelected(getValueCheckBoxBoolean(charity.getHasequip()));
        
    }   
    
    public void setEvent(){ 
        
        int check = -1;
        if ((PhoneTextField.getText().length()) < 10) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!",
                    "Lỗi!", JOptionPane.ERROR_MESSAGE);
        } else {
            Charity charity = new Charity();
            charity.setIdchar(parseInt(IdcharTextField.getText()));
            charity.setName(NameTextField.getText());
            charity.setDistrict(DistrictTextField.getText());
            charity.setTown(TownTextField.getText());
            charity.setAddress(AddressTextField.getText());
            
            if (ProvinceComboBox.getSelectedIndex() != 0) {
                charity.setProvince(String.valueOf(ProvinceComboBox.getSelectedItem()));
            } else
                charity.setProvince("");  
            
            charity.setPhone(PhoneTextField.getText());
            charity.setHasfood(getValueCheckBox(HasFoodCheckBox));
            charity.setHasnecess(getValueCheckBox(HasNecessCheckBox));
            charity.setHasequip(getValueCheckBox(HasEquipCheckBox));
//
//            try {
//            Connection con = OracleConnection.getOracleConnection();
//            int i = -1;
//            i = Connection.TRANSACTION_READ_COMMITTED;
////            i = Connection.TRANSACTION_SERIALIZABLE;
//            con.setAutoCommit(false);
//            con.setTransactionIsolation(i);
//            check = CharityController.UpdateCharityLostUpdate(con,charity);
//            if(check==0){
//            JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công!",
//                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
//            emp.setTableManageCharity();
//            emp.resizeColumnWidth(emp.getCharityTable());
//            this.dispose(); 
//            Thread.sleep(60000);
//            
//            con.commit();
//            }
//            
//            
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//              
//            

            emp.setTableManageCharity();
            emp.resizeColumnWidth(emp.getCharityTable());
            
            
            check = CharityController.UpdateCharity(charity);
            
            emp.setTableManageCharity();
            emp.resizeColumnWidth(emp.getCharityTable());
            
            if(check ==0){
            JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công!",
                        "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
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

        app1 = new com.k33ptoo.App();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        IdcharTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        DistrictTextField = new javax.swing.JTextField();
        TownTextField = new javax.swing.JTextField();
        AddressTextField = new javax.swing.JTextField();
        NameTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        PhoneTextField = new javax.swing.JTextField();
        ProvinceComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        PointTextField = new javax.swing.JTextField();
        ErrorLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        HasFoodCheckBox = new javax.swing.JCheckBox();
        HasNecessCheckBox = new javax.swing.JCheckBox();
        HasEquipCheckBox = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(106, 128, 254));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin trung tâm thiện nguyện", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel1.setText("Mã người dùng");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setText("Tên");

        IdcharTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IdcharTextField.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel6.setText("Tỉnh/Thành phố");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setText("Quận/Huyện");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setText("Phường");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setText("Địa chỉ");

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

        NameTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NameTextFieldKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setText("Số điện thoại");

        PhoneTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PhoneTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PhoneTextFieldKeyPressed(evt);
            }
        });

        ProvinceComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProvinceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel10.setText("Điểm");

        PointTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PointTextField.setEnabled(false);

        ErrorLabel.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        ErrorLabel.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(NameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                    .addComponent(IdcharTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(ProvinceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TownTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PointTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DistrictTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(IdcharTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(DistrictTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(TownTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(NameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(AddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(PhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(PointTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(ProvinceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(ErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin vật phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 14))); // NOI18N

        HasFoodCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        HasFoodCheckBox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        HasFoodCheckBox.setSelected(true);
        HasFoodCheckBox.setText("Lương thực");
        HasFoodCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HasFoodCheckBoxActionPerformed(evt);
            }
        });

        HasNecessCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        HasNecessCheckBox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        HasNecessCheckBox.setText("Nhu yếu phẩm");

        HasEquipCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        HasEquipCheckBox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        HasEquipCheckBox.setText("Vật dụng");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(HasFoodCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(181, 181, 181)
                .addComponent(HasNecessCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(HasEquipCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HasEquipCheckBox)
                    .addComponent(HasNecessCheckBox)
                    .addComponent(HasFoodCheckBox))
                .addContainerGap(31, Short.MAX_VALUE))
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(9, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setEvent();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void HasFoodCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HasFoodCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HasFoodCheckBoxActionPerformed

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
            java.util.logging.Logger.getLogger(UpateCharityScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpateCharityScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpateCharityScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpateCharityScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private javax.swing.JCheckBox HasEquipCheckBox;
    private javax.swing.JCheckBox HasFoodCheckBox;
    private javax.swing.JCheckBox HasNecessCheckBox;
    private javax.swing.JTextField IdcharTextField;
    private javax.swing.JTextField NameTextField;
    private javax.swing.JTextField PhoneTextField;
    private javax.swing.JTextField PointTextField;
    private javax.swing.JComboBox<String> ProvinceComboBox;
    private javax.swing.JTextField TownTextField;
    private com.k33ptoo.App app1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
