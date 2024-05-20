package assignments.assignment4.page.admin;

import assignments.assignment4.MainApp;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddRestaurantController {
  private Stage stage;
  private MainApp mainApp;
  private Consumer<String> onAddRestaurantHandler;
  @FXML private TextField restaurantName;

  public AddRestaurantController(
      Stage stage, MainApp mainApp, Consumer<String> onAddRestaurantHandler) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.onAddRestaurantHandler = onAddRestaurantHandler;
  }

  private void showAlert(String title, String header, String content, Alert.AlertType c) {
    Alert alert = new Alert(c);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  // Event handler untuk redirect ke halaman utama admin
  @FXML
  private void redirectToBaseMenu() {
    Scene baseMenu = this.mainApp.getScene("Admin");
    this.mainApp.setScene(baseMenu);
  }

  // Event handler untuk menambahkan restoran
  @FXML
  private void handleAddRestaurant() {
    String restaurantName = this.restaurantName.getText();
    if (restaurantName.isEmpty()) {
      this.showAlert(
          "Error",
          "Restaurant Name is Empty",
          "Please fill the restaurant name",
          Alert.AlertType.ERROR);
      return;
    }
    this.onAddRestaurantHandler.accept(restaurantName);
  }
}
