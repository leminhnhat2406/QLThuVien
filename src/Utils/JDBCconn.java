/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LocNe
 */
public class JDBCconn {

    private static Connection conn;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/qlthu","root", "anhlocproo909");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCconn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCconn.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Connection getConnection() {
        return conn;
    }
}
