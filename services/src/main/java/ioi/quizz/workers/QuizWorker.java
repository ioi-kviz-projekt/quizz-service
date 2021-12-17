package ioi.quizz.workers;

import ioi.quizz.persistence.QuizInstanceEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class QuizWorker {
    
    @Inject
    private EntityManagerFactory emFactory;
    
    public List<QuizInstanceEntity> getActiveQuizzes() {
        EntityManager em = emFactory.createEntityManager();
        TypedQuery<QuizInstanceEntity> query = em.createNamedQuery(QuizInstanceEntity.GET_ACTIVES, QuizInstanceEntity.class);
        List<QuizInstanceEntity> result = query.getResultList();
        return result;
    }
    
}
