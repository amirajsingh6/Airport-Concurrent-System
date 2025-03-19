import java.util.concurrent.Semaphore;

public class Gate {
    private Semaphore gates = new Semaphore(3);

    public boolean acquireGate() throws InterruptedException {
        return gates.tryAcquire(10, TimeUnit.SECONDS);
    }

    public void releaseGate() {
        gates.release();
    }
}
