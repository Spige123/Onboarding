import java.io.IOException;

public interface Node {

    /**
     * states whether the node is available for processing data
     * @return true if available, false otherwise
     */
    boolean isAvailable();

    /**
     * sends data to the node for processing
     * @param rawData the raw data to process
     */
    void process(int rawData);

    /**
     * checks if the node has finished processing data
     * @return true if information has been processed, false otherwise
     */
    boolean isProcessedDataAvailable();

    /**
     * retrieves the processed data from the node
     * @return the processed data
     */
    int getProcessedData();

    /**
     * checks if the node was previously sent a test value for verification
     * @return true if yes, false otherwise
     */
    boolean isVerified();

}
