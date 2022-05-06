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
//class yeu cau tu van
public class Advisory {
    //ma cua yeu cau tu van
    private int idad;
    
    //ma cua nguoi can giup do
    private int idper;
    
    //ma cua bac si nhan yeu cau tu van
    private int iddoc;
    
    //ngay tao yeu cau
    private Date created;
    
    //nam sinh cua nguoi can giup do
    private int yearbirth;
    
    //chieu cao cua nguoi can giup do
    private int height;
    
    //can nang cua nguoi can giup do
    private int weight;
    
    //trang thai yeu cau tu van
    //gom 3 trang thai: 1.Da mo 2.Dang cho 3.Hoan thanh
    private int status;
    
    //tien su benh an cua nguoi can giup do(neu co)
    private String pastmedicalhistory;
    
    //Mo ta chi tiet cua yeu cau tu van
    private String detail;

    public Advisory() {
    }

    public int getIdper() {
        return idper;
    }

    public void setIdper(int idper) {
        this.idper = idper;
    }

    public int getIdad() {
        return idad;
    }

    public void setIdad(int idad) {
        this.idad = idad;
    }

    public int getIddoc() {
        return iddoc;
    }

    public void setIddoc(int iddoc) {
        this.iddoc = iddoc;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getYearbirth() {
        return yearbirth;
    }

    public void setYearbirth(int yearbirth) {
        this.yearbirth = yearbirth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPastmedicalhistory() {
        return pastmedicalhistory;
    }

    public void setPastmedicalhistory(String pastmedicalhistory) {
        this.pastmedicalhistory = pastmedicalhistory;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    
}
