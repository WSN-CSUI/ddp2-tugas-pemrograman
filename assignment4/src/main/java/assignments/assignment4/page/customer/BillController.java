package assignments.assignment4.page.customer;

import assignments.assignment4.MainApp;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BillController {
  private Stage stage;
  private MainApp mainApp;
  private Consumer<String> onPrintBillhandler;
  @FXML private TextField orderId;

  public BillController(Stage stage, MainApp mainApp, Consumer<String> onPrintBillhandler) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.onPrintBillhandler = onPrintBillhandler;
  }

  private void showAlert(String title, String header, String content, Alert.AlertType c) {
    Alert alert = new Alert(c);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  @FXML
  private void redirectToBaseMenu() {
    Scene baseMenu = this.mainApp.getScene("Customer");
    this.mainApp.setScene(baseMenu);
  }

  @FXML
  private void handlePrintBill() {
    String orderId = this.orderId.getText();
    if (orderId.isEmpty()) {
      this.showAlert(
          "Error", "Order ID is empty", "Please fill the order ID", Alert.AlertType.ERROR);
      return;
    }
    this.onPrintBillhandler.accept(orderId);
  }
}
