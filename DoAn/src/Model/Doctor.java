/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
//class Bac si
public class Doctor {
    //ma bac si
    private int iddoc;
    
    //ten tai khoan cua bac si
    private String username;
    
    //ho ten bac si
    private String name;
    
    //gioi tinh 
    private int gender;
    
    //so dien thoai 
    private String phone;
    
    //Hoc ham hoc vi cua bac si
    //Gom: 1.Cu nhan 2.Bac si 3.Thac si
    //4.Tien si 5.Giao su/Pho giao su
    //6.Chuyen khoa 1  7.Chuyen khoa 2
    //8.Sinh vien nam cuoi/ke cuoi Y Duoc
    //9: Ky thuat vien
    private int accademicrank;
    
    //Bac si thuoc doi tuong
    //Gom: 1.Bac si chuyên khoa
    //2.Chuyen vien tam ly 3.Duoc si
    //4.Dieu duong, ho sinh
    //5.Sinh viên nam cuoi/ke cuoi Y Duoc
    private int subject;
    
    //Don vi bac si dang cong tac
    private String workunits;
    
    //Tinh/Thanh pho bac si dang sinh song
    private String province;

    public Doctor() {
    }
    
    public Doctor(int iddoc, String username, String name, int gender, String phone, int accademicrank, int subject, String workunits, String province) {
        this.iddoc = iddoc;
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.accademicrank = accademicrank;
        this.subject = subject;
        this.workunits = workunits;
        this.province = province;
    }
    
    public int getIddoc() {
        return iddoc;
    }

    public void setIddoc(int iddoc) {
        this.iddoc = iddoc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhonez(String phone) {
        this.phone = phone;
    }

    public int getAccademicrank() {
        return accademicrank;
    }

    public void setAccademicrank(int accademicrank) {
        this.accademicrank = accademicrank;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public String getWorkunits() {
        return workunits;
    }

    public void setWorkunits(String workunits) {
        this.workunits = workunits;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    
}
