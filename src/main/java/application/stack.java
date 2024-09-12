package application;

import java.util.ArrayList;
import java.util.List;


public class stack {
    static int calculate = 0;
    static long startTime = System.currentTimeMillis();
    static long previousTime = startTime;
    static long end_time = 0;

    /**
     * this method simulates stack memory alloc
     */
    public static void stack() {
            try {
                stackTest(0);


        } catch (StackOverflowError e) {
                System.out.println("StackOverFlowError");
                end_time = System.currentTimeMillis();
                System.out.println("Total time taken to throw stackOverflow: " + (end_time - startTime));
                System.out.println("Number of objects created: "+calculate);
            }
        }

    /**
     * recursive method to test the stack memory alloc
     * @param parameter
     */
    public static void stackTest(int parameter){
            calculate++;
            if(parameter < 10000000){
                if(calculate % 10000 == 0) {
                    end_time = System.currentTimeMillis();
                    previousTime = end_time;
                    stackTest(parameter+1);
                }
                else
                    stackTest(parameter+1);
            }
        }
}
