package ioi.quizz.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "log_entries")
public class LogEntryEntity extends BaseEntity {
    
    private String severity;
    
    private String message;
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
