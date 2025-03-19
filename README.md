Run the main file. 
This project simulates an Airport Traffic Control (ATC) System, managing multiple planes attempting to land, dock, refuel, and take off concurrently. The system uses multithreading and synchronization techniques to handle plane operations simultaneously while ensuring proper coordination between runways, gates, and refueling trucks.

Key Features:
Concurrent Operations:

Multiple planes can be landing, unloading passengers, being cleaned, and preparing for departure at the same time.
Only one plane can be on the runway at a time.
Refueling is limited to one plane at a time.
Priority-Based Landing:

Emergency planes (20% probability) get priority landing but must wait for an available gate if all are occupied.
Normal planes land in a first-come, first-served manner.
Resource Management:

Runway: Only one plane can use the runway at any given time.
Gates: The airport has three gates, and planes must wait if all gates are full.
Refueling Trucks: Only one refueling truck is available, so refueling is handled sequentially.
Multithreading for Real-Time Simulation:

Each plane runs as a separate thread, performing tasks simultaneously with other planes at different gates.
Thread synchronization ensures that no two planes occupy the same runway or refueling truck at the same time.
Simulation Process:
Plane Arrival & Landing Request:

A new plane requests to land every 0-2 seconds (randomized delay).
If gates are available, the plane lands; otherwise, it waits in the air until a gate is free.
Emergency planes get priority in the queue.
Ground Operations (At the Gate):

Once docked at a gate, three tasks happen simultaneously:
Passengers are unloaded and boarded.
Aircraft is cleaned.
Refueling (one plane at a time).
Takeoff & Departure:

Once all operations are completed, the plane leaves the gate, takes off, and the runway is freed for the next plane.
Simulation Statistics:

After all planes have completed their journeys, the system prints final statistics, including:
Total planes served
Total passengers transported
Maximum, minimum, and average wait times
Technologies Used:
Java (Core Programming Language)
Multithreading & Concurrency (Thread, ExecutorService, Semaphore)
Synchronization Mechanisms (CountDownLatch, PriorityQueue)
Expected Outcome:
The project provides a realistic simulation of airport traffic control, ensuring:
✅ Planes land in an organized and concurrent manner.
✅ Emergency landings are prioritized correctly.
✅ Efficient resource management (runway, gates, refueling).
✅ Proper synchronization prevents deadlocks and delays.

🚀 A fully functional, real-time airport simulation with multiple planes running concurrently!
