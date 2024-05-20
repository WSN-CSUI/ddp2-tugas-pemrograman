package assignments.assignment4.page;

import assignments.assignment1.OrderGenerator;
import assignments.assignment3.DepeFood;
import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.printer.BillPrinter;
import assignments.assignment4.page.customer.*;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CustomerMenu extends MemberMenu {
  private final User user;
  //  private final Stage stage;
  //  private final MainApp mainApp;
  //  private final Scene scene;
  private final Scene addOrderScene;
  private final Scene printBillScene;
  private final Scene payBillScene;
  private final Scene cekSaldoScene;
  private final List<Restaurant> restoList;

  public CustomerMenu(Stage stage, MainApp mainApp, User user) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.user = user;
    this.restoList = DepeFood.getRestoList();
    this.addOrderScene = createTambahPesananForm();
    this.printBillScene = createBillPrinter();
    this.payBillScene = createBayarBillForm();
    this.cekSaldoScene = createCekSaldoScene();
    this.scene = createBaseMenu();
    this.mainApp.addScene("CustomerAddOrder", this.addOrderScene);
    this.mainApp.addScene("CustomerPrintBill", this.printBillScene);
    this.mainApp.addScene("CustomerPayBill", this.payBillScene);
    this.mainApp.addScene("CustomerCekSaldo", this.cekSaldoScene);
  }

  @Override
  public Scene createBaseMenu() {
    try {
      FXMLLoader baseMenuFXML = new FXMLLoader(getClass().getResource("./customer/base.fxml"));
      baseMenuFXML.setControllerFactory(
          controller ->
              new BaseController(
                  this.stage,
                  this.mainApp,
                  this::refresh,
                  this::createTambahPesananForm,
                  this::createBillPrinter,
                  this::createBayarBillForm,
                  this::createCekSaldoScene));
      return new Scene(baseMenuFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private Scene createTambahPesananForm() {
    try {
      FXMLLoader addOrderFXML = new FXMLLoader(getClass().getResource("./customer/add-order.fxml"));
      addOrderFXML.setControllerFactory(
          controller ->
              new AddOrderController(
                  this.stage, this.mainApp, this.restoList, this::handleBuatPesanan));
      return new Scene(addOrderFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private Scene createBillPrinter() {
    try {
      FXMLLoader createBillFXML = new FXMLLoader(getClass().getResource("./customer/bill.fxml"));
      createBillFXML.setControllerFactory(
          controller -> new BillController(this.stage, this.mainApp, this::handleLihatBill));
      return new Scene(createBillFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private Scene createBayarBillForm() {
    try {
      FXMLLoader paybillFXML = new FXMLLoader(getClass().getResource("./customer/paybill.fxml"));
      paybillFXML.setControllerFactory(
          controller -> new PaybillController(this.stage, this.mainApp, this::handleBayarBill));
      return new Scene(paybillFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private Scene createCekSaldoScene() {
    try {
      FXMLLoader saldoFXML = new FXMLLoader(getClass().getResource("./customer/saldo.fxml"));
      saldoFXML.setControllerFactory(
          controller -> new SaldoController(this.stage, this.mainApp, this.user));
      return new Scene(saldoFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private void handleBuatPesanan(
      String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
    Restaurant restaurant = DepeFood.findRestaurant(namaRestoran);
    if (restaurant == null) {
      this.showAlert("Error", null, "Restoran tidak terdaftar pada sistem.", Alert.AlertType.ERROR);
      return;
    }
    if (!OrderGenerator.validateDate(tanggalPemesanan)) {
      this.showAlert(
          "Error", null, "Masukkan tanggal sesuai format (DD/MM/YYYY)", Alert.AlertType.ERROR);
      return;
    }
    if (!DepeFood.validateRequestPesanan(restaurant, menuItems)) {
      this.showAlert(
          "Error", null, "Mohon memesan menu yang tersedia di Restoran!", Alert.AlertType.ERROR);
      return;
    }

    String orderId =
        DepeFood.handleBuatPesanan(namaRestoran, tanggalPemesanan, menuItems.size(), menuItems);
    this.showAlert(
        "Success",
        null,
        String.format("Order dengan ID %s berhasil ditambahkan!", orderId),
        Alert.AlertType.INFORMATION);
  }

  private void handleBayarBill(String orderID, String pilihanPembayaran) {
    Order order = DepeFood.findUserOrderById(orderID);

    if (order == null) {
      this.showAlert("Error", null, "Order does not exist", Alert.AlertType.ERROR);
      return;
    }
    if (order.getOrderFinished()) {
      this.showAlert("Error", null, "Order has been paid", Alert.AlertType.ERROR);
      return;
    }
    if (!pilihanPembayaran.equals("Debit") && !pilihanPembayaran.equals("Credit Card")) {
      this.showAlert("Error", null, "Payment method not available", Alert.AlertType.ERROR);
      return;
    }

    boolean isCreditCard = this.user.getPaymentSystem() instanceof CreditCardPayment;
    if ((isCreditCard && pilihanPembayaran.equals("Debit"))
        || (!isCreditCard && pilihanPembayaran.equals("Credit Card"))) {
      this.showAlert(
          "Error", null, "Payment method not available for this user", Alert.AlertType.ERROR);
      return;
    }

    try {
      this.user
          .getPaymentSystem()
          .processPayment(this.user.getSaldo(), (long) order.getTotalHarga());
    } catch (Exception e) {
      this.showAlert("Error", null, e.getMessage(), Alert.AlertType.ERROR);
      return;
    }

    DepeFood.handleBayarBill(orderID, pilihanPembayaran);
    this.showAlert("Success", null, "Bill successfully paid", Alert.AlertType.INFORMATION);
  }

  private void handleLihatBill(String orderID) {
    Order order = DepeFood.findUserOrderById(orderID);
    if (order == null) {
      this.showAlert("Error", null, "Order tidak dapat ditemukan", Alert.AlertType.ERROR);
      return;
    }
    Scene billPrinterScene = new BillPrinter(this.stage, this.mainApp, this.user, order).getScene();
    this.mainApp.addScene("CustomerBillPrinter", billPrinterScene);
    this.mainApp.setScene(this.mainApp.getScene("CustomerBillPrinter"));
  }
}
