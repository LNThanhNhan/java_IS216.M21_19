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
//class cua yeu cau tiep te
public class Supply {
    //ma cua yeu cau tiep te
    private int idsup;
    
    //ma cua nguoi can giup do
    private int idper;
    
    //ma cua trung tam thien nguyen nhan yeu cau
    private int idchar;
    
    //ngay tao yeu cau tiep te
    private Date created;
    
    //yeu cau tiep te co can luong thuc hay khong?
    private int needfood;
    
    //yeu cau tiep te co can nhu yeu pham hay khong?
    private int neednecess;
    
    //yeu cau tiep te co can vat trang y te hay khong?
    private int needequip;
    
    //trang thai cau yeu tiep te
    //Gom: 1.Da mo 2.Da xac thuc 3.Dang cho
    //4.Hoan thanh 5,Da huy
    private int status;
    
    //Mo ta chi tiet cua yeu cau tiep te
    private String detail;

    public Supply() {
    }

    public int getIdsup() {
        return idsup;
    }

    public void setIdsup(int idsup) {
        this.idsup = idsup;
    }

    public int getIdper() {
        return idper;
    }

    public void setIdper(int idper) {
        this.idper = idper;
    }

    public int getIdchar() {
        return idchar;
    }

    public void setIdchar(int idchar) {
        this.idchar = idchar;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getNeedfood() {
        return needfood;
    }

    public void setNeedfood(int needfood) {
        this.needfood = needfood;
    }

    public int getNeednecess() {
        return neednecess;
    }

    public void setNeednecess(int neednecess) {
        this.neednecess = neednecess;
    }

    public int getNeedequip() {
        return needequip;
    }

    public void setNeedequip(int needequip) {
        this.needequip = needequip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
}
