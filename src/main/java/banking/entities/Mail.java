package banking.entities;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String content;

}
