package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/ExportPdfServlet")
public class ExportPdfServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=RiskAssessmentReport.pdf");

        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            OutputStream out = response.getOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Risk Assessment Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add smaller spacing after the title
            Paragraph smallSpacing = new Paragraph(" "); // Creates a small blank paragraph
            smallSpacing.setSpacingAfter(2f);  // Adjust the spacing value to make it smaller
            document.add(smallSpacing);

            // Section: Client Information
            Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph clientInfoHeader = new Paragraph("Client Information", sectionFont);
            document.add(clientInfoHeader);
            document.add(smallSpacing);

            // Create a table for client details
            PdfPTable clientTable = new PdfPTable(2);
            clientTable.setWidthPercentage(100);
            clientTable.setSpacingBefore(5f);
            clientTable.setSpacingAfter(5f);
            clientTable.setWidths(new float[]{1.1f, 2.0f});  // Adjusted the widths to reduce the gap between columns

            // Add client details to table
            addTableCell(clientTable, "Client Name:", request.getParameter("clientName"));
            addTableCell(clientTable, "Client Age:", request.getParameter("clientAge"));
            addTableCell(clientTable, "Credit Score:", request.getParameter("creditScore"));
            addTableCell(clientTable, "Claim History:", request.getParameter("claimHistory"));
            addTableCell(clientTable, "Years Licensed:", request.getParameter("yearsLicensed"));
            addTableCell(clientTable, "Number of Accidents:", request.getParameter("accidentsCount"));

            document.add(clientTable);

            // Add line separator after client information
            document.add(new LineSeparator());

            // Section: Car Information
            Paragraph carInfoHeader = new Paragraph("Car Information", sectionFont);
            document.add(carInfoHeader);
            document.add(smallSpacing);

            PdfPTable carTable = new PdfPTable(2);
            carTable.setWidthPercentage(100);
            carTable.setSpacingBefore(5f);
            carTable.setSpacingAfter(5f);
            carTable.setWidths(new float[]{1.1f, 2.0f});  // Adjusted the widths to reduce the gap between columns

            // Add car details to table
            addTableCell(carTable, "Car Age:", request.getParameter("carAge"));
            addTableCell(carTable, "Safety Rating:", request.getParameter("safetyRating"));
            addTableCell(carTable, "Annual Mileage:", request.getParameter("annualMileage"));
            addTableCell(carTable, "Anti-Theft Device:", request.getParameter("antiTheftDevice").equals("true") ? "Yes" : "No");
            addTableCell(carTable, "Reliability Rating:", request.getParameter("reliabilityRating"));

            document.add(carTable);

            // Add line separator after car information
            document.add(new LineSeparator());

            // Section: Risk Assessment
            Paragraph riskHeader = new Paragraph("Risk Assessment", sectionFont);
            document.add(riskHeader);
            document.add(smallSpacing);

            PdfPTable riskTable = new PdfPTable(2);
            riskTable.setWidthPercentage(100);
            riskTable.setSpacingBefore(5f);
            riskTable.setSpacingAfter(5f);
            riskTable.setWidths(new float[]{1.1f, 2.0f});  // Adjusted the widths to reduce the gap between columns

            // Add risk assessment details to table
            addTableCell(riskTable, "Risk Score:", request.getParameter("riskScore"));
            addTableCell(riskTable, "Risk Description:", request.getParameter("riskDescription"));

            document.add(riskTable);

            // Add line separator after risk assessment
            document.add(new LineSeparator());

            document.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    private void addTableCell(PdfPTable table, String label, String value) {
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font valueFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(2f);  // Reduced padding for better alignment
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "N/A", valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(2f);  // Reduced padding for better alignment
        table.addCell(valueCell);
    }
}
