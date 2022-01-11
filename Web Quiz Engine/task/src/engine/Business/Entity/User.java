package engine.Business.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Email
    @NotBlank
    private String email;
    @Size(min=5)
    @NotBlank
    private String password;

    public User(UserDetailsImpl user) {
        id = user.getId();
        email = user.getUsername();
        password = user.getPassword();
    }
}
