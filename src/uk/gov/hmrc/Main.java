package uk.gov.hmrc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        File inputFile = new File("./input/input.txt");
        List<String> inputFiles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                listFiles(line, inputFiles);
            }
        }catch (Exception e){
            System.out.println(e);
        }

        Checker checker = new Checker(inputFiles);

        checker.generateMissingContentFile();
        checker.generateIncorrectContentFile();
        checker.generateCorrectConfigFile();

    }


    private static void listFiles(String directoryName, List<String> files) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile() && file.getAbsolutePath().contains("/eng/") && (!file.getAbsolutePath().contains(".DS_Store"))) {
                files.add(file.getAbsolutePath());
                //System.out.println("part 1");
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                listFiles(file.getAbsolutePath(), files);
                //System.out.println("part 2");
            }
        }
    }
}
