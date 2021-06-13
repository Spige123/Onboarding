public class NodeImpl implements Node {

    private boolean available;
    private final int testValue = -1;
    private int output;
    private int tickCounter;


    /**
     * indicates if the node is available to process new data
     * @return true if available, false otherwise
     */
    @Override
    public boolean isAvailable(){
        return available;
    }

    /**
     * sends raw data to the node to process
     * @param rawData the raw data to process
     */
    @Override
    public void process(int rawData) {
        available = false;
        if (rawData == testValue) {
            output = testValue;
            tickCounter = 0;
        } else {
            tickCounter = (int) Math.floor(Math.random() * (6 - 1) + 1); // return random Integer in range [1, 5]ù
            output = Integer.valueOf(rawData).hashCode();
        }
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
