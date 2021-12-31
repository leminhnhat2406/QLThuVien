/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author LocNe
 */
public class Book {
    private int id;
    private String ma;
    private String tenSach;
    private String tacGia;
    private String moTa;
    private String namXuatBan;
    private String ngayNhap;
    private String viTri;

    public Book(int id,String ma,String ten,String tacGia,String moTa,String nam,String ngayNhap,String viTri){
        this.id =id;
        this.tenSach =ten;
        this.ma =ma;
        this.moTa=moTa;
        this.namXuatBan= nam;
        this.ngayNhap=ngayNhap;
        this.viTri=viTri;
        this.tacGia=tacGia;
                
    }
    public Book(String ma,String ten,String tacGia,String moTa,String nam,String ngayNhap,String viTri){
        //this.id =id;
        this.tenSach =ten;
        this.ma =ma;
        this.moTa=moTa;
        this.namXuatBan= nam;
        this.ngayNhap=ngayNhap;
        this.viTri=viTri;
        this.tacGia=tacGia;
                
    }
    
    /**
     * @return the id
     */
   

    /**
     * @return the ma
     */
    public String getMa() {
        return ma;
    }

    /**
     * @param ma the ma to set
     */
    public void setMa(String ma) {
        this.ma = ma;
    }

    /**
     * @return the tenSach
     */
    public String getTenSach() {
        return tenSach;
    }

    /**
     * @param tenSach the tenSach to set
     */
    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    /**
     * @return the tacGia
     */
    public String getTacGia() {
        return tacGia;
    }

    /**
     * @param tacGia the tacGia to set
     */
    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    /**
     * @return the moTa
     */
    public String getMoTa() {
        return moTa;
    }

    /**
     * @param moTa the moTa to set
     */
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    /**
     * @return the namXuatBan
     */
    public String getNamXuatBan() {
        return namXuatBan;
    }

    /**
     * @param namXuatBan the namXuatBan to set
     */
    public void setNamXuatBan(String namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    /**
     * @return the ngayNhap
     */
    public String getNgayNhap() {
        return ngayNhap;
    }

    /**
     * @param ngayNhap the ngayNhap to set
     */
    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    /**
     * @return the viTri
     */
    public String getViTri() {
        return viTri;
    }

    /**
     * @param viTri the viTri to set
     */
    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
