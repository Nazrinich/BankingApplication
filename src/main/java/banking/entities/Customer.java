package banking.entities;

import lombok.*;
import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customer_table")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customer_id;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @Column(name="payment")
    private int payment;

    @OneToOne(mappedBy = "customer")
    private CustomerDetails customerDetails;
}
