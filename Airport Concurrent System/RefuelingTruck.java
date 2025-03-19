import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RefuelingTruck {
    private Lock refuelingLock = new ReentrantLock();

    public void refuel(Plane plane) {
        refuelingLock.lock();
        try {
            System.out.println("Refueling Truck: Refueling Plane " + plane.getId());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            refuelingLock.unlock();
        }
    }
}
