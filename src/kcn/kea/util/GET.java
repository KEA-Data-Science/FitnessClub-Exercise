package kcn.kea.util;


import kcn.kea.fitnessclub.ui.Console;

import java.util.NoSuchElementException;
import java.util.Scanner;

// GET klassen indeholder statiske metoder til at hente eller pynte data
public class GET
{
    /* temp easy solution, bad/high coupling */
    public static Console console;

    static
    {
        console = new Console();
    }

    public static Console getConsole()
    {
        return console;
    }

    public static void setConsole(Console console)
    {
        GET.console = console;
    }

    /**
     * Method returns an integer by querying the user
     */
    public static int getInteger()
    {
        return getInteger("Please enter a whole number: ");
    }

    /**
     * Method returns an integer by querying the user; parameter presented as user-message
     */
    public static int getInteger(String queryString)
    {


        Scanner scanner;
        int enteredInt = -1;
        /*user entered integer, initialized to -1 to*/
        boolean integerDetected = false;
        /*boolean registers if an int was entered */
        /*loop runs while integer is not detected*/
        while(!integerDetected)
        {
            try
            {
                console.show("");
                console.showNoCR("\t" + queryString + "");
                /*take user input */
                scanner = new Scanner(System.in);
                /*read next int from scanner object*/
                /*AKA attempt to get entered int */
                enteredInt = scanner.nextInt();
                /*code only reaches this point if what was entered was an int*/
                /*adjustment makes the while loop end*/
                integerDetected = true;

            } catch(Exception e)
            {
                /*catch exception*/
                console.show("");
                console.show("Du indtastede noget, der ikke er et heltal.");
                console.show("");
            }
        }
        return enteredInt;
    }

    /**
     * Method simply gets a string, a zero-length string if things to bad
     */
    public static String getString(String queryText)
    {
        String result = "";

        console.showNoCR(queryText);

        Scanner scanner = new Scanner(System.in);

        if(scanner.hasNext())
        {
            try
            {
                result = scanner.nextLine();
            } catch(NoSuchElementException e)
            {
                /* we're just going to do nothing */
//                System.out.println(e.getMessage());
            }
        }

        return result;
    }

    /**
     * Method queries the user to verify choice: paramter printed as question.
     */
    public static boolean getVerification(String queryText, String confirmationString, String negationString)
    {
        String tempQText = queryText.equals("") ? "Confirm:\ty / n " : queryText;

        Scanner scanner; // scanner to obtain input from user
        String inputString = ""; // where user input is stored;
        boolean verificationObtained = false;

        /* this while loop will run as long as user has not verified choice; deadlock is never good though */
        while(!verificationObtained)
        {
            console.showNoCR(queryText);
            scanner = new Scanner(System.in);
            inputString = scanner.nextLine();
            if(inputString.equals((confirmationString == "") ? "y" : confirmationString)){ return true; }
            if(inputString.equals((negationString == "") ? "n" : negationString)){ return false; }
        }
        // code should never reach here; if so, return false;
        return false;
    }

    public static void enterToContinue()
    {
        enterToContinue("Press enter to continue");
    }

    public static void enterToContinue(String messageString)
    {
        Scanner scanner = new Scanner(System.in);
        console.show("\t" + messageString);
        scanner.nextLine();
    }

}
