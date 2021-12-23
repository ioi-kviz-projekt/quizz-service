package ioi.quizz.lib;

import java.util.List;

public class GroupWithMembers {
    
    private Group group;
    
    private List<Student> members;
    
    public Group getGroup() {
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    
    public List<Student> getMembers() {
        return members;
    }
    
    public void setMembers(List<Student> members) {
        this.members = members;
    }
}
