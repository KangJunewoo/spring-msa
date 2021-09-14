package socialmultiplication.multiplication.repository;

import org.springframework.data.repository.CrudRepository;
import socialmultiplication.multiplication.domain.Multiplication;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}
