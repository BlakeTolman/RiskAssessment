package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletContext;
import services.ClientService;
import services.AuthorizationService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ServletContext context = getServletContext();
        AuthorizationService authService = new AuthorizationService(context);
        String role = authService.authorizeUser(username, password);

        if (role != null) {
            // Create a session if one does not exist
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("userRole", role);

            // Get the list of brokers and admins for populating the dropdown in admin page
            if ("admin".equals(role)) {
                // Retrieve all brokers and admins
                Map<String, String> usersWithRoles = authService.getAllUsersWithRoles();
                request.setAttribute("brokerNamesWithRoles", usersWithRoles);

                // Forward to admin page
                request.getRequestDispatcher("adminPage.jsp").forward(request, response);
            } else if ("broker".equals(role)) {
                // Use ClientService to populate dropdown list with client names
                ClientService clientService = new ClientService(context);

                // Retrieve client names using ClientService
                List<String> clientNames = clientService.getAllClientNames();

                // Set clientNames attribute to the request before forwarding to brokerPage.jsp
                request.setAttribute("clientNames", clientNames);

                // Forward to broker page
                request.getRequestDispatcher("brokerPage.jsp").forward(request, response);
            }
        } else {
            // If login fails, redirect back to login with error message
            request.setAttribute("errorMessage", "Invalid username or password. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
