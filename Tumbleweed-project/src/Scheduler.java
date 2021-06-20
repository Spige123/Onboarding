import java.util.ArrayList;

public class Scheduler implements GlobalData {

    // Variables used in the program
    // number of ticks (= cycles) as function of #hours and ticks/s rate
    private static int cycles = hours * 3600 * ticksPerSecond;
    // next node to be checked for failure
    private static int nextForCheck = 0;
    // detected broken node that is removed from the network
    private static Node nodeToRemove;

    // Variables for statistics
    // number of corrupted packages (sent for processing to broken nodes)
    private static int corruptedPackages = 0;
    // number of packages received as input
    private static int inputPackages = 0;
    // number of successfully transmitted packages
    private static int transmittedPackages = 0;

    // System components
    private static final ArrayList<Node> network = new ArrayList<Node>();
    private static final Database database = new DatabaseImpl();
    private static final DataSource dataSource = new DataSourceImpl();
    private static final Transmission antenna = new TransmissionImpl();

    /**
     * instantiates the network with nodes
     */
    private static void instantiateNetwork() {
        for (int i = 0; i < nrOfNodes; i++) {
            network.add(new NodeImpl());
        }
    }

    /**
     * prints on the console the brokenness status of all nodes in the network
     */
    private static void checkBrokenStatus() {
        for (Node node : network) {
            node.checkBroken();
        }
        System.out.println("--------------------");
    }

    /**
     * prints on the console the statistics for the current program run
     */
    private static void showStatistics() {
        System.out.println("Statistics:");
        System.out.println(inputPackages + " packages received");
        System.out.println(corruptedPackages + " packages corrupted");
        System.out.println(transmittedPackages + " packages transmitted");
    }



    public static void main(String[] args) {
        instantiateNetwork();
        while (cycles > 0) {
            cycles--;

            // is data being received?
            if (dataSource.hasDataAvailable()) { // yes -> receive raw data from the data source
                inputPackages++;
                boolean isANodeAvailable = false;
                for (Node node : network) {
                    if (node.isAvailable()) { // is a node in the network available?

                        // see if you can send heartbeat signal to node
                        if (network.indexOf(node) == nextForCheck % network.size()) { // yes -> send signal
                            node.process(testValue);
                            nextForCheck++;
                        } else { // no -> proceed with normal steps

                            // yes -> is raw data already available in the database?
                            if (database.hasRawDataAvailable()) { // yes -> retrieve old raw data + send to process, store new raw data
                                node.process(database.retrieveRawData());
                                database.saveRawData(dataSource.retrieveData());
                            } else { // no -> send available raw data for processing, store it in the database
                                int rawData = dataSource.retrieveData();
                                node.process(rawData);
                                database.saveRawData(rawData);
                            }
                            isANodeAvailable = true;
                        }
                    }
                }

                if (! isANodeAvailable) { // no -> store data in the database
                    database.saveRawData(dataSource.retrieveData());
                }

            } else if (database.hasRawDataAvailable()) { // no -> is raw data already available in the database?
                // yes -> is a node in the network available?
                for (Node node : network) {
                    if (node.isAvailable()) { // yes -> send raw data from database for processing
                        node.process(database.retrieveRawData());
                        break;
                    }
                }
                // no -> go to the processed data retrieval part

            } // no -> go to the processed data retrieval part

            boolean isDataBeingReceived = false;
            for (Node node : network) {
                // is processed data being received (from the network) ?
                if (node.isProcessedDataAvailable()) { // yes -> is transmission hardware available?
                    // check if the node has been previously verified
                    if (node.isVerified()) { // yes -> check for broken
                        if (node.getProcessedData() != testValue) { // broken
                            System.out.println("node " + node + " has broken down");
                            nodeToRemove = node;
                            corruptedPackages += ((NodeImpl)node).brokennessEfficiency;
                        } else { // not broken
                            node.getProcessedData();
                        }
                    } else { // no -> proceed wth normal steps
                        isDataBeingReceived = true;
                        if (antenna.isAvailable(cycles)) { // yes -> is processed data already available in the database?
                            if (database.hasProcessedDataAvailable()) { // yes -> retrieve processed data to transmit, store new one in DB
                                antenna.transmitProcessedData(database.retrieveProcessedData());
                                transmittedPackages++;
                                database.saveProcessedData(node.getProcessedData());
                            } else { // no -> send available processed data for transmission + store it in database
                                int processedData = node.getProcessedData();
                                antenna.transmitProcessedData(processedData);
                                transmittedPackages++;
                                database.saveProcessedData(processedData);
                            }

                        } else { // no -> store processed data in database + delete equivalent raw data
                            database.saveProcessedData(node.getProcessedData());
                        }
                    }
                }
            }

            // remove any broken node detected during this iteration
            // Note: removes if only still contained in network
            network.remove(nodeToRemove);

            if (! isDataBeingReceived) { // no -> is there processed data in the database?
                if (database.hasProcessedDataAvailable()) { // yes -> is the antenna available?
                    if (antenna.isAvailable(cycles)) { // yes -> send processed data from DB to antenna
                        antenna.transmitProcessedData(database.retrieveProcessedData());
                        transmittedPackages++;
                    }
                    // no -> do nothing
                }
                // no -> do nothing (no processed data available anywhere)
            }

            checkBrokenStatus();
        }
        showStatistics();
    }


}
