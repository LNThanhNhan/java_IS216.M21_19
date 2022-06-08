/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Luong Nguyen Thanh Nhan
 */
//class trung tam thien nguyen
public class Charity {
    //ma cua trung tam
    private int idchar;
    
    //Ten tai khoan cua trung tam
    private String username;
    
    //Ten cua trung tam
    private String name;
    
    //So dien thoai cua trung tam
    private String phone;
    
    //Tinh/Thanh pho noi trung tam dang hoat dong
    private String province;
    
    //Quan/Huyen noi trung tam dang hoat dong
    private String district;
    
    //Phuong/Thi xa noi trung tam dang hoat dong
    private String town;
    
    //Dia chi, duong noi trung tam dang hoat dong
    private String address;
    
    //Trung tam co the cung cap luong thuc duoc hay khong?
    private int hasfood;
    
    //Trung tam co the cung cap nhu yeu pham duoc hay khong?
    private int hasnecess;
    
    //Trung tam co the cung cap vat trang y te duoc hay khong?
    private int hasequip;
    
    //Diem hoat dong cua trung tam
    private int point;

    public Charity() {
    }
public Charity(int idchar, String username, String name, String phone, String province, String district, String town, String address, int hasfood, int hasnecess, int hasequip, int point) {
        this.idchar = idchar;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.district = district;
        this.town = town;
        this.address = address;
        this.hasfood = hasfood;
        this.hasnecess = hasnecess;
        this.hasequip = hasequip;
        this.point = point;
    }
    public int getIdchar() {
        return idchar;
    }

    public void setIdchar(int idchar) {
        this.idchar = idchar;
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

    public int getHasfood() {
        return hasfood;
    }

    public void setHasfood(int hasfood) {
        this.hasfood = hasfood;
    }

    public int getHasnecess() {
        return hasnecess;
    }

    public void setHasnecess(int hasnecess) {
        this.hasnecess = hasnecess;
    }

    public int getHasequip() {
        return hasequip;
    }

    public void setHasequip(int hasequip) {
        this.hasequip = hasequip;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
}
