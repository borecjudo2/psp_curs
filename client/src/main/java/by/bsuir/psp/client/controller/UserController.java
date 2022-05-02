package by.bsuir.psp.client.controller;

import by.bsuir.psp.client.config.StageManager;
import by.bsuir.psp.client.view.FxmlView;
import by.bsuir.psp.model.dto.InsuranceDto;
import by.bsuir.psp.model.dto.ReviewDto;
import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.model.dto.UserRole;
import by.bsuir.psp.model.dto.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;

@Setter
@Getter
@Slf4j
@Controller
public class UserController implements Initializable {

  @FXML
  private boolean currentUserBasic;

  @FXML
  private RadioButton rbAdmin;

  @FXML
  private Label userId;

  @FXML
  private TextField txUserName;

  @FXML
  private TextField txRewName;

  @FXML
  private TextField txRew;

  @FXML
  private TextField txInsuranceName;

  @FXML
  private TextField txInsuranceInfo;

  @FXML
  private TextField txLogin;

  @FXML
  private PasswordField txPassword;

  @FXML
  private DatePicker txBirthDate;

  @FXML
  private TextField txStar;

  @FXML
  private TableColumn<UserDto, String> columnLogin;

  @FXML
  private TableView<UserDto> userTable;

  @FXML
  private TableColumn<UserDto, UUID> columnUserId;

  @FXML
  private TableColumn<UserDto, UUID> columnName;

  @FXML
  private TableColumn<UserDto, String> columnInsuranceName;

  @FXML
  private TableColumn<UserDto, String> columnMiddleStar;


  @FXML
  private TableView<ReviewDto> reviewTable;

  @FXML
  private TableColumn<UserDto, Date> reviewColumnBirthDate;

  @FXML
  private TableColumn<UserDto, String> reviewColumnName;

  @FXML
  private TableColumn<UserDto, String> reviewColumnRew;

  
  @FXML
  private TableColumn<UserDto, Integer> reviewColumnStar;

  @FXML
  private Pane pane;

  @FXML
  private Label middleStar;

  @FXML
  private Label Star;

  @Lazy
  @Autowired
  private StageManager stageManager;

  @Autowired
  private UserService userService;

  private final ObservableList<UserDto> userList = FXCollections.observableArrayList();

  private final ObservableList<ReviewDto> ReviewDtoObservableList = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    clearFields();
    userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    setUserColumnProperties();
    setRewColumnProperties();

    loadUserDetails();
  }

  private void setUserColumnProperties() {
    columnUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnInsuranceName.setCellValueFactory(new PropertyValueFactory<>("insurance"));
    //columnMiddleStar.setCellValueFactory(new PropertyValueFactory<>("insurance"));
  }

  private void setRewColumnProperties() {
    reviewColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
    reviewColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    reviewColumnRew.setCellValueFactory(new PropertyValueFactory<>("rew"));
    reviewColumnStar.setCellValueFactory(new PropertyValueFactory<>("star"));
  }

  private void loadUserDetails() {
    userList.clear();
    userList.addAll(userService.getAll());
    userTable.setItems(userList);
    updateRewTable(Collections.emptyList());
  }

  private void updateRewTable(List<ReviewDto> ReviewDtos) {
    ReviewDtoObservableList.clear();
    ReviewDtoObservableList.addAll(ReviewDtos);
    reviewTable.setItems(ReviewDtoObservableList);
    log.info(String.valueOf(ReviewDtos));
  }

  @FXML
  private void logout() {
    stageManager.switchScene(FxmlView.LOGIN);
  }

  @FXML
  void reset() {
    clearFields();
    resetRewFields();
  }

  @FXML
  void resetRewFields() {
    txRewName.setText("");
    txRew.setText("");
    txBirthDate.getEditor().setText(null);
    txStar.setText("");
  }

  @FXML
  public void clickOnUserTable() {
    UserDto selectedItem = userTable.getSelectionModel().getSelectedItem();
    if (!Objects.isNull(selectedItem)) {
      boolean isAdmin = getUserRole(selectedItem).equals(UserRole.ADMIN);
      rbAdmin.setSelected(isAdmin);
      userId.setText(String.valueOf(selectedItem.getId()));
      txUserName.setText(selectedItem.getName());
      txInsuranceName.setText(selectedItem.getInsurance().getName());
      txInsuranceInfo.setText(selectedItem.getInsurance().getInfo());
      txLogin.setText(selectedItem.getLogin());
      txPassword.setText(selectedItem.getPassword());

      //GetReviewDto or getReview
      updateRewTable(selectedItem.getReviewDto());
      updateDiagram(selectedItem.getReviewDto());
    }
  }

  public void clickOnReviewTable() {
    ReviewDto selectedItem = reviewTable.getSelectionModel().getSelectedItem();
    if (!Objects.isNull(selectedItem)) {
      txRewName.setText(selectedItem.getName());
      txRew.setText(selectedItem.getRew());
      txStar.setText(String.valueOf(selectedItem.getStar()));

    }
  }

  @FXML
  private void saveUser() {
    if(userId.getText() == null || Objects.equals(userId.getText(), "")) {
      UserDto user = new UserDto();
      log.info("USER CREATED LOCAL");
      user.setRole(getUserRole(null));
      user.setName(txUserName.getText());
      user.setInsurance(new InsuranceDto(null, txInsuranceName.getText(), txInsuranceInfo.getText()));
      user.setLogin(txLogin.getText());
      user.setPassword(txPassword.getText());
      log.info("USER CREATED FULL");
      UserDto newUser = userService.save(user);
      log.info("USER CREATED DB");
      saveAlert(newUser);
    } else {
      UserDto user = userService.getById(UUID.fromString(userId.getText()));

      user.setRole(getUserRole(null));
      user.setName(txUserName.getText());
      user.setInsurance(new InsuranceDto(null, txInsuranceName.getText(), txInsuranceInfo.getText()));
      user.setLogin(txLogin.getText());
      user.setPassword(txPassword.getText());

      UserDto updatedUser = userService.save(user);
      updateAlert(updatedUser);
    }

    cleanDiagram();
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
  private void showInf() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(userTable.getSelectionModel().getSelectedItem().getName());
    alert.setHeaderText(userTable.getSelectionModel().getSelectedItem().getInsurance().getName());
    alert.setContentText(userTable.getSelectionModel().getSelectedItem().getInsurance().getInfo());
    alert.showAndWait();

  }

  @FXML
  public void saveRew() {
    UserDto selectedItem = userTable.getSelectionModel().getSelectedItem();
    if(!Objects.isNull(selectedItem)) {
      Date date = Date.from(txBirthDate.getValue().atStartOfDay()
          .atZone(ZoneId.systemDefault())
          .toInstant());
      String name = txRewName.getText();
      String rew = txRew.getText();
      int star = Integer.parseInt(txStar.getText());
      System.out.println(star);
      ReviewDto ReviewDto = new ReviewDto(null, name, rew, star, date);
      selectedItem.getReviewDto().add(ReviewDto);
      userService.save(selectedItem);
    }
    cleanDiagram();
    resetRewFields();
    loadUserDetails();
  }


  @FXML
  public void deleteRew() {
    ReviewDto ReviewDto = reviewTable.getSelectionModel().getSelectedItem();
    UserDto user = userTable.getSelectionModel().getSelectedItem();
    user.getReviewDto().remove(ReviewDto);

    userService.save(user);

    resetRewFields();
    loadUserDetails();
  }

  private void clearFields() {
    rbAdmin.setSelected(false);
    userId.setText(null);
    txUserName.clear();
    txInsuranceName.clear();
    txInsuranceInfo.clear();
    txLogin.clear();
    txPassword.clear();
    updateRewTable(Collections.emptyList());
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

  private void showExcelAlert() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Excel");
    alert.setHeaderText(null);
    alert.setContentText("temp.xlsx has created!");
    alert.showAndWait();
  }

  private UserRole getUserRole(UserDto userDto) {
    if(Objects.isNull(userDto)) {
      return rbAdmin.isSelected() ? UserRole.ADMIN : UserRole.BASIC;
    } else {
      return userDto.getRole();
    }
  }

  public void toExcel() {
    UserDto selectedItem = userTable.getSelectionModel().getSelectedItem();
    if (Objects.isNull(selectedItem)) {
      throw new RuntimeException();
    }
    userService.toExcel(selectedItem);
    showExcelAlert();
  }

  private void updateDiagram(List<ReviewDto> ReviewDtos) {


  }

  private String getMonth(Date date) {
    return new SimpleDateFormat("MMMM", new Locale("ru")).format(date);
  }

  private void cleanDiagram() {
    pane.getChildren().clear();
  }
}
