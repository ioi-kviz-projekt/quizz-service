package ioi.quizz.mappers;

import ioi.quizz.lib.QuestionAnswer;
import ioi.quizz.lib.QuizInstance;
import ioi.quizz.lib.ThemeQuestion;
import ioi.quizz.persistence.QuestionAnswerEntity;
import ioi.quizz.persistence.QuizInstanceEntity;
import ioi.quizz.persistence.ThemeQuestionEntity;

public class QuizMapper {
    
    public static QuizInstance fromEntity(QuizInstanceEntity entity) {
        QuizInstance quiz = BaseMapper.fromEntity(entity, QuizInstance.class);
        quiz.setActive(entity.isActive());
        quiz.setState(entity.getState());
        quiz.setStateEndsAt(entity.getStateEndsAt());
        return quiz;
    }
    
    public static ThemeQuestion fromEntity(ThemeQuestionEntity entity) {
        ThemeQuestion question = BaseMapper.fromEntity(entity, ThemeQuestion.class);
        question.setContent(entity.getContent());
        
        if (entity.getTheme() != null) {
            question.setThemeId(entity.getTheme().getId());
            question.setTheme(ThemeMapper.fromEntity(entity.getTheme()));
        }
        
        return question;
    }
    
    public static QuestionAnswer fromEntity(QuestionAnswerEntity entity) {
        QuestionAnswer answer = BaseMapper.fromEntity(entity, QuestionAnswer.class);
        answer.setCorrect(entity.isCorrect());
        answer.setContent(entity.getContent());
        
        if (entity.getQuestion() != null) {
            answer.setQuestionId(entity.getQuestion().getId());
        }
        return answer;
    }
    
}
