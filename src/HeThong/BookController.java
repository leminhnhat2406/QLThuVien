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
        //Search bôok

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
        //Lấy thông tin
        tbmuon.setRowFactory(evt -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(et -> {
                Book b = tbmuon.getSelectionModel().getSelectedItem();
                txtma1.setText((String.valueOf(b.getId())));
                txtten1.setText(b.getTenSach());
            });

            return row;
        });
        //lấy thông tin sách
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
                alert.setContentText("Thêm sách thành công !!!!");
                loadBook();
                alert.show();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Thêm sách thất bại !!!!" + ex.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vui lòng nhập đủ thông tin !!!");
            alert.show();
        }

    }

    @FXML
    public void loadFull(ActionEvent ev) throws SQLException {
        this.tbmuon.getColumns().clear();
        TableColumn clid = new TableColumn("ID Sách");
        TableColumn clma = new TableColumn("Mã sách ");
        TableColumn clten = new TableColumn("Tên sách ");
        TableColumn cltg = new TableColumn("Tác giả ");
        TableColumn clmota = new TableColumn("Mô tả ");
        TableColumn clnam = new TableColumn("Năm xuất bản ");
        TableColumn clnhap = new TableColumn("Ngày nhập ");
        TableColumn clvitri = new TableColumn("Vị trí sách ");

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
        TableColumn clmota = new TableColumn("Ngày mượn");
        TableColumn clnam = new TableColumn("Ngày trả");
        TableColumn cltien = new TableColumn("Tiền phạt");

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

        TableColumn clid = new TableColumn("ID sách ");
        TableColumn clma = new TableColumn("Mã sách ");
        TableColumn clten = new TableColumn("Tên sách ");
        TableColumn cltg = new TableColumn("Tác giả ");
        TableColumn clmota = new TableColumn("Mô tả ");
        TableColumn clnam = new TableColumn("Năm xuất bản ");
        TableColumn clnhap = new TableColumn("Ngày nhập ");
        TableColumn clvitri = new TableColumn("Vị trí sách ");

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
            Button btn = new Button("Xóa");
            btn.setOnAction(evt -> {
                // thực hiện sự kiện xóa câu hỏi
                Button b = (Button) evt.getSource();
                TableCell c = (TableCell) b.getParent();
                Book q = (Book) c.getTableRow().getItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Bạn chắc chắn xóa? ");
                alert.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        try {
                            BookServices.delBook(q.getId());

                            MemberServices.getAlertInfo("Xóa thành công", Alert.AlertType.INFORMATION).show();

                            this.loadData("");
                            loadBook();
                        } catch (SQLException ex) {
                            MemberServices.getAlertInfo("Xóa thất bại: " + ex.getMessage(), Alert.AlertType.INFORMATION).show();
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
        if (MemberServices.getDue(Integer.parseInt(txtIdUser.getText())).equals("còn hạn")) {
            try {
                BorrowServices.addBorrow(b);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Mượn Thành Công!!");
                this.loadBook1();
                txtIdUser.setText("");
                txtma1.setText("");
                txtten1.setText("");
                alert.showAndWait();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Mượn không thành công" + ex.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Hết hạn thẻ");
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
                alert.setContentText("Trả sách thành công !!!");
                this.loadBook1();
                alert.showAndWait();

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Trả sách thất bại !!!" + ex.getMessage());
                alert.showAndWait();

            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Trả sách thất bại !!!");
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

                issueData.add("ID là : " + a);
                issueData.add("Mã Độc giả: " + a1);
                issueData.add("Họ Tên : " + a2);
                issueData.add("Giới Tính : " + a3);
                issueData.add("Ngày Sinh: " + a4);
                issueData.add("Đối tượng: " + a5);
                issueData.add("Bộ Phận " + a6);
                issueData.add("Email : " + a7);
                issueData.add("Giới Tính :" + a8);
                issueData.add("Địa chỉ: " + a9);
                issueData.add("Hạn Thẻ: " + a10);

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
        TableColumn clten = new TableColumn("Mã độc giả");
        TableColumn cltg = new TableColumn("Họ tên");
        TableColumn clmota = new TableColumn("Giới tính");
        TableColumn clnam = new TableColumn("Ngày sinh");
        TableColumn cltien = new TableColumn("Đối tượng");
        TableColumn cltien1 = new TableColumn("Bộ phận");
        TableColumn cltien2 = new TableColumn("Email");
        TableColumn cltien3 = new TableColumn("Địa chỉ");
        TableColumn cltien4 = new TableColumn("Sđt");
        TableColumn cltien5 = new TableColumn("Hạn thẻ");

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
        TableColumn clten = new TableColumn("Mã độc giả");
        TableColumn cltg = new TableColumn("Họ tên");
        TableColumn clmota = new TableColumn("Giới tính");
        TableColumn clnam = new TableColumn("Ngày sinh");
        TableColumn cltien = new TableColumn("Đối tượng");
        TableColumn cltien1 = new TableColumn("Bộ phận");
        TableColumn cltien2 = new TableColumn("Email");
        TableColumn cltien3 = new TableColumn("Địa chỉ");
        TableColumn cltien4 = new TableColumn("Sđt");
        TableColumn cltien5 = new TableColumn("Hạn thẻ");

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

                    BookServices.getAlertInfo("Cập nhật thông tin sách thành công !!!",
                            Alert.AlertType.INFORMATION).show();
                    this.txtma.setText("");
                    this.txtNXB.setText("");
                    this.txtten.setText("");
                    this.txttacGia.setText("");
                    this.txtmota.setText("");
                    this.txtNgayNhapSach.setText("");
                    this.txtViTri.setText("");
                } catch (SQLException ex) {
                    BookServices.getAlertInfo("Cập nhật thông tin sách thất bại !!!" + ex.getMessage(),
                            Alert.AlertType.ERROR).show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vui lòng nhập đủ thông tin !!!");
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
            issueData.add("Tổng tiền phạt đã nhận là : " + a + " VND");
        }
        String sql1 = "Select count(id) from bookdocgia";
        PreparedStatement stm1 = conn.prepareStatement(sql1);
        ResultSet rs1 = stm1.executeQuery();
        if (rs1.next()) {
            String a = rs1.getString("count(id)");
            issueData.add("Số quyển sách đã mượn là :" + a);
        }

        String sql2 = "Select count(ngaytra) from bookdocgia";
        PreparedStatement stm2 = conn.prepareStatement(sql2);
        ResultSet rs2 = stm2.executeQuery();
        if (rs2.next()) {
            String a = rs2.getString("count(ngaytra)");
            issueData.add("Số quyển sách đã trả là :" + a);
        }
//        String sql3 = "SELECT month(ngaytra),count(ngaytra) "
//                + "FROM bookdocgia  "
//                + "where  ngaytra >= '01/12/2020' AND ngaytra <  '01/01/2021' ";
//        PreparedStatement stm3 = conn.prepareStatement(sql3);
//        ResultSet rs3 = stm3.executeQuery();
//        if (rs3.next()) {
//            String a = rs3.getString("count(ngaytra)");
//            issueData.add("Số quyển sách đã mượn trong năm 2020:" + a);
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
