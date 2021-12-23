package ioi.quizz.persistence.identifiers;

import ioi.quizz.persistence.RoomEntity;
import ioi.quizz.persistence.StudentEntity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoomStudentId implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
    
    public RoomEntity getRoom() {
        return room;
    }
    
    public void setRoom(RoomEntity room) {
        this.room = room;
    }
    
    public StudentEntity getStudent() {
        return student;
    }
    
    public void setStudent(StudentEntity student) {
        this.student = student;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomStudentId that = (RoomStudentId) o;
        return room.getId().equals(that.room.getId()) && student.getId().equals(that.student.getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(room.getId(), student.getId());
    }
}
