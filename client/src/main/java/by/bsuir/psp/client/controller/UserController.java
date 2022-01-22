package by.bsuir.psp.client.controller;

import by.bsuir.psp.client.config.StageManager;
import by.bsuir.psp.client.view.FxmlView;
import by.bsuir.psp.model.dto.DepartmentDto;
import by.bsuir.psp.model.dto.OverratedTimeDto;
import by.bsuir.psp.model.dto.PaymentDto;
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

  private static final double AWARD_PERCENT = 0.1;

  @FXML
  private boolean currentUserBasic;

  @FXML
  private RadioButton rbAdmin;

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
  private DatePicker txPaymentDate;

  @FXML
  private TextField txSalary;

  @FXML
  private TextField txOverratedTime;

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
  private TableView<PaymentDto> paymentTable;

  @FXML
  private TableColumn<UserDto, Date> paymentColumnDate;

  @FXML
  private TableColumn<UserDto, String> paymentColumnSalary;

  @FXML
  private TableColumn<UserDto, String> paymentColumnAward;

  @FXML
  private TableColumn<UserDto, Integer> paymentColumnOverratedTime;

  @FXML
  private Pane pane;

  @Lazy
  @Autowired
  private StageManager stageManager;

  @Autowired
  private UserService userService;

  private final ObservableList<UserDto> userList = FXCollections.observableArrayList();

  private final ObservableList<PaymentDto> paymentDtoObservableList = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    clearFields();
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
    paymentColumnDate.setCellValueFactory(new PropertyValueFactory<>("receiveDate"));
    paymentColumnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    paymentColumnAward.setCellValueFactory(new PropertyValueFactory<>("award"));
    paymentColumnOverratedTime.setCellValueFactory(new PropertyValueFactory<>("overratedTime"));
  }

  private void loadUserDetails() {
    userList.clear();
    userList.addAll(userService.getAll());
    userTable.setItems(userList);
    updateAwardTable(Collections.emptyList());
  }

  private void updateAwardTable(List<PaymentDto> paymentDtos) {
    paymentDtoObservableList.clear();
    paymentDtoObservableList.addAll(paymentDtos);
    paymentTable.setItems(paymentDtoObservableList);
    log.info(String.valueOf(paymentDtos));
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
    txSalary.setText("");
    txPaymentDate.getEditor().setText(null);
    txOverratedTime.setText("");
  }

  @FXML
  public void clickOnUserTable() {
    UserDto selectedItem = userTable.getSelectionModel().getSelectedItem();
    if (!Objects.isNull(selectedItem)) {
      boolean isAdmin = getUserRole(selectedItem).equals(UserRole.ADMIN);
      rbAdmin.setSelected(isAdmin);
      userId.setText(String.valueOf(selectedItem.getId()));
      txName.setText(selectedItem.getName());
      txDepartmentName.setText(selectedItem.getDepartment().getName());
      txLogin.setText(selectedItem.getLogin());
      txPassword.setText(selectedItem.getPassword());

      updateAwardTable(selectedItem.getPayments());
      updateDiagram(selectedItem.getPayments());
    }
  }

  @FXML
  private void saveUser() {
    if(userId.getText() == null || Objects.equals(userId.getText(), "")) {
      UserDto user = new UserDto();
      log.info("USER CREATED LOCAL");
      user.setRole(getUserRole(null));
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

      user.setRole(getUserRole(null));
      user.setName(txName.getText());
      user.setDepartment(new DepartmentDto(null, txDepartmentName.getText()));
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
  public void saveAward() {
    UserDto selectedItem = userTable.getSelectionModel().getSelectedItem();
    if(!Objects.isNull(selectedItem)) {
      Date date = Date.from(txPaymentDate.getValue().atStartOfDay()
          .atZone(ZoneId.systemDefault())
          .toInstant());
      long salary = Long.parseLong(txSalary.getText());
      int overratedTime = Integer.parseInt(txOverratedTime.getText());
      System.out.println(overratedTime);
      PaymentDto paymentDto = new PaymentDto(null, date, salary, getAwardBySalary(salary, overratedTime),
          new OverratedTimeDto(null, overratedTime));
      selectedItem.getPayments().add(paymentDto);
      userService.save(selectedItem);
    }
    cleanDiagram();
    resetAwardFields();
    loadUserDetails();
  }

  private Long getAwardBySalary(long salary, int overratedTime) {
    Double award = salary * AWARD_PERCENT;
    if (overratedTime > 0) {
      award += overratedTime * salary * 0.01;
    }
    return award.longValue();
  }

  @FXML
  public void deleteAward() {
    PaymentDto paymentDto = paymentTable.getSelectionModel().getSelectedItem();
    UserDto user = userTable.getSelectionModel().getSelectedItem();
    user.getPayments().remove(paymentDto);

    userService.save(user);

    resetAwardFields();
    loadUserDetails();
  }

  private void clearFields() {
    rbAdmin.setSelected(false);
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

  private void updateDiagram(List<PaymentDto> paymentDtos) {
    cleanDiagram();
    CategoryAxis categoryAxis = new CategoryAxis();
    categoryAxis.setLabel("Месяц");

    NumberAxis numberAxis = new NumberAxis();
    numberAxis.setLabel("Сумма");

    BarChart chart = new BarChart(categoryAxis, numberAxis);
    chart.setTitle("Месячная премия");

    XYChart.Series series = new XYChart.Series();
    series.setName("Премия");
    paymentDtos.forEach( paymentDto ->
        series.getData().add(new XYChart.Data<>(getMonth(paymentDto.getReceiveDate()), paymentDto.getAward())));

    chart.getData().add(series);
    pane.getChildren().add(chart);
  }

  private String getMonth(Date date) {
    return new SimpleDateFormat("MMMM", new Locale("ru")).format(date);
  }

  private void cleanDiagram() {
    pane.getChildren().clear();
  }
}
