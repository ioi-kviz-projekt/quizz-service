package ioi.quizz.services;

import ioi.quizz.lib.requests.QuestionCreateRequest;

public interface QuestionService {
    
    void addQuestion(String deviceId, QuestionCreateRequest request);
    
}
