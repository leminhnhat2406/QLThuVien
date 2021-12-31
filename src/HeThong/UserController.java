/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeThong;

import Utils.JDBCconn;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LocNe
 */
public class UserController implements Initializable {

    @FXML
    Label lb_close;
    @FXML
    TextField txtUsername;
    @FXML
    TextField txtPassword;
    @FXML
    Button btnSignIn;
    @FXML
    Label lblError;

    //-- 
    Connection conn = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (event.getSource() == lb_close) {
            System.exit(0);
        }
        if (event.getSource() == btnSignIn) {
            //login
            if (logIn().equals("Success")) {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.setMaximized(false);
                    stage.close();
                    Scene scence = new Scene(FXMLLoader.load(getClass().getResource(
                            "Book.fxml")));
                    stage.setScene(scence);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (conn == null) {
            lblError.setTextFill(Color.TOMATO);
            lblError.setText("Server Error : Check");
        } else {
            lblError.setTextFill(Color.GREEN);
            lblError.setText("Server is up : Good to go");
        }
        btnSignIn.setOnKeyReleased(e->{
            if(e.getCode()==KeyCode.ENTER){
               
            }
        });
        
        
    }

    private String logIn() {

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        String sql = "SELECT * FROM users WHERE username  = ? and password = ?";

        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                lblError.setTextFill(Color.GREEN);
                lblError.setText("Login successful..");
                System.err.println("Successfull..");
                return "Success";
            } else {
                lblError.setTextFill(Color.TOMATO);
                lblError.setText("Enter correct username/pass..");
                System.err.println("Woring Logins..");
                return "Error";
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "Exception";
        }
    }

    public UserController() {
        conn = JDBCconn.getConnection();
    }

    @FXML
    private void clickM(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(false);
        stage.close();
        Scene scence = new Scene(FXMLLoader.load(getClass().getResource(
                "TheDocGia.fxml")));
        stage.setScene(scence);
        stage.show();
    }
}
