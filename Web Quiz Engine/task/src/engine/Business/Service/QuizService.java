package engine.Business.Service;

import engine.Business.Entity.Quiz;
import engine.Business.Entity.User;
import engine.Persistance.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    QuizRepository repo;

    @Autowired
    public QuizService(QuizRepository repo) {
        this.repo = repo;
    }

    public Optional<Quiz> findById(Long id){
        return repo.findById(id);
    }

    public List<Quiz> findAll(){
        return (List<Quiz>) repo.findAll();
    }

    public ResponseEntity<Page<Quiz>> getQuizes(int page){
        return new ResponseEntity(repo.findAll(PageRequest.of(page, 10)), HttpStatus.OK);
    }

    public void save(Quiz quiz){
        repo.save(quiz);
    }

    public void delete(Quiz quiz) {
        repo.delete(quiz);
    }
}
