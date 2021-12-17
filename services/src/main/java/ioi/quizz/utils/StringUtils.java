package ioi.quizz.utils;

import java.security.SecureRandom;
import java.util.Random;

public class StringUtils {
    
    private StringUtils() {
    
    }
    
    public static String randomString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new SecureRandom();
        
        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
