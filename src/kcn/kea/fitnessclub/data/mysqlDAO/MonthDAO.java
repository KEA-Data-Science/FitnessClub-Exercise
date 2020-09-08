package kcn.kea.fitnessclub.data.mysqlDAO;

import kcn.kea.fitnessclub.data.IDAO;
import kcn.kea.fitnessclub.models.Month;
import kcn.kea.util.DBConnectionManager;
import kcn.kea.util.javatuples.Triplet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

// idperson, year, month
public class MonthDAO implements IDAO<Month, Triplet<Integer, Integer, Integer>>
{
    Connection connection;

    public MonthDAO()
    {
        connection = DBConnectionManager.getConnection();
    }

    /**
     * Creates a new entity in data-source based on the supplied thing.
     * Returns id of thing if successful; some agreed signal, see implementation.
     *
     * @param thing
     */
    @Override
    public Triplet<Integer, Integer, Integer> create(Month thing)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO fitnessclub.month (idperson, year, month, notedhours) VALUES (?,?,?,?)"
                                                                             );

            preparedStatement.setInt(1, thing.getPersonID());
            preparedStatement.setInt(2, thing.getYear());
            preparedStatement.setInt(3, thing.getMonth());
            preparedStatement.setInt(4, thing.getNotedHours());

            int columnsAffected = preparedStatement.executeUpdate();
            if(columnsAffected > 0)
            {
                return new Triplet<>(thing.getPersonID(), thing.getYear(), thing.getMonth());
            } else
            {
                System.out.println("MonthDAO::create - It seems lines were not affected.");
            }

        } catch(SQLException e) {e.printStackTrace();
        DBConnectionManager.runUseDBStatement("fitnessclub");
        }

        return new Triplet<Integer, Integer, Integer>(-1, -1, -1);
    }

    /**
     * Returns a T-type object, read from data-source
     * - queried with supplied id (type U).
     * Returns null if there is no 'thing' by id in db.
     *
     * @param idYearMonth
     */
    @Override
    public Month read(Triplet<Integer, Integer, Integer> idYearMonth)
    {
        Month month = new Month();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM month " +
                    "WHERE idperson = ? " +
                    "AND year = ? " +
                    "AND month = ?");

            preparedStatement.setInt(1, idYearMonth.getValue0());
            preparedStatement.setInt(2, idYearMonth.getValue1());
            preparedStatement.setInt(3, idYearMonth.getValue2());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){ month = monthFromResultSet(resultSet); }

        } catch(SQLException e) {e.printStackTrace();
        DBConnectionManager.runUseDBStatement("fitnessclub");}

        return month;
    }

    private Month monthFromResultSet(ResultSet resultSet)
    {
        Month month = new Month();
        try
        {
            month.setMonthID(resultSet.getInt("idmonth"));
            month.setPersonID(resultSet.getInt("idperson"));
            month.setYear(resultSet.getInt("year"));
            month.setMonth(resultSet.getInt("month"));
            month.setNotedHours(resultSet.getInt("notedhours"));
        } catch(SQLException e) {e.printStackTrace();}

        return month;
    }

    /**
     * Method queries data-source and returns complete map of type T objects,
     * keyed by entity id (of type U)
     */
    @Override
    public HashMap<Triplet<Integer, Integer, Integer>, Month> readAll()
    {
        var monthMap = new HashMap<Triplet<Integer, Integer, Integer>, Month>();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM month");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Month month = monthFromResultSet(resultSet);
                monthMap.put(new Triplet<>(month.getPersonID(),month.getYear(), month.getMonth()), month);
            }
        }catch(SQLException e){e.printStackTrace();}

        return monthMap;
    }

    /**
     * Method executes update to DB based on supplied thing type-T:
     * Returns true if update was written to DB, false if nothing was written.
     *
     * @param thing
     * @param idYearMonth
     */
    @Override
    public boolean update(Month thing, Triplet<Integer, Integer, Integer> idYearMonth)
    {
        return false;
    }

    /**
     * Method removes object from data-source, where data-source entity-id
     * equals the supplied id, and returns a thing of type U
     *
     * @param idYearMonth
     */
    @Override
    public boolean delete(Triplet<Integer, Integer, Integer> idYearMonth)
    {
        return false;
    }
}
