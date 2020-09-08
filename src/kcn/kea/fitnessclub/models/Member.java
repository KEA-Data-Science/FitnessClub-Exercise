package kcn.kea.fitnessclub.models;

import kcn.kea.fitnessclub.abstracts.MemberType;
import kcn.kea.util.Tuple;

import java.util.HashMap;

public class Member extends Person
{
    private int membershipID;
    private MemberType membershipType;



    public Member(Person person, MemberType membershipType)
    {
        super(person.getID(), person.getName(), person.getCpr());
        this.membershipType = membershipType;
        this.membershipID = -1;
    }

    public Member(Person person, int membershipID, MemberType membershipType)
    {
        super(person.getID(), person.getName(), person.getCpr());
        this.membershipType = membershipType;
        this.membershipID = membershipID;
    }

    public int getMembershipID()
    {
        return membershipID;
    }

    public void setMembershipID(int membershipID)
    {
        this.membershipID = membershipID;
    }

    public MemberType getMembershipType()
    {
        return membershipType;
    }

    public void setMembershipType(MemberType membershipType)
    {
        this.membershipType = membershipType;
    }

    @Override
    public String toString()
    {
        String result = String.format("%-15s", membershipType);
        return super.toString() + result;
    }

    /**
     * Method returns two maps for printing formatted fields of object;
     * any club-related superclass-fields will be included.
     *
     * @return Two hashmaps with string-keys signifying names of fields in Person class.
     * <b>First/A in tuple</b> is map of field-strings.
     * <b>Second/B in tuple</b> is map of formatting-strings for fields
     */
    @Override
    public Tuple<HashMap<String, String>, HashMap<String, String>> getFormattingAndFields()
    {
        HashMap<String, String> fieldMap = new HashMap<>(), formattingMap = new HashMap<>();

        var fieldsAndFormattingTuple =
                super.getFormattingAndFields();

        fieldMap.put("membershipType", membershipType.toString());
        formattingMap.put("membershipType", "%-15s");

        fieldsAndFormattingTuple.getItemA().putAll(fieldMap);
        fieldsAndFormattingTuple.getItemB().putAll(formattingMap);

        return fieldsAndFormattingTuple;
    }
}
