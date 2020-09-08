package kcn.kea.fitnessclub.abstracts;

import kcn.kea.fitnessclub.models.Employee;

public interface ICalculateSalary<T extends Employee>
{
    /* Method promises to calculate salary of supplied Employee */
    double calculateSalaryForCurrentMonth(T employee);
}
