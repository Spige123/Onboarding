import java.util.ArrayList;

public class Scheduler implements GlobalData {

    // number of ticks for the given time interval
    private static int cycles = hours * 3600 * ticksPerSecond;
    // next node for checking
    private static int nextForCheck = 1;
    // the broken node that should be removed from the network
    private static Node nodeToRemove;

    // system components
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

    public static void main(String[] args) {
        instantiateNetwork();
        while (cycles > 0) {
            cycles--;
           // System.out.println(cycles);

            // is data being received?
            if (dataSource.hasDataAvailable()) { // yes -> receive raw data from the data source
                boolean isANodeAvailable = false;
                for (Node node : network) {
                    if (node.isAvailable()) { // is a node in the network available?

                        // see if you can send heartbeat signal to node
                        if (network.indexOf(node) == nextForCheck % nrOfNodes) { // yes -> send signal
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
                    isDataBeingReceived = true;

                    // check if the node has been previously verified
                    if (node.isVerified()) { // yes -> check for broken
                        if (node.getProcessedData() != testValue) { // broken
                            System.out.println("node " + node + " has broken down");
                            nodeToRemove = node;
                        } else { // not broken
                            node.getProcessedData();
                        }
                    } else { // no -> proceed wth normal steps
                        if (antenna.isAvailable(cycles)) { // yes -> is processed data already available in the database?
                            if (database.hasProcessedDataAvailable()) { // yes -> retrieve processed data to transmit, store new one in DB
                                antenna.transmitProcessedData(database.retrieveProcessedData());
                                database.saveProcessedData(node.getProcessedData());
                            } else { // no -> send available processed data for transmission + store it in database
                                int processedData = node.getProcessedData();
                                antenna.transmitProcessedData(processedData);
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
                    }
                    // no -> do nothing
                }
                // no -> do nothing (no processed data available anywhere)
            }

        }
        System.out.println("done");
    }


}
