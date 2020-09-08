package kcn.kea.fitnessclub.data.mysqlDAO;

import kcn.kea.fitnessclub.abstracts.MemberType;
import kcn.kea.fitnessclub.data.IDAO;
import kcn.kea.fitnessclub.models.Member;
import kcn.kea.fitnessclub.models.Person;
import kcn.kea.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * The DAOs are Data Access Object that enable CRUD operations at object/\db level.
 * DAO instances rely on established connections to a well formed mysql-db.
 */
public class MemberDAO implements IDAO<Member, Integer>
{
    private IDAO<Person, Integer> personDAO;

    private Connection connection; // utilized in all query-executing methods

    public MemberDAO()
    {
        connection = DBConnectionManager.getConnection();
        personDAO = new PersonDAO();
    }


    /**
     * Creates a new entity in data-source based on the supplied thing.
     * Returns id of thing if successful; some agreed signal, see implementation.
     *
     * @param thing
     */
    @Override
    public Integer create(Member thing)
    {
        /* A PreparedStatement includes the data from supplied person */
        /* execution of update via connection might throw */
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO fitnessclub.member (idperson, membershiptype) VALUES (?,?)"
                                                                             );

            preparedStatement.setInt(1, thing.getID());
            preparedStatement.setString(2, thing.getMembershipType().toString());

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
    public Member read(Integer id)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM member WHERE idperson = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return memberFromResultSet(resultSet);
            }

        } catch(SQLException e) { e.printStackTrace(); }
        // when something goes wrong this is dud object
        return new Member(new Person(), MemberType.BASIC);
    }

    /**
     * Assembles a member from resultset
     */
    private Member memberFromResultSet(ResultSet resultSet)
    {
        try
        {

            int idPerson = resultSet.getInt("idperson");
            int membershipID = resultSet.getInt("idmember");

            MemberType type = MemberType.getType(resultSet.getString("membershiptype"));

            Person personIDed = personDAO.read(idPerson);

            return new Member(personIDed, membershipID, type);

        } catch(SQLException e) {e.printStackTrace();}

        return new Member(new Person(), MemberType.BASIC);
    }


    /**
     * Method queries data-source and returns complete map of type T objects,
     * keyed by entity id (of type U)
     */
    @Override
    public HashMap<Integer, Member> readAll()
    {
        var membersMap = new HashMap<Integer, Member>();

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM fitnessclub.member");

            ResultSet resultSet = preparedStatement.executeQuery();

            int i = 0;
            while(resultSet.next())
            {
                Member readMember = memberFromResultSet(resultSet);

                // add, if not bad (id ==-1)
                if(readMember.getID() != -1)
                {
                    membersMap.put(readMember.getID(), readMember);
                }
            }
        } catch(SQLException e) { e.printStackTrace(); }

        return membersMap;
    }

    /**
     * Method executes update to DB based on supplied thing type-T:
     * Returns true if update was written to DB, false if nothing was written.
     *
     * @param thing
     */
    @Override
    public boolean update(Member thing, Integer id)
    {
        System.out.println("MemberDAO::update NOT IMPLEMENTED");
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
        System.out.println("MemberDAO::delete NOT IMPLEMENTED");
        return false;
    }
}
