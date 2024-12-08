# Risk Assessment Tool

The **Risk Assessment Tool** is a web-based application designed to evaluate and manage risk profiles for insurance clients based on their personal and vehicle information. This tool provides brokers with a streamlined interface to manage client data, generate risk reports, and export detailed assessments in PDF format. It also includes an admin panel for managing user roles and accounts.

## Features

### Authentication
- Secure login system for brokers and admins.
- Role-based access control (admin vs. broker functionality).

### Admin Dashboard
- Add or remove brokers and admins.
- Password confirmation to ensure data integrity.
- Real-time updates to the user list.

### Broker Dashboard
- Dropdown to select and autofill existing client data.
- Comprehensive form to input client and car details.
- Generate risk reports based on a sophisticated scoring algorithm.

### Risk Assessment Reports
- Dynamic calculation of risk scores based on client and car attributes.
- Intuitive risk descriptions to guide decision-making.
- Exportable reports in PDF format for easy sharing and record-keeping.

### Client Management
- Add, update, and delete client records.
- Real-time updates to client lists upon modification.

## Technology Stack
- **Frontend**: JSP, HTML, CSS.
- **Backend**: Java Servlets, Java.
- **Server**: Apache Tomcat.
- **Data Management**: CSV storage for client and user data.
- **PDF Generation**: iText library.


## How It Works
1. **Login**: Admins and brokers authenticate through the login page.
   - Login via defualt admin account (username) **admin** -- (password) **adminPass**
3. **Admin Management**: Admins can manage user accounts, including brokers and other admins.
   - If user_profiles.csv file is empty the program will add a default admin. 
5. **Broker Workflow**:
   - Select existing clients or input new client data.
   - Generate risk assessments based on personal and vehicle details.
   - Export risk reports as PDF for further use.
6. **Client Management**: Modify client records with immediate updates to dropdown menus and associated workflows.

