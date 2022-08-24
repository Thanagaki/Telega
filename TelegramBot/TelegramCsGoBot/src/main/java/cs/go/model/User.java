package cs.go.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends  AbstractBaseEntity {

    @Column(name = "chat_id", unique = true, nullable = false)
    @NotNull
    long chatId;

    @Column(name = "username", unique = true, nullable = false)
    @NotBlank
    String userName;

    @Column(name = "bot_state", nullable = false)
    @NotBlank
    private State botState;


    public User(long chatId) {

        this.chatId = chatId;

    }
}
