public class ATC {
    private Airport airport;

    public ATC(Airport airport) {
        this.airport = airport;
    }

    public synchronized boolean requestLanding(Plane plane) throws InterruptedException {
        System.out.println("ATC: Plane " + plane.getId() + " requesting to land.");
        return airport.requestRunway(plane);
    }

    public synchronized void releaseRunway() {
        airport.releaseRunway();
    }
}
