/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.sun.javafx.binding.StringFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import pojo.Borrow;

/**
 *
 * @author USER
 */
public class BorrowServices {

    public static boolean addBorrow(Borrow b) throws SQLException, ParseException {
        String sql = "INSERT INTO bookdocgia(id,idbook,iddocgia,ngaymuon) VALUES (?,?,?,?)";
        Connection conn = JDBCconn.getConnection();
        
        String c= getDateNow();

        conn.setAutoCommit(false);
        //add Question
        PreparedStatement stm = conn.prepareStatement(sql);

        stm.setInt(1, b.getId());
        stm.setInt(2, b.getIdbook());
        stm.setInt(3, b.getIddocgia());
        stm.setString(4, c);
      

        stm.executeUpdate();
        
        conn.commit();
        return true;
    }
    
    public static boolean returnB(Borrow b, String tra, String muon) throws ParseException, SQLException{
        String sql= "UPDATE bookdocgia SET ngaytra=?, tienphat=? WHERE id=?";
        Connection conn = JDBCconn.getConnection();
        
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//         dateFormat.format(tra);
//         dateFormat.format(muon);

        // Perform addition/subtraction
        Date date1 = dateFormat.parse(muon);
        Date date2 = dateFormat.parse(tra);
        long diff = date2.getTime() - date1.getTime();
        long diff1=diff/(24 * 60 * 60 * 1000);
        
        if (diff1 > 30) {
            int c = (int) (5000 * (diff1-30));
            String tien = Integer.toString(c);
            
            conn.setAutoCommit(false);
            //add Question
            PreparedStatement stm = conn.prepareStatement(sql);

            stm.setInt(3, b.getId());
            stm.setString(2, tien + "VND");
            stm.setString(1, tra);

            stm.executeUpdate();

            conn.commit();
        } else {
            conn.setAutoCommit(false);
            //add Question
            PreparedStatement stm = conn.prepareStatement(sql);

            stm.setInt(3, b.getId());
            stm.setString(2, "0");
            stm.setString(1, tra);

            stm.executeUpdate();

            conn.commit();
        }
        return true;
        
    }
    
     public static List<Borrow> getBorrow(String keyword) throws SQLException{
         String sql = "SELECT * FROM bookdocgia ";
        Connection conn = JDBCconn.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);

        ResultSet rs = stm.executeQuery();

        List<Borrow> books = new ArrayList<>();
        while (rs.next()) {
            Borrow q = new Borrow(rs.getString("ngaymuon"), rs.getString("ngaytra"),
                    rs.getInt("idbook"), rs.getInt("id"),
                    rs.getInt("iddocgia"),rs.getString("tienphat"));
            books.add(q);
        }
        return books;

     }
    public static String getDateNow(){
        Date date= new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        return format.format(date);
        
    }
    
}
