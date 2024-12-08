package services;

import jakarta.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Handles user authentication, authorization, and management of user credentials and roles.
public class AuthorizationService {
    private Map<String, String> userCredentials;
    private Map<String, String> userRoles;
    private DataStorage dataStorage;

    public AuthorizationService(ServletContext servletContext) {
        userCredentials = new HashMap<>();
        userRoles = new HashMap<>();
        dataStorage = new DataStorage(servletContext);

        // Load users from CSV or create default admin if CSV is empty
        loadUsersFromCSV();
        if (userCredentials.isEmpty()) {
            addDefaultAdmin();
        }
    }
    // Loads user credentials and roles from CSV storage
    private void loadUsersFromCSV() {
        List<String[]> users = dataStorage.getAllBrokersOrAdmins();
        for (String[] user : users) {
            String username = user[0];
            String password = user[1];
            String role = user[2];
            userCredentials.put(username, password);
            userRoles.put(username, role);
        }
    }
    // Adds a default admin user if no users exist
    private void addDefaultAdmin() {
        addUser("admin", "adminPass", "admin");
    }

    // Validates a user's credentials and returns their role if successful
    public String authorizeUser(String username, String password) {
        if (userCredentials.containsKey(username)) {
            if (userCredentials.get(username).equals(password)) {
                return userRoles.get(username);
            }
        }
        return null;
    }
    // Adds a new user with the specified credentials and role
    public void addUser(String username, String password, String role) {
        userCredentials.put(username, password);
        userRoles.put(username, role);
        dataStorage.addBrokerOrAdmin(username, password, role);
    }

    // New method to get all users with roles
    public Map<String, String> getAllUsersWithRoles() {
        return new HashMap<>(userRoles);
    }
}
