package kcn.kea.util;


import kcn.kea.fitnessclub.abstracts.EmployeeType;
import kcn.kea.fitnessclub.abstracts.MemberType;
import kcn.kea.fitnessclub.models.Month;
import kcn.kea.fitnessclub.models.Employee;
import kcn.kea.fitnessclub.models.Member;
import kcn.kea.fitnessclub.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Purpose of class is to make a lot of individuals - club needs a population
 */
public class PersonMaker
{


    public static final String[] maleFirstNames = { // længste drengenavn er 9 char
            "Peter", "Jens", "Michael", "Lars", "Henrik",
            "Thomas", "Søren", "Jan", "Christian", "Martin",
            "Niels", "Anders", "Morten", "Jesper", "Hans",
            "Jørgen", "Mads", "Per", "Ole", "Rasmus",
            "Altantsetseg", "Bat Erdene", "Batbayar", "Batjargal",
            "Bolormaa", "Enkhjargal", "Enkhtuya", "Erdenechimeg",
            "Ganbaatar", "Ganbold", "Gantulga", "Ganzorig",
            "Lkhagvasüren", "Monkh", "Erdene", "Monkhbat", "Mönkhtsetseg",
            "Narantsetseg", "Nergüi", "Otgonbayar", "Oyuunchimeg",
            "Altan", "Altansarnai", "Arban", "Bataar", "Batsaikhan",
            "Batu", "Batuhan", "Batukhan", "Batzorig", "Bayarmaa",
            "Chaghatai", "Chenghiz", "Chime", "Chingis"};
    public static final String[] femaleFirstNames = { // længste pigenavn er, wierdly, også 9 char
            "Anne", "Kirsten", "Mette", "Hanne", "Anna",
            "Helle", "Susanne", "Lene", "Maria", "Marianne",
            "Lone", "Camilla", "Pia", "Louise", "Charlotte",
            "Inge", "Bente", "Karen", "Tina", "Jette",
            "Ida", "Emma", "Alma", "Ella", "Sofia", "Freja",
            "Josefine", "Clara", "Anna", "Karla", "Laura", "Alberte", "Olivia",
            "Agnes", "Nora", "Lærke", "Luna", "Isabella",
            "Frida", "Lily", "Victoria", "Aya", "Ellen", "Ellie",
            "Maja", "Mathilde", "Esther", "Mille", "Sofie",
            "Emily", "Astrid", "Liva", "Marie", "Caroline",
            "Rosa", "Emilie", "Sara", "Saga", "Liv",
            "Andrea", "Alba", "Asta", "Hannah", "Naja", "Vilma",
            "Johanne", "Lea", "Vigga", "Gry", "Eva"
    };
    public static String[] lastNames = { // 12 char i længste efternavn
            "Nielsen", "Jensen", "Hansen", "Pedersen", "Andersen",
            "Christensen", "Larsen", "Sørensen", "Rasmussen",
            "Jørgensen", "Petersen", "Madsen", "Kristensen",
            "Olsen", "Thomsen", "Christiansen", "Poulsen",
            "Johansen", "Møller", "Mortensen"};
    public static String[] roadNames = {"Klaruphave", "Færgevej", "Mediebyen",
            "Ole Lund Kirkegaards Allé", "Seglen", "Peter Panums Vej", "Bellahøjvænget",
            "Flovt Bygade", "Søgård Alle", "Ellipsehaven", "Degns Hauge",
            "Dorthe Lottrups Lund", "Flækken", "Hedvig Billes Top", "Humle Hauge",
            "Karine Krumpen", "Kirsten Munks Dal", "Marie Haves Vænge", "Nørrestrands Alle",
            "Stikkelstien"};

    public List<Person> createPersons(int amount, int percentFemale)
    {
        Random genie = new Random();
        ArrayList<Person> persons = new ArrayList<>();
        int i = 0;

        while(i++ < amount)
        {
            int percent = genie.nextInt(101);
            persons.add(new Person(getFullName((percent < percentFemale) ? true : false), getCpr()));
        }
        return persons;
    }

    public String getFullName(boolean femaleName)
    {
        String fullName = "";

        fullName += femaleName ?
                femaleFirstNames[((int)(Math.random() * femaleFirstNames.length))] :
                maleFirstNames[((int)(Math.random() * maleFirstNames.length))];

        fullName += " " + lastNames[((int)(Math.random() * lastNames.length))];

        return fullName;
    }

    public String getCpr()
    {
        StringBuilder newCpr = new StringBuilder();

        newCpr.append(getRandomIntString(6, true, true));
        newCpr.append("-");
        newCpr.append(getRandomIntString(4, true, true));

        return newCpr.toString();
    }

    public String getRandomIntString(int numberLength, boolean zeroFirstOkay, boolean allowZero)
    {

        StringBuilder sNumber = new StringBuilder();
        Random generator = new Random();
        int remainingLength = numberLength;


        /*first round */
        int firstInt = getInt(zeroFirstOkay ? 1 : 0, 10);
        sNumber.append(firstInt);
        remainingLength--;

        while(--remainingLength >= 0)
        {
            sNumber.append(getInt(allowZero ? 0 : 1, 10));
        }

        return sNumber.toString();
    }

    public int getInt(int lowerBound, int upperBound)
    {
        Random generator = new Random();
        int result = lowerBound + generator.nextInt(upperBound);
        return result;
    }

    public List<Member> createMembers(List<Person> personList, int percentFullMember)
    {
        ArrayList<Member> newMembers = new ArrayList<>();
        Random genie = new Random();

        for(int i = 0; i < personList.size(); i++)
        {
            int percent = genie.nextInt(101);
            newMembers.add(new Member(personList.get(i),
                                      percent < percentFullMember ? MemberType.FULL : MemberType.BASIC));

        }

        return newMembers;// not done
    }

    public List<Employee> createEmployee(List<Person> persons, EmployeeType employeeType, int baseAmount)
    {
        List<Employee> employees = new ArrayList<>();
        Random genie = new Random();

        for(int i = 0; i < persons.size(); i++)
        {
            int percent = genie.nextInt(101);
            employees.add(new Employee(persons.get(i), employeeType, baseAmount));
        }

        return employees;
    }

    public List<Month> createMonths(int id, int personID, int year, int hoursNoted, boolean hoursVariable)
    {
        List<Month> generatedMonths = new ArrayList<>();
        Random genie = new Random();

        int mCount = 1;
        for(; mCount < 13; mCount++)
        {
            int hours = hoursVariable ? ((int)(hoursNoted*0.7) + genie.nextInt(hoursNoted)):hoursNoted;

            generatedMonths.add(new Month(id,personID,mCount,year,hours));
        }

        return generatedMonths;
    }
}


//    /**
//     * Purpose of method is to populate lists with proxy people
//     */
//    public ArrayList<Person> createMembers(int numberOfPeople, int baseYear, int ageVariance, Role role)
//    {
//        ArrayList<Person> newMembers = new ArrayList<>();
//
//        for(int i = 1; i < numberOfPeople + 1; i++)
//        {
//            newMembers.add(
//                    new Person(-1,
//                               ((i % 2 == 0) ? getFirstName(false) : getFirstName(true)) +
//                               " " + getLastName(),
//                               new Date((int)(Math.random() * 30),
//                                        (int)(Math.random() * 12),
//                                        (int)(Math.random() * ageVariance) + baseYear),
//                               ((i % 3 == 0) ? Gender.Male : Gender.Female),
//                               new Address(roadNames[(int)((Math.random() * roadNames.length - 1))],
//                                           (int)(Math.random() * 342),
//                                           1000 + (int)(Math.random() * 8999)),
//                               role
//                    ));
//        }
//
//        return newMembers;
//    }
//
//
//    /**
//     * Returns a name
//     *
//     * @param femaleName if set to true, method returns female name,
//     *                   <p>if set to false, method returns a male name </p>
//     */
//    private String getFirstName(boolean femaleName)
//    {
//
//        int randomIndex = (int)(Math.random() * (
//                (femaleName ? femaleFirstNames.length - 1 : maleFirstNames.length - 1)));
//
//        return (femaleName ? femaleFirstNames[randomIndex] : maleFirstNames[randomIndex]);
//    }
//
//    private String getLastName()
//    {
//        int randomIndex = (int)(Math.random() * lastNames.length - 1);
//
//        return lastNames[randomIndex];
//    }
//
//    /**
//     * Development time method; creates x number of Persons Role.Member, and saves then to file
//     */
//    public void writeNewPeopleToFile(String pathToFile, int baseYear,
//                                     int ageVariance, Role role, int numberOfPeopleToMake)
//    {
//        /* Cooking up som people */
//        ArrayList<Person> newPersons = createMembers(numberOfPeopleToMake, baseYear, ageVariance, role);
//
//        ArrayList<String> newPersonsCSV = new ArrayList<>();
//        /* Transforming each new person into a CSV string */
//        for(Person person : newPersons)
//        {
//            newPersonsCSV.add(person.toCSV());
//        }
//
//        FileIO.writeLinesToTextFile(newPersonsCSV, pathToFile, true);
//    }

//    /**
//     * Development time method; USE WITH CAUTION, will delete/overwrite old member-files.
//     * <p><b> Also, leaders (particularly Cashiers )need to have Role adjusted in the files manually,
//     * since writeNewMembersToFile(.) return just CEO leaders - so go edit the files if you
//     * repopulate club; and then new Performance times, teams, and bills need to be
//     * regenerated, because they are tied for particular IDs (). </b></p>
//     */
//    public void repopulateFiles(int numberOfMembers, int numberOfTrainers, int numberOfLeaders)
//    {
//        writeNewMembersToFile("src/resource/MemberList.txt", numberOfMembers, Role.Member);
//        writeNewMembersToFile("src/resource/TrainerList.txt", numberOfTrainers, Role.Trainer);
//        writeNewMembersToFile("src/resource/LeaderList.txt", numberOfLeaders, Role.CEO); // NB; just CEO
//    }


//    public static void givePhoneNumberToConsuela(ArrayList<Number> numbers, int lastNumber)
//    {
//        for(int i = -1, k = 0; i < numbers.size() && !(i == lastNumber);
//            numbers.add(i + k),
//                    System.out.print(numbers.size() + " "))
//        {
//            switch((k-- + ++i))
//            {
//                case -1:
//                    System.out.println("My nose, Juan.");
//                    break;
//                case 0:
//                    System.out.println("Lemon pledge!");
//                    break;
//                case 1:
//                    System.out.println("Juan!");
//                    break;
//                case 401555112:
//                    System.out.println("4.");
//                default:
//                    System.out.println((i < k) ? numbers.get(i) : "No.");
//            }
//        }
//        System.out.println("Let me get my pen.");
//    }