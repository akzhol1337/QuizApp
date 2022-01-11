package engine.Business.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizCompletion {
    @Id
    @Column(name = "completion_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private User user;
    @OneToOne
    private Quiz quiz;

    @JsonProperty("id")
    public Long getQuiz() {
        return quiz.getId();
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    private LocalDateTime completedAt;

    public QuizCompletion(User user, Quiz quiz, LocalDateTime date) {
        this.user = user;
        this.quiz = quiz;
        this.completedAt = date;
    }
}
