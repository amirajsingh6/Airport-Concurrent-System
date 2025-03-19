import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.Collections;

public class Airport {
    private Semaphore runway = new Semaphore(1);
    private Semaphore gates = new Semaphore(3);
    private Lock refuelingLock = new ReentrantLock();
    private ExecutorService cleaningService = Executors.newFixedThreadPool(3);
    private AtomicInteger totalPlanes = new AtomicInteger(0);
    private AtomicInteger totalPassengers = new AtomicInteger(0);
    private ArrayList<Long> waitingTimes = new ArrayList<>();

    public boolean requestRunway(Plane plane) throws InterruptedException {
        System.out.println("ATC_Thread: Checking Runway Status for Plane " + plane.getId());
        long startTime = System.currentTimeMillis();
        boolean granted = runway.tryAcquire(5, TimeUnit.SECONDS);
        long waitTime = System.currentTimeMillis() - startTime;
        waitingTimes.add(waitTime);

        if (granted) {
            System.out.println("ATC_Thread: Runway is free. Plane " + plane.getId() + " has permission to land.");
        } else {
            System.out.println("ATC_Thread: Runway busy. Plane " + plane.getId() + " must wait.");
        }
        return granted;
    }

    public void releaseRunway() {
        runway.release();
    }

    public boolean requestGate(Plane plane) throws InterruptedException {
        System.out.println("ATC_Thread: Checking Gate Availability for Plane " + plane.getId());
        boolean granted = gates.tryAcquire(10, TimeUnit.SECONDS);

        if (granted) {
            System.out.println("ATC_Thread: Gate assigned for Plane " + plane.getId());
        } else {
            System.out.println("ATC_Thread: No gates available. Plane " + plane.getId() + " must wait.");
        }
        return granted;
    }

    public void releaseGate() {
        gates.release();
    }

    public void processPlane(Plane plane) {
        cleanPlane(plane);  // Cleaning happens first
        refuel(plane);       // Refueling happens after cleaning
    }

    public void refuel(Plane plane) {
        refuelingLock.lock();
        try {
            System.out.println("GTO-Refuelling Thread: Plane " + plane.getId() + " Refuelling....");
            Thread.sleep(3000);
            System.out.println("GTO-Refuelling Thread: Plane " + plane.getId() + " refuelled successfully.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            refuelingLock.unlock();
        }
    }

    public void cleanPlane(Plane plane) {
        cleaningService.execute(() -> {
            System.out.println("GTO-Cleaning Thread: Plane " + plane.getId() + " Cleaning Started...");
            try {
                Thread.sleep(2000);
                System.out.println("GTO-Cleaning Thread: Plane " + plane.getId() + " Cleaned Successfully.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public synchronized void recordPlaneServed() {
        totalPlanes.incrementAndGet();
        totalPassengers.addAndGet(50);
    }

    public void printStatistics() {
        System.out.println("\n============ STATISTICS =============");
        System.out.println("ATC: All planes have departed. ATC shutting down.");
        System.out.println("All gates are empty.");
        System.out.println("Planes Served: " + totalPlanes.get());
        System.out.println("Total Passengers Boarded: " + totalPassengers.get());

        if (!waitingTimes.isEmpty()) {
            long maxWait = Collections.max(waitingTimes);
            long minWait = Collections.min(waitingTimes);
            double avgWait = waitingTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
            System.out.println("Max Plane Waiting Time: " + maxWait + " ms");
            System.out.println("Min Plane Waiting Time: " + minWait + " ms");
            System.out.println("Average Plane Waiting Time: " + avgWait + " ms");
        }
    }
}
