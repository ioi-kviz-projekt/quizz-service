package ioi.quizz.persistence;

import ioi.quizz.persistence.identifiers.GroupStudentId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "group_memberships")
public class GroupMembershipEntity {
    
    @EmbeddedId
    private GroupStudentId id;
    
    public GroupStudentId getId() {
        return id;
    }
    
    public void setId(GroupStudentId id) {
        this.id = id;
    }
    
    public StudentEntity getStudent() {
        return id.getStudent();
    }
    
    public GroupEntity getGroup() {
        return id.getGroup();
    }
}
