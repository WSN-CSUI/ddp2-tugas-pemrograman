package assignments.assignment4.page.admin;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Restaurant;
import assignments.assignment4.MainApp;
import assignments.assignment4.helpers.TriConsumer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMenuController {
  private Stage stage;
  private MainApp mainApp;
  private TriConsumer<Restaurant, String, Double> onAddMenuHandler;
  @FXML private TextField restaurantName;
  @FXML private TextField menuName;
  @FXML private TextField menuPrice;

  public AddMenuController(
      Stage stage, MainApp mainApp, TriConsumer<Restaurant, String, Double> onAddMenuHandler) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.onAddMenuHandler = onAddMenuHandler;
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

  // Event handler untuk menambahkan menu
  @FXML
  private void handleAddMenu() {
    String restaurantName = this.restaurantName.getText();
    String menuName = this.menuName.getText();
    String menuPrice = this.menuPrice.getText();

    if (restaurantName.isEmpty()) {
      this.showAlert(
          "Error", "Resto Name is Empty", "Please fill the restaurant name", Alert.AlertType.ERROR);
    }

    if (menuName.isEmpty()) {
      this.showAlert(
          "Error", "Menu Name is Empty", "Please fill the menu name", Alert.AlertType.ERROR);
    }

    if (menuPrice.isEmpty()) {
      this.showAlert(
          "Error", "Menu Price is Empty", "Please fill the menu price", Alert.AlertType.ERROR);
    }

    if (!restaurantName.isEmpty() && !menuName.isEmpty() && !menuPrice.isEmpty()) {
      try {
        Restaurant restaurant = DepeFood.findRestaurant(restaurantName);
        this.onAddMenuHandler.accept(restaurant, menuName, Double.parseDouble(menuPrice));
      } catch (NumberFormatException e) {
        this.showAlert(
            "Error",
            "Menu Price is Invalid",
            "Please fill the menu price with a number",
            Alert.AlertType.ERROR);
      }
    }
  }
}
