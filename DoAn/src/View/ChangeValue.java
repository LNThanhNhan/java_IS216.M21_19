/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

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
    
    public static int AcademicRankInt(String value)
    {
        switch(value)
        {
            case "Cử nhân": 
                return 1 ;
            case "Bác sĩ":
                return 2;
            case "Thạc sĩ":
                return 3;    
            case "Tiến sĩ":
                return 4;
            case "Giáo sư/Phó giáo sư":
                return 5;    
            case "Chuyên khoa 1":
                return 6;
            case "Chuyên khoa 2":
                return 7;
            case "Sinh viên năm cuối/kế cuối Y Dược":
                return 8;
            case "Kỹ thuật viên":
                return 9;
        }
        return -1;
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
    
    public static int SubjectInt(String value)
    {
        switch(value)
        {
            case "Bác sĩ chuyên khoa": 
                return 1;
            case "Chuyên viên tâm lý":
                return 2;
            case "Dược sĩ":
                return 3 ;    
            case "Điều dưỡng, hộ sinh":
                return 4 ;
            case "Sinh viên năm cuối/kế cuối Y Dược":
                return 5;   
        }
        return -1;
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
    
    public static int GenderInt(String value)
    {
        if(value=="Nam")
            return 1;
        else if(value=="Nữ")
            return 0;
        else
            return -1;
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
    
    public static int SupplyStatusInt(String value)
    {
        switch(value)
        {
            case "Đã mở": 
                return 1;
            case "Đã xác thực":
                return 2;
            case "Đang chờ":
                return 3;
            case "Hoàn thành":
                return 4;
            case "Đã hủy":
                return 5;
        }
        return -1;
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
    
    public static int PersonStatusInt(String value)
    {
        if(value=="Đã bị khóa")
            return 1;
        else if(value=="Đang sử dụng")
            return 0;
        else
            return -1;
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
    
    public static String Ishas(int value) {
        if(value==1)
            return "Có";
        else if(value==0)
            return "Không";
        else
            return "ERROR";
    }
    
    public static int IshasInt(String value) {
        if(value=="Có")
            return 1;
        else if(value=="Không")
            return 0;
        else
            return -1;
    }
    
    
    
    /*
    public static String UserName(String value) {
        if (value == "thường")
            return "";
        else if (value == "hotline");
            return value;
}*/
     public static String[] getProvince()
    {
        String province[]={"Tỉnh/Thành phố",
        "Hà Nội","Hồ Chí Minh","An Giang","Bà Rịa – Vũng Tàu","Bắc Giang",
        "Bắc Kạn","Bạc Liêu","Bắc Ninh","Bến Tre","Bình Định","Bình Dương",
        "Bình Phước","Bình Thuận","Cà Mau","Cần Thơ","Cao Bằng","Đà Nẵng",
        "Đắk Lắk","Đắk Nông","Điện Biên","Đồng Nai","Đồng Tháp","Gia Lai",
        "Hà Giang","Hà Nam","Hà Tĩnh","Hải Dương","Hải Phòng","Hậu Giang",
        "Hòa Bình","Hưng Yên","Khánh Hòa","Kiên Giang","Kon Tum","Lai Châu",
        "Lâm Đồng","Lạng Sơn","Lào Cai","Long An","Nam Định","Nghệ An",
        "Ninh Bình","Ninh Thuận","Phú Thọ","Phú Yên","Quảng Bình","Quảng Nam",
        "Quảng Ngãi","Quảng Ninh","Quảng Trị","Sóc Trăng","Sơn La","Tây Ninh",
        "Thái Bình","Thái Nguyên","Thanh Hóa","Thừa Thiên Huế","Tiền Giang",
        "Trà Vinh","Tuyên Quang","Vĩnh Long","Vĩnh Phúc","Yên Bái"
    };
        return province;
    }
     
    public static String[] getAcademicRank()
    {
        String AcademicRank[]={"Học hàm/Học vị","Cử nhân","Bác sĩ","Thạc sĩ","Tiến sĩ","Giáo sư/Phó giáo sư",
        "Chuyên khoa 1","Chuyên khoa 2", "Sinh viên năm cuối/kế cuối Y Dược","Kỹ thuật viên"
    };
        return AcademicRank;
    }
    
     public static String[] getSubject()
    {
        String Subject[]={"Chuyên khoa","Bác sĩ chuyên khoa","Chuyên viên tâm lý","Dược sĩ","Tiến sĩ","Điều dưỡng, hộ sinh",
        "Sinh viên năm cuối/kế cuối Y Dược"
    };
        return Subject;
    }
     
    public static String getComboBoxValue(JComboBox cbb){ 
        String string ="";
            if (cbb.getSelectedIndex() == 0) {
                string = "";
            } else {
                string = cbb.getSelectedItem().toString();
            }
        return string;
    }
    
    public static int getGender(JRadioButton rd){ 
        if (rd.isSelected()==true) {
            return 1;
            } else
            return 0;
    }
    
    public static int getValueCheckBox(JCheckBox cb){ 
        if(cb.isSelected()==true)
            return 1;
        else
            return 0;
    }
    
    public static boolean getValueCheckBoxBoolean(int cb){ 
        if(cb==1)
            return true;
        else
            return false;
    }
}
