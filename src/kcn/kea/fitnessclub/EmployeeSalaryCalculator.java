package kcn.kea.fitnessclub;

import kcn.kea.fitnessclub.abstracts.ICalculateSalary;
import kcn.kea.fitnessclub.models.Employee;

import java.time.LocalDateTime;

/** This calculator is NOT implemented properly
 * */
public class EmployeeSalaryCalculator implements ICalculateSalary<Employee>
{

    private double calculateAdministratorSalary(Employee employee)
    {
        return employee.getBaseAmount();
    }

    @Override
    public double calculateSalaryForCurrentMonth(Employee employee)
    {
        /* Switching on EmploymentType, ehm yup */
        switch(employee.getEmployementType())
        {
            case INSTRUCTOR:
                return calculateInstructorSalary(employee);
            case ADMINISTRATION:
                return calculateAdministratorSalary(employee);
        }
        /** Returning 0 if no WorkMonth mathced with current place in time. */
        return 0;
    }

    private double calculateInstructorSalary(Employee employee)
    {
        LocalDateTime date = LocalDateTime.now();

//        for(Month m : employee.getMonths())
//        {
//            if(m.getMonth() == date.getMonthValue() && m.getYear() == date.getYear())
//            {
//                /* here the base amount is interpreted as the hourly par */
//                double salary = employee.getBaseAmount() * m.getNotedHours();
//                return salary;
//            }
//        }

        return -1;
    }
}

///
/// https://www.wikihow.com/Calculate-Leap-Years
//  Calculating leap year:
//        boolean isLeapYear = ((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0);
//        int lengthOfMonth = date.getMonth().length(isLeapYear);