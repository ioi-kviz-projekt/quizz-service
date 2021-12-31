package ioi.quizz.services.impl;

import com.mjamsek.rest.services.Validator;
import ioi.quizz.lib.Room;
import ioi.quizz.lib.requests.RoomRegistrationRequest;
import ioi.quizz.mappers.RoomMapper;
import ioi.quizz.persistence.RoomEntity;
import ioi.quizz.persistence.RoomMembershipEntity;
import ioi.quizz.persistence.StudentEntity;
import ioi.quizz.persistence.identifiers.RoomStudentId;
import ioi.quizz.services.RoomService;
import ioi.quizz.services.StudentService;
import ioi.quizz.utils.StringUtils;

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
public class RoomServiceImpl implements RoomService {
    
    @Inject
    private EntityManager em;
    
    @Inject
    private Validator validator;
    
    @Inject
    private StudentService studentService;
    
    @Override
    public Room createRoom(String teacherId) {
        RoomEntity entity = new RoomEntity();
        
        String roomNumber = getAvailableRoomNumber();
        entity.setRoomNumber(roomNumber);
        entity.setAdminId(teacherId);
        
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return RoomMapper.fromEntity(entity);
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Room registerUserToRoom(RoomRegistrationRequest request) {
        validator.assertNotBlank(request.getDeviceId());
        
        Optional<StudentEntity> student = studentService.getStudentEntityByDevice(request.getDeviceId());
        if (student.isPresent()) {
            // If student is present, check of existing membership
            Optional<RoomMembershipEntity> existingMembership = getExistingMembership(request.getRoomNumber(), student.get().getId());
            if (existingMembership.isPresent()) {
                return RoomMapper.fromEntity(existingMembership.get().getRoom());
            }
            
            // Membership for this room doesn't exists yet, add membership
            RoomMembershipEntity membership = addNewRoomMember(student.get(), request.getRoomNumber());
            return RoomMapper.fromEntity(membership.getRoom());
        }
        
        // Student doesn't exist yet, add it, before adding it to room
        StudentEntity newStudent = studentService.register(request.getDeviceId(), request.getFullName());
        
        RoomMembershipEntity membership = addNewRoomMember(newStudent, request.getRoomNumber());
        return RoomMapper.fromEntity(membership.getRoom());
    }
    
    @Override
    public Optional<RoomEntity> getRoomByNumber(String roomNumber) {
        TypedQuery<RoomEntity> query = em.createNamedQuery(RoomEntity.GET_BY_NUMBER, RoomEntity.class);
        query.setParameter("roomNumber", roomNumber);
        
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
    public Optional<RoomEntity> getRoomEntity(String roomId) {
        return Optional.ofNullable(em.find(RoomEntity.class, roomId));
    }
    
    @Override
    public List<Room> getTeacherRooms(String teacherId) {
        TypedQuery<RoomEntity> query = em.createNamedQuery(RoomEntity.GET_BY_ADMIN, RoomEntity.class);
        query.setParameter("adminId", teacherId);
        
        return query.getResultStream()
            .map(RoomMapper::fromEntity)
            .collect(Collectors.toList());
    }
    
    private RoomMembershipEntity addNewRoomMember(StudentEntity student, String roomNumber) {
        RoomEntity room = getRoomByNumber(roomNumber).orElseThrow();
        
        RoomStudentId membershipId = new RoomStudentId();
        membershipId.setStudent(student);
        membershipId.setRoom(room);
        
        RoomMembershipEntity membership = new RoomMembershipEntity();
        membership.setId(membershipId);
        
        try {
            em.getTransaction().begin();
            em.persist(membership);
            em.flush();
            em.getTransaction().commit();
            return membership;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    private Optional<RoomMembershipEntity> getExistingMembership(String roomNumber, String studentId) {
        TypedQuery<RoomMembershipEntity> query = em.createNamedQuery(RoomMembershipEntity.CHECK_MEMBERSHIP, RoomMembershipEntity.class);
        query.setParameter("roomNumber", roomNumber);
        query.setParameter("studentId", studentId);
        
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    private String getAvailableRoomNumber() {
        Optional<RoomEntity> existingRoom;
        String newNumber;
        do {
            newNumber = StringUtils.randomNumericString(6);
            existingRoom = getRoomByNumber(newNumber);
        } while (existingRoom.isPresent());
        return newNumber;
    }
}
