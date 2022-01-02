package ioi.quizz.persistence;

import javax.persistence.*;

@Entity
@Table(name = "quiz_questions")
@NamedQueries({
    @NamedQuery(name = QuizQuestionEntity.GET_NEXT_QUESTION, query = "SELECT q FROM QuizQuestionEntity q WHERE q.quiz.id = :quizId AND q.position > q.quiz.questionIndex ORDER BY q.position"),
    @NamedQuery(name = QuizQuestionEntity.GET_CURRENT_QUESTION, query = "SELECT q FROM QuizQuestionEntity q WHERE q.quiz.id = :quizId AND q.position = q.quiz.questionIndex ORDER BY q.position"),
    @NamedQuery(name = QuizQuestionEntity.COUNT_REMAINING_QUESTIONS, query = "SELECT COUNT(q) FROM QuizQuestionEntity q WHERE q.quiz.id = :quizId AND q.position > q.quiz.questionIndex"),
    @NamedQuery(name = QuizQuestionEntity.GET_QUIZ_QUESTIONS, query = "SELECT q FROM QuizQuestionEntity q WHERE q.quiz.id = :quizId")
})
public class QuizQuestionEntity extends BaseEntity {
    
    public static final String GET_NEXT_QUESTION = "QuizQuestionEntity.getNextQuestion";
    public static final String GET_CURRENT_QUESTION = "QuizQuestionEntity.getCurrentQuestion";
    public static final String COUNT_REMAINING_QUESTIONS = "QuizQuestionEntity.countRemainingQuestions";
    public static final String GET_QUIZ_QUESTIONS = "QuizQuestionEntity.getQuizQuestions";
    
    @Column(name = "position")
    private int position;
    
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizInstanceEntity quiz;
    
    @ManyToOne
    @JoinColumn(name = "question_id")
    private ThemeQuestionEntity question;
    
    public QuizInstanceEntity getQuiz() {
        return quiz;
    }
    
    public void setQuiz(QuizInstanceEntity quiz) {
        this.quiz = quiz;
    }
    
    public ThemeQuestionEntity getQuestion() {
        return question;
    }
    
    public void setQuestion(ThemeQuestionEntity question) {
        this.question = question;
    }
    
    public int getPosition() {
        return position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }
}
