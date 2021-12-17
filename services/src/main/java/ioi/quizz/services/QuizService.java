package ioi.quizz.services;

import ioi.quizz.lib.QuizInstance;
import ioi.quizz.lib.requests.QuizPasskey;
import ioi.quizz.lib.responses.QuizSummary;
import ioi.quizz.persistence.QuizInstanceEntity;

import java.util.Optional;

public interface QuizService {
    
    QuizInstance createInstance();
    
    QuizInstance getQuizByPasskey(QuizPasskey passkey);
    
    void startQuiz(String id);
    
    Optional<QuizInstanceEntity> getQuizzEntity(String id);
    
    QuizSummary getQuizzSummary(String userId, String quizId);
    
}
