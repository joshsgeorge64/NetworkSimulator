import java.util.LinkedList;
import java.util.Scanner;

/**
 *  @author joshua.s.george@stonybrook.edu
 *  ID: 112839378
 *  Rec: 02
 *
 *  <p>
 *      This Simulator class contains the main method of the program and the outputs the results of the simulation
 *  </p>
 */

public class Simulator {
    private Router dispatcher;
    private LinkedList<Router> routers = new LinkedList<Router>();
    private int totalServiceTime = 0;
    private int totalPacketsArrived = 0;
    private int packetsDropped = 0;
    private double arriveProb;
    private int numIntRouters;
    private int maxBufferSize;
    private int minPacketSize;
    private int maxPacketSize;
    private int bandWidth;
    private int duration;

    public static int MAX_PACKETS = 3;

    /**
     * Creates a simulator object
     * @param numIntRouters Number of Intermediate routers
     * @param arriveProb Arrival probability of a packet
     * @param maxBufferSize Maximum # of packets an intermediate router can handle
     * @param minPacketSize Minimum size of a packet
     * @param maxPacketSize Maximum size of a packet
     * @param bandWidth Maximum # of packets that can be handles by the destination router
     * @param duration Length of simulation
     * @throws InvalidArgException Thrown if any of the given parameters are invalid
     */
    public Simulator(int numIntRouters, double arriveProb, int maxBufferSize, int minPacketSize, int maxPacketSize,
                     int bandWidth, int duration) throws InvalidArgException {
        this.numIntRouters = numIntRouters;
        if(numIntRouters <= 0)
            throw new InvalidArgException("Please enter a positive number of routers");

        this.arriveProb = arriveProb;
        if(arriveProb < 0 || arriveProb > 1)
            throw new InvalidArgException("Please enter an arrival probability between 0 and 1");

        this.maxBufferSize = maxBufferSize;
        if(maxBufferSize <= 0)
            throw new InvalidArgException("Please enter a positive buffer size");

        this.minPacketSize = minPacketSize;
        if(minPacketSize < 100)
            throw new InvalidArgException("Please enter a minimum packet size of 100 or more");

        this.maxPacketSize = maxPacketSize;
        if(maxPacketSize < minPacketSize || maxPacketSize < 0)
            throw new InvalidArgException("Please enter a positive maximum packet size that is larger than the minimum");

        this.bandWidth = bandWidth;
        if(bandWidth <= 0)
            throw new InvalidArgException("Please enter a positive bandwidth");

        this.duration = duration;
        if(duration <=  0)
            throw new InvalidArgException("Please enter a positive duration");
    }

    /**
     * This method models with simulation
     * @return Average time for packets in the simulation to reach destination
     */
    public double simulate() {
        dispatcher = new Router(0);
        for (int i = 1; i <= numIntRouters; i++) {
            routers.add(new Router(i));
        }
        for (int i = 1; i <= duration; i++) {
            System.out.println("Time: " + i);
            for (int j = 0; j < MAX_PACKETS; j++) {
                if (Math.random() < arriveProb) {
                    int packetSize = randInt(minPacketSize, maxPacketSize);
                    Packet packet = new Packet(packetSize, i);
                    System.out.println("Packet " + packet.getId() + " arrives at " +
                            "dispatcher with size " + packet.getPacketSize());
                    dispatcher.enqueue(packet);
                }
            }
            for (Object o : routers) {
                Router router = (Router) o;
                if (router.peek() != null) {
                    router.peek().setTimeToDest(router.peek().getTimeToDest() - 1);
                }

            }

            for (Object o : dispatcher) {
                Packet p = (Packet) o;
                int location = Router.sendPacketTo(routers);
                if (routers.get(location).size() == maxBufferSize) {
                    System.out.println("Network is congested. Packet " +
                            p.getId() + " is dropped");
                    packetsDropped++;
                } else {
                    routers.get(location).enqueue(p);
                    System.out.println("Packet " + p.getId() +
                            " sent to Router " + (location + 1));
                }
            }
            dispatcher.clear();
            int numRemoved = 0;
            for (Object o : routers) {
                Router router = (Router) o;
                Packet temp;
                int add;
                if (numRemoved == bandWidth) {
                    break;
                }
                if (router.peek() != null && router.peek().getTimeToDest() <= 0) {
                    temp = router.dequeue();
                    add = i - temp.getTimeArrive();
                    System.out.println("Packet " + temp.getId() + " has successfully reached its destination: +"
                            + (add));
                    totalServiceTime += add;
                    totalPacketsArrived++;
                    numRemoved++;
                }
            }
            for (Object o : routers) {
                Router router = (Router) o;
                System.out.println(router);
            }
            System.out.println();
        }
        return ((double) totalServiceTime) / totalPacketsArrived;
    }

    /**
     * Returns a random integer between two values (inclusive)
     * @param minVal minimum value
     * @param maxVal maximum value
     * @return the random integer
     */
    public static int randInt(int minVal, int maxVal) {
        return (int) (Math.random() * (maxVal - minVal + 1) + minVal);
    }

    /**
     * Main method that runs the simulation
     * @param args Command line args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String selection = "";
        System.out.println("Starting simulator...");
        while (!selection.equalsIgnoreCase("n")) {
            try {
                System.out.print("Enter the number of Intermediate routers: ");
                int intRouters = scan.nextInt();
                System.out.print("Enter the arrival probability of a packet: ");
                double prob = scan.nextDouble();
                System.out.print("Enter the maximum buffer size of a router: ");
                int maxBuff = scan.nextInt();
                System.out.print("Enter the minimum size of a packet: ");
                int minSize = scan.nextInt();
                System.out.print("Enter the maximum size of a packet: ");
                int maxSize = scan.nextInt();
                System.out.print("Enter the bandwidth size: ");
                int band = scan.nextInt();
                System.out.print("Enter the simulation duration: ");
                int length = scan.nextInt();
                Simulator simulator = new Simulator(intRouters, prob, maxBuff, minSize, maxSize, band, length);
                double result = simulator.simulate();
                System.out.println("Total service time: " + simulator.totalServiceTime);
                System.out.println("Total packets served: " + simulator.totalPacketsArrived);
                System.out.println(String.format("Average service time per packet: %.2f", result));
                System.out.println("Total packets dropped: " + simulator.packetsDropped);
                System.out.println("Do you want to try another simulation? (y/n): ");
                selection = scan.next();
            } catch (InvalidArgException e) {
                System.out.println(e);
            } catch (Exception e) {
                System.out.println("Please enter a valid input");
                selection = scan.nextLine();
            }
        }
        System.out.println("Program terminating successfully...");
    }
}
