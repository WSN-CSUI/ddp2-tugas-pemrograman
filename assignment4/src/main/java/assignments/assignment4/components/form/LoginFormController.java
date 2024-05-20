package assignments.assignment4.components.form;

import assignments.assignment4.MainApp;
import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginFormController {
  private Stage stage;
  private MainApp mainApp;
  private BiConsumer<String, String> onLoginHandler;
  @FXML private TextField username;
  @FXML private TextField phone;

  public LoginFormController(
      Stage stage, MainApp mainApp, BiConsumer<String, String> onLoginHandler) {
    this.stage = stage;
    this.mainApp = mainApp;
    this.onLoginHandler = onLoginHandler;
  }

  private void showAlert(String title, String header, String content, Alert.AlertType c) {
    Alert alert = new Alert(c);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
  }

  @FXML
  private void handleLogin() {
    String username = this.username.getText();
    String phone = this.phone.getText();
    if (username.isEmpty()) {
      this.showAlert(
          "Error", "Username is Empty", "Please fill the username", Alert.AlertType.ERROR);
    }
    if (phone.isEmpty()) {
      this.showAlert("Error", "Phone is Empty", "Please fill the phone", Alert.AlertType.ERROR);
    }
    if (!username.isEmpty() && !phone.isEmpty()) {
      this.onLoginHandler.accept(username, phone);
    }
  }
}
