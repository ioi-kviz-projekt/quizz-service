package ioi.quizz.lib.responses;

public class QuizSummary {
    
    public static QuizSummary empty() {
        return empty(0);
    }
    
    public static QuizSummary empty(int total) {
        QuizSummary summary = new QuizSummary();
        summary.setCorrect(0);
        summary.setTotal(total);
        return summary;
    }
    
    private int total;
    
    private int correct;
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    public int getCorrect() {
        return correct;
    }
    
    public void setCorrect(int correct) {
        this.correct = correct;
    }
    
    public void addCorrect(int amount) {
        this.correct = this.correct + amount;
    }
}
