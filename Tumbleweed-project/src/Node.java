import java.io.IOException;

public interface Node {

    /**
     * states whether the node is available for processing data
     * @return true if available, false otherwise
     */
    boolean isAvailable();

    /**
     * states if the node is broken/functional or not
     * @return true if node is broken, false otherwise
     */
    //boolean isBroken();

    /**
     * @param rawData the raw data to process
     * @throws Exception exception in case processing fails
     */
    void process(int rawData, int n) throws Exception;

    /**
     * checks if the node has finished processing data
     * @return true if information has been processed, false otherwise
     */
    boolean isProcessedDataAvailable(int n);

    /**
     * retrieves the processed data from the node
     * @return the processed data
     */
    int getProcessedData();

}
