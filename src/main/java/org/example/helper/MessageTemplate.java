package org.example.helper;

import java.io.*;
import java.util.ArrayList;

public class MessageTemplate {

    public static ArrayList<String> readData() throws IOException {
        String filename = "message_template.txt";
        String pathToFile = System.getProperty("user.dir") + File.separator + filename;
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
