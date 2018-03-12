package uk.gov.hmrc.contentFiles;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class ContentFile {

    protected File contentFile;
    protected Map<String, String> content;

    public ContentFile(String filePath){

        try {
            this.contentFile = new File(filePath);
        } catch (Exception e){
            System.out.println("File don't exist mayyyte: " + filePath);
            this.contentFile = null;
        }

        this.content = new HashMap<String, String>();
    }

    public Map<String, String> getContent(){
        return this.content;
    }


    public abstract void readContent();


}
