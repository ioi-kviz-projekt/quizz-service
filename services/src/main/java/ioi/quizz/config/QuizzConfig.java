package ioi.quizz.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("quizz")
@ApplicationScoped
public class QuizzConfig {
    
    @ConfigValue("durations.loading")
    private int loadingDuration;
    
    @ConfigValue("durations.question")
    private int questionDuration;
    
    @ConfigValue("groups.members.limit")
    private int groupMembersLimit;
    
    public int getLoadingDuration() {
        return loadingDuration;
    }
    
    public void setLoadingDuration(int loadingDuration) {
        this.loadingDuration = loadingDuration;
    }
    
    public int getQuestionDuration() {
        return questionDuration;
    }
    
    public void setQuestionDuration(int questionDuration) {
        this.questionDuration = questionDuration;
    }
    
    public int getGroupMembersLimit() {
        return groupMembersLimit;
    }
    
    public void setGroupMembersLimit(int groupMembersLimit) {
        this.groupMembersLimit = groupMembersLimit;
    }
}
