package project.oop.loadDataToSearchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class LoadDataToServer {
    public static void loadToServer() {
        gitPushDataFile();        
        reloadData();
    }

    public static void gitPushDataFile() {
        String batDirectory = "C:\\Coding\\News-Aggregator\\newsaggregator"; //change this path to local path from local machine
        //this is relative Path: new File(System.getProperty("user.dir")).getParent();

        // Path to the batch file
        String batFilePath = batDirectory + "\\reload-data-from-local-machine.bat";
        
        // Create a ProcessBuilder instance
        ProcessBuilder processBuilder = new ProcessBuilder(batFilePath);

        // Set the working directory (optional)
        processBuilder.directory(new java.io.File(batDirectory));

        try {
            // Start the process
            Process process = processBuilder.start();

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("Batch file exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void reloadData() {
        try {
            // Create a URL object
            URL url = new URL("https://vtqn-search-engine-75080fd33305.herokuapp.com/reloadDataFromJava");
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
                System.out.println(inputLine);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}