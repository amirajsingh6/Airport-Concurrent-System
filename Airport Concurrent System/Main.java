import java.util.concurrent.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("======== ATC Thread Active: Managing Air Traffic =========");
        Airport airport = new Airport();
        ExecutorService executor = Executors.newFixedThreadPool(6);
        Random rand = new Random();

        for (int i = 1; i <= 6; i++) {
            try {
                Thread.sleep(rand.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Plane plane = new Plane(i, airport);
            executor.execute(plane);
        }
        executor.shutdown();
        
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        airport.printStatistics();
    }
}
