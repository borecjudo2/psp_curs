package by.bsuir.psp.client.controller;

import by.bsuir.psp.client.config.StageManager;
import by.bsuir.psp.client.view.FxmlView;
import by.bsuir.psp.model.dto.DepartmentDto;
import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.model.dto.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Slf4j
@Controller
public class UserController implements Initializable {

  @FXML
  private Button btnLogout;

  @FXML
  private Label userId;

  @FXML
  private TextField txName;

  @FXML
  private TextField txDepartmentName;

  @FXML
  private TextField txLogin;

  @FXML
  private PasswordField txPassword;

  @FXML
  private Button reset;

  @FXML
  private Button saveUser;

  @FXML
  private TableView<UserDto> userTable;

  @FXML
  private TableColumn<UserDto, UUID> columnUserId;

  @FXML
  private TableColumn<UserDto, String> columnName;

  @FXML
  private TableColumn<UserDto, String> columnDepartmentName;

  @FXML
  private TableColumn<UserDto, String> columnLogin;

  @FXML
  private TableColumn<UserDto, Boolean> colEdit;

  @FXML
  private MenuItem deleteUsers;

  @Lazy
  @Autowired
  private StageManager stageManager;

  @Autowired
  private UserService userService;

  private ObservableList<UserDto> userList = FXCollections.observableArrayList();


  @FXML
  private void logout(ActionEvent event) {
    stageManager.switchScene(FxmlView.LOGIN);
  }

  @FXML
  void reset(ActionEvent event) {
    clearFields();
  }

//  @FXML
//  private void saveUser(ActionEvent event) {
//
//    if (validate("First Name", getFirstName(), "[a-zA-Z]+") &&
//        validate("Last Name", getLastName(), "[a-zA-Z]+") &&
//        emptyValidation("DOB", dob.getEditor().getText().isEmpty()) &&
//        emptyValidation("Role", getRole() == null)) {
//
//      if (userId.getText() == null || userId.getText() == "") {
//        if (validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+") &&
//            emptyValidation("Password", getPassword().isEmpty())) {
//
//          UserDto user = new UserDto();
//          user.setFirstName(getFirstName());
//          user.setLastName(getLastName());
//          user.setDob(getDob());
//          user.setGender(getGender());
//          user.setRole(getRole());
//          user.setEmail(getEmail());
//          user.setPassword(getPassword());
//
//          UserDto newUser = userService.save(user);
//
//          saveAlert(newUser);
//        }
//      } else {
//        UserDto user = userService.getById(UUID.fromString(userId.getText()));
//        user.setFirstName(getFirstName());
//        user.setLastName(getLastName());
//        user.setDob(getDob());
//        user.setGender(getGender());
//        user.setRole(getRole());
//        UserDto updatedUser = userService.save(user);
//        updateAlert(updatedUser);
//      }
//
//      clearFields();
//      loadUserDetails();
//    }
//  }

  @FXML
  private void saveUser(ActionEvent event) {
    if(userId.getText() == null || Objects.equals(userId.getText(), "")) {
      UserDto user = new UserDto();
      log.info("USER CREATED LOCAL");
      user.setName(txName.getText());
      user.setDepartment(new DepartmentDto(null, txDepartmentName.getText()));
      user.setLogin(txLogin.getText());
      user.setPassword(txPassword.getText());
      log.info("USER CREATED FULL");
      UserDto newUser = userService.save(user);
      log.info("USER CREATED DB");
      saveAlert(newUser);
    } else {
      UserDto user = userService.getById(UUID.fromString(userId.getText()));

      user.setName(txName.getText());
      user.setDepartment(new DepartmentDto(null, txDepartmentName.getText()));
      user.setLogin(txLogin.getText());
      user.setPassword(txPassword.getText());

      UserDto updatedUser = userService.save(user);
      updateAlert(updatedUser);
    }

    clearFields();
    loadUserDetails();
  }

  @FXML
  private void deleteUser(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to delete selected?");
    Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {
			userService.delete(userTable.getSelectionModel().getSelectedItem().getId());
		}

    loadUserDetails();
  }

  private void clearFields() {
    userId.setText(null);
    txName.clear();
    txDepartmentName.clear();
    txLogin.clear();
    txPassword.clear();
  }

  private void saveAlert(UserDto user) {

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("User saved successfully.");
    alert.setHeaderText(null);
    alert.setContentText("The user " + user.getName() + " " + user.getLogin() + " has been created.");
    alert.showAndWait();
  }

  private void updateAlert(UserDto user) {

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("User updated successfully.");
    alert.setHeaderText(null);
    alert.setContentText("The user " + user.getName() + " " + user.getLogin() + " has been updated.");
    alert.showAndWait();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    setColumnProperties();

    loadUserDetails();
  }

  private void setColumnProperties() {
    columnUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnDepartmentName.setCellValueFactory(new PropertyValueFactory<>("department"));
//    c.setCellValueFactory(new PropertyValueFactory<>("password"));
//    colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
//    colRole.setCellValueFactory(new PropertyValueFactory<>("department"));
//    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
//    colEdit.setCellFactory(cellFactory);
  }

//  Callback<TableColumn<UserDto, Boolean>, TableCell<UserDto, Boolean>> cellFactory =
//      new Callback<>() {
//        @Override
//        public TableCell<UserDto, Boolean> call(final TableColumn<UserDto, Boolean> param) {
//          final TableCell<UserDto, Boolean> cell = new TableCell<UserDto, Boolean>() {
//            Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
//            final Button btnEdit = new Button();
//
//            @Override
//            public void updateItem(Boolean check, boolean empty) {
//              super.updateItem(check, empty);
//              if (empty) {
//                setGraphic(null);
//                setText(null);
//              } else {
//                btnEdit.setOnAction(e -> {
//                  UserDto user = getTableView().getItems().get(getIndex());
//                  updateUser(user);
//                });
//
//                btnEdit.setStyle("-fx-background-color: transparent;");
//                ImageView iv = new ImageView();
//                iv.setImage(imgEdit);
//                iv.setPreserveRatio(true);
//                iv.setSmooth(true);
//                iv.setCache(true);
//                btnEdit.setGraphic(iv);
//
//                setGraphic(btnEdit);
//                setAlignment(Pos.CENTER);
//                setText(null);
//              }
//            }
//
//            private void updateUser(UserDto user) {
//              userId.setText(Long.toString(user.getId()));
//              firstName.setText(user.getFirstName());
//              lastName.setText(user.getLastName());
//              dob.setValue(user.getDob());
//              if (user.getGender().equals("Male")) {
//                rbMale.setSelected(true);
//              } else {
//                rbFemale.setSelected(true);
//              }
//              cbRole.getSelectionModel().select(user.getRole());
//            }
//          };
//          return cell;
//        }
//      };

  private void loadUserDetails() {
    userList.clear();
    userList.addAll(userService.getAll());

    userTable.setItems(userList);
  }

  private boolean validate(String field, String value, String pattern) {
    if (!value.isEmpty()) {
      Pattern p = Pattern.compile(pattern);
      Matcher m = p.matcher(value);
      if (m.find() && m.group().equals(value)) {
        return true;
      } else {
        validationAlert(field, false);
        return false;
      }
    } else {
      validationAlert(field, true);
      return false;
    }
  }

  private boolean emptyValidation(String field, boolean empty) {
    if (!empty) {
      return true;
    } else {
      validationAlert(field, true);
      return false;
    }
  }

  private void validationAlert(String field, boolean empty) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle("Validation Error");
    alert.setHeaderText(null);
		if (field.equals("Role")) {
			alert.setContentText("Please Select " + field);
		} else {
			if (empty) {
				alert.setContentText("Please Enter " + field);
			} else {
				alert.setContentText("Please Enter Valid " + field);
			}
		}
    alert.showAndWait();
  }
}
