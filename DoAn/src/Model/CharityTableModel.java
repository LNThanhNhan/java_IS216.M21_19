/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import static View.ChangeValue.Ishas;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MyPC
 */
public class CharityTableModel {
    private String[] listColumn = {"Mã", "Tên đăng nhập", "Tên"
            , "Số điện thoại","Tỉnh/Thành phố", "Quận/huyện", "Phường"
            , "Địa chỉ", "Có lương thực", "Có nhu yếu phẩm","Có vật dụng", "Điểm"};
    
    public DefaultTableModel setCharityTable(ArrayList<Charity> listItem){    
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        
        dtm.setColumnIdentifiers(listColumn);
        int rows=listItem.size();
        int column = listColumn.length;
          for (Charity charity : listItem)
            {
                dtm.addRow(new Object[]{charity.getIdchar(), charity.getUsername(),
                        charity.getName(),charity.getPhone(),charity.getProvince(),
                        charity.getDistrict(), charity.getTown(), charity.getAddress(), 
                        Ishas(charity.getHasfood()), Ishas(charity.getHasnecess()), 
                        Ishas(charity.getHasequip()), charity.getPoint()});
            }
        return dtm;
    }
}
