package ioi.quizz.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    
    public static Date getSecondsAfterNow(int secondsAfter) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, secondsAfter);
        return calendar.getTime();
    }
    
}
