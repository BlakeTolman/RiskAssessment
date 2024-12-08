package services;

import jakarta.servlet.ServletContext;
import model.Client;
import model.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Data management class for storing data into CSV
public class DataStorage {

    private String CLIENT_CSV_FILE_PATH;
    private String BROKER_CSV_FILE_PATH;
    private SecurityModule securityModule = new SecurityModule();

    // Constructor to set up the CSV file paths
    public DataStorage(ServletContext servletContext) {
        this.CLIENT_CSV_FILE_PATH = servletContext.getRealPath("/data/risk_assessment_records.csv");
        this.BROKER_CSV_FILE_PATH = servletContext.getRealPath("/data/user_profiles.csv");
    }

    // Method to add or update client data in the CSV
    public void updateClientData(Client client, Car car, float riskScore) {
        List<String[]> allRecords = new ArrayList<>();
        String clientName = client.getName();
        boolean clientFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(CLIENT_CSV_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String decryptedClientName = securityModule.decryptData(data[0]);

                if (decryptedClientName.equals(clientName)) {
                    // Update client and car data
                    data[0] = securityModule.encryptData(client.getName());
                    data[1] = securityModule.encryptData(String.valueOf(client.getAge()));
                    data[2] = securityModule.encryptData(String.valueOf(client.getCreditScore()));
                    data[3] = securityModule.encryptData(client.getClaimHistory());
                    data[4] = securityModule.encryptData(String.valueOf(client.getYearsLicensed()));
                    data[5] = securityModule.encryptData(String.valueOf(client.getAccidentsCount()));
                    data[6] = securityModule.encryptData(String.valueOf(car.getAge()));
                    data[7] = securityModule.encryptData(String.valueOf(car.getSafetyRating()));
                    data[8] = securityModule.encryptData(String.valueOf(car.getAnnualMileage()));
                    data[9] = securityModule.encryptData(String.valueOf(car.hasAntiTheftDevice()));
                    data[10] = securityModule.encryptData(String.valueOf(car.getReliabilityRating()));
                    data[11] = securityModule.encryptData(String.format("%.2f", riskScore));
                    clientFound = true;
                }

                allRecords.add(data);
            }

            // If the client was not found, add a new record
            if (!clientFound) {
                String[] newData = new String[12];
                newData[0] = securityModule.encryptData(client.getName());
                newData[1] = securityModule.encryptData(String.valueOf(client.getAge()));
                newData[2] = securityModule.encryptData(String.valueOf(client.getCreditScore()));
                newData[3] = securityModule.encryptData(client.getClaimHistory());
                newData[4] = securityModule.encryptData(String.valueOf(client.getYearsLicensed()));
                newData[5] = securityModule.encryptData(String.valueOf(client.getAccidentsCount()));
                newData[6] = securityModule.encryptData(String.valueOf(car.getAge()));
                newData[7] = securityModule.encryptData(String.valueOf(car.getSafetyRating()));
                newData[8] = securityModule.encryptData(String.valueOf(car.getAnnualMileage()));
                newData[9] = securityModule.encryptData(String.valueOf(car.hasAntiTheftDevice()));
                newData[10] = securityModule.encryptData(String.valueOf(car.getReliabilityRating()));
                newData[11] = securityModule.encryptData(String.format("%.2f", riskScore));
                allRecords.add(newData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated records back to CSV
        try (FileWriter fileWriter = new FileWriter(CLIENT_CSV_FILE_PATH);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            for (String[] record : allRecords) {
                writer.println(String.join(",", record));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to add or update broker/admin data in the CSV
    public void addBrokerOrAdmin(String username, String password, String role) {
        List<String[]> allRecords = new ArrayList<>();
        boolean userFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(BROKER_CSV_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String decryptedUsername = securityModule.decryptData(data[0]);

                if (decryptedUsername.equals(username)) {
                    // Update existing broker/admin
                    data[0] = securityModule.encryptData(username);
                    data[1] = securityModule.encryptData(password);
                    data[2] = securityModule.encryptData(role);
                    userFound = true;
                }

                allRecords.add(data);
            }

            if (!userFound) {
                // Add new broker/admin if not found
                String[] newUser = new String[3];
                newUser[0] = securityModule.encryptData(username);
                newUser[1] = securityModule.encryptData(password);
                newUser[2] = securityModule.encryptData(role);
                allRecords.add(newUser);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated records back to CSV
        try (FileWriter fileWriter = new FileWriter(BROKER_CSV_FILE_PATH);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            for (String[] record : allRecords) {
                writer.println(String.join(",", record));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get all brokers and admins from the CSV
    public List<String[]> getAllBrokersOrAdmins() {
        List<String[]> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(BROKER_CSV_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Check if data has at least 3 columns (username, password, role)
                if (data.length >= 3) {
                    String decryptedUsername = securityModule.decryptData(data[0]);
                    String decryptedPassword = securityModule.decryptData(data[1]);
                    String decryptedRole = securityModule.decryptData(data[2]);

                    users.add(new String[]{decryptedUsername, decryptedPassword, decryptedRole});
                } else {
                    // Handle or log the case where data doesn't have enough columns
                    System.err.println("[ERROR] Malformed line in CSV, skipping: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

}
