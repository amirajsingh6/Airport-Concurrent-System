public class Plane implements Runnable {
    private int id;
    private Airport airport;
    private boolean emergencyLanding;

    public Plane(int id, Airport airport) {
        this.id = id;
        this.airport = airport;
        this.emergencyLanding = (id == 3);
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + id + " Plane: PL" + id + " is Added to the Waiting Queue");
            while (!airport.requestRunway(this)) {
                System.out.println("Thread " + id + " Plane: PL" + id + " is Waiting for permission to land");
                Thread.sleep(2000); // Wait before retrying
            }
            
            System.out.println("Thread " + id + ": Plane with ID: PL" + id + " Landing........");
            Thread.sleep(2000);
            System.out.println("Thread " + id + ": Plane with ID: PL" + id + " Landed Successfully");

            // Now check for an available gate
            while (!airport.requestGate(this)) {
                System.out.println("Thread " + id + " Plane: PL" + id + " is Waiting for an available gate");
                Thread.sleep(2000);
            }
            System.out.println("Thread " + id + ": Plane- PL" + id + " docking at Gate");            
            Thread.sleep(2000);
            airport.cleanPlane(this);
            airport.refuel(this);
            System.out.println("Thread " + id + ": Plane with ID: PL" + id + " is taking off.");
            Thread.sleep(2000);
            airport.releaseGate();
            airport.recordPlaneServed();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
