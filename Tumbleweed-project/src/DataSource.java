public interface DataSource {

    /**
     * checks if the data source has new incoming data
     * @return true if data available, false otherwise
     */
    boolean hasDataAvailable();

    /**
     * retrieves the raw data from the data source (when available)
     * @return raw data
     */
    int retrieveData();

}
