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
 * @author Tran Nhat Sinh
 */
public class AdvisoryPersonTableModel {
    private String[] listColumn = {"Mã tư vấn","Ngày tạo","Năm sinh","Chiều cao","Cân nặng","Tiền sử"}; 
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
                HashMap<String,String> ad =listItem.get(i);
                obj = new Object[column];
                obj[0]=ad.get("idad");
                obj[1]=ad.get("created");
                obj[2]=ad.get("yearbirth");
                obj[3]=ad.get("height");
                obj[4]=ad.get("weight");
                obj[5]=ad.get("pastmedicalhistory");
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}
