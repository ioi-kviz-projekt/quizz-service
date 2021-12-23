package ioi.quizz.persistence;

import ioi.quizz.persistence.identifiers.RoomStudentId;

import javax.persistence.*;

@Entity
@Table(name = "room_memberships")
@NamedQueries({
      @NamedQuery(name = RoomMembershipEntity.CHECK_MEMBERSHIP, query = "SELECT rm FROM RoomMembershipEntity rm WHERE rm.id.room.roomNumber = :roomNumber AND rm.id.student.id = :studentId")
})
public class RoomMembershipEntity {
    
    public static final String CHECK_MEMBERSHIP = "RoomMembershipEntity.checkMembership";

    @EmbeddedId
    private RoomStudentId id;
    
    public RoomStudentId getId() {
        return id;
    }
    
    public void setId(RoomStudentId id) {
        this.id = id;
    }
    
    public RoomEntity getRoom() {
        return id.getRoom();
    }
    
    public StudentEntity getStudent() {
        return id.getStudent();
    }
}
