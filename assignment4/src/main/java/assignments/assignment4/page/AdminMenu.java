package assignments.assignment4.page;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.admin.AddMenuController;
import assignments.assignment4.page.admin.AddRestaurantController;
import assignments.assignment4.page.admin.BaseController;
import assignments.assignment4.page.admin.RestaurantController;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu {
  private final User user;
  //  private final Stage stage;
  //  private final Scene scene;
  //  private final MainApp mainApp; // Reference to MainApp instance
  private final Scene addRestaurantScene;
  private final Scene addMenuScene;
  private final Scene viewRestaurantsScene;
  private final List<Restaurant> restoList;

  public AdminMenu(Stage stage, MainApp mainApp, User user) {
    this.user = user;
    this.stage = stage;
    this.mainApp = mainApp;
    this.restoList = DepeFood.getRestoList();
    this.addRestaurantScene = this.createAddRestaurantForm();
    this.addMenuScene = this.createAddMenuForm();
    this.viewRestaurantsScene = this.createViewRestaurantsForm();
    this.scene = this.createBaseMenu();
    this.mainApp.addScene("AdminAddRestaurant", this.addRestaurantScene);
    this.mainApp.addScene("AdminAddMenu", this.addMenuScene);
    this.mainApp.addScene("AdminViewRestaurant", this.viewRestaurantsScene);
  }

  @Override
  public Scene createBaseMenu() {
    try {
      FXMLLoader baseMenuFXML = new FXMLLoader(getClass().getResource("./admin/base.fxml"));
      baseMenuFXML.setControllerFactory(
          controller ->
              new BaseController(
                  this.stage,
                  this.mainApp,
                  this::refresh,
                  this::createAddRestaurantForm,
                  this::createAddMenuForm,
                  this::createViewRestaurantsForm));
      return new Scene(baseMenuFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private Scene createAddRestaurantForm() {
    try {
      FXMLLoader addRestaurantFXML =
          new FXMLLoader(getClass().getResource("./admin/add-restaurant.fxml"));
      addRestaurantFXML.setControllerFactory(
          controller ->
              new AddRestaurantController(this.stage, this.mainApp, this::handleTambahRestoran));
      return new Scene(addRestaurantFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private Scene createAddMenuForm() {
    try {
      FXMLLoader addMenuFXML = new FXMLLoader(getClass().getResource("./admin/add-menu.fxml"));
      addMenuFXML.setControllerFactory(
          controller ->
              new AddMenuController(this.stage, this.mainApp, this::handleTambahMenuRestoran));
      return new Scene(addMenuFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private Scene createViewRestaurantsForm() {
    try {
      FXMLLoader viewRestaurantFXML =
          new FXMLLoader(getClass().getResource("./admin/restaurant.fxml"));
      viewRestaurantFXML.setControllerFactory(
          controller ->
              new RestaurantController(this.stage, this.mainApp, this::handleLihatRestaurant));
      return new Scene(viewRestaurantFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  private void handleTambahRestoran(String nama) {
    String validRestoName = DepeFood.getValidRestaurantName(nama);
    if (!validRestoName.equalsIgnoreCase(nama)) {
      this.showAlert("Error", null, validRestoName, Alert.AlertType.ERROR);
      return;
    }
    DepeFood.handleTambahRestoran(nama);
    this.showAlert(
        "Success", null, "Restaurant Successfully Registered", Alert.AlertType.INFORMATION);
  }

  private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
    if (restaurant == null) {
      this.showAlert("Error", null, "Restaurant Not Found", Alert.AlertType.ERROR);
      return;
    }
    boolean isMenuAlreadyExists =
        restaurant.getMenu().stream()
            .anyMatch(menu -> menu.getNamaMakanan().equalsIgnoreCase(itemName));
    if (isMenuAlreadyExists) {
      this.showAlert("Error", null, "Menu Item Already Exists", Alert.AlertType.ERROR);
      return;
    }
    if (price < 0) {
      this.showAlert("Error", null, "Price Cannot Be Negative", Alert.AlertType.ERROR);
      return;
    }
    DepeFood.handleTambahMenuRestoran(restaurant, itemName, price);
    this.showAlert("Success", null, "Menu Item Added Successfully!", Alert.AlertType.INFORMATION);
  }

  private List<String> handleLihatRestaurant(String restaurantName) {
    Restaurant restaurant = DepeFood.findRestaurant(restaurantName);
    if (restaurant == null) {
      this.showAlert("Error", null, "Restaurant Not Found", Alert.AlertType.ERROR);
      return null;
    }
    return restaurant.getMenu() == null
        ? null
        : restaurant.getMenu().stream()
            .map(menu -> String.format("%s - %s", menu.getNamaMakanan(), menu.getHarga()))
            .toList();
  }
}
