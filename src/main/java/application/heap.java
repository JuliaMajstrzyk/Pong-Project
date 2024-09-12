package application;

import java.util.ArrayList;
import java.util.List;

public class heap {
    static List<Integer> Ball = new ArrayList<>();
    static long begin_time = System.currentTimeMillis();
    static long prev_time = begin_time;
    static int calc = 0;
    static long final_time = 0;

    /**
     * this method simulate heap memory alloc
     */
    public static void heap() {

        try {
            while (true) {
                Integer ball = new Integer(1);
                Ball.add(ball);
                calc++;
                if(calc % 1000000 == 0) {
                    final_time = System.currentTimeMillis();
                    System.out.println("Time taken " + (final_time - prev_time) + " milliseconds");
                    prev_time = final_time;
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Out of memory");
            final_time = System.currentTimeMillis();
            System.out.println("Total time taken before out of memory: " + (final_time - begin_time));
            System.out.println("Num objects: "+calc);
        }
    }
}
