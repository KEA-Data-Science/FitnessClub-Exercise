package kcn.kea.fitnessclub.models;

import kcn.kea.fitnessclub.abstracts.EmployeeType;
import kcn.kea.util.Tuple;

import java.util.HashMap;

public class Employee extends Person
{
    private EmployeeType employementType;
    private int baseAmount;

    public Employee(Person person, EmployeeType employmentType, int baseAmount)
    {
        super(person.getID(), person.getName(), person.getCpr());
        this.employementType = employmentType;
        this.baseAmount = baseAmount;
    }

    public Employee()
    {
        super(-1, "NA", "000000-0000");
        this.employementType = EmployeeType.ERROR;
        this.baseAmount = 0;
    }

    public void setEmploymentType(EmployeeType employmentType)
    {
        this.employementType = employmentType;
    }


    @Override
    /**
     * Method returns two maps for printing formatted fields of object;
     * any club-related superclass-fields will be included.
     * @return Two hashmaps with string-keys signifying names of fields in Person class.
     *  <b>First/A in tuple</b> is map of field-strings.
     *  <b>Second/B in tuple</b> is map of formatting-strings for fields
     * */
    public Tuple<HashMap<String, String>, HashMap<String, String>> getFormattingAndFields()
    {
        HashMap<String, String> fieldMap = new HashMap<>(), formattingMap = new HashMap<>();

        var fieldsAndFormattingTuple = super.getFormattingAndFields();

        fieldMap.put("employmentType", getEmployementType().toString());
        fieldMap.put("baseAmount", String.valueOf(getBaseAmount()));

        formattingMap.put("employmentType", "%-18s");
        formattingMap.put("baseAmount", "%-16s");

        fieldsAndFormattingTuple.getItemA().putAll(fieldMap);
        fieldsAndFormattingTuple.getItemB().putAll(formattingMap);

        return fieldsAndFormattingTuple;


    }

    public EmployeeType getEmployementType()
    {
        return employementType;
    }

    /**
     * base amount represents fundamental unit of pay an employee
     * earns. for instructors this is measured hourly, for administration, it is monthly
     */
    public int getBaseAmount()
    {
        return baseAmount;
    }

    public void setBaseAmount(int baseAmount){ this.baseAmount = baseAmount; }

    @Override
    public String toString()
    {
        String result = String.format("%-18s%-16d", getEmployementType(), getBaseAmount());

        return super.toString() + result;
    }
}
