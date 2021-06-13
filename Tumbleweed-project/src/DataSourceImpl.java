public class DataSourceImpl implements DataSource{

    /**
     * indicates if the data source has new raw data to provide
     * @return true (70%) if yes, false (30%) otherwise
     */
    @Override
    public boolean hasDataAvailable() {
        if (Math.random() < 0.7) {
            return true;
        }
        return false;
    }

    /**
     * retrieves the newly available raw data
     * @return raw data
     */
    @Override
    public int retrieveData(){
        return (int) Math.floor(Math.random() * (1001 - 1) + 1); // return random Integer in range [1, 1000]
    }
}
