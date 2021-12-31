package ioi.quizz.mappers;

import ioi.quizz.lib.LogEntry;
import ioi.quizz.persistence.LogEntryEntity;

public class LogMapper {
    
    public static LogEntry fromEntity(LogEntryEntity entity) {
        LogEntry entry = BaseMapper.fromEntity(entity, LogEntry.class);
        entry.setMessage(entity.getMessage());
        entry.setSeverity(entity.getSeverity());
        return entry;
    }
    
}
