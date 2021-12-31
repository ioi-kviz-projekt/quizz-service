package ioi.quizz.services;

import ioi.quizz.lib.LogEntry;

import java.util.List;

public interface LogService {
    
    List<LogEntry> getLastNEntries(int n);
    
    void logEntry(String level, String message);
    
}
