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
public class Member {
    private int id;
    private String ma;
    private String hoten;  
    private String gioitinh;
    private String ngaysinh;
    private String doituong;
    private String bophan;
    private String hanthe;
    private String email;
    private String diachi;
    private String sdt;

    public Member(int id,String ma, String hoten,String gioitinh,  String ngaysinh, String doituong, String bophan, String email, String diachi, String sdt, String hanthe) {
        this.ma = ma;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.doituong = doituong;
        this.bophan = bophan;
        
        this.email = email;
        this.diachi = diachi;
        this.sdt = sdt;
        this.id= id;
        this.hanthe = hanthe;
    }
      public Member(int id,String ma, String hoten,String gioitinh,  String ngaysinh, String doituong, String bophan, String email, String diachi, String sdt) {
        this.ma = ma;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.doituong = doituong;
        this.bophan = bophan;
        
        this.email = email;
        this.diachi = diachi;
        this.sdt = sdt;
        this.id= id;
        this.hanthe = hanthe;
    }
    public Member(String ma, String hoten,String gioitinh, String ngaysinh, String doituong, String bophan, String email, String diachi, String sdt) {
        this.ma = ma;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.doituong = doituong;
        this.bophan = bophan;
        
        this.email = email;
        this.diachi = diachi;
        this.sdt = sdt;
       
//        this.cbgioitinh=cbGioitinh;
    }

  

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
         * @return the hoten
         */
        public String getHoten() {
            return hoten;
        }

        /**
         * @param hoten the hoten to set
         */
        public void setHoten(String hoten) {
            this.hoten = hoten;
        }

     

        /**
         * @return the ngaysinh
         */
        public String getNgaysinh() {
            return ngaysinh;
        }

        /**
         * @param ngaysinh the ngaysinh to set
         */
        public void setNgaysinh(String ngaysinh) {
            this.ngaysinh = ngaysinh;
        }

        /**
         * @return the doituong
         */
        public String getDoituong() {
            return doituong;
        }

        /**
         * @param doituong the doituong to set
         */
        public void setDoituong(String doituong) {
            this.doituong = doituong;
        }

        /**
         * @return the bophan
         */
        public String getBophan() {
            return bophan;
        }

        /**
         * @param bophan the bophan to set
         */
        public void setBophan(String bophan) {
            this.bophan = bophan;
        }

        /**
         * @return the hanthe
         */
        public String getHanthe() {
            return hanthe;
        }

        /**
         * @param hanthe the hanthe to set
         */
        public void setHanthe(String hanthe) {
            this.hanthe = hanthe;
        }

        /**
         * @return the email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email the email to set
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return the diachi
         */
        public String getDiachi() {
            return diachi;
        }

        /**
         * @param diachi the diachi to set
         */
        public void setDiachi(String diachi) {
            this.diachi = diachi;
        }

        /**
         * @return the sdt
         */
        public String getSdt() {
            return sdt;
        }

        /**
         * @param sdt the sdt to set
         */
        public void setSdt(String sdt) {
            this.sdt = sdt;
        }
        /**
     * @return the gioitinh
     */
    public String getGioitinh() {
        return gioitinh;
    }

    /**
     * @param gioitinh the gioitinh to set
     */
    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
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

    /**
     * @return the gioitinhID


    /**
     * @return the gioitinh
     */
 
}
