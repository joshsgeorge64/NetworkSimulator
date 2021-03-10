
import java.util.LinkedList;

/**
 *  @author joshua.s.george@stonybrook.edu
 *  ID: 112839378
 *  Rec: 02
 *
 *  <p>
 *      This class represents the Routers that transfer Packets between them
 *  </p>
 */
public class Router extends LinkedList {
    private int routerID;

    /**
     * Creates router with an id
     * @param id id to set for the router
     */
    public Router(int id) {
        super();
        this.routerID = id;
    }

    /**
     * Adds packet to end of router
     * @param p packet to be added
     */
    public void enqueue(Packet p) {
        super.add(p);
    }

    /**
     * Removes packet from the top of the router
     * @return packet at top of router
     */
    public Packet dequeue() {
        return (Packet) super.remove();
    }

    /**
     *
     * @return Packet at top of the router
     */
    public Packet peek() {
        return (Packet) super.peek();
    }

    /**
     * Determines the number of packets in the list
     * @return Number of packets of the list
     */
    @Override
    public int size() {
        return super.size();
    }

    /**
     * Determines if the router has no packets
     * @return true if the router has no packets, false otherwise
     */
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Returns the packet at a certain location in the router
     * @param i Location of packet in the list
     * @return the packet at location i in the router
     */
    public Packet get(int i) {
        return (Packet) super.get(i);
    }

    /**
     * Creates a string representation of a router
     * @return the string representation of the router
     */
    public String toString() {
        String s = ("R" + routerID + ": {");
        for (int i = 0; i < this.size(); i++) {
            s += this.get(i);
            s += " ";
        }
        s += "}";
        return s;
    }

    /**
     * Determines which router to send the packet to
     * @param routers List of intermediate routers
     * @return location of router with least packets
     */
    public static int sendPacketTo(LinkedList<Router> routers) {
        int leastPackets = routers.get(0).size();
        int routerNum = 0;
        for (int i = 0; i < routers.size(); i++) {
            if (routers.get(i).size() < leastPackets) {
                leastPackets = routers.get(i).size();
                routerNum = i;

            }
        }
        return routerNum;
    }


}
