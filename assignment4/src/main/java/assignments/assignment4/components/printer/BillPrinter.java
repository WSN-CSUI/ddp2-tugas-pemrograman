package assignments.assignment4.components.printer;

import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BillPrinter {
  private Stage stage;
  private MainApp mainApp;
  private User user;
  private Order order;

  public BillPrinter(Stage stage, MainApp mainApp, User user, Order order) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.user = user;
    this.order = order;
  }

  public Scene getScene() {
    return this.createBillPrinterForm();
  }

  private Scene createBillPrinterForm() {
    try {
      FXMLLoader billPrinterFormFXML =
          new FXMLLoader(getClass().getResource("./bill-printer.fxml"));
      billPrinterFormFXML.setControllerFactory(
          controller -> new BillPrinterController(this.stage, this.mainApp, this.user, this.order));
      return new Scene(billPrinterFormFXML.load(), 400, 600);
    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
      return null;
    }
  }
}
