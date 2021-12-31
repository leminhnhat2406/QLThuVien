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
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import pojo.Book;
import pojo.Borrow;
import pojo.Member;

/**
 *
 * @author USER
 */
public class BookServices {

    public static void addorUpdateBook(Book book, String sql) throws SQLException {
        Connection conn = JDBCconn.getConnection();

        conn.setAutoCommit(false);

        //add Question
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, book.getId());
        stm.setString(2, book.getMa());
        stm.setString(3, book.getTenSach());
        stm.setString(4, book.getTacGia());
        stm.setString(5, book.getMoTa());
        stm.setString(6, book.getNamXuatBan());
        stm.setString(7, book.getNgayNhap());
        stm.setString(8, book.getViTri());

        stm.executeUpdate();
    }

    public static boolean addBookk(Book book, String sql) throws SQLException {
        if (!book.getMa().equals("") && !book.getTenSach().equals("") && !book.getTacGia().equals("") && !book.getMoTa().equals("")
                && !book.getNamXuatBan().equals("") && !book.getNgayNhap().equals("") && !book.getViTri().equals("")) {
            Connection conn = JDBCconn.getConnection();

            conn.setAutoCommit(false);

            //add Question
            PreparedStatement stm = conn.prepareStatement(sql);
            //stm.setString(1, book.getId()));
            stm.setString(1, book.getMa());
            stm.setString(2, book.getTenSach());
            stm.setString(3, book.getTacGia());
            stm.setString(4, book.getMoTa());
            stm.setString(5, book.getNamXuatBan());
            stm.setString(6, book.getNgayNhap());
            stm.setString(7, book.getViTri());

            stm.executeUpdate();

            conn.commit();
            return true;

        }
        return false;

    }

    public static boolean addBook(Book book) throws SQLException {

        String sql = "INSERT INTO book(masach,tensach,tacgia,motasach,"
                + "namxuatban,ngaynhapsach,vitri) VALUES(?,?,?,?,?,?,?)";
        if (addBookk(book, sql)) {
            return true;
        }

        return false;
    }

    public static boolean delBook(int bookId) throws SQLException {
        Connection conn = JDBCconn.getConnection();
        String sql = "DELETE FROM book WHERE id=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, bookId);

        int kq = stm.executeUpdate();

        return kq > 0;
    }

    public static boolean update(Book book, String sql) throws SQLException {
        if (!book.getMa().equals("") && !book.getTenSach().equals("") && !book.getTacGia().equals("") && !book.getMoTa().equals("")
                && !book.getNamXuatBan().equals("") && !book.getNgayNhap().equals("") && !book.getViTri().equals("")) {
            Connection conn = JDBCconn.getConnection();

            conn.setAutoCommit(false);

            //add Question
            PreparedStatement stm = conn.prepareStatement(sql);
//            stm.setInt(1, book.getId());
            stm.setString(1, book.getMa());
            stm.setString(2, book.getTenSach());
            stm.setString(3, book.getTacGia());
            stm.setString(4, book.getMoTa());
            stm.setString(5, book.getNamXuatBan());
            stm.setString(6, book.getNgayNhap());
            stm.setString(7, book.getViTri());
            stm.setInt(8, book.getId());

            stm.executeUpdate();

            conn.commit();
            return true;
        }
        return false;
    }

    public static boolean updateBook(Book book) throws SQLException {

        String sql = "UPDATE book Set masach=?,tensach=?,tacgia=?,motasach=?,"
                + "namxuatban=?,ngaynhapsach=?,vitri=? WHERE id=?";
        if (update(book, sql)) {
            return true;
        }
        return false;
    }

    public static void addB(Book book, String sql) throws SQLException {
        Connection conn = JDBCconn.getConnection();
        conn.setAutoCommit(false);
        //add Question
        PreparedStatement stm = conn.prepareStatement(sql);

        stm.setString(1, book.getMa());
        stm.setString(2, book.getTenSach());
        stm.setString(3, book.getTacGia());
        stm.setString(4, book.getMoTa());
        stm.setString(5, book.getNamXuatBan());
        stm.setString(6, book.getNgayNhap());
        stm.setString(7, book.getViTri());

        stm.executeUpdate();

    }

    public static List<Book> getBooks(String keyword) throws SQLException {
        String sql = "SELECT * FROM book ";
        Connection conn = JDBCconn.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);

        ResultSet rs = stm.executeQuery();

        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            Book q = new Book(rs.getInt("id"), rs.getString("masach"),
                    rs.getString("tensach"), rs.getString("tacgia"),
                    rs.getString("motasach"), rs.getString("namxuatban"),
                    rs.getString("ngaynhapsach"), rs.getString("vitri"));
            books.add(q);
        }

        return books;

    }

    public static List<Book> Search(String keyword) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book ";
        if (keyword != null && !keyword.isEmpty()) {
            sql += "WHERE masach like?" + "or tensach like?" + "or tacgia like?" + "or namxuatban like?" + "or vitri like?";
        }
        Connection conn = JDBCconn.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        if (keyword != null && !keyword.trim().isEmpty())
            
           try {
            stm.setString(1, String.format("%%%s%%", keyword.trim()));
            stm.setString(2, String.format("%%%s%%", keyword.trim()));
            stm.setString(3, String.format("%%%s%%", keyword.trim()));
            stm.setString(4, String.format("%%%s%%", keyword.trim()));
            stm.setString(5, String.format("%%%s%%", keyword.trim()));
        } catch (SQLException sQLException) {
        }

        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Book q = new Book(rs.getInt("id"), rs.getString("masach"),
                    rs.getString("tensach"), rs.getString("tacgia"),
                    rs.getString("motasach"), rs.getString("namxuatban"),
                    rs.getString("ngaynhapsach"), rs.getString("vitri"));
            books.add(q);
        }
        return books;
    }

    public static Alert getAlertInfo(String content, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setContentText(content);

        return a;
    }
}
