package ioi.quizz.persistence;

import javax.persistence.*;

@Entity
@Table(name = "theme_questions")
@NamedNativeQueries({
    @NamedNativeQuery(name = ThemeQuestionEntity.GET_RAND_QS, query = "SELECT DISTINCT ON (tq.theme_id) tq.* FROM theme_questions tq ORDER BY tq.theme_id, random()", resultClass = ThemeQuestionEntity.class)
})
public class ThemeQuestionEntity extends BaseEntity {
    
    public static final String GET_RAND_QS = "ThemeQuestionEntity.getRandQs";
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private ThemeEntity theme;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;
    
    @Column(name = "student_id")
    private String studentId;
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public ThemeEntity getTheme() {
        return theme;
    }
    
    public void setTheme(ThemeEntity theme) {
        this.theme = theme;
    }
    
    public RoomEntity getRoom() {
        return room;
    }
    
    public void setRoom(RoomEntity room) {
        this.room = room;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
