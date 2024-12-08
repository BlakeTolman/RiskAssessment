package controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AuthorizationService;

import java.io.IOException;
import java.util.Map;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        AuthorizationService authService = new AuthorizationService(context);

        String action = request.getParameter("action");

        if ("addBrokerOrAdmin".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            // Validate input fields
            if (username != null && !username.isEmpty() && password != null && !password.isEmpty() && role != null && !role.isEmpty()) {
                authService.addUser(username, password, role);
            } else {
                request.setAttribute("errorMessage", "All fields are required.");
            }
        } else {
            request.setAttribute("errorMessage", "Unsupported action: " + action);
        }

        // Redirect to doGet to reload the updated admin page
        response.sendRedirect("AdminServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        AuthorizationService authService = new AuthorizationService(context);

        // Reload the list of users
        Map<String, String> usersWithRoles = authService.getAllUsersWithRoles();
        request.setAttribute("brokerNamesWithRoles", usersWithRoles);

        // Forward to adminPage.jsp with updated list of users
        request.getRequestDispatcher("adminPage.jsp").forward(request, response);
    }
}
