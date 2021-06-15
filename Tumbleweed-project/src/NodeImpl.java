public class NodeImpl implements Node {

    private boolean available;
    private final int testValue = -1;
    private int output;
    private int tickCounter;
    private double failureProbability = 0.01;
    private boolean brokenness =false;


    public NodeImpl() {
        available = true;
    }


    /**
     * indicates if the node is available to process new data
     * @return true if available, false otherwise
     */
    @Override
    public boolean isAvailable(){
        return available;
    }


    // TO DO:
    // - move failure probability to each and every task, not just when you check with the testValue

    /**
     * sends raw data to the node to process
     * @param rawData the raw data to process
     */
    @Override
    public void process(int rawData) {
        // !ER00R! sanity check if we haven't called a non available node
        if (!available) System.out.println("The node was called while not available");
        // the node starts work (becomes unavailable)
        available = false;
        // !ER00R! a broken node was called
        if (brokenness){
            System.out.println("A broken node was called");
            output = (int) Math.floor(Math.random() * (1001 - 1) + 1); // range [1, 1000]
            tickCounter = 0;
        }
        //simulating node behaviour
        else{
            if (rawData == testValue ) {
                //heartbeat signal
                output = testValue;
                tickCounter = 0;
            }
            else {
                //How many ticks is the process taking
                tickCounter = (int) Math.floor(Math.random() * (6 - 1) + 1); // return random Integer in range [1, 5]
                //output the node will return after #tickCounter ticks
                output = Integer.valueOf(rawData).hashCode();
            }
        }
        // Simulating node failure
        if (Math.random() < failureProbability) brokenness=true;
    }

    /**
     * shows if the node has finished processing the data
     * @return true if available, false otherwise
     */
    @Override
    public boolean isProcessedDataAvailable() {
        if (tickCounter == 0) {
            return true;
        }
        tickCounter--;
        return false;
    }

    /**
     * gets the latest processed data from the node
     * @return processed data package
     */
    @Override
    public int getProcessedData() {
        available = true;
        return output;
    }
}
