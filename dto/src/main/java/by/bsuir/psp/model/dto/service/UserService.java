package by.bsuir.psp.model.dto.service;

import by.bsuir.psp.model.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto authentication(String login, String password);

    UserDto save(UserDto userDto);

    UserDto getById(UUID id);

    UserDto getByLogin(String login);

    List<UserDto> getAll();

    void delete(UUID id);

    void deleteAll(List<UserDto> list);

    void toExcel(UserDto userDto);
}