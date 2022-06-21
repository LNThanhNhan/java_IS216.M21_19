/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
//class nguoi can giup do
public class Person {
    //ma nguoi can giup do
    private int idper;
    
    //ten tai khoan cua nguoi can giup do
    private String username;
    
    //ho ten cua nguoi can giup do
    private String name;
    
    //gioi tinh
    private int gender;
    
    //so dien thoai
    private String phone;
    
    //Tinh/Thanh pho dang sinh song
    private String province;
    
    //Quan/Huyen
    private String district;
    
    //Phuong/Thi xa
    private String town;
    
    //Dia chi nha, so duong cua nguoi can giup do
    private String address;
    
    //Trang thai cua nguoi can giup do
    //0.Dang hoat dong 1.Da bi khoa
    private int status;

    public Person() {
    }

    public Person(int idper, String username, String name, int gender, String phone, String province, String district, String town, String address, int status) {
        this.idper = idper;
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.province = province;
        this.district = district;
        this.town = town;
        this.address = address;
        this.status = status;
    }

    public int getIdper() {
        return idper;
    }

    public void setIdper(int idper) {
        this.idper = idper;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
