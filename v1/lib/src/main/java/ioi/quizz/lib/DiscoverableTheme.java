package ioi.quizz.lib;

public class DiscoverableTheme extends BaseType {
    
    private String title;
    
    private boolean discovered;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public boolean isDiscovered() {
        return discovered;
    }
    
    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }
}
