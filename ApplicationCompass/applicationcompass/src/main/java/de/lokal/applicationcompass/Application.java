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
     * @param companyName the name of the Company 
     * @param positionTitle the title of the position
     * @param applicationDate the Date of Application
     * @param status the status of the application. Finished,running  
     * @param contactPerson the contact Person of the Company
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

    public String getcompanyName() {
        return companyName;
    }

    public String getpositionTitle() {
        return positionTitle;
    }

    public String getapplicationDate() {
        return applicationDate;
    }

    public String getstatus() {
        return status;
    }

    public String getcontactPerson() {
        return contactPerson;
    }

    public String getnotes() {
        return notes;
    }

    // --Setter Methods--

    public void setcompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setpositionTitle (String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public void setapplicationDate (String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setstatus (String status) {
        this.status = status;
    }

    public void setcontactPerson (String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setnotes (String notes) {
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
