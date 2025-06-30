package de.lokal.applicationcompass;

/**
 * Represents a single job application with its details.
 * This class acts as a data model for an application entry.
 */

public class Application {
    private String companyName;
    private String positionTitle;
    private String applicationDate;
    private String status;
    private String contactPerson;
    private String notes;

    /**
     * @param companyName       The name of the company the application was sent to.
     * @param positionTitle     The title of the position applied for.
     * @param applicationDate   The date when the application was sent, in YYYY-MM-DD format.
     * @param status            The current status of the application ("Applied", "Interview", "Rejected", "Offer").
     * @param contactPerson     The name of the contact person at the company.
     * @param notes             Any additional notes or comments related to the application.
     */

    // Constructor (the blueprint of a Application)
    public Application(String companyName, String positionTitle, String applicationDate, String status, String contactPerson, String notes) {
        this.companyName = companyName;
        this.positionTitle = positionTitle;
        this.applicationDate = applicationDate;
        this.status = status;
        this.contactPerson = contactPerson;
        this.notes = notes;

    }

    // --Getter Methods-- 

    public String getCompanyName() {
        return companyName;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getNotes() {
        return notes;
    }

    // --Setter Methods--

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPositionTitle (String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public void setApplicationDate (String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public void setContactPerson (String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setNotes (String notes) {
        this.notes = notes;
    }

     /**
     * Provides a string representation of the Application object.
     * Useful for displaying application details in a readable format,
     * especially for console output or file storage (e.g., CSV).
     */

    @Override
    public String toString() {
        return companyName + ";" + positionTitle + ";" + applicationDate + ";" + status + ";" + contactPerson + ";" + notes + ";";
    }

}
