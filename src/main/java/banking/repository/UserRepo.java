package banking.repository;

import banking.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Worker, Integer> {
    Optional<Worker> findByEmail(String user_email);
}
