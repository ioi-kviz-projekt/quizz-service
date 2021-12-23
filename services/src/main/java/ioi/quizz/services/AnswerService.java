package ioi.quizz.services;

import ioi.quizz.lib.UserAnswer;
import ioi.quizz.lib.responses.QuizSummary;

import java.util.Map;

public interface AnswerService {
    
    void saveUserAnswer(UserAnswer userAnswer);
    
    QuizSummary getUserSummary(String userId, String quizId);
    
    Map<String, QuizSummary> getParticipantsSummaries(String quizId);
}
