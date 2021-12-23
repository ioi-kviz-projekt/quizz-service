package ioi.quizz.services.impl;

import com.mjamsek.rest.services.Validator;
import ioi.quizz.lib.Student;
import ioi.quizz.lib.enums.UserStatus;
import ioi.quizz.lib.responses.CheckUserResponse;
import ioi.quizz.mappers.StudentMapper;
import ioi.quizz.persistence.StudentEntity;
import ioi.quizz.services.StudentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@RequestScoped
public class StudentServiceImpl implements StudentService {
    
    @Inject
    private EntityManager em;
    
    @Inject
    private Validator validator;
    
    @Override
    public Student getStudentByDevice(String deviceId) {
        return getStudentEntityByDevice(deviceId)
            .map(StudentMapper::fromEntity)
            .orElseThrow();
    }
    
    @Override
    public Optional<StudentEntity> getStudentEntityByDevice(String deviceId) {
        TypedQuery<StudentEntity> query = em.createNamedQuery(StudentEntity.GET_BY_DEVICE_ID, StudentEntity.class);
        query.setParameter("deviceId", deviceId);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Student getStudent(String id) {
        return getStudentEntity(id)
            .map(StudentMapper::fromEntity)
            .orElseThrow();
    }
    
    @Override
    public Optional<StudentEntity> getStudentEntity(String id) {
        return Optional.ofNullable(em.find(StudentEntity.class, id));
    }
    
    @Override
    public StudentEntity register(String deviceId, String fullName) {
        return getStudentEntityByDevice(deviceId)
            .orElseGet(() -> {
                StudentEntity entity = new StudentEntity();
                entity.setFullName(fullName);
                entity.setDeviceId(deviceId);
                
                try {
                    em.getTransaction().begin();
                    em.persist(entity);
                    em.getTransaction().commit();
                    return entity;
                } catch (PersistenceException e) {
                    em.getTransaction().rollback();
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
    }
    
    @Override
    public CheckUserResponse checkUser(String deviceId) {
        validator.assertNotBlank(deviceId);
        return getStudentEntityByDevice(deviceId)
            .map(student -> {
                CheckUserResponse response = new CheckUserResponse();
                response.setUser(StudentMapper.fromEntity(student));
                response.setStatus(UserStatus.EXISTS);
                return response;
            }).orElseGet(() -> {
                CheckUserResponse response = new CheckUserResponse();
                response.setStatus(UserStatus.NOT_FOUND);
                return response;
            });
    }
}
