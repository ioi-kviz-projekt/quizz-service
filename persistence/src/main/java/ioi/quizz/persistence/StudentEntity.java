package ioi.quizz.persistence;

import javax.persistence.*;

@Entity
@Table(name = "students")
@NamedQueries({
    @NamedQuery(name = StudentEntity.GET_BY_DEVICE_ID, query = "SELECT s FROM StudentEntity s WHERE s.deviceId = :deviceId")
})
public class StudentEntity extends BaseEntity {
    
    public static final String GET_BY_DEVICE_ID = "StudentEntity.getByDeviceId";
    
    @Column(name = "device_id")
    private String deviceId;
    
    @Column(name = "full_name")
    private String fullName;
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
