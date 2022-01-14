package by.bsuir.psp.server.config;

import by.bsuir.psp.model.dto.service.UserService;
import by.bsuir.psp.server.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Configuration
public class Config {

  @Bean
  UserMapper userMapper() {
    return Mappers.getMapper(UserMapper.class);
  }

  @Bean
  RmiServiceExporter exporter(UserService implementation) {
    Class<UserService> serviceInterface = UserService.class;
    RmiServiceExporter exporter = new RmiServiceExporter();
    exporter.setServiceInterface(serviceInterface);
    exporter.setService(implementation);
    exporter.setServiceName(serviceInterface.getSimpleName());
    exporter.setRegistryPort(1099);
    return exporter;
  }
}
