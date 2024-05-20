package assignments.assignment4.components.printer;

import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BillPrinterController {
  private Stage stage;
  private MainApp mainApp;
  private User user;
  private Order order;
  @FXML private Label orderId;
  @FXML private Label orderDate;
  @FXML private Label restaurantName;
  @FXML private Label deliveryLocation;
  @FXML private Label deliveryStatus;
  @FXML private Label deliveryCost;
  @FXML private Label totalCost;
  @FXML private VBox listOfOrder;

  public BillPrinterController(Stage stage, MainApp mainApp, User user, Order order) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.user = user;
    this.order = order;
  }

  @FXML
  private void redirectToPrintBill() {
    Scene customerPrintBillScene = this.mainApp.getScene("CustomerPrintBill");
    this.mainApp.setScene(customerPrintBillScene);
  }

  @FXML
  public void initialize() {
    this.orderId.setText("Order ID: " + this.order.getOrderId());
    this.orderDate.setText("Tanggal Pemesanan: " + this.order.getTanggal());
    this.restaurantName.setText("Restaurant: " + this.order.getRestaurant().getNama());
    this.deliveryLocation.setText("Lokasi Pengiriman: " + this.user.getLokasi());
    this.deliveryStatus.setText(
        "Status Pengiriman: " + (this.order.getOrderFinished() ? "Finished" : "Not Finished"));
    for (Menu item : this.order.getItems()) {
      Label itemName =
          new Label(String.format("- %s Rp%.0f", item.getNamaMakanan(), item.getHarga()));
      this.listOfOrder.getChildren().add(itemName);
    }
    this.deliveryCost.setText("Biaya Ongkos Kirim: " + this.order.getOngkir());
    this.totalCost.setText("Total Biaya: " + this.order.getTotalHarga());
  }
}
