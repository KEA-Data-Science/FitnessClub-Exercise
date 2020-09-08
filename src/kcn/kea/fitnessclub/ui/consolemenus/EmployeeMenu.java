package kcn.kea.fitnessclub.ui.consolemenus;

import kcn.kea.fitnessclub.data.ClubDataAccess;
import kcn.kea.fitnessclub.abstracts.EmployeeType;
import kcn.kea.fitnessclub.models.Employee;
import kcn.kea.fitnessclub.models.Month;
import kcn.kea.fitnessclub.ui.Console;
import kcn.kea.fitnessclub.ui.IMenu;
import kcn.kea.util.GET;
import kcn.kea.util.javatuples.Triplet;

import java.util.HashMap;
import java.util.Map;

public class EmployeeMenu extends ConsoleMenu implements IMenu<Console>
{

    public EmployeeMenu(Console ui, ClubDataAccess dataAccess)
    {
        super(ui, dataAccess);
        name = "Employee Menu";
    }

    @Override
    public Console ui(){ return ui; }

    @Override
    public String getName(){ return name; }

    @Override
    public void view()
    {
        boolean menuExited = false;
        while(!menuExited)
        {
            ui.show("");
            ui.show("\tEmployee functionality, pick a number:");
            ui.show("");

            ui.show("\t[1]\tList all instructors");
            ui.show("\t[2]\tList all instructors");
            ui.show("\t[3]\tList all employees");
            ui.show("\t[4]\tFind employee salary");

            int choice = GET.getInteger("Please enter the number of desired menu: ");
            switch(choice)
            {
                case 1:
                    listEmployee(EmployeeType.INSTRUCTOR);
                    break;
                case 2:
                    listEmployee(EmployeeType.ADMINISTRATION);
                    break;
                case 3:
                    listEmployee(EmployeeType.ERROR);
                    break;
                case 4:
                    showSalary();
                    break;
                default:
                    menuExited = true;
                    break;
            }
            menuExited = askIfExitMenu(menuExited);
        }
    }

    private void listEmployee(EmployeeType type)
    {
        ui.show("");
        ui.show("\tListing " + type + " all members basic info:");
        ui.show("");

        HashMap<Integer, Employee> employeeMap = dataAccess.getEmployeeDAO().readAll();

        var empFAF = new Employee().getFormattingAndFields();

        // here goes formatting like hereunder...
        String formattedHeadline = String.format("%-5s%-23s%-14s%-18s%-16s",
                                                 "Person ID", "Name", "CPR", "Employment Type", "Base Amount");
        ui.show(formattedHeadline);

        for(Map.Entry<Integer, Employee> e : employeeMap.entrySet())
        {
            if(e.getValue().getEmployementType() == type || type == EmployeeType.ERROR)
            {
                ui.show(e.getValue().toString() + "   " + e.getValue().getEmployementType());
            }
        }

        ui.show("");
    }

    private void showSalary()
    {
        HashMap<Integer, Employee> employeeMap = dataAccess.getEmployeeDAO().readAll();

        boolean showSalaryInfoNow = GET.getVerification("Would you like to look up someone's salary? " +
                                                        "Type: ' yes ' or ' no '", "yes", "no");
        ui.show("");
        while(showSalaryInfoNow)
        {
            int idChoice = GET.getInteger("Which employee would you like to look up? ");

            Employee employee = employeeMap.get(idChoice);
            if(employee != null)
            {
                ui.show("");
                ui.show("Employee found:");
                ui.show(employee.toString());

                String formattedHeadline = String.format("%-10s%-10s%-14s%-14s%-15s",
                                                         "Month ID", "Person ID", "Year", "Month", "Noted hours");
                ui.show(formattedHeadline);

                var monthMap = dataAccess.getMonthDAO().readAll();
                for(Map.Entry<Triplet<Integer, Integer, Integer>, Month> m : monthMap.entrySet())
                {
                    if(m.getValue().getPersonID() == idChoice)
                    {
                        ui.show(m.getValue().toString()); // prints the month
                    }
                }

            } else{ui.show("Sorry, no employee found by " + idChoice);}

            showSalaryInfoNow = GET.getVerification("Would you like to look up another salary? " +
                                                    "Type: ' yes ' or ' no '", "yes", "no");
        }
    }
}
