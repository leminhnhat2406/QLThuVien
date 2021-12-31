/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeThong;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pojo.Member;

/**
 * FXML Controller class
 *
 * @author LocNe
 */
public class TheDocGiaController implements Initializable {

    @FXML
    GridPane grPand;
    @FXML
    TextField txtMa;
    @FXML
    TextField txtHoten;
//    @FXML TextField txtGioiTinh;
    @FXML
    DatePicker txtNgaySinh;
    @FXML
    TextField txtDoiTuong;
    @FXML
    TextField txtBoPhan;
//    @FXML
//    TextField txtHanThe;
    @FXML
    TextField txtEmail;
    @FXML
    TextField txtDiaChi;
    @FXML
    TextField txtSdt;
    @FXML
    Button btnSave;
    @FXML
    ComboBox<String> cbGioiTinh;

    ObservableList<String> list = FXCollections.observableArrayList("Man", "Woman", "Other");
    @FXML
    private Button btnThoat2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbGioiTinh.setItems(list);
        txtSdt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtSdt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
//        txtNgaySinh.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*")) {
//                txtSdt.setText(newValue.replaceAll("[^\\d]", ""));
//            }
//        });
    }

    @FXML
    public void comboBoxChanged(ActionEvent event) {
        cbGioiTinh.setPromptText(cbGioiTinh.getValue());
    }

    @FXML
    public void addMember(ActionEvent event) {
        if (!this.txtMa.getText().equals("") && !this.txtHoten.getText().equals("")
                && !this.txtNgaySinh.toString().equals("") && !this.txtDoiTuong.getText().equals("")
                && !this.txtBoPhan.getText().equals("")
                && !this.txtEmail.getText().equals("") && !this.txtDiaChi.getText().equals("")
                && !this.txtSdt.getText().isEmpty()) {

            String date = txtNgaySinh.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
           
            
                Member b = new Member(this.txtMa.getText(), this.txtHoten.getText(),
                        cbGioiTinh.getValue(),
                        date, txtDoiTuong.getText(), txtBoPhan.getText(),txtEmail.getText(), txtDiaChi.getText(),
                        txtSdt.getText());

                try {
                    Utils.MemberServices.addMember(b);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Add member succsessful!!!!");
                    alert.show();
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Add member failed!!!!" + ex.getMessage());
                    alert.show();
                }

            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vui lòng nhập đủ các trường!!!");
            alert.show();
        }

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(false);
        stage.close();
        Scene scence = new Scene(FXMLLoader.load(getClass().getResource(
                "User.fxml")));
        stage.setScene(scence);
        stage.show();
    }

}
