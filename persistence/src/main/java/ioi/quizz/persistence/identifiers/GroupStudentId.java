package ioi.quizz.persistence.identifiers;

import ioi.quizz.persistence.GroupEntity;
import ioi.quizz.persistence.StudentEntity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupStudentId implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
    
    public GroupEntity getGroup() {
        return group;
    }
    
    public void setGroup(GroupEntity room) {
        this.group = room;
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
        GroupStudentId that = (GroupStudentId) o;
        return group.getId().equals(that.group.getId()) && student.getId().equals(that.student.getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(group.getId(), student.getId());
    }
}
