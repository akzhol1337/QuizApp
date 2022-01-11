package engine.Business.Service;

import engine.Business.Entity.Quiz;
import engine.Business.Entity.QuizCompletion;
import engine.Business.Entity.User;
import engine.Persistance.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompletionService {

    CompletionRepository repo;

    @Autowired
    public CompletionService(CompletionRepository repo) {
        this.repo = repo;
    }

    public void addCompletion(Quiz quiz, User user){
        repo.save(new QuizCompletion(user, quiz, java.time.LocalDateTime.now()));
    }

    public Page<QuizCompletion> getCompletedQuizzes(User user, int page) {
        return repo.findAllByUserIdOrderByCompletedAtDesc(user.getId(), PageRequest.of(page, 10));
    }

    public List<QuizCompletion> findAllByQuiz(Quiz quiz){
        return repo.findAllByQuiz(quiz);
    }


    public void deleteAll(List<QuizCompletion> quizCompletion) {
        for(QuizCompletion quiz : quizCompletion){
            repo.delete(quiz);
        }
    }
}
