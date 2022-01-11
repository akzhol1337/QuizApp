package engine.Business.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz{


    @Id
    @GeneratedValue
    private Long id;

   @NotBlank
    @Column
    private String title;

    @NotBlank
    @Column
    private String text;

    @NotNull
    @Size(min=2)
    @Column
    @ElementCollection
    private List<String> options = new ArrayList<>();

    @ElementCollection
    @Column
    private List<Integer> answer = new ArrayList<>();

    @OneToOne
    User user;

    @JsonIgnore
    public List<Integer> getAnswer() {
        return answer;
    }
    @JsonProperty("answer")
    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean answerIsCorrect(List<Integer> answer) {
        for (Integer integer : this.answer) {
            System.out.print(integer + " ");
        }
        System.out.println();
        return new HashSet<>(this.answer).equals(new HashSet<>(answer));
    }
}
