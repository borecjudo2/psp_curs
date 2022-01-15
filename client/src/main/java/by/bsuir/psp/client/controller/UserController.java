package by.bsuir.psp.client.controller;

import by.bsuir.psp.client.config.StageManager;
import by.bsuir.psp.client.view.FxmlView;
import by.bsuir.psp.model.dto.AwardDto;
import by.bsuir.psp.model.dto.DepartmentDto;
import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.model.dto.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Getter
@Slf4j
@Controller
public class UserController implements Initializable {

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
  private DatePicker txAwardDate;

  @FXML
  private TextField txAward;


  @FXML
  private TableColumn<UserDto, String> columnLogin;

  @FXML
  private TableView<UserDto> userTable;

  @FXML
  private TableColumn<UserDto, UUID> columnUserId;

  @FXML
  private TableColumn<UserDto, String> columnName;

  @FXML
  private TableColumn<UserDto, String> columnDepartmentName;

  @FXML
  private boolean renderAwardTable = false;

  public boolean isRenderAwardTable() {
    return renderAwardTable;
  }

  @FXML
  private TableView<AwardDto> awardTable;

  @FXML
  private TableColumn<UserDto, Date> awardColumnDate;

  @FXML
  private TableColumn<UserDto, String> awardColumnAmount;

  @Lazy
  @Autowired
  private StageManager stageManager;

  @Autowired
  private UserService userService;

  private final ObservableList<UserDto> userList = FXCollections.observableArrayList();

  private final ObservableList<AwardDto> awardDtoObservableList = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    setUserColumnProperties();
    setAwardColumnProperties();

    loadUserDetails();
  }

  private void setUserColumnProperties() {
    columnUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnDepartmentName.setCellValueFactory(new PropertyValueFactory<>("department"));
  }

  private void setAwardColumnProperties() {
    awardColumnDate.setCellValueFactory(new PropertyValueFactory<>("receiveDate"));
    awardColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
  }

  private void loadUserDetails() {
    userList.clear();
    userList.addAll(userService.getAll());
    userTable.setItems(userList);
    updateAwardTable(Collections.emptyList());
  }

  private void updateAwardTable(List<AwardDto> awardDtoList) {
    awardDtoObservableList.clear();
    awardDtoObservableList.addAll(awardDtoList);
    awardTable.setItems(awardDtoObservableList);
    log.info(String.valueOf(awardDtoList));
  }

  @FXML
  private void logout() {
    stageManager.switchScene(FxmlView.LOGIN);
  }

  @FXML
  void reset() {
    clearFields();
    resetAwardFields();
  }

  @FXML
  void resetAwardFields() {
    txAward.setText("");
    txAwardDate.getEditor().setText(null);
  }

  @FXML
  public void clickedOnUserTable() {
    UserDto selectedItem = userTable.getSelectionModel().getSelectedItem();
    if (!Objects.isNull(selectedItem)) {
      userId.setText(String.valueOf(selectedItem.getId()));
      txName.setText(selectedItem.getName());
      txDepartmentName.setText(selectedItem.getDepartment().getName());
      txLogin.setText(selectedItem.getLogin());
      txPassword.setText(selectedItem.getPassword());

      renderAwardTable = true;
      log.info(String.valueOf(renderAwardTable));
      updateAwardTable(selectedItem.getAwards());
    }
  }

  @FXML
  private void saveUser() {
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
  private void deleteUser() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to delete selected?");
    ButtonType action = alert.showAndWait().orElseThrow(RuntimeException::new);

		if (action == ButtonType.OK) {
			userService.delete(userTable.getSelectionModel().getSelectedItem().getId());
		}
    loadUserDetails();
  }

  @FXML
  public void saveAward() {
    UserDto selectedItem = userTable.getSelectionModel().getSelectedItem();
    if(!Objects.isNull(selectedItem)) {
      Date date = Date.from(txAwardDate.getValue().atStartOfDay()
          .atZone(ZoneId.systemDefault())
          .toInstant());
      AwardDto awardDto = new AwardDto(null, date, Long.parseLong(txAward.getText()));
      selectedItem.getAwards().add(awardDto);
      userService.save(selectedItem);
    }
    resetAwardFields();
    loadUserDetails();
  }

  @FXML
  public void deleteAward() {
    AwardDto award = awardTable.getSelectionModel().getSelectedItem();
    UserDto user = userTable.getSelectionModel().getSelectedItem();
    user.getAwards().remove(award);

    userService.save(user);

    resetAwardFields();
    loadUserDetails();
  }

  private void clearFields() {
    renderAwardTable = false;
    userId.setText(null);
    txName.clear();
    txDepartmentName.clear();
    txLogin.clear();
    txPassword.clear();
    updateAwardTable(Collections.emptyList());
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


}
