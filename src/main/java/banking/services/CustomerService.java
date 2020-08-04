package banking.services;

import banking.entities.Customer;
import banking.repository.CustomerRepo;
import com.sun.tools.javac.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> allowedCustomersList(int id){
        return customerRepo.getAllByPaymentAfter(id);
    }


}
