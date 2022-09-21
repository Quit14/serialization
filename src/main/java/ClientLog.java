import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String> logList = new ArrayList<>();


    public void log(int productNum, int amount) {
        logList.add(productNum + "," + amount);
    }
    public void exportAsCSV(File txtFile) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile))) {
            csvWriter.writeNext(formatedList().split(", "), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String formatedList() {

        return logList.toString().replace("[","").replace("]", "");
}
}

