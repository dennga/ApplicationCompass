package de.lokal.applicationcompass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The main application class for the Application Compass CLI tool.
 * Manages job applications, provides a user interface, and handles data persistence.
 */
public class ApplicationManagerApp {

    // --- Private Fields ---
    // These fields store the application data and tools needed for the app.
    private List<Application> applications; // Stores all job application objects
    private Scanner scanner; // Used to read user input from the console
    private final String DATA_FILE_PATH = "applications.csv"; // The file path for saving/loading data

    /**
     * Constructor for the ApplicationManagerApp.
     * Initializes the list of applications and the scanner, and attempts to load existing data.
     */
    public ApplicationManagerApp() {
        this.applications = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        loadApplications(); // Attempt to load data when the application starts
    }

    /**
     * The main entry point of the Application Compass application.
     * Creates an instance of ApplicationManagerApp and starts its main loop.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        ApplicationManagerApp app = new ApplicationManagerApp();
        app.run(); // Starts the main application logic
    }

    /**
     * Runs the main loop of the application.
     * Displays the menu, gets user input, and executes the corresponding action.
     */
    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addApplication();
                    break;
                case 2:
                    viewAllApplications();
                    break;
                case 3:
                    editApplication(); // Now active!
                    break;
                case 4:
                    deleteApplication(); // Now active!
                    break;
                case 5:
                    running = false;
                    saveApplications(); // Save data before exiting
                    System.out.println("Exiting Application Compass. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println("\n"); // Add a newline for better readability between menu cycles
        }
        scanner.close(); // Close the scanner to release system resources
    }

    /**
     * Displays the main menu options to the user.
     */
    private void displayMenu() {
        System.out.println("--- Application Compass ---");
        System.out.println("1. Add New Application");
        System.out.println("2. View All Applications");
        System.out.println("3. Edit Application"); // Updated menu text
        System.out.println("4. Delete Application"); // Updated menu text
        System.out.println("5. Exit");
        System.out.print("Your choice: ");
    }

    /**
     * Reads and validates the user's menu choice.
     * Ensures that the input is an integer.
     *
     * @return The valid integer choice made by the user.
     */
    private int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Consume the invalid input
            System.out.print("Your choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the remaining newline character after reading the integer
        return choice;
    }

    /**
     * Guides the user through adding a new job application.
     * Prompts for details and creates a new Application object.
     */
    private void addApplication() {
        System.out.println("\n--- Add New Application ---");
        System.out.print("Company Name: ");
        String companyName = scanner.nextLine();

        System.out.print("Position Title: ");
        String positionTitle = scanner.nextLine();

        System.out.print("Application Date (YYYY-MM-DD): ");
        String applicationDate = scanner.nextLine();

        System.out.print("Status (e.g., Applied, Interview, Rejected): ");
        String status = scanner.nextLine();

        System.out.print("Contact Person (optional, press Enter to skip): ");
        String contactPerson = scanner.nextLine();

        System.out.print("Notes (optional, press Enter to skip): ");
        String notes = scanner.nextLine();

        // Create a new Application object and add it to the list
        Application newApp = new Application(companyName, positionTitle, applicationDate, status, contactPerson, notes);
        applications.add(newApp);
        System.out.println("Application successfully added!");
    }

    /**
     * Displays all currently stored job applications.
     * Shows a message if no applications are present.
     */
    private void viewAllApplications() {
        System.out.println("\n--- All Applications ---");
        if (applications.isEmpty()) {
            System.out.println("No applications available yet.");
            return;
        }
        for (int i = 0; i < applications.size(); i++) {
            System.out.println((i + 1) + ". " + applications.get(i).toString());
        }
    }

    /**
     * Allows the user to edit an existing job application.
     * User selects an application by number and then can update its details.
     */
    private void editApplication() {
        System.out.println("\n--- Edit Application ---");
        if (applications.isEmpty()) {
            System.out.println("No applications to edit yet.");
            return;
        }

        viewAllApplications(); // Show list to help user choose
        System.out.print("Enter the number of the application to edit: ");
        int appNumber = getUserChoice(); // Reusing getUserChoice for number input

        if (appNumber < 1 || appNumber > applications.size()) {
            System.out.println("Invalid application number.");
            return;
        }

        // Get the application object (adjust for 0-based index)
        Application appToEdit = applications.get(appNumber - 1);
        System.out.println("Editing: " + appToEdit.toString());
        System.out.println("Enter new values (press Enter to keep current value):");

        System.out.print("New Company Name (" + appToEdit.getCompanyName() + "): ");
        String newCompanyName = scanner.nextLine();
        if (!newCompanyName.isEmpty()) {
            appToEdit.setCompanyName(newCompanyName);
        }

        System.out.print("New Position Title (" + appToEdit.getPositionTitle() + "): ");
        String newPositionTitle = scanner.nextLine();
        if (!newPositionTitle.isEmpty()) {
            appToEdit.setPositionTitle(newPositionTitle);
        }

        System.out.print("New Application Date (YYYY-MM-DD, " + appToEdit.getApplicationDate() + "): ");
        String newApplicationDate = scanner.nextLine();
        if (!newApplicationDate.isEmpty()) {
            appToEdit.setApplicationDate(newApplicationDate);
        }

        System.out.print("New Status (" + appToEdit.getStatus() + "): ");
        String newStatus = scanner.nextLine();
        if (!newStatus.isEmpty()) {
            appToEdit.setStatus(newStatus);
        }

        System.out.print("New Contact Person (" + (appToEdit.getContactPerson().isEmpty() ? "none" : appToEdit.getContactPerson()) + "): ");
        String newContactPerson = scanner.nextLine();
        // Allow user to clear contact person by entering ""
        appToEdit.setContactPerson(newContactPerson);


        System.out.print("New Notes (" + (appToEdit.getNotes().isEmpty() ? "none" : appToEdit.getNotes()) + "): ");
        String newNotes = scanner.nextLine();
        // Allow user to clear notes by entering ""
        appToEdit.setNotes(newNotes);

        System.out.println("Application updated successfully!");
    }

    /**
     * Allows the user to delete an existing job application.
     * User selects an application by number to remove it from the list.
     */
    private void deleteApplication() {
        System.out.println("\n--- Delete Application ---");
        if (applications.isEmpty()) {
            System.out.println("No applications to delete yet.");
            return;
        }

        viewAllApplications(); // Show list to help user choose
        System.out.print("Enter the number of the application to delete: ");
        int appNumber = getUserChoice();

        if (appNumber < 1 || appNumber > applications.size()) {
            System.out.println("Invalid application number.");
            return;
        }

        // Remove the application (adjust for 0-based index)
        Application removedApp = applications.remove(appNumber - 1);
        System.out.println("Application for '" + removedApp.getCompanyName() + "' (" + removedApp.getPositionTitle() + ") deleted successfully.");
    }

    /**
     * Saves all current job applications to a CSV file.
     * Each application is written as a line in the format: company;position;date;status;contact;notes
     * Handles potential IOException during file writing.
     */
    private void saveApplications() {
        try (FileWriter writer = new FileWriter(DATA_FILE_PATH)) {
            for (Application app : applications) {
                writer.write(app.getCompanyName() + ";" +
                             app.getPositionTitle() + ";" +
                             app.getApplicationDate() + ";" +
                             app.getStatus() + ";" +
                             app.getContactPerson() + ";" +
                             app.getNotes() + "\n");
            }
            System.out.println("Applications saved successfully to " + DATA_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error saving applications: " + e.getMessage());
        }
    }

    /**
     * Loads job applications from a CSV file into the application list.
     * Reads each line, parses the data, and creates Application objects.
     * Handles potential IOException during file reading and parsing errors.
     */
    private void loadApplications() {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            System.out.println("Data file not found. Starting with an empty list.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", -1); // -1 to keep trailing empty strings
                // Ensure we have enough parts for mandatory fields (company, position, date, status)
                if (parts.length >= 4) {
                    String companyName = parts[0];
                    String positionTitle = parts[1];
                    String applicationDate = parts[2];
                    String status = parts[3];
                    String contactPerson = (parts.length > 4) ? parts[4] : ""; // Optional field
                    String notes = (parts.length > 5) ? parts[5] : ""; // Optional field

                    Application loadedApp = new Application(companyName, positionTitle, applicationDate, status, contactPerson, notes);
                    applications.add(loadedApp);
                } else {
                    System.err.println("Skipping malformed line in data file: " + line);
                }
            }
            System.out.println("Applications loaded successfully from " + DATA_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error loading applications: " + e.getMessage());
        }
    }
}