package by.bsuir.psp.client;

import by.bsuir.psp.client.config.StageManager;
import by.bsuir.psp.client.view.FxmlView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {

    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        springContext = springBootApplicationContext();
    }

    @Override
    public void start(Stage stage) {
//        UserService service = springContext.getBean(UserService.class);
//        AwardDto awardDto = new AwardDto();
//        awardDto.setAmount(100L);
//        awardDto.setReceiveDate(new Date());
//
//        DepartmentDto departmentDto = new DepartmentDto();
//        departmentDto.setName("main");
//
//        UserDto dto = new UserDto();
//        dto.setName("login");
//        dto.setLogin("login");
//        dto.setPassword("pass");
//        dto.setAwards(Collections.singletonList(awardDto));
//        dto.setDepartment(departmentDto);
//
//        UserDto save = service.save(dto);
//        System.out.println(save);
        stageManager = springContext.getBean(StageManager.class, stage);
        displayInitialScene();
    }

    @Override
    public void stop() {
        springContext.close();
    }

    protected void displayInitialScene() {
        stageManager.switchScene(FxmlView.LOGIN);
    }
    
    private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        String[] args = getParameters().getRaw().toArray(String[]::new);
        return builder.run(args);
    }
}
