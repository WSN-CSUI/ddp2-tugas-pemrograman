package assignments.assignment4.page.admin;

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
  private final Supplier<Scene> addRestaurantScene;
  private final Supplier<Scene> addMenuScene;
  private final Supplier<Scene> viewRestaurantScene;

  public BaseController(
      Stage stage,
      MainApp mainApp,
      BiConsumer<String, Supplier<Scene>> refresh,
      Supplier<Scene> addRestaurantScene,
      Supplier<Scene> addMenuScene,
      Supplier<Scene> viewRestaurantScene) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.refresh = refresh;
    this.addRestaurantScene = addRestaurantScene;
    this.addMenuScene = addMenuScene;
    this.viewRestaurantScene = viewRestaurantScene;
  }

  // Event handler untuk logout
  @FXML
  private void logout() {
    this.mainApp.logout();
  }

  // Event handler untuk redirect ke halaman tambah restoran
  @FXML
  private void redirectToAddRestaurant() {
    this.refresh.accept("AdminAddRestaurant", this.addRestaurantScene);
    this.mainApp.setScene(this.mainApp.getScene("AdminAddRestaurant"));
    //    this.mainApp.setScene(this.addRestaurantScene);
  }

  // Event handler untuk redirect ke halaman tambah menu
  @FXML
  private void redirectToAddMenu() {
    this.refresh.accept("AdminAddMenu", this.addMenuScene);
    this.mainApp.setScene(this.mainApp.getScene("AdminAddMenu"));
    //    this.mainApp.setScene(this.addMenuScene);
  }

  // Event handler untuk redirect ke halaman lihat restoran
  @FXML
  private void redirectToRestaurant() {
    this.refresh.accept("AdminViewRestaurant", this.viewRestaurantScene);
    this.mainApp.setScene(this.mainApp.getScene("AdminViewRestaurant"));
    //    this.mainApp.setScene(this.viewRestaurantScene);
  }
}
