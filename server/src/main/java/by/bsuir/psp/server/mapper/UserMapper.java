package by.bsuir.psp.server.mapper;

import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.server.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(source = "role", target = "role")
  User dtoToUser(UserDto userDto);

  @Mapping(source = "role", target = "role")
  UserDto userToDto(User user);
}
