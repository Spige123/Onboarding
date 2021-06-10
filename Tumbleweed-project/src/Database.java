public interface Database {

    /**
     * sava raw data in the database
     * @param rawData the data to be saved
     */
    void saveRawData(int rawData);

    /**
     * save processed data in the database
     * @param processedData the data to be saved
     */
    void saveProcessedData(int processedData);

    /**
     * retrieve the next piece of raw data stored in the database
     * @return next piece of raw data
     */
    int retrieveRawData();

    /**
     * retrieve the next piece of processed data stored in the database
     * @return next piece of processed data
     */
    int retrieveProcessedData();

    /**
     * checks if there is raw data in the database
     * @return true if there is data, false otherwise
     */
    boolean hasRawDataAvailable();

    /**
     * checks if there is processed data in the database
     * @return true if there is data, false otherwise
     */
    boolean hasProcessedDataAvailable();

    /**
     * deletes the processed data that has been successfully sent
     * by the antenna
     */
    void removeProcessedData();


}
