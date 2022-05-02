package by.bsuir.psp.server;

import by.bsuir.psp.model.dto.UserRole;
import by.bsuir.psp.server.model.Insurance;
import by.bsuir.psp.server.model.Review;
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
				.name("Kirill")
				.role(UserRole.ADMIN)
				.login("1")
				.password("1")
				.insurance(new Insurance(null, "Hilton", "some info"))
				.reviewDto(Collections.singletonList(new Review(null, "Better then Sputnik",
						"Chill!!!", 5, new Date())))
				.build());
	}
}
