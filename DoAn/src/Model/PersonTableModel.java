/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import static View.ChangeValue.Gender;
import static View.ChangeValue.PersonStatus;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MyPC
 */
public class PersonTableModel {
    private String[] listColumn = {"Mã", "Tên đăng nhập", "Họ và tên"
            , "Giới tính", "Số điện thoại","Tỉnh/Thành phố", "Quận/huyện", "Phường"
            , "Địa chỉ", "Trạng thái"};
    
    public DefaultTableModel setAdvisoryTable(ArrayList<Person> listItem){    
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        
        dtm.setColumnIdentifiers(listColumn);
        int rows=listItem.size();
        int column = listColumn.length;
          for (Person person : listItem)
            {
                dtm.addRow(new Object[]{person.getIdper(), person.getUsername(),
                        person.getName(), Gender(person.getGender()), person.getPhone(),
                        person.getProvince(), person.getDistrict(), person.getTown(),
                        person.getAddress(), PersonStatus(person.getStatus())});
            }
        return dtm;
    }
}

