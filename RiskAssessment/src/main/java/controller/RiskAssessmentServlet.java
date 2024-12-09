package controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Client;
import model.Car;
import services.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/RiskAssessmentServlet")
public class RiskAssessmentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();

        // Check if the user is logged in by verifying the session role
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("userRole");

        if (role != null && "broker".equals(role)) {
            // Populate broker-specific attributes and handle the request
            setBrokerAttributes(request);
            handleBrokerRequest(request);
            reloadClientList(request);

            // Forward updated attributes to the broker page
            request.getRequestDispatcher("brokerPage.jsp").forward(request, response);
        } else {
            // Redirect to login page if no valid session exists
            response.sendRedirect("login.jsp");
        }
    }

    private void setBrokerAttributes(HttpServletRequest request) {
        ServletContext context = getServletContext();
        DataRetrieval dataRetrieval = new DataRetrieval(context);

        // Populate the dropdown list with client names
        List<String> clientNames = dataRetrieval.getAllClientNames();
        request.setAttribute("clientNames", clientNames);

        // Handle selected client for auto-filling details
        String selectedClientName = request.getParameter("selectedClientName");
        if (selectedClientName != null && !selectedClientName.isEmpty()) {
            request.setAttribute("selectedClientName", selectedClientName);
            Client client = dataRetrieval.findClientByName(selectedClientName);
            Car car = dataRetrieval.findCarByClientName(selectedClientName);

            // Populate request attributes with client and car details if found
            if (client != null && car != null) {
                setRequestAttributesForClientAndCar(request, client, car, null, null);
            }
        }
    }

    private void handleBrokerRequest(HttpServletRequest request) {
        ServletContext context = getServletContext();
        DataRetrieval dataRetrieval = new DataRetrieval(context);
        DataStorage dataStorage = new DataStorage(context);

        // Retrieve and validate client and car data from the form
        String clientName = request.getParameter("clientName");
        String clientAge = request.getParameter("clientAge");
        String clientCreditScore = request.getParameter("creditScore");
        String clientClaimHistory = request.getParameter("claimHistory");
        String yearsLicensed = request.getParameter("yearsLicensed");
        String accidentsCount = request.getParameter("accidentsCount");

        String carAge = request.getParameter("carAge");
        String safetyRating = request.getParameter("safetyRating");
        String annualMileage = request.getParameter("annualMileage");
        String reliabilityRating = request.getParameter("reliabilityRating");
        boolean hasAntiTheftDevice = request.getParameter("antiTheftDevice") != null;

        if (isClientAndCarDataValid(clientName, clientAge, clientCreditScore, clientClaimHistory, yearsLicensed, accidentsCount, carAge, safetyRating, annualMileage, reliabilityRating)) {
            try {
                // Create Client and Car objects with provided details
                Client client = new Client(
                        clientName,
                        Integer.parseInt(clientAge),
                        Integer.parseInt(clientCreditScore),
                        clientClaimHistory,
                        Integer.parseInt(yearsLicensed),
                        Integer.parseInt(accidentsCount)
                );

                Car car = new Car(
                        Integer.parseInt(carAge),
                        Integer.parseInt(safetyRating),
                        Integer.parseInt(annualMileage),
                        hasAntiTheftDevice,
                        Integer.parseInt(reliabilityRating)
                );

                // Calculate risk score and generate risk description
                RiskAssessmentEngine engine = new RiskAssessmentEngine();
                float riskScore = engine.calculateRiskScore(client, car);
                String riskDescription = getRiskDescription(riskScore);

                // Pass calculated risk details to the request
                setRequestAttributesForClientAndCar(request, client, car, riskScore, riskDescription);

                // Update client and car data in persistent storage
                dataStorage.updateClientData(client, car, riskScore);

            } catch (NumberFormatException e) {
                // Handle invalid input formats gracefully
                request.setAttribute("errorMessage", "Invalid input. Please ensure all fields are filled in correctly.");
            }
        } else {
            // Handle missing or incomplete input data
            request.setAttribute("errorMessage", "All client and car details are required.");
        }
    }

    private void setRequestAttributesForClientAndCar(HttpServletRequest request, Client client, Car car, Float riskScore, String riskDescription) {
        // Populate request attributes with client and car details
        request.setAttribute("clientName", client.getName());
        request.setAttribute("clientAge", client.getAge());
        request.setAttribute("creditScore", client.getCreditScore());
        request.setAttribute("claimHistory", client.getClaimHistory());
        request.setAttribute("yearsLicensed", client.getYearsLicensed());
        request.setAttribute("accidentsCount", client.getAccidentsCount());
        request.setAttribute("carAge", car.getAge());
        request.setAttribute("safetyRating", car.getSafetyRating());
        request.setAttribute("annualMileage", car.getAnnualMileage());
        request.setAttribute("antiTheftDevice", car.hasAntiTheftDevice());
        request.setAttribute("reliabilityRating", car.getReliabilityRating());

        // Include risk score and description if available
        if (riskScore != null) {
            request.setAttribute("riskScore", String.format("%.2f", riskScore));
            request.setAttribute("riskDescription", riskDescription);
        }
    }

    private boolean isClientAndCarDataValid(String clientName, String clientAge, String clientCreditScore, String clientClaimHistory, String yearsLicensed, String accidentsCount, String carAge, String safetyRating, String annualMileage, String reliabilityRating) {
        // Validate that all required fields are not null or empty
        return clientName != null && clientAge != null && clientCreditScore != null && clientClaimHistory != null &&
                yearsLicensed != null && accidentsCount != null && carAge != null && safetyRating != null &&
                annualMileage != null && reliabilityRating != null;
    }

    private String getRiskDescription(float riskScore) {
        // Generate a risk description based on the calculated risk score
        if (riskScore >= 1.0 && riskScore <= 1.99) {
            return "Low Risk: Eligible for lower premiums due to favorable risk factors.";
        } else if (riskScore >= 2.0 && riskScore <= 2.99) {
            return "Medium Risk: Moderate level of risk with standard premiums.";
        } else if (riskScore >= 3.0 && riskScore <= 5.0) {
            return "High Risk: Higher insurance premiums due to significant risk factors.";
        } else {
            return "Unknown Risk: The risk score is outside the expected range.";
        }
    }

    private void reloadClientList(HttpServletRequest request) {
        ServletContext context = getServletContext();
        DataRetrieval dataRetrieval = new DataRetrieval(context);

        // Reload the list of client names for the dropdown
        List<String> clientNames = dataRetrieval.getAllClientNames();
        request.setAttribute("clientNames", clientNames);
    }
}
