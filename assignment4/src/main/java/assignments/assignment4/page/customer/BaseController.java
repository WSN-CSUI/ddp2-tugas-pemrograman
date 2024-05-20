package assignments.assignment4.page.customer;

import assignments.assignment4.MainApp;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseController {
  private final Stage stage;
  private final MainApp mainApp;
  private final BiConsumer<String, Supplier<Scene>> refresh;
  private final Supplier<Scene> addOrderScene;
  private final Supplier<Scene> printBillScene;
  private final Supplier<Scene> payBillScene;
  private final Supplier<Scene> saldoScene;

  public BaseController(
      Stage stage,
      MainApp mainApp,
      BiConsumer<String, Supplier<Scene>> refresh,
      Supplier<Scene> addOrderScene,
      Supplier<Scene> printBillScene,
      Supplier<Scene> payBillScene,
      Supplier<Scene> checkSaldoScene) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.refresh = refresh;
    this.addOrderScene = addOrderScene;
    this.printBillScene = printBillScene;
    this.payBillScene = payBillScene;
    this.saldoScene = checkSaldoScene;
  }

  @FXML
  private void logout() {
    this.mainApp.logout();
  }

  @FXML
  private void redirectToAddOrder() {
    this.refresh.accept("CustomerAddOrder", this.addOrderScene);
    this.mainApp.setScene(this.mainApp.getScene("CustomerAddOrder"));
  }

  @FXML
  private void redirectToBill() {
    this.refresh.accept("CustomerPrintBill", this.printBillScene);
    this.mainApp.setScene(this.mainApp.getScene("CustomerPrintBill"));
  }

  @FXML
  private void redirectToPayBill() {
    this.refresh.accept("CustomerPayBill", this.payBillScene);
    this.mainApp.setScene(this.mainApp.getScene("CustomerPayBill"));
  }

  @FXML
  private void redirectToSaldo() {
    this.refresh.accept("CustomerCekSaldo", this.saldoScene);
    this.mainApp.setScene(this.mainApp.getScene("CustomerCekSaldo"));
  }
}
