package ioi.quizz.persistence;

import javax.persistence.*;

@Entity
@Table(name = "theme_questions")
@NamedNativeQueries({
    @NamedNativeQuery(name = ThemeQuestionEntity.GET_RAND_QS, query = "SELECT DISTINCT ON (tq.theme_id) tq.* FROM theme_questions tq ORDER BY tq.theme_id, random()", resultClass = ThemeQuestionEntity.class)
})
public class ThemeQuestionEntity extends BaseEntity {
    
    public static final String GET_RAND_QS = "ThemeQuestionEntity.getRandQs";
    
    @Column(name = "html_content", columnDefinition = "TEXT")
    private String htmlContent;
    
    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;
    
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private ThemeEntity theme;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;
    
    public String getHtmlContent() {
        return htmlContent;
    }
    
    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
    
    public String getTextContent() {
        return textContent;
    }
    
    public void setTextContent(String textContent) {
        this.textContent = textContent;
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
}
