package kcn.kea.fitnessclub.data.mysqlDAO;

import kcn.kea.fitnessclub.data.IDAO;
import kcn.kea.fitnessclub.models.Person;
import kcn.kea.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PersonDAO implements IDAO<Person, Integer>
{
    private Connection connection; // utilized in all query-executing methods

    public PersonDAO(){connection = DBConnectionManager.getConnection();}

    /**
     * Creates a new entity in data-source based on the supplied thing.
     * Returns id of thing if successful.
     *
     * @param thing
     */
    @Override
    public Integer create(Person thing)
    {
        /* A PreparedStatement includes the data from supplied person */
        /* execution of update via connection might throw */
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO fitnessclub.person (name, cpr) " +
                    "VALUES (?,?)");

            preparedStatement.setString(1, thing.getName());
            preparedStatement.setString(2, thing.getCpr());

            int linesAffected = preparedStatement.executeUpdate();

            Person personFromDB = read(thing.getCpr());

            return personFromDB.getID();


        } catch(SQLException throwables)
        {
            throwables.printStackTrace();
        }

        /* if code reaches here, something went wrong; return -1 as error indicator */
        return -1;
    }

    public Person read(String cpr)
    {
        Person personFromDB = new Person();
        try
        {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM fitnessclub.person WHERE cpr = ?");
            preparedStatement.setString(1, cpr);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){ personFromDB = personFromResultSet(resultSet);}

        } catch(SQLException e) {e.printStackTrace();}

        return personFromDB;
    }

    private Person personFromResultSet(ResultSet resultSet)
    {
        var person = new Person();

        try
        {
            /* i don't need to assemble the full object, but good for refactoring times */
            person.setId(resultSet.getInt("idperson"));
            person.setName(resultSet.getString("name"));
            person.setCpr(resultSet.getString("cpr"));
        } catch(SQLException e) {e.printStackTrace();}

        return person;
    }

    /**
     * Returns a T-type object, read from data-source
     * - queried with supplied id (type U).
     * Returns null if there is no 'thing' by id in db.
     *
     * @param id
     */
    @Override
    public Person read(Integer id)
    {
        Person person = new Person();

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE idperson = ?");

            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return personFromResultSet(resultSet);
            }

        } catch(SQLException e) {e.printStackTrace();}

        return person;
    }

    /**
     * Method queries data-source and returns complete map of type T objects,
     * keyed by entity id (of type U)
     */
    @Override
    public HashMap<Integer, Person> readAll()
    {
        var result = new HashMap<Integer, Person>();

        try
        {
            var preparedStatement = connection.prepareStatement("SELECT * FROM fitnessclub.person");

            ResultSet allPeopleResultSet = preparedStatement.executeQuery();

            while(allPeopleResultSet.next())
            {
                Person assembledPerson = personFromResultSet(allPeopleResultSet);
                result.put(assembledPerson.getID(), assembledPerson);
            }

        } catch(SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return result;
    }

    /**
     * Method executes update to DB based on supplied thing type-T:
     * Returns true if update was written to DB, false if nothing was written.
     *
     * @param thing
     */
    @Override
    public boolean update(Person thing, Integer id)
    {
        System.out.println("PersonDAO::update NOT IMPLEMENTED");
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
        System.out.println("PersonDAO::delete NOT IMPLEMENTED");
        return false;
    }
}
