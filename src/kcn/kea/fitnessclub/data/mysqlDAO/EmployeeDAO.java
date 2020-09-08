package kcn.kea.fitnessclub.data.mysqlDAO;

import kcn.kea.fitnessclub.abstracts.EmployeeType;
import kcn.kea.fitnessclub.data.IDAO;
import kcn.kea.fitnessclub.models.Employee;
import kcn.kea.fitnessclub.models.Person;
import kcn.kea.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class EmployeeDAO implements IDAO<Employee, Integer>
{
    private IDAO<Person, Integer> personDAO;
    private Connection connection;

    public EmployeeDAO()
    {
        personDAO = new PersonDAO();
        connection = DBConnectionManager.getConnection();
    }

    /**
     * Creates a new entity in data-source based on the supplied thing.
     * Returns id of thing if successful; some agreed signal, see implementation.
     *
     * @param thing
     */
    @Override
    public Integer create(Employee thing)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO fitnessclub.employee (idperson,employeetype,baseamount) " +
                    "VALUES (?,?,?) ");

            preparedStatement.setInt(1, thing.getID());
            preparedStatement.setString(2, thing.getEmployementType().toString());
            preparedStatement.setInt(3, thing.getBaseAmount());

            preparedStatement.executeUpdate();

            return thing.getID();

        } catch(SQLException e) {e.printStackTrace();}

        return -1;
    }

    /**
     * Returns a T-type object, read from data-source
     * - queried with supplied id (type U).
     * Returns null if there is no 'thing' by id in db.
     *
     * @param id
     */
    @Override
    public Employee read(Integer id)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM employee WHERE idperson = ?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isAfterLast())
            {
                return extractEmployeeFromResultSet(resultSet);
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        return new Employee(new Person(), EmployeeType.ERROR, 0);
    }

    private Employee extractEmployeeFromResultSet(ResultSet resultSet)
    {
        try
        {
            if(resultSet.next())
            {
                Person person = personDAO.read(resultSet.getInt("idperson"));
                EmployeeType type = EmployeeType.getType(resultSet.getString("employeetype"));
                int baseAmount = resultSet.getInt("baseamount");
                return new Employee(person, type, baseAmount);
            }
        } catch(SQLException e) { e.printStackTrace(); }

        return new Employee(new Person(), EmployeeType.ERROR, 0);
    }

    /**
     * Method queries data-source and returns complete map of type T objects,
     * keyed by entity id (of type U)
     */
    @Override
    public HashMap<Integer, Employee> readAll()
    {
        HashMap<Integer, Employee> employeeMap = new HashMap<>();

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM employee");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(!resultSet.isAfterLast()){
                Employee employee = extractEmployeeFromResultSet(resultSet);
                employeeMap.put(employee.getID(),employee);
            }

        } catch(SQLException e) {e.printStackTrace();}

        return employeeMap;
    }

    /**
     * Method executes update to DB based on supplied thing type-T:
     * Returns true if update was written to DB, false if nothing was written.
     *
     * @param thing
     * @param id
     */
    @Override
    public boolean update(Employee thing, Integer id)
    {
        System.out.println("EmployeeDAO :: Update not implemented");
        return false;
    }

    /**
     * Method removes object from data-source, where data-source entity-id
     * equals the supplied id, and returns a thing of type U
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id)
    {
        System.out.println("EmployeeDAO :: Delete not implemented");
        return false;
    }
}
