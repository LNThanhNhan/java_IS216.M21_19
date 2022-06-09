/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import static View.ChangeValue.Ishas;
import static View.ChangeValue.SupplyStatus;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MyPC
 */
public class SupplyTableModel {
    private String[] listColumn = {"Mã yêu cầu", "Mã người dùng", "Mã trung tâm"
            , "Ngày tạo","Cần thức ăn", "Cần nhu yếu %nphẩm", "Cần vật dụng"
            , "Trạng thái", "Chi tiết"};
    
    public DefaultTableModel setSupplyTable(ArrayList<Supply> listItem){    
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        
        dtm.setColumnIdentifiers(listColumn);
        int rows=listItem.size();
        int column = listColumn.length;
          for (Supply Supply : listItem)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String Created = sdf.format(Supply.getCreated());
                
                dtm.addRow(new Object[]{Supply.getIdsup(), Supply.getIdper(),
                        Supply.getIdchar(), Created,
                        Ishas(Supply.getNeedfood()), Ishas(Supply.getNeednecess()), 
                        Ishas(Supply.getNeedequip()), SupplyStatus(Supply.getStatus()), Supply.getDetail()});
            }
        return dtm;
    }
}