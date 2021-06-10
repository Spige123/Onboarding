import java.util.ArrayList;

public class DatabaseImpl implements Database{

    private ArrayList<Integer> rawData = new ArrayList<>();
    private ArrayList<Integer> processedData = new ArrayList<>();
    private int indexRawData;
    private int indexProcessedData;

    public DatabaseImpl() {
        indexRawData = 0;
        indexProcessedData = 0;
    }

    @Override
    public void saveRawData(int data) {
        rawData.add(data);
    }

    @Override
    public void saveProcessedData(int data) {
        for(int i = 0; i < rawData.size(); i++) {
            if (rawData.get(i).hashCode() == data) {
                rawData.remove(i);
                indexRawData--;
                processedData.add(data);
                break;
            }
        }
    }

    @Override
    public int retrieveRawData() {
        return rawData.get(indexRawData++);
    }

    @Override
    public int retrieveProcessedData() {
        return processedData.get(indexProcessedData++);
    }

    @Override
    public boolean hasRawDataAvailable() {
        return (indexRawData < rawData.size());
    }

    @Override
    public boolean hasProcessedDataAvailable() {
        return (indexProcessedData < processedData.size());
    }

    @Override
    public void removeProcessedData() {
        processedData.remove(0);
    }

}
