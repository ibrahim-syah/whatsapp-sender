package org.example.helper;

import java.io.*;
import java.util.ArrayList;

public class MessageTemplate {
    private String pathToFile;
    private String filename;
    public MessageTemplate(boolean _isJar, String _filename) {
        filename = _filename;
        if (_isJar) {
            pathToFile = "./" + _filename;
        } else {
            pathToFile = System.getProperty("user.dir") + File.separator + filename;
        }
    }

    public ArrayList<String> readData() throws IOException {
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
