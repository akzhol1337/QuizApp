package task;

import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;

@RestController
@Validated
public class Controller {

    @GetMapping("/test/{id}")
    public int test(@PathVariable int id) {
        return id;
    }
}
