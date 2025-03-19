import java.util.concurrent.Semaphore;

public class Runway {
    private Semaphore runway = new Semaphore(1);

    public boolean acquireRunway() throws InterruptedException {
        return runway.tryAcquire(5, TimeUnit.SECONDS);
    }

    public void releaseRunway() {
        runway.release();
    }
}
