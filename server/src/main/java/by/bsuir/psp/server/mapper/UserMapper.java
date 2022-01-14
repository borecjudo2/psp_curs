package by.bsuir.psp.server.mapper;

import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.server.model.User;
import org.mapstruct.Mapper;
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

  User dtoToUser(UserDto userDto);

  UserDto userToDto(User user);
}
