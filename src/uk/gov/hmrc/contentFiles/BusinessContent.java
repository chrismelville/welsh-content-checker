package uk.gov.hmrc.contentFiles;

import java.io.BufferedReader;
import java.io.FileReader;

public class BusinessContent extends ContentFile {


    public BusinessContent(String filePath) {
        super(filePath);
        readContent();
    }

    @Override
    public void readContent() {

        String key = extractKey();
        StringBuilder value = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(contentFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                value.append(line);
            }
        }catch (Exception e){
            value.append("null");
        }finally {
            if(!(key.equals(""))){
                System.out.println(key + ":" + value.toString());
                content.put(key, value.toString());
            }
        }
    }

    private String extractKey(){
        int startIndex = 0;
        int endIndex = 0;

        String filePath = contentFile.getAbsolutePath();

        startIndex = filePath.lastIndexOf("/") + 1;
        endIndex = filePath.lastIndexOf(".html");

        System.out.println(filePath);
        return filePath.substring(startIndex, endIndex);
        // print out what string its working on

    }
}
