public class TransmissionImpl implements Transmission, GlobalData{

    //Assuming we have a communications satellite at an altitude h=1000km.
    // time the satellite is available for transmission (in seconds * ticks/s)
    private int timeAvailable = 1928 * ticksPerSecond;
    // time the satellite is not available for transmission (in seconds * ticks/s)
    private int timeUnavailable = 6904 * ticksPerSecond;
    // probability that the antenna is pointed towards the sky (70%)
    private double communicationProbability = 0.7;


    /**
     * checks if the antenna can transmit data at a certain time instant
     * @param time the current tick number we are at
     * @return true if yes, false otherwise
     */
    @Override
    public boolean isAvailable(int time) {
        // check if the satellite is available for transmission
        if (time % (timeUnavailable + timeAvailable) < timeAvailable) {
            // probability that the antenna is not positioned so that it can communicate
            return Math.random() < communicationProbability;
        } else {
            return false;
        }
    }


    @Override
    public void transmitProcessedData(int processedData) {

    }
}
