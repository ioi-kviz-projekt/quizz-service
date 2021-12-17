package ioi.quizz.lib;

public class QuizInstance extends BaseType {
    
    private boolean active;
    
    private String passkey;
    
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
    
}
