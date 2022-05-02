package by.bsuir.psp.server.service.impl;

import by.bsuir.psp.model.dto.ReviewDto;
import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.model.dto.service.UserService;
import by.bsuir.psp.server.mapper.UserMapper;
import by.bsuir.psp.server.model.User;
import by.bsuir.psp.server.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  private final UserMapper mapper;

  @Override
  public UserDto authentication(String login, String password) {
    Optional<User> user = repository.findByLoginAndPassword(login, password);
    return user.map(mapper::userToDto).orElse(null);
  }

  @Override
  public UserDto save(UserDto userDto) {
   try {
     User dtoToUser = mapper.dtoToUser(userDto);
     User user = repository.save(dtoToUser);
     return mapper.userToDto(user);
   }catch (Exception e) {
     e.printStackTrace();
     return null;
   }
  }

  @Override
  public UserDto getById(UUID id) {
    return mapper.userToDto(repository.findById(id).orElseThrow(RuntimeException::new));
  }

  @Override
  public UserDto getByLogin(String login) {
    return mapper.userToDto(repository.findByLogin(login));
  }

  @Override
  public List<UserDto> getAll() {
    List<User> users = (List<User>) repository.findAll();
    return users.stream().map(mapper::userToDto).collect(Collectors.toList());
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }

  @Override
  public void deleteAll(List<UserDto> list) {
    repository.deleteAll(list.stream().map(mapper::dtoToUser).collect(Collectors.toList()));
  }

  @SneakyThrows
  @Override
  public void toExcel(UserDto userDto) {
    Workbook workbook = new XSSFWorkbook();

    Sheet sheet = workbook.createSheet(userDto.getName());
    sheet.setColumnWidth(0, 10000);
    sheet.setColumnWidth(1, 10000);
    sheet.setColumnWidth(2, 10000);

    Row header = sheet.createRow(0);

    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    XSSFFont font = ((XSSFWorkbook) workbook).createFont();
    font.setFontName("Times New Roman");
    font.setFontHeightInPoints((short) 14);
    font.setBold(true);
    headerStyle.setFont(font);

    Cell headerCell = header.createCell(0);
    headerCell.setCellValue("Дата получения ЗП");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(1);
    headerCell.setCellValue("ЗП");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(2);
    headerCell.setCellValue("Премия");
    headerCell.setCellStyle(headerStyle);

    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);

    Row row;
    Cell cell;
    for (int i = 0; i < userDto.getReviewDto().size(); i++) {
      row = sheet.createRow(i+1);
      ReviewDto reviewDto = userDto.getReviewDto().get(i);

//      cell = row.createCell(0);
//      cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(paymentDto.getReceiveDate()));
//      cell.setCellStyle(style);
//
//      cell = row.createCell(1);
//      cell.setCellValue(paymentDto.getSalary());
//      cell.setCellStyle(style);
//
//      cell = row.createCell(2);
//      cell.setCellValue(paymentDto.getAward());
//      cell.setCellStyle(style);
    }

    File currDir = new File(".");
    String path = currDir.getAbsolutePath();
    String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

    FileOutputStream outputStream = new FileOutputStream(fileLocation);
    workbook.write(outputStream);
    workbook.close();
  }
}
