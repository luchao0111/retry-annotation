package retry.client;

import retry.service.ConsolePrintService;
import retry.service.PrintService;

/**
 * Created by M1011579 on 9/11/2015.
 */
public class Test {

    public static void main(String[] args) throws Exception {

        long t1 = System.currentTimeMillis();

        PrintService printService = ConsolePrintService.getInstance(true);
        printService.print();

        long t2 = System.currentTimeMillis();
        System.err.println("Time taken to execute service call " + (t2 - t1) + " ms");

    }
}
