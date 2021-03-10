/**
 * @author joshua.s.george@stonybrook.edu
 * ID: 112839378
 * Rec: 02
 *
 * <p>
 *     This class represents the Packets that are transferred between the Router objects
 * </p>
 */
public class Packet {
    private int id;
    private int packetSize;
    private int timeArrive;
    private int timeToDest;

    private static int packetCount = 0;

    /**
     * Creates a Packet with a given size
     * @param packetSize Size of the packet
     * @param timeArrive Time in the simulation the packet arrives
     */
    public Packet(int packetSize, int timeArrive){
        this.packetSize = packetSize;
        this.timeArrive = timeArrive;
        this.timeToDest = packetSize / 100;
        packetCount++;
        this.id = packetCount;
    }

    /**
     *
     * @return id of the packet
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id id to set for the packet
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return size of the packet
     */
    public int getPacketSize() {
        return packetSize;
    }

    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    /**
     *
     * @return time the packet arrives at dispatcher
     */
    public int getTimeArrive() {
        return timeArrive;
    }

    public void setTimeArrive(int timeToArrive) {
        this.timeArrive = timeToArrive;
    }

    /**
     *
     * @return time left to destination router
     */
    public int getTimeToDest() {
        return timeToDest;
    }

    /**
     *
     * @param timeToDest time to set that is left to destination
     */
    public void setTimeToDest(int timeToDest) {
        this.timeToDest = timeToDest;
    }

    /**
     * Creates a string representation of the packet
     * @return string representation of the packet
     */
    public String toString(){
        return ("[" + id + ", " + timeArrive + ", " + timeToDest + "]");
    }
}
