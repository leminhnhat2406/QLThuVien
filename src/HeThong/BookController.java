/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeThong;

import Utils.BookServices;
import Utils.BorrowServices;
import Utils.JDBCconn;
import Utils.MemberServices;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pojo.Book;
import pojo.Borrow;
import pojo.Member;

/**
 * FXML Controller class
 *
 * @author LocNe
 */
public class BookController implements Initializable {

    private ObservableList<Book> data;
    private static final int MAX_BOOK_TO_ISSUE = 3;
    @FXML
    TextField txtma;
    @FXML
    TextField txtten;
    @FXML
    TextField txttacGia;
    @FXML
    TextField txtmota;
    @FXML
    TextField txtNXB;
    @FXML
    TextField txtNgayNhapSach;
    @FXML
    TextField txtViTri;
    @FXML
    Button btnLuu;
    @FXML
    Button btnThoat;
    @FXML
    TextField txttimkiem;
    @FXML
    TableView<Book> tbBook;
    Button btnXoa;

    @FXML
    TableView<Book> tbmuon;
    @FXML
    TextField txtma1;
    @FXML
    TextField txtten1;
    @FXML
    TextField txtIdUser;
    @FXML
    private TableView<Borrow> tbmuon1;
    @FXML
    private DatePicker dateReturn;
    @FXML
    private ListView<String> listViewData;
    @FXML
    private ListView<String> listViewData1;
    @FXML
    private TextField txtLoadMember;
    @FXML
    private TableView<Member> tbMem;
    @FXML
    private Button btFullMem;
    @FXML
    private Button btnThoat1;
    @FXML
    private Button btnThoat2;
    @FXML
    private Button btnThoat21;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load books
        try {
            this.loadBook();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            this.loadBook1();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            this.loadMem1();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        //Search b??ok

        this.txttimkiem.textProperty().addListener(et -> {
            this.tbBook.getItems().clear();
            try {
                this.tbBook.setItems(
                        FXCollections.observableArrayList(BookServices.Search(
                                this.txttimkiem.getText())));

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

        });
        // Search Member
        this.txtLoadMember.textProperty().addListener(et -> {

            this.tbMem.getItems().clear();
            try {
                this.tbMem.setItems(
                        FXCollections.observableArrayList(MemberServices.Search(
                                this.txtLoadMember.getText())));

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        });
        //L???y th??ng tin
        tbmuon.setRowFactory(evt -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(et -> {
                Book b = tbmuon.getSelectionModel().getSelectedItem();
                txtma1.setText((String.valueOf(b.getId())));
                txtten1.setText(b.getTenSach());
            });

            return row;
        });
        //l???y th??ng tin s??ch
        tbBook.setRowFactory(evt -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(et -> {
                Book b = tbBook.getSelectionModel().getSelectedItem();
                txtma.setText(b.getMa());
                txtten.setText(b.getTenSach());
                txttacGia.setText(b.getTacGia());
                txtmota.setText(b.getMoTa());
                txtNXB.setText(b.getNamXuatBan());
                txtNgayNhapSach.setText(b.getNgayNhap());
                txtViTri.setText(b.getViTri());

            });

            return row;
        });
//         txttacGia.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("^[a-zA-Z][\\\\w-]+@([\\\\w]+\\\\.[\\\\w]+|[\\\\w]+\\\\.[\\\\w]{2,}\\\\.[\\\\w]{2,})$")) {
//                txttacGia.setText(newValue.replaceAll("^[a-zA-Z][\\\\w-]+@([\\\\w]+\\\\.[\\\\w]+|[\\\\w]+\\\\.[\\\\w]{2,}\\\\.[\\\\w]{2,})$", ""));
//            }
//        });
        txtNgayNhapSach.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtNgayNhapSach.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        txtNXB.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtNXB.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    public void addBook(ActionEvent event) {
        if (!this.txtma.getText().equals("") && !this.txtNXB.toString().equals("") && !this.txtten.getText().equals("") && !this.txttacGia.getText().equals("")
                && !this.txtmota.getText().equals("") && !this.txtNgayNhapSach.toString().equals("") && !this.txtViTri.getText().equals("")) {
            Book b = new Book(this.txtma.getText(), this.txtten.getText(),
                    txttacGia.getText(), txtmota.getText(), this.txtNXB.getText(), this.txtNgayNhapSach.getText(),
                    txtViTri.getText());

            try {
                Utils.BookServices.addBook(b);
                this.tbBook.getColumns().clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Th??m s??ch th??nh c??ng !!!!");
                loadBook();
                alert.show();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Th??m s??ch th???t b???i !!!!" + ex.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vui l??ng nh???p ????? th??ng tin !!!");
            alert.show();
        }

    }

    @FXML
    public void loadFull(ActionEvent ev) throws SQLException {
        this.tbmuon.getColumns().clear();
        TableColumn clid = new TableColumn("ID S??ch");
        TableColumn clma = new TableColumn("M?? s??ch ");
        TableColumn clten = new TableColumn("T??n s??ch ");
        TableColumn cltg = new TableColumn("T??c gi??? ");
        TableColumn clmota = new TableColumn("M?? t??? ");
        TableColumn clnam = new TableColumn("N??m xu???t b???n ");
        TableColumn clnhap = new TableColumn("Ng??y nh???p ");
        TableColumn clvitri = new TableColumn("V??? tr?? s??ch ");

        clid.setCellValueFactory(new PropertyValueFactory("id"));
        clma.setCellValueFactory(new PropertyValueFactory("ma"));
        clten.setCellValueFactory(new PropertyValueFactory("tenSach"));
        cltg.setCellValueFactory(new PropertyValueFactory("tacGia"));
        clmota.setCellValueFactory(new PropertyValueFactory("moTa"));
        clnam.setCellValueFactory(new PropertyValueFactory("namXuatBan"));
        clnhap.setCellValueFactory(new PropertyValueFactory("ngayNhap"));
        clvitri.setCellValueFactory(new PropertyValueFactory("viTri"));
//        clContent.setPrefWidth(200);

        this.tbmuon.getColumns().addAll(clid, clma, clten, cltg, clmota, clnam, clnhap, clvitri);
        this.tbmuon.setItems(FXCollections.observableArrayList(BookServices.getBooks("")));
        this.tbmuon.setVisible(true);
        this.tbmuon1.setVisible(false);

    }

    private void loadBook1() throws SQLException {

        this.tbmuon1.getItems().clear();

        TableColumn clma = new TableColumn("ID");
        TableColumn clten = new TableColumn("IDbook");
        TableColumn cltg = new TableColumn("IDMember");
        TableColumn clmota = new TableColumn("Ng??y m?????n");
        TableColumn clnam = new TableColumn("Ng??y tr???");
        TableColumn cltien = new TableColumn("Ti???n ph???t");

        clma.setCellValueFactory(new PropertyValueFactory("id"));
        clten.setCellValueFactory(new PropertyValueFactory("idbook"));
        cltg.setCellValueFactory(new PropertyValueFactory("iddocgia"));
        clmota.setCellValueFactory(new PropertyValueFactory("ngaymuon"));
        clnam.setCellValueFactory(new PropertyValueFactory("ngaytra"));
        cltien.setCellValueFactory(new PropertyValueFactory("tienphat"));
        this.tbmuon1.getColumns().clear();
        this.tbmuon1.getColumns().addAll(clma, clten, cltg, clmota, clnam, cltien);
        this.tbmuon1.setItems(FXCollections.observableArrayList(BorrowServices.getBorrow("")));
        this.tbmuon.setVisible(false);

    }

    private void loadBook() throws SQLException {

        TableColumn clid = new TableColumn("ID s??ch ");
        TableColumn clma = new TableColumn("M?? s??ch ");
        TableColumn clten = new TableColumn("T??n s??ch ");
        TableColumn cltg = new TableColumn("T??c gi??? ");
        TableColumn clmota = new TableColumn("M?? t??? ");
        TableColumn clnam = new TableColumn("N??m xu???t b???n ");
        TableColumn clnhap = new TableColumn("Ng??y nh???p ");
        TableColumn clvitri = new TableColumn("V??? tr?? s??ch ");

        clid.setCellValueFactory(new PropertyValueFactory("id"));
        clma.setCellValueFactory(new PropertyValueFactory("ma"));
        clten.setCellValueFactory(new PropertyValueFactory("tenSach"));
        cltg.setCellValueFactory(new PropertyValueFactory("tacGia"));
        clmota.setCellValueFactory(new PropertyValueFactory("moTa"));
        clnam.setCellValueFactory(new PropertyValueFactory("namXuatBan"));
        clnhap.setCellValueFactory(new PropertyValueFactory("ngayNhap"));
        clvitri.setCellValueFactory(new PropertyValueFactory("viTri"));
//        clContent.setPrefWidth(200);
        TableColumn colAction = new TableColumn();
        colAction.setCellFactory(et -> {
            TableCell cell = new TableCell();
            Button btn = new Button("X??a");
            btn.setOnAction(evt -> {
                // th???c hi???n s??? ki???n x??a c??u h???i
                Button b = (Button) evt.getSource();
                TableCell c = (TableCell) b.getParent();
                Book q = (Book) c.getTableRow().getItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("B???n ch???c ch???n x??a? ");
                alert.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        try {
                            BookServices.delBook(q.getId());

                            MemberServices.getAlertInfo("X??a th??nh c??ng", Alert.AlertType.INFORMATION).show();

                            this.loadData("");
                            loadBook();
                        } catch (SQLException ex) {
                            MemberServices.getAlertInfo("X??a th???t b???i: " + ex.getMessage(), Alert.AlertType.INFORMATION).show();
                        }
                    }
                });

            });

            cell.setGraphic(btn);
            return cell;
        });
        this.tbBook.getColumns().addAll(clid, clma, clten, cltg, clmota, clnam, clnhap, clvitri, colAction);
        this.tbBook.setItems(FXCollections.observableArrayList(BookServices.getBooks("")));
    }

    @FXML
    public void muonSach(ActionEvent event) throws ParseException, SQLException {

        Borrow b = new Borrow(Integer.parseInt(txtIdUser.getText()), Integer.parseInt(txtma1.getText()));
        if (MemberServices.getDue(Integer.parseInt(txtIdUser.getText())).equals("c??n h???n")) {
            try {
                BorrowServices.addBorrow(b);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("M?????n Th??nh C??ng!!");
                this.loadBook1();
                txtIdUser.setText("");
                txtma1.setText("");
                txtten1.setText("");
                alert.showAndWait();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("M?????n kh??ng th??nh c??ng" + ex.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("H???t h???n th???");
            alert.showAndWait();

        }
        this.tbmuon1.setVisible(true);
        this.tbmuon.setVisible(false);
    }

    private void loadData(String kw) throws SQLException {
        tbBook.getColumns().clear();
        tbBook.setItems(FXCollections.observableArrayList(BookServices.getBooks(kw)));
    }

    @FXML
    private void traSach(ActionEvent event) throws ParseException {
        Borrow b = tbmuon1.getSelectionModel().getSelectedItem();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//         dateFormat.format(tra);
//         dateFormat.format(muon);

        // Perform addition/subtraction
        String ngaymuon = b.getNgaymuon();

        String ngaytra = dateReturn.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
        Date date1 = dateFormat.parse(ngaymuon);
        Date date2 = dateFormat.parse(ngaytra);
        long diff = date2.getTime() - date1.getTime();
        if (diff >= 0) {
            Borrow bor = new Borrow(ngaymuon, ngaytra, b.getIdbook(), b.getId(), b.getIddocgia(), b.getTienphat());

            try {
                BorrowServices.returnB(b, ngaytra, ngaymuon);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Tr??? s??ch th??nh c??ng !!!");
                this.loadBook1();
                alert.showAndWait();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Tr??? s??ch th???t b???i !!!" + ex.getMessage());
                alert.showAndWait();

            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Tr??? s??ch th???t b???i !!!");
            alert.showAndWait();
        }

    }

    @FXML
    private void loadMemberCard(ActionEvent event) throws SQLException {
        ObservableList<String> issueData = FXCollections.observableArrayList();
        Member b = tbMem.getSelectionModel().getSelectedItem();
        txtLoadMember.setText((String.valueOf(b.getId())));

        String id = txtLoadMember.getText();
        String sql = "Select * from thedocgia where id='" + id + "'";
        Connection conn = JDBCconn.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        stm = conn.prepareStatement(sql);
        rs = stm.executeQuery();

        try {
            while (rs.next()) {
                String a = rs.getString("id");
                String a1 = rs.getString("madocgia");
                String a2 = rs.getString("hoten");
                String a3 = rs.getString("gioitinh");
                String a4 = rs.getString("ngaysinh");
                String a5 = rs.getString("doituong");
                String a6 = rs.getString("bophan");
                String a7 = rs.getString("email");
                String a8 = rs.getString("diachi");
                String a9 = rs.getString("sdt");
                String a10 = rs.getString("hanthe");

                issueData.add("ID l?? : " + a);
                issueData.add("M?? ?????c gi???: " + a1);
                issueData.add("H??? T??n : " + a2);
                issueData.add("Gi???i T??nh : " + a3);
                issueData.add("Ng??y Sinh: " + a4);
                issueData.add("?????i t?????ng: " + a5);
                issueData.add("B??? Ph???n " + a6);
                issueData.add("Email : " + a7);
                issueData.add("Gi???i T??nh :" + a8);
                issueData.add("?????a ch???: " + a9);
                issueData.add("H???n Th???: " + a10);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listViewData1.getItems().setAll(issueData);
        tbMem.setVisible(false);
    }

    private void loadMem1() throws SQLException {
        this.tbMem.getItems().clear();

        TableColumn clma = new TableColumn("ID");
        TableColumn clten = new TableColumn("M?? ?????c gi???");
        TableColumn cltg = new TableColumn("H??? t??n");
        TableColumn clmota = new TableColumn("Gi???i t??nh");
        TableColumn clnam = new TableColumn("Ng??y sinh");
        TableColumn cltien = new TableColumn("?????i t?????ng");
        TableColumn cltien1 = new TableColumn("B??? ph???n");
        TableColumn cltien2 = new TableColumn("Email");
        TableColumn cltien3 = new TableColumn("?????a ch???");
        TableColumn cltien4 = new TableColumn("S??t");
        TableColumn cltien5 = new TableColumn("H???n th???");

        clma.setCellValueFactory(new PropertyValueFactory("id"));
        clten.setCellValueFactory(new PropertyValueFactory("ma"));
        cltg.setCellValueFactory(new PropertyValueFactory("hoten"));
        clmota.setCellValueFactory(new PropertyValueFactory("gioitinh"));
        clnam.setCellValueFactory(new PropertyValueFactory("ngaysinh"));
        cltien.setCellValueFactory(new PropertyValueFactory("doituong"));
        cltien1.setCellValueFactory(new PropertyValueFactory("bophan"));
        cltien2.setCellValueFactory(new PropertyValueFactory("email"));
        cltien3.setCellValueFactory(new PropertyValueFactory("diachi"));
        cltien4.setCellValueFactory(new PropertyValueFactory("sdt"));
        cltien5.setCellValueFactory(new PropertyValueFactory("hanthe"));

        this.tbMem.getColumns().clear();
        this.tbMem.getColumns().addAll(clma, clten, cltg, clmota, clnam, cltien, cltien1, cltien2, cltien3, cltien4, cltien5);
        this.tbMem.setItems(FXCollections.observableArrayList(MemberServices.getMembers("")));
    }

    @FXML
    public void loadMem(ActionEvent e) throws SQLException {
        this.tbMem.getItems().clear();

        TableColumn clma = new TableColumn("ID");
        TableColumn clten = new TableColumn("M?? ?????c gi???");
        TableColumn cltg = new TableColumn("H??? t??n");
        TableColumn clmota = new TableColumn("Gi???i t??nh");
        TableColumn clnam = new TableColumn("Ng??y sinh");
        TableColumn cltien = new TableColumn("?????i t?????ng");
        TableColumn cltien1 = new TableColumn("B??? ph???n");
        TableColumn cltien2 = new TableColumn("Email");
        TableColumn cltien3 = new TableColumn("?????a ch???");
        TableColumn cltien4 = new TableColumn("S??t");
        TableColumn cltien5 = new TableColumn("H???n th???");

        clma.setCellValueFactory(new PropertyValueFactory("id"));
        clten.setCellValueFactory(new PropertyValueFactory("ma"));
        cltg.setCellValueFactory(new PropertyValueFactory("hoten"));
        clmota.setCellValueFactory(new PropertyValueFactory("gioitinh"));
        clnam.setCellValueFactory(new PropertyValueFactory("ngaysinh"));
        cltien.setCellValueFactory(new PropertyValueFactory("doituong"));
        cltien1.setCellValueFactory(new PropertyValueFactory("bophan"));
        cltien2.setCellValueFactory(new PropertyValueFactory("email"));
        cltien3.setCellValueFactory(new PropertyValueFactory("diachi"));
        cltien4.setCellValueFactory(new PropertyValueFactory("sdt"));
        cltien5.setCellValueFactory(new PropertyValueFactory("hanthe"));

        this.tbMem.getColumns().clear();
        this.tbMem.getColumns().addAll(clma, clten, cltg, clmota, clnam, cltien, cltien1, cltien2, cltien3, cltien4, cltien5);
        this.tbMem.setItems(FXCollections.observableArrayList(MemberServices.getMembers("")));
        this.tbMem.setVisible(true);
    }

    @FXML
    private void updateB(ActionEvent event) {
        if (!this.txtma.getText().equals("") && !this.txtten.getText().equals("") && !this.txttacGia.getText().equals("")
                && !this.txtmota.getText().equals("") && !this.txtViTri.getText().equals("")) {
//           
            Book q = this.tbBook.getSelectionModel().getSelectedItem();
            Book b = new Book(q.getId(), this.txtma.getText(), this.txtten.getText(),
                    txttacGia.getText(), txtmota.getText(), this.txtNXB.getText(), this.txtNgayNhapSach.getText(),
                    txtViTri.getText());
            if (b != null) {
                try {
                    BookServices.updateBook(b);

                    this.tbBook.getItems().clear();
                    this.tbBook.setItems(FXCollections.observableArrayList(BookServices.getBooks("")));

                    BookServices.getAlertInfo("C???p nh???t th??ng tin s??ch th??nh c??ng !!!",
                            Alert.AlertType.INFORMATION).show();
                    this.txtma.setText("");
                    this.txtNXB.setText("");
                    this.txtten.setText("");
                    this.txttacGia.setText("");
                    this.txtmota.setText("");
                    this.txtNgayNhapSach.setText("");
                    this.txtViTri.setText("");
                } catch (SQLException ex) {
                    BookServices.getAlertInfo("C???p nh???t th??ng tin s??ch th???t b???i !!!" + ex.getMessage(),
                            Alert.AlertType.ERROR).show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vui l??ng nh???p ????? th??ng tin !!!");
            alert.show();
        }
    }

    @FXML
    private void loadThongKe(Event event) throws SQLException {
        ObservableList<String> issueData = FXCollections.observableArrayList();
        String sql = "Select sum(tienphat) from bookdocgia";
        Connection conn = JDBCconn.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            String a = rs.getString("sum(tienphat)");
            issueData.add("T???ng ti???n ph???t ???? nh???n l?? : " + a + " VND");
        }
        String sql1 = "Select count(id) from bookdocgia";
        PreparedStatement stm1 = conn.prepareStatement(sql1);
        ResultSet rs1 = stm1.executeQuery();
        if (rs1.next()) {
            String a = rs1.getString("count(id)");
            issueData.add("S??? quy???n s??ch ???? m?????n l?? :" + a);
        }

        String sql2 = "Select count(ngaytra) from bookdocgia";
        PreparedStatement stm2 = conn.prepareStatement(sql2);
        ResultSet rs2 = stm2.executeQuery();
        if (rs2.next()) {
            String a = rs2.getString("count(ngaytra)");
            issueData.add("S??? quy???n s??ch ???? tr??? l?? :" + a);
        }
//        String sql3 = "SELECT month(ngaytra),count(ngaytra) "
//                + "FROM bookdocgia  "
//                + "where  ngaytra >= '01/12/2020' AND ngaytra <  '01/01/2021' ";
//        PreparedStatement stm3 = conn.prepareStatement(sql3);
//        ResultSet rs3 = stm3.executeQuery();
//        if (rs3.next()) {
//            String a = rs3.getString("count(ngaytra)");
//            issueData.add("S??? quy???n s??ch ???? m?????n trong n??m 2020:" + a);
//        }
        listViewData.getItems().setAll(issueData);
    }

    @FXML
    private void reset(ActionEvent event) {
        this.txtma.setText("");
        this.txtNXB.setText("");
        this.txtten.setText("");
        this.txttacGia.setText("");
        this.txtmota.setText("");
        this.txtNgayNhapSach.setText("");
        this.txtViTri.setText("");
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

    @FXML
    private void loadbrow(ActionEvent event) throws SQLException {
        loadBook1();
        tbmuon1.setVisible(true);
    }
}
