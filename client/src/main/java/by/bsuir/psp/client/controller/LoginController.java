package by.bsuir.psp.client.controller;

import by.bsuir.psp.client.config.StageManager;
import by.bsuir.psp.client.view.FxmlView;
import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.model.dto.UserRole;
import by.bsuir.psp.model.dto.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class LoginController implements Initializable {

  @Autowired
  private UserController userController;

  @FXML
  private Button btnLogin;

  @FXML
  private PasswordField password;

  @FXML
  private TextField username;

  @FXML
  private Label lblLogin;

  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private StageManager stageManager;

  @FXML
  private void login() {
    UserDto userDto = userService.authentication(getUsername(), getPassword());
    if (!Objects.isNull(userDto)) {
      userController.setCurrentUserBasic(userDto.getRole().equals(UserRole.BASIC));
      stageManager.switchScene(FxmlView.USER);
    } else {
      lblLogin.setText("Ошибка входа.");
    }
  }

  public String getPassword() {
    return password.getText();
  }

  public String getUsername() {
    return username.getText();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
