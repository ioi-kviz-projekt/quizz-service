package ioi.quizz.lib;

import ioi.quizz.lib.enums.QuizState;

import java.util.Date;

public class QuizInstance extends BaseType {
    
    private boolean active;
    
    private QuizState state;
    
    private Date stateEndsAt;
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
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
