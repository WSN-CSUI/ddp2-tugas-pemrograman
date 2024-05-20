package assignments.assignment4.page.admin;

import assignments.assignment4.MainApp;
import java.util.List;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RestaurantController {
  private Stage stage;
  private MainApp mainApp;
  private Function<String, List<String>> onViewRestaurantHandler;
  @FXML private TextField restaurantName;
  @FXML private ListView<String> menuItems;

  public RestaurantController(
      Stage stage, MainApp mainApp, Function<String, List<String>> onViewRestaurantHandler) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.onViewRestaurantHandler = onViewRestaurantHandler;
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

  // Event handler untuk melihat menu restoran
  @FXML
  private void handleViewRestaurant() {
    String restaurantName = this.restaurantName.getText();
    if (restaurantName.isEmpty()) {
      this.showAlert(
          "Error",
          "Restaurant Name is Empty",
          "Please fill the restaurant name",
          Alert.AlertType.ERROR);
      return;
    }

    List<String> menuItems = this.onViewRestaurantHandler.apply(restaurantName);
    this.menuItems.setItems(FXCollections.observableArrayList(menuItems));
  }
}
