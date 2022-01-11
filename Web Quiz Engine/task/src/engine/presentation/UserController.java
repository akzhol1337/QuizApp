package engine.presentation;

import engine.Business.Entity.User;
import engine.Persistance.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    UserRepository repo;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/register")
    public ResponseEntity register(@RequestBody @Valid User user){
        if(repo.findByEmail(user.getEmail()) != null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        boolean dot = false;
        boolean sobaka = false;
        for(int i = 0; i < user.getEmail().length(); i++){
            if(user.getEmail().charAt(i) == '.'){
                dot = true;
            } else if(user.getEmail().charAt(i) == '@'){
                sobaka = true;
            }
        }
        if(!dot || !sobaka){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
