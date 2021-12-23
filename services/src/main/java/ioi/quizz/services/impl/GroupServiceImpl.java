package ioi.quizz.services.impl;

import ioi.quizz.config.QuizzConfig;
import ioi.quizz.config.QuizzConstants;
import ioi.quizz.lib.GroupWithMembers;
import ioi.quizz.lib.Student;
import ioi.quizz.mappers.GroupMapper;
import ioi.quizz.mappers.StudentMapper;
import ioi.quizz.persistence.GroupEntity;
import ioi.quizz.persistence.GroupMembershipEntity;
import ioi.quizz.persistence.StudentEntity;
import ioi.quizz.persistence.identifiers.GroupStudentId;
import ioi.quizz.services.GroupService;
import ioi.quizz.services.StudentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class GroupServiceImpl implements GroupService {
    
    @Inject
    private EntityManager em;
    
    @Inject
    private StudentService studentService;
    
    @Inject
    private QuizzConfig quizzConfig;
    
    @Override
    public void assignUserToGroup(String deviceId, String roomId) {
        StudentEntity student = studentService.getStudentEntityByDevice(deviceId)
            .orElseThrow();
        
        GroupEntity group = getEmptyGroupOrCreateNew();
    
        GroupStudentId groupStudentId = new GroupStudentId();
        groupStudentId.setStudent(student);
        groupStudentId.setGroup(group);
    
        GroupMembershipEntity groupMembership = new GroupMembershipEntity();
        groupMembership.setId(groupStudentId);
        
        try {
            em.getTransaction().begin();
            em.persist(groupMembership);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public GroupWithMembers getUserGroup(String deviceId) {
        GroupEntity group = getUserGroupByDeviceId(deviceId).orElseThrow();
        
        TypedQuery<StudentEntity> query = em.createNamedQuery(GroupEntity.GET_GROUP_MEMBERS, StudentEntity.class);
        query.setParameter("groupId", group.getId());
        
        try {
            List<Student> students = query.getResultStream()
                .map(StudentMapper::fromEntity)
                .collect(Collectors.toList());
            
            GroupWithMembers response = new GroupWithMembers();
            response.setGroup(GroupMapper.fromEntity(group));
            response.setMembers(students);
            return response;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    private Optional<GroupEntity> getUserGroupByDeviceId(String deviceId) {
        TypedQuery<GroupEntity> query = em.createNamedQuery(GroupEntity.GET_USER_GROUP, GroupEntity.class);
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
    public Optional<GroupEntity> getGroupEntity(String id) {
        return Optional.ofNullable(em.find(GroupEntity.class, id));
    }
    
    private GroupEntity getEmptyGroupOrCreateNew() {
        return null;
    }
}
