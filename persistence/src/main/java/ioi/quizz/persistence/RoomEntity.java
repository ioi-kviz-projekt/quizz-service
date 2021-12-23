package ioi.quizz.persistence;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
@NamedQueries({
    @NamedQuery(name = RoomEntity.GET_BY_NUMBER, query = "SELECT r FROM RoomEntity r WHERE r.roomNumber = :roomNumber"),
    @NamedQuery(name = RoomEntity.GET_BY_ADMIN, query = "SELECT r FROM RoomEntity r WHERE r.adminId = :adminId")
})
public class RoomEntity extends BaseEntity {
    
    public static final String GET_BY_NUMBER = "RoomEntity.getByNumber";
    public static final String GET_BY_ADMIN = "RoomEntity.getByAdmin";
    
    @Column(name = "room_number")
    private String roomNumber;
    
    @Column(name = "admin_id")
    private String adminId;
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getAdminId() {
        return adminId;
    }
    
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
