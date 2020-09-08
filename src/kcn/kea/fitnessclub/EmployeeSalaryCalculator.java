package kcn.kea.fitnessclub;

import kcn.kea.fitnessclub.abstracts.EmployeeType;
import kcn.kea.fitnessclub.abstracts.ICalculateSalary;
import kcn.kea.fitnessclub.data.mysqlDAO.MonthDAO;
import kcn.kea.fitnessclub.models.Employee;
import kcn.kea.util.javatuples.Triplet;

import java.time.LocalDateTime;

/**
 * This calculator is NOT implemented properly
 */
public class EmployeeSalaryCalculator implements ICalculateSalary<Employee>
{
    private MonthDAO monthDAO;

    public EmployeeSalaryCalculator()
    {
        monthDAO = new MonthDAO();
    }

    @Override
    public double calculateSalaryForCurrentMonth(Employee employee)
    {
        LocalDateTime date = LocalDateTime.now();

        var currentMonthTriplet = new Triplet<Integer, Integer, Integer>(employee.getID(),
                                                                         date.getYear(),
                                                                         date.getMonth().getValue());
        return calculateSalary(employee, currentMonthTriplet);

    }

    public double calculateSalary(Employee employee, Triplet<Integer, Integer, Integer> personIdYearMonth)
    {
        double salary = -1;
        if(employee.getEmployementType() == EmployeeType.ERROR){return salary;}

        double baseAmount = employee.getBaseAmount();

        LocalDateTime date = LocalDateTime.now();

        var fcMonth = monthDAO.read(personIdYearMonth);

        /* here the base amount is interpreted as the hourly par */
        if(employee.getEmployementType() == EmployeeType.INSTRUCTOR)
        {
            salary = baseAmount * fcMonth.getNotedHours();
        }

        return salary;
    }

    private double calculateAdministratorSalary(Employee employee)
    {
        return employee.getBaseAmount();
    }
}

///
/// https://www.wikihow.com/Calculate-Leap-Years
//  Calculating leap year:
//        boolean isLeapYear = ((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0);
//        int lengthOfMonth = date.getMonth().length(isLeapYear);