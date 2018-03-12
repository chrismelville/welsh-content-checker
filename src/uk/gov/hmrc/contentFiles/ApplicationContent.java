package uk.gov.hmrc.contentFiles;

import java.io.BufferedReader;
import java.io.FileReader;

public class ApplicationContent extends ContentFile{

    public ApplicationContent(String filePath) {
        super(filePath);
        readContent();
    }

    @Override
    public void readContent() {
        try (BufferedReader br = new BufferedReader(new FileReader(contentFile))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {

                if(line.trim().startsWith("<li id=")){

                    String key = extractKey(sb.toString());
                    String value = extractValue(sb.toString());

                    System.out.println(sb.toString().trim());

                    if(!(key.equals(""))){
//                    System.out.println(key + ":" + value);
                        content.put(key, value);
                    }

                    sb = new StringBuilder();
                }

                sb.append(line);

            }
        }catch (Exception e){
            System.out.println("File don't exist mayyyyyte: " + contentFile );
        }
    }


    private String extractKey(String line){

        int startIndex = 0;
        int endIndex = 0;
        String key = "";

        if(line.contains("id=")) {
            try {
                startIndex = line.indexOf('"');
                endIndex = line.indexOf('"', startIndex + 1);
                key = line.substring(startIndex + 1, endIndex);
            } catch (Exception e) {
            }
        }

        return key;
    }

    private String extractValue(String line){

        int startIndex = 0;
        int endIndex = 0;
        String value = "";

        try {
            startIndex = line.indexOf('>');
            endIndex= line.indexOf("</li>", startIndex + 1);
            value = line.substring(startIndex +1 , endIndex);
        }catch (Exception e){
        }

        return value;
    }

}
