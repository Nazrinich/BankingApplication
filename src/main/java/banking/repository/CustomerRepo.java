package banking.repository;

import banking.entities.Customer;
import com.sun.tools.javac.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    List<Customer> getAllByPaymentAfter(int payment);
}
