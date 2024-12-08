package controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AuthorizationService;
import services.DataDeletion;

import java.io.IOException;

@WebServlet("/DeleteAdminServlet")
public class DeleteAdminServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        DataDeletion dataDeletion = new DataDeletion(context);

        // Get the username to delete
        String username = request.getParameter("username");

        // Delete the user using DataDeletion class
        boolean deleted = dataDeletion.deleteBrokerOrAdmin(username);

        if (!deleted) {
            request.setAttribute("errorMessage", "User not found or could not be deleted.");
        }

        // Forward the request to AdminServlet for repopulating the dropdown list and rendering the page
        request.getRequestDispatcher("/AdminServlet").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
