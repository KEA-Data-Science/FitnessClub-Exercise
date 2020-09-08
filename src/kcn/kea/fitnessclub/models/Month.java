package kcn.kea.fitnessclub.models;

import kcn.kea.util.Tuple;

import javax.lang.model.element.NestingKind;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Month
{
    private int monthID;
    private int personID;
    private int month;
    private int year;
    private int notedHours;

    public Tuple<HashMap<String,String>,HashMap<String,String>> getFieldsAndFormatting(){

        var formattingMap = new HashMap<String,String>();
        var fieldMap = new HashMap<String,String>();

        Tuple<HashMap<String, String>, HashMap<String, String>> fieldFormattingMapsTuple =
                new Tuple<>(fieldMap,formattingMap);

        fieldMap.put("monthID", String.valueOf(monthID));
        fieldMap.put("personID", String.valueOf(personID));
        fieldMap.put("year", String.valueOf(year));
        fieldMap.put("month", String.valueOf(month));
        fieldMap.put("notedHours", String.valueOf(notedHours));

        formattingMap.put("monthID","%-10d%");
        formattingMap.put("personID","-10d%");
        formattingMap.put("year","-14d%");
        formattingMap.put("month","-14d%");
        formattingMap.put("notedHours","-15d");

        return fieldFormattingMapsTuple;
    }

    @Override
    public String toString()
    {
        String result = String.format("%-10d%-10d%-14d%-14d%-15d",monthID,personID,year,month,notedHours);

        return result;
    }

    public Month(int monthID, int personID, int month, int year, int notedHours)
    {
        this.monthID = monthID;
        this.personID = personID;
        this.month = month;
        this.year = year;
        this.notedHours = notedHours;
    }
    /** Constructor news up a WorkMonth for this month */
    public Month()
    {
        LocalDateTime now = LocalDateTime.now();
        month = now.getMonthValue();
        year = now.getYear();
        this.notedHours = 0;

        personID = -1;
    }

    public int getMonthID()
    {
        return monthID;
    }

    public void setMonthID(int monthID)
    {
        this.monthID = monthID;
    }

    public int getPersonID()
    {
        return personID;
    }

    public void setPersonID(int personID)
    {
        this.personID = personID;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getNotedHours()
    {
        return notedHours;
    }

    public void setNotedHours(int notedHours)
    {
        this.notedHours = notedHours;
    }
}
