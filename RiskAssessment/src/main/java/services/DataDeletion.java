package services;

import jakarta.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Data management class to handle deletion of Data from CSV
public class DataDeletion {

    private String CLIENT_CSV_FILE_PATH;
    private String BROKER_CSV_FILE_PATH;
    private SecurityModule securityModule = new SecurityModule();

    // Constructor to set up the CSV file paths
    public DataDeletion(ServletContext servletContext) {
        this.CLIENT_CSV_FILE_PATH = servletContext.getRealPath("/data/risk_assessment_records.csv");
        this.BROKER_CSV_FILE_PATH = servletContext.getRealPath("/data/user_profiles.csv");
    }

    // Method to delete a client record from the CSV
    public boolean deleteClientData(String clientName) {
        List<String[]> allRecords = new ArrayList<>();
        boolean clientFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(CLIENT_CSV_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String decryptedClientName = securityModule.decryptData(data[0]);

                if (!decryptedClientName.equals(clientName)) {
                    allRecords.add(data);
                } else {
                    clientFound = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated records back to CSV if the client was found
        if (clientFound) {
            try (FileWriter fileWriter = new FileWriter(CLIENT_CSV_FILE_PATH);
                 PrintWriter writer = new PrintWriter(fileWriter)) {
                for (String[] record : allRecords) {
                    writer.println(String.join(",", record));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return clientFound;
    }

    // Method to delete a broker or admin record from the CSV
    public boolean deleteBrokerOrAdmin(String username) {
        List<String[]> allRecords = new ArrayList<>();
        boolean userFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(BROKER_CSV_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String decryptedUsername = securityModule.decryptData(data[0]);

                if (!decryptedUsername.equals(username)) {
                    allRecords.add(data);
                } else {
                    userFound = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated records back to CSV if the user was found
        if (userFound) {
            try (FileWriter fileWriter = new FileWriter(BROKER_CSV_FILE_PATH);
                 PrintWriter writer = new PrintWriter(fileWriter)) {
                for (String[] record : allRecords) {
                    writer.println(String.join(",", record));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return userFound;
    }
}
