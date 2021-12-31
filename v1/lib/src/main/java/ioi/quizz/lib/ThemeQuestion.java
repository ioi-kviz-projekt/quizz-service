package ioi.quizz.lib;

public class ThemeQuestion extends BaseType {
    
    private String content;
    
    private Theme theme;
    
    private String themeId;
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
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
