package banking.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "confirm_table")
@Entity
public class ConfirmationToken {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="token_id")
    private int token_id;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = Worker.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "worker_id")
    private Worker worker;

    public ConfirmationToken(Worker worker) {
        this.worker = worker;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
