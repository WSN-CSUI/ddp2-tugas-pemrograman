package assignments.assignment4.page.customer;

import assignments.assignment4.MainApp;
import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PaybillController {
  private Stage stage;
  private MainApp mainApp;
  private BiConsumer<String, String> onPaybillHandler;
  @FXML private TextField orderId;
  @FXML private ComboBox<String> paymentMethod;

  public PaybillController(
      Stage stage, MainApp mainApp, BiConsumer<String, String> onPaybillHandler) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.onPaybillHandler = onPaybillHandler;
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
  private void handlePaybill() {
    String orderId = this.orderId.getText();
    String paymentMethod = this.paymentMethod.getValue();
    if (orderId.isEmpty()) {
      this.showAlert(
          "Error", "Order ID is empty", "Please fill the order ID", Alert.AlertType.ERROR);
    }
    if (paymentMethod == null) {
      this.showAlert(
          "Error",
          "Payment Method is empty",
          "Please choose the payment method",
          Alert.AlertType.ERROR);
    }
    if (!orderId.isEmpty() && paymentMethod != null) {
      this.onPaybillHandler.accept(orderId, paymentMethod);
    }
  }
}
