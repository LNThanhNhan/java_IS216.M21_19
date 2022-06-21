/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import static View.ChangeValue.Ishas;
import static View.ChangeValue.SupplyStatus;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    private String[] listColumnCharity = {"Mã tiếp tế","Họ và Tên","Ngày tạo", "Tỉnh/Thành phố"}; 
    public DefaultTableModel setSupplyTableCharity(ArrayList<HashMap> listItem) {
        
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        dtm.setColumnIdentifiers(listColumnCharity);
        int rows=listItem.size();
        int column = listColumnCharity.length;
        Object[] obj = null;
        if(rows>0)
        {
            for (int i = 0; i < rows; i++)
            {
                HashMap<String,String> ad=listItem.get(i);
                obj = new Object[column];
                obj[0]=ad.get("idsup");
                obj[1]=ad.get("name");
                obj[2]=ad.get("created");
                obj[3]=ad.get("province");
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
    
      private String[] listColumnperson = {"Mã tiếp tế","Ngày tạo","Lương thực","Nhu yếu phẩm","Vật dụng"}; 
    public DefaultTableModel setSupplyTableByPerson(ArrayList<HashMap> listItem) {
        
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        dtm.setColumnIdentifiers(listColumnperson);
        int rows=listItem.size();
        int column = listColumnperson.length;
        Object[] obj = null;
        if(rows>0)
        {
            for (int i = 0; i < rows; i++)
            {
                HashMap<String,String> sup =listItem.get(i);
                obj = new Object[column];
                obj[0]=sup.get("idsup");
                obj[1]=sup.get("created");
                if(Integer.parseInt(sup.get("needfood"))==1) obj[2]="Có";else obj[2]="Không";
                if(Integer.parseInt(sup.get("neednecess"))==1) obj[3]="Có";else obj[3]="Không";
                if(Integer.parseInt(sup.get("needequip"))==1) obj[4]="Có";else obj[4]="Không";
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}