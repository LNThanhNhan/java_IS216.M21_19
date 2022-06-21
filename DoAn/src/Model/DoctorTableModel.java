/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import static View.ChangeValue.Gender;
import static View.ChangeValue.PersonStatus;
import static View.ChangeValue.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MyPC
 */
public class DoctorTableModel {
    private String[] listColumn = {"Mã", "Tên đăng nhập", "Họ và tên"
            , "Giới tính", "Số điện thoại", "Học hàm/Học vị","Chuyên khoa", "Đơn vị", "Tỉnh/Thành phố"};
    
    public DefaultTableModel setDoctorTable(ArrayList<Doctor> listItem){    
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        
        dtm.setColumnIdentifiers(listColumn);
        int rows=listItem.size();
        int column = listColumn.length;
          for (Doctor doctor : listItem)
            {
                dtm.addRow(new Object[]{doctor.getIddoc(), doctor.getUsername(),
                        doctor.getName(), Gender(doctor.getGender()), doctor.getPhone(),
                        AcademicRank(doctor.getAccademicrank()), Subject(doctor.getSubject()),doctor.getWorkunits(),
                        doctor.getProvince()});
            }
        return dtm;
    }
}
