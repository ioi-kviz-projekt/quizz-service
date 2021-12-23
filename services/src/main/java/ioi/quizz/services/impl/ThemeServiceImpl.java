package ioi.quizz.services.impl;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import ioi.quizz.lib.DiscoverableTheme;
import ioi.quizz.mappers.BaseMapper;
import ioi.quizz.persistence.DiscoveredThemeEntity;
import ioi.quizz.persistence.StudentEntity;
import ioi.quizz.persistence.ThemeEntity;
import ioi.quizz.services.StudentService;
import ioi.quizz.services.ThemeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class ThemeServiceImpl implements ThemeService {
    
    @Inject
    private EntityManager em;
    
    @Inject
    private StudentService studentService;
    
    @Override
    public List<DiscoverableTheme> getThemes(String deviceId) {
        List<ThemeEntity> themes = JPAUtils.queryEntities(em, ThemeEntity.class, new QueryParameters());
        Map<String, DiscoveredThemeEntity> userThemes = getDiscoveredThemes(deviceId);
        
        return themes.stream().map(t -> {
            DiscoverableTheme theme = BaseMapper.fromEntity(t, DiscoverableTheme.class);
            theme.setTitle(t.getTitle());
            theme.setDiscovered(userThemes.containsKey(t.getId()));
            return theme;
        }).collect(Collectors.toList());
    }
    
    @Override
    public void discoverTheme(String themeId, String deviceId) {
        ThemeEntity theme = getThemeEntity(themeId).orElseThrow();
        StudentEntity student = studentService.getStudentEntityByDevice(deviceId).orElseThrow();
        
        DiscoveredThemeEntity entity = new DiscoveredThemeEntity();
        entity.setTheme(theme);
        entity.setStudent(student);
        
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Optional<ThemeEntity> getThemeEntity(String themeId) {
        return Optional.ofNullable(em.find(ThemeEntity.class, themeId));
    }
    
    private Map<String, DiscoveredThemeEntity> getDiscoveredThemes(String deviceId) {
        TypedQuery<DiscoveredThemeEntity> query = em.createNamedQuery(DiscoveredThemeEntity.GET_BY_USER, DiscoveredThemeEntity.class);
        query.setParameter("deviceId", deviceId);
        
        return query.getResultStream()
            .collect(Collectors.toMap(t -> t.getTheme().getId(), t -> t));
    }
}
