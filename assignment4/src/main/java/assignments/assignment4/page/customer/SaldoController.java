package assignments.assignment4.page.customer;

import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SaldoController {
  private Stage stage;
  private MainApp mainApp;
  private User currentUser;
  @FXML private Label username;
  @FXML private Label saldoAmount;

  public SaldoController(Stage stage, MainApp mainApp, User currentUser) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.currentUser = currentUser;
  }

  @FXML
  private void initialize() {
    this.username.setText(currentUser.getNama());
    this.saldoAmount.setText("Saldo: Rp." + currentUser.getSaldo());
  }

  // Event handler untuk redirect ke halaman utama customer
  @FXML
  private void redirectToBaseMenu() {
    Scene baseMenu = this.mainApp.getScene("Customer");
    this.mainApp.setScene(baseMenu);
  }
}
