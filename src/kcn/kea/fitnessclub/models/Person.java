package kcn.kea.fitnessclub.models;

import kcn.kea.fitnessclub.abstracts.IHaveID;
import kcn.kea.util.Tuple;

import java.util.HashMap;

public class Person implements IHaveID<Integer>
{
    protected int id;
    protected String name;
    protected String cpr;

    public Person()
    {
        id = -1;
        name = "";
        cpr = "";
    }

    public Person(int id, String name, String cpr)
    {
        this.id = id;
        this.name = name;
        this.cpr = cpr;
    }

    public Person(String name, String cpr)
    {
        this.name = name;
        this.cpr = cpr;
    }

    /**
     * Method returns two maps for printing formatted fields of object;
     * any club-related superclass-fields will be included.
     * @return Two hashmaps with string-keys signifying names of fields in Person class.
     *  <b>First/A in tuple</b> is map of field-strings.
     *  <b>Second/B in tuple</b> is map of formatting-strings for fields
     * */
    public Tuple<HashMap<String, String>, HashMap<String, String>> getFormattingAndFields()
    {
        var formattingMap = new HashMap<String,String>();
        var fieldMap = new HashMap<String,String>();

        Tuple<HashMap<String, String>, HashMap<String, String>> fieldFormattingMapsTuple =
                new Tuple<>(fieldMap,formattingMap);

        fieldMap.put("id",getID().toString());
        fieldMap.put("name",getName());
        fieldMap.put("cpr",getCpr());

        formattingMap.put("id","%-5d");
        formattingMap.put("name","%-23s");
        formattingMap.put("cpr","%-14s");

        return fieldFormattingMapsTuple;
    }

    @Override
    public String toString()
    {
        String result = String.format("%-5d%-23s%-14s", id, name, cpr);

        return result;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCpr()
    {
        return cpr;
    }

    public void setCpr(String cpr)
    {
        this.cpr = cpr;
    }

    @Override
    public Integer getID()
    {
        return id;
    }
}
