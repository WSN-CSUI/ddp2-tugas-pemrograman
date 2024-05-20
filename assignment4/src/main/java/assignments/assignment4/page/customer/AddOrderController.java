package assignments.assignment4.page.customer;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment4.MainApp;
import assignments.assignment4.helpers.TriConsumer;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddOrderController {
  private Stage stage;
  private MainApp mainApp;
  private List<Restaurant> restaurants;
  private TriConsumer<String, String, List<String>> onAddOrderHandler;
  @FXML private ComboBox<String> restaurantName;
  @FXML private TextField orderDate;
  @FXML private ListView<String> menuItems;

  public AddOrderController(
      Stage stage,
      MainApp mainApp,
      List<Restaurant> restaurants,
      TriConsumer<String, String, List<String>> onAddOrderHandler) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.restaurants = restaurants;
    this.onAddOrderHandler = onAddOrderHandler;
  }

  private void showAlert(String title, String header, String content, Alert.AlertType c) {
    Alert alert = new Alert(c);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  @FXML
  private void initialize() {
    this.restaurantName.setItems(
        FXCollections.observableArrayList(
            this.restaurants.stream().map(Restaurant::getNama).toList()));
    this.menuItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }

  // Event handler untuk redirect ke halaman utama customer
  @FXML
  private void redirectToBaseMenu() {
    Scene baseMenu = this.mainApp.getScene("Customer");
    this.mainApp.setScene(baseMenu);
  }

  // Event handler untuk melihat menu restoran
  @FXML
  private void handleListMenu() {
    String restaurantName = this.restaurantName.getValue();
    Restaurant restaurant = DepeFood.findRestaurant(restaurantName);
    List<String> menuItems = restaurant.getMenu().stream().map(Menu::getNamaMakanan).toList();
    this.menuItems.setItems(FXCollections.observableArrayList(menuItems));
  }

  // Event handler untuk menambahkan order
  @FXML
  private void handleAddOrder() {
    String restaurantName = this.restaurantName.getValue();
    String orderDate = this.orderDate.getText();
    List<String> orderMenu = this.menuItems.getSelectionModel().getSelectedItems();
    if (restaurantName == null) {
      this.showAlert(
          "Error",
          "Restaurant Name is empty",
          "Please choose the restaurant name",
          Alert.AlertType.ERROR);
    }
    if (orderDate.isEmpty()) {
      this.showAlert(
          "Error", "Order Date is empty", "Please fill the order date", Alert.AlertType.ERROR);
    }
    if (orderMenu.isEmpty()) {
      this.showAlert(
          "Error", "Order Menu is empty", "Please choose the order menu", Alert.AlertType.ERROR);
    }
    if (restaurantName != null && !orderDate.isEmpty() && !orderMenu.isEmpty()) {
      this.onAddOrderHandler.accept(restaurantName, orderDate, orderMenu);
    }
  }
}
