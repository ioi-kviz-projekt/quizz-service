package ioi.quizz.persistence;

import javax.persistence.*;

@Entity
@Table(name = "groups")
@NamedQueries({
    @NamedQuery(name = GroupEntity.GET_USER_GROUP,
        query = "SELECT gm.id.group FROM GroupMembershipEntity gm WHERE gm.id.student.deviceId = :deviceId"),
    @NamedQuery(name = GroupEntity.GET_GROUP_MEMBERS, query = "SELECT gm.id.student FROM GroupMembershipEntity gm WHERE gm.id.group.id = :groupId"),
    @NamedQuery(name = GroupEntity.GET_EMPTY_GROUP, query = "SELECT gm FROM GroupMembershipEntity gm ")
})
public class GroupEntity extends BaseEntity {
    
    public static final String GET_USER_GROUP = "GroupEntity.getUserGroup";
    public static final String GET_GROUP_MEMBERS = "GroupEntity.getGroupMembers";
    public static final String GET_EMPTY_GROUP = "GroupEntity.getEmptyGroup";
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;
    
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private ThemeEntity theme;
    
    public RoomEntity getRoom() {
        return room;
    }
    
    public void setRoom(RoomEntity room) {
        this.room = room;
    }
    
    public ThemeEntity getTheme() {
        return theme;
    }
    
    public void setTheme(ThemeEntity theme) {
        this.theme = theme;
    }
}
