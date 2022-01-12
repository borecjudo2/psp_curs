package by.bsuir.psp.client;

import by.bsuir.psp.model.dto.AwardDto;
import by.bsuir.psp.model.dto.DepartmentDto;
import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.model.dto.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		UserService service = SpringApplication.run(Application.class, args).getBean(UserService.class);
		AwardDto awardDto = new AwardDto();
		awardDto.setAmount(100L);
		awardDto.setReceiveDate(new Date());

		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setName("main");

		UserDto dto = new UserDto();
		dto.setName("login");
		dto.setLogin("login");
		dto.setPassword("pass");
		dto.setAwards(Collections.singletonList(awardDto));
		dto.setDepartment(departmentDto);

		UserDto save = service.save(dto);
		System.out.println(save);
	}

	@Bean
	RmiProxyFactoryBean service() {
		RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
		rmiProxyFactory.setServiceUrl("rmi://localhost:1099/UserService");
		rmiProxyFactory.setServiceInterface(UserService.class);
		return rmiProxyFactory;
	}
}
