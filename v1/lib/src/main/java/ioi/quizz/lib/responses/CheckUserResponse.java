package ioi.quizz.lib.responses;

import ioi.quizz.lib.Student;
import ioi.quizz.lib.enums.UserStatus;

public class CheckUserResponse {
    
    public UserStatus status;
    
    public Student user;
    
    public UserStatus getStatus() {
        return status;
    }
    
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    public Student getUser() {
        return user;
    }
    
    public void setUser(Student user) {
        this.user = user;
    }
}
