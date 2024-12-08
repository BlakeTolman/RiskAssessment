package services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletContext;

import java.util.List;

//Dedicated service layer for client UI-related data preparation
public class ClientService {

    private final DataRetrieval dataRetrieval;

    public ClientService(ServletContext context) {
        this.dataRetrieval = new DataRetrieval(context);
    }

    // Get list of all client names
    public List<String> getAllClientNames() {
        return dataRetrieval.getAllClientNames();
    }

    // Set client names in request for dropdown
    public void setClientNamesAttribute(HttpServletRequest request) {
        List<String> clientNames = dataRetrieval.getAllClientNames();
        request.setAttribute("clientNames", clientNames);
    }

}
