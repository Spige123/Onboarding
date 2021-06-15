public class TransmissionImpl implements Transmission{
    
    // TO DO:
    // how many ticks are in an hour?


    //Assuming we have a communications satellite at an altitude h=1000km.
    //The time available for communications timeDay
    // and timeNight is where communications is not available.
    private int timeDay = 1928;
    private int timeNight = 6904;
    private double communicationProbability = 0.7;


    @Override
    public boolean isAvailable(int time) {
        // check if the satellite is visible in the sky
        if (time%(timeNight+timeDay)<timeDay) {
            //a random probability that the antenna will will not be positioned so that it can communicate
            if (Math.random()< communicationProbability) return true;
            else return false;
        }
        else return false;
    }


    @Override
    public void transmitProcessedData(int processedData) {

    }
}
