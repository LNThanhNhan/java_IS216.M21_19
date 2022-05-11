/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
public class ChangeValue {
    public static String AcademicRank(int value)
    {
        switch(value)
        {
            case 1: 
                return "Cử nhân";
            case 2:
                return "Bác sĩ";
            case 3:
                return "Thạc sĩ";    
            case 4:
                return "Tiến sĩ";
            case 5:
                return "Giáo sư/Phó giáo sư";    
            case 6:
                return "Chuyên khoa 1";
            case 7:
                return "Chuyên khoa 2";
            case 8:
                return "Sinh viên năm cuối/kế cuối Y Dược";
            case 9:
                return "Kỹ thuật viên";
        }
        return "ERROR";
    }
    public static String Subject(int value)
    {
        switch(value)
        {
            case 1: 
                return "Bác sĩ chuyên khoa";
            case 2:
                return "Chuyên viên tâm lý";
            case 3:
                return "Dược sĩ";    
            case 4:
                return "Điều dưỡng, hộ sinh";
            case 5:
                return "Sinh viên năm cuối/kế cuối Y Dược";   
        }
        return "ERROR";
    }
    public static String Gender(int value)
    {
        if(value==1)
            return "Nam";
        else if(value==0)
            return "Nữ";
        else
            return "ERROR";
    }
    public static String AdvisoryStatus(int value)
    {
        switch(value)
        {
            case 1: 
                return "Đã mở";
            case 2:
                return "Đang chờ";
            case 3:
                return "Hoàn thành";    
        }
        return "ERROR";
    }
    public static String SupplyStatus(int value)
    {
        switch(value)
        {
            case 1: 
                return "Đã mở";
            case 2:
                return "Đã xác thực";
            case 3:
                return "Đang chờ";
            case 4:
                return "Hoàn thành";
            case 5:
                return "Đã hủy";
        }
        return "ERROR";
    }
    public static String PersonStatus(int value)
    {
        if(value==1)
            return "Đã bị khóa";
        else if(value==0)
            return "Đang sử dụng";
        else
            return "ERROR";
    }
    public static String AccountRole(int value)
    {
        switch(value)
        {
            case 1: 
                return "Người cần giúp đỡ";
            case 2:
                return "Nhân viên";
            case 3:
                return "Trung tâm";
            case 4:
                return "Bác sĩ";
        }
        return "ERROR";
    }
    public static String DateToString(Date date)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
        return sdf.format(date);
    }
}
