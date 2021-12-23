package ioi.quizz.services;

import ioi.quizz.persistence.ThemeEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@RequestScoped
public class TestService {
    
    @Inject
    private EntityManager em;
    
    public String getTest() {
        TypedQuery<ThemeEntity> query = em.createQuery("SELECT t FROM ThemeEntity t", ThemeEntity.class);
        var temp = query.getResultList();
        for (var t : temp) {
            System.err.println(t.getTitle());
        }
        return "test";
    }
    
}
