package org.example.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecipientsLoader {
    private String pathToFile;
    private String filename;
    public RecipientsLoader(boolean _isJar, String _filename) {
        filename = _filename;
        if (_isJar) {
            pathToFile = "./" + _filename;
        } else {
            pathToFile = System.getProperty("user.dir") + File.separator + filename;
        }
    }
    public List<String[]> readData() throws IOException {
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
