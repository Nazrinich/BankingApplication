package banking.controller;


import banking.entities.ConfirmationToken;
import banking.entities.Customer;
import banking.entities.Mail;
import banking.entities.Worker;
import banking.repository.ConfirmationTokenRepository;
import banking.repository.CustomerRepo;
import banking.repository.UserRepo;
import banking.services.CustomerService;
import banking.services.EmailService;
import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class MainController {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    private final CustomerService customerService;
    private final CustomerRepo customerRepo;
    private final UserRepo userRepo;


    public MainController(CustomerService customerService, CustomerRepo customerRepo, UserRepo userRepo) {
        this.customerService = customerService;
        this.customerRepo = customerRepo;
        this.userRepo = userRepo;
    }





    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value={"/", "/customer-list/{amount}/{month}"}, method = RequestMethod.GET)
    public List<Customer> customerList(@PathVariable("amount") int amount,
                                       @PathVariable("month") int month,
                                       BindingResult bindingResult, HttpServletRequest request){
        int result= amount/month;
        Worker worker= new Worker();
        ConfirmationToken confirmationToken = new ConfirmationToken(worker);

        confirmationTokenRepository.save(confirmationToken);

        Mail mail = new Mail();
        mail.setFrom("new.news.2020@gmail.com");
        mail.setTo(worker.getEmail());
        mail.setSubject("List of customers is ready, please check");
        mail.setContent("Dear " + worker.getFull_name() +
                "\n\nTo confirm list of customers, please click here :  "
                + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/confirm-account?token="+confirmationToken.getConfirmationToken());
        emailService.sendEmail(mail);
       return customerService.allowedCustomersList(result);
    }

    @RequestMapping(value="/confirm", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        modelAndView.setViewName("confirm");

        return modelAndView;
    }
}
