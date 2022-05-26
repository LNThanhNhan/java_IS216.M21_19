/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
public class AdvisoryTableModel {
    private String[] listColumn = {"Mã tư vấn","Họ và Tên", "Giới tính", "Năm sinh","Ngày tạo", "Tỉnh/Thành phố"}; 
    public DefaultTableModel setAdvisoryTable(ArrayList<HashMap> listItem) {
        
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        dtm.setColumnIdentifiers(listColumn);
        int rows=listItem.size();
        int column = listColumn.length;
        Object[] obj = null;
        if(rows>0)
        {
            for (int i = 0; i < rows; i++)
            {
                HashMap<String,String> ad=listItem.get(i);
                obj = new Object[column];
                obj[0]=ad.get("idad");
                obj[1]=ad.get("name");
                if(Integer.parseInt(ad.get("gender"))==1) obj[2]="Nam";else obj[2]="Nữ";
                obj[3]=ad.get("yearbirth");
                obj[4]=ad.get("created");
                obj[5]=ad.get("province");
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}
