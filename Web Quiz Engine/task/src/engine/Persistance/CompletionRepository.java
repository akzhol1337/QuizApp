package engine.Persistance;

import engine.Business.Entity.Quiz;
import engine.Business.Entity.QuizCompletion;
import engine.Business.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompletionRepository extends CrudRepository<QuizCompletion, Long> {
    Page<QuizCompletion> findAllByUserIdOrderByCompletedAtDesc(Long id, Pageable pageable);
    List<QuizCompletion> findAllByQuiz(Quiz quiz);
}
