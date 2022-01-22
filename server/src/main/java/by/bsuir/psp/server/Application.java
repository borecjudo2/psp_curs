package by.bsuir.psp.server;

import by.bsuir.psp.model.dto.UserRole;
import by.bsuir.psp.server.model.OverratedTime;
import by.bsuir.psp.server.model.Payment;
import by.bsuir.psp.server.model.Department;
import by.bsuir.psp.server.model.User;
import by.bsuir.psp.server.repo.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
		UserRepository userRepository = run.getBean(UserRepository.class);
		userRepository.save(User.builder()
				.name("Vlad")
				.role(UserRole.ADMIN)
				.login("adm")
				.password("adm")
				.department(new Department(null, "MAIN"))
				.payments(Collections.singletonList(new Payment(null, new Date(), 1000L, 300L, new OverratedTime(null, 10))))
				.build());
	}
}
