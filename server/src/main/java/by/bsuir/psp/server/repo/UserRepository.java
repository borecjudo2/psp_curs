package by.bsuir.psp.server.repo;

import by.bsuir.psp.server.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * DESCRIPTION
 *
 * @author Vladislav_Karpeka
 * @version 1.0.0
 */
public interface UserRepository extends CrudRepository<User, UUID> {

  User findByLogin(String login);
}
