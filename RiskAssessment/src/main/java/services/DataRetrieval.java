package services;

import jakarta.servlet.ServletContext;
import model.Car;
import model.Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Data management class to handle retrieval of data from CSV
public class DataRetrieval {

    private String CSV_FILE_PATH;
    private SecurityModule securityModule = new SecurityModule();

    // Constructor to set the file path using ServletContext
    public DataRetrieval(ServletContext context) {
        this.CSV_FILE_PATH = context.getRealPath("/data/risk_assessment_records.csv");
    }

    // Method to find a client by name (for auto-filling fields)
    public Client findClientByName(String clientName) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Decrypt the client name to check if it matches
                String decryptedClientName = securityModule.decryptData(data[0]);
                if (decryptedClientName.equalsIgnoreCase(clientName)) {
                    int clientAge = Integer.parseInt(securityModule.decryptData(data[1]));
                    int creditScore = Integer.parseInt(securityModule.decryptData(data[2]));
                    String claimHistory = securityModule.decryptData(data[3]);
                    int yearsLicensed = Integer.parseInt(securityModule.decryptData(data[4]));
                    int accidentsCount = Integer.parseInt(securityModule.decryptData(data[5]));

                    // Construct Client object
                    return new Client(decryptedClientName, clientAge, creditScore, claimHistory, yearsLicensed, accidentsCount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric data: " + e.getMessage());
        }
        return null;
    }

    // Method to find car data for a client by name (to pre-fill car information)
    public Car findCarByClientName(String clientName) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Decrypt the client name to check if it matches
                String decryptedClientName = securityModule.decryptData(data[0]);
                if (decryptedClientName.equalsIgnoreCase(clientName)) {
                    int carAge = Integer.parseInt(securityModule.decryptData(data[6]));
                    int safetyRating = Integer.parseInt(securityModule.decryptData(data[7]));
                    int annualMileage = Integer.parseInt(securityModule.decryptData(data[8]));
                    boolean hasAntiTheftDevice = Boolean.parseBoolean(securityModule.decryptData(data[9]));
                    int reliabilityRating = Integer.parseInt(securityModule.decryptData(data[10]));

                    // Construct Car object
                    return new Car(carAge, safetyRating, annualMileage, hasAntiTheftDevice, reliabilityRating);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric data: " + e.getMessage());
        }
        return null;
    }

    // Method to get all client names (for populating dropdown in the form)
    public List<String> getAllClientNames() {
        List<String> clientNames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String decryptedClientName = securityModule.decryptData(data[0]);
                clientNames.add(decryptedClientName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Decryption error: " + e.getMessage());
        }
        return clientNames;
    }
}
