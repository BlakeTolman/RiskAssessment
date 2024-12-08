<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Broker Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: flex-start; /* Allow the content to start at the top */
            height: 100%;
            margin: 0;
        }
        .container {
            width: 400px;
            padding: 30px;
            margin-top: 20px; /* Add margin from the top to allow space when scrolling back */
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
            text-align: left;
        }
        label {
            display: block;
            font-weight: bold;
        }
        input[type="text"], input[type="number"], input[type="checkbox"], select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type="submit"], button {
            padding: 10px 20px;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            margin-top: 10px;
        }
        .generate-button {
            background-color: #007bff;
        }
        .clear-button {
            background-color: #6c757d;
        }
        .delete-button {
            background-color: #dc3545;
        }
        .logout-button {
            margin-top: 20px;
            background-color: #dc3545;
        }
        .export-button {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            display: none; /* Initially hidden */
        }
        .report {
            margin-top: 20px;
            padding: 15px;
            background-color: #f9f9f9;
            border: 1px solid #ccc;
            border-radius: 10px;
        }
        .risk-score {
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            margin-top: 20px;
        }
        .risk-description {
            text-align: left;
            margin-top: 10px;
            font-style: italic;
            font-size: 16px;
        }
        .section-title {
            margin-top: 20px;
            font-weight: bold;
            text-align: left;
        }
        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 15px;
        }
    </style>
    <script>
        function loadClientData() {
            const clientDropdown = document.getElementById("existingClient");
            const selectedClient = clientDropdown.value;

            if (selectedClient !== "") {
                document.getElementById("clientName").value = selectedClient;

                // Trigger form submission to autofill fields for the selected client
                document.getElementById("loadClientDataForm").submit();
            }
        }

        function clearFields() {
            // Clear all input fields
            document.getElementById("clientName").value = "";
            document.getElementById("clientAge").value = "";
            document.getElementById("creditScore").value = "";
            document.getElementById("claimHistory").value = "";
            document.getElementById("yearsLicensed").value = "";
            document.getElementById("accidentsCount").value = "";
            document.getElementById("carAge").value = "";
            document.getElementById("safetyRating").value = "";
            document.getElementById("annualMileage").value = "";
            document.getElementById("antiTheftDevice").checked = false;
            document.getElementById("reliabilityRating").value = "";

            // Reset the dropdown to default
            document.getElementById("existingClient").selectedIndex = 0;

            // Hide the report section if it exists
            const reportSection = document.querySelector(".report");
            if (reportSection) {
                reportSection.remove();
            }

            // Hide the Export PDF button
            document.getElementById("exportButton").style.display = "none";
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Welcome to Broker Dashboard</h2>
        <p>Enter client and car information to generate a risk assessment report.</p>

        <!-- Form to select an existing client -->
        <form action="RiskAssessmentServlet" method="post" id="loadClientDataForm">
            <div class="form-group">
                <label for="existingClient">Select Existing Client Or Enter New Data:</label>
               <select id="existingClient" name="selectedClientName" onchange="loadClientData()">
                   <option value="">-- Select a Client --</option>
                   <%
                       List<String> clientNames = (List<String>) request.getAttribute("clientNames");
                       String selectedClientName = (String) request.getAttribute("selectedClientName");
                       if (clientNames != null) {
                           for (String name : clientNames) {
                   %>
                       <option value="<%= name %>" <%= name.equals(selectedClientName) ? "selected" : "" %>><%= name %></option>
                   <%
                           }
                       }
                   %>
               </select>

            </div>
        </form>

        <!-- Separate Delete Button Form -->
        <form action="DeleteClientServlet" method="post" class="delete-button-form">
            <input type="hidden" id="deleteClientName" name="clientName" value="<%= request.getAttribute("clientName") != null ? request.getAttribute("clientName") : "" %>">
            <button type="submit" class="delete-button">Delete Existing User</button>
        </form>

        <!-- Form to Generate Risk Report -->
        <form action="RiskAssessmentServlet" method="post">
            <!-- Client Information Fields -->
            <h3>Client Information</h3>
            <div class="form-group">
                <label for="clientName">Client Name:</label>
                <input type="text" id="clientName" name="clientName" value="<%= request.getAttribute("clientName") != null ? request.getAttribute("clientName") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="clientAge">Client Age:</label>
                <input type="number" id="clientAge" name="clientAge" value="<%= request.getAttribute("clientAge") != null ? request.getAttribute("clientAge") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="creditScore">Credit Score:</label>
                <input type="number" id="creditScore" name="creditScore" value="<%= request.getAttribute("creditScore") != null ? request.getAttribute("creditScore") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="claimHistory">Claim History:</label>
                <input type="text" id="claimHistory" name="claimHistory" value="<%= request.getAttribute("claimHistory") != null ? request.getAttribute("claimHistory") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="yearsLicensed">Years Licensed:</label>
                <input type="number" id="yearsLicensed" name="yearsLicensed" value="<%= request.getAttribute("yearsLicensed") != null ? request.getAttribute("yearsLicensed") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="accidentsCount">Number of Accidents:</label>
                <input type="number" id="accidentsCount" name="accidentsCount" value="<%= request.getAttribute("accidentsCount") != null ? request.getAttribute("accidentsCount") : "" %>" required>
            </div>

            <!-- Car Information Fields -->
            <h3>Car Information</h3>
            <div class="form-group">
                <label for="carAge">Car Age (years):</label>
                <input type="number" id="carAge" name="carAge" value="<%= request.getAttribute("carAge") != null ? request.getAttribute("carAge") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="safetyRating">Safety Rating (1-5):</label>
                <input type="number" id="safetyRating" name="safetyRating" min="1" max="5" value="<%= request.getAttribute("safetyRating") != null ? request.getAttribute("safetyRating") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="annualMileage">Annual Mileage:</label>
                <input type="number" id="annualMileage" name="annualMileage" value="<%= request.getAttribute("annualMileage") != null ? request.getAttribute("annualMileage") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="antiTheftDevice">Anti-Theft Device:</label>
                <input type="checkbox" id="antiTheftDevice" name="antiTheftDevice" <%= (Boolean.TRUE.equals(request.getAttribute("antiTheftDevice"))) ? "checked" : "" %>>
            </div>
            <div class="form-group">
                <label for="reliabilityRating">Reliability Rating (1-5):</label>
                <input type="number" id="reliabilityRating" name="reliabilityRating" min="1" max="5" value="<%= request.getAttribute("reliabilityRating") != null ? request.getAttribute("reliabilityRating") : "" %>" required>
            </div>
            <div class="button-group">
                <input type="submit" value="Generate Risk Report" class="generate-button">
                <button type="button" class="clear-button" onclick="clearFields()">Clear Fields</button>
            </div>
        </form>

        <!-- Display the risk assessment report if it's available -->
        <%
            String riskScore = (String) request.getAttribute("riskScore");
            if (riskScore != null) {
        %>
        <div class="report">
            <div class="risk-score">
                Risk Score: <%= riskScore %>
            </div>
            <div class="risk-description">
                <%= (String) request.getAttribute("riskDescription") %>
            </div>
            <p class="section-title">Client Information</p>
            <p>Client Name: <%= request.getAttribute("clientName") != null ? request.getAttribute("clientName") : "N/A" %></p>
            <p>Client Age: <%= request.getAttribute("clientAge") != null ? request.getAttribute("clientAge") : "N/A" %></p>
            <p>Credit Score: <%= request.getAttribute("creditScore") != null ? request.getAttribute("creditScore") : "N/A" %></p>
            <p>Claim History: <%= request.getAttribute("claimHistory") != null ? request.getAttribute("claimHistory") : "N/A" %></p>
            <p>Years Licensed: <%= request.getAttribute("yearsLicensed") != null ? request.getAttribute("yearsLicensed") : "N/A" %></p>
            <p>Number of Accidents: <%= request.getAttribute("accidentsCount") != null ? request.getAttribute("accidentsCount") : "N/A" %></p>

            <p class="section-title">Car Information</p>
            <p>Car Age: <%= request.getAttribute("carAge") != null ? request.getAttribute("carAge") + " years" : "N/A" %></p>
            <p>Safety Rating: <%= request.getAttribute("safetyRating") != null ? request.getAttribute("safetyRating") : "N/A" %></p>
            <p>Annual Mileage: <%= request.getAttribute("annualMileage") != null ? request.getAttribute("annualMileage") : "N/A" %></p>
            <p>Anti-Theft Device: <%= (Boolean.TRUE.equals(request.getAttribute("antiTheftDevice"))) ? "Yes" : "No" %></p>
            <p>Reliability Rating: <%= request.getAttribute("reliabilityRating") != null ? request.getAttribute("reliabilityRating") : "N/A" %></p>
        </div>
        <style>
            .export-button {
                display: block;
            }
        </style>
        <% } %>

         <!-- Export PDF button -->
         <form action="ExportPdfServlet" method="post">
             <input type="hidden" name="clientName" value="<%= request.getAttribute("clientName") %>">
             <input type="hidden" name="riskScore" value="<%= request.getAttribute("riskScore") %>">
             <input type="hidden" name="riskDescription" value="<%= request.getAttribute("riskDescription") %>">
             <input type="hidden" name="clientAge" value="<%= request.getAttribute("clientAge") %>">
             <input type="hidden" name="creditScore" value="<%= request.getAttribute("creditScore") %>">
             <input type="hidden" name="claimHistory" value="<%= request.getAttribute("claimHistory") %>">
             <input type="hidden" name="yearsLicensed" value="<%= request.getAttribute("yearsLicensed") %>">
             <input type="hidden" name="accidentsCount" value="<%= request.getAttribute("accidentsCount") %>">
             <input type="hidden" name="carAge" value="<%= request.getAttribute("carAge") %>">
             <input type="hidden" name="safetyRating" value="<%= request.getAttribute("safetyRating") %>">
             <input type="hidden" name="annualMileage" value="<%= request.getAttribute("annualMileage") %>">
             <input type="hidden" name="antiTheftDevice" value="<%= request.getAttribute("antiTheftDevice") != null && (Boolean) request.getAttribute("antiTheftDevice") ? "true" : "false" %>">
             <input type="hidden" name="reliabilityRating" value="<%= request.getAttribute("reliabilityRating") %>">
             <input type="submit" value="Export Risk Report to PDF" id="exportButton" class="export-button">
         </form>

        <!-- Logout button -->
        <form action="LogoutServlet" method="get">
            <input type="submit" class="logout-button" value="Logout">
        </form>
    </div>
</body>
</html>
