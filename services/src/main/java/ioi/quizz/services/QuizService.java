package ioi.quizz.services;

import ioi.quizz.lib.ActiveQuizState;
import ioi.quizz.lib.QuizInstance;
import ioi.quizz.persistence.QuizInstanceEntity;

import java.util.Optional;

public interface QuizService {
    
    QuizInstance createInstance(String roomId);
    
    void startQuiz(String id);
    
    ActiveQuizState getActiveQuizState(String deviceId, String roomId);
    
    Optional<QuizInstanceEntity> getActiveQuizEntity(String deviceId, String roomId);
    
    Optional<QuizInstanceEntity> getQuizzEntity(String id);
    
}
