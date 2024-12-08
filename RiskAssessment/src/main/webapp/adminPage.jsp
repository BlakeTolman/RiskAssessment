<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profiles</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            width: 400px;
            padding: 30px;
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
        input[type="text"], input[type="password"], select {
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
        .add-button {
            background-color: #007bff;
        }
        .delete-button {
            background-color: #dc3545;
        }
        .logout-button {
            margin-top: 20px;
            background-color: #dc3545;
        }
    </style>
    <script>
        function validatePasswordMatch(event) {
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;

            if (password !== confirmPassword) {
                alert("Passwords do not match. Please try again.");
                event.preventDefault(); // Prevent form submission
            }
        }

        function autofillUserDetails() {
            const userDropdown = document.getElementById("existingUser");
            const selectedUser = userDropdown.value;

            if (selectedUser !== "") {
                const userRole = userDropdown.options[userDropdown.selectedIndex].getAttribute("data-role");
                document.getElementById("accountType").value = userRole;
                document.getElementById("username").value = selectedUser;
            } else {
                document.getElementById("accountType").value = "";
                document.getElementById("username").value = "";
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>User Profiles</h2>
        <p>Manage broker and admin profiles by adding or deleting users.</p>

        <!-- Dropdown to select existing user for deletion -->
        <form action="DeleteAdminServlet" method="post">
            <div class="form-group">
                <label for="existingUser">Select Existing User to Delete or Edit:</label>
                <select id="existingUser" name="username" onchange="autofillUserDetails()">
                    <option value="">-- Select a User --</option>
                    <%
                        Map<String, String> brokerNamesWithRoles = (Map<String, String>) request.getAttribute("brokerNamesWithRoles");
                        if (brokerNamesWithRoles != null) {
                            for (Map.Entry<String, String> entry : brokerNamesWithRoles.entrySet()) {
                                String brokerName = entry.getKey();
                                String role = entry.getValue();
                    %>
                    <option value="<%= brokerName %>" data-role="<%= role %>"><%= brokerName %> - <%= role %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <input type="hidden" name="action" value="deleteBrokerOrAdmin">
            <button type="submit" class="delete-button">Delete User</button>
        </form>

        <!-- Form to add a new user profile -->
        <form action="AdminServlet" method="post" onsubmit="validatePasswordMatch(event)">
            <div class="form-group">
                <label for="accountType">Account Type:</label>
                <select id="accountType" name="role" required>
                    <option value="">-- Select Account Type --</option>
                    <option value="admin">Admin</option>
                    <option value="broker">Broker</option>
                </select>
            </div>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" required>
            </div>
            <input type="hidden" name="action" value="addBrokerOrAdmin">
            <button type="submit" class="add-button">Add User</button>
        </form>

        <!-- Logout button -->
        <form action="LogoutServlet" method="get">
            <input type="submit" class="logout-button" value="Logout">
        </form>
    </div>
</body>
</html>
