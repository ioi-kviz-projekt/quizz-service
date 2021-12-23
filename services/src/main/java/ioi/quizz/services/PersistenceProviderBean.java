package ioi.quizz.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@RequestScoped
public class PersistenceProviderBean {
    
    @Inject
    private EntityManager entityManager;
    
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
    
}
