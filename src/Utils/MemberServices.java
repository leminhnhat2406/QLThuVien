/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Alert;
import pojo.Book;
import pojo.Member;
import pojo.Borrow;

/**
 *
 * @author LocNe
 */
public class MemberServices {

    public static List<Member> getMembers(String keyword) throws SQLException {
        String sql = "SELECT * FROM thedocgia ";

        Connection conn = JDBCconn.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);

        ResultSet rs = stm.executeQuery();

        List<Member> books = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String ma = rs.getString("madocgia");
            String hoten = rs.getString("hoten");
            String gioitinh = rs.getString("gioitinh");
            String ngaysinh = rs.getString("ngaysinh");
            String doituong = rs.getString("doituong");
            String bophan = rs.getString("bophan");
            String hanthe = rs.getString("hanthe");
            String email = rs.getString("email");
            String diachi = rs.getString("diachi");
            String sdt = rs.getString("sdt");

            Member q = new Member(id, ma, hoten, gioitinh, ngaysinh, doituong, bophan, hanthe, email, diachi, sdt);

            books.add(q);

        }

        return books;

    }

    public static boolean addM(Member b, String s) throws SQLException {
        if (!b.getMa().equals("") && !b.getHoten().equals("") && !b.getGioitinh().equals("") 
                && !b.getNgaysinh().equals("")&& !b.getDoituong().equals("") && !b.getBophan().equals("") 
                && !b.getEmail().equals("")&& !b.getDiachi().equals("")
                && !b.getSdt().equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Connection conn = JDBCconn.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement stm = conn.prepareStatement(s);
            stm.setString(1, b.getMa());
            stm.setString(2, b.getHoten());
            stm.setString(3, b.getGioitinh());
            stm.setString(4, b.getNgaysinh());
            stm.setString(5, b.getDoituong());
            stm.setString(6, b.getBophan());
            stm.setString(7, b.getDiachi());
            stm.setString(8, b.getEmail());
            stm.setString(9, b.getSdt());

            Random so = new Random();
//        
            int rd = so.nextInt(2);
            if (rd == 1) {
                stm.setString(10, "còn hạn");
            } else {
                stm.setString(10, "hết hạn");
            }
            stm.executeUpdate();

            conn.commit();
            return true;
        }
        return false;

    }

    public static boolean addMember(Member m) throws SQLException {
        String sql = "INSERT INTO thedocgia(madocgia,hoten,gioitinh,ngaysinh,"
                + "doituong,bophan,email,diachi,sdt,hanthe)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";
        if(addM(m, sql)) {
            return true;
        }
        return false;
    }
//    

   

    public static List<Member> Search(String keyword) throws SQLException {
        List<Member> books = new ArrayList<>();
        String sql = "SELECT * FROM thedocgia ";
        if (keyword != null && !keyword.isEmpty()) {
            sql += "WHERE hoten like?";
        }

        Connection conn = JDBCconn.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        if (keyword != null && !keyword.trim().isEmpty())            
           try {
            stm.setString(1, String.format("%%%s%%", keyword.trim()));
        } catch (SQLException sQLException) {
        }

        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Member q = new Member(rs.getInt("id"), rs.getString("madocgia"),
                    rs.getString("hoten"), rs.getString("gioitinh"),
                    rs.getString("ngaysinh"), rs.getString("doituong"),
                    rs.getString("bophan"), rs.getString("email"), rs.getString("diachi"),
                    rs.getString("sdt"), rs.getString("hanthe"));
            books.add(q);
        }
        return books;
    }

    public static String getDue(int m) throws SQLException {
        Connection conn = JDBCconn.getConnection();
        String sql = "SELECT hanthe FROM thedocgia WHERE id=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, m);

        ResultSet rs = stm.executeQuery();
        String kq = null;
        while (rs.next()) {
            kq = rs.getString("hanthe");
        }
        return kq;

    }

    public static Alert getAlertInfo(String content, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setContentText(content);

        return a;
    }

    public static void loadMuonSach() {

    }

}
