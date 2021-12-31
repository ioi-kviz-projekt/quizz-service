package ioi.quizz.services.impl;

import com.kumuluz.ee.rest.beans.QueryOrder;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.enums.OrderDirection;
import com.kumuluz.ee.rest.utils.JPAUtils;
import ioi.quizz.lib.LogEntry;
import ioi.quizz.mappers.LogMapper;
import ioi.quizz.persistence.LogEntryEntity;
import ioi.quizz.services.LogService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class LogServiceImpl implements LogService {
    
    @Inject
    private EntityManager em;
    
    @Override
    public List<LogEntry> getLastNEntries(int n) {
        QueryParameters queryParams = new QueryParameters();
        
        queryParams.setOrder(List.of(new QueryOrder("createdAt", OrderDirection.DESC)));
        queryParams.setLimit(n);
        
        return JPAUtils.getEntityStream(em, LogEntryEntity.class, queryParams)
            .map(LogMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public void logEntry(String level, String message) {
        try {
            em.getTransaction().begin();
            LogEntryEntity entity = new LogEntryEntity();
            entity.setSeverity(level);
            entity.setMessage(message);
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
