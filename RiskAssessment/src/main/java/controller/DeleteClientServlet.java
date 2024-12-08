package controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ClientService;
import services.DataDeletion;

import java.io.IOException;

@WebServlet("/DeleteClientServlet")
public class DeleteClientServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the context and instantiate services
        ServletContext context = getServletContext();
        DataDeletion dataDeletion = new DataDeletion(context);
        ClientService clientService = new ClientService(context);

        // Get the client name to delete
        String clientName = request.getParameter("clientName");

        // Delete the client data if the client name is provided
        if (clientName != null && !clientName.isEmpty()) {
            dataDeletion.deleteClientData(clientName);
        }

        // Use ClientService to set the updated client list in the request
        clientService.setClientNamesAttribute(request);

        // Forward back to brokerPage.jsp with updated list of clients
        request.getRequestDispatcher("brokerPage.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
