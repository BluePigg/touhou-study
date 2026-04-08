package org.bluepig.backend;

public class BpUtil {
    public static int keywordScoring(String string, String... keywords) {
        int score = 0;
        for (String k : keywords) {
            if (string.contains(k)) {
                score++;
            }
        }
        return score;
    }
}
