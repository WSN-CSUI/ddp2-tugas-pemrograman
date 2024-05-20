package assignments.assignment4.page;

import assignments.assignment4.MainApp;
import java.util.function.Supplier;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class MemberMenu {
  protected Stage stage;
  protected Scene scene;
  protected MainApp mainApp;

  protected abstract Scene createBaseMenu();

  protected void showAlert(String title, String header, String content, Alert.AlertType c) {
    Alert alert = new Alert(c);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  public Scene getScene() {
    return this.scene;
  }

  protected void refresh(String sceneName, Supplier<Scene> callback) {
    // TODO: Implemenetasi method ini untuk merefresh data yang dimiliki aplikasi
    // Hint: Method ini digunakan pada *seluruh method* yang membutuhkan update
    this.mainApp.addScene(sceneName, callback.get());
    this.mainApp.setScene(this.mainApp.getScene(sceneName));
  }
}
