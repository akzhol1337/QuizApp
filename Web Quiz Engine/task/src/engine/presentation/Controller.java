package engine.presentation;

import engine.Business.Entity.*;
import engine.Business.Service.CompletionService;
import engine.Business.Service.QuizService;
import engine.Business.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class Controller {
    private final Result correct = new Result(true, "YES");
    private final Result wrong = new Result(false, "NO");

    QuizService quizService;
    UserService userService;
    CompletionService completionService;

    @Autowired
    public Controller(QuizService quizService, UserService userService, CompletionService completionService) {
        this.quizService = quizService;
        this.userService = userService;
        this.completionService = completionService;
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<Quiz>> getQuizzes(@RequestParam(defaultValue = "0") int page){
        return quizService.getQuizes(page);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Optional<Quiz> quiz = quizService.findById(id);

        User user = new User(userDetails);

        if(quiz.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if(quiz.get().getUser().getId() != user.getId()){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } else {
            completionService.deleteAll(completionService.findAllByQuiz(quiz.get()));
            quizService.delete(quiz.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<Quiz> createQuiz(@RequestBody @Valid Quiz quiz, @AuthenticationPrincipal UserDetailsImpl user){
        quiz.setUser(new User(user));
        quizService.save(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable long id){
        Optional<Quiz> quiz = quizService.findById(id);
        return new ResponseEntity<>(quiz.orElse(null), quiz.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Result> solveQuiz(@PathVariable long id, @RequestBody Answer answer, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Optional<Quiz> quiz = quizService.findById(id);
        if(quiz.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            Result result = wrong;
            if(quiz.get().answerIsCorrect(answer.getAnswer())){
                result = correct;
                User user = userService.findById(userDetails.getId()).get();
                completionService.addCompletion(quiz.get(), user);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<QuizCompletion>> getCompletedQuizzes(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam(defaultValue = "0") int page){
        User user = userService.findById(userDetails.getId()).get();
        Page<QuizCompletion> quizPage = completionService.getCompletedQuizzes(user, page);
        System.out.println("halo!");
        System.out.println(quizPage.getContent().get(0).getUser().getId());
        return new ResponseEntity(quizPage, HttpStatus.OK);

    }


}
