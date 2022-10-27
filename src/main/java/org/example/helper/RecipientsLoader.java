package org.example.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipientsLoader {
    public static List<String[]> readData() throws IOException {
        String file = "recipients.csv";
        String pathToFile = System.getProperty("user.dir") + "\\" + file;
        List<String[]> content = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + pathToFile + " not found");
        }
        return content;
    }


}
