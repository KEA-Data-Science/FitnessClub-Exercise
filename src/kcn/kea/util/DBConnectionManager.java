package kcn.kea.util;
// kcn

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

/**
 * The db-connection manager is designed to work like a singleton.
 * Instances are prohibited, make static calls.
 */
public class DBConnectionManager
{
    private final static String standardAPropertiesFileAddress = "src/application.properties";

    private static String user;
    private static String password;
    private static String url;

    private static Connection connection;

    private DBConnectionManager(){}

    public static String getState()
    {
        HashMap<String, String> states = getStateMap();
        return String.format("User:  %-15s  Connection:  %-9s  URL:  %-45s",
                             states.get("user"), states.get("connection-state"), states.get("url"));
    }

    public static HashMap<String, String> getStateMap()
    {
        HashMap<String, String> states = new HashMap<>();
        try
        {
            states.put("connection-state", connection != null && connection.isValid(1) ? "OPEN" : "CLOSED");
        } catch(SQLException throwables)
        {
            states.put("connection-state", "INVALID");
            throwables.printStackTrace();
        }
        states.put("user", user);
        states.put("url", url);
        return states;
    }

    /**
     * Returns an open MySQL DB connection if possible.
     * Use specifyCredentials() to set custom info.
     */
    public static Connection getConnection()
    {
        if(connection != null){return connection;}
        return connection = getDatabaseConnection();
    }

    private static Connection getDatabaseConnection()
    {
        boolean lackingCredentials = false;
        boolean nullsDetected = user == null || password == null || url == null;

        if(!nullsDetected)
        {lackingCredentials = (user.length() < 1 || password.length() < 1 || url.length() < 1);}

        /* This will be triggered, if user has not set custom credentials */
        if(nullsDetected || lackingCredentials)
        { // "jdbc:mysql://localhost:3306/".length == 26, I believe

            String state =
                    (nullsDetected ? "Missing " : "") +
                    (lackingCredentials ? "Bad " : "");

            System.out.println(state + "database credentials, trying standard connection at:\t" +
                               standardAPropertiesFileAddress);

            /* Attempting to set standard credentials if custom ones have not been specified */
            setCredentials(standardAPropertiesFileAddress);

        }

        try
        {
            return DriverManager.getConnection(url, user, password);
        } catch(SQLException e)
        {
            System.out.println("SQL Exception while establishing connection.");
            e.printStackTrace();
        }

        return null;
    }


    /**
     * supply credentials for MySQL database
     * from file found at absolute file location in format:
     * ex C:\folder\file.txt
     * and must have following lines (words in [] are concepts)
     * db.url = [url]
     * db.user = [user]
     * db.password = [passphrase]
     */
    public static boolean setCredentials(String pathToPropertiesFile)
    {

        Properties prop = new Properties();
        FileInputStream propertyFile = null;
        try
        {
            propertyFile = new FileInputStream(pathToPropertiesFile);
            prop.load(propertyFile);
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            url = prop.getProperty("db.url");

            return true;

        } catch(FileNotFoundException e) { e.printStackTrace(); } catch(IOException e)
        {
            e.printStackTrace();
        }
        /* if code reaches here, something went wrong*/
        return false;
    }

    /**
     * Method changes the default user, password and url. Connection is attempted,
     * and the Singleton instance will return the new Connection.
     * Returns true if now conenction is successful
     */
    public static boolean setCredentials(String USER, String PASSWORD, String URL)
    {
        user = USER;
        password = PASSWORD;
        url = URL;

        if(getDatabaseConnection() != null)
        {
            return true;
        }
        return false;
    }

    /**
     *
     */
    public static void closeCurrentConnection()
    {
        if(connection == null){ return; } // return if nothing is to be done


        try
        {
            if(!connection.isClosed())
            {
                connection.close();
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        connection = null;
    }

    public void resetStandardCredentials()
    {
        setCredentials(standardAPropertiesFileAddress);
    }

    public static void runUseDBStatement(String dbName)
    {
        try
        {
            PreparedStatement preparedStatement = getConnection().prepareStatement(
                    "use " + dbName + ";");
            preparedStatement.executeUpdate();
        } catch(SQLException e)
        {
            System.out.println("An SQLException occurred while trying to 'use' database " +
                               "with supplied name " + dbName);
        }
    }
}