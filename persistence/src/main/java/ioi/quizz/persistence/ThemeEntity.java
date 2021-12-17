package ioi.quizz.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "themes")
public class ThemeEntity extends BaseEntity {
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "position")
    private int position;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getPosition() {
        return position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }
}
