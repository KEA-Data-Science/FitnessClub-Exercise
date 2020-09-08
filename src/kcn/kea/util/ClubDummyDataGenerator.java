package kcn.kea.util;

import kcn.kea.fitnessclub.abstracts.EmployeeType;
import kcn.kea.fitnessclub.data.mysqlDAO.EmployeeDAO;
import kcn.kea.fitnessclub.data.mysqlDAO.MemberDAO;
import kcn.kea.fitnessclub.data.mysqlDAO.MonthDAO;
import kcn.kea.fitnessclub.data.mysqlDAO.PersonDAO;
import kcn.kea.fitnessclub.models.Employee;
import kcn.kea.fitnessclub.models.Member;
import kcn.kea.fitnessclub.models.Month;
import kcn.kea.fitnessclub.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ClubDummyDataGenerator
{
    public void generateData()
    {
        PersonMaker maker = new PersonMaker();
        PersonDAO personDAO = new PersonDAO();
        MemberDAO memberDAO = new MemberDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        /* Creating members */
        List<Person> memberPersons = maker.createPersons(231, 54);
        for(Person p : memberPersons) { p.setId(personDAO.create(p)); }

        List<Member> members = maker.createMembers(memberPersons, 30);
        for(Member member : members) { memberDAO.create(member); }

        /* Creating instructors */
        List<Person> employeePersons = maker.createPersons(23, 52);
        for(Person person : employeePersons) { person.setId(personDAO.create(person)); }

        List<Employee> employees = maker.createEmployee(employeePersons, EmployeeType.INSTRUCTOR, 199);
        for(Employee employee : employees) { employee.setId(employeeDAO.create(employee)); }

        /* Creating administration */
        List<Person> adminPersons = maker.createPersons(12, 71);
        for(Person person : adminPersons) { person.setId(personDAO.create(person)); }

        List<Employee> admins = maker.createEmployee(adminPersons, EmployeeType.ADMINISTRATION, 33000);
        for(Employee employee : admins) { employeeDAO.create(employee); }

        /* Creating months*/
        var personList = new PersonDAO().readAll();

        Random random = new Random();

        ArrayList<Month> months = new ArrayList<>();
        for(Map.Entry<Integer, Person> p : personList.entrySet())
        {
            int baseHours = 5 + random.nextInt(85);
            int year = 2019;
            for(; year < 2021; year++)
            {
                months.addAll(maker.createMonths(-1, p.getKey(), year, baseHours, true));
            }
        }

        MonthDAO monthDAO = new MonthDAO();

        for(Month m : months)
        {
            var id = monthDAO.create(m);
            Month month = monthDAO.read(id);
        }
    }
}
