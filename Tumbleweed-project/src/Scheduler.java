public class Scheduler {

    // functional parameters
    // number of nodes
    private static final int n = 5;
    // number of iterations until executions stops
    private static int cycles = 1000;

    // system components
    private static final Node[] network = new Node[n];
    private static final Database database = new DatabaseImpl();
    private static final DataSource dataSource = new DataSourceImpl();
    private static final Transmission antenna = new TransmissionImpl();

    /**
     * instantiates the network with nodes
     */
    private static void instantiateNetwork() {
        for (int i = 0; i < n; i++) {
            network[i] = new NodeImpl();
        }
    }

    public static void main(String[] args) {
        instantiateNetwork();
        while (cycles > 0) {
            cycles--;

            // actual implementation of the scheduler

        }
    }


}
