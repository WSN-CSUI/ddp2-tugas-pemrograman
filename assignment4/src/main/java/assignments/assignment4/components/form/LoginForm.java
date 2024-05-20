package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginForm {
  private final Stage stage;
  private final MainApp mainApp;

  public LoginForm(Stage stage, MainApp mainApp) {
    this.stage = stage;
    this.mainApp = mainApp;
  }

  public Scene getScene() {
    return this.createLoginForm();
  }

  // Method untuk menampilkan form login
  private Scene createLoginForm() {
    try {
      FXMLLoader loginFormFXML = new FXMLLoader(getClass().getResource("./login-form.fxml"));
      loginFormFXML.setControllerFactory(
          controller -> new LoginFormController(this.stage, this.mainApp, this::handleLogin));
      return new Scene(loginFormFXML.load(), 400, 600);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      return null;
    }
  }

  // Method untuk menangani login
  private void handleLogin(String username, String phone) {
    User userLoggedIn = DepeFood.handleLogin(username, phone);
    if (userLoggedIn == null) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Login Failed");
      alert.setContentText("User Not Found");
      alert.showAndWait();
      return;
    }

    this.mainApp.setUser(userLoggedIn);
    String userRole = this.mainApp.getUser().getRole();

    if (userRole.equalsIgnoreCase("admin")) {
      Scene adminScene = new AdminMenu(this.stage, this.mainApp, userLoggedIn).getScene();
      this.mainApp.addScene("Admin", adminScene);
      this.mainApp.setScene(this.mainApp.getScene("Admin"));
    } else {
      Scene customerScene = new CustomerMenu(this.stage, this.mainApp, userLoggedIn).getScene();
      this.mainApp.addScene("Customer", customerScene);
      this.mainApp.setScene(this.mainApp.getScene("Customer"));
    }
  }
}
