public class NodeImpl implements Node {

    private boolean availability;
    private boolean brokenness;
    private boolean ProcessedDataAvailability;
    private final int testValue = 0;
    private int ticksToProcess = -1;
    private int output = -1;
    private int tickCounter=-1;
    private double failureProbability = 0.01;



    @Override
    public boolean isAvailable(){
        return availability;
    }

    /*@Override
    public boolean isBroken() {
        return brokenness;
    }
     */

    @Override
    public void process(int rawData,int n) throws Exception {
        if(!brokenness && availability){
            tickCounter = n;
            if(rawData == testValue){
                output =testValue;
                ticksToProcess=0;
            }else{
                output = rawData%101;
                ticksToProcess = (int)(java.lang.Math.random()*10)+2;
            }
        }
        availability=false;
        if(java.lang.Math.random()<failureProbability)brokenness=true;
    }

    @Override
    public boolean isProcessedDataAvailable(int n) {
        if(n<(ticksToProcess+tickCounter-1)||brokenness)ProcessedDataAvailability=false;
        else ProcessedDataAvailability=true;
        return ProcessedDataAvailability;
    }

    @Override
    public int getProcessedData() {
        if(brokenness)return (int)(java.lang.Math.random()*101);
        if(ProcessedDataAvailability) {
            availability =true;
            return output;
        }
        return -1;
    }
}
