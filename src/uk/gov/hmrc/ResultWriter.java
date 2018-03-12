package uk.gov.hmrc;

import java.io.File;
import java.io.FileWriter;

public class ResultWriter {

    private File file;
    private FileWriter writer;

    public ResultWriter(String fileName) throws Exception{

            this.file = new File("./results/" + fileName + ".csv");

            // creates the file
            file.createNewFile();

            // creates a FileWriter Object
            this.writer = new FileWriter(file);

            // Writes the content to the file
            writer.write("Key|English Content|Welsh Content");
    }


    public void writeLine(String key, String english, String welsh) throws Exception{
        writer.write("\r\n" + key + "|" + english  + "|" + welsh);
    }

    public void close() throws Exception{
        writer.flush();
        writer.close();
    }
}
