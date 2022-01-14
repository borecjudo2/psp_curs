package by.bsuir.psp.client.config;

import by.bsuir.psp.model.dto.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Configuration
public class RmiConfig {

  @Bean
  @Primary
  RmiProxyFactoryBean service() {
    RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
    rmiProxyFactory.setServiceUrl("rmi://localhost:1099/UserService");
    rmiProxyFactory.setServiceInterface(UserService.class);
    return rmiProxyFactory;
  }

  @Bean
  UserService userService(ApplicationContext context) {
    return context.getBean(UserService.class);
  }
}
