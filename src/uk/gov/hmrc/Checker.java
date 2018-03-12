package uk.gov.hmrc;

import uk.gov.hmrc.contentFiles.ApplicationContent;
import uk.gov.hmrc.contentFiles.BusinessContent;
import uk.gov.hmrc.contentFiles.ContentFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Checker {

    private List<String> correctContent;
    private List<String> missingContent;
    private List<String> incorrectContent;

    private Map<String, String> englishContent;
    private Map<String, String> welshContent;

    public Checker (List<String> inputFiles){

        englishContent = new HashMap<>();
        welshContent = new HashMap<>();

        correctContent = new ArrayList<>();
        missingContent = new ArrayList<>();
        incorrectContent = new ArrayList<>();

        for(String file : inputFiles){
            String welshFile = file.replace("/eng/", "/cym/");

            ContentFile engCf = null;
            ContentFile cymCf = null;

            if(file.contains("/content/")){
                if(file.contains("/applicationText/")){
                    engCf = new ApplicationContent(file);
                    cymCf = new ApplicationContent(welshFile);

                }else{
                    engCf = new BusinessContent(file);
                    cymCf = new BusinessContent(welshFile);
                }
            }

            for (String key : engCf.getContent().keySet()) {
                if (!englishContent.containsKey(key)) {
                    englishContent.put(key, engCf.getContent().get(key));
                }
            }

            for (String key : cymCf.getContent().keySet()) {
                if (!welshContent.containsKey(key)) {
                    welshContent.put(key, cymCf.getContent().get(key));
                }
            }
        }

        sort();
    }

    private void sort(){

        for (String key : englishContent.keySet()) {

            String welshEquivalent = welshContent.get(key);

            if(welshEquivalent == null || welshEquivalent.equals("") || welshEquivalent.equals("null")){
                this.missingContent.add(key);
            }else if(welshEquivalent.equals(englishContent.get(key))){
                this.incorrectContent.add(key);
            }else{
                this.correctContent.add(key);
            }
        }
    }

    private String getWelshContent(String key){
        return welshContent.get(key);
    }

    private String getEnglishContent(String key){
        return englishContent.get(key);
    }

    public void generateMissingContentFile(){
        try {
            ResultWriter missingContentWriter = new ResultWriter("missingContent");
            for (String key : missingContent) {
                missingContentWriter.writeLine(key, getEnglishContent(key), getWelshContent(key));
            }
            missingContentWriter.close();
        } catch (Exception e) {
            System.out.println("Ducky's Fault");
        }
    }

    public void generateIncorrectContentFile(){
        try {
            ResultWriter incorrectContentWriter = new ResultWriter("incorrectContent");
            for (String key : incorrectContent) {
                incorrectContentWriter.writeLine(key, getEnglishContent(key), getWelshContent(key));
            }
            incorrectContentWriter.close();
        } catch (Exception e) {
            System.out.println("Foxy's fault");
        }
    }

    public void generateCorrectConfigFile(){
        try {
            ResultWriter correctContentWriter = new ResultWriter("correctContent");
            for (String key : correctContent) {
                correctContentWriter.writeLine(key, getEnglishContent(key), getWelshContent(key));
            }
            correctContentWriter.close();
        } catch (Exception e) {
            System.out.println("Shut Up Ricky");
        }
    }

}
