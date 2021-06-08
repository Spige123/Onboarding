public interface Database {

    /**
     * sava data in the database
     * @param data the data to be saved
     */
    void save(int data);

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


}
