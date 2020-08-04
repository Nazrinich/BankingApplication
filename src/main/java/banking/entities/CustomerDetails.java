package banking.entities;

import lombok.*;
import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customer_details")
public class CustomerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "paid_amount")
    private int paid_amount;

    @Column(name = "amount")
    private int amount;

    @Column(name = "paid_date")
    private int paid_date;

    @Column(name = "deadline")
    private int deadline;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "payment_history",
            joinColumns =
                    {@JoinColumn(name = "payment_id", referencedColumnName ="id" )},
            inverseJoinColumns =
                    {@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")}
    )
    private Customer customer;
}
