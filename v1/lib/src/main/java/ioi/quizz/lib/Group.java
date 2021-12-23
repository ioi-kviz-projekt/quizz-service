package ioi.quizz.lib;

public class Group extends BaseType {
    
    private String roomId;
    
    private Theme theme;
    
    private String themeId;
    
    public String getRoomId() {
        return roomId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public Theme getTheme() {
        return theme;
    }
    
    public void setTheme(Theme theme) {
        this.theme = theme;
    }
    
    public String getThemeId() {
        return themeId;
    }
    
    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
}
