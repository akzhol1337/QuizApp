package task;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.util.*;
import java.util.concurrent.*;

@RestController
public class Controller {

    @PostMapping("/api/colors")
    public void acceptColors(@Valid @RequestBody Entity entity){

    }
}

class Entity {
    @Size(min=3, max=12)
    @NotNull
    private String[] colors;

    public Entity(String[] colors) {
        this.colors = colors;
    }

    public Entity() {
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }
}