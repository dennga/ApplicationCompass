package de.lokal.applicationcompass;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File; 
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * The Main application class for the Application Compass CLI tool.
 * Manages job applications, provides a user interface, and handles data persistence.
 */

public class ApplicationManagerApp {

    // --- Private Fields ---
    // These fields store the application data and tools needed for the app.
    private List<Application> applications; 
    private Scanner sc;
    private final String DATA_FILE_PATH = "applications.csv";
    









    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}