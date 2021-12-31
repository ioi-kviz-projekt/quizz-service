package ioi.quizz.services;

import ioi.quizz.lib.UserAnswer;
import ioi.quizz.lib.responses.QuizSummary;

import java.util.Map;

public interface AnswerService {
    
    void saveUserAnswer(UserAnswer userAnswer, String deviceId);
    
    Map<String, QuizSummary> getParticipantsSummaries(String quizId);
}
