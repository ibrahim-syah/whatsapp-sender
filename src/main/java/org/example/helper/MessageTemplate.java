package org.example.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MessageTemplate {
    public static ArrayList<String> readData() throws IOException {
        String filename = "message_template.txt";
        String pathToFile = System.getProperty("user.dir") + "\\" + filename;
        ArrayList<String> content = new ArrayList<>();
        content.add("Halo ");
        try(BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + pathToFile + " not found");
        }
        return content;
    }
}
