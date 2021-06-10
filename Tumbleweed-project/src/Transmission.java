public interface Transmission {

    /**
     * checks if the antenna is available for transmission
     * @return true if antenna is available, false otherwise
     */
    boolean isAvailable();

    /**
     * sends processed data to the antenna for transmission
     * @param processedData the data to be transmitted
     */
    void transmitProcessedData(int processedData);

}
