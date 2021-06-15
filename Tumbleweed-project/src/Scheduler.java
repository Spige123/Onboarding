public class Scheduler implements GlobalData {

    // number of ticks for the given time interval
    private static int cycles = hours * 3600 * ticksPerSecond;

    // system components
    private static final Node[] network = new Node[nrOfNodes];
    private static final Database database = new DatabaseImpl();
    private static final DataSource dataSource = new DataSourceImpl();
    private static final Transmission antenna = new TransmissionImpl();

    /**
     * instantiates the network with nodes
     */
    private static void instantiateNetwork() {
        for (int i = 0; i < nrOfNodes; i++) {
            network[i] = new NodeImpl();
        }
    }

    public static void main(String[] args) {
        instantiateNetwork();
        while (cycles > 0) {
            cycles--;

            // actual implementation of the scheduler

        }
        System.out.println("done");
    }


}
