package by.bsuir.psp.server.service.impl;

import by.bsuir.psp.model.dto.UserDto;
import by.bsuir.psp.model.dto.service.UserService;
import by.bsuir.psp.server.mapper.UserMapper;
import by.bsuir.psp.server.model.User;
import by.bsuir.psp.server.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  private final UserMapper mapper;

  @Override
  public boolean isAuthentication(String login, String password) {
   return repository.findByLoginAndPassword(login, password).isPresent();
  }

  @Override
  public UserDto save(UserDto userDto) {
   try {
     User dtoToUser = mapper.dtoToUser(userDto);
     User user = repository.save(dtoToUser);
     return mapper.userToDto(user);
   }catch (Exception e) {
     e.printStackTrace();
     return null;
   }
  }

  @Override
  public UserDto getById(UUID id) {
    return mapper.userToDto(repository.findById(id).orElseThrow(RuntimeException::new));
  }

  @Override
  public UserDto getByLogin(String login) {
    return mapper.userToDto(repository.findByLogin(login));
  }

  @Override
  public List<UserDto> getAll() {
    List<User> users = (List<User>) repository.findAll();
    return users.stream().map(mapper::userToDto).collect(Collectors.toList());
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }

  @Override
  public void deleteAll(List<UserDto> list) {
    repository.deleteAll(list.stream().map(mapper::dtoToUser).collect(Collectors.toList()));
  }
}
