package kcn.kea.fitnessclub.data;

import kcn.kea.fitnessclub.abstracts.EmployeeType;
import kcn.kea.fitnessclub.abstracts.ICalculateSalary;
import kcn.kea.fitnessclub.data.mysqlDAO.EmployeeDAO;
import kcn.kea.fitnessclub.data.mysqlDAO.MemberDAO;
import kcn.kea.fitnessclub.data.mysqlDAO.MonthDAO;
import kcn.kea.fitnessclub.data.mysqlDAO.PersonDAO;
import kcn.kea.fitnessclub.models.Employee;
import kcn.kea.util.DBConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * BookKeeper  class acts as a controller-class, owned by some unknown UI
 * - and as a repository. Because, I had to be lazy at some point,
 * but injecting dependency to exa a db would not be very taxing. But, lazy.
 */
public class ClubDataAccess
{
    private PersonDAO personDAO;
    private MemberDAO memberDAO;
    private EmployeeDAO employeeDAO;
    private MonthDAO monthDAO;

    public ICalculateSalary<Employee> getCalculator()
    {
        return calculator;
    }

    private ICalculateSalary<Employee> calculator;

    public ClubDataAccess(String databaseName, ICalculateSalary<Employee> calculator)
    {
        DBConnectionManager.runUseDBStatement(databaseName);
        personDAO = new PersonDAO();
        memberDAO = new MemberDAO();
        employeeDAO = new EmployeeDAO();
        monthDAO = new MonthDAO();

        this.calculator = calculator;

    }


        public MonthDAO getMonthDAO () { return monthDAO; }

        public PersonDAO getPersonDAO ()
        {
            return personDAO;
        }

        public MemberDAO getMemberDAO ()
        {
            return memberDAO;
        }

        public EmployeeDAO getEmployeeDAO ()
        {
            return employeeDAO;
        }

        /**
         * Method returns salaries for all employess this month
         */
        public HashMap<Employee, Double> calculateSalariesForThisMonth ()
        {
            HashMap<Employee, Double> salaryMap = new HashMap<>();

            HashMap<Integer, Employee> employees = employeeDAO.readAll();

            for(Map.Entry<Integer, Employee> e : employees.entrySet())
            {
                salaryMap.put(e.getValue(), calculator.calculateSalaryForCurrentMonth(e.getValue()));
            }

            return salaryMap;
        }

        /**
         * Method returns salaries for this month for employees of requested type
         */
        public HashMap<Employee, Double> calculateSalariesForThisMonth (EmployeeType employeeType)
        {
            HashMap<Employee, Double> salaryMap = new HashMap<>();
            HashMap<Integer, Employee> employees = employeeDAO.readAll();

            for(Map.Entry<Integer, Employee> e : employees.entrySet())
            {
                if(e.getValue().getEmployementType() == employeeType)
                {
                    salaryMap.put(e.getValue(), calculator.calculateSalaryForCurrentMonth(e.getValue()));
                }
            }

            return salaryMap;
        }

//    public Person findPersonByID(int id)
//    {
//        /* returning Person object */
//        for(Person p : employees) { if(id == p.getID()){ return p; } }
//        for(Person p : members) { if(id == p.getID()){ return p; } }
//
//        /** returning null to signify no object found. is null checking cheaper than allocation? */
//        return null;
//    }
    }
