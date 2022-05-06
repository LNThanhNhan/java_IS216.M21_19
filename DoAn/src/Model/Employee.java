/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
//class nhan vien trong he thong
public class Employee {
    //ma nhan vien
    private int idemp;
    
    //ten tai khoan cua nhan vien
    private String username;
    
    //ho ten nhan vien
    private String name;
    
    //gioi tinh 
    private int gender;
    
    //ngay vao lam
    private Date startdate;
    
    //so dien thoai
    private String phone;
    
    //Dia chi nha, so duong, phuong/thi xa, quan/huyen, tinh/thanh pho
    private String address;

    public Employee() {
    }

    public int getIdemp() {
        return idemp;
    }

    public void setIdemp(int idemp) {
        this.idemp = idemp;
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

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
