package skat;

import java.util.Arrays;

public class Tests {

    static ScoreboardManager sm;


    public static void main(String[] args) {
        sm = new ScoreboardManager();
        for(int i = 0; i < 5; i++){
            String[] line = {"12:09", "Tom"+i, Math.random()*30+""};
            System.out.println(Arrays.toString(line));
            sm.addScore(line);
        }
        sm.sortScoreboard();

    }
}
