/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import static View.ChangeValue.Gender;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MyPC
 */
public class EmployeeTableModel {
    private String[] listColumn = {"Mã", "Tên đăng nhập", "Họ và tên"
            , "Giới tính", "Ngày bắt đầu","Số điện thoại", "Địa chỉ"};
    
    public DefaultTableModel setEmployeeTable(ArrayList<Employee> listItem){    
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        
        
        dtm.setColumnIdentifiers(listColumn);
        int rows=listItem.size();
        int column = listColumn.length;
          for (Employee employee : listItem)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String startDate = sdf.format(employee.getStartdate());
                        
                dtm.addRow(new Object[]{employee.getIdemp(), employee.getUsername(),
                        employee.getName(),Gender(employee.getGender()), 
                        startDate, employee.getPhone(),employee.getAddress()});
            }
        return dtm;
    }
}
