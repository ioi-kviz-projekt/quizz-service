package ioi.quizz.services;

import ioi.quizz.lib.UserAnswer;
import ioi.quizz.lib.responses.QuizSummary;

public interface AnswerService {
    
    void saveUserAnswer(UserAnswer userAnswer);
    
    QuizSummary getUserSummary(String userId, String quizId);
    
}
