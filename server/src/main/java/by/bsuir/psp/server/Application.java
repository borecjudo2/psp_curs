package by.bsuir.psp.server;

import by.bsuir.psp.model.dto.AwardDto;
import by.bsuir.psp.model.dto.DepartmentDto;
import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.model.dto.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;
import java.util.Date;

@EnableScheduling
@Slf4j
@SpringBootApplication
public class Application {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Scheduled(fixedDelay = 1000L)
//	public void save() {
//		AwardDto awardDto = new AwardDto();
//		awardDto.setAmount(100L);
//		awardDto.setReceiveDate(new Date());
//
//		DepartmentDto departmentDto = new DepartmentDto();
//		departmentDto.setName("main");
//
//		UserDto dto = new UserDto();
//		dto.setName("login");
//		dto.setLogin("login");
//		dto.setPassword("pass");
//		dto.setAwards(Collections.singletonList(awardDto));
//		dto.setDepartment(departmentDto);
//
//		UserDto save = userService.save(dto);
//		System.out.println(save);
//	}

}
