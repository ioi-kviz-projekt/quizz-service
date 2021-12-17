package ioi.quizz.persistence;

import ioi.quizz.lib.enums.QuizState;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "quiz_instances")
@NamedQueries({
    @NamedQuery(name = QuizInstanceEntity.GET_BY_PASSKEY, query = "SELECT q FROM QuizInstanceEntity q WHERE q.passkey = :passkey"),
    @NamedQuery(name = QuizInstanceEntity.GET_ACTIVES, query = "SELECT q FROM QuizInstanceEntity q WHERE q.active = true")
})
public class QuizInstanceEntity extends BaseEntity {
    
    public static final String GET_BY_PASSKEY = "QuizInstanceEntity.getByPasskey";
    public static final String GET_ACTIVES = "QuizInstanceEntity.getActives";
 
    @Column(name = "active")
    private boolean active;
    
    @Column(name = "passkey")
    private String passkey;
    
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private QuizState state;
    
    @Column(name = "state_ends_at")
    private Date stateEndsAt;
    
    @Column(name = "question_index")
    private int questionIndex;
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getPasskey() {
        return passkey;
    }
    
    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }
    
    public int getQuestionIndex() {
        return questionIndex;
    }
    
    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }
    
    public QuizState getState() {
        return state;
    }
    
    public void setState(QuizState state) {
        this.state = state;
    }
    
    public Date getStateEndsAt() {
        return stateEndsAt;
    }
    
    public void setStateEndsAt(Date stateEndsAt) {
        this.stateEndsAt = stateEndsAt;
    }
}
