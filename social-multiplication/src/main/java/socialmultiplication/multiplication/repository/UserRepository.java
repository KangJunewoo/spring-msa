package socialmultiplication.multiplication.repository;

import org.springframework.data.repository.CrudRepository;
import socialmultiplication.multiplication.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByAlias(final String alias);
}
