package ioi.quizz.persistence;

import javax.persistence.*;

@Entity
@Table(name = "discovered_themes")
@NamedQueries({
    @NamedQuery(name = DiscoveredThemeEntity.GET_BY_USER, query = "SELECT dt FROM DiscoveredThemeEntity dt WHERE dt.student.deviceId = :deviceId")
})
public class DiscoveredThemeEntity extends BaseEntity {
    
    public static final String GET_BY_USER = "DiscoveredThemeEntity.getByUser";
    
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private ThemeEntity theme;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
    
    public ThemeEntity getTheme() {
        return theme;
    }
    
    public void setTheme(ThemeEntity theme) {
        this.theme = theme;
    }
    
    public StudentEntity getStudent() {
        return student;
    }
    
    public void setStudent(StudentEntity student) {
        this.student = student;
    }
}
