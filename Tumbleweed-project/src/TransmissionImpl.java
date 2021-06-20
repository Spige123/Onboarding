public class TransmissionImpl implements Transmission{


    // Assuming we have a communications satellite at an altitude h = 1000km
    // time span when communication is possible (in seconds)
    private final int timeDay = 1928;
    // time span when communication is not possible (in seconds)
    private final int timeNight = 6904;
    // probability that the antenna is pointed towards the sky
    private final double communicationProbability = 0.7;

    @Override
    public boolean isAvailable(int time) {
        // check if the satellite is visible in the sky
        if (time % (timeNight + timeDay) < timeDay) {
            // account for probability that the antenna will will not be positioned towards sky
            return Math.random() < communicationProbability;
        } else {
            return false;
        }
    }

    @Override
    public void transmitProcessedData(int processedData) {

    }
}
